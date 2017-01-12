package com.tpv13.costa.nuno.quizv1;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

public class GanhouActivity extends AppCompatActivity {

    private LinearLayout linearLayout ;
    private TextView tmpTextView;
    private String valorGanho;

    private int color;
    private Animation myAnim;
    private int cont=0;
    private Random rnd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ganhou);

        valorGanho=getIntent().getStringExtra("Valor");
        linearLayout = (LinearLayout)findViewById(R.id.activity_ganhou_linear);

        rnd = new Random();



        //for (int i=0; i<100; i++){
            tmpTextView=new TextView(this);
            tmpTextView.setText(valorGanho);
            //tmpTextView.setPadding(rnd.nextInt(10), rnd.nextInt(10), rnd.nextInt(10), rnd.nextInt(10));
            color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            tmpTextView.setTextColor(color);
            tmpTextView.setTextSize(rnd.nextInt(50));

            myAnim =allAnimStuffVenceu(250,this);
            tmpTextView.startAnimation(myAnim);

            linearLayout.addView(tmpTextView);
        //}


    }

    private Animation allAnimStuffVenceu(final long duration, final Context _context) {

        Animation milkshake = AnimationUtils.loadAnimation(this, R.anim.anim_translate);
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
                int tmpSize=0;

                if (cont<30){
                    tmpTextView=new TextView(_context);
                    tmpTextView.setText(valorGanho);
                    //tmpTextView.setPadding(rnd.nextInt(10), rnd.nextInt(10), rnd.nextInt(10), rnd.nextInt(10));
                    color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                    tmpTextView.setTextColor(color);

                    do{
                        tmpSize=rnd.nextInt(50);
                    } while (tmpSize<10);

                    tmpTextView.setTextSize(tmpSize);


//                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
////                    params.setMargins(10,10,10,10);
////                    tv1.setLayoutParams(params);
////                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, Gravity.RIGHT);
//
//                    int x, y, _x, _y;
//
//                    _x=rnd.nextInt(20);
//                    x=rnd.nextInt(20);
//                    _y=rnd.nextInt(20);
//                    y=rnd.nextInt(20);
//
//                    params.setMargins(_x,y,x,_y);
//                    tmpTextView.setLayoutParams(params);

                    myAnim =allAnimStuffVenceu(250,_context);
                    tmpTextView.startAnimation(myAnim);

                    linearLayout.addView(tmpTextView);
                    cont++;
                }
                else{
                    finish();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });


        return milkshake;
    }
}
