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

    private MyDbHelper_game dbHelper;
    private Random randomGenerator=new Random();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qqsm_);

        dbHelper = new MyDbHelper_game(this);

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
            perguntaFragment.setPergunta(carregarPerguntaSelecionada(pergId));  //tablet
        } else {
            perguntaFragment = new PerguntaDetailFragment(); //tlm
            perguntaFragment.setPergunta(carregarPerguntaSelecionada(pergId));

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, perguntaFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    private Pergunta carregarPerguntaSelecionada(int pergId)
    {
        int index_rsp;
        Pergunta res=null;

        ArrayList<Resposta> rspLstPesq, rspLst = new ArrayList<>();
        rspLstPesq = selecionarRespostas(pergId);

        String[] columnsPerguntasSelect = {"Id", "Pergunta", "Pontuacao", "Niveis_Id", "Categorias_Id"};
        String WHERE = "Id='" + pergId + "'";

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        //Cursor c = db.rawQuery("SELECT * FROM tblPessoas", null);
        Cursor c = db.query(true, "Perguntas", columnsPerguntasSelect, WHERE, null, null, null, null, null);

        try {
            if (c.getCount() > 0) {
                c.moveToFirst();

                do {

                    while (rspLstPesq.size() > 0) {
                        index_rsp = randomGenerator.nextInt(rspLstPesq.size());
                        rspLst.add(rspLstPesq.get(index_rsp));
                        rspLstPesq.remove(index_rsp);
                    }

                    res = new Pergunta(c.getInt(0), c.getInt(3), c.getString(1), c.getInt(2), c.getInt(4), rspLst);


                } while (c.moveToNext());
            }

            c.close();
            db.close();

        } catch (Exception e) {
            Log.e("Error", "Error", e);
//            Toast.makeText(this, "Erro sortearPergunta: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            throw e;
        }

        return res;
    }

    private ArrayList<Resposta> selecionarRespostas ( int idPergunta ){
        ArrayList<Resposta> rspList = new ArrayList<>();
        boolean correta;

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columnsRespostasSelect={"Id","Perguntas_Id","Descricao","Correta"};
        String WHERE_RSP =  "Perguntas_Id='" + idPergunta + "'" ;

        Cursor c2= db.query(true,"Respostas",columnsRespostasSelect,WHERE_RSP,null,null,null,null,null);

        if (c2.getCount()>0 ) {
            c2.moveToFirst();

            do {
                if (c2.getString(3).equals("S")) {
                    correta = true;
                } else {
                    correta = false;
                }
                rspList.add(new Resposta(c2.getInt(0), c2.getInt(1), c2.getString(2), correta));
            } while (c2.moveToNext());
        }

        c2.close();
        db.close();

        return rspList;
    }

    private ArrayList<Integer> carregarPerguntas(int _nivel){



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

