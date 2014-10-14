package com.example.speaking_calculator;

import java.util.ArrayList;
import java.util.Locale;

import de.congrace.exp4j.Calculable;
import de.congrace.exp4j.ExpressionBuilder;
import de.congrace.exp4j.UnknownFunctionException;
import de.congrace.exp4j.UnparsableExpressionException;
import android.os.Bundle;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class KelToFah extends Activity  implements
TextToSpeech.OnInitListener {
	protected static final int RESULT_SPEECH = 1;
	private TextView input;
	private EditText kelvin;
	private ImageButton button;
	private TextToSpeech ttobj;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kel_to_fah);
		ttobj=new TextToSpeech(this,this);
		input = (TextView) findViewById(R.id.textView1);
		kelvin = (EditText) findViewById(R.id.editText1);

		button = (ImageButton) findViewById(R.id.imageButton1);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(
						RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

				intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");

				try {
					startActivityForResult(intent, RESULT_SPEECH);
					input.setText("");
				} catch (ActivityNotFoundException a) {
					Toast t = Toast.makeText(getApplicationContext(),
							"Opps! Your device doesn't support Speech to Text",
							Toast.LENGTH_SHORT);
					t.show();
				}

			}
		});
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		switch (requestCode) {
		case RESULT_SPEECH: {
			if (resultCode == RESULT_OK && null != data) {

				ArrayList<String> text = data
						.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

				input.setText(text.get(0));
				String str= input.getText().toString();
			
				int f=Integer.parseInt(str);
				double fah=(Double) ((f-273.15)*9/5 + 32);
				String val=Double.toString(fah);
			//	Log.e(val, "msg");
				Calculable calc = null;
				
				try {
					calc = new ExpressionBuilder(val).build();
					
				} catch (UnknownFunctionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnparsableExpressionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				double result = calc.calculate();
				
				// Toast.makeText(getApplicationContext(),Double.toString(result),
				// Toast.LENGTH_SHORT).show();
				
				kelvin.setText(Double.toString(result));
				
				convertTextToSpeech();
			}
		}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.kel_to_fah, menu);
		return true;
	}

	@Override
	public void onInit(int status) {
		// TODO Auto-generated method stub
		if (status == TextToSpeech.SUCCESS) {
			int result = ttobj.setLanguage(Locale.US);
			if (result == TextToSpeech.LANG_MISSING_DATA
					|| result == TextToSpeech.LANG_NOT_SUPPORTED) {
				Log.e("error", "This Language is not supported");
			} else {
				convertTextToSpeech();
			}
		} else {
			Log.e("error", "Initilization Failed!");
		}

		
	}
	public void onDestroy() {
		ttobj.shutdown();
		super.onDestroy();
	}
	private void convertTextToSpeech() {
		// TODO Auto-generated method stub
		String text = kelvin.getText().toString();
		if (null == text || "".equals(text)) {
			text = "Please give some input.";
		}
		ttobj.speak(text, TextToSpeech.QUEUE_FLUSH, null);

	}
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.about_us:
			aboutUs();
			return true;
		case R.id.exit:
			exit();
			return true;
		case R.id.instruction:
			instruction();
			return true;
		default:

		}
		return super.onOptionsItemSelected(item);
	}

	public void aboutUs() {
		Intent obj = new Intent(KelToFah.this, AboutUs.class);
		startActivity(obj);
	}

	public void instruction() {
		Intent obj = new Intent(KelToFah.this, Instruction.class);
		startActivity(obj);
	}

	public void exit() {
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}
		

}
