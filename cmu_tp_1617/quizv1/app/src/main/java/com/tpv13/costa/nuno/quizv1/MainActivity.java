package com.tpv13.costa.nuno.quizv1;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button bt_quemQuerSerMilionario, bt_novoJogo, bt_estatisticas;
    MediaPlayer sound_menu ;


//    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        bt_novoJogo = (Button) findViewById(R.id.bt_novoJogo);
        bt_novoJogo.setOnClickListener(this);

        bt_estatisticas = (Button) findViewById(R.id.bt_estatisticas);
        bt_estatisticas.setOnClickListener(this);

        bt_quemQuerSerMilionario = (Button) findViewById(R.id.bt_quemQuerSerMilionario);
        bt_quemQuerSerMilionario.setOnClickListener(this);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        //rrclient = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    //http://stackoverflow.com/questions/15658687/how-to-use-onresume
    @Override
    public void onResume(){
        super.onResume();
        // put your code here...
        sound_menu= MediaPlayer.create(MainActivity.this, R.raw.sound_menu);
        sound_menu.start();
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
}
