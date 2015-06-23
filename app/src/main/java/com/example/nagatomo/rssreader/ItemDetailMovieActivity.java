package com.example.nagatomo.rssreader;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Html;
import android.text.util.Linkify;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;

import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ItemDetailMovieActivity extends Activity {
    private TextView mTitle;
    private TextView mDescr;
    private TextView mURL;
    private String titlecut;
    private CharSequence cs1;
    private String SRC;
    private String SRCURL;
    private String tagurl[] = new String[20];
    private int counturl = 0;
    private String tagtitle[] = new String[20];
    private int counttitle = 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemdetail);

        System.out.println("標準出力です。");
        //受け取り側のActivityは設定されてるkey(ここではTITLE)で値を取り出す
        Intent intent = getIntent();
        String title = intent.getStringExtra("TITLE");
        String descr = intent.getStringExtra("DESCRIPTION");
        titlecut = title.substring(0, 5);
        cs1 = Html.fromHtml(titlecut);
        CharSequence cs2 = Html.fromHtml(descr);
        mTitle = (TextView) findViewById(R.id.item_detail_title);//findViewByIdでリソースIDに対応するビューのオブジェクトを取得する
        // mTitle.setText(title);  //変数titleを、mTitleオブジェクトのsetTextメソッドに渡して何かしらの結果を返す
        mDescr = (TextView) findViewById(R.id.item_detail_descr);
        mTitle.setText(cs1);
        mTitle.setTextColor(Color.parseColor("magenta"));

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());

            try{
                String s01 = "https://news.google.com/news?hl=ja&ned=us&ie=UTF-8&oe=UTF-8&output=rss&q=";
                String s02 = cs1.toString();
                String s03 = URLEncoder.encode(s02, "UTF-8");
                String s = s01 + s03;

                XmlPullParser xmlPullParser = Xml.newPullParser();
                URL Url = new URL(String.valueOf(s));
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
                            tag2 = xmlPullParser.nextText();
                            Log.d("XmlPullParserSampleUrl", tag2);

                            System.out.println(tag2);
                            tag2 = tag2.replaceAll("tag:news.google.com,2005:cluster=", "");
                            tagurl[counturl] = tag2;
                            counturl = counturl +1;
                        }else if("title".equals(tag)){
                            tag3 = xmlPullParser.nextText();
                            Log.d("XmlPullParserSampleUrl", tag3);

                            System.out.println(tag3);
                            tagtitle[counttitle] = tag3;
                            counttitle = counttitle +1;
                        }
                    }
                }
            } catch (Exception e){
                Log.d("XmlPullParserSampleUrl", "Error");
            }
            Link();
            for(int l=0;l<4;l++){
                System.out.println(tagtitle[l+2]);
                System.out.println(tagurl[l]);
            }



//以下、webViewに飛ぶようにするコード
        HandleableLinkMovementMethod linkMethod = new HandleableLinkMovementMethod();
        linkMethod.setOnUrlClickListener(new HandleableLinkMovementMethod.OnUrlClickListener() {
            @Override
            public void onUrlClick(Uri uri) {
                // ここでuriを使ってWebView表示用のIntentを飛ばしたりする
                WebView webView = (WebView)findViewById(R.id.webview);
                webView.setWebViewClient(new WebViewClient());
                webView.loadUrl(String.valueOf(uri));
                mTitle.setVisibility(View.GONE);
                mDescr.setVisibility(View.GONE);
                ImageView Hassan2 = (ImageView) findViewById(R.id.hassan2);
                Hassan2.setVisibility(View.GONE);
                TextView mURL2 = (TextView) findViewById(R.id.item_detail_url2);
                TextView mURL3 = (TextView) findViewById(R.id.item_detail_url3);
                TextView mURL4 = (TextView) findViewById(R.id.item_detail_url4);
                TextView mURL5 = (TextView) findViewById(R.id.item_detail_url5);
                mURL2.setVisibility(View.GONE);
                mURL3.setVisibility(View.GONE);
                mURL4.setVisibility(View.GONE);
                mURL5.setVisibility(View.GONE);
            }
        });
        mDescr.setMovementMethod(linkMethod);
        mDescr.setText(cs2);
    }


    //★★★特定の文字列にリンクを付ける、開始
    public void Link(){
        int i ;
        for(i=0 ; i<4 ;i++) {
            int num = i + 2;
            String detailtag = "item_detail_url" + num;
            mURL = (TextView) findViewById(getResources().getIdentifier(detailtag, "id", getPackageName()));
            SRC = tagtitle[i+2];
            SRCURL =tagurl[i];
            mURL.setText(SRC);
            Pattern pattern = Pattern.compile(SRC);
            final String strUrl = SRCURL;
            Linkify.TransformFilter filter = new Linkify.TransformFilter() {
                @Override
                public String transformUrl(Matcher match, String url) {
                    return strUrl;
                }
            };
            Linkify.addLinks(mURL,pattern, strUrl, null, filter);//LinkifyクラスのaddLinksメソッドを用いて、TextViewにリンクを作成
            // (リンクにしたいTextView,リンクにしたい文字列,遷移させたいwebサイトのURL,Linkify.MatchFilterクラスのインスタンス,Linkify.TransformFilterクラスのインスタンス)
        }
    }
}
