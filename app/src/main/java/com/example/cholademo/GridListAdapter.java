package com.example.cholademo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class GridListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> arrayList;
    private LayoutInflater inflater;
    private boolean isListView;
    private int selectedPosition = -1;

    public GridListAdapter(Context context, ArrayList<String> arrayList, boolean isListView) {
        this.context = context;
        this.arrayList = arrayList;
        this.isListView = isListView;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
                view = inflater.inflate(R.layout.grid_custom_row_layout, viewGroup, false);

            viewHolder.label = (TextView) view.findViewById(R.id.label);
            viewHolder.radioButton = (RadioButton) view.findViewById(R.id.radio_button);

            view.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) view.getTag();



        viewHolder.label.setText(arrayList.get(i));

        //check the radio button if both position and selectedPosition matches
        viewHolder.radioButton.setChecked(i == selectedPosition);

        //Set the position tag to both radio button and label
        viewHolder.radioButton.setTag(i);
        viewHolder.label.setTag(i);

        viewHolder.radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemCheckChanged(v);
                Toast.makeText(context, viewHolder.label.getText().toString() , Toast.LENGTH_SHORT).show();
            }
        });

        viewHolder.label.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemCheckChanged(v);
                Toast.makeText(context, viewHolder.label.getText().toString() , Toast.LENGTH_SHORT).show();
            }


        });
        return view;
    }

    //On selecting any view set the current position to selectedPositon and notify adapter
    private void itemCheckChanged(View v) {
        selectedPosition = (Integer) v.getTag();
        notifyDataSetChanged();
    }

    private class ViewHolder {
        private TextView label;
        private RadioButton radioButton;
    }
}
