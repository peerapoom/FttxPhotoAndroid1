package com.codewithdio.fttxphotoandroid1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Main5Activity extends AppCompatActivity {
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        sp = getSharedPreferences("PREFNAME", Context.MODE_PRIVATE);
        editor = sp.edit();

    }
    public void BackToMain5 (View view) {

        // get the text from the EditText
        EditText remarkInput = (EditText) findViewById(R.id.remarkNameInput5);
        String stringToPassBack = remarkInput.getText().toString();
        editor.putString("RemarkSaved",stringToPassBack);
        editor.commit();
        // put the String to pass back into an Intent and close this activity
        //  Intent intent = new Intent();



        Button btn_back = (Button) findViewById(R.id.backButton5);


        Intent intent = new Intent(Main5Activity.this, MainActivity.class);
        intent.putExtra("KeyName5", stringToPassBack);
        intent.putExtra("Activity5",true);
        intent.putExtra("activityNameCase",4);
        startActivity(intent);
        finish();
    }
}
