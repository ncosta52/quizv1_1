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

//package pt.ipp.estgf.cmu.widgetproject;

import com.tpv13.costa.nuno.quizv1.R;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import java.util.ArrayList;

public class MyBroadcastReceiverWidget extends AppWidgetProvider {

    private String lastAction = "No action";
    private String pergunta ;


    @Override
    public void onReceive(Context context, Intent intent) {

        ComponentName thisWidget = new ComponentName(context, MyBroadcastReceiverWidget.class);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

        if(intent.hasExtra(MainActivity.INTENT_MESSAGE_EXTRA))
            pergunta = intent.getStringExtra(MainActivity.INTENT_MESSAGE_EXTRA);

        lastAction = intent.getAction();

        // executa onUpdate sempre que recebe um Intent neste caso trata-se de um Intents do tipo
        onUpdate(context, appWidgetManager, appWidgetManager.getAppWidgetIds(thisWidget));

        super.onReceive(context, intent);
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
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

}

