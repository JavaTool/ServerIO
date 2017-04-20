package org.tool.server.io.dispatch;

import java.io.DataOutputStream;

import org.tool.server.anthenticate.IDataAnthenticate;

/**
 * A factory of {@link IContent} creator.
 * @author 	hyfu
 */
public interface IContentFactory {
	
	/**
	 * session id name.
	 */
	String SESSION_ID = "SESSION_ID";
	
	/**
	 * To create a content from full params.
	 * @param 	sessionId
	 * 			Indicate a id of client who connect server.
	 * @param 	messageId
	 * 			Indicate this content what to do.
	 * @param 	datas
	 * 			Byte array datas of message.
	 * @param 	sender
	 * 			Use to send message to server/client
	 * @return	{@link IContent}
	 */
	IContent createContent(String ip, byte[] datas, String sessionId, int messageId, ISender sender, int serial);
	/**
	 * To create a content from byte array and sender.
	 * @param 	data
	 * 			Include sessionId, messageId and byte array datas of message.
	 * @param 	sender
	 * 			Use to send message to server/client
	 * @return	{@link IContent}
	 */
	IContent createContent(String ip, byte[] data, ISender sender);
	
	IDataAnthenticate<byte[], DataOutputStream> getDataAnthenticate();

}