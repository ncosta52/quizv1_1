package com.tpv13.costa.nuno.quizv1;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

/**
 * Created by Nuno on 11-12-2016.
 */

public class IsRspFinal_Dialog extends Dialog implements android.view.View.OnClickListener {

    private static int BLOQUEAR_RSP_DADA=0;
    private static int CANCELAR_RSP_DADA=1;
    private static int DESISTIR=2;

    IsRSpFinal_Dialog_listener mListener;

    private Activity activ;
    private String valorPremio;
    private Dialog d;
    private Button bt_bloquear, bt_pensarMelhor, bt_desistir;

        public IsRspFinal_Dialog(Activity a, String _valorPremio) {
            super(a);
            // TODO Auto-generated constructor stub
            this.activ = a;
            this.valorPremio=_valorPremio;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.is_rsp_final_dialog);

            bt_bloquear = (Button) findViewById(R.id.btn_bloquear);
            bt_pensarMelhor = (Button) findViewById(R.id.btn_pensarM);
            bt_desistir=(Button) findViewById(R.id.btn_desistir);

            bt_bloquear.setOnClickListener(this);
            bt_pensarMelhor.setOnClickListener(this);
            bt_desistir.setOnClickListener(this);

            String strnuno = activ.getResources().getString(R.string.desistir,  this.valorPremio);
            this.bt_desistir.setText(strnuno);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_bloquear:
                    mListener.userSelectedAValue(this.BLOQUEAR_RSP_DADA);
                    dismiss();
                    break;
                case R.id.btn_pensarM:
                    mListener.userSelectedAValue(this.CANCELAR_RSP_DADA);
                    dismiss();
                    break;
                case R.id.btn_desistir:
                    mListener.userSelectedAValue(this.DESISTIR);
                    break;
                default:
                    break;
            }

            dismiss();
        }

//    public void onClick(View v)
//    {
//        /** When OK Button is clicked, dismiss the dialog */
//        if (v == okButton)
//        {
//            listener.userSelectedAValue(selected_value);
//            // listener is object of your MyDialogListener, which you have set from
//            // Activity.
//            dismiss();
//        }
//    }



    public IsRSpFinal_Dialog_listener getmListener() {
        return mListener;
    }

    public void setmListener(IsRSpFinal_Dialog_listener mListener) {
        this.mListener = mListener;
    }
}

