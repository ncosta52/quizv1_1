package com.tpv13.costa.nuno.quizv1;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class AddPerguntas extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener{

    private Spinner spn_Categoria;
    private Button bt_inserePerg;
    private ArrayList<Categoria> mListcategorias;
    private SpinnerAdapter_categorias categoriaAdapter;

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

        spn_Categoria = (Spinner) findViewById(R.id.spinner_categorias);
        //categoriaAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mListcategorias);
        categoriaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_Categoria.setAdapter(categoriaAdapter);
        spn_Categoria.setOnItemSelectedListener(this);

        bt_inserePerg = (Button) findViewById(R.id.bt_inserePergunta);
        bt_inserePerg.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_inserePergunta: //error
                Toast.makeText(this, "Ola", Toast.LENGTH_SHORT).show();
                break;
            case R.id.spinner_categorias:
                Toast.makeText(this, "Ola spinner categorias", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        if (adapterView== findViewById(R.id.spinner_categorias)){
            Categoria selected = (Categoria) adapterView.getItemAtPosition(position);

            Toast.makeText(this, " Escolhi a categoria: "+ selected.getDescricao() +
                                  "\nCom o Id: " + selected.getId(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
