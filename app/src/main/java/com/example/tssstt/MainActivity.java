package com.example.tssstt;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import at.markushi.ui.CircleButton;

public class MainActivity extends AppCompatActivity {

    TextView tque;
    CircleButton umic_stt1;
    CircleButton uhear1;
    TextToSpeech tts;
    private static final int REQUEST_CODE_SPEECH_INPUT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tque = findViewById(R.id.tque);
        umic_stt1 = findViewById(R.id.umic_stt1);
        uhear1 = findViewById(R.id.uhear1);

        umic_stt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spe();
            }
        });

        uhear1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int i) {
                        if (i == TextToSpeech.SUCCESS){
                            tts.setLanguage(Locale.ENGLISH);
                            tts.setSpeechRate(1.0f);
                            tts.speak(tque.getText().toString(), TextToSpeech.QUEUE_ADD, null);
                        }

                    }
                });
            }
        });


    }



    private void spe() {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak Something");

        try {
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);

        }catch (Exception e){
            Toast.makeText(this, "Error Occur", Toast.LENGTH_SHORT).show();
        }

    }

     @Override
     protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode)
        {
            case REQUEST_CODE_SPEECH_INPUT:{
                if (resultCode == RESULT_OK && null !=data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    tque.setText(result.get(0));

                }
                break;
            }
        }
     }
}