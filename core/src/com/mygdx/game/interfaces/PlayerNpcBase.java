package com.mygdx.game.interfaces;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGdxGame;


public class PlayerNpcBase 
{
    private Array<TextureRegion> idleSprite;
	private Array<TextureRegion> upSprite;
	private Array<TextureRegion> downSprite;
	private Array<TextureRegion> leftSprite;
	private Array<TextureRegion> rightSprite;
	public  Animation<TextureRegion> playerAnim;
    public Texture playerSprite;
    
    private float animationSpeed = 0.2f;

    protected boolean isRunning = false;
    
    private float spriteWidth = 34f;
    private float spriteHeight = 64f;
    
    protected String playerDirection = "idle";


    public PlayerNpcBase()
    {
        playerSprite = new Texture(Gdx.files.internal("player/knight.png"));


		idleSprite   = new Array<TextureRegion>();
		upSprite     = new Array<TextureRegion>();
		downSprite   = new Array<TextureRegion>();
		leftSprite   = new Array<TextureRegion>();
		rightSprite  = new Array<TextureRegion>();
		
		for(int i = 0; i <=3; i++)
		{
			idleSprite.add(new TextureRegion(playerSprite,24 +((34 * i) + (50 * i)),0,34,82));
		}
		for(int i = 4; i <=7; i++)
		{
			downSprite.add(new TextureRegion(playerSprite,24 +((34 * i) + (50 * i)),0,34,82));
		}
		for(int i = 1; i <=5; i++)
		{
			upSprite.add(new TextureRegion(playerSprite,24 +((34 * i) + (50 * i)),98,34,82));
		}
		for(int i = 6; i <=7; i++)
		{
			rightSprite.add(new TextureRegion(playerSprite,24 +((34 * i) + (50 * i)),98,34,82));
		}
		for(int i = 0; i <=3; i++)
		{
			rightSprite.add(new TextureRegion(playerSprite,24 +((34 * i) + (50 * i)),178,34,82));
		}
		for(int i = 4; i <=7; i++)
		{
			leftSprite.add(new TextureRegion(playerSprite,24 +((34 * i) + (50 * i)),178,34,82));
		}
		for(int i = 0; i <=1; i++)
		{
			leftSprite.add(new TextureRegion(playerSprite,24 +((34 * i) + (50 * i)),262,34,82));
		}
    }

    public void setRunningState(boolean param)
	{
		isRunning = param;
		animationSpeed = param ? 0.2f : 0.4f;
	}


	public void update (SpriteBatch batch, float elapsedTime,Body b2Body)
	{
		switchDraw(playerDirection, batch, elapsedTime,b2Body);
	}

	public void switchDraw(String state, SpriteBatch batch, float elapsedTime,Body b2Body)
	{
		switch(state)
		{
			case "idle":
				playerAnim = new Animation<TextureRegion>(0.1f,idleSprite);
				batch.draw(playerAnim.getKeyFrame(elapsedTime,true), drawPosX(b2Body), drawPosY(b2Body), drawValueWidth(), drawValueHeight());
				break; 
			case "UP": 
				playerAnim = new Animation<TextureRegion>(animationSpeed,upSprite); 
				batch.draw(playerAnim.getKeyFrame(elapsedTime,true), drawPosX(b2Body), drawPosY(b2Body), drawValueWidth(), drawValueHeight());
				break; 
			case "DOWN": 
				playerAnim = new Animation<TextureRegion>(animationSpeed,downSprite); 
				batch.draw(playerAnim.getKeyFrame(elapsedTime,true), drawPosX(b2Body), drawPosY(b2Body), drawValueWidth(), drawValueHeight());
				break; 
			case "LEFT": 
				playerAnim = new Animation<TextureRegion>(animationSpeed,leftSprite); 
				batch.draw(playerAnim.getKeyFrame(elapsedTime,true), drawPosX(b2Body), drawPosY(b2Body), drawValueWidth(), drawValueHeight());
				break; 
			case "RIGHT": 
				playerAnim = new Animation<TextureRegion>(animationSpeed,rightSprite); 
				batch.draw(playerAnim.getKeyFrame(elapsedTime,true), drawPosX(b2Body), drawPosY(b2Body), drawValueWidth(), drawValueHeight());
				break;
		}
	}

	private float drawPosX(Body b2Body)
	{
		return b2Body.getPosition().x - (spriteWidth / 2 / 3  / MyGdxGame.PPM);
	}
	private float drawPosY(Body b2Body)
	{
		return b2Body.getPosition().y - (spriteHeight / 2 / 10f  / MyGdxGame.PPM);
	}

	private float drawValueWidth()
	{
		return spriteWidth / MyGdxGame.PPM / 2.5f;
	}
	private float drawValueHeight()
	{
		return spriteHeight / MyGdxGame.PPM / 2;
	}
}