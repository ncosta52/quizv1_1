package com.tpv13.costa.nuno.quizv1;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Nuno on 13-12-2016.
 */

public class QQSM_Adapter extends ArrayAdapter<String> {

    private  Context mContext;
    private ArrayList <String> mList;
    private Animation myAnim;


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
            TextView mNivel=(TextView) v.findViewById(R.id.nivel_patamar);//
            mNivel.setText(mList.get(position));


            return v;
        }
        catch (Exception e)
        {
            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
        }


       return null;

    }

    public void coloRow(int in,  View convertView){
        View v = convertView;
        myAnim =allAnimStuff(500);

        if(v == null) {
            LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.row_qqsm, null);
        }

        TextView text=(TextView) v.findViewById(R.id.nivel_patamar);

        if(mList.get(in) != null )
        {
            text.startAnimation(myAnim);
            text.setTextColor(Color.WHITE);
            text.setText(mList.get(in));
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

}
