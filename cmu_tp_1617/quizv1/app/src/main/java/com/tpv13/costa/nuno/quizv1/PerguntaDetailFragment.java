package com.tpv13.costa.nuno.quizv1;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class PerguntaDetailFragment extends Fragment {

private String nivel;
    private TextView tvPergunta;
    private Button btnA, btnB, btnC, btnD;//, btn_valSeg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View mContentView = inflater.inflate(R.layout.fragment_pergunta, container, false);

        tvPergunta = (TextView) mContentView.findViewById(R.id.tv_pergunta);

        return mContentView;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateLevel();
    }

    public void setNivel(String n) {
        this.nivel = n;
        updateLevel();
    }

    public void updateLevel() {


//        if(txtPessoaIdade != null && txtPessoaNome != null && mpessoas!= null) {
//            txtPessoaIdade.setText(mpessoas.getIdade() + "");
//            txtPessoaNome.setText(mpessoas.getNome());
////            txtPersonLastname.setText(mPerson.getLastName());
//        }
    }
}


