package org.example.pacman;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;

import static android.widget.Toast.LENGTH_LONG;

/**
 *
 * This class should contain all your game logic
 */

public class Game {
    //context is a reference to the activity
    final int UP = 1;
    final int DOWN = 2;
    final int RIGHT = 3;
    final int LEFT = 4;
    public int direction=0;
    public int ghostDirection = 0;
    public Ghost ghost= new Ghost();
    private Context context;
    private int points = 0; //how points do we have
    //bitmap of the pacman
    private Bitmap pacBitmap;
    private Bitmap pacghost;
    //textview reference to points
    private TextView pointsView;
    private int pacx, pacy;
    private int ghostx, ghosty;

    //the list of goldcoins - initially empty

    private ArrayList<GoldCoin> coins = new ArrayList<>();

    private SoundPlayer soundPlayer;
    private Bitmap goldCoin;
    //a reference to the gameview
    private GameView gameView;
    private int h,w; //height and width of screen


    public Ghost getGhost() {
        return ghost;
    }

    public Game(Context context, TextView view, SoundPlayer soundPlayer)
    {
        this.context = context;
        this.pointsView = view;
        this.soundPlayer = soundPlayer;
        pacBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.pacman);
        goldCoin = BitmapFactory.decodeResource(context.getResources(), R.drawable.goldcoin);
        pacghost = BitmapFactory.decodeResource(context.getResources(), R.drawable.pacghost);
    }

    public void setGameView(GameView view)
    {
        this.gameView = view;
    }

    public void newGame()
    {
        ghost.setGhostx(50);
        ghost.setGhosty(600);
        ghost.setAlive(true);

        pacx = 50;
        pacy = 400; //just some starting coordinates
        //reset the points
        points = 0;

        pointsView.setText(context.getResources().getString(R.string.points)+" "+points);


        for (int i =0; i<5; i++){
            int randx=new Random().nextInt(400);
            int randy = new Random().nextInt(600);
            coins.add(new GoldCoin(randx, randy, context ));
        }

        gameView.invalidate(); //redraw screen
    }

    public void setSize(int h, int w)
    {
        this.h = h;
        this.w = w;
    }

    public void movePacmanRight(int pixels)
    {
        //still within our boundaries?
        if (pacx+pixels+pacBitmap.getWidth()<w) {
            pacx = pacx + pixels;
            doCollisionCheck();
            ghostCollision();
            direction = RIGHT;
            gameView.invalidate();

        }
    }
    public void movePacmanLeft(int pixels){
        if (pacx-pixels>0){
            pacx = pacx - pixels;
            doCollisionCheck();
            ghostCollision();
            direction = LEFT;
            gameView.invalidate();

        }
    }
    public void movePacmanUp (int pixels){
        if (pacy-pixels>0){
            pacy = pacy - pixels;
            doCollisionCheck();
           ghostCollision();
            direction = UP;
            gameView.invalidate();

        }
    }
    public void movePacmanDown (int pixels){
        if (pacy+pixels+pacBitmap.getHeight()<h){
            pacy = pacy + pixels;
            doCollisionCheck();
            ghostCollision();
            direction = DOWN;
            gameView.invalidate();

        }
    }
    public void moveGhostRight(int pixels)
    {
        //still within our boundaries?
        if (ghostx+pixels+pacghost.getWidth()<w) {
            ghostx = ghostx + pixels;
            ghost.setGhostx(ghostx);
            ghost.setGhostDirection(ghostDirection);
            gameView.invalidate();
        }
    }

    public void moveGhostLeft (int pixels){
        if (ghostx-pixels>0){
            ghostx = ghostx - pixels;
            ghost.setGhostx(ghostx);
            ghost.setGhostDirection(ghostDirection);
            gameView.invalidate();
        }
    }
    public void moveGhostUp (int pixels){
        if (ghosty-pixels>0){
            ghosty = ghosty - pixels;
            ghost.setGhosty(ghosty);
            ghost.setGhostDirection(ghostDirection);
            gameView.invalidate();

        }
    }

    public void moveGhostDown (int pixels){
        if (ghosty+pixels+pacghost.getHeight()<h){
            ghosty = ghosty + pixels;
            ghost.setGhosty(ghosty);
            ghost.setGhostDirection(ghostDirection);
            gameView.invalidate();
        }
    }

    public void doCollisionCheck()
    {
       for(GoldCoin coin : coins){
               if(Math.sqrt(Math.pow((double) pacx - (double) coin.getGoldx(), 2)+Math.pow((double) pacy - (double) coin.getGoldy(), 2)) < 100) {
                   if(coin.isTaken != true) { // if the coin  was not taken , we set the coin state to taken and then increment the score
                       coin.setTaken(true);
                       AddPoints();
                       soundPlayer.playFruitSound();
                   }
               }

        }
    }
    public void AddPoints ()
    {
        points++;
        pointsView.setText("Points: " + Integer.toString(points));
        if(getPoints() == 5) {
            Toast.makeText(this.context,"You won",Toast.LENGTH_LONG).show();
        }
    }

    public void ghostCollision(){

            double distance =Math.sqrt(Math.pow((double) pacx - (double) ghost.getGhostx(), 2)+Math.pow((double) pacy - (double) ghost.getGhosty(), 2));
        Log.d("distance",distance+"");
        Log.d("ghostx", ghost.getGhostx()+" ");
        Log.d ("ghosty", ghost.getGhosty()+"");
        if(distance<100){
            Log.d("here", "hit ");
            Log.d("alive", ghost.getAlive()+"ghostCollision ");
                if(ghost.getAlive() == true){
                    soundPlayer.playDeadSound();
                    ghost.setAlive(false);
                    gameOver(ghost);
                }
        }

    }

    public void gameOver(Ghost ghost) {
        if (ghost.isAlive == false){

            Toast.makeText(this.context, "You lost. New Game?", Toast.LENGTH_LONG).show();
        }
    }

    public int getPacx()
    {
        return pacx;
    }

    public int getPacy()
    {
        return pacy;
    }

    public int getPoints()
    {
        return points;
    }

    public ArrayList<GoldCoin> getCoins()
    {
        return coins;
    }

    public Bitmap getPacBitmap()
    {
        return pacBitmap;
    }

    public Bitmap getPacghost(){return pacghost; }

    public Bitmap getGoldCoin() {
        return goldCoin;
    }


}

