package com.example.nagatomo.rssreader;

/**
 * Created by Nagatomo on 2015/06/17.
 */
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.app.ListActivity;
import java.util.ArrayList;
import android.widget.ListView;
import android.view.View;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.widget.Button;
import android.content.DialogInterface;
import android.view.View.OnClickListener;

public class RSSReaderJapanActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rssreader_japan);
    }
}
