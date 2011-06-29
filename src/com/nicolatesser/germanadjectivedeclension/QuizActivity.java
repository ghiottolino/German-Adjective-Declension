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

public class QuizActivity extends Activity  implements OnClickListener {

/** Called when the activity is first created. */
	
	public static final String PREFS_NAME = "GERMAN_ADJECTIVE_DECLENSION";
	
	public static final String RECORD_PREF_KEY = "RECORD";
	
	public static final String CONSECUTIVE_PREF_KEY = "CONSECUTIVE";
	
	public static final String TOTAL_PREF_KEY = "TOTAL";
	
	public static final String CORRECT_PREF_KEY = "CORRECT";
		
	public static final String DICTIONARIES_PREF_KEY = "DICTIONARIES";
	
	private TextView sentenceTextView;

	private TextView outputTextView;

	private TextView totalResultTextView;

	private TextView consecutiveResultTextView;

	private TextView recordTextView;
	
	private Button declensionE;
	private Button declensionEn;
	private Button declensionEr;
	private Button declensionEm;
	private Button declensionEs;
	

	private Declension currentDeclension;

	private String currentSentence;

	private Map<String, Declension> sentences;

	private List<String> recentWrongAnsweredQuizes;

	private static final Integer RECENT_WRONG_ANSWERED_WORDS_SIZE = 10;

	private Integer consecutive = 0;

	private Integer correctAttempts = 0;

	private Integer totalAttempts = 0;

	private Random rg = new Random();
	
	private Integer currentRecord = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		
		sentenceTextView = (TextView) findViewById(R.id.word);
		totalResultTextView = (TextView) findViewById(R.id.totalResult);
		consecutiveResultTextView = (TextView) findViewById(R.id.consecutiveResult);
		recordTextView = (TextView) findViewById(R.id.record);
		outputTextView = (TextView) findViewById(R.id.output);
		outputTextView.setVisibility(0);
		outputTextView.setText("");
		
		consecutive =  getFieldInPreferences(CONSECUTIVE_PREF_KEY);
		correctAttempts =  getFieldInPreferences(CORRECT_PREF_KEY);
		totalAttempts =  getFieldInPreferences(TOTAL_PREF_KEY);
		currentRecord = getFieldInPreferences(RECORD_PREF_KEY);
		
		printCurrentRecord();

		updateTotalResult();
		updateConsecutiveResult();

		declensionE = (Button) findViewById(R.id.e);
		declensionEr = (Button) findViewById(R.id.er);
		declensionEn = (Button) findViewById(R.id.en);
		declensionEs = (Button) findViewById(R.id.es);
		declensionEm = (Button) findViewById(R.id.em);
		
		declensionE.setOnClickListener(this);
		declensionEr.setOnClickListener(this);
		declensionEn.setOnClickListener(this);
		declensionEs.setOnClickListener(this);
		declensionEm.setOnClickListener(this);
	}
	
	
	@Override
	public void onResume() {
		super.onResume();
		try {
			generateSentences();
		} catch (IOException e) {
			throw new RuntimeException("Could not load dictionary");
		}
		recentWrongAnsweredQuizes = new Vector<String>();
		initTest();

	}
	
	

	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.e: {
			handleResponse(Declension.E, view);
			break;
		}
		case R.id.en: {
			handleResponse(Declension.EN, view);
			break;
		}
		case R.id.em: {
			handleResponse(Declension.EM, view);
			break;
		}
		case R.id.er: {
			handleResponse(Declension.ER, view);
			break;
		}
		case R.id.es: {
			handleResponse(Declension.ES, view);
			break;
		}
		
		}
	}

	public boolean handleResponse(Declension declension, View view) {
		boolean correct = declension.equals(currentDeclension);

		totalAttempts++;

		if (correct) {
			outputTextView.setVisibility(0);
			outputTextView.setText("");
			// showTextToClipboardNotification("OK.");
			correctAttempts++;
			consecutive++;
			updateTotalResult();
			updateConsecutiveResult();
			updateRecord();
			initTest();
		} else {
			outputTextView.setVisibility(1);
			outputTextView.setText("Wrong, try again.");
			consecutive = 0;
			// showTextToClipboardNotification("Wrong.");
			view.setEnabled(false);
			if (!recentWrongAnsweredQuizes.contains(currentSentence))
			{
				recentWrongAnsweredQuizes.add(currentSentence);
			}
		}

		updateTotalResult();
		updateConsecutiveResult();

		return correct;
	}

	protected void showTextToClipboardNotification(String text) {
		Context context = getApplicationContext();
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(context, text, duration);
		toast.show();

	}



	
	public void initTest() {

		String word = "";

		// if there are more than 10 words in the recentWrongAnsweredWords list
		// then retrieves one of that words
		if (shouldChooseFromWrongAnswers()) {
			word = getWordFromWrongAnswers();
		} else {
			word = getWordFromDictionary();
		}

		this.currentSentence = word;
		this.currentDeclension = sentences.get(currentSentence);
		this.sentenceTextView.setText(currentSentence);

		// reset all buttons
		declensionE.setEnabled(true);
		declensionEn.setEnabled(true);
		declensionEm.setEnabled(true);
		declensionEr.setEnabled(true);
		declensionEs.setEnabled(true);
	}

	public String getWordFromWrongAnswers() {
		Collections.shuffle(recentWrongAnsweredQuizes);
		String word = recentWrongAnsweredQuizes.get(0);
		recentWrongAnsweredQuizes.remove(word);
		return word;
	}

	public String getWordFromDictionary() {
		
		if (sentences.size()==0)
		{
			return "";
		}
		
		Set<String> keySet = sentences.keySet();
		List<String> keyList = new Vector<String>();
		keyList.addAll(keySet);
		Collections.shuffle(keyList);
		return keyList.get(0);
	}

	public boolean shouldChooseFromWrongAnswers() {
		if  ((recentWrongAnsweredQuizes==null)||(recentWrongAnsweredQuizes.size()==0))
		{
			return false;
		}
		
		if (recentWrongAnsweredQuizes.size() > RECENT_WRONG_ANSWERED_WORDS_SIZE) {
			return true;
		}

		int random = rg.nextInt(RECENT_WRONG_ANSWERED_WORDS_SIZE);

		if (random < recentWrongAnsweredQuizes.size()) {
			return true;
		} else {
			return false;
		}
	}

	public void updateTotalResult() {
		totalResultTextView.setText("total: " + correctAttempts.toString()
				+ "/" + totalAttempts.toString());

		saveFieldInPreferences(CORRECT_PREF_KEY, correctAttempts);
		saveFieldInPreferences(TOTAL_PREF_KEY, totalAttempts);

	}

	public void updateConsecutiveResult() {
		consecutiveResultTextView.setText("consecutive: "
				+ consecutive.toString());
		
		saveFieldInPreferences(CONSECUTIVE_PREF_KEY, consecutive);
		
	}
	
	private void generateSentences()  throws IOException
	{
		
		sentences = new HashMap<String, Declension>();
		
		AdjectiveDeclensionSentenceGenerator generator = new AdjectiveDeclensionSentenceGenerator();

		for (int i=0;i<500;i++)
		{
			Object[] sentence = generator.next();
			sentences.put((String)sentence[1],(Declension) sentence[0]);
		}

		
	}

	
	// RECORD
	
	protected void updateRecord()
	{
		if (consecutive>currentRecord)
		{
			this.currentRecord=consecutive;
			saveFieldInPreferences(RECORD_PREF_KEY,currentRecord);
			showTextToClipboardNotification("Congratulations, you set a new record: "+currentRecord);
			printCurrentRecord();
		}
	}
	

	public void printCurrentRecord() {
		recordTextView.setText("record: "
				+ currentRecord.toString());
	}
	
	protected Integer getFieldInPreferences(String fieldName) {

    	SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
    	int record = settings.getInt(fieldName, 0);
    	return record;
	}
	
	protected void saveFieldInPreferences(String fieldName, Integer n) {

    	SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt(fieldName, n);

		// Commit the edits!
		boolean commit = editor.commit();
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