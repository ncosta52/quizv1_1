package com.tpv13.costa.nuno.quizv1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String SOM_PREFERENCE_State = "pref_somOnOff";
    public static final String CurrentUSer_Preference="pref_currentUser";
    public static final boolean SOM_PREFERENCE_DEFAULT = false;

    private EditText editT_user, editT_pass;
    private Button bt_submit;

    private MyDbHelper_game dbHelper;
    private AudioManager a;
    private SharedPreferences mSettings;
    private MediaPlayer sound_menu ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        a=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
        mSettings = PreferenceManager.getDefaultSharedPreferences(this);

        dbHelper = new MyDbHelper_game(this);

        editT_user = (EditText) findViewById(R.id.et_user);
        editT_pass =(EditText) findViewById(R.id.et_password);

        bt_submit=(Button) findViewById(R.id.sign_in_button);
        bt_submit.setOnClickListener(this);
    }

    @Override
    public void onResume(){
        super.onResume();

        if(mSettings.getBoolean(SOM_PREFERENCE_State, SOM_PREFERENCE_DEFAULT)){
            a.setMode(AudioManager.MODE_NORMAL);
            a.setStreamSolo(AudioManager.STREAM_VOICE_CALL, false);
//            Toast.makeText(this, "Switch is currently ON", Toast.LENGTH_SHORT).show();

        }else{
            a.setMode(AudioManager.MODE_IN_CALL);
            a.setStreamSolo(AudioManager.STREAM_VOICE_CALL, true);
//            Toast.makeText(this, "Switch is currently OFF", Toast.LENGTH_SHORT).show();
        }

        // put your code here...
        sound_menu= MediaPlayer.create(LoginActivity.this, R.raw.sound_menu);
        sound_menu.setLooping(true);
        sound_menu.start();

        editT_user.requestFocus();
    }

    @Override
    public void onPause(){
        super.onPause();

        if (sound_menu!=null){
            sound_menu.pause();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_in_button: //error
                validarLogIn();
                break;
            default:
                break;
        }
    }

    private void validarLogIn()
    {
        Cursor d;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        boolean valido=false;
        String query;
        query="SELECT * FROM Utilizadores WHERE Username='" + this.editT_user.getText().toString() + "'";

        d=db.rawQuery(query ,null);
        if (d.getCount()>0 ) {
            d.moveToFirst();

                if (d.getString(2).equals(this.editT_pass.getText().toString())) {
                    valido = true;
                }
                else{
                    Toast.makeText(this, "Password errada!", Toast.LENGTH_LONG ).show();
                }

        }
        else
        {
            Toast.makeText(this, "Este utilizador não existe", Toast.LENGTH_LONG ).show();
        }
        d.close();

        if (valido){
            sound_menu.start();

            SharedPreferences.Editor mEditor = mSettings.edit();

            // Guardar a String com o username na prefer�ncia da aplica��o respectiva.
            // N�o esquecer de invocar o m�todo commit() para guardar o registo.
            mEditor.putString(CurrentUSer_Preference, editT_user.getText().toString());
            mEditor.apply();

            editT_user.setText("");
            editT_pass.setText("");

            startActivity(new Intent(this, MainActivity.class));
        }
    }
}
