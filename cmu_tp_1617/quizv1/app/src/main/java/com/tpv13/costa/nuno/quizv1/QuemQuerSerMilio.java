package com.tpv13.costa.nuno.quizv1;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class QuemQuerSerMilio extends ListActivity {

    private Context mContext;
    private QQSM_Adapter mAdapter ;
    private ArrayList<String> mList= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quem_quer_ser_milio);

        mContext = this;

        inserirPatamar();
        mAdapter = new QQSM_Adapter(mContext, mList);
        setListAdapter(mAdapter);
    }

    public void onResume()
    {
        super.onResume();
    }

    private void inserirPatamar(){
        String[] patamarArray=getResources().getStringArray(R.array.niveis_array);

        for (int i=0;i<patamarArray.length;i++){
            mList.add(patamarArray[i]);
        }
    }
}
