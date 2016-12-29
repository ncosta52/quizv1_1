package com.tpv13.costa.nuno.quizv1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.VolumeProvider;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends Activity implements View.OnClickListener,CompoundButton.OnCheckedChangeListener  {

    private Button bt_quemQuerSerMilionario, bt_novoJogo, bt_estatisticas, bt_testeWidget;
    private Switch mySwitch;
    private MediaPlayer sound_menu ;
    private MyDbHelper_game dbHelper;

    private ArrayList<Pergunta> perguLS=new ArrayList<>();

    public static final String INTENT_MESSAGE = "pt.ipp.estgf.cmu.widgetproject.MESSAGE";
    public static final String INTENT_MESSAGE_EXTRA = "message_extra";

//    private Random randomGenerator=new Random();

    AudioManager a;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        a=(AudioManager)getSystemService(Context.AUDIO_SERVICE);

        bt_novoJogo = (Button) findViewById(R.id.bt_novoJogo);
        bt_novoJogo.setOnClickListener(this);

        bt_estatisticas = (Button) findViewById(R.id.bt_estatisticas);
        bt_estatisticas.setOnClickListener(this);

        bt_quemQuerSerMilionario = (Button) findViewById(R.id.bt_quemQuerSerMilionario);
        bt_quemQuerSerMilionario.setOnClickListener(this);

        bt_testeWidget=(Button) findViewById(R.id.bt_testeWidget);
        bt_testeWidget.setOnClickListener(this);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        //rrclient = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        dbHelper = new MyDbHelper_game(this);

        mySwitch = (Switch) findViewById(R.id.sw);
        //set the switch to ON
        mySwitch.setChecked(false);

        //attach a listener to check for changes in state
        mySwitch.setOnCheckedChangeListener(this);


//        //check the current state before we display the screen
        if(mySwitch.isChecked()){
            a.setMode(AudioManager.MODE_NORMAL);
            a.setStreamSolo(AudioManager.STREAM_VOICE_CALL, false);
//            Toast.makeText(this, "Switch is currently ON", Toast.LENGTH_SHORT).show();

        }else{
            a.setMode(AudioManager.MODE_IN_CALL);
            a.setStreamSolo(AudioManager.STREAM_VOICE_CALL, true);
//            Toast.makeText(this, "Switch is currently OFF", Toast.LENGTH_SHORT).show();
        }

        carregarLstTesteWidget();
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
                //launch activity
                break;
            case R.id.bt_testeWidget:
                testeWidget();
                break;
            default:
                break;
        }
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
            intent.putExtra("Pergunta",perguLS);
            intent.putExtra(INTENT_MESSAGE_EXTRA, "msg");
            sendBroadcast(intent);
        }
//            Toast.makeText(this, getString(R.string.messageSent), Toast.LENGTH_LONG).show();
//        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        boolean isChecked=b;
            if(isChecked){
                a.setMode(AudioManager.MODE_NORMAL);
                a.setStreamSolo(AudioManager.STREAM_VOICE_CALL, false);
//                Toast.makeText(this, "Switch is currently ON", Toast.LENGTH_SHORT).show();

            }else{
                a.setMode(AudioManager.MODE_IN_CALL);
                a.setStreamSolo(AudioManager.STREAM_VOICE_CALL, true);
//                Toast.makeText(this, "Switch is currently OFF", Toast.LENGTH_SHORT).show();
            }
    }
}
