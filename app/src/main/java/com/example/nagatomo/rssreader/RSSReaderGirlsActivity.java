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
import android.content.Intent;
import android.view.View.OnClickListener;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.Html;
import com.example.nagatomo.rssreader.HandleableLinkMovementMethod.OnUrlClickListener;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;

public class RSSReaderGirlsActivity extends ListActivity {
    private static final String RSS_FEED_URL = "http://news.livedoor.com/topics/rss/love.xml ";
  //  private static final int DIALOG_ID = 1;
    private RSSListGirlsAdapter mAdapter;
    private ArrayList<Item> mItems;

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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        Item item = mItems.get(position);
        builder.setTitle(item.getTitle());
        CharSequence descr = item.getDescription();
        String string = (String) descr;
        CharSequence cs1 = Html.fromHtml(string);
        TextView tv = new TextView(this);
       // tv.setAutoLinkMask(Linkify.WEB_URLS);
       // String str = this.getString(R.string.text);
        tv.setText(cs1);
        ScrollView sv = new ScrollView(this);
        sv.addView(tv);
        builder.setView(sv);
        //builder.setMessage(cs1);
        builder.show();
        MovementMethod mMethod = LinkMovementMethod.getInstance();
        tv.setMovementMethod(mMethod);
        tv.setText(cs1);



/*
        Item item = mItems.get(position);
        //以下4行で、他のActivity起動時に値を渡す
        Intent intent = new Intent(this, ItemDetailGirlsActivity.class);
        intent.putExtra("TITLE", item.getTitle());//第一引数key、第二引数渡したい値
        intent.putExtra("DESCRIPTION", item.getDescription());
        intent.putExtra("PUBDATE", item.getPubDate());
        startActivity(intent);*/
    }
}