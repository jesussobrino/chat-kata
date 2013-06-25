package chat.kata

//import chat.kata.command.SendChatMessageCommand;

class ChatController {
	ChatService chatService

	static constraints = { seq istanceof Integer }



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
			render(status:400, contentType: "text/json"){   error="Invalid seq parameter" }
		}
		else{
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
	}


	/**
	 * Método enviar (POST)
	 * @return
	 */
	def send(){
		def jsonVar = request.JSON

		if(jsonVar){
			def msgs = new ChatMessage(jsonVar)

			if(msgs.validate()){

				chatService.putChatMessage(msgs);
				render(status: 201)
			}
			else{//Es un JSON con constraints nick / message
				if(msgs.errors.hasFieldErrors("nick")){
					render(status:400, contentType: "text/json"){  error="Missing nick parameter"  }
				}

				if(msgs.errors.hasFieldErrors("message")){
					render(status:400, contentType: "text/json"){  error="Missing message parameter"  }
				}

			}
		}else{ // NO ES JSON

			render(status:400, contentType: "text/json"){  error="Invalid body"  }
		}

	}
}