package com.tpv13.costa.nuno.quizv1;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Nuno on 13-01-2017.
 */

public class Ranking {
    private int id;
    private int utilizadorId;
    private String utilizadorNome;

    private int nivelId;
    private String nivelDescricao;

    private int pontuacao;
    private int rspDadas;
    private int tempo;
    private String data;

    private MyDbHelper_game dbHelper;

    public Ranking(int _id, int _utilizadorId, int _nivelId, int _pontuacaoId, int _rspDadas, int _tempo, String _data, Context c){
        dbHelper=new MyDbHelper_game(c);

        this.id=_id;
        this.utilizadorId=_utilizadorId;
        this.setUtilizadorNome(getUtilizadorNome(_utilizadorId));

        this.nivelId=_nivelId;
        this.setNivelDescricao(getNivelDescricao(_nivelId));

        this.pontuacao=_pontuacaoId;
        this.rspDadas=_rspDadas;
        this.tempo=_tempo;
        this.setData(_data);


    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUtilizadorId() {
        return utilizadorId;
    }

    public void setUtilizadorId(int utilizadorId) {
        this.utilizadorId = utilizadorId;
    }

    public int getNivelId() {
        return nivelId;
    }

    public void setNivelId(int nivelId) {
        this.nivelId = nivelId;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(int pontuacao) {
        this.pontuacao = pontuacao;
    }

    public int getRspDadas() {
        return rspDadas;
    }

    public void setRspDadas(int rspDadas) {
        this.rspDadas = rspDadas;
    }

    public int getTempo() {
        return tempo;
    }

    public void setTempo(int tempo) {
        this.tempo = tempo;
    }

    private String getUtilizadorNome(int userId)
    {
        String nomeTmp="";
        Cursor d;
        SQLiteDatabase db =  dbHelper.getReadableDatabase();
        String query;
        query="SELECT * FROM Utilizadores WHERE Id=" + userId;

        d=db.rawQuery(query ,null);
        if (d.getCount()>0 ) {
            d.moveToFirst();

            do{
                nomeTmp=d.getString(1);
            }while (d.moveToNext());

        }

        d.close();
        db.close();

       return  nomeTmp;
    }

    private String getNivelDescricao(int nivelId)
    {
        String nivelTmp="";
        Cursor d;
        SQLiteDatabase db =  dbHelper.getReadableDatabase();
        String query;
        query="SELECT * FROM Niveis WHERE Id=" + nivelId;

        d=db.rawQuery(query ,null);
        if (d.getCount()>0 ) {
            d.moveToFirst();

            do{
                nivelTmp=d.getString(1);
            }while (d.moveToNext());

        }

        d.close();
        db.close();

        return  nivelTmp;
    }

    public String getUtilizadorNome() {
        return utilizadorNome;
    }

    public void setUtilizadorNome(String utilizadorNome) {
        this.utilizadorNome = utilizadorNome;
    }

    public String getNivelDescricao() {
        return nivelDescricao;
    }

    public void setNivelDescricao(String nivelDescricao) {
        this.nivelDescricao = nivelDescricao;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
