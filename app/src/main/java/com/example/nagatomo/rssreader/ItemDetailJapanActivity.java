package com.example.nagatomo.rssreader;

import android.app.Activity;
import android.os.StrictMode;
import android.widget.TextView;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemdetail);

        //受け取り側のActivityは設定されてるkey(ここではTITLE)で値を取り出す
        Intent intent = getIntent();
        String title = intent.getStringExtra("TITLE");
        String descr = intent.getStringExtra("DESCRIPTION");
        CharSequence cs1 = Html.fromHtml(title);
        CharSequence cs2 = Html.fromHtml(descr);
        mTitle = (TextView) findViewById(R.id.item_detail_title);//findViewByIdでリソースIDに対応するビューのオブジェクトを取得する
        // mTitle.setText(title);  //変数titleを、mTitleオブジェクトのsetTextメソッドに渡して何かしらの結果を返す
        mDescr = (TextView) findViewById(R.id.item_detail_descr);
        //  mDescr.setText(descr);
        mTitle.setText(cs1);
        mTitle.setTextColor(Color.parseColor("magenta"));



//特定のURLに対して、そのtitle、すなわちサイト名のみを抜き出す
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());

        HttpURLConnection http = null;
        InputStream in = null;
        TextView web = (TextView) findViewById(R.id.textViewURL);
        try {
            // URLにHTTP接続
            String s = "https://www.taylorguitars.com/";
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
            int index = src.indexOf("<title>");
            String src2 =  src.substring(index + 7);
            int index2 = src2.indexOf("</title>");
            String src3 =  src2.substring(0, index2);
            //String trans = new String(String.valueOf(src3).getBytes("EUC_JP"), "EUC_JP");
            web.setText(src3);
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








//以下、webViewに飛ぶようにするコード
        HandleableLinkMovementMethod linkMethod = new HandleableLinkMovementMethod();
        linkMethod.setOnUrlClickListener(new OnUrlClickListener() {
            @Override
            public void onUrlClick(Uri uri) {
                // ここでuriを使ってWebView表示用のIntentを飛ばしたりする
                WebView webView = (WebView)findViewById(R.id.webview);
                webView.setWebViewClient(new WebViewClient());
                webView.loadUrl(String.valueOf(uri));
                mDescr.setVisibility(View.GONE);
                //  Layout.removeView(mDescr);
            }
        });
        mDescr.setMovementMethod(linkMethod);
        mDescr.setText(cs2);
/*      MovementMethod mMethod = LinkMovementMethod.getInstance();
        mDescr.setMovementMethod(mMethod);
        mDescr.setText(cs2);*/







    }
}
