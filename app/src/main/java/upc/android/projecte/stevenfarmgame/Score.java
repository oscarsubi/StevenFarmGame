package upc.android.projecte.stevenfarmgame;

import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import java.util.List;

public class Score extends AppCompatActivity {

    private MediaPlayer mp_buttons, mp_score;
    private ImageButton b_backscore;
    private List<Player> ListPlayers;
    private ArrayAdapter<Player> ListAdapter;
    private int pini, pmax=0;
    private String winner;
    private int wn, ws;
    private boolean tie=false;

    public static String key_score = "score";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        ListPlayers = (List<Player>)getIntent().getExtras().getSerializable(key_score);
        ListView List = (ListView) findViewById(R.id.List);
        mp_buttons = MediaPlayer.create(this,R.raw.pop);
        mp_score = MediaPlayer.create(this,R.raw.options);
        b_backscore = (ImageButton) findViewById(R.id.b_backscore);
        mp_score.start();

        ListAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, ListPlayers
        );
        List.setAdapter(ListAdapter);

        for(int i=0; i<ListPlayers.size(); i++){
            pini=ListPlayers.get(i).getScore();
            if (pini>=pmax){
                pmax=pini;
                wn=ListPlayers.get(i).getNumber();
                ws=ListPlayers.get(i).getScore();
                winner=ListPlayers.get(i).getName();
            }
        }

        for(int i=0; i<ListPlayers.size(); i++){
            pini=ListPlayers.get(i).getScore();
            if (pini==pmax && wn!=ListPlayers.get(i).getNumber()){
                tie=true;
                final AlertDialog.Builder builder_tie =new AlertDialog.Builder(Score.this);
                builder_tie.setTitle(R.string.tie_braker);
                builder_tie.setMessage("           ¡ "+ws+" pts. !");
                builder_tie.setIcon(R.drawable.stevengood);
                builder_tie.setPositiveButton(R.string.positive_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder_tie.setCancelable(false);
                builder_tie.create().show();
            }
        }

        if(!tie) {
            final AlertDialog.Builder builder_win =new AlertDialog.Builder(Score.this);
            builder_win.setTitle(R.string.win_msg);
            builder_win.setMessage("           > "+winner+": "+ws+" pts.");
            builder_win.setIcon(R.drawable.stevenperfect);
            builder_win.setPositiveButton(R.string.positive_ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder_win.setCancelable(false);
            builder_win.create().show();
        }

        //Go to Menu
        b_backscore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Create_Menu();
            }
        });
    }

    private void Create_Menu() {
        b_backscore.setRotationY(-20f);
        mp_buttons.start();
        finish();
    }
}
