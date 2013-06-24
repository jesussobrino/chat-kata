package chat.kata

import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantReadWriteLock



class ChatService {

	private ArrayList <ChatMessage> listaMensajes = new ArrayList<>()
	private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
	private final Lock r = rwl.readLock();
	private final Lock w = rwl.writeLock();


	/**
	 * Collects chat messages in the provided collection
	 * 
	 * @param if specified messages are collected from the provided sequence (exclusive)
	 * @param messages the collection where to add collected messages
	 * 
	 * @return the sequence of the last message collected.
	 */
	Integer collectChatMessages(Collection<ChatMessage> collector, Integer fromSeq = null){
		r.lock()
		try{
			if(fromSeq == null){
				fromSeq = 0
			}else{
				fromSeq = fromSeq+1
			}

			while(listaMensajes.size() > fromSeq){
				collector.add(listaMensajes[fromSeq])
				fromSeq++
			}

			fromSeq--
		}
		
		finally{
			r.unlock()
		}
		
		return fromSeq
	}


	/**
	 * Puts a new message at the bottom of the chat
	 * 
	 * @param message the message to add to the chat
	 */
	void putChatMessage(ChatMessage message){
		w.lock()
		
		try{
			if(message != null)
			listaMensajes.add(message)
		}
		
		finally{
			w.unlock()
		}
	}
}
