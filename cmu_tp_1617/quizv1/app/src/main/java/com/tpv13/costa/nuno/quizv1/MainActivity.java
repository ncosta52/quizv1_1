package com.tpv13.costa.nuno.quizv1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.Thing;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends Activity implements View.OnClickListener{//},CompoundButton.OnCheckedChangeListener  {

    public static final String SOM_PREFERENCE_State = "pref_somOnOff";
    public static final boolean SOM_PREFERENCE_DEFAULT = false;
    public static final String CurrentUSer_Preference="pref_currentUser";

    private Button bt_quemQuerSerMilionario, bt_novoJogo, bt_estatisticas, bt_testeWidget, bt_preferencias, bt_addcategoria, bt_addPergunta;
    private TextView tv_currentUser;
    private ImageView _photoUser;
//    private Switch mySwitch;
    private MediaPlayer sound_menu ;
    private MyDbHelper_game dbHelper;

    private ArrayList<Pergunta> perguLS=new ArrayList<>();

    private SharedPreferences mSettings;

    public static final String INTENT_MESSAGE = "pt.ipp.estgf.cmu.widgetproject.MESSAGE";
    public static final String INTENT_MESSAGE_EXTRA = "message_extra";

//    private Random randomGenerator=new Random();

   private AudioManager a;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSettings = PreferenceManager.getDefaultSharedPreferences(this);

        a=(AudioManager)getSystemService(Context.AUDIO_SERVICE);


        tv_currentUser=(TextView) findViewById(R.id.tv_currentUser);
        SpannableString content = new SpannableString(mSettings.getString(CurrentUSer_Preference, ""));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);

        tv_currentUser.setText(content);
        tv_currentUser.setOnClickListener(this);


        bt_novoJogo = (Button) findViewById(R.id.bt_novoJogo);
        bt_novoJogo.setOnClickListener(this);

        bt_estatisticas = (Button) findViewById(R.id.bt_estatisticas);
        bt_estatisticas.setOnClickListener(this);

        bt_quemQuerSerMilionario = (Button) findViewById(R.id.bt_quemQuerSerMilionario);
        bt_quemQuerSerMilionario.setOnClickListener(this);

        bt_testeWidget=(Button) findViewById(R.id.bt_testeWidget);
        bt_testeWidget.setOnClickListener(this);

        bt_preferencias=(Button) findViewById(R.id.bt_preferencias);
        bt_preferencias.setOnClickListener(this);

        bt_addcategoria = (Button) findViewById(R.id.bt_addCategarias );
        bt_addcategoria.setOnClickListener(this);

        bt_addPergunta=(Button) findViewById(R.id.bt_addPergunta );
        bt_addPergunta.setOnClickListener(this);



        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        //rrclient = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        dbHelper = new MyDbHelper_game(this);

        _photoUser=(ImageView) findViewById(R.id.photoUser);
        _photoUser.setOnClickListener(this);

        carregarPhotoUser(mSettings.getString(CurrentUSer_Preference, ""));
        carregarLstTesteWidget();
    }

    private void carregarPhotoUser(String user){


        String pathName="";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c= db.rawQuery("SELECT Photo FROM Utilizadores WHERE Username='" + user + "'",null);


        try
        {
            if (c.getCount()>0 ) {
                c.moveToFirst();
                // Loop through all Results
                do {
                    pathName=c.getString(0);
                }while(c.moveToNext());


                File extStore = Environment.getExternalStorageDirectory();
                File myFile = new File(extStore.getAbsolutePath() + "/quizImages/" + pathName);

                if(myFile.exists()){
                    Bitmap bmp = BitmapFactory.decodeFile(myFile.toString());
                    _photoUser.setImageBitmap(bmp);
                }
                else{
                    _photoUser.setImageDrawable(getResources().getDrawable(R.drawable.user_icon));
                    //qImageView.setBackgroundResource(R.drawable.thumbs_down);
                }
            }

            c.close();
            db.close();
        }
        catch(Exception e) {
            Log.e("Error", "Error", e);
            throw e;
        }

    }

    private void carregarLstTesteWidget(){
        Pergunta p=null ;
        ArrayList<Resposta> r=new ArrayList<>();


        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c= db.rawQuery("SELECT * FROM Perguntas",null);
        Cursor d;
        boolean certa;

        try
        {
            if (c.getCount()>0 ) {
                c.moveToFirst();
                // Loop through all Results
                do {
                    d=db.rawQuery("SELECT * FROM Respostas WHERE Perguntas_Id=" +  c.getInt(0) ,null);
                    if (d.getCount()>0 ) {
                        d.moveToFirst();

                        do {

                            if (d.getString(3).equals("S")) {
                                certa = true;
                            } else {
                                certa = false;
                            }

                            r.add(new Resposta(d.getInt(0), d.getInt(1), d.getString(2), certa));
                            //int _id, int _perguntaId, String _descricao, boolean _correta
                        } while (d.moveToNext());

                        p = new Pergunta(c.getInt(0), c.getInt(1),c.getString(3), c.getInt(4), c.getInt(2),r );
    //                    int _id, int _nivelID, String _pergunta, int _pontucao,int _categoria, ArrayList<Resposta> _resposta

                        perguLS.add(p);
                    }
                    d.close();
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

    //http://stackoverflow.com/questions/15658687/how-to-use-onresume
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

        if (nivelUser(mSettings.getString(CurrentUSer_Preference, ""))<3){
            bt_addcategoria.setVisibility(View.INVISIBLE);
            bt_addPergunta.setVisibility(View.INVISIBLE);
        }


        // put your code here...
        sound_menu= MediaPlayer.create(MainActivity.this, R.raw.sound_menu);
        sound_menu.setLooping(true);
        sound_menu.start();
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
        sound_menu.stop();
        switch (view.getId()) {
            case R.id.bt_quemQuerSerMilionario: //error
                startActivity(new Intent(this, QQSM_Activity.class));
                break;
            case R.id.bt_novoJogo: //error
                startActivity(new Intent(this, NovoJogoOpcoes.class));
                break;
            case R.id.bt_estatisticas: //error
                startActivity(new Intent(this, EstatisticasActivity.class));
                break;
            case R.id.bt_testeWidget:
                testeWidget();
                break;
            case R.id.bt_preferencias:
                startActivity(new Intent(this, PreferencesActivity.class));
                break;
            case R.id.tv_currentUser:
                Toast.makeText(this, "SAIR LOG OUT", Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor mEditor = mSettings.edit();
                mEditor.putString(CurrentUSer_Preference, "");
                mEditor.apply();
                finish();
                break;
            case R.id.photoUser:
                Toast.makeText(this, "SAIR LOG OUT", Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor mEditor1 = mSettings.edit();
                mEditor1.putString(CurrentUSer_Preference, "");
                mEditor1.apply();
                finish();
                break;
            case R.id.bt_addCategarias:
                startActivity(new Intent(this, AddCategorias.class));
                break;
            case R.id.bt_addPergunta:
                startActivity(new Intent(this, AddPerguntas.class));
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, 0, R.string.preferencias);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == 0)
            startActivity(new Intent(this, PreferencesActivity.class));

        return true;
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        //client.connect();
        //AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        //AppIndex.AppIndexApi.end(client, getIndexApiAction());
        //client.disconnect();
    }

    private void testeWidget(){
        int index=0;

        if (perguLS.size()>0) {


//            index = randomGenerator.nextInt(perguLS.size());
//
////        if (v.getId() == R.id.btnAction) {
//            String msg = perguLS.get(index).getPergunta();
//            perguLS.remove(index);

            // intent que indica a ac��o a executar e cont�m os dados a enviar
            // o identificador da ac��o est� registado no intent-filter do broadcast receiver no manifest
            Intent intent = new Intent(INTENT_MESSAGE);
            //intent.putExtra("Pergunta",perguLS);
            intent.putExtra(INTENT_MESSAGE_EXTRA, "msg");
            sendBroadcast(intent);
        }
//            Toast.makeText(this, getString(R.string.messageSent), Toast.LENGTH_LONG).show();
//        }
    }

    private int nivelUser(String user)
    {
        Cursor d;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query;
        int nivel=1;

        query="SELECT * FROM Utilizadores WHERE Username='" + user + "'";

        d=db.rawQuery(query ,null);
        if (d.getCount()>0 ) {
            d.moveToFirst();

            nivel=d.getInt(3);

        }

        d.close();

        return nivel;
    }

//    @Override
//    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//        boolean isChecked=b;
//            if(isChecked){
//                a.setMode(AudioManager.MODE_NORMAL);
//                a.setStreamSolo(AudioManager.STREAM_VOICE_CALL, false);
////                Toast.makeText(this, "Switch is currently ON", Toast.LENGTH_SHORT).show();
//
//            }else{
//                a.setMode(AudioManager.MODE_IN_CALL);
//                a.setStreamSolo(AudioManager.STREAM_VOICE_CALL, true);
////                Toast.makeText(this, "Switch is currently OFF", Toast.LENGTH_SHORT).show();
//            }
//    }
}
