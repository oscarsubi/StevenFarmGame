package upc.android.projecte.stevenfarmgame;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class CoverPage extends AppCompatActivity {

    private MediaPlayer mp_cover;
    private ImageButton b_cover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cover_page);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mp_cover = MediaPlayer.create(this, R.raw.intro);
        b_cover = (ImageButton) findViewById(R.id.b_cover);
        mp_cover.start();
        Toast.makeText(this, R.string.msg_cover, Toast.LENGTH_LONG).show();

        //Menu comunication
        b_cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Create_Menu();
            }
        });

    }

    private void Create_Menu() {
        Intent intent = new Intent(this, Menu.class);
        b_cover.setRotation(15f);
        startActivity(intent);
        finish();
    }
}
