package com.example.nagatomo.rssreader;

import android.app.Activity;
import android.os.StrictMode;
import android.widget.TextView;
import android.widget.ImageView;
import android.os.Bundle;
import android.content.Intent;
import android.text.Html;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.graphics.Color;
import com.example.nagatomo.rssreader.HandleableLinkMovementMethod.OnUrlClickListener;
import android.net.Uri;
import android.view.View;
import android.app.AlertDialog;
import android.view.View.OnClickListener;
import android.text.util.Linkify;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ItemDetailJapanActivity extends Activity {
    private TextView mTitle;
    private TextView mDescr;
    private TextView mURL2;
    private TextView mURL3;
    private TextView mURL4;
    private String src3;
    private String src6;
    private String srcurl3;
    private String srcurl6;
    private String titlecut;
    private CharSequence cs1;
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
        TextView web2 = (TextView) findViewById(R.id.textViewURL2);
        TextView web3 = (TextView) findViewById(R.id.textViewURL3);
        try {
            // URLにHTTP接続
            String s01 = "https://news.google.com/news?hl=ja&ned=us&ie=UTF-8&oe=UTF-8&output=rss&q=";
            String s02 = "biyougeka";
            String s03 = cs1.toString();
            String s = s01 + s03;
            URL url = new URL(String.valueOf(s));
            http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");
            http.connect();
            // データを取得
            in = http.getInputStream();

            String src = new String();
            byte[] line = new byte[1024];
            int size;
            while (true) {
                size = in.read(line);
                if (size <= 0)
                    break;
                src += new String(line);
            }
            // HTMLソースを表示
            int index = src.indexOf("<item><title>");
            String src2 =  src.substring(index + 13);
            int index2 = src2.indexOf("</title>");
            src3 =  src2.substring(0, index2);
            //String trans = new String(String.valueOf(src3).getBytes("EUC_JP"), "EUC_JP");
            web.setText(src3);

            int indexurl = src.indexOf("&amp;url=");
            String srcurl2 =  src.substring(indexurl + 9);
            int indexurl2 = srcurl2.indexOf("</link>");
            srcurl3 =  srcurl2.substring(0, indexurl2);
            //String trans = new String(String.valueOf(src3).getBytes("EUC_JP"), "EUC_JP");
            web2.setText(srcurl3);


            int index3 = src.indexOf(String.valueOf(src3));
            String src4 =  src.substring(index3 + 13);
            int index4 = src4.indexOf("<item><title>");
            String src5 =  src4.substring(index4 + 13);
            int index5 = src5.indexOf("</title>");
            src6 =  src5.substring(0, index5);
            //String trans = new String(String.valueOf(src3).getBytes("EUC_JP"), "EUC_JP");
            web3.setText(src6);

            int indexurl3 = src.indexOf(String.valueOf(srcurl3));
            String srcurl4 =  src.substring(indexurl3 + 9);
            int indexurl4 = srcurl4.indexOf("&amp;url=");
            String srcurl5 =  srcurl4.substring(indexurl4 + 9);
            int indexurl5 = srcurl5.indexOf("</link>");
            srcurl6 =  srcurl5.substring(0, indexurl5);
            //String trans = new String(String.valueOf(src3).getBytes("EUC_JP"), "EUC_JP");
            web2.setText(srcurl6);

    /*        int index6 = src.indexOf(String.valueOf(src6));
            String src7 =  src.substring(index6 + 13);
            int index7 = src7.indexOf("<item><title>");
            String src8 =  src7.substring(index7 + 13);
            int index8 = src8.indexOf("</title>");
            String src9 =  src8.substring(0, index8);
            //String trans = new String(String.valueOf(src3).getBytes("EUC_JP"), "EUC_JP");
            web3.setText(src9);
*/

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



        //関連記事×3作成開始
        String murl2 = intent.getStringExtra("URL2");
        //特定の文字列にリンクを付ける、開始
        mURL2 = (TextView) findViewById(R.id.item_detail_url2);
        mURL2.setText(src3);
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
        String murl3 = intent.getStringExtra("URL3");
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

     /*   String murl3 = intent.getStringExtra("URL3");
        mURL3 = (TextView) findViewById(R.id.item_detail_url3);
        mURL3.setText(murl3);
        Pattern pattern3 = Pattern.compile(murl3);
        final String strUrl3 = "http://www.techbooster.org/" ;
        Linkify.TransformFilter filter3 = new Linkify.TransformFilter() {
            @Override
            public String transformUrl(Matcher match, String url) {
                return strUrl3;
            }
        };
        Linkify.addLinks(mURL3, pattern3, strUrl3, null, filter3);
*/
        final String strUrl4 = intent.getStringExtra("URL4");
        String murl4 = "techbooster";
        mURL4 = (TextView) findViewById(R.id.item_detail_url4);
        mURL4.setText(murl4);
        Pattern pattern4 = Pattern.compile(murl4);
        Linkify.TransformFilter filter4 = new Linkify.TransformFilter() {
            @Override
            public String transformUrl(Matcher match, String url) {
                return strUrl4;
            }
        };
        Linkify.addLinks(mURL4, pattern4, strUrl4, null, filter4);
        //関連記事作成終了

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
        linkMethod.setOnUrlClickListener(new OnUrlClickListener() {
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
}
