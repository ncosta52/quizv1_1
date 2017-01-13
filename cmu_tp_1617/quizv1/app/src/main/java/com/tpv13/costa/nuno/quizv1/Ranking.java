package com.tpv13.costa.nuno.quizv1;

/**
 * Created by Nuno on 13-01-2017.
 */

public class Ranking {
    private int id;
    private int utilizadorId;
    private int nivelId;
    private int pontuacao;
    private int rspDadas;
    private int tempo;

    public Ranking(int _id, int _utilizadorId, int _nivelId, int _pontuacaoId, int _rspDadas, int _tempo){
        this.id=_id;
        this.utilizadorId=_utilizadorId;
        this.nivelId=_nivelId;
        this.pontuacao=_pontuacaoId;
        this.rspDadas=_rspDadas;
        this.tempo=_tempo;
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
}
