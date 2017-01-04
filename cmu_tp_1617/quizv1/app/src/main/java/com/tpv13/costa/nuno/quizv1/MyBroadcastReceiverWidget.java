package com.tpv13.costa.nuno.quizv1;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
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
    private MyDbHelper_game dbHelper;


    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        ComponentName thisWidget = new ComponentName(context, MyBroadcastReceiverWidget.class);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

        dbHelper = new MyDbHelper_game(context);

        perguLS=new ArrayList<Pergunta>();

        carregarLstTesteWidget();

        if(intent.hasExtra(MainActivity.INTENT_MESSAGE_EXTRA))
            //perguLS=new ArrayList<Pergunta>();
//            tmp = intent.getStringExtra(MainActivity.INTENT_MESSAGE_EXTRA);
//            perguLS= (ArrayList<Pergunta>) intent.getSerializableExtra("Pergunta");


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


    private void carregarLstTesteWidget(){
        Pergunta p=null ;
        ArrayList<Resposta> r=new ArrayList<>();


        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c= db.rawQuery("SELECT * FROM Perguntas",null);
        Cursor d;
        boolean certa;

        try
        {
            if (c.getCount()>0 ) {
                c.moveToFirst();
                // Loop through all Results
                do {
                    d=db.rawQuery("SELECT * FROM Respostas WHERE Perguntas_Id=" +  c.getInt(0) ,null);
                    if (d.getCount()>0 ) {
                        d.moveToFirst();

                        do {

                            if (d.getString(3).equals("S")) {
                                certa = true;
                            } else {
                                certa = false;
                            }

                            r.add(new Resposta(d.getInt(0), d.getInt(1), d.getString(2), certa));
                            //int _id, int _perguntaId, String _descricao, boolean _correta
                        } while (d.moveToNext());

                        p = new Pergunta(c.getInt(0), c.getInt(1),c.getString(3), c.getInt(4), c.getInt(2),r );
                        //                    int _id, int _nivelID, String _pergunta, int _pontucao,int _categoria, ArrayList<Resposta> _resposta

                        perguLS.add(p);
                    }
                    d.close();
                }while(c.moveToNext());

            }

            c.close();
            db.close();

        }
        catch(Exception e) {
            Log.e("Error", "Error", e);
            throw e;
        }

    }

}

