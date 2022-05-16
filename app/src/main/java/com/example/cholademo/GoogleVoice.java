package com.example.cholademo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import darren.googlecloudtts.GoogleCloudTTS;
import darren.googlecloudtts.GoogleCloudTTSFactory;
import darren.googlecloudtts.model.VoicesList;
import darren.googlecloudtts.parameter.AudioConfig;
import darren.googlecloudtts.parameter.AudioEncoding;
import darren.googlecloudtts.parameter.VoiceSelectionParams;

public class GoogleVoice extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_voice);




    }

    public void run(View view)
    {

        MyAsyncTasks myAsyncTasks = new MyAsyncTasks();
        myAsyncTasks.execute();





    }

    public class MyAsyncTasks extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {

            // Set the ApiKey and create GoogleCloudTTS.
            GoogleCloudTTS googleCloudTTS = GoogleCloudTTSFactory.create("AIzaSyAiAAoPGviRztvfPyfZA4uViH8okN7MKUE");

            // Load google cloud VoicesList and select the languageCode and voiceName with index (0 ~ N).
            VoicesList voicesList = googleCloudTTS.load();
            String languageCode = voicesList.getLanguageCodes()[0];
            String voiceName = voicesList.getVoiceNames(languageCode)[0];

// Set languageCode and voiceName, Rate and pitch parameter.
            googleCloudTTS.setVoiceSelectionParams(new VoiceSelectionParams(languageCode, voiceName))
                    .setAudioConfig(new AudioConfig(AudioEncoding.MP3, 0.35f , 10f));

// start speak
            googleCloudTTS.start("you want speak something");

            return "";
        }

        @Override
        protected void onPostExecute(String s) {

            Log.d("data", s.toString());
            // dismiss the progress dialog after receiving data from API


        }

    }



    }