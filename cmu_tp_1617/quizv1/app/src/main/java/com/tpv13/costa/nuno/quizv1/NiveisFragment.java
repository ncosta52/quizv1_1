package com.tpv13.costa.nuno.quizv1;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;



public class NiveisFragment extends ListFragment {
    private Context mContext;
    private QQSM_Adapter mAdapter ;
    private ArrayList<String> mList= new ArrayList<>();
    private OnLevelSelectedListener mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();

//        inserirPatamar();

        String[] patamarArray=getResources().getStringArray(R.array.niveis_array);

        for (int i=0;i<patamarArray.length;i++){
            mList.add(patamarArray[i]);
        }

        mAdapter = new QQSM_Adapter(this.mContext, mList);
        setListAdapter(mAdapter);


    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mListener = (OnLevelSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnNewsSelectedListener");
        }
    }



    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        mListener.onLevelSelected(mList.get(position));
    }



    private void inserirPatamar(){
        String[] patamarArray=getResources().getStringArray(R.array.niveis_array);

        for (int i=0;i<patamarArray.length;i++){
            mList.add(patamarArray[i]);
        }
    }

}
