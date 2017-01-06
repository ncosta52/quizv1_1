package com.tpv13.costa.nuno.quizv1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddCategorias extends AppCompatActivity  implements View.OnClickListener{

    private Button bt_insere;
    private EditText ed_categoria;
    private MyDbHelper_game dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_categorias);

        dbHelper = new MyDbHelper_game(this);

        bt_insere = (Button) findViewById(R.id.bt_insereCategoria);
        bt_insere.setOnClickListener(this);

        ed_categoria = (EditText) findViewById(R.id.edit_categoria);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_insereCategoria: //error
                insereBD();
                break;
            default:
                break;
        }
    }

    private void insereBD()
    {
        if(validarCategoria()== false)
        {
            String tmp = ed_categoria.toString();
            //inserir bd
            dbHelper.getWritableDatabase().execSQL("INSERT INTO Categorias (Descricao) VALUES (ed_categoria.toString());");
            Toast.makeText(this, "Categoria inserida com sucesso!", Toast.LENGTH_SHORT).show();
            dbHelper.close();
            finish();
        }
        else
        {
            Toast.makeText(this, "Base de dados existente", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean validarCategoria()
    {
        Cursor d;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        boolean existe=false;
        String query;
        query="SELECT * FROM Categorias WHERE Descricao='" + this.ed_categoria.getText().toString() + "'";

        d=db.rawQuery(query ,null);
        if (d.getCount()>0 ) {
           existe=true;

        }

        d.close();
        return existe;
    }
}
