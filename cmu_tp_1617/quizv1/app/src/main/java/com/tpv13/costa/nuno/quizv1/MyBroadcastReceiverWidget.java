package com.tpv13.costa.nuno.quizv1;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import java.util.ArrayList;
import java.util.Random;

//package pt.ipp.estgf.cmu.widgetproject;

public class MyBroadcastReceiverWidget extends AppWidgetProvider {

    private String lastAction = "No action";
    private String pergunta, tmp ;
    private ArrayList<Pergunta> perguLS;//=new ArrayList<>();
    private Random randomGenerator=new Random();
    private int index;

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        ComponentName thisWidget = new ComponentName(context, MyBroadcastReceiverWidget.class);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        perguLS=new ArrayList<Pergunta>();
        if(intent.hasExtra(MainActivity.INTENT_MESSAGE_EXTRA))
            tmp = intent.getStringExtra(MainActivity.INTENT_MESSAGE_EXTRA);
            perguLS= (ArrayList<Pergunta>) intent.getSerializableExtra("Pergunta");


        lastAction = intent.getAction();

        // executa onUpdate sempre que recebe um Intent neste caso trata-se de um Intents do tipo
        onUpdate(context, appWidgetManager, appWidgetManager.getAppWidgetIds(thisWidget));


    }

//
//        ComponentName thisWidget = new ComponentName(context, MyBroadcastReceiverWidget.class);
//        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
//
//       // if(intent.hasExtra(MainActivity.INTENT_MESSAGE_EXTRA))
//            pergunta = intent.getStringExtra("Pergunta"); // .getSerializableExtra("Pergunta");
//        //lastAction = intent.getAction();
//
//        // executa onUpdate sempre que recebe um Intent neste caso trata-se de um Intents do tipo
//        onUpdate(context, appWidgetManager, appWidgetManager.getAppWidgetIds(thisWidget));
//
//        super.onReceive(context, intent);
//    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        if (perguLS.size()>0) {


            index = randomGenerator.nextInt(perguLS.size());

//        if (v.getId() == R.id.btnAction) {

            pergunta = perguLS.get(index).getPergunta();
            perguLS.remove(index);

        }

        // update a cada inst�ncia da widget
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            int appWidgetId = appWidgetIds[i];
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }

    }

    private void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        // cria um objecto RemoteViews com o layout da widget


        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        views.setTextViewText(R.id.txtPerguntaWidget , pergunta );
        views.setTextViewText(R.id.respA,perguLS.get(index).getRespostaByIndex(0).getDescricao());
        views.setTextViewText(R.id.respB,perguLS.get(index).getRespostaByIndex(1).getDescricao());
        views.setTextViewText(R.id.respC,perguLS.get(index).getRespostaByIndex(2).getDescricao());
        views.setTextViewText(R.id.respD,perguLS.get(index).getRespostaByIndex(3).getDescricao());
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

}

