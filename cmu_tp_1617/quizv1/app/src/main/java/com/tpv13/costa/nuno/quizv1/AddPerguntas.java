package com.tpv13.costa.nuno.quizv1;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tpv13.costa.nuno.quizv1.dummy.SpinnerAdapter_niveis;

import java.util.ArrayList;

public class AddPerguntas extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener{

    private Spinner spn_Categoria;
    private Spinner spn_Nivel;
    private Button bt_inserePerg;

    private ArrayList<Categoria> mListcategorias;
    private SpinnerAdapter_categorias categoriaAdapter;
    private ArrayList<Nivel> mListniveis;
    private SpinnerAdapter_niveis nivelAdapter;

    private Integer nivelSelectedId, categoriaSelectdId;

    private EditText _new_pergunta, _new_resposta_1, _new_resposta_2, _new_resposta_3, _new_resposta_4;

    private MyDbHelper_game dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_perguntas);

        //Chama a Base de dados
        dbHelper = new MyDbHelper_game(this);
        //Inicia um array de categorias
        mListcategorias = new ArrayList<>();
        carregarAdapter();
        categoriaAdapter= new SpinnerAdapter_categorias(this,android.R.layout.simple_spinner_dropdown_item,mListcategorias);

        //Inicia um array de niveis
        mListniveis = new ArrayList<>();
        carregarAdapter_niveis();
        nivelAdapter = new SpinnerAdapter_niveis(this, android.R.layout.simple_spinner_dropdown_item,mListniveis);

        spn_Categoria = (Spinner) findViewById(R.id.spinner_categorias);
        spn_Nivel = (Spinner) findViewById(R.id.spinner_niveis);

        categoriaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_Categoria.setAdapter(categoriaAdapter);
        spn_Categoria.setOnItemSelectedListener(this);

        nivelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_Nivel.setAdapter(nivelAdapter);
        spn_Nivel.setOnItemSelectedListener(this);

        _new_pergunta=(EditText) findViewById(R.id.new_pergunta);
        _new_resposta_1=(EditText) findViewById(R.id.new_resposta_correta);
        _new_resposta_2=(EditText) findViewById(R.id.new_resposta_2);
        _new_resposta_3=(EditText) findViewById(R.id.new_resposta_3);
        _new_resposta_4=(EditText) findViewById(R.id.new_resposta_4);

        bt_inserePerg = (Button) findViewById(R.id.bt_inserePergunta);
        bt_inserePerg.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.bt_inserePergunta: //error
                    if (verificarTudoPreenchido()){
                        inserirBD();
//                        Toast.makeText(this, "Inserir BD", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else{
                        Toast.makeText(this, "Campos Obrigatórios por preencher.", Toast.LENGTH_SHORT).show();
                    }

                    break;
//            case R.id.spinner_categorias:
//                Toast.makeText(this, "Ola spinner categorias", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.spinner_niveis:
//                Toast.makeText(this,"Ola spinner niveis",Toast.LENGTH_SHORT).show();
//                break;
                default:
                    break;
            }

        } catch (Exception exc){
            Toast.makeText(this, "Falhei a inserção. Erro: " + exc.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    //Mostrar Categorias
//    private void getCategorias()
//    {
//        //Limpa a lista
//        mListcategorias.clear();
//        // Realizar a query � base de dados
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        Cursor c = db.query(MyDbHelper_game.Categorias, MyDbHelper_game.Columns, null, null, null, null, MyDbHelper_game.Second_Column + " ASC");
//    }

    private boolean verificarTudoPreenchido(){
        if (!this._new_pergunta.getText().toString().trim().equals("") &&
            !this._new_resposta_1.getText().toString().trim().equals("") &&
            !this._new_resposta_2.getText().toString().trim().equals("") &&
            !this._new_resposta_3.getText().toString().trim().equals("") &&
            !this._new_resposta_4.getText().toString().trim().equals("") ){
            return true;
        }

        return false;
    }

    private void inserirBD(){
        SQLiteDatabase db=dbHelper.getWritableDatabase();

        ContentValues valuesPerg = new ContentValues();
        valuesPerg.put("Niveis_Id", nivelSelectedId);
        valuesPerg.put("Categorias_Id", categoriaSelectdId);
        valuesPerg.put("Pergunta", (this._new_pergunta.getText().toString().trim() +"?").replace("??","?"));
        valuesPerg.put("Pontuacao",nivelAdapter.getPontuacao(nivelSelectedId));

        Toast.makeText(this, "Aqui temos de verificar qual o nivel e atribuir a pontuação.", Toast.LENGTH_LONG).show();

        long idRsp = db.insert("Perguntas",null,valuesPerg);

        ContentValues valuesResp1 = new ContentValues();
        ContentValues valuesResp2 = new ContentValues();
        ContentValues valuesResp3 = new ContentValues();
        ContentValues valuesResp4 = new ContentValues();

        valuesResp1.put("Perguntas_Id",idRsp);
        valuesResp1.put("Descricao",this._new_resposta_1.getText().toString().trim());
        valuesResp1.put("Correta","S");
        db.insert("Respostas",null,valuesResp1);

        valuesResp2.put("Perguntas_Id",idRsp);
        valuesResp2.put("Descricao",this._new_resposta_2.getText().toString().trim());
        valuesResp2.put("Correta","N");
        db.insert("Respostas",null,valuesResp2);

        valuesResp3.put("Perguntas_Id",idRsp);
        valuesResp3.put("Descricao",this._new_resposta_3.getText().toString().trim());
        valuesResp3.put("Correta","N");
        db.insert("Respostas",null,valuesResp3);

        valuesResp4.put("Perguntas_Id",idRsp);
        valuesResp4.put("Descricao",this._new_resposta_4.getText().toString().trim());
        valuesResp4.put("Correta","N");
        db.insert("Respostas",null,valuesResp4);

        db.close();

        Toast.makeText(this, "Pergunta Inserida com SUCESSO!", Toast.LENGTH_LONG).show();

    }

    private void carregarAdapter(){
        mListcategorias.clear();

        Cursor c;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query;
        query="SELECT * FROM Categorias ";

        c=db.rawQuery(query ,null);

        try
        {
            if (c.getCount()>0 ) {
                c.moveToFirst();
                // Loop through all Results
                do {
                    mListcategorias.add(new Categoria(c.getInt(0),c.getString(1)));

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

    private void carregarAdapter_niveis()
    {
        mListniveis.clear();

        Cursor c;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query;
        query="SELECT * FROM Niveis ";

        c=db.rawQuery(query ,null);

        try
        {
            if (c.getCount()>0 ) {
                c.moveToFirst();
                // Loop through all Results
                do {
                    mListniveis.add(new Nivel(c.getInt(0),c.getString(1)));

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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        if (adapterView== findViewById(R.id.spinner_categorias)){
            Categoria selected = (Categoria) adapterView.getItemAtPosition(position);
            ((TextView) adapterView.getChildAt(0)).setTextSize(15);

            categoriaSelectdId=selected.getId();

//            Toast.makeText(this, " Escolhi a categoria: "+ selected.getDescricao() +
//                                  "\nCom o Id: " + selected.getId(), Toast.LENGTH_SHORT).show();
        }
        else
            if(adapterView == findViewById(R.id.spinner_niveis))
            {
                Nivel selected = (Nivel) adapterView.getItemAtPosition(position);
                ((TextView) adapterView.getChildAt(0)).setTextSize(15);
                nivelSelectedId=selected.getId();

//                Toast.makeText(this, " Escolhi o Nivel: "+ selected.getNome() +
//                        "\nCom o Id: " + selected.getId(), Toast.LENGTH_SHORT).show();

            }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
