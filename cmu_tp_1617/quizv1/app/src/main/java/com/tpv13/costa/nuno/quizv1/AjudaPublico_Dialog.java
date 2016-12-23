package com.tpv13.costa.nuno.quizv1;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by Nuno on 22-12-2016.
 */

public class AjudaPublico_Dialog extends Dialog implements View.OnClickListener {

    private Activity c;
    private Dialog d;
    private Button ok;

    private ProgressBar rspA, rspB, rspC, rspD;
    private TextView tv_A, tv_B, tv_C, tv_D;

    private boolean isA_enable, isB_enable, isC_enable, isD_enable;


    public AjudaPublico_Dialog(Activity a, boolean _isA_enable, boolean _isB_enable, boolean _isC_enable, boolean _isD_enable) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.isA_enable=_isA_enable;
        this.isB_enable = _isB_enable;
        this.isC_enable=_isC_enable;
        this.isD_enable=_isD_enable;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_public_help);

        rspA=(ProgressBar) findViewById(R.id.pb_ajudaA);
        rspB=(ProgressBar) findViewById(R.id.pb_ajudaB);
        rspC=(ProgressBar) findViewById(R.id.pb_ajudaC);
        rspD=(ProgressBar) findViewById(R.id.pb_ajudaD);

        tv_A=(TextView) findViewById(R.id.tv_ajudaA);
        tv_B=(TextView) findViewById(R.id.tv_ajudaB);
        tv_C=(TextView) findViewById(R.id.tv_ajudaC);
        tv_D=(TextView) findViewById(R.id.tv_ajudaD);

        inicializarBarras();

        ok = (Button) findViewById(R.id.btn_ok_publicoHelp);
        ok.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        dismiss();
    }

    private void  inicializarBarras(){
        Integer ajudaDisp=100, ajuA=0, ajuB=0, ajuC=0, ajuD=0, tmp=0;

        Random randomGenerator=new Random();

        do{
            if (isA_enable) {

                tmp=randomGenerator.nextInt(ajudaDisp);
                ajuA = ajuA + tmp;
                ajudaDisp = ajudaDisp - tmp;

                if (ajudaDisp <5)
                {
                    ajuA = ajuA + ajudaDisp;
                    break;
                }
            }
            else {ajuA=0;}

            if (isB_enable){
                tmp=randomGenerator.nextInt(ajudaDisp);
                ajuB= ajuB+tmp;
                ajudaDisp=ajudaDisp-tmp;

                if (ajudaDisp <5)
                {
                    ajuB = ajuB + ajudaDisp;
                    break;
                }
            }
            else {ajuB=0;}

            if (isC_enable){
                tmp=randomGenerator.nextInt(ajudaDisp);
                ajuC= ajuC+tmp;
                ajudaDisp=ajudaDisp-tmp;

                if (ajudaDisp <5)
                {
                    ajuC = ajuC + ajudaDisp;
                    break;
                }
            }
            else {ajuC=0;}

            if (isD_enable){
                tmp=randomGenerator.nextInt(ajudaDisp);
                ajuD= ajuD+tmp;
                ajudaDisp=ajudaDisp-tmp;

                if (ajudaDisp <5)
                {
                    ajuD = ajuD + ajudaDisp;
                    break;
                }
            }
            else {ajuD=0;}
        }while (ajudaDisp>0);




        rspA.setProgress(ajuA);
        tv_A.setText(ajuA + "%");

        rspB.setProgress(ajuB);
        tv_B.setText(ajuB + "%");

        rspC.setProgress(ajuC);
        tv_C.setText(ajuC + "%");

        rspD.setProgress(ajuD);
        tv_D.setText(ajuD + "%");

    }


}
