package com.tpv13.costa.nuno.quizv1;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Nuno on 13-12-2016.
 */

public class QQSM_Adapter extends ArrayAdapter<String>{//implements Checkable {

    private  Context mContext;
    private ArrayList <String> mList;
    private Animation myAnim;
    private int selectedIndex = -1;
//
//    private ArrayList<Integer> checkedpositions_qqsm;


    public QQSM_Adapter(Context context, ArrayList<String> list) {
        super(context, R.layout.row_qqsm,list);

        this.mList=list;
        this.mContext = context;

    }

    public View getView(int position, View convertView, ViewGroup parent)
    {

        try{
            View v = convertView;

            if(v == null) {
                LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.row_qqsm, null);
            }

            TextView tPatamar=(TextView) v.findViewById(R.id.tv_nivel_patamar);
            tPatamar.setText(mList.get(position));

            if (position== this.selectedIndex) {

                coloRow(v);
            }
            else {
                if (position>getSelectedIndex() && getSelectedIndex()!=-1){
                    tPatamar.setBackgroundColor(Color.GRAY);
                }
            }

//            RadioButton rPatamar = (RadioButton) v.findViewById(R.id.rb_nivel_patamar);
//
//            if(getSelectedIndex() == position){
//                rPatamar.setChecked(true);
//            }
//            else{
//                rPatamar.setChecked(false);
//            }

            return v;
        }
        catch (Exception e)
        {
            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
        }


       return null;

    }

    public void coloRow(View convertView){ //int in,  View convertView
        View v = convertView;
        myAnim =allAnimStuff(1000);
//
        if(v == null) {
            LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.row_qqsm, null);
        }

        TextView text=(TextView) v.findViewById(R.id.tv_nivel_patamar);

        if(mList.get(getSelectedIndex()) != null )
        {
            text.startAnimation(myAnim);
            text.setTextColor(Color.WHITE);
            text.setText(mList.get(getSelectedIndex()));
            text.setBackgroundColor(Color.RED);
            int color = Color.argb( 200, 255, 64, 64 );
            text.setBackgroundColor( color );

        }

    }

    private Animation allAnimStuff(final long duration) {

        Animation milkshake = AnimationUtils.loadAnimation(mContext, R.anim.anim_alpha);
        //AnimationUtils.loadAnimation(this, R.anim.milkshake);
        //new AlphaAnimation(4,0);

        milkshake.setDuration(duration);
        milkshake.setFillAfter(false);


        milkshake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                //some code to make it wait here?
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });


        return milkshake;
    }

//    @Override
//    public void setChecked(boolean b) {
//
//    }
//
//    @Override
//    public boolean isChecked() {
//        return false;
//    }
//
//    @Override
//    public void toggle() {
//
//    }
//
    public void setSelectedIndex(int index){
        selectedIndex = index;
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }
}
