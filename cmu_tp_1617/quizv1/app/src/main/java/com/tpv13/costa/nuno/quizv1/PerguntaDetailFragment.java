package com.tpv13.costa.nuno.quizv1;

import android.app.Activity;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class PerguntaDetailFragment extends Fragment implements View.OnClickListener  {
    private OnRspSelecionada mListenerRsp;

    private TextView tvPergunta;
    private Button btnA, btnB, btnC, btnD;//, btn_valSeg;
    private Pergunta apresPergunta;

//    private MyDbHelper_game dbHelper=new MyDbHelper_game(this.getContext());;
//    private Random randomGenerator=new Random();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View mContentView = inflater.inflate(R.layout.fragment_pergunta, container, false);

        tvPergunta = (TextView) mContentView.findViewById(R.id.tv_pergunta);
        btnA= (Button) mContentView.findViewById(R.id.bt_respostaA);
        btnB= (Button) mContentView.findViewById(R.id.bt_respostaB);
        btnC= (Button) mContentView.findViewById(R.id.bt_respostaC);
        btnD= (Button) mContentView.findViewById(R.id.bt_respostaD);

        btnA.setOnClickListener(this);
        btnB.setOnClickListener(this);
        btnC.setOnClickListener(this);
        btnD.setOnClickListener(this);

        return mContentView;
    }

    @Override
    public void onResume() {
        super.onResume();
        updatePergunta();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mListenerRsp = (OnRspSelecionada) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnNewsSelectedListener");
        }
    }

    public void setPergunta(Pergunta _pergunta) {
        this.apresPergunta = _pergunta;
//       dbHelper = new MyDbHelper_game(this.getContext());
        updatePergunta();
    }

    public void updatePergunta() {

        if (apresPergunta != null && tvPergunta!= null && btnA != null && btnB != null && btnC != null && btnD != null) {
            tvPergunta.setText(apresPergunta.getPergunta());
            btnA.setText(apresPergunta.getRespostaByIndex(0).getDescricao());
            btnA.setTag("" + apresPergunta.getRespostaByIndex(0).isCorreta());

            btnB.setText(apresPergunta.getRespostaByIndex(1).getDescricao());
            btnB.setTag("" + apresPergunta.getRespostaByIndex(1).isCorreta());

            btnC.setText(apresPergunta.getRespostaByIndex(2).getDescricao());
            btnC.setTag("" + apresPergunta.getRespostaByIndex(2).isCorreta());

            btnD.setText(apresPergunta.getRespostaByIndex(3).getDescricao());
            btnD.setTag("" + apresPergunta.getRespostaByIndex(3).isCorreta());


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


    @Override
    public void onClick(View view) {

        try{
            if (view.getTag().equals("")==false) {
                switch (view.getId()) {
                    case R.id.bt_respostaA: //error
                        setRspSelecionada_frag(0);
                        break;
                    case R.id.bt_respostaB: //error
                        setRspSelecionada_frag(1);
                        break;
                    case R.id.bt_respostaC: //error
                        setRspSelecionada_frag(2);
                        break;
                    case R.id.bt_respostaD: //error
                        setRspSelecionada_frag(3);
                        break;
                    default:
                        break;
                }
            }

        }catch (Exception e )
        {
            throw e;
        }
    }

    public void setRspSelecionada_frag(final int _rspSelecionada) {

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

            mListenerRsp.onRsp( this.apresPergunta.getRespostaByIndex(_rspSelecionada).isCorreta());
        }

    }


}


