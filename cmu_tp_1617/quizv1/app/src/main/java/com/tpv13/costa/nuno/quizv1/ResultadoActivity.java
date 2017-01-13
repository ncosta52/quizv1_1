package com.tpv13.costa.nuno.quizv1;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
//import android.support.annotation.RequiresApi;
import android.preference.PreferenceManager;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;

public class ResultadoActivity extends Activity {
    private MyDbHelper_game dbHelper;
    private SharedPreferences mSettings;

    private ArrayList<Jogo_RespostasCertas> rspCertas = new ArrayList<>();
    //private ArrayList<Integer> rspCatgs = new ArrayList<>();
    private int killProcess;
    private int _nivelID;


   // @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);

        this.rspCertas = (ArrayList<Jogo_RespostasCertas>) getIntent().getSerializableExtra("ResultadosLst");
        this.killProcess = getIntent().getIntExtra("KillProcess", 0);
        this._nivelID=getIntent().getIntExtra("NivelID",0);

        dbHelper = new MyDbHelper_game(this);
        mSettings = PreferenceManager.getDefaultSharedPreferences(this);

        TextView tmp = (TextView) findViewById(R.id.a);
        TextView tmp2 = (TextView) findViewById(R.id.fraseResultado);

        tmp2.setText("" + getIntent().getStringExtra("Frase") + "\n\nPontuação Total: " + getIntent().getIntExtra("Pontuacao",0));




//        tmp.setText("" + noDupSet.size());
        tmp.setText("");

    }

    private int userId()
    {
        int user_Id=0;
        Cursor d;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query;
        query = "SELECT * FROM Utilizadores WHERE Username='" + mSettings.getString("pref_currentUser", "") + "'";

        d = db.rawQuery(query, null);

        if (d.getCount() > 0) {
             do {
                 user_Id=d.getInt(0);
            } while (d.moveToNext());

        }

        d.close();
        db.close();

        return user_Id;
    }



}
