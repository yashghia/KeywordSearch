/*Assignment 3
MainActivity.Java
Yash Ghia & Prabhakar Teja Seeda*/

package com.example.teja.homework03;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    int j = 0;
    String[] arrays = new String[20];
    ArrayList<String> mylist = new ArrayList<String>();
    ArrayList<LinearLayout> getText = new ArrayList<LinearLayout>();
    ArrayList<String> fileArray = new ArrayList<String>();
    ArrayList<String> result = new ArrayList<String>();
    int setIndex = 1000,setIndex1 = 1100, restrictCounter = 20;
    ProgressBar progressBar;
    String textFile;
    int searchWordCount,counter = 0;
    String s[];
    public static final String RESULT_KEY = "searchResult";
    public static final String SEARCH_WORD_KEY = "searchWord";
    CheckBox checkBox;
    boolean isCaseSensitive = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            setTitle("Search Words");
            InputStream is = getAssets().open("textfile.txt");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            textFile = new String(buffer);
            s = textFile.split("\\r\\n");
            for (String line : s) {
                fileArray.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ScrollView sv = (ScrollView) findViewById(R.id.scrollView2);
        final LinearLayout ll = (LinearLayout) findViewById(R.id.newLayoutTester);
        progressBar = (ProgressBar) findViewById(R.id.determinateBar);
        checkBox = (CheckBox) findViewById(R.id.checkbox);

        LinearLayout hl = new LinearLayout(this);
        hl.setOrientation(LinearLayout.HORIZONTAL);
        hl.setTag(j);
        // Create EditText
        EditText product = new EditText(this);
        product.setWidth(850);
        product.setHint(" Product" + j + "    ");
        hl.addView(product);
        // Create Button
        final Button btn = new Button(this);
        // Give button an ID
        btn.setWidth(10);
        btn.setHeight(10);
        btn.setLayoutParams(new LinearLayout.LayoutParams(100, 100));
        btn.setId(j + 1);
        btn.setTag(setIndex);
        btn.setBackgroundResource(R.drawable.add);
        hl.addView(btn);
        ll.addView(hl);
        final int index = j;
        // Set click listener for button
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (btn.getTag().equals(setIndex)) {
                    createUI(index + 1);
                    btn.setBackgroundResource(R.drawable.remove);
                    btn.setTag(setIndex + 1);
                } else {
                    if (ll.getChildCount() == 1) {
                        btn.setBackgroundResource(R.drawable.add);
                        createUI(j + 1);
                    } else {
                        ll.removeViewAt(0);
                    }
                }
                Log.i("TAG", "index :" + index);
            }
        });
    }

    public void createUI(final int i){
        if (i<restrictCounter) {
            final LinearLayout ll = (LinearLayout) findViewById(R.id.newLayoutTester);
            final LinearLayout hl = new LinearLayout(this);
            hl.setOrientation(LinearLayout.HORIZONTAL);
            hl.setId(i);
            hl.setTag(i);
            // Create TextView
            EditText product = new EditText(this);
            product.setWidth(850);
            product.setHint(" Product" + i + "    ");
            hl.addView(product);
            // Create Button
            final Button btn = new Button(this);
            btn.setLayoutParams(new LinearLayout.LayoutParams(100, 100));
            btn.setBackgroundResource(R.drawable.add);
            btn.setId(i + 1);
            btn.setTag(setIndex1 + i);
            hl.addView(btn);
            ll.addView(hl);
            final int j = i;
            btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (btn.getTag().equals(setIndex1 + i)) {
                        //if (btn.getBackground().equals(R.drawable.add)) {
                        btn.setBackgroundResource(R.drawable.remove);
                        createUI(j + 1);
                        btn.setTag(setIndex1 + i + 1);
                    } else {
                        if (ll.getChildCount()==1){
                            btn.setBackgroundResource(R.drawable.add);
                            createUI(j+1);
                        }else {
                            ll.removeView(hl.findViewWithTag(i));
                        }
                    }
                    Log.i("TAG", "index :" + j);
                }
            });
        }
    }
    public void searchFunction(View view){
        mylist = getChildArray();
        searchWordCount = mylist.size();
        isCaseSensitive = checkBox.isChecked();
        counter=0;
        progressBar.setVisibility(ProgressBar.VISIBLE);
        for (String str:mylist) {
            String s = str;
            if(!isCaseSensitive){
                s = str.toLowerCase();
            }
            new DoAsyncTask().execute(s);
        }
    }
    public ArrayList<String> getChildArray() {
        LinearLayout layout = (LinearLayout) findViewById(R.id.newLayoutTester);
        int count = layout.getChildCount();
        LinearLayout v = null;
        for(int i=0; i<count; i++) {
            v = (LinearLayout) layout.getChildAt(i);
            getText.add(v);
            //do something with your child element
        }
        Log.i("THE ARRAY", "getText" + getText);
//        final LinearLayout ll = (LinearLayout) findViewById(R.id.newLayoutTester);
        for (int i = 0; i < getText.size(); i++) {
            LinearLayout ll = getText.get(i);
            j=0;
            if (ll.getChildAt(j) instanceof EditText) {
                EditText newText = (EditText) ll.getChildAt(j);
                mylist.add(newText.getText().toString().trim());
            }
        }
        return mylist;
    }
    public class DoAsyncTask extends AsyncTask<String, Integer, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(String... params) {
            counter++;
            ArrayList<String> response = new ArrayList<String>();
            response = TextSearchUtil.SearchKeyWordInFile(fileArray,params[0],isCaseSensitive);
            publishProgress(counter*100/searchWordCount);
            Log.d("searchResult","response of "+counter+" "+response.toString());
            return response;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            super.onPostExecute(strings);
            result.addAll(strings);

            if(searchWordCount==counter) {
                Intent i = new Intent(MainActivity.this,Wordsfound.class);
                i.putExtra(RESULT_KEY,result);
                i.putExtra(SEARCH_WORD_KEY,mylist);
                startActivity(i);
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setProgress(values[0]);
        }
    }
}

