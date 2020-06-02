package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.InputProcessor.InputCore;
import com.mygdx.game.Scenes.Hud;
import com.mygdx.game.Tools.Box2DWorldCreator;
import com.mygdx.game.Tools.JSONDialogReader;
import com.mygdx.game.Tools.MyContactListener;
import com.mygdx.game.Tools.SoundPlayer;
import com.mygdx.game.objects.Npc;
import com.mygdx.game.objects.Player;

public class MainMenuScreen implements Screen 
{
	private InputProcessor InputCore;

	private MyGdxGame game;
	
	private Viewport gameViewPort;
	private Hud hud;
	public OrthographicCamera gameCamera;
	
	private TmxMapLoader maploader;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	
	private Player player;
	public Texture playerSprite;

	private Npc npcs[] = new Npc[3];
	
	private World world;
	private MyContactListener colisionTrigger;

	private Box2DWorldCreator worldColisions; 

	private JSONDialogReader dialogReader;

	public MainMenuScreen(MyGdxGame game)
	{
		this.game = game;
		world = new World(new Vector2(0,0), true);

		maploader = new TmxMapLoader();
		map = maploader.load("map002.tmx");

		playerSprite = new Texture(Gdx.files.internal("player/knight.png"));
		player = new Player("Gabriel", this, game);
		npcs[0]= new Npc(this,map,"NPC001");

		gameCamera = new OrthographicCamera();
		gameViewPort = new FitViewport((MyGdxGame.V_WIDTH/2.8f)/MyGdxGame.PPM,(MyGdxGame.V_HEIGHT/2.8f)/MyGdxGame.PPM,gameCamera);
		hud = new Hud(game.batch);

		dialogReader = new JSONDialogReader();
		
		colisionTrigger = new MyContactListener(hud);
		world.setContactListener(colisionTrigger);

		renderer = new OrthogonalTiledMapRenderer(map, 1 / MyGdxGame.PPM);
		SoundPlayer.rain();

		worldColisions = new Box2DWorldCreator(world,map);

		gameCamera.position.set(gameViewPort.getWorldWidth()/2, gameViewPort.getWorldHeight()/2,0);

		InputCore = new InputCore(gameCamera);
		Gdx.input.setInputProcessor(InputCore);
	}
	@Override
	public void show() {}

	public void handleInput(float dt)
	{
		if(Gdx.input.isKeyPressed(Input.Keys.D))
		{
			player.Move(Input.Keys.D);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.A))
		{
			player.Move(Input.Keys.A);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.W))
		{
			player.Move(Input.Keys.W);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.S))
		{
			player.Move(Input.Keys.S);
		}
		if(!Gdx.input.isKeyPressed(Input.Keys.S) && !Gdx.input.isKeyPressed(Input.Keys.W) && !Gdx.input.isKeyPressed(Input.Keys.A) && !Gdx.input.isKeyPressed(Input.Keys.D))
		{
			player.Move(0);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))
		{
			player.setRunningState(true);
		}
		if(!Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))
		{
			player.setRunningState(false);
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.E) && colisionTrigger.hasNpcPlayerColision)
		{
			if(!hud.alreadyInDialog)
			{
				hud.setSpeechArray(dialogReader.setActualSpeech(colisionTrigger.activeNpc));
				hud.updateSpeech();
			} 
			else
			{
				if(!hud.updateSpeech())
				{
					hud.resetDialog();
				}
			}
		}
	}
	
	public void update(float dt)
	{
		handleInput(dt);
		world.step(1/60f, 6, 2);
		gameCamera.position.x = player.b2Body.getPosition().x;
		gameCamera.position.y = player.b2Body.getPosition().y;
		gameCamera.update();
		hud.update(dt);
		renderer.setView(gameCamera);
	}

	@Override
	public void render(float delta) 
	{
		game.elapsedTime += Gdx.graphics.getDeltaTime();

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		

		renderer.render();

		worldColisions.b2dr.render(world, gameCamera.combined);
		//hud.stage.getCamera().combined
		game.batch.setProjectionMatrix(gameCamera.combined);
		game.batch.begin();
		player.update(game.batch,game.elapsedTime);
		game.batch.end();
		hud.stage.draw();
		update(delta);
	}

	@Override
	public void resize(int width, int height) 
	{
		gameViewPort.update(width, height);
	}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void hide() {}

	@Override
	public void dispose() {}

	public World getWorld()
	{
		return world;
	}
	
}
