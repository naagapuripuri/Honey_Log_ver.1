package com.example.nagatomo.rssreader;

import android.net.Uri;
import android.os.Bundle;
import android.app.ListActivity;
import android.app.Dialog;
import java.util.ArrayList;
import android.text.util.Linkify;
import android.widget.ScrollView;
import android.widget.TextView;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ListView;
import android.view.View;
import android.view.Gravity;
import android.content.Intent;
import android.view.View.OnClickListener;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.Html;
import com.example.nagatomo.rssreader.HandleableLinkMovementMethod.OnUrlClickListener;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.widget.Toast;

public class RSSReaderGirlsActivity extends ListActivity {
    private static final String RSS_FEED_URL = "http://news.livedoor.com/topics/rss/love.xml ";
  //  private static final int DIALOG_ID = 1;
    private RSSListGirlsAdapter mAdapter;
    private ArrayList<Item> mItems;
    private TextView tv;
    private AlertDialog.Builder builder;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rssreader_girls);

        // Itemオブジェクトを保持するためのリストを生成し、アダプタに追加する
        mItems = new ArrayList<Item>();//リストに表示したい配列を用意。ArrayListクラスは大きさが決まっていない配列。ArrayList<型> 変数名 = new ArrayList<型>();のように記述。Generics機能（型の部分にはクラス名を指定し、指定したクラスのオブジェクトを要素として格納できる。）
        mAdapter = new RSSListGirlsAdapter(this, mItems);//動的配列mItemsを渡してArrayAdapterクラスのオブジェクトを作成。

        // タスクを起動する
        RSSParserGirlsTask task = new RSSParserGirlsTask(this, mAdapter);
        task.execute(RSS_FEED_URL);   //AsyncTaskはインスタンスのexecuteメソッドでバックグラウンド処理を開始します。

    }


    // リストの項目を選択した時の処理
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
      /*  AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("start")
                .setPositiveButton("起動", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
// ボタンをクリックしたときの動作
                    }
                });*/
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
        linkMethod.setOnUrlClickListener(new OnUrlClickListener() {
            @Override
            public void onUrlClick(Uri uri) {
                // ここでuriを使ってWebView表示用のIntentを飛ばしたりする
                WebView webView = (WebView)findViewById(R.id.webview2);
                webView.setWebViewClient(new WebViewClient());
                webView.loadUrl(String.valueOf(uri));
                tv.setVisibility(View.GONE);
                //  Layout.removeView(mDescr);
                dialog.dismiss();
                toastMake(String.valueOf(uri), 180, 30);
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