package com.tpv13.costa.nuno.quizv1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Checkable;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Nuno on 13-01-2017.
 */

public class ListAdapterEstatistica extends ArrayAdapter<Ranking>
{
    private Context context;
    private ArrayList<Ranking> rankingList;
    private MyDbHelper_game dbHelper;
    private int selectedIndex = -1;

    public ListAdapterEstatistica(Context context, int nivel) {
        super(context,R.layout.row_estatistica,nivel);

        dbHelper=new MyDbHelper_game(context);
        this.rankingList = carregarAdapter(nivel);
        this.context = context;


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View v = convertView;

        if(v == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.row_estatistica, null);
        }

        TextView tvUser = (TextView) v.findViewById(R.id.tv_userEsta);
        TextView tvPontu = (TextView) v.findViewById(R.id.tv_pontuacaoEsta);


        tvUser.setText(rankingList.get(position).getUtilizadorId());
        tvPontu.setText(rankingList.get(position).getPontuacao());

        return v;

    }


    public void setSelectedIndex(int index){
        selectedIndex = index;
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    private ArrayList<Ranking> carregarAdapter(int nivel)
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
               rankingListTMP.add(new Ranking(c.getInt(0), c.getInt(1), c.getInt(2), c.getInt(3), c.getInt(4), c.getInt(5)));
           }while (c.moveToNext());
        }

        c.close();

        return rankingListTMP;
    }
}


