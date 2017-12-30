package upc.android.projecte.stevenfarmgame;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Menu extends AppCompatActivity {
    //Array intents
    private Class[] intents ={
            Start.class, Help.class
    };
    //Array idbuttons
    private int[] idbuttons ={
            R.id.b_newgame, R.id.b_help
    };
    private MediaPlayer mp_buttons, mp_fondo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mp_buttons = MediaPlayer.create(this,R.raw.pop);
        mp_fondo = MediaPlayer.create(this, R.raw.menu);
        mp_fondo.start();

        //Button exit + Builder
        ImageButton b_exit = (ImageButton) findViewById(R.id.b_exit);
        final AlertDialog.Builder builder_exit =new AlertDialog.Builder(this);
        builder_exit.setTitle(R.string.confirm_exit);
        builder_exit.setMessage(R.string.message_exit);
        builder_exit.setIcon(R.drawable.diglett);
        builder_exit.setPositiveButton(R.string.positive_exit, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mp_fondo.stop();
                finish();
            }
        });
        builder_exit.setNegativeButton(R.string.negative_exit, null);

        //OnClick Exit Button
        b_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp_buttons.start();
                builder_exit.create().show();
            }
        });

        //Comunacation methods
        for(int i=0; i<idbuttons.length; i++) {
             Create_Imgbutton(i);
        }

    }


    //Buttons Menu creation
    private void Create_Imgbutton(final int i) {
        final ImageButton imgbutton = (ImageButton) findViewById(idbuttons[i]);
        imgbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp_buttons.start();
                if(i==0) {
                    mp_fondo.stop();
                }
                    Call_Activity(i);
            }
        });
    }

    //Comunication classes
    private void Call_Activity(int i) {
        Intent intent = new Intent(this,intents[i]);
        startActivity(intent);
    }
}
