package com.mygdx.game.objects;


import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Screens.MainMenuScreen;

public class Npc
{
	public String id;

	public Box2DDebugRenderer b2dr = new Box2DDebugRenderer();

	World world;
    
    BodyDef bdef = new BodyDef();
    PolygonShape shape = new PolygonShape();
    FixtureDef fixtDef = new FixtureDef();
	Body body;
	
	
	public Npc(MainMenuScreen screen,TiledMap map,String id)
	{
		super();
		this.world = screen.getWorld();
		this.id = id;
		definePlayer(map);
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
			body.createFixture(fixtDef).setUserData(this);
		}
	}

}
