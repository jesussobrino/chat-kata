package org.ejmc.android.simplechat.net;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.ejmc.android.simplechat.model.ChatList;
import org.ejmc.android.simplechat.model.Message;
import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

/**
 * Proxy to remote API.
 * 
 * @author startic
 * 
 */
public class NetRequests {

	/**
	 * Gets chat messages from sequence number.
	 * 
	 * @param seq
	 * @param handler
	 */
	public void chatGET(int seq, NetResponseHandler<ChatList> handler) {

		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httpget = new HttpGet(
					"http://172.20.0.9/chat-kata/api/chat");
			HttpResponse response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			String entityStr = EntityUtils.toString(entity);

			if (entity != null) {
				JSONObject jsonObject = new JSONObject(entityStr);
				
				//Última Secuencia de JSon
				Integer lastSequence = jsonObject.getInt("last_seq");

				//Mensajes recibidos de JSon
				JSONArray jsonMainArr = new JSONArray();
				jsonMainArr = jsonObject.getJSONArray("messages");
				
				// Recorremos el JSONArray con los mensajes para añadirlos a ChatList
				Message jsonMessage;
				ChatList chatList = new ChatList();
				chatList.setLastSeq(lastSequence);

				for (int i = 0; i < jsonMainArr.length(); i++) {
					JSONObject childJson = jsonMainArr.getJSONObject(i);
					jsonMessage = new Message(childJson.getString("nick"), childJson.getString("message"));
					
					chatList.addMessage(jsonMessage);
				}

				handler.onSuccess(chatList);

				Log.i("Entity: .getContentLength()","" + entity.getContentLength());
			}

		} catch (Exception e) {
			Log.e("Net", "Error in network call", e);
		}
	}

	/**
	 * POST message to chat.
	 * 
	 * @param message
	 * @param handler
	 */
	public void chatPOST(Message message, NetResponseHandler<Message> handler) {

		try {
			HttpClient httpclient = new DefaultHttpClient();

			HttpPost httppost = new HttpPost("http://172.20.0.9/chat-kata/api/chat");
			httppost.setHeader("content-type", "application/json");
			JSONObject datoChat = new JSONObject();
			datoChat.put("nick", message.getUserMessage());
			datoChat.put("message", message.getMessage());

			StringEntity entity = new StringEntity(datoChat.toString());
			httppost.setEntity(entity);

			HttpResponse response = httpclient.execute(httppost);
			HttpEntity responseEntity = response.getEntity();

			if (responseEntity != null) {
				String responseString = EntityUtils.toString(responseEntity);
				Log.i("responseString", responseString);
			}

			Log.i("chatPOST: OK", "" + null);
		} catch (Exception e) {
			Log.e("Net", "Error in network call", e);
		}
	}
	
	public String getLastSequenceNumber(){
		return null;
		
	}

}
