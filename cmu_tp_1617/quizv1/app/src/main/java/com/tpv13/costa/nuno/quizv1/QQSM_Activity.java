package com.tpv13.costa.nuno.quizv1;

import android.app.FragmentManager;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentContainer;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class QQSM_Activity extends ActionBarActivity implements OnLevelSelectedListener,OnRspSelecionada,OnAjudasSelectListener{

    private NiveisFragment levelFragment;
    private PerguntaDetailFragment perguntaFragment;
    private ArrayList<Integer> pergsNivel_1=new ArrayList<>();
    private ArrayList<Integer> pergsNivel_2=new ArrayList<>();
    private ArrayList<Integer> pergsNivel_3=new ArrayList<>();

    private boolean ajuda_50;
    private boolean ajuda_Publico;
    private boolean ajuda_Tlf;

    private MyDbHelper_game dbHelper;
    private Random randomGenerator=new Random();

    private View tmp;
    private Animation myAnim ;

    private int nivelSel=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qqsm_);

        dbHelper = new MyDbHelper_game(this);

        tmp = (View) findViewById(R.id.fragment_container);


        pergsNivel_1 = carregarPerguntas(1);
        pergsNivel_2 = carregarPerguntas(2);
        pergsNivel_3 = carregarPerguntas(3);

        ajuda_50=false;
        ajuda_Publico=false;
        ajuda_Tlf=false;

        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null)
                return;
            levelFragment = new NiveisFragment();

            FragmentTransaction transaction_i = getSupportFragmentManager().beginTransaction();
            transaction_i.add(R.id.fragment_container, levelFragment);
            transaction_i.addToBackStack(null);
            transaction_i.commit();
        }


    }

    @Override
    public void  onLevelSelected(int level) {
        int index=0;
        int pergId=0;
        if ( level<=getResources().getStringArray(R.array.niveis_array).length) {
            if (level <= 5) {
                //nivel 1
                index = randomGenerator.nextInt(pergsNivel_1.size());
                pergId = pergsNivel_1.get(index);
                pergsNivel_1.remove(index);
            } else {
                if (level <= 10) {
                    //nivel 2
                    index = randomGenerator.nextInt(pergsNivel_2.size());
                    pergId = pergsNivel_2.get(index);
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
            myAnim =allAnimStuff(getResources().getInteger(R.integer.animacao_qqsm_patamar),pergId);
            tmp.startAnimation(myAnim);


        }
        else {
            Toast.makeText(this, "Vencedor", Toast.LENGTH_SHORT).show();
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


    @Override
    public void onRsp(Boolean rsp) {
        if (rsp) {
            //Toast.makeText(this, "Certa\nPassar para nivel Seguinte", Toast.LENGTH_SHORT).show();
            nivelSel++;
        }
        else{
//            Toast.makeText(this, "ERRADA\nMostrar Dinheiro Ganho", Toast.LENGTH_SHORT).show();
            String[] tmp=getResources().getStringArray(R.array.niveis_array);

            if ((tmp.length - nivelSel)>=10){
                //não ganhou nada
                Toast.makeText(this, "ERRADA\n" + "Não ganhou nada!", Toast.LENGTH_SHORT).show();
            }
            else{
                if ((tmp.length - nivelSel)>=5){
                    //ganhou 1000€ ou valor na 10ª posicao
                    Toast.makeText(this, "ERRADA\n" +"Ganhou: " + tmp[10],Toast.LENGTH_SHORT).show();
                }
                else{
                    if ((tmp.length - nivelSel)>=0 ){
                        //ganhou 32000€ ou valor na 5ª
                        Toast.makeText(this, "ERRADA\n" +"Ganhou: " + tmp[5],Toast.LENGTH_SHORT).show();
                    }else{
                        //ganhou 1Milhão ou valor na posição 0
                        Toast.makeText(this, "ERRADA\n" +"Ganhou: " + tmp[0],Toast.LENGTH_SHORT).show();
                    }
                }
            }


        }




        if (findViewById(R.id.fragment_container) != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            //transaction.remove(perguntaFragment);
            transaction.replace(R.id.fragment_container, levelFragment);
            //transaction.addToBackStack(null);
            transaction.commit();
        }

        if (rsp) {
            levelFragment.selectNivel(nivelSel);
        }

    }

    private Animation allAnimStuff(final long duration, final int _pergId) {

        Animation milkshake = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_qqsm);
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
                perguntaFragment = (PerguntaDetailFragment) getSupportFragmentManager().findFragmentById(R.id.b_fragment);

                if (perguntaFragment != null) {
                    perguntaFragment.setAjudasEstado(isAjuda_50(),isAjuda_Tlf(),isAjuda_Publico());
                    perguntaFragment.setPergunta(carregarPerguntaSelecionada(_pergId));  //tablet
                } else {
                    perguntaFragment = new PerguntaDetailFragment(); //tlm
                    perguntaFragment.setAjudasEstado(isAjuda_50(),isAjuda_Tlf(),isAjuda_Publico());
                    perguntaFragment.setPergunta(carregarPerguntaSelecionada(_pergId));

                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, perguntaFragment);
                    // transaction.addToBackStack("perguntaFragment");
                    transaction.commit();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });


        return milkshake;
    }

    @Override
    public void onAjuda_50(Boolean _ajuda) {
        this.setAjuda_50(_ajuda);
    }

    @Override
    public void onAjuda_Tlf(Boolean _ajuda) {
        this.setAjuda_Tlf(_ajuda);
    }

    @Override
    public void onAjuda_Publico(Boolean _ajuda) {
        this.setAjuda_Publico(_ajuda);
    }

    public boolean isAjuda_50() {
        return ajuda_50;
    }

    public void setAjuda_50(boolean ajuda_50) {
        this.ajuda_50 = ajuda_50;
    }

    public boolean isAjuda_Publico() {
        return ajuda_Publico;
    }

    public void setAjuda_Publico(boolean ajuda_Publico) {
        this.ajuda_Publico = ajuda_Publico;
    }

    public boolean isAjuda_Tlf() {
        return ajuda_Tlf;
    }

    public void setAjuda_Tlf(boolean ajuda_Tlf) {
        this.ajuda_Tlf = ajuda_Tlf;
    }
}

