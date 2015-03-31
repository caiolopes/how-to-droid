package com.caiolopes.howto;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.caiolopes.howto.R;

/**
 * The Class How To.
 */
public class HowTo extends Activity {
	
	/** The file the will be open. */
	private String file;
	
	/** The order of files. */
	private ArrayList<String> order;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_how_to);
		WebView myWebView = (WebView) findViewById(R.id.webview);

		if (savedInstanceState == null) {
			Bundle extras = getIntent().getExtras();
			if (extras == null) {
				file = null;
			} else {
				file = extras.getString("FILE");
				order = (ArrayList<String>) getIntent().getSerializableExtra(
						"ORDER");
			}
		} else {
			file = (String) savedInstanceState.getSerializable("FILE");
			order = (ArrayList<String>) savedInstanceState
					.getSerializable("ORDER");

		}

		final Button nextButton = (Button) findViewById(R.id.next);
		final Button previousButton = (Button) findViewById(R.id.previous);

		nextButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (order.indexOf(file) + 1 >= order.size()) {
					Toast.makeText(getApplicationContext(),
							"There is no next how to", Toast.LENGTH_LONG)
							.show();
				} else {
					openHowTo(order.get(order.indexOf(file) + 1));
				}
			}
		});

		previousButton.setOnClickListener(new View.OnClickListener() {
			
			/* (non-Javadoc)
			 * @see android.view.View.OnClickListener#onClick(android.view.View)
			 */
			public void onClick(View v) {
				if (order.indexOf(file) - 1 < 0) {
					Toast.makeText(getApplicationContext(),
							"There is no previous how to", Toast.LENGTH_LONG)
							.show();
				} else {
					openHowTo(order.get(order.indexOf(file) - 1));
				}
			}
		});

		myWebView.loadUrl("file:///android_asset/" + file + ".html");
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.how_to, menu);
		return true;
	}

	/**
	 * Open how to.
	 *
	 * @param filename the filename
	 */
	protected void openHowTo(String filename) {
		WebView myWebView = (WebView) findViewById(R.id.webview);
		myWebView.loadUrl("file:///android_asset/" + filename + ".html");
		this.file = filename;
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
