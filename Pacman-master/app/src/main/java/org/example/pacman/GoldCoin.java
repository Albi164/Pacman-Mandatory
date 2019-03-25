package org.example.pacman;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * This class should contain information about a single goldcoin.
 * such as x and y coordinates (int) and whether or not the goldcoin
 * has been taken (boolean)
 */

public class GoldCoin {
    public int goldx;
    public int goldy;
    public boolean isTaken;
    private Bitmap goldCoin;


    public int getGoldx(){

        return goldx;
    }
    public int getGoldy(){
        return goldy;
    }


    public Bitmap getGoldCoin() {
        return goldCoin;
    }

    public GoldCoin(int goldx, int goldy, Context context){
        this.goldx = goldx;
        this.goldy = goldy;
        this.isTaken = false;
        goldCoin = BitmapFactory.decodeResource(context.getResources(), R.drawable.goldcoin);
    }

    public void setTaken(boolean taken) {
        isTaken = taken;
    }
}
