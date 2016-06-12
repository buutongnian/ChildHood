package com.example.lcc.childhood;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.StringBuilderPrinter;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by lcc on 2016/6/8.
 */
public class Collect extends ListActivity {
    String[] presidents;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListView listView = getListView();
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setTextFilterEnabled(true);
        presidents = getResources().getStringArray(R.array.presidents_array);
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, presidents));
    }

    @Override
    protected void onListItemClick(ListView parent, View v, int position, long id) {
        Toast.makeText(this, "You are selected" + presidents[position], Toast.LENGTH_LONG).show();
    }
}
