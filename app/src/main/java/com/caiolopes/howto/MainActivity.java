package com.caiolopes.howto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

import com.caiolopes.howto.R;

/**
 * The Class MainActivity.
 * Uses an Expandable List to show all the how to categories and items
 */
public class MainActivity extends Activity {
	
	/** The file list. */
	private ArrayList<String> fileList = new ArrayList<String>();
	
	/** The list adapter. */
	ExpandableListAdapter listAdapter;
	
	/** The expandable list view. */
	ExpandableListView expListView;
	
	/** The order of how-to files. */
	ArrayList<String> categories, order;
	
	/** HasMap for the categories and items. */
	HashMap<String, List<String>> items;

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// get the listview
		expListView = (ExpandableListView) findViewById(R.id.exp_list);

		// preparing list data
		listFiles("");
		prepareListData();

		listAdapter = new ExpandableListAdapter(this, categories, items);

		// setting list adapter
		expListView.setAdapter(listAdapter);

		// Listview Group click listener
		expListView.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {

				return false;
			}
		});

		// Listview Group expanded listener
		expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

			@Override
			public void onGroupExpand(int groupPosition) {

				for (String file : fileList) {
					if (file.equals(categories.get(groupPosition))) {
						openHowTo(categories.get(groupPosition), groupPosition,
								-1);
					}
				}
			}
		});

		// Listview Group collasped listener
		expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

			@Override
			public void onGroupCollapse(int groupPosition) {

			}
		});

		// Listview on child click listener
		expListView.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				openHowTo(
						items.get(categories.get(groupPosition)).get(
								childPosition), groupPosition, childPosition);

				return false;
			}
		});
	}

	/**
	 * Use an intent to open a new activity in order to open the how to file.
	 *
	 * @param file the file
	 * @param groupPosition the group position
	 * @param childPosition the child position
	 */
	protected void openHowTo(String file, int groupPosition, int childPosition) {
		Intent intent = new Intent(this, HowTo.class);
		intent.putExtra("FILE", file);
		intent.putExtra("ORDER", order);
		startActivity(intent);
	}

	/**
	 * Prepare list data.
	 */
	private void prepareListData() {
		categories = new ArrayList<String>();
		items = new HashMap<String, List<String>>();

		categories.add("Displaying Dialog");
		categories.add("Screen Orientation");
		categories.add("Reading Asset File");
		categories.add("Intents");

		List<String> dialogs = new ArrayList<String>();
		dialogs.add("Displaying Alert Dialog");
		dialogs.add("Displaying Progress Dialog");

		List<String> screen = new ArrayList<String>();
		screen.add("Detecting orientation changes");
		screen.add("Determining current orientation");
		screen.add("Controlling orientation");

		List<String> asset = new ArrayList<String>();
		
		List<String> intent = new ArrayList<String>();
		intent.add("Intents and filters");
		intent.add("Intents types");
		intent.add("Data transfer between activities");

		// Group, item
		items.put(categories.get(0), dialogs);
		items.put(categories.get(1), screen);
		items.put(categories.get(2), asset);
		items.put(categories.get(3), intent);

		order = new ArrayList<String>();
		order.add("Displaying Alert Dialog");
		order.add("Displaying Progress Dialog");
		order.add("Detecting orientation changes");
		order.add("Determining current orientation");
		order.add("Controlling orientation");
		order.add("Reading Asset File");
		order.add("Intents and filters");
		order.add("Intents types");
		order.add("Data transfer between activities");
	}

	/**
	 * List files from assets folder.
	 *
	 * @param dirFrom the dir from
	 */
	private void listFiles(String dirFrom) {
		Resources res = getResources(); // if you are in an activity
		AssetManager am = res.getAssets();

		try {
			String fileList[] = am.list(dirFrom);

			if (fileList != null) {
				for (int i = 0; i < fileList.length; i++) {
					String[] parts = fileList[i].split("\\.(?=[^\\.]+$)");
					if (parts.length > 1) {
						if (parts[1].equals("html")) {
							this.fileList.add(parts[0]);
						}
					}
				}
			}
		} catch (IOException e) {

		}
	}
}