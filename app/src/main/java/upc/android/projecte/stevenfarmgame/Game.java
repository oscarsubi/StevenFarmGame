package upc.android.projecte.stevenfarmgame;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import java.io.Serializable;
import java.util.List;


public class Game extends AppCompatActivity {

    private TextView text_time, text_points,text_name, text_number;
    private CountDownTimer cd_timer;
    private int[] iddigletts ={
            R.id.b_diglett1, R.id.b_diglett2, R.id.b_diglett3, R.id.b_diglett4, R.id.b_diglett5
    };
    private MediaPlayer mp_pop, mp_game, mp_finish, mp_diglett;
    private Intent intentscore, intentstart;
    private RandomArray random;
    private boolean music, finish, replay=false;
    private String name;
    private int number, mode, points, t, d, delay;
    private ImageButton b_start, m_on, m_off, b_digletts;
    private List<Player> players;
    public static String key_player = "player";
    public static String key_start = "start";
    private Handler handler;
    public static int request_name=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        players = (List<Player>) getIntent().getExtras().getSerializable(key_player);
        if(players!=null && !players.isEmpty()) {
            Player player = players.get(players.size()-1);
            name = player.getName();
            number = player.getNumber();
            mode = player.getMode();
        }
        intentstart = new Intent(this, Start.class);
        intentscore = new Intent(this, Score.class);
        b_start = (ImageButton) findViewById(R.id.b_start);
        m_on = (ImageButton) findViewById(R.id.b_musicon);
        m_off = (ImageButton) findViewById(R.id.b_musicoff);
        text_time = (TextView) findViewById(R.id.time);
        text_points = (TextView) findViewById(R.id.points);
        mp_pop = MediaPlayer.create(this,R.raw.pop);
        mp_game = MediaPlayer.create(this,R.raw.game);
        mp_finish = MediaPlayer.create(this,R.raw.finish);
        mp_diglett = MediaPlayer.create(this,R.raw.diglett);


        b_start.setVisibility(View.INVISIBLE);

        //Select music
        Toast toastm = Toast.makeText(this, R.string.music, Toast.LENGTH_SHORT);
        toastm.setGravity(Gravity.CENTER|Gravity.CENTER,0,0);
        toastm.show();
        Control_Music();

        for (int i=0; i<iddigletts.length; i++){
            b_digletts = (ImageButton) findViewById(iddigletts[i]);
            b_digletts.setVisibility(View.INVISIBLE);
            Click_Diglett(i);
        }

        //Select mode game
        Mode_Game();

        b_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start game (code)
                text_name = (TextView) findViewById(R.id.player_name);
                text_number = (TextView) findViewById(R.id.player_number);
                text_name.setText(name);
                text_number.setText("P"+number);
                Game_Code();
            }
        });
    }

    //If press button back...
    public void onBackPressed() {
            Toast toastb = Toast.makeText(this, R.string.back_press, Toast.LENGTH_SHORT);
            toastb.setGravity(Gravity.CENTER|Gravity.CENTER,0,0);
            toastb.show();
    }

    private void Replay_Game() {
        points=0;
        text_points.setText("" +points);
        text_time.setTextColor(0xFF4B3404);
        text_time.setTextSize(36f);
        text_time.setText("30");
        b_start.setVisibility(View.VISIBLE);
    }


    private void Control_Music() {
        m_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                music = true;
                Start_Game();
            }
        });
        m_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                music = false;
                Start_Game();
            }
        });
    }


    private void Mode_Game() {
        if(mode == 0){
            t=1000;
            d=2;
        }
        if(mode == 1){
            t=750;
            d=3;
        }
        if(mode == 2){
            t=500;
            d=4;
        }
    }


    private void Start_Game() {
        mp_pop.start();
        m_on.setVisibility(View.GONE);
        m_off.setVisibility(View.GONE);
        b_start.setVisibility(View.VISIBLE);
        Toast toasts = Toast.makeText(this, R.string.start, Toast.LENGTH_SHORT);
        toasts.setGravity(Gravity.CENTER|Gravity.CENTER,0,0);
        toasts.show();
    }


    private void Game_Code() {
        mp_pop.start();
        b_start.setVisibility(View.GONE);
        if (music) {
            mp_game.start();
        }
        Start_Time();
        finish = false;

        handler = new Handler();
        delay = t;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < iddigletts.length; i++) {
                    (findViewById(iddigletts[i])).setVisibility(View.INVISIBLE);
                }
                if (!finish) {
                    random = new RandomArray(d, 5);
                    int[] x = random.getValors();
                    for (int i = 0; i < x.length; i++) {
                        (findViewById(iddigletts[x[i]])).setVisibility(View.VISIBLE);
                    }
                    handler.postDelayed(this, delay);
                }
            }
        }, delay);
    }


    private void Click_Diglett(final int i) {
        b_digletts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(finish) return;
                v.setVisibility(View.INVISIBLE);
                mp_diglett.start();
                if (i + 1 == 3) {
                    points = points + 3;
                }
                else {
                    points = points + 1;
                }
                text_points.setText("" +points);
           }
        });
    }


    private void Start_Time() {
        cd_timer = new CountDownTimer(30*1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                text_time.setText("" +millisUntilFinished / 1000);
                if (millisUntilFinished < 6*1000){
                    text_time.setTextColor(0xFF8C000E);
                    text_time.setTextSize(45f);
                }
            }

            @Override
            public void onFinish() {
                text_time.setText("FINISH");
                finish=true;
                players.get(players.size()-1).setScore(points);
                final AlertDialog.Builder builder_finish =new AlertDialog.Builder(Game.this);
                builder_finish.setTitle(R.string.confirm_finish);
                builder_finish.setMessage("           " +points+ " pts.");
                builder_finish.setIcon(R.drawable.estrella);
                if(number==1) {
                    builder_finish.setPositiveButton(R.string.positive_finish, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            intentscore.putExtra(Score.key_score, (Serializable) players);
                            startActivity(intentscore);
                            finish();
                        }
                    });
                }
                else{
                    builder_finish.setPositiveButton(R.string.positive_next, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            intentstart.putExtra (key_start, (Serializable) players);
                            setResult(RESULT_OK,intentstart);
                            finish();
                            String text_np = String.format(getResources().getString(R.string.msg_np), number-1);
                            Toast toastnp = Toast.makeText(Game.this, text_np, Toast.LENGTH_SHORT);
                            toastnp.setGravity(Gravity.LEFT|Gravity.RIGHT,0,0);
                            toastnp.show();
                        }
                    });
                }
                if(!replay) {
                    builder_finish.setNegativeButton(R.string.negative_replay, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            replay = true;
                            Replay_Game();
                        }
                    });
                }
                builder_finish.setCancelable(false);
                builder_finish.create().show();
                mp_finish.start();
            }
        };

        cd_timer.start();
    }
}