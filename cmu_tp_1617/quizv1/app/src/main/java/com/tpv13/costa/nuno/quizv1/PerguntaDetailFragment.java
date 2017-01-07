package com.tpv13.costa.nuno.quizv1;

import android.app.Activity;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PerguntaDetailFragment extends Fragment implements View.OnClickListener  {
    private OnRspSelecionada mListenerRsp;
    private OnAjudasSelectListener mListanerAjudas;

//    private static int animacaoCorretaTime=500;
//    private static int animacaoErradaTime=600;

    private TextView tvPergunta;
    private Button btnA, btnB, btnC, btnD;//, btn_valSeg;
    private Pergunta apresPergunta;
    private boolean ajuda_50, ajuda_Tlf, ajuda_Pub;

    private ImageButton btn_ajuda_50, btn_ajudaTlf, btn_ajudaPublico;

    private AjudaPublico_Dialog ajudaPublico_dial;
    private AjudaTelefone_Dialog ajudaTelefone_dial;


    private Animation myAnim;

    MediaPlayer soundResponse;



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

        btn_ajuda_50= (ImageButton) mContentView.findViewById(R.id.btn_ajuda50);
        btn_ajudaTlf= (ImageButton) mContentView.findViewById(R.id.btn_ajudaTlf);
        btn_ajudaPublico= (ImageButton) mContentView.findViewById(R.id.btn_ajudaPublic);

        btnA.setOnClickListener(this);
        btnB.setOnClickListener(this);
        btnC.setOnClickListener(this);
        btnD.setOnClickListener(this);

        btn_ajuda_50.setOnClickListener(this);
        btn_ajudaTlf.setOnClickListener(this);
        btn_ajudaPublico.setOnClickListener(this);




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
            mListanerAjudas=(OnAjudasSelectListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnNewsSelectedListener");
        }
    }

    public void setPergunta(Pergunta _pergunta) {
        this.setApresPergunta(_pergunta);
//       dbHelper = new MyDbHelper_game(this.getContext());
        updatePergunta();
    }

    public void updatePergunta() {
        String tmp;

        if (getApresPergunta() != null && tvPergunta!= null && btnA != null && btnB != null && btnC != null && btnD != null) {
            tvPergunta.setText(getApresPergunta().getPergunta());
            tmp="<b>A. </b> " + getApresPergunta().getRespostaByIndex(0).getDescricao();
            btnA.setText(Html.fromHtml(tmp));
            btnA.setTag("" + getApresPergunta().getRespostaByIndex(0).isCorreta());

            tmp="<b>B. </b> " + getApresPergunta().getRespostaByIndex(1).getDescricao();
            btnB.setText(Html.fromHtml(tmp));
            btnB.setTag("" + getApresPergunta().getRespostaByIndex(1).isCorreta());

            tmp="<b>C. </b> " + getApresPergunta().getRespostaByIndex(2).getDescricao();
            btnC.setText(Html.fromHtml(tmp));
            btnC.setTag("" + getApresPergunta().getRespostaByIndex(2).isCorreta());

            tmp="<b>D. </b> " + getApresPergunta().getRespostaByIndex(3).getDescricao();
            btnD.setText(Html.fromHtml(tmp));
            btnD.setTag("" + getApresPergunta().getRespostaByIndex(3).isCorreta());


            btnA.setBackgroundResource(android.R.drawable.btn_default);
            btnB.setBackgroundResource(android.R.drawable.btn_default);
            btnC.setBackgroundResource(android.R.drawable.btn_default);
            btnD.setBackgroundResource(android.R.drawable.btn_default);

            btnA.setClickable(true);
            btnB.setClickable(true);
            btnC.setClickable(true);
            btnD.setClickable(true);

        }

        if (btn_ajuda_50 != null && btn_ajudaPublico != null && btn_ajudaTlf != null){
            this.btn_ajuda_50.setEnabled(!this.ajuda_50);
            this.btn_ajudaPublico.setEnabled(!this.ajuda_Pub);
            this.btn_ajudaTlf.setEnabled(!this.ajuda_Tlf);
        }




    }


    @Override
    public void onClick(View view) {

        try{
            if (view.getTag() !=null) {
                if (view.getTag().equals("") == false) {
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
            }
            else {
                switch (view.getId()) {
                    case R.id.btn_ajuda50: //error
                        mListanerAjudas.onAjuda_50(true);
                        this.btn_ajuda_50.setEnabled(false);
                        sortear_2_erradas();
//                        Toast.makeText(this.getContext(), "btn_ajuda50", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.btn_ajudaPublic: //error
                        mListanerAjudas.onAjuda_Publico(true);
                        this.btn_ajudaPublico.setEnabled(false);

                        ajudaPublico_dial=new AjudaPublico_Dialog(this.getActivity(),
                                                                    this.btnA.isEnabled(),
                                                                    this.btnB.isEnabled(),
                                                                    this.btnC.isEnabled(),
                                                                    this.btnD.isEnabled());
                        ajudaPublico_dial.show();

//                        Toast.makeText(this.getContext(), "btn_ajudaPublic", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.btn_ajudaTlf: //error
                        mListanerAjudas.onAjuda_Tlf(true);
                        this.btn_ajudaTlf.setEnabled(false);
                        List<String> rspDisp=new ArrayList<String>();

                        if (this.btnA.isEnabled()){
                            rspDisp.add(btnA.getText().toString());
                        }

                        if (this.btnB.isEnabled()){
                            rspDisp.add(btnB.getText().toString());
                        }

                        if (this.btnC.isEnabled()){
                            rspDisp.add(btnC.getText().toString());
                        }

                        if (this.btnD.isEnabled()){
                            rspDisp.add(btnD.getText().toString());
                        }

                        ajudaTelefone_dial= new AjudaTelefone_Dialog(this.getActivity(),getApresPergunta(),rspDisp);
                        ajudaTelefone_dial.show();
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

    public void sortear_2_erradas()
    {
        ArrayList<Integer> ajudasD=new ArrayList<>();
        Integer rspRem=0, rspTmp;
        Random randomGenerator=new Random();

        ajudasD.add(0);
        ajudasD.add(1);
        ajudasD.add(2);
        ajudasD.add(3);

        do{
            rspTmp= randomGenerator.nextInt(ajudasD.size());

            switch (ajudasD.get(rspTmp)) {
                case 0: //error
                    if(this.btnA.getTag().equals("false")){
                        this.btnA.setEnabled(false);
                        rspRem++;
                    }
                    break;
                case 1: //error
                    if(this.btnB.getTag().equals("false")){
                        this.btnB.setEnabled(false);
                        rspRem++;
                    }
                    break;
                case 2: //error
                    if(this.btnC.getTag().equals("false")){
                        this.btnC.setEnabled(false);
                        rspRem++;
                    }
                    break;
                case 3: //error
                    if(this.btnD.getTag().equals("false")){
                        this.btnD.setEnabled(false);
                        rspRem++;
                    }
                    break;
                default:
                    break;
            }
            ajudasD.remove(rspTmp);

        } while (rspRem<2);
    }
//    public void setRspSelecionada(final int _rspSelecionada) {
//
//        if (_rspSelecionada>-1 && _rspSelecionada<6) {
//
//            switch (_rspSelecionada) {
//                case 0: //error
//                    btnA.setBackgroundColor(getResources().getColor(R.color.rspEscolhidaColor));
//                    break;
//                case 1: //error
//                    btnB.setBackgroundColor(getResources().getColor(R.color.rspEscolhidaColor));
//                    break;
//                case 2: //error
//                    btnC.setBackgroundColor(getResources().getColor(R.color.rspEscolhidaColor));
//                    break;
//                case 3: //error
//                    btnD.setBackgroundColor(getResources().getColor(R.color.rspEscolhidaColor));
//                    break;
//                default:
//                    break;
//            }
//
//            mListenerRsp.onRsp( this.getApresPergunta().getRespostaByIndex(_rspSelecionada).isCorreta());
//        }
//
//    }

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

//            IsRspFinal_Dialog isRspFinal_dialog=new IsRspFinal_Dialog(this);
//            isRspFinal_dialog.show();
//
            final Resposta tmp_apresPergunta= this.getApresPergunta().getRespostaByIndex(_rspSelecionada);

            AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
            builder.setMessage(R.string.rsp_Final);
            //builder.setTitle("Responder");

            builder.setPositiveButton(R.string.rsp_Sim, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {


                    try {

                        if (tmp_apresPergunta.isCorreta()){
                            pintarCorreta(_rspSelecionada);

                        }
                        else{
                            pintarErrada(_rspSelecionada);
                        }
//


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
                            //_rspSelecionada = -1;


                        }
                    }

            );

            AlertDialog mDialog = builder.create();
            mDialog.show();

        }



    }

    private void pintarCorreta(int _rspDada){
        myAnim =allAnimStuff(getResources().getInteger(R.integer.animacaoCorretaTime), true);
        soundResponse= MediaPlayer.create(this.getActivity(), R.raw.feedback_positivo);
        soundResponse.start();
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
        myAnim =allAnimStuff(getResources().getInteger(R.integer.animacaoErradaTime), false);

        soundResponse= MediaPlayer.create(this.getActivity(), R.raw.feedback_negativo);
        soundResponse.start();

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
            if (this.getApresPergunta().getRespostaByIndex(i).isCorreta()){
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

    private Animation allAnimStuff(final long duration, final boolean _isCorrecta) {

        Animation milkshake = AnimationUtils.loadAnimation(this.getContext(), R.anim.anim_alpha);
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
                soundResponse.stop();
                mListenerRsp.onRsp(_isCorrecta);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });


        return milkshake;
    }

    public Pergunta getApresPergunta() {
        return apresPergunta;
    }

    public void setApresPergunta(Pergunta apresPergunta) {
        this.apresPergunta = apresPergunta;
    }

    public void setAjudasEstado(boolean _ajuda_50, boolean _ajuda_Tlf, boolean _ajuda_Pub)
    {
        this.ajuda_50=_ajuda_50;
        this.ajuda_Tlf=_ajuda_Tlf;
        this.ajuda_Pub=_ajuda_Pub;
    }
}


