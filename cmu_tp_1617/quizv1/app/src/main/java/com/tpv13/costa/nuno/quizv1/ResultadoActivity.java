package com.tpv13.costa.nuno.quizv1;

import android.app.Activity;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
//import android.support.annotation.RequiresApi;
import android.preference.PreferenceManager;
import android.widget.TextView;

import java.util.ArrayList;

public class ResultadoActivity extends Activity {
    private MyDbHelper_game dbHelper;
    private SharedPreferences mSettings;
    public static final String TIMEOUT_PREFERENCE_time = "pref_timeoutC";

    private ArrayList<Jogo_RespostasCertas> rspCertas = new ArrayList<>();
    //private ArrayList<Integer> rspCatgs = new ArrayList<>();
    private int killProcess;
    private int _nivelID;
    private int _pontuacao;


   // @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);

        this.rspCertas = (ArrayList<Jogo_RespostasCertas>) getIntent().getSerializableExtra("ResultadosLst");
        this.setKillProcess(getIntent().getIntExtra("KillProcess", 0));

        this.set_nivelID(getIntent().getIntExtra("NivelID",0));
        this.set_pontuacao(getIntent().getIntExtra("Pontuacao",0));

        dbHelper = new MyDbHelper_game(this);
        mSettings = PreferenceManager.getDefaultSharedPreferences(this);

        if (this.rspCertas.size()>0) {
            inserir_Users_Ranking();
        }

        TextView tmp = (TextView) findViewById(R.id.a);
        TextView tmp2 = (TextView) findViewById(R.id.fraseResultado);

        tmp2.setText("" + getIntent().getStringExtra("Frase") + "\n\nPontuação Total: " + this.get_pontuacao());




//        tmp.setText("" + noDupSet.size());
        tmp.setText("");

    }

    private void inserir_Users_Ranking()
    {
        try{
            SQLiteDatabase db_inserir_Users_Ranking = dbHelper.getWritableDatabase();

            ContentValues users_Ranking_Nivel = new ContentValues();
            users_Ranking_Nivel.put("Utilizadores_Id", this.userId());
            users_Ranking_Nivel.put("Niveis_Id", this.get_nivelID());
            users_Ranking_Nivel.put("PontucaoTotal", this.get_pontuacao());
            users_Ranking_Nivel.put("RespostasDadasTotal", rspCertas.size());
            users_Ranking_Nivel.put("Tempo", Integer.parseInt(mSettings.getString(TIMEOUT_PREFERENCE_time,"6")));


            db_inserir_Users_Ranking.insert("Users_Ranking",null,users_Ranking_Nivel);

            db_inserir_Users_Ranking.close();
        } catch (Exception exc){
            throw exc;
        }

    }

    private int userId()
    {
        int user_Id=0;
        Cursor c;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query;
        String userName=mSettings.getString("pref_currentUser", "");

        query = "SELECT * FROM Utilizadores WHERE Username='" + userName + "'";

        c = db.rawQuery(query, null);

        if (c.getCount() > 0) {
            c.moveToFirst();
             do {
                 user_Id = c.getInt(0);
            } while (c.moveToNext());

        }

        c.close();
        return user_Id;
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        android.os.Process.killProcess(this.getKillProcess());
        return;
    }

//    @Override
//    public void onBackPressed() {
//        // do something on back.
//        android.os.Process.killProcess(this.getKillProcess());
//        return;
//    }

    public int get_nivelID() {
        return _nivelID;
    }

    public void set_nivelID(int _nivelID) {
        this._nivelID = _nivelID;
    }

    public int get_pontuacao() {
        return _pontuacao;
    }

    public void set_pontuacao(int _pontuacao) {
        this._pontuacao = _pontuacao;
    }

    public int getKillProcess() {
        return killProcess;
    }

    public void setKillProcess(int killProcess) {
        this.killProcess = killProcess;
    }
}
