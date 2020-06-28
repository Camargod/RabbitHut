package com.mygdx.game.objects;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Screens.MainMenuScreen;

import com.badlogic.gdx.ai.steer.behaviors.Arrive;

public class Npc
{
	public String id;

	public Box2DDebugRenderer b2dr = new Box2DDebugRenderer();

	World world;
    
	BodyDef bdef = new BodyDef();
	PolygonShape shape = new PolygonShape();
    FixtureDef fixtDef = new FixtureDef();
	Body body;
	
	public B2DSteering ai;

	public Npc(MainMenuScreen screen,TiledMap map,String id)
	{
		this.world = screen.getWorld();
		this.id = id;
		if(map != null)
		{
			definePlayer(map);
		}
	}

	public Npc(MainMenuScreen screen,String id, Float size)
	{
		this.world = screen.getWorld();
		this.id = id;
		definePlayer(size);
	}
	
	public void definePlayer(TiledMap map)
	{
		MapObject objectNpcArea = map.getLayers().get(id).getObjects().getByType(RectangleMapObject.class).first();
		if(objectNpcArea != null)
		{
			Rectangle rect = ((RectangleMapObject) objectNpcArea).getRectangle();
			
			bdef.type = BodyDef.BodyType.StaticBody;
			bdef.position.set((rect.getX() + rect.getWidth() /2)/ MyGdxGame.PPM, (rect.getY() + rect.getHeight()/2)/MyGdxGame.PPM);
			
			body = world.createBody(bdef);
			shape.setAsBox((rect.getWidth()/2)/MyGdxGame.PPM, (rect.getHeight()/2)/MyGdxGame.PPM);
			
			fixtDef.shape = shape;
			fixtDef.isSensor= true;
			this.ai = new B2DSteering(body, 10);
			body.createFixture(fixtDef).setUserData(this);
		}
	}

	public void definePlayer(Float size)
	{
		
		bdef.position.set(20/MyGdxGame.PPM,20/MyGdxGame.PPM);
		bdef.type = BodyDef.BodyType.DynamicBody;
		bdef.linearDamping = 5f;

		body = world.createBody(bdef);
		CircleShape shape = new CircleShape();
		shape.setRadius(size/MyGdxGame.PPM);
		
		fixtDef.shape = shape;
		fixtDef.friction = 1f;
		fixtDef.isSensor= false;
		this.ai = new B2DSteering(body, 10);
		body.createFixture(fixtDef).setUserData(this);
		
	} 

	public void setAI(Player followEntity)
	{
		Arrive<Vector2> arriveBehavior = new Arrive<Vector2>(this.ai,followEntity.ai).setTimeToTarget(0.03f).setArrivalTolerance(0.3f).setDecelerationRadius(10);
		ai.setBehavior(arriveBehavior);
	}

}
