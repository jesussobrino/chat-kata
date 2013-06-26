package org.ejmc.android.simplechat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Main activity.
 * 
 * Shows login config.
 * 
 * @author startic
 * 
 */
public class LoginActivity extends Activity {

	private EditText userNameTextEdit;
	private EditText passwordTextEdit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		userNameTextEdit = (EditText) findViewById(R.id.userNameEditText);
		passwordTextEdit = (EditText) findViewById(R.id.passwordEditText);

	}
	
	/**
	 * Botón de login
	 * @param view
	 */
	public void loginButton_OnClick(View view){
		
		Boolean loginConPassword = false;
		
		if(userNameTextEdit.getText() != null && !userNameTextEdit.getText().toString().equals("")){
			
			
	        if(passwordTextEdit.getText() != null && passwordTextEdit.getText().toString().equals("")){
	        	Toast.makeText(getApplicationContext(), R.string.alertSetPassword, Toast.LENGTH_LONG).show();
	        	//Para habilitar login con Password obligatorio, se debe marcar como true la siguiente variable
	        	loginConPassword = false;
			}
	        
	        if(!loginConPassword){
		        Intent i = new Intent(this, ChatActivity.class);
		        i.putExtra("userName", userNameTextEdit.getText().toString());
		        startActivity(i);
	        }
	        
		}else{
			Toast.makeText(getApplicationContext(), R.string.alertSetUser, Toast.LENGTH_LONG).show();
		}
		
	}
	
	/**
	 * Botón de Limpiar
	 * @param view
	 */
	public void cleanButton_onClick(View view){
		userNameTextEdit.setText("");
		passwordTextEdit.setText("");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

}
