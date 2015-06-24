package com.example.nagatomo.rssreader;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RSSReaderSportActivity extends ListActivity {
    private static final String RSS_FEED_URL = "http://news.livedoor.com/topics/rss/spo.xml";
    private RSSListSportAdapter mAdapter;
    private ArrayList<Item> mItems;
    private TextView tv;
    private CharSequence titlename;
    private String TitleName;
    private AlertDialog.Builder builder;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rssreader_sport);
        // Itemオブジェクトを保持するためのリストを生成し、アダプタに追加する
        mItems = new ArrayList<Item>();//リストに表示したい配列を用意。ArrayListクラスは大きさが決まっていない配列。ArrayList<型> 変数名 = new ArrayList<型>();のように記述。Generics機能（型の部分にはクラス名を指定し、指定したクラスのオブジェクトを要素として格納できる。）
        mAdapter = new RSSListSportAdapter(this, mItems);//動的配列mItemsを渡してArrayAdapterクラスのオブジェクトを作成。

        // タスクを起動する
        RSSParserSportTask task = new RSSParserSportTask(this, mAdapter);
        task.execute(RSS_FEED_URL);   //AsyncTaskはインスタンスのexecuteメソッドでバックグラウンド処理を開始します。
    }
/*
    // リストの項目を選択した時の処理
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Item item = mItems.get(position);
        //以下4行で、他のActivity起動時に値を渡す
        Intent intent = new Intent(this, ItemDetailSportActivity.class);
        intent.putExtra("TITLE", item.getTitle());//第一引数key、第二引数渡したい値
        intent.putExtra("DESCRIPTION", item.getDescription());
        intent.putExtra("PUBDATE", item.getPubDate());
        startActivity(intent);
    }
*/


    // リストの項目を選択した時の処理
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Item item = mItems.get(position);
        titlename = item.getTitle();
        TitleName = titlename.toString();

        builder = new AlertDialog.Builder(this);
        Item items = mItems.get(position);
        builder.setTitle(items.getTitle());
        CharSequence descr = items.getDescription();
        String string = (String) descr;
        CharSequence cs1 = Html.fromHtml(string);
        tv = new TextView(this);
        // tv.setAutoLinkMask(Linkify.WEB_URLS);
        ScrollView sv = new ScrollView(this);
        sv.addView(tv);
        builder.setView(sv);
        //builder.setMessage(cs1);
        final AlertDialog dialog = builder.create();
        dialog.show();
        /// MovementMethod mMethod = LinkMovementMethod.getInstance();
        /// tv.setMovementMethod(mMethod);
        tv.setText(cs1);


        HandleableLinkMovementMethod linkMethod = new HandleableLinkMovementMethod();
        linkMethod.setOnUrlClickListener(new HandleableLinkMovementMethod.OnUrlClickListener() {
            @Override
            public void onUrlClick(Uri uri) {
                // ここでuriを使ってWebView表示用のIntentを飛ばしたりする
              //  WebView webView = (WebView)findViewById(R.id.webview2);
              //  webView.setWebViewClient(new WebViewClient());
              //  webView.loadUrl(String.valueOf(uri));
              //  tv.setVisibility(View.GONE);

                Intent intent = new Intent(getApplicationContext(), Body.class);
                intent.putExtra("TITLE", TitleName);
                intent.putExtra("pageurl",uri);
                startActivity(intent);
                RSSReaderSportActivity.this.finish();
                //  Layout.removeView(mDescr);
                dialog.dismiss();
                toastMake(String.valueOf(uri), 180, 30);
                System.out.println(uri);
            }
        });
        tv.setMovementMethod(linkMethod);
    }

    private void toastMake(String message, int x, int y){
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER|Gravity.LEFT, x, y);
        toast.show();
    }

}