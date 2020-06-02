package com.mygdx.game.objects;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Screens.MainMenuScreen;

public class Player extends Sprite
{
	public  World world;
	public  Body b2Body;

	private String name = "Default";
	public final String id = "Player";
	private float X = 32f;
	private float Y = 32f;
	private Array<TextureRegion> idleSprite;
	private Array<TextureRegion> upSprite;
	private Array<TextureRegion> downSprite;
	private Array<TextureRegion> leftSprite;
	private Array<TextureRegion> rightSprite;
	public  Animation<TextureRegion> playerAnim;
	private float animationSpeed = 0.2f;

	private boolean isRunning = false;
	private int health = 25;
	private float walkSpeed = 4.25f;
	private float runSpeed = 5.25f;
	private float speedMulti = 1f;
	private float maxSpeed = 1f;
	private String playerDirection = "idle";
	
	public Player(String name, MainMenuScreen screen, MyGdxGame game)
	{
		super(screen.playerSprite,43,88);
		this.name = name;
		this.world = screen.getWorld();

		idleSprite   = new Array<TextureRegion>();
		upSprite     = new Array<TextureRegion>();
		downSprite   = new Array<TextureRegion>();
		leftSprite   = new Array<TextureRegion>();
		rightSprite  = new Array<TextureRegion>();
		
		//tamanho do sprite 34/82
		for(int i = 0; i <=3; i++)
		{
			idleSprite.add(new TextureRegion(screen.playerSprite,24 +((34 * i) + (50 * i)),0,34,82));
		}
		for(int i = 4; i <=7; i++)
		{
			downSprite.add(new TextureRegion(screen.playerSprite,24 +((34 * i) + (50 * i)),0,34,82));
		}
		for(int i = 1; i <=5; i++)
		{
			upSprite.add(new TextureRegion(screen.playerSprite,24 +((34 * i) + (50 * i)),98,34,82));
		}
		for(int i = 6; i <=7; i++)
		{
			rightSprite.add(new TextureRegion(screen.playerSprite,24 +((34 * i) + (50 * i)),98,34,82));
		}
		for(int i = 0; i <=3; i++)
		{
			rightSprite.add(new TextureRegion(screen.playerSprite,24 +((34 * i) + (50 * i)),178,34,82));
		}
		for(int i = 4; i <=7; i++)
		{
			leftSprite.add(new TextureRegion(screen.playerSprite,24 +((34 * i) + (50 * i)),178,34,82));
		}
		for(int i = 0; i <=1; i++)
		{
			leftSprite.add(new TextureRegion(screen.playerSprite,24 +((34 * i) + (50 * i)),262,34,82));
		}
		setBounds(0, 0, 43 / MyGdxGame.PPM, 88 / MyGdxGame.PPM);
		setRegion(idleSprite.first());
		definePlayer();
	}
	
	public void definePlayer()
	{
		BodyDef bdef = new BodyDef();
		bdef.position.set(X/MyGdxGame.PPM,Y/MyGdxGame.PPM);
		bdef.type = BodyDef.BodyType.DynamicBody;
		bdef.linearDamping = 5f;
		b2Body = world.createBody(bdef);
		FixtureDef fdef = new FixtureDef();
		fdef.friction = 1f;
		CircleShape shape = new CircleShape();
		shape.setRadius(5/MyGdxGame.PPM);
		
		fdef.shape = shape;
		b2Body.createFixture(fdef).setUserData(this);
	}
	
	/**
	 * @param direction 
	 * The keyCode pressed to validate the direction of the character 
	 */
	public void Move(int direction)
	{
		switch(direction)
		{
			case Input.Keys.D:
			{
				if(b2Body.getLinearVelocity().x <= maxSpeed)
				{
					b2Body.applyLinearImpulse(new Vector2((walkSpeed * speedMulti * (isRunning ? runSpeed : 1))/MyGdxGame.PPM,0), b2Body.getWorldCenter(), true);
				}
				playerDirection = "RIGHT";
				break;
			}
			case Input.Keys.A:
			{
				if(b2Body.getLinearVelocity().x >= -maxSpeed)
				{
					b2Body.applyLinearImpulse(new Vector2(-(walkSpeed * speedMulti * (isRunning ? runSpeed : 1))/MyGdxGame.PPM,0), b2Body.getWorldCenter(), true);
				}
				playerDirection = "LEFT";
				break;
			}
			case Input.Keys.W:
			{
				if(b2Body.getLinearVelocity().y <= maxSpeed)
				{
					b2Body.applyLinearImpulse(new Vector2(0,(walkSpeed * speedMulti * (isRunning ? runSpeed : 1))/MyGdxGame.PPM), b2Body.getWorldCenter(), true);
				}
				playerDirection = "UP";
				break;
			}
			case Input.Keys.S:
			{
				if(b2Body.getLinearVelocity().y >= -maxSpeed)
				{
					b2Body.applyLinearImpulse(new Vector2(0,-(walkSpeed * speedMulti * (isRunning ? runSpeed : 1))/MyGdxGame.PPM), b2Body.getWorldCenter(), true);
				}
				playerDirection = "DOWN";
				break;
			}
			case 0:
			{
				playerDirection = "idle";
			}
		}
	}
	
	public float getX()
	{
		return X;
	}
	
	public float getY()
	{
		return Y;
	}
	
	public void setRunningState(boolean param)
	{
		isRunning = param;
		animationSpeed = param ? 0.2f : 0.4f;
	}


	public void update (SpriteBatch batch, float elapsedTime)
	{
		setPosition(b2Body.getPosition().x, b2Body.getPosition().y);
		setBounds(0, 0, 43 / MyGdxGame.PPM, 88 / MyGdxGame.PPM);
		switchDraw(playerDirection, batch, elapsedTime);
	}

	public void switchDraw(String state, SpriteBatch batch, float elapsedTime)
	{
		switch(state)
		{
			case "idle":
				playerAnim = new Animation<TextureRegion>(0.1f,idleSprite);
				batch.draw(playerAnim.getKeyFrame(elapsedTime,true), b2Body.getPosition().x, b2Body.getPosition().y);
				break;
			case "UP":
				playerAnim = new Animation<TextureRegion>(animationSpeed,upSprite);
				batch.draw(playerAnim.getKeyFrame(elapsedTime,true), b2Body.getPosition().x, b2Body.getPosition().y);
				break;
			case "DOWN":
				playerAnim = new Animation<TextureRegion>(animationSpeed,downSprite);
				batch.draw(playerAnim.getKeyFrame(elapsedTime,true), b2Body.getPosition().x, b2Body.getPosition().y);
				break;
			case "LEFT":
				playerAnim = new Animation<TextureRegion>(animationSpeed,leftSprite);
				batch.draw(playerAnim.getKeyFrame(elapsedTime,true), b2Body.getPosition().x, b2Body.getPosition().y);
				break;
			case "RIGHT":
				playerAnim = new Animation<TextureRegion>(animationSpeed,rightSprite);
				batch.draw(playerAnim.getKeyFrame(elapsedTime,true), b2Body.getPosition().x, b2Body.getPosition().y);
				break;
		}
		
	}
	
}
