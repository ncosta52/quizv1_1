package com.tpv13.costa.nuno.quizv1;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;

public class QQSM_Activity extends ActionBarActivity implements OnLevelSelectedListener{

    private NiveisFragment levelFragment;
    private PerguntaDetailFragment perguntaFragment;
    //LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qqsm_);

        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null)
                return;
            levelFragment = new NiveisFragment();

            FragmentTransaction transaction_i = getSupportFragmentManager().beginTransaction();
            transaction_i.add(R.id.fragment_container, levelFragment);
            transaction_i.commit();
        }
    }

    @Override
    public void  onLevelSelected(String l) {

//FAZER a gestão smartPhone vs Tablet
        //smartPhone - trocar fragment
        //Tables Actualizar detalhes

        perguntaFragment = (PerguntaDetailFragment) getSupportFragmentManager().findFragmentById(R.id.b_fragment);

        if(perguntaFragment != null) {
            perguntaFragment.setNivel(l);  //tablet
        } else {
            perguntaFragment = new PerguntaDetailFragment(); //tlm
            perguntaFragment.setNivel (l);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, perguntaFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }



}

