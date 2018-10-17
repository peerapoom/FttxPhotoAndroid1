package com.codewithdio.fttxphotoandroid1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Main3Activity extends AppCompatActivity {
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        sp = getSharedPreferences("PREFNAME", Context.MODE_PRIVATE);
        editor = sp.edit();
    }
    public void BackToMain3 (View view) {

        // get the text from the EditText
        EditText splitterNameInput = (EditText) findViewById(R.id.splitterNameInput3);
        String stringToPassBack = splitterNameInput.getText().toString();
        editor.putString("SplitterNameSaved",stringToPassBack);
        editor.commit();
        // put the String to pass back into an Intent and close this activity
        //  Intent intent = new Intent();



        Button btn_back = (Button) findViewById(R.id.backButton3);


        Intent intent = new Intent(Main3Activity.this, MainActivity.class);
        intent.putExtra("KeyName3", stringToPassBack);
        intent.putExtra("Activity3",true);
        intent.putExtra("activityNameCase",2);
        startActivity(intent);
        finish();
    }
}
