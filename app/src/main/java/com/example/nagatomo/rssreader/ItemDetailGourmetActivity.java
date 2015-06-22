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
import android.widget.ScrollView;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItemDetailGourmetActivity extends Activity {
    private TextView mTitle;
    private TextView mDescr;
    private TextView mURL;
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
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemdetail);

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

        //特定のURLに対して、そのhtmlリソースの欲しい一部をとりにいく
        HttpURLConnection http = null;
        InputStream in = null;
        TextView web = (TextView) findViewById(R.id.textViewURL);
        try {
            String s01 = "https://news.google.com/news?hl=ja&ned=us&ie=UTF-8&oe=UTF-8&output=rss&q=";
            String s02 = cs1.toString();
            String s03 = URLEncoder.encode(s02, "UTF-8");
            String s = s01 + s03;
            //URL で指定されたコンテンツを HTTP で取得する大まかな流れは以下
            URL url ;                                          //クラスの参照型変数の宣言
            url = new URL(String.valueOf(s));                  //クラスのインスタンスを生成し、その参照を参照型変数に入れる。URL オブジェクトを生成する。
            http = (HttpURLConnection) url.openConnection();   //接続用HttpURLConnectionオブジェクト作成。サイトに接続
            http.setRequestMethod("GET");                      // リクエストメソッドの設定 （デフォルトが GET メソッドなので省略可）。プロトコルの設定
            http.connect();                                    //接続する．
            in = http.getInputStream();                        // ネット上のファイルを開く。

            String src = "";                                   // InputStreamから取得したbyteデータを文字列にして保持するための変数。初期値として空文字（長さが0の文字列）
            byte[] line = new byte[1024];                      // InputStreamからbyteデータを取得するための変数
            int size;
            while (true) {                                     // while内で、InputStreamからのデータを文字列として取得する
                size = in.read(line);                          //ネット上のファイルから１バイトのデータが読み取られ、int型の変数にセット
                if (size <= 0)
                    break;
                src += new String(line);
            }




            // ★★★HTMLソースの中からタイトル・URLをピックアップしていく
            int index1 = src.indexOf("<item><title>");
            String src2 =  src.substring(index1 + 13);
            int index2 = src2.indexOf("</title>");
            src3 =  src2.substring(0, index2);
            //src3 = src;

            int indexurl1 = src.indexOf("2005:cluster=");
            String srcurl2 =  src.substring(indexurl1 + 13);
            int indexurl2 = srcurl2.indexOf("</guid>");
            srcurl3 =  srcurl2.substring(0, indexurl2);

      //      int index3 = src.indexOf(String.valueOf(src3));
      //      String src4 =  src.substring(index3 + 100);
            int index4 = src2.indexOf("<item><title>");
            String src5 =  src2.substring(index4 + 13);
            int index5 = src5.indexOf("</title>");
            src6 =  src5.substring(0, index5);


       //     int indexurl3 = src.indexOf(String.valueOf(srcurl3));
       //     String srcurl4 =  src.substring(indexurl3 + 100);
            int indexurl4 = srcurl2.indexOf("&amp;url=");
            String srcurl5 =  srcurl2.substring(indexurl4 + 9);
            int indexurl5 = srcurl5.indexOf("</link>");
            srcurl6 =  srcurl5.substring(0, indexurl5);

         //   int index6 = src.indexOf(String.valueOf(src6));
         //   String src7 =  src.substring(index6 + 100);
            int index7 = src5.indexOf("<item><title>");
            String src8 =  src5.substring(index7 + 13);
            int index8 = src8.indexOf("</title>");
            src9 =  src8.substring(0, index8);

      //      int indexurl6 = src.indexOf(String.valueOf(srcurl6));
      //      String srcurl7 =  src.substring(indexurl6 + 100);
            int indexurl7 = srcurl5.indexOf("&amp;url=");
            String srcurl8 =  srcurl5.substring(indexurl7 + 9);
            int indexurl8 = srcurl8.indexOf("</link>");
            srcurl9 =  srcurl8.substring(0, indexurl8);

     //       int index9 = src.indexOf(String.valueOf(src9));
     //       String src10 =  src.substring(index9 + 100);
            int index10 = src8.indexOf("<item><title>");
            String src11 =  src8.substring(index10 + 13);
            int index11 = src11.indexOf("</title>");
            src12 =  src11.substring(0, index11);

         //   int indexurl9 = src.indexOf(String.valueOf(srcurl9));
         //   String srcurl10 =  src.substring(indexurl9 + 100);
            int indexurl10 = srcurl8.indexOf("&amp;url=");
            String srcurl11 =  srcurl8.substring(indexurl10 + 9);
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
            if(i == 0){
                SRC = src3;
                SRCURL = srcurl3;
            };
            if(i == 1){
                SRC = src6;
                SRCURL = srcurl6;
            };
            if(i == 2){
                SRC = src9;
                SRCURL = srcurl9;
            };
            if(i == 3){
                SRC = src12;
                SRCURL = srcurl12;
            };
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
