package com.example.speaking_calculator;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

public class AboutUs extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_us);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.about_us, menu);
		return true;
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
		Intent obj = new Intent(AboutUs.this, AboutUs.class);
		startActivity(obj);
	}

	public void instruction() {
		Intent obj = new Intent(AboutUs.this, Instruction.class);
		startActivity(obj);
	}

	public void exit() {
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}
}
