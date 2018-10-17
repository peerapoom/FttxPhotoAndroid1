package com.codewithdio.fttxphotoandroid1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Main4Activity extends AppCompatActivity {

    SharedPreferences sp;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        sp = getSharedPreferences("PREFNAME", Context.MODE_PRIVATE);
        editor = sp.edit();
    }
    public void BackToMain4 (View view) {

        // get the text from the EditText
        EditText drawingGridInput = (EditText) findViewById(R.id.drawingNameInput4);
        String stringToPassBack = drawingGridInput.getText().toString();
        editor.putString("DrawingGridSaved",stringToPassBack);
        editor.commit();
        // put the String to pass back into an Intent and close this activity
        //  Intent intent = new Intent();



        Button btn_back = (Button) findViewById(R.id.backButton4);


        Intent intent = new Intent(Main4Activity.this, MainActivity.class);
        intent.putExtra("KeyName4", stringToPassBack);
        intent.putExtra("Activity4",true);
        intent.putExtra("activityNameCase",3);
        startActivity(intent);
        finish();
    }
}
