package com.example.nagatomo.rssreader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.util.Linkify;
import android.util.Log;
import android.util.Xml;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;

import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Body extends Activity {
    private String SRC;
    private String SRCURL;
    private String tagurl[] = new String[20];
    private int counturl = 0;
    private String tagtitle[] = new String[20];
    private int counttitle = 0;
    private TextView mURL;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body);
/*
        Intent intent = getIntent();
        String pagetitle = intent.getStringExtra("TITLE");
        String pageurl = intent.getStringExtra("pageurl");

        try{
            XmlPullParser xmlPullParser = Xml.newPullParser();
            URL Url = new URL(String.valueOf(pageurl));
            URLConnection connection = Url.openConnection();
            xmlPullParser.setInput(connection.getInputStream(), "UTF-8");

            int eventType;
            while ((eventType = xmlPullParser.next()) != XmlPullParser.END_DOCUMENT) {
                String tag = null;
                String tag2 = null;
                String tag3 = null;
                if (eventType == XmlPullParser.START_TAG) {
                    tag = xmlPullParser.getName();;
                    if("guid".equals(tag)){

                    }else if("title".equals(tag)){

                    }
                }
            }
        } catch (Exception e){
            Log.d("XmlPullParserSampleUrl", "Error");
        }
*/
    }




}