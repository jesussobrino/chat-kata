package org.ejmc.android.simplechat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Main activity.
 * 
 * Shows login config.
 * 
 * @author startic
 * 
 */
public class LoginActivity extends Activity {

	private Button loginButton;
	private EditText userNameTextEdit;
	private EditText passwordTextEdit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		loginButton = (Button) findViewById(R.id.loginButton);
		userNameTextEdit = (EditText) findViewById(R.id.userNameEditText);
		passwordTextEdit = (EditText) findViewById(R.id.passwordEditText);

		loginButton = (Button) findViewById(R.id.loginButton);

	}
	
	public void loginButton_OnClick(View view){
		Intent i = new Intent(this, ChatActivity.class);
        i.putExtra("userName", userNameTextEdit.getText().toString());
        startActivity(i);
	}
	
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
