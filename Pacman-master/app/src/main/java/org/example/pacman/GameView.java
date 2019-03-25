package org.example.pacman;

import android.content.Context;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class GameView extends View {

	Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pacman);
	Bitmap pacGhost = BitmapFactory.decodeResource(getResources(), R.drawable.pacghost);
	int pacx = 50;
	int pacy = 400;
	int ghostx;
	int ghosty;
	Game game;
    int h,w; //used for storing our height and width of the view

	public void setGame(Game game)
	{
		this.game = game;
	}

	public void reset()
	{
		pacx = 50;
		invalidate();
	}

	public void move(int x)
	{
		if (ghostx+pacGhost.getWidth()<w)
		ghostx = ghostx+x;
		//still within our boundaries?
		if (pacx+x+bitmap.getWidth()<w)
			pacx=pacx+x;
		invalidate(); //redraw everything
	}


	/* The next 3 constructors are needed for the Android view system,
	when we have a custom view.
	 */
	public GameView(Context context) {
		super(context);

	}

	public GameView(Context context, AttributeSet attrs) {
		super(context,attrs);
	}


	public GameView(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context,attrs,defStyleAttr);
	}

	//In the onDraw we put all our code that should be
	//drawn whenever we update the screen.
	@Override
	protected void onDraw(Canvas canvas) {
		//Here we get the height and weight
		h = canvas.getHeight();
		w = canvas.getWidth();
		//update the size for the canvas to the game.
		game.setSize(h,w);
		Log.d("GAMEVIEW","h = "+h+", w = "+w);
		//Making a new paint object
		Paint paint = new Paint();
		canvas.drawColor(Color.WHITE); //clear entire canvas to white color

		//draw the pacman
		canvas.drawBitmap(game.getPacghost(), game.getGhost().getGhostx(), game.getGhost().getGhosty(), paint);


		for (GoldCoin coin : game.getCoins())
		{
			canvas.drawBitmap(game.getPacBitmap(), game.getPacx(),game.getPacy(), paint);
			if(coin.isTaken == false)
			{
				canvas.drawBitmap(coin.getGoldCoin(), coin.getGoldx(), coin.getGoldy(), paint);
			}
		}

		super.onDraw(canvas);
	}

}
