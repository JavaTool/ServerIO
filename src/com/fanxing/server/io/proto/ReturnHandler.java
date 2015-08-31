package com.fanxing.server.io.proto;

import com.fanxing.server.io.proto.protocol.MessageIdProto.MessageId;
import com.fanxing.server.io.proto.RedirectRequest;
import com.fanxing.server.io.proto.Request;
import com.fanxing.server.io.dispatch.IContentHandler;
import com.fanxing.server.io.dispatch.IContent;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public class ReturnHandler implements IContentHandler {

	@Override
	public void handle(IContent content) throws Exception {
		switch (MessageId.valueOf(content.getMessageId())) {
		default : 
		}
	}

}
