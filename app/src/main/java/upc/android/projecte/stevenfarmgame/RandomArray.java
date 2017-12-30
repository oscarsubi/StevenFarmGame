package upc.android.projecte.stevenfarmgame;

import java.util.Random;

//Create class with Random values

public class RandomArray {
    private int[] valors;

    private Random numdigletts;

    public RandomArray(int tamanyMax,int valorMax) {
        numdigletts = new Random();
        int nd = numdigletts.nextInt(tamanyMax)+1;
        valors = new int[nd];
        for(int i=0; i<valors.length; i++) {
            valors[i] = newValor(i,valorMax);
        }
    }

    public int[] getValors() {
        return valors;
    }

    private int newValor(int i,int valorMax) {
        int d= numdigletts.nextInt(valorMax);
        int j = 0;
        while(j<i) {
            if(valors[j]==d) {
                d = numdigletts.nextInt(valorMax);
                j = 0;
            }
            else {
                j++;
            }
        }
        return  d;
    }

}
