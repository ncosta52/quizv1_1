package com.tpv13.costa.nuno.quizv1;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Nuno on 04-01-2017.
 */

public class AjudaTelefone_Dialog extends Dialog implements View.OnClickListener {
    private Button bt_ok;
    private TextView tv_ajuda;
    private Pergunta pergun;

    private String[] ajudas=new String[3];
    private List<String> rspDisponiveis;

    public AjudaTelefone_Dialog(Activity a, Pergunta per, List<String> _rspDisponiveis) {
        super(a);
        // TODO Auto-generated constructor stub
        this.pergun=per;
        this.rspDisponiveis=_rspDisponiveis;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_phone_help);

        bt_ok=(Button) findViewById(R.id.btn_ok_phoneHelp );
        tv_ajuda=(TextView) findViewById(R.id.tv_ajudaPhone);

        bt_ok.setOnClickListener(this);

        ajudas[0]= getContext().getString(R.string.achoQue);
        ajudas[1]= getContext().getString(R.string.rspCerta_e);
        ajudas[2]= getContext().getString(R.string.naoSeiRsp);

        sortearAjuda();

    }

    @Override
    public void onClick(View view) {
       this.dismiss();
    }

    private void sortearAjuda(){
        Random randomGenerator=new Random();

        int tmpInd;

        tmpInd = randomGenerator.nextInt(3);

        switch (tmpInd){
            case 0:
                tv_ajuda.setText(ajudas[tmpInd] + "\n" + rspDisponiveis.get(randomGenerator.nextInt(rspDisponiveis.size())));
                break;
            case 1:
                for (int i=0;i<4;i++){
                    if (pergun.getRespostaByIndex(i).isCorreta() ){
                        for (String r : rspDisponiveis){
                            if (r.substring(3).equals(pergun.getRespostaByIndex(i).getDescricao())){
                                tv_ajuda.setText(ajudas[tmpInd] + "\n" + r);
                            }
                        }
                        break;
                    }
                }
                break;
            case 2:
                tv_ajuda.setText(ajudas[tmpInd]);
                break;
        }



    }
}
