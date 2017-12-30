package upc.android.projecte.stevenfarmgame;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;



public class Start extends AppCompatActivity {

    private ArrayList<Player> list_players;
    //Array idnumberbuttons
    private int[] idnumberbuttons ={
            R.id.b_1, R.id.b_2, R.id.b_3, R.id.b_4, R.id.b_5, R.id.b_6
    };
    private RelativeLayout rl_number;
    private TextView text_number;

    private EditText name;
    private RadioGroup rgmode;
    private int[] idmode = {R.id.rb_easy, R.id.rb_normal, R.id.rb_xtreme};
    private ImageButton b_next;

    private boolean selection = false;
    private int inp, points=0;
    private Player player;
    private MediaPlayer mp_go, mp_start,mp_nbuttons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        rl_number = (RelativeLayout) findViewById(R.id.rl_number);
        text_number = (TextView) findViewById(R.id.text_number);
        mp_nbuttons = MediaPlayer.create(this,R.raw.pop);

        rgmode = (RadioGroup) findViewById(R.id.rg_mode);
        b_next = (ImageButton) findViewById(R.id.b_next);
        name = (EditText) findViewById(R.id.name);

        mp_go = MediaPlayer.create(this,R.raw.start);
        mp_start = MediaPlayer.create(this,R.raw.options);

        if (selection==false) {
            //Number of players selection
            for (int i = 0; i < idnumberbuttons.length; i++) {
                Select_Number(i);
            }
            mp_start.start();
            b_next.setVisibility(View.INVISIBLE);
            name.setVisibility(View.INVISIBLE);
        }

        if(false) {
            list_players =null;
            // list = llegirLlistaDeFitxer
        }
        else {
            list_players = new ArrayList<Player>();
        }

        //Go to Game
        b_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Create_Game();
            }
        });
    }


    //Buttons number creation
    private void Select_Number(final int i) {
        final Button bnumber = (Button) findViewById(idnumberbuttons[i]);
        bnumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selection = true;
                mp_nbuttons.start();
                rl_number.setVisibility(View.GONE);
                text_number.setVisibility(View.GONE);
                bnumber.setVisibility(View.GONE);
                b_next.setVisibility(View.VISIBLE);
                name.setVisibility(View.VISIBLE);
                ((RadioButton) findViewById(idmode[0])).setChecked(true);
                inp = i+1;
                String text_np = String.format(getResources().getString(R.string.msg_np), inp);
                Toast toastnp = Toast.makeText(Start.this, text_np, Toast.LENGTH_SHORT);
                toastnp.setGravity(Gravity.LEFT|Gravity.RIGHT,0,0);
                toastnp.show();
            }
        });
    }


    //Conditions to start Game
    private void Create_Game() {
        int imode=0;

        Intent intent = new Intent(this, Game.class);
        String sname = name.getText().toString();
        int idselected = rgmode.getCheckedRadioButtonId();

        //Name and Mode control
        if(!sname.isEmpty()){

            //Mode Game
            for(int i=0; i<idmode.length; i++) {
                if (idselected == idmode[i]) {
                    imode = i;
                }
                else{
                    (findViewById(idmode[i])).setClickable(false);
                }
            }

            mp_go.start();

            //Create a Player
            player = new Player(sname,inp,imode,points);

            list_players.add(player);

            intent.putExtra(Game.key_player, list_players);
            startActivityForResult(intent,Game.request_name);
            Next_Player();
        }
        else{
            Toast toastn = Toast.makeText(Start.this, R.string.msg_name, Toast.LENGTH_SHORT);
            toastn.setGravity(Gravity.CENTER|Gravity.CENTER,0,0);
            toastn.show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intentstart){
        if(requestCode==Game.request_name){
            if (resultCode==RESULT_OK){
               list_players = (ArrayList<Player>) intentstart.getExtras().getSerializable(Game.key_start);
            }
        }
        super.onActivityResult(requestCode, resultCode, intentstart);
    }

    //Decrement player number
    private void Next_Player() {
        if (inp==1){
            selection=false;
            finish();
        }
        else {
            inp = inp - 1;
            name.setText(null);
        }
    }
}
