package upc.android.projecte.stevenfarmgame;

import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class Help extends AppCompatActivity {

    private MediaPlayer mp_buttons;
    private TextView explanation;
    private ImageButton b_backhelp, b_next, b_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mp_buttons = MediaPlayer.create(this,R.raw.pop);
        b_backhelp = (ImageButton) findViewById(R.id.b_backhelp);
        b_next = (ImageButton) findViewById(R.id.b_next);
        b_back = (ImageButton) findViewById(R.id.b_back);
        explanation = (TextView) findViewById(R.id.explanation);

        b_back.setVisibility(View.INVISIBLE);
        explanation.setText(R.string.explanation1);

        b_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b_back.setVisibility(View.VISIBLE);
                b_next.setVisibility(View.INVISIBLE);
                explanation.setText(R.string.explanation2);
            }
        });

        b_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b_next.setVisibility(View.VISIBLE);
                b_back.setVisibility(View.INVISIBLE);
                explanation.setText(R.string.explanation1);
            }
        });

        //Go to menu
        b_backhelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Create_Menu();
            }
        });
    }

    private void Create_Menu() {
        b_backhelp.setRotationY(-20f);
        mp_buttons.start();
        finish();
    }
}
