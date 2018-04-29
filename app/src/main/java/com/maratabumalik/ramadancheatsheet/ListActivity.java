package com.maratabumalik.ramadancheatsheet;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


public class ListActivity extends MenuActivity {
    JSONArray sections;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Resources res = getResources();

        Intent intent = getIntent();
        String sectionJsonString = intent.getStringExtra("sectionJsonString");
        if(sectionJsonString == null){
            sectionJsonString = getStringFromJsonFile("book.json");
        }

        showSectionList(sectionJsonString);
    }

    public void showSectionList(String sectionJsonString) {
        try {
            JSONObject section = new JSONObject(sectionJsonString);
            ListView headList = (ListView) findViewById(R.id.headList);
            sections = section.getJSONArray("sections");

            String[] listItemArray = new String[sections.length()];
            for (int i = 0; i < sections.length(); i++) {
                listItemArray[i] = (i + 1) + ". " + sections.getJSONObject(i).getString("name");
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    R.layout.layout_head, listItemArray);
            headList.setAdapter(adapter);

            headList.setOnItemClickListener(onClickSection);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    AdapterView.OnItemClickListener onClickSection = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View view,
                                int position, long id) {
            try {
                if (sections.getJSONObject(position).getInt("textNum") == 0){
                    Intent intent = new Intent(view.getContext(), ListActivity.class);
                    intent.putExtra("sectionJson", sections.getJSONObject(position).toString());
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(view.getContext(), TextActivity.class);
                    intent.putExtra("headIndex", sections.getJSONObject(position).getInt("textNum"));
                    startActivity(intent);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    public String getStringFromJsonFile(String book){
        StringBuilder buf = new StringBuilder();
        try {
            InputStream json=getAssets().open(book);
            BufferedReader in = new BufferedReader(new InputStreamReader(json, "UTF-8"));
            String str;
            while ((str = in.readLine()) != null) {
                buf.append(str);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buf.toString();
    }

}
