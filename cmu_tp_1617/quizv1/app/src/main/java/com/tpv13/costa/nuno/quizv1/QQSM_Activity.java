package com.tpv13.costa.nuno.quizv1;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class QQSM_Activity extends ActionBarActivity implements OnLevelSelectedListener{

    private NiveisFragment levelFragment;
    private PerguntaDetailFragment perguntaFragment;
    private ArrayList<Integer> pergsNivel_1=new ArrayList<>();
    private ArrayList<Integer> pergsNivel_2=new ArrayList<>();
    private ArrayList<Integer> pergsNivel_3=new ArrayList<>();

//    private MyDbHelper_game dbHelper;
    private Random randomGenerator=new Random();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qqsm_);



        pergsNivel_1 = carregarPerguntas(1);
        pergsNivel_2 = carregarPerguntas(2);
        pergsNivel_3 = carregarPerguntas(3);

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
    public void  onLevelSelected(int level) {
        int index=0;
        int pergId=0;

        if (level<=5){
          //nivel 1
            index = randomGenerator.nextInt(pergsNivel_1.size());
            pergId=pergsNivel_1.get(index);
            pergsNivel_1.remove(index);
      }
        else {
          if (level >=10){
              //nivel 2
              index = randomGenerator.nextInt(pergsNivel_2.size());
              pergId=pergsNivel_2.get(index);
              pergsNivel_2.remove(index);
          } else {
              //nivel 3
              index = randomGenerator.nextInt(pergsNivel_3.size());
              pergId = pergsNivel_3.get(index);
              pergsNivel_3.remove(index);
          }
      }


//FAZER a gestão smartPhone vs Tablet
        //smartPhone - trocar fragment
        //Tables Actualizar detalhes

        perguntaFragment = (PerguntaDetailFragment) getSupportFragmentManager().findFragmentById(R.id.b_fragment);

        if(perguntaFragment != null) {
            perguntaFragment.setPergunta(pergId);  //tablet
        } else {
            perguntaFragment = new PerguntaDetailFragment(); //tlm
            perguntaFragment.setPergunta(pergId);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, perguntaFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }


    private ArrayList<Integer> carregarPerguntas(int _nivel){

        MyDbHelper_game dbHelper = new MyDbHelper_game(this);

        ArrayList<Integer> lis=new ArrayList<>();

        String[] columnsPerguntasSelect={"Id"};
        String WHERE =  "Niveis_Id='" + _nivel + "'" ;



//        Toast.makeText(this, WHERE , Toast.LENGTH_LONG).show();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        //Cursor c = db.rawQuery("SELECT * FROM tblPessoas", null);
        Cursor c= db.query(true,"Perguntas",columnsPerguntasSelect,WHERE,null,null,null,null,null);

        try
        {
            if (c.getCount()>0 ) {
                c.moveToFirst();
                // Loop through all Results
                do {
                    lis.add(c.getInt(0));
                }while(c.moveToNext());
            }
            c.close();
            db.close();

        }
        catch(Exception e) {
            Log.e("Error", "Error", e);
            throw e;
        }

        return  lis;
    }



}

