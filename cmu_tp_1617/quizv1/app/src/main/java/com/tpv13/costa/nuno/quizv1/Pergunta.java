package com.tpv13.costa.nuno.quizv1;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Nuno on 02-12-2016.
 */

public class Pergunta implements Serializable {
    private int id;
    private int nivelID;
    private String pergunta;
    private int pontuacao;
    private int categoriaId;
    private ArrayList<Resposta> respostas;

    public Pergunta(int _id, int _nivelID, String _pergunta, int _pontucao,int _categoria, ArrayList<Resposta> _respostas){
        this.setId(_id);
        this.setNivelID(_nivelID);
        this.categoriaId=_categoria;
        this.setPergunta(_pergunta);
        this.setPontuacao(_pontucao);
        this.respostas=_respostas;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNivelID() {
        return nivelID;
    }

    public void setNivelID(int nivelID) {
        this.nivelID = nivelID;
    }

    public String getPergunta() {
        return pergunta;
    }

    public void setPergunta(String pergunta) {
        this.pergunta = pergunta;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(int pontuacao) {
        this.pontuacao = pontuacao;
    }

    public int getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(int categoriaId) {
        this.categoriaId = categoriaId;
    }

    public Resposta getRespostaByIndex(int index){
        return this.respostas.get(index);
    }



}
