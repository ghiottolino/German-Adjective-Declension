package com.nicolatesser.germanadjectivedeclension;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Vector;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CheatsheetActivity extends Activity {



	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cheatsheet);
		
		
		
	}
	
	

	
	// MENU
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.settings_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		Intent myIntent;
		switch (item.getItemId()) {
		 case R.id.game:
			 // do something
		 myIntent = new Intent(this, QuizActivity.class);
         startActivityForResult(myIntent, 0);
		 return true;
		 case R.id.instruction:
			 // do something
		 myIntent = new Intent(this, InstructionActivity.class);
         startActivityForResult(myIntent, 0);
		 return true;
		 case R.id.cheatsheet:
			 // do something
		 myIntent = new Intent(this, CheatsheetActivity.class);
         startActivityForResult(myIntent, 0);
		 return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
}