package com.codewithdio.fttxphotoandroid1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Main2Activity extends AppCompatActivity {


        SharedPreferences sp;
        SharedPreferences.Editor editor;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main2);
            sp = getSharedPreferences("PREFNAME", Context.MODE_PRIVATE);
            editor = sp.edit();

        }
    public void BackToMain1 (View view) {

        // get the text from the EditText
        EditText siteNameInput = (EditText) findViewById(R.id.siteNameInput2);
        String stringToPassBack = siteNameInput.getText().toString();

        // put the String to pass back into an Intent and close this activity
        //  Intent intent = new Intent();



        Button btn_back = (Button) findViewById(R.id.backButton2);
        Bundle bundle = getIntent().getExtras();
        editor.putString("SiteNameSaved",stringToPassBack);
        editor.commit();
        Intent intent = new Intent(Main2Activity.this, MainActivity.class);
        intent.putExtra("KeyName", stringToPassBack);
        intent.putExtra("Activity2",true);
        intent.putExtra("activityNameCase",0);
        startActivity(intent);
        finish();
    }

}
