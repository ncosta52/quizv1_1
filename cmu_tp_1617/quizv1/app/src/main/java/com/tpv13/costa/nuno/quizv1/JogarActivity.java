package com.tpv13.costa.nuno.quizv1;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;


public class JogarActivity extends Activity implements View.OnClickListener{


    //private PerguntaFragment mPerguntaFragment;
    private Random randomGenerator=new Random();
    private MyDbHelper_game dbHelper;
    private Pergunta apresPergunta;
    private Button btnA, btnB, btnC, btnD;//, btn_valSeg;
    private TextView tvPergunta;
    private ArrayList <Jogo_RespostasCertas> rspCertas;
    private ArrayList<Integer> rspCertasCategorias=new ArrayList<>();
    private int pontuacaoTotal;
    private int rspSelecionada;

    private Animation myAnim;

//    private static final int savePontuacaoFinal=0;
//    private static final ArrayList<Integer> savePerguntasId=null;

    private ArrayList<Integer> perguntasId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogar);

        //nivel=getIntent().getIntExtra("Nivel",0);
        perguntasId= getIntent().getIntegerArrayListExtra("Perguntas");

        dbHelper = new MyDbHelper_game(this);

        rspCertas=new ArrayList<>();
        pontuacaoTotal=0;

        tvPergunta = (TextView) findViewById(R.id.tv_pergunta);

        btnA = (Button) findViewById(R.id.bt_respostaA);
        btnA.setOnClickListener(this);

        btnB = (Button) findViewById(R.id.bt_respostaB);
        btnB.setOnClickListener(this);

        btnC = (Button) findViewById(R.id.bt_respostaC);
        btnC.setOnClickListener(this);

        btnD = (Button) findViewById(R.id.bt_respostaD);
        btnD.setOnClickListener(this);

            sortearPergunta(perguntasId);


    }

    @Override
    public void onBackPressed() {
        // do something on back.
        android.os.Process.killProcess(android.os.Process.myPid());
        return;
    }


//    @Override
//    protected void onSaveInstanceState (Bundle outState) {
//        super.onSaveInstanceState(outState);
//        //outState.putCharSequence(KEY_TEXT_VALUE, mTextView.getText());
//    }


    private void sortearPergunta(ArrayList<Integer> lst){

        if (getRspSelecionada()==6){
            callActividadeResultado("Resposta Errada\nAcabou de perder.");
            return;
        }
        else {

            Pergunta res = null;
            setRspSelecionada(-1);


            if (lst.size() == 0) {
//            Toast.makeText(this, "Respondeu  todas as perguntas. É UM VENCEDOR!!!", Toast.LENGTH_LONG).show();

                //GANHOU
                //CHAMAR activity de vencedor
                //android.os.Process.killProcess(android.os.Process.myPid());
                callActividadeResultado("Respondeu todas as perguntas.\n\n" +
                        getResources().getString(R.string.vencedor_Str)); //"----  É UM VENCEDOR!!!  ----"
            }

            int index = randomGenerator.nextInt(lst.size());
            int index_rsp;

            ArrayList<Resposta> rspLstPesq, rspLst = new ArrayList<>();
            rspLstPesq = selecionarRespostas(lst.get(index));

            String[] columnsPerguntasSelect = {"Id", "Pergunta", "Pontuacao", "Niveis_Id", "Categorias_Id"};
            String WHERE = "Id='" + lst.get(index) + "'";

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

                        apresPergunta = new Pergunta(c.getInt(0), c.getInt(3), c.getString(1), c.getInt(2), c.getInt(4), rspLst);
                        res = apresPergunta;

                    } while (c.moveToNext());
                }

                c.close();
                db.close();

            } catch (Exception e) {
                Log.e("Error", "Error", e);
                Toast.makeText(this, "Erro sortearPergunta: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                throw e;
            }

            if (res != null) {
                tvPergunta.setText(res.getPergunta());
                btnA.setText(res.getRespostaByIndex(0).getDescricao());
                btnA.setTag("" + res.getRespostaByIndex(0).isCorreta());

                btnB.setText(res.getRespostaByIndex(1).getDescricao());
                btnB.setTag("" + res.getRespostaByIndex(1).isCorreta());

                btnC.setText(res.getRespostaByIndex(2).getDescricao());
                btnC.setTag("" + res.getRespostaByIndex(2).isCorreta());

                btnD.setText(res.getRespostaByIndex(3).getDescricao());
                btnD.setTag("" + res.getRespostaByIndex(3).isCorreta());


                btnA.setBackgroundResource(android.R.drawable.btn_default);
                btnB.setBackgroundResource(android.R.drawable.btn_default);
                btnC.setBackgroundResource(android.R.drawable.btn_default);
                btnD.setBackgroundResource(android.R.drawable.btn_default);

                btnA.setClickable(true);
                btnB.setClickable(true);
                btnC.setClickable(true);
                btnD.setClickable(true);

            }
        }


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

    @Override
    public void onClick(View view) {
        try{
            if (view.getTag().equals("")==false) {
                switch (view.getId()) {
                    case R.id.bt_respostaA: //error
                        setRspSelecionada(0);
                        break;
                    case R.id.bt_respostaB: //error
                        setRspSelecionada(1);
                        break;
                    case R.id.bt_respostaC: //error
                        setRspSelecionada(2);
                        break;
                    case R.id.bt_respostaD: //error
                        setRspSelecionada(3);
                        break;
                    default:
                        break;
                }
            }

        }catch (Exception e )
        {
            Toast.makeText(this, "Erro onClick: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }

    private void isRespostaCerta(int ind){

        try{
            if (this.apresPergunta!=null) {
                if (this.apresPergunta.getRespostaByIndex(ind).isCorreta()) {
                    int in = this.perguntasId.indexOf(this.apresPergunta.getId());
                    this.perguntasId.remove(in);

                    this.rspCertas.add(new Jogo_RespostasCertas(this.apresPergunta.getCategoriaId(),this.apresPergunta.getPontuacao(),this));
                    this.rspCertasCategorias.add(this.apresPergunta.getCategoriaId());
                    pontuacaoTotal=pontuacaoTotal+this.apresPergunta.getPontuacao();

                    //Toast.makeText(this, "Resposta Certa", Toast.LENGTH_SHORT).show();
                    pintarCorreta(ind);
//                    setRspSelecionada(5);

                } else {
//                    Toast.makeText(this, "Resposta Errada", Toast.LENGTH_SHORT).show();

                    pintarErrada(ind);

                    //perdeu
                    //chamar activity com animação de perdedor
                    //android.os.Process.killProcess(android.os.Process.myPid());
                }
                //this.apresPergunta=null;
            }
        }catch (Exception e)
        {
            Toast.makeText(this, "Erro respostaCerta: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            throw e;
        }
    }

    private void callActividadeResultado(String _frase){
        try{
            Intent i = new Intent(this, ResultadoActivity.class);
//                i.putExtra("Nivel",  mNivel.get(mAdapter_niveis.getSelectedIndex()).getId());
//                i.putExtra("Categorias",  mAdapter_catgs.getCheckedpositions());
            i.putExtra("ResultadosLst", rspCertas);
            i.putExtra("RspCertasCategorias", rspCertasCategorias);
            i.putExtra("Pontuacao",this.pontuacaoTotal);
            i.putExtra("KillProcess", android.os.Process.myPid() );
            i.putExtra("Frase",_frase );

            startActivity(i);
            android.os.Process.killProcess(android.os.Process.myPid());
        }catch (Exception e)
        {
            Toast.makeText(this, "Erro callActividadeResultado: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            throw e;
        }


    }

    public int getRspSelecionada() {
        return rspSelecionada;
    }

    public void setRspSelecionada(final int _rspSelecionada) {

        if (_rspSelecionada>-1 && _rspSelecionada<6) {

            switch (_rspSelecionada) {
                case 0: //error
                    btnA.setBackgroundColor(getResources().getColor(R.color.rspEscolhidaColor));
                    break;
                case 1: //error
                    btnB.setBackgroundColor(getResources().getColor(R.color.rspEscolhidaColor));
                    break;
                case 2: //error
                    btnC.setBackgroundColor(getResources().getColor(R.color.rspEscolhidaColor));
                    break;
                case 3: //error
                    btnD.setBackgroundColor(getResources().getColor(R.color.rspEscolhidaColor));
                    break;
                default:
                    break;
            }

//            IsRspFinal_Dialog isRspFinal_dialog=new IsRspFinal_Dialog(this);
//            isRspFinal_dialog.show();
//

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.rsp_Final);
            //builder.setTitle("Responder");

            builder.setPositiveButton(R.string.rsp_Sim, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    try {

                        rspSelecionada = _rspSelecionada;
                        isRespostaCerta(_rspSelecionada);


                    } catch (Exception e) {
                        throw e;
                    }


                }
            });

            builder.setNegativeButton(R.string.rsp_Nao, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    btnA.setBackgroundResource(android.R.drawable.btn_default);
                    btnB.setBackgroundResource(android.R.drawable.btn_default);
                    btnC.setBackgroundResource(android.R.drawable.btn_default);
                    btnD.setBackgroundResource(android.R.drawable.btn_default);
                    rspSelecionada = -1;
                }
            });

            AlertDialog mDialog = builder.create();
            mDialog.show();
        }
        else{


            btnA.setBackgroundResource(android.R.drawable.btn_default);
            btnB.setBackgroundResource(android.R.drawable.btn_default);
            btnC.setBackgroundResource(android.R.drawable.btn_default);
            btnD.setBackgroundResource(android.R.drawable.btn_default);
            if (_rspSelecionada==6)
            {
                rspSelecionada=6;
                sortearPergunta(perguntasId);
            }
        }



    }

    private void pintarCorreta(int _rspDada){
        myAnim =allAnimStuff(getResources().getInteger(R.integer.animacaoCorretaTime));

        switch (_rspDada) {
            case 0: //error
                btnA.setBackgroundColor(getResources().getColor(R.color.rspCertaColor));
                btnA.startAnimation(myAnim);
                break;
            case 1: //error
                btnB.setBackgroundColor(getResources().getColor(R.color.rspCertaColor));
                btnB.startAnimation(myAnim);
                break;
            case 2: //error
                btnC.setBackgroundColor(getResources().getColor(R.color.rspCertaColor));
                btnC.startAnimation(myAnim);
                break;
            case 3: //error
                btnD.setBackgroundColor(getResources().getColor(R.color.rspCertaColor));
                btnD.startAnimation(myAnim);
                break;
            default:
                break;
        }
    }

    private void pintarErrada(int _rspDada){
        myAnim =allAnimStuff(getResources().getInteger(R.integer.animacaoErradaTime));

        switch (_rspDada) {
            case 0: //error
                btnA.setBackgroundColor(getResources().getColor(R.color.rspErradaColor));
                break;
            case 1: //error
                btnB.setBackgroundColor(getResources().getColor(R.color.rspErradaColor));
                break;
            case 2: //error
                btnC.setBackgroundColor(getResources().getColor(R.color.rspErradaColor));
                break;
            case 3: //error
                btnD.setBackgroundColor(getResources().getColor(R.color.rspErradaColor));
                break;
            default:
                break;
        }

        for (int i=0;i<4;i++){
            if (this.apresPergunta.getRespostaByIndex(i).isCorreta()){
                switch (i) {
                    case 0: //error
                        btnA.setBackgroundColor(getResources().getColor(R.color.rspCertaColor));
                        btnA.startAnimation(myAnim);
                        break;
                    case 1: //error
                        btnB.setBackgroundColor(getResources().getColor(R.color.rspCertaColor));
                        btnB.startAnimation(myAnim);
                        break;
                    case 2: //error
                        btnC.setBackgroundColor(getResources().getColor(R.color.rspCertaColor));
                        btnC.startAnimation(myAnim);
                        break;
                    case 3: //error
                        btnD.setBackgroundColor(getResources().getColor(R.color.rspCertaColor));
                        btnD.startAnimation(myAnim);
                        break;
                    default:
                        break;
                }
                i=4;
            }
            else{
                if (i!=_rspDada){
                   //btnD.setBackgroundResource(android.R.drawable.btn_default);
                    switch (i) {
                        case 0: //error
                            btnA.setBackgroundResource(android.R.drawable.btn_default);
                            break;
                        case 1: //error
                            btnB.setBackgroundResource(android.R.drawable.btn_default);
                            break;
                        case 2: //error
                            btnC.setBackgroundResource(android.R.drawable.btn_default);
                            break;
                        case 3: //error
                            btnD.setBackgroundResource(android.R.drawable.btn_default);
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }


    private Animation allAnimStuff(final long duration) {

        Animation milkshake =AnimationUtils.loadAnimation(this, R.anim.anim_alpha);
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
                if (duration==getResources().getInteger(R.integer.animacaoCorretaTime)) {

                    btnA.setClickable(true);
                    btnB.setClickable(true);
                    btnC.setClickable(true);
                    btnD.setClickable(true);
                    setRspSelecionada(-1);
                    sortearPergunta(perguntasId);
                }
                else
                {
//                    btnA.setClickable(false);
//                    btnB.setClickable(false);
//                    btnC.setClickable(false);
//                    btnD.setClickable(false);
                    setRspSelecionada(6);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });


        return milkshake;
    }
}
