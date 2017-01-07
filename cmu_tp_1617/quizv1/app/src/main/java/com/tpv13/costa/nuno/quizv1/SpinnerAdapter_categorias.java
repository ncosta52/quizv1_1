package com.tpv13.costa.nuno.quizv1;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Utilizador on 05/01/2017.
 */

public class SpinnerAdapter_categorias extends ArrayAdapter<Categoria> {
    private Context mContext;
    private List<Categoria> mValues;

    public SpinnerAdapter_categorias(Context context,
                                 int textViewResourceId, List<Categoria> objects) {
        super(context, textViewResourceId, objects);
        this.mContext = context;
        this.mValues = objects;
    }

    @Override
    public int getCount(){
        return mValues.size();
    }

    @Override
    public Categoria getItem(int position){
        return mValues.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //This is for the first item before dropdown or default state.
        TextView label = new TextView(mContext);
        label.setTextColor(Color.BLACK);
        label.setTextSize(24);
        label.setText(" " + mValues.get(position).getDescricao() );
        //label.setHeight(50);
        label.setGravity(Gravity.LEFT | Gravity.CENTER );
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        //This is when user click the spinner and list of item display
        // beneath it
        TextView label = new TextView(mContext);
        label.setTextColor(Color.BLACK);
        //label.setTextSize(18);
        label.setText(" " + mValues.get(position).getDescricao());
        //label.setHeight(70);
        label.setGravity(Gravity.LEFT | Gravity.CENTER );

        return label;
    }
}

