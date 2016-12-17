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
    public ArrayList<String> mList= new ArrayList<>();
    private OnLevelSelectedListener mListener;
    private int nivelSel=1;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();

        inserirPatamar();

//        String[] patamarArray=getResources().getStringArray(R.array.niveis_array);
//
//        for (int i=0;i<patamarArray.length;i++){
//            mList.add(new String(patamarArray[i]) );
//        }

        mAdapter = new QQSM_Adapter(mContext, mList);
        setListAdapter(mAdapter);

        mAdapter.setSelectedIndex(14);

        mAdapter.coloRow(null);
        mListener.onLevelSelected(1);

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
    public void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();


    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        mAdapter.setSelectedIndex(position);
//        mAdapter.notifyDataSetChanged();
        mListener.onLevelSelected(getResources().getStringArray(R.array.niveis_array).length - position);
    }

    public void selectNivel(int n){

        mAdapter.setSelectedIndex(getResources().getStringArray(R.array.niveis_array).length -n);
        mAdapter.coloRow(null);
        ////mList.performItemClick(mList.getAdapter().getView(3, null, null), 3, mList.getItemIdAtPosition(3));

        mListener.onLevelSelected( n);
    }



    private void inserirPatamar(){
        String[] patamarArray=getResources().getStringArray(R.array.niveis_array);

        for (int i=0;i<patamarArray.length;i++){
            mList.add(patamarArray[i]);
        }
    }

}
