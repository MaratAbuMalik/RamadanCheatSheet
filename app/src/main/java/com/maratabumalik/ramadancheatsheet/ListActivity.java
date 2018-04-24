package com.maratabumalik.ramadancheatsheet;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListActivity extends MenuActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Resources res = getResources();
        String[] headArray = res.getStringArray(R.array.uniqueQuestionArray);
        String[] listItemArray = new String[headArray.length];
        for(int i = 0; i < headArray.length; i++){
            listItemArray[i] = (i + 1) + ". " + headArray[i];
        }

        ListView headList = (ListView) findViewById(R.id.headList);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.layout_head, listItemArray);
        headList.setAdapter(adapter);

        headList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(view.getContext(), TextActivity.class);
                intent.putExtra("headIndex", position);
                startActivity(intent);
            }
        });
    }
}
