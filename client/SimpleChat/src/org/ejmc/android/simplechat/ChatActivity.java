package org.ejmc.android.simplechat;

import java.util.ArrayList;

import org.ejmc.android.simplechat.model.ChatList;
import org.ejmc.android.simplechat.model.Message;
import org.ejmc.android.simplechat.net.NetRequests;
import org.ejmc.android.simplechat.net.NetResponseHandler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Chat activity.
 * 
 * @author startic
 */
public class ChatActivity extends Activity {

	private TextView loggedUserEditText;
	private EditText messageEditText;
	private Integer lastSequence = Integer.valueOf(0);
	//private ListView messageHistory;
	
//	private Handler hilosHandler = new Handler(){
//		public void handleMessage(android.os.Message msgAndroid){
//			ChatList chatList = (ChatList) msgAndroid.obj;
//			ArrayList<Message> messages = chatList.getMessages();
//			
//			for (Message message : messages) {
//				messageHistory.append("\n "+message.getUserMessage()+" dice: "+message.getMessage());
//			}
//		}
//	};
	
	
	private Handler hilosHandler = new Handler() {
		/**
		 * @param msgAndroid
		 */
		public void handleMessage(android.os.Message msgAndroid) {
			ChatList chatList = (ChatList) msgAndroid.obj;
			lastSequence = chatList.getLastSeq();
			ArrayList<String> listaMessagesString = new ArrayList<String>();
			for (Message message : chatList.getMessages()) {
				listaMessagesString.add("\n " + message.getUserMessage()+" "+ getResources().getString(R.string.userSays) +" "+ message.getMessage());
			}
			
			ArrayAdapter<String> adaptador = new ArrayAdapter<String>(
					ChatActivity.this, android.R.layout.simple_list_item_1, listaMessagesString);

			ListView lv = (ListView) findViewById(R.id.messageHistory);
			lv.setAdapter(adaptador);
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);

		loggedUserEditText = (TextView) findViewById(R.id.loggedUserEditText);
		messageEditText = (EditText) findViewById(R.id.messageEditText);
		//messageHistory = (ListView) findViewById(R.id.messageHistory);

		Bundle bundle = getIntent().getExtras();
		loggedUserEditText.setText(bundle.getString("userName"));

		// Show the Up button in the action bar.
		setupActionBar();

	}

	/**
	 * Método POST al pinchar en el botón "enviar"
	 * @param view
	 */
	public void sendChat_Onclick(View view) {
		
		if(messageEditText.getText() != null && !messageEditText.getText().toString().equals("")){

			new Thread(new Runnable() {
				public void run() {
					NetRequests netRequests = new NetRequests();
					NetResponseHandler<Message> handler = new NetResponseHandler<Message>();
					Message message = new Message();
					message.setMessage(messageEditText.getText().toString());
					message.setUserMessage(loggedUserEditText.getText().toString());
					try {

						netRequests.chatPOST(message, handler);
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			}).start();
			
			messageEditText.setText("");
			
		}else{
			Toast.makeText(getApplicationContext(), R.string.alertSetMessage, Toast.LENGTH_LONG).show();
		}

	}
	
	
	/**
	 * Método GET al pulsar el botón "Recibir"
	 * @param view
	 */
	public void getChat_Onclick(View view) {
		new Thread(new Runnable() {
			public void run() {
				NetRequests netRequests = new NetRequests();
				NetResponseHandler<ChatList> handler = new NetResponseHandler<ChatList>();
				
				try {

					netRequests.chatGET(lastSequence, handler);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				android.os.Message mensajeAnd = new android.os.Message();
				mensajeAnd.obj = handler.getChatList();
				hilosHandler.sendMessage(mensajeAnd);


			}
		}).start();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.chat, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().hide();
	}

}
