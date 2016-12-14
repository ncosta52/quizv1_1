package com.tpv13.costa.nuno.quizv1;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
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

public class PerguntaDetailFragment extends Fragment  {


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



        return mContentView;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateLevel();
    }

    public void setPergunta(Pergunta _pergunta) {
        this.apresPergunta = _pergunta;
//       dbHelper = new MyDbHelper_game(this.getContext());
        updateLevel();
    }

    public void updateLevel() {
//        int index_rsp;
//        Pergunta res=null;
//
//        ArrayList<Resposta> rspLstPesq, rspLst = new ArrayList<>();
//        rspLstPesq = selecionarRespostas(this.perguntaID);
//
//        String[] columnsPerguntasSelect = {"Id", "Pergunta", "Pontuacao", "Niveis_Id", "Categorias_Id"};
//        String WHERE = "Id='" + this.perguntaID + "'";
//
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        //Cursor c = db.rawQuery("SELECT * FROM tblPessoas", null);
//        Cursor c = db.query(true, "Perguntas", columnsPerguntasSelect, WHERE, null, null, null, null, null);
//
//        try {
//            if (c.getCount() > 0) {
//                c.moveToFirst();
//
//                do {
//
//                    while (rspLstPesq.size() > 0) {
//                        index_rsp = randomGenerator.nextInt(rspLstPesq.size());
//                        rspLst.add(rspLstPesq.get(index_rsp));
//                        rspLstPesq.remove(index_rsp);
//                    }
//
//                    apresPergunta = new Pergunta(c.getInt(0), c.getInt(3), c.getString(1), c.getInt(2), c.getInt(4), rspLst);
//                    res = apresPergunta;
//
//                } while (c.moveToNext());
//            }
//
//            c.close();
//            db.close();
//
//        } catch (Exception e) {
//            Log.e("Error", "Error", e);
////            Toast.makeText(this, "Erro sortearPergunta: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//            throw e;
//        }

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


}


