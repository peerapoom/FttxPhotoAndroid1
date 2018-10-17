package com.codewithdio.fttxphotoandroid1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main6Activity extends AppCompatActivity {
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
        sp = getSharedPreferences("PREFNAME", Context.MODE_PRIVATE);
        editor = sp.edit();
    }
    public void BackToMainL1 (View view) {


        // put the String to pass back into an Intent and close this activity
        //  Intent intent = new Intent();



        Button l1_btn = (Button) findViewById(R.id.L1_btn);


        Intent intent = new Intent(Main6Activity.this, MainActivity.class);
        intent.putExtra("KeyNameL1", "L1");
        editor.putString("SplitterTypeSaved","L1");
        editor.commit();
        intent.putExtra("activityNameCase",1);
        startActivity(intent);
        finish();
    }

    public void BackToMainL2 (View view) {


        // put the String to pass back into an Intent and close this activity
        //  Intent intent = new Intent();



        Button l2_btn = (Button) findViewById(R.id.L2_btn);


        Intent intent = new Intent(Main6Activity.this, MainActivity.class);
        intent.putExtra("KeyNameL2", "L2");
        editor.putString("SplitterTypeSaved","L2");
        editor.commit();
        intent.putExtra("activityNameCase",1);
        startActivity(intent);
        finish();
    }
}
