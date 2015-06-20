package com.example.nagatomo.rssreader;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Html;
import android.text.util.Linkify;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Nagatomo on 2015/06/18.
 */
public class ItemDetailArtistActivity extends Activity {
    private TextView mTitle;
    private TextView mDescr;
    private TextView mURL;
    private TextView mURL2;
    private TextView mURL3;
    private TextView mURL4;
    private TextView mURL5;
    private String src0;
    private String src3;
    private String src6;
    private String src9;
    private String src12;
    private String srcurl3;
    private String srcurl6;
    private String srcurl9;
    private String srcurl12;
    private String titlecut;
    private CharSequence cs1;
    private String SRC;
    private String SRCURL;
   // private String detailtag = "item_detail_url2";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemdetail);

        //受け取り側のActivityは設定されてるkey(ここではTITLE)で値を取り出す
        Intent intent = getIntent();
        String title = intent.getStringExtra("TITLE");
        String descr = intent.getStringExtra("DESCRIPTION");
        titlecut = title.substring(0, 5);
        //   CharSequence cs1 = Html.fromHtml(title);
        cs1 = Html.fromHtml(titlecut);
        CharSequence cs2 = Html.fromHtml(descr);
        mTitle = (TextView) findViewById(R.id.item_detail_title);//findViewByIdでリソースIDに対応するビューのオブジェクトを取得する
        // mTitle.setText(title);  //変数titleを、mTitleオブジェクトのsetTextメソッドに渡して何かしらの結果を返す
        mDescr = (TextView) findViewById(R.id.item_detail_descr);
        //  mDescr.setText(descr);
        mTitle.setText(cs1);
        mTitle.setTextColor(Color.parseColor("magenta"));




//特定のURLに対して、そのhtmlリソースの欲しい一部をとりにいく
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());

        HttpURLConnection http = null;
        InputStream in = null;
        TextView web = (TextView) findViewById(R.id.textViewURL);
        try {
            // URLにHTTP接続
            String s01 = "https://news.google.com/news?hl=ja&ned=us&ie=UTF-8&oe=UTF-8&output=rss&q=";
            String s03 = cs1.toString();
          //  String s03 = (String) cs1;
            String s = s01 + s03;
            URL url = new URL(String.valueOf(s));
            http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");
            http.connect();
            // データを取得
            in = http.getInputStream();

          //  String src = new String();
            String src = "";
            byte[] line = new byte[1024];
            int size;
            while (true) {
                size = in.read(line);
                if (size <= 0)
                    break;
                src += new String(line);
            }


            // HTMLソースを表示
            int index1 = src.indexOf("<item><title>");
            String src2 =  src.substring(index1 + 13);
            int index2 = src2.indexOf("</title>");
            src3 =  src2.substring(0, index2);


            int indexurl1 = src.indexOf("&amp;url=");
            String srcurl2 =  src.substring(indexurl1 + 9);
            int indexurl2 = srcurl2.indexOf("</link>");
            srcurl3 =  srcurl2.substring(0, indexurl2);

            int index3 = src.indexOf(String.valueOf(src3));
            String src4 =  src.substring(index3 + 13);
            int index4 = src4.indexOf("<item><title>");
            String src5 =  src4.substring(index4 + 13);
            int index5 = src5.indexOf("</title>");
            src6 =  src5.substring(0, index5);


            int indexurl3 = src.indexOf(String.valueOf(srcurl3));
            String srcurl4 =  src.substring(indexurl3 + 9);
            int indexurl4 = srcurl4.indexOf("&amp;url=");
            String srcurl5 =  srcurl4.substring(indexurl4 + 9);
            int indexurl5 = srcurl5.indexOf("</link>");
            srcurl6 =  srcurl5.substring(0, indexurl5);

            int index6 = src.indexOf(String.valueOf(src6));
            String src7 =  src.substring(index6 + 13);
            int index7 = src7.indexOf("<item><title>");
            String src8 =  src7.substring(index7 + 13);
            int index8 = src8.indexOf("</title>");
            src9 =  src8.substring(0, index8);

            int indexurl6 = src.indexOf(String.valueOf(srcurl6));
            String srcurl7 =  src.substring(indexurl6 + 9);
            int indexurl7 = srcurl7.indexOf("&amp;url=");
            String srcurl8 =  srcurl7.substring(indexurl7 + 9);
            int indexurl8 = srcurl8.indexOf("</link>");
            srcurl9 =  srcurl8.substring(0, indexurl8);

            int index9 = src.indexOf(String.valueOf(src9));
            String src10 =  src.substring(index9 + 13);
            int index10 = src10.indexOf("<item><title>");
            String src11 =  src10.substring(index10 + 13);
            int index11 = src11.indexOf("</title>");
            src12 =  src11.substring(0, index11);

            int indexurl9 = src.indexOf(String.valueOf(srcurl9));
            String srcurl10 =  src.substring(indexurl9 + 9);
            int indexurl10 = srcurl10.indexOf("&amp;url=");
            String srcurl11 =  srcurl10.substring(indexurl10 + 9);
            int indexurl11 = srcurl11.indexOf("</link>");
            srcurl12 =  srcurl11.substring(0, indexurl11);

            Link();

        } catch (Exception e) {
            web.setText(e.toString());
        } finally {
            try {
                if (http != null)
                    http.disconnect();
                if (in != null)
                    in.close();
            } catch (Exception e) {
            }
        }












/*******
        //関連記事×3作成開始
       
        //特定の文字列にリンクを付ける、開始
        String detail = "item_detail_url";
        int num = 2;
        String number = String.valueOf(num);
        String detailtag = detail +number ;

        mURL2 = (TextView) findViewById(getResources().getIdentifier(detailtag, "id", getPackageName()));
        mURL2.setText(src3);
     //   mURL2.setVisibility(View.GONE);
        Pattern pattern2 = Pattern.compile(src3);
        final String strUrl2 = srcurl3;
        Linkify.TransformFilter filter2 = new Linkify.TransformFilter() {
            @Override
            public String transformUrl(Matcher match, String url) {
                return strUrl2;
            }
        };
        Linkify.addLinks(mURL2, pattern2, strUrl2, null, filter2);
        //特定の文字列にリンクを付ける、終了
        //以下、繰り返し

        mURL3 = (TextView) findViewById(R.id.item_detail_url3);
        mURL3.setText(src6);
        Pattern pattern3 = Pattern.compile(src6);
        final String strUrl3 = srcurl6 ;
        Linkify.TransformFilter filter3 = new Linkify.TransformFilter() {
            @Override
            public String transformUrl(Matcher match, String url) {
                return strUrl3;
            }
        };
        Linkify.addLinks(mURL3, pattern3, strUrl3, null, filter3);

        mURL4 = (TextView) findViewById(R.id.item_detail_url4);
        mURL4.setText(src9);
        Pattern pattern4 = Pattern.compile(src9);
        final String strUrl4 = srcurl9 ;
        Linkify.TransformFilter filter4 = new Linkify.TransformFilter() {
            @Override
            public String transformUrl(Matcher match, String url) {
                return strUrl4;
            }
        };
        Linkify.addLinks(mURL4, pattern4, strUrl4, null, filter4);

        mURL5 = (TextView) findViewById(R.id.item_detail_url5);
        mURL5.setText(src12);
        Pattern pattern5 = Pattern.compile(src12);
        final String strUrl5 = srcurl12 ;
        Linkify.TransformFilter filter5 = new Linkify.TransformFilter() {
            @Override
            public String transformUrl(Matcher match, String url) {
                return strUrl5;
            }
        };
        Linkify.addLinks(mURL5, pattern5, strUrl5, null, filter5);
        //関連記事作成終了
*//////////
        /*

        //特定の文字列にリンクを付ける
        TextView text = (TextView)findViewById(R.id.linktext1);
        text.setText("TechboosterではAndroidを中心に技術解説しています。Techboosterはほぼ毎日更新しています。");
        Pattern pattern = Pattern.compile("Techbooster");
        final String strUrl = "http://www.techbooster.org/";

        Linkify.TransformFilter filter = new Linkify.TransformFilter() {
            @Override
            public String transformUrl(Matcher match, String url) {
                return strUrl;
            }
        };

        Linkify.addLinks(text, pattern, strUrl, null, filter);


*/




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
                mURL2.setVisibility(View.GONE);
                mURL3.setVisibility(View.GONE);
                mURL4.setVisibility(View.GONE);
                //  Layout.removeView(mDescr);
                ImageView Hassan2 = (ImageView) findViewById(R.id.hassan2);
                Hassan2.setVisibility(View.GONE);
            }
        });
        mDescr.setMovementMethod(linkMethod);
        mDescr.setText(cs2);
/*      MovementMethod mMethod = LinkMovementMethod.getInstance();
        mDescr.setMovementMethod(mMethod);
        mDescr.setText(cs2);*/







    }






    public void Link(){
        int i ;
        for(i=0 ; i<3 ;i++) {
            String detail = "item_detail_url";
            int num = i + 2;
            String number = String.valueOf(num);
            String detailtag = detail + number;
            mURL = (TextView) findViewById(getResources().getIdentifier(detailtag, "id", getPackageName()));

            //String detail2 = "src";
            //int num2 = 3*(i+1);
            //String number2 = String.valueOf(num2);
            //String detailtag2 = detail2 + number2;

            if(i == 0){
                SRC = src3;
                SRCURL = srcurl3;
                mURL.setText(SRC);
            };
            if(i == 1){
                SRC = src6;
                SRCURL = srcurl6;
                mURL.setText(SRC);
            };
            if(i == 2){
                SRC = src9;
                SRCURL = srcurl9;
                mURL.setText(SRC);
            };
            Pattern pattern = Pattern.compile(SRC);
            final String strUrl = SRCURL;
            Linkify.TransformFilter filter = new Linkify.TransformFilter() {
                @Override
                public String transformUrl(Matcher match, String url) {
                    return strUrl;
                }
            };
            Linkify.addLinks(mURL, pattern, strUrl, null, filter);

           // String detail3 = "srcurl";
           // String number3 = String.valueOf(num2);
           // String detailtag3 = detail3 + number3;



        }
    }
}
