package org.ejmc.android.simplechat;



import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * Chat activity.
 * 
 * @author startic
 */
public class ChatActivity extends Activity {
	
	TextView loggedUserEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);
		
		loggedUserEditText = (TextView) findViewById(R.id.loggedUserEditText);
        Bundle bundle = getIntent().getExtras();
        loggedUserEditText.setText(bundle.getString("userName"));


		// Show the Up button in the action bar.
		setupActionBar();

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
