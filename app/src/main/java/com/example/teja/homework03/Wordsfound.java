/*Assignment 3
Wordsfound.Java
Yash Ghia & Prabhakar Teja Seeda*/


package com.example.teja.homework03;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class Wordsfound extends AppCompatActivity {
    int j = 0;
    int k = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wordsfound);
        setTitle("Words found");
        ScrollView sv = (ScrollView) findViewById(R.id.scrollViewLayout);
        final LinearLayout ll = (LinearLayout) findViewById(R.id.additionLayout);
        ArrayList<String> result = (ArrayList<String>) getIntent().getExtras().getSerializable(MainActivity.RESULT_KEY);
        ArrayList<String> mylist = (ArrayList<String>) getIntent().getExtras().getSerializable(MainActivity.SEARCH_WORD_KEY);
        String coloredString;
        HashMap<String,String> colorCode= new HashMap<>();
        String[] colors = {"#FF0000","#0000FF","#00FF00"};
        int colorCounter = 0;
        for (String word:mylist)
        {
            if(colorCounter<3){
                colorCode.put(word.toLowerCase(),colors[colorCounter]);
                colorCounter++;
            }
            else{
                colorCounter=0;
                colorCode.put(word.toLowerCase(),colors[colorCounter]);
                colorCounter++;
            }
        }
        //Get the data here and populate accordingly
        for (String s:result) {
            String word="";
            String color="";
            String res[];
            res = s.split(" ");

            for (String w:res) {
                color = colorCode.get(w.toLowerCase());
                if(color!=null&&color!=""){
                    word = w;
                    break;
                }
            }
            coloredString = s.replace(word,"<font color='"+color+"'>"+word+"</font>");
            k++;
            LinearLayout hl = new LinearLayout(this);
            hl.setOrientation(LinearLayout.HORIZONTAL);
            hl.setTag(k);
            // Create EditText
            EditText product = new EditText(this);
            product.setFocusable(false);
            product.setTextSize(24);
            product.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200));
            product.setText(Html.fromHtml(coloredString));
            hl.addView(product);
            ll.addView(hl);
        }
    }
    public void finishFunction(View view){
        Intent i = new Intent(Wordsfound.this,MainActivity.class);
        startActivity(i);
    }
}
