package com.example.nagatomo.rssreader;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.app.ListActivity;
import java.util.ArrayList;

/**
 * Created by Nagatomo on 2015/06/23.
 */
public class RSSReaderMovieActivity extends ListActivity {
    private static final String RSS_FEED_URL = "http://news.livedoor.com/rss/summary/52.xml";
    private RSSListMovieAdapter mAdapter;
    private ArrayList<Item> mItems;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rssreader_movie);
        // Itemオブジェクトを保持するためのリストを生成し、アダプタに追加する
        mItems = new ArrayList<Item>();//リストに表示したい配列を用意。ArrayListクラスは大きさが決まっていない配列。ArrayList<型> 変数名 = new ArrayList<型>();のように記述。Generics機能（型の部分にはクラス名を指定し、指定したクラスのオブジェクトを要素として格納できる。）
        mAdapter = new RSSListMovieAdapter(this, mItems);//動的配列mItemsを渡してArrayAdapterクラスのオブジェクトを作成。

        // タスクを起動する
        RSSParserMovieTask task = new RSSParserMovieTask(this, mAdapter);
        task.execute(RSS_FEED_URL);   //AsyncTaskはインスタンスのexecuteメソッドでバックグラウンド処理を開始します。
    }

    // リストの項目を選択した時の処理
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Item item = mItems.get(position);
        //以下4行で、他のActivity起動時に値を渡す
        Intent intent = new Intent(this, ItemDetailMovieActivity.class);
        intent.putExtra("TITLE", item.getTitle());//第一引数key、第二引数渡したい値
        intent.putExtra("DESCRIPTION", item.getDescription());
        intent.putExtra("PUBDATE", item.getPubDate());
        startActivity(intent);
    }
}