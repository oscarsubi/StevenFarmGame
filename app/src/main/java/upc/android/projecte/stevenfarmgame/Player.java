package upc.android.projecte.stevenfarmgame;

//Class with Name, Mode, and Score

import java.io.Serializable;

public class Player implements Serializable {
    private String name;
    private int num;
    private int mode;
    private int score;


    public Player(String name, int num, int mode, int score) {
        this.name = name;
        this.num = num;
        this.mode = mode;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getNumber() {
        return num;
    }


    public int getMode() {
        return mode;
    }


    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }


    @Override
    public String toString() {
        String m = "EASY";
        if (mode==0) {m="EASY";}
        if (mode==1) {m="NORMAL";}
        if (mode==2) {m="XTREME";}
        return "> PLAYER "+num+": " + name+"\n"+
                "     · Mode: " + m +"\n"+
                "     · Punctuation: " + score;
    }
}
