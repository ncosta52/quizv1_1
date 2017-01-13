package com.tpv13.costa.nuno.quizv1;

import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class EstatisticasActivity2 extends ListActivity {
    private MyDbHelper_game dbHelper;
    ListAdapterEstatistica mAdapter_estatistica;
    private ArrayList<Ranking> mRankingList =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{

            setContentView(R.layout.fragment_estatisticas);

            dbHelper = new MyDbHelper_game(this);

            carregarAdapter(getIntent().getIntExtra("NivelId",0));

            mAdapter_estatistica = new ListAdapterEstatistica(this,mRankingList );
            setListAdapter(mAdapter_estatistica);


        } catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void onResume()
    {
        super.onResume();
        mAdapter_estatistica.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        // do something on back.
        android.os.Process.killProcess(android.os.Process.myPid());
        return;
    }

    private void carregarAdapter(int nivel)
    {
        Cursor c;
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        ArrayList<Ranking> rankingListTMP=new ArrayList<>();

        String query;
        query="SELECT * FROM Users_Ranking WHERE Niveis_Id=" + nivel;

        c=db.rawQuery(query ,null);
        if (c.getCount()>0 ) {
            c.moveToFirst();

            do{
                mRankingList.add(new Ranking(c.getInt(0), c.getInt(1), c.getInt(2), c.getInt(3), c.getInt(4), c.getInt(5), c.getString(6),this));
            }while (c.moveToNext());
        }

        c.close();

    }
}
