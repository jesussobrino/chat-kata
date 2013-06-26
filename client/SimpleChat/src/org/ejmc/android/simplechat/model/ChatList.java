package org.ejmc.android.simplechat.model;

import java.util.ArrayList;

/**
 * List off chat messages..
 * 
 * @author startic
 * 
 */
public class ChatList {
	private ArrayList<Message> messages;
	private Integer lastSeq;

	/**
	 * @param messages
	 */
	public ChatList() {
		super();
		this.messages = new ArrayList<Message>();
		this.lastSeq = 0;
	}

	

	/**
	 * @return the messages
	 */
	public ArrayList<Message> getMessages() {
		return messages;
	}

	/**
	 * @param messages the messages to set
	 */
	public void setMessages(ArrayList<Message> messages) {
		this.messages = messages;
	}
	
	
	/**
	 * @return the lastSeq
	 */
	public Integer getLastSeq() {
		return lastSeq;
	}



	/**
	 * @param lastSeq the lastSeq to set
	 */
	public void setLastSeq(Integer lastSeq) {
		this.lastSeq = lastSeq;
	}



	/**
	 * Añade mensajes a lista de chat
	 * @param msg
	 */
	public void addMessage(Message msg) {
		messages.add(msg);
	}

}
