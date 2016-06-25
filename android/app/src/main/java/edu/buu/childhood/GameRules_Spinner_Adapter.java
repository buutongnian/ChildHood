package edu.buu.childhood;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lcc on 2016/6/24.
 */
class GameRules_Spinner_Adapter extends ArrayAdapter<String> {
    private List<String> list = new ArrayList<String>();
    private Spinner spinner;
    private Context content;

    public GameRules_Spinner_Adapter(Context context, int textViewResourceId, List<String> list, Spinner spinner) {
        super(context, textViewResourceId, list);
        this.list = list;
        this.spinner = spinner;
        this.content = context;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = android.view.View.inflate(getContext(), R.layout.game_spinner_dropdownlist, null);
        TextView label = (TextView) view
                .findViewById(R.id.spinner_item_label);
        ImageView check = (ImageView) view
                .findViewById(R.id.spinner_item_checked_image);
        label.setText(list.get(position));
        label.setTextColor(content.getResources().getColor(R.color.black));
        if (spinner.getSelectedItemPosition() == position) {
            view.setBackgroundColor(content.getResources().getColor(
                    R.color.spinner_green));
            check.setImageResource(R.drawable.pre);
        } else {
            view.setBackgroundColor(content.getResources().getColor(
                    R.color.spinner_light_green));
            check.setImageResource(R.drawable.pre);
        }
        return view;
    }

    ;

}


