package org.tool.server.thread;

import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import gnu.trove.impl.unmodifiable.TUnmodifiableIntObjectMap;
import gnu.trove.map.TIntObjectMap;

public final class MessageProcessorGroup implements IMessageProcessor<IMessagePackage> {
	
	private static final Logger log = LoggerFactory.getLogger(MessageProcessorGroup.class);
	
	private final Map<IThreadType, IMessageProcessor<IMessagePackage>> processors;
	
	private final TIntObjectMap<IThreadType> threadIds;
	
	public MessageProcessorGroup(TIntObjectMap<IThreadType> threadTypes, IMessageProcessorFactory<IMessagePackage> factory) {
		this.threadIds = new TUnmodifiableIntObjectMap<>(threadTypes);
		Set<IThreadType> threadIdSet = Sets.newHashSet();
		threadTypes.forEachValue(value -> {
			if (!threadIdSet.contains(value)) {
				threadIdSet.add(value);
			}
			return true;
		});
		Map<IThreadType, IMessageProcessor<IMessagePackage>> processors = Maps.newHashMapWithExpectedSize(threadIdSet.size());
		threadIdSet.forEach(threadType -> addMessageProcessor(threadType, factory, processors));
		this.processors = ImmutableMap.copyOf(processors);
	}
	
	private static void addMessageProcessor(IThreadType threadType, 
			IMessageProcessorFactory<IMessagePackage> factory, 
			Map<IThreadType, IMessageProcessor<IMessagePackage>> processors) {
		processors.put(threadType, factory.create());
		log.info("Start messageProcessor {}.", threadType);
	}

	@Override
	public void put(IMessagePackage msg) {
		if (msg == null) {
			return;
		}
		
		int messageId = msg.getMessageId();
		if (threadIds.containsKey(messageId)) {
			processors.get(threadIds.get(messageId)).put(msg);
		} else {
			log.warn("Unhandle message {}.", messageId);
		}
	}

}
