package com.mygdx.game.objects;

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
import com.mygdx.game.interfaces.PlayerNpcBase;

public class Player extends PlayerNpcBase
{
	public  World world;
	public  Body b2Body;

	private String name = "Default";
	public final String id = "Player";
	private float X = 32f;
	private float Y = 32f;

	public  Animation<TextureRegion> playerAnim;
	public Texture playerSprite;

	private float walkSpeed = 4.25f;
	private float runSpeed = 5.25f;
	private float speedMulti = 1f;
	private float maxSpeed = 1f;


	public B2DSteering ai;
	
	public Player(String name, MainMenuScreen screen, MyGdxGame game)
	{
		super();
		this.name = name;
		this.world = screen.getWorld();

	

		// //tamanho do sprite 34/82
		// for(int i = 0; i <=3; i++)
		// {
		// 	idleSprite.add(new TextureRegion(playerSprite,16 +((spriteWidth * i) + (29 * i)),0, 16 +((spriteWidth * i) + (29 * i)) + spriteWidth,spriteHeight));
		// }
		// for(int i = 0; i <=3; i++)
		// {
		// 	upSprite.add(new TextureRegion(playerSprite,17 +((spriteWidth * i) + (34 * i)),98,spriteWidth,spriteHeight));
		// }
		// for(int i = 0; i <=3; i++)
		// {
		// 	rightSprite.add(new TextureRegion(playerSprite,17 +((spriteWidth * i) + (34 * i)),178,spriteWidth,spriteHeight));
		// }
		// for(int i = 0; i <=3; i++)
		// {
		// 	leftSprite.add(new TextureRegion(playerSprite,17 +((spriteWidth * i) + (34 * i)),178,spriteWidth,spriteHeight));
		// }

		// for(int i = 4; i <=7; i++)
		// {
		// 	downSprite.add(new TextureRegion(playerSprite,24 +((34 * i) + (50 * i)),0,34,82));
		// }
		
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
		ai = new B2DSteering(this.b2Body, 10);
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
	

	
}
