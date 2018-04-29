package com.maratabumalik.ramadancheatsheet;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TextView;

public class TextActivity extends MenuActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);

        Resources res = getResources();
        Intent intent = getIntent();

        int headIndex = intent.getIntExtra("headIndex", 0);
        String[] textArray = res.getStringArray(R.array.textArray);

        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(AntiDeprecation.fromHtml(textArray[headIndex]));
    }
}