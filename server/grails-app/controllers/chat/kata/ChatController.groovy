package chat.kata

//import chat.kata.command.SendChatMessageCommand;

class ChatController {
	ChatService chatService

	
	/**
	 * Método listar (SEND)
	 * Montará un json, al que le añadirá:	- messages(nick + message)
	 * 										- last_seq (secuencia)
	 * @param seq
	 * @return
	 */
	def list(Integer seq) {
		if(hasErrors()){
			log.error("Invalid seq: ${errors.getFieldError('seq').rejectedValue}")
			//TODO: implement me
		}
		final Collection<ChatMessage> collector = []
		final int lastSequence = chatService.collectChatMessages(collector, seq)

		render(contentType: "text/json"){
			messages = []
			for (int i = 0; i < collector.size(); i++) {
				ChatMessage chat = collector[i]
				messages.add(nick:chat.nick, message:chat.message)
			}
			last_seq = lastSequence
		}
	}


	/**
	 * Método enviar (POST)
	 * @return
	 */
	def send(){
		def msgs = new ChatMessage(request.JSON)
		chatService.putChatMessage(msgs);
		
		render(status: 201)
	}
}