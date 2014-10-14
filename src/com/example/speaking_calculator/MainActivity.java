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

public class MainActivity extends Activity implements
		TextToSpeech.OnInitListener {

	protected static final int RESULT_SPEECH = 1;
	private ImageButton btnSpeak;
	private TextView txtText;
	private EditText ans;
	private TextToSpeech ttobj;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		txtText = (TextView) findViewById(R.id.textView1);
		ans = (EditText) findViewById(R.id.editText1);
		ttobj = new TextToSpeech(this, this);
		btnSpeak = (ImageButton) findViewById(R.id.imageButton1);
		btnSpeak.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(
						RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

				intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");

				try {
					startActivityForResult(intent, RESULT_SPEECH);
					txtText.setText("");
				} catch (ActivityNotFoundException a) {
					Toast t = Toast.makeText(getApplicationContext(),
							"Opps! Your device doesn't support Speech to Text",
							Toast.LENGTH_SHORT);
					t.show();
				}

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		switch (requestCode) {
		case RESULT_SPEECH: {
			if (resultCode == RESULT_OK && null != data) {

				ArrayList<String> text = data
						.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

				txtText.setText(text.get(0));

				Log.e(text.get(0), "msg");

				String str = txtText
						.getText()
						.toString()
						.replaceAll("multiplied by", "*")
						.replaceAll("by", "/")
						.replaceAll("sine", "sin")
						.replaceAll("%", "/100")
						.replaceAll("mod", "%")
						.replaceAll("square", "^2")
						.replaceAll("cube", "^3")
						.replaceAll("raise to", "^")
						.replaceAll("root of", "sqrt")
						.replace("celsius to fahrenheit",
								"Celsius to fahrenheit")
						.replace("celsius to kelvin", "Celsius to kelvin")
						.replace("fahrenheit to celsius",
								"fahrenheit to Celsius")
						.replace("kelvin to celsius", "kelvin to Celsius")
						.replace("centimetre to metre", "Centimeter to meter")
						
						.replace("metre to kilometre", "meter to kilometer")
						.replace("metre to centimetre", "meter to Centimeter")
						.replace("kilometre to centimetre", "kilometer to Centimeter")
						.replace("kilometre to metre", "kilometer to meter");

				// String str2=txtText.getText().toString().replaceAll("divide",
				// "/");

				if (str.contains("Celsius to fahrenheit")) {
					Intent intobj = new Intent(MainActivity.this,
							Temperature.class);
					startActivity(intobj);
				}
				if (str.contains("Celsius to kelvin")) {
					Intent intobj = new Intent(MainActivity.this,
							CelsiusToKelvin.class);
					startActivity(intobj);
				}
				if (str.contains("fahrenheit to kelvin")) {
					Intent intobj = new Intent(MainActivity.this,
							FahrenheitToKelvin.class);
					startActivity(intobj);
				}
				if (str.contains("fahrenheit to Celsius")) {
					Intent intobj = new Intent(MainActivity.this,
							FahToCelsius.class);
					startActivity(intobj);
				}

				if (str.contains("kelvin to Celsius")) {
					Intent intobj = new Intent(MainActivity.this,
							KelToCelsius.class);
					startActivity(intobj);
				}

				if (str.contains("kelvin to fahrenheit")) {
					Intent intobj = new Intent(MainActivity.this,
							KelToFah.class);
					startActivity(intobj);
				}

				if (str.contains("Centimeter to meter")) {
					Intent intobj = new Intent(MainActivity.this,
							CentiToMetre.class);
					startActivity(intobj);
				}
				if (str.contains("centimeter to kilometer")) {
					Intent intobj = new Intent(MainActivity.this,
							CentiToKilo.class);
					startActivity(intobj);
				}
				if (str.contains("meter to kilometer")) {
					Intent intobj = new Intent(MainActivity.this,
							MeterToKilo.class);
					startActivity(intobj);
				}
				if (str.contains("meter to Centimeter")) {
					Intent intobj = new Intent(MainActivity.this,
							MeterToCenti.class);
					startActivity(intobj);
				}
				if (str.contains("kilometer to Centimeter")) {
					Intent intobj = new Intent(MainActivity.this,
							KiloToCenti.class);
					startActivity(intobj);
				}
				if (str.contains("kilometer to meter")) {
					Intent intobj = new Intent(MainActivity.this,
							KiloToMeter.class);
					startActivity(intobj);
				}
				
				if (str.contains("log") || str.contains("+")
						|| str.contains("-") || str.contains("*")
						|| str.contains("/") || str.contains("%")
						|| str.contains("^") || str.contains("cos")) {

					int st2 = txtText.getText().toString().indexOf("g");
					// System.out.print(st2);

					String st3 = "";
					for (int i = 0; i <= st2 + 1; i++) {
						if (i <= st2) {
							Character ch;
							ch = str.charAt(i);
							String k = Character.toString(ch);
							st3 = st3.concat(k);
						}
						if (i > st2) {
							// ch=str.charAt(i);
							// String k=Character.toString(ch);
							st3 = st3.concat("(");
						}
					}
					Log.e("value", st3);
					int len = str.length();
					for (int j = st2 + 1; j <= len; j++) {
						if (j < len) {
							Character ch;
							ch = str.charAt(j);
							String l = Character.toString(ch);
							st3 = st3.concat(l);
						}
						if (j == len) {
							st3 = st3.concat(")");
						}
					}

					Calculable calc = null;
					try {
						calc = new ExpressionBuilder(st3).build();
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
					ans.setText(Double.toString(result));
					convertTextToSpeech();
				}
				if (str.contains("clear")) {
					ans.setText("");
				}

				if (str.contains("sin") || str.contains("tan")) {

					int st2 = txtText.getText().toString().indexOf("n");
					String st3 = "";
					for (int i = 0; i <= st2 + 1; i++) {
						if (i <= st2) {
							Character ch;
							ch = str.charAt(i);
							String k = Character.toString(ch);
							st3 = st3.concat(k);
						}
						if (i > st2) {
							// ch=str.charAt(i);
							// String k=Character.toString(ch);
							st3 = st3.concat("(");
						}
					}
					Log.e("value", st3);
					int len = str.length();
					for (int j = st2 + 1; j <= len; j++) {
						if (j < len) {
							Character ch;
							ch = str.charAt(j);
							String l = Character.toString(ch);
							st3 = st3.concat(l);
						}
						if (j == len) {
							st3 = st3.concat(")");
						}
					}
					Log.e("value", st3);
					Calculable calc2 = null;
					try {
						calc2 = new ExpressionBuilder(st3).build();
					} catch (UnknownFunctionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (UnparsableExpressionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					double result = calc2.calculate();
					// Toast.makeText(getApplicationContext(),Double.toString(result),
					// Toast.LENGTH_SHORT).show();
					ans.setText(Double.toString(result));
				}
				if (str.contains("cos")) {
					int st2 = txtText.getText().toString().indexOf("s");
					String st3 = "";
					for (int i = 0; i <= st2 + 1; i++) {
						if (i <= st2) {
							Character ch;
							ch = str.charAt(i);
							String k = Character.toString(ch);
							st3 = st3.concat(k);
						}
						if (i > st2) {
							// ch=str.charAt(i);
							// String k=Character.toString(ch);
							st3 = st3.concat("(");
						}
					}
					Log.e("value", st3);
					int len = str.length();
					for (int j = st2 + 1; j <= len; j++) {
						if (j < len) {
							Character ch;
							ch = str.charAt(j);
							String l = Character.toString(ch);
							st3 = st3.concat(l);
						}
						if (j == len) {
							st3 = st3.concat(")");
						}
					}
					Log.e("value", st3);
					Calculable calc2 = null;
					try {
						calc2 = new ExpressionBuilder(st3).build();
					} catch (UnknownFunctionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (UnparsableExpressionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					double result = calc2.calculate();
					// Toast.makeText(getApplicationContext(),Double.toString(result),
					// Toast.LENGTH_SHORT).show();
					ans.setText(Double.toString(result));
				}
				if (str.contains("sqrt")) {
					int st2 = txtText.getText().toString().indexOf("t");
					String st3 = "";
					for (int i = 0; i <= st2 + 1; i++) {
						if (i <= st2) {
							Character ch;
							ch = str.charAt(i);
							String k = Character.toString(ch);
							st3 = st3.concat(k);
						}
						if (i > st2) {
							// ch=str.charAt(i);
							// String k=Character.toString(ch);
							st3 = st3.concat("(");
						}
					}
					Log.e("value", st3);
					int len = str.length();
					for (int j = st2 + 1; j <= len; j++) {
						if (j < len) {
							Character ch;
							ch = str.charAt(j);
							String l = Character.toString(ch);
							st3 = st3.concat(l);
						}
						if (j == len) {
							st3 = st3.concat(")");
						}
					}
					Log.e("value", st3);
					Calculable calc2 = null;
					try {
						calc2 = new ExpressionBuilder(st3).build();
					} catch (UnknownFunctionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (UnparsableExpressionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					double result = calc2.calculate();
					// Toast.makeText(getApplicationContext(),Double.toString(result),
					// Toast.LENGTH_SHORT).show();
					ans.setText(Double.toString(result));
				}

			}

		}
		}
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
		Intent obj = new Intent(MainActivity.this, AboutUs.class);
		startActivity(obj);
	}

	public void instruction() {
		Intent obj = new Intent(MainActivity.this, Instruction.class);
		startActivity(obj);
	}

	public void exit() {
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
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
		String text = ans.getText().toString();
		if (null == text || "".equals(text)) {
			text = "Please give some input.";
		}
		ttobj.speak(text, TextToSpeech.QUEUE_FLUSH, null);

	}

}
