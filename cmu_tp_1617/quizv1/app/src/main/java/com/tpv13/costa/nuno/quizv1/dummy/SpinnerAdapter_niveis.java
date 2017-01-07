package com.tpv13.costa.nuno.quizv1.dummy;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.tpv13.costa.nuno.quizv1.Nivel;

import java.util.List;

/**
 * Created by Utilizador on 06/01/2017.
 */

public class SpinnerAdapter_niveis extends ArrayAdapter<Nivel>
{
    private Context mContext;
    private List<Nivel> mValues;

    public SpinnerAdapter_niveis(Context context,int textViewResourceId, List<Nivel> objects) {
        super(context, textViewResourceId, objects);
        this.mContext = context;
        this.mValues = objects;
    }

    @Override
    public int getCount(){
        return mValues.size();
    }

    @Override
    public Nivel getItem(int position){
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
        label.setText(" " + mValues.get(position).getNome() );
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
        label.setText(" " + mValues.get(position).getNome());
        //label.setHeight(70);
        label.setGravity(Gravity.LEFT | Gravity.CENTER );

        return label;
    }
}
