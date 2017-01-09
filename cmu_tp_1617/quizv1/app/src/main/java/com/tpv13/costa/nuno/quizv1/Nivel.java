package com.tpv13.costa.nuno.quizv1;

/**
 * Created by Nuno on 02-12-2016.
 */

public class Nivel {

    private int id;
    private String nome;
    private String descricao;
    private int pontuacao;

    public Nivel(int _id, String _nome, String _descricao, int _pontuacao)
    {
        this.setId(_id);
        this.setNome(_nome);
        this.setDescricao(_descricao);
        this.setPontuacao(_pontuacao);
    }

    public Nivel(int _id, String _nome)
    {
        this.setId(_id);
        this.setNome(_nome);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(int pontuacao) {
        this.pontuacao = pontuacao;
    }
}
