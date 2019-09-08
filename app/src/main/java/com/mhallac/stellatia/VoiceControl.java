package com.mhallac.stellatia;

import androidx.appcompat.app.AppCompatActivity;


// added to appease the coding gods
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

//import android.speech.RecognizerIntent;

import android.os.Bundle;

public class VoiceControl extends AppCompatActivity {

    private TextView txvResult;

    //public static void main(String[] args) { getSpeechInput(txvResult); }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_control);
        txvResult = (TextView) findViewById(R.id.txvResult);
    }

    public void getSpeechInput(View view) {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 10);
        } else {
            Toast.makeText(this, "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    txvResult.setText(result.get(0));

                    if (txvResult.getText().toString().contains("ATM")) {
                        Intent myIntent = new Intent(VoiceControl.this, Cap1Maps.class);
                        myIntent.putExtra("key", "Important Message"); //Optional parameters
                        VoiceControl.this.startActivity(myIntent);
                    }
                }
                break;
        }
    }

}
