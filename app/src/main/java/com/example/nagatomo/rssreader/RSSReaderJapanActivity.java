package com.example.nagatomo.rssreader;

import android.app.Activity;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.Menu;
import android.app.ListActivity;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.widget.ListView;
import android.view.View;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.widget.Button;
import android.content.DialogInterface;
import android.view.View.OnClickListener;

public class RSSReaderJapanActivity extends ListActivity {
    private static final String RSS_FEED_URL = "http://news.livedoor.com/topics/rss/dom.xml ";
    private RSSListJapanAdapter mAdapter;
    private ArrayList<Item> mItems;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rssreader_japan);
        // Itemオブジェクトを保持するためのリストを生成し、アダプタに追加する
        mItems = new ArrayList<Item>();//リストに表示したい配列を用意。ArrayListクラスは大きさが決まっていない配列。ArrayList<型> 変数名 = new ArrayList<型>();のように記述。Generics機能（型の部分にはクラス名を指定し、指定したクラスのオブジェクトを要素として格納できる。）
        mAdapter = new RSSListJapanAdapter(this, mItems);//動的配列mItemsを渡してArrayAdapterクラスのオブジェクトを作成。

        // タスクを起動する
        RSSParserJapanTask task = new RSSParserJapanTask(this, mAdapter);
        task.execute(RSS_FEED_URL);   //AsyncTaskはインスタンスのexecuteメソッドでバックグラウンド処理を開始します。

    }




    // リストの項目を選択した時の処理
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Item item = mItems.get(position);
        //以下4行で、他のActivity起動時に値を渡す
        Intent intent = new Intent(this, ItemDetailJapanActivity.class);
        intent.putExtra("TITLE", item.getTitle());//第一引数key、第二引数渡したい値
        intent.putExtra("DESCRIPTION", item.getDescription());
        intent.putExtra("PUBDATE", item.getPubDate());


        String strUrl2 = "techbooster.org/";
        String strUrl3 = "techbooster.org/";
        String strUrl4 = "http://www.techbooster.org/";
        intent.putExtra("URL2",strUrl2 );
        intent.putExtra("URL3",strUrl3 );
        intent.putExtra("URL4",strUrl4 );
        startActivity(intent);
    }
}