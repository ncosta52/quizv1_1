package com.tpv13.costa.nuno.quizv1;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.net.URI;

public class Regista_User extends Activity implements View.OnClickListener
{
    Button bt_cam;
    ImageView imageView;

    private Button bt_cancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regista__user);

        bt_cam = (Button) findViewById(R.id.bt_uploadFoto);
        imageView = (ImageView) findViewById(R.id.image_view);
        bt_cam.setOnClickListener(this);

        bt_cancelar = (Button) findViewById(R.id.bt_cancelar);
        bt_cancelar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
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
            default:
                break;
        }
    }

    private void chamaCamera()
    {
        Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = getFile();
        camera_intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(camera_intent,1);
    }


    private File getFile()
    {
        File folder = new File("sdcard/camera_app"); //sdcard/quizImages

        if(!folder.exists())
        {
            folder.mkdir();
        }

        File image_file = new File(folder,"cam_image.jpg"); //username.jpg
        return image_file;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String path = "sdcard/camera_app/cam_image.jpg";
        imageView.setImageDrawable(Drawable.createFromPath(path));
    }
}
