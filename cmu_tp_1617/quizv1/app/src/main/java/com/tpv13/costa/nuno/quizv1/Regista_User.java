package com.tpv13.costa.nuno.quizv1;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.net.URI;

public class Regista_User extends Activity implements View.OnClickListener {
    Button bt_cam;
    ImageView imageView;

    private Button bt_cancelar, bt_guardar;
    private EditText edit_User, edit_Pass, edit_Confirm_Pass;

    private MyDbHelper_game dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regista__user);

        dbHelper = new MyDbHelper_game(this);

        bt_cam = (Button) findViewById(R.id.bt_uploadFoto);
        imageView = (ImageView) findViewById(R.id.image_view);
        bt_cam.setOnClickListener(this);

        bt_cancelar = (Button) findViewById(R.id.bt_cancelar);
        bt_cancelar.setOnClickListener(this);

        bt_guardar = (Button) findViewById(R.id.bt_guardar);
        bt_guardar.setOnClickListener(this);

        edit_User = (EditText) findViewById(R.id.ed_new_user);
        edit_Pass = (EditText) findViewById(R.id.ed_new_pass);
        edit_Confirm_Pass = (EditText) findViewById(R.id.ed_new_pass_confirm);
    }

    @Override
    public void onClick(View view) {
//        Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        File file = getFile();
//        camera_intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
//        startActivityForResult(camera_intent,1);

        switch (view.getId()) {
            case R.id.bt_uploadFoto: //error
                chamaCamera();
                break;
            case R.id.bt_cancelar:
                finish();
                break;
            case R.id.bt_guardar:
                if (userExist() == false)
                {
                    if(passValida() == true)
                    {
                        //GRAVAR BD
                        SQLiteDatabase db = dbHelper.getWritableDatabase();

                        ContentValues valuesUser = new ContentValues();
                        valuesUser.put("Username", this.edit_User.getText().toString().trim());
                        valuesUser.put("Password", this.edit_Pass.getText().toString().trim());
                        valuesUser.put("Nivel", 1);
                        valuesUser.put("Photo",this.edit_User.getText().toString() + ".jpg");

                        db.insert("Utilizadores",null,valuesUser);

                        Toast.makeText(this,this.edit_User.getText().toString().trim()+".jpeg", Toast.LENGTH_LONG).show();
                        db.close();
                        finish();
                    }
                    else
                    {   Toast.makeText(this, "Reveja a password", Toast.LENGTH_LONG).show();}
                }
                break;
            default:
                break;
        }
    }

    private void chamaCamera() {
        Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = getFile();
        camera_intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(camera_intent, 1);
    }


    private File getFile() {
        File folder = new File("sdcard/quizImages");

        if (!folder.exists()) {
            folder.mkdir();
        }

        File image_file = new File(folder, this.edit_User.getText().toString() + ".jpg");
        return image_file;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String path = "sdcard/quizImages/" + this.edit_User.getText().toString() + ".jpg";
        imageView.setImageDrawable(Drawable.createFromPath(path));
    }

    private boolean userExist() {
        boolean existe = false;
        Cursor d;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query;
        query = "SELECT * FROM Utilizadores WHERE Username='" + this.edit_User.getText().toString().trim() + "'";

        d = db.rawQuery(query, null);

        if (d.getCount() > 0) {
//
            Toast.makeText(this, "Este utilizador já existe", Toast.LENGTH_LONG).show();
            existe = true;
        } else {
            existe = false;
        }

        d.close();
        return existe;
    }

    private boolean passValida()
    {
        boolean valido = false;

        if(this.edit_Pass.getText().toString().trim().length() > 0)
        {
            if(this.edit_Pass.getText().toString().trim().equals(this.edit_Confirm_Pass.getText().toString().trim())) {
                return true;
            }
            else
            {
                Toast.makeText(this, "As passwords não são iguais!", Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(this, "Insira uma password!", Toast.LENGTH_LONG).show();
            return false;
        }

        return false;
    }

}