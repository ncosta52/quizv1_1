package com.tpv13.costa.nuno.quizv1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Nuno on 13-12-2016.
 */

public class QQSM_Adapter extends ArrayAdapter<String> {
    private  Context mContext;
    private ArrayList <String> mList;


    public QQSM_Adapter(Context context, ArrayList<String> list) {
        super(context, R.layout.row_qqsm,list);

        mList=list;
        mContext = context;


    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        View v = convertView;

        if(v == null) {
            LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.row_qqsm, null);
        }
        TextView mNivel=(TextView) v.findViewById(R.id.nivel_patamar);//
        mNivel.setText(mList.get(position).toString());


        return v;

    }
}
