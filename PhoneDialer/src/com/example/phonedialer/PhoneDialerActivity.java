package com.example.phonedialer;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class PhoneDialerActivity extends Activity {
	
	private EditText number = null;
	
	private NumberListener buttonListener= new NumberListener();
	
	private class NumberListener implements View.OnClickListener {
		
		@Override
        public void onClick(View view) {
			String nr = number.getText().toString();
			switch (view.getId())
			{
			case R.id.delete:
				if (number.length() > 0){
					nr = nr.substring(0, number.length()-1);
					number.setText(nr);
				}
				break;
			case R.id.call_button:
				Intent intent = new Intent(Intent.ACTION_CALL);
				intent.setData(Uri.parse("tel:"+nr));
				startActivity(intent);
				if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
	                startActivity(intent);
	            } else {
	                ActivityCompat.requestPermissions((Activity) getApplicationContext(), new String[]{Manifest.permission.CALL_PHONE}, 1);
	            }
				break;
			case R.id.hangup_button:
				finish();
				break;
			default:
				number.setText(nr + ((Button)view).getText().toString());
			}
		}
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_phone_dialer);
		
//		System.out.println("BUM "+findViewById(Constants.buttonIds[0]));
		number = (EditText)findViewById(R.id.editText1);
		
		Button b;
		for (int i=0;i< Constants.buttonIds.length; i++){
			b = (Button)findViewById(Constants.buttonIds[i]);
            b.setOnClickListener(buttonListener);
		}
		ImageButton ib;
		ib = (ImageButton)findViewById(R.id.call_button);
		ib.setOnClickListener(buttonListener);
		ib = (ImageButton)findViewById(R.id.hangup_button);
		ib.setOnClickListener(buttonListener);
		ib = (ImageButton)findViewById(R.id.delete);
		ib.setOnClickListener(buttonListener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.phone_dialer, menu);
		return true;
	}

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
