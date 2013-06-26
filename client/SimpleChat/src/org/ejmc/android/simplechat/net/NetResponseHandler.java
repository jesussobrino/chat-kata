package org.ejmc.android.simplechat.net;

import org.ejmc.android.simplechat.model.ChatList;
import org.ejmc.android.simplechat.model.RequestError;

/**
 * Empty response handler.
 * 
 * Base class for Net Response handlers.
 * 
 * @author startic
 * 
 * @param <Response>
 */
public class NetResponseHandler<Response> {
	private ChatList chatList;
	
	
	/**
	 * Handles a successful request
	 * */
	public void onSuccess(Response response) {
		this.chatList = (ChatList) response;
	}

	/**
	 * Handles a network error.
	 */
	public void onNetError() {

	}

	/**
	 * Handles a request error.
	 */
	public void onRequestError(RequestError error) {

	}
	
	/**
	 * @return the chatList
	 */
	public ChatList getChatList() {
		return chatList;
	}

	/**
	 * @param chatList the chatList to set
	 */
	public void setChatList(ChatList chatList) {
		this.chatList = chatList;
	}
}
