package com.mygdx.game.Scenes;

import java.time.Instant;
import java.util.Date;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import com.mygdx.game.MyGdxGame;

public class Hud 
{
	public Stage stage;
	private Viewport viewport;
	
	private Date worldTimer;
	
	private Label timeLabel;

	private Label dialogLabel;
	private String dialogString;

	public String[] speeches;
	private int actualSpeech = 0;
	public boolean alreadyInDialog;

	public Hud(SpriteBatch sb)
	{
		worldTimer = Date.from(Instant.now());
		
		viewport = new FitViewport(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT, new OrthographicCamera());
		stage = new Stage(viewport,sb);
		
		Table table = new Table();
		table.top();
		table.setFillParent(true);
		
		timeLabel = new Label(worldTimer.toString(), new Label.LabelStyle(new BitmapFont(),Color.WHITE));
		dialogLabel = new Label("texto", new Label.LabelStyle(new BitmapFont(),Color.WHITE));

		table.add(timeLabel).expandX().padTop(10).right();
		table.row();
		table.add(dialogLabel).expandY().center().padBottom(180);
		
		stage.addActor(table);

	}
	public void update(float dt)
	{
		worldTimer = Date.from(Instant.now());
		timeLabel.setText(worldTimer.toString());
	}
	public boolean updateSpeech()
	{
		if(speeches != null)
		{
			alreadyInDialog = true;
			dialogLabel.setText(speeches[actualSpeech]);
			if(actualSpeech < speeches.length)
			{
				actualSpeech++;
				return true;
			}
			resetDialog();
			return false;
		}
		return false;
	}
	public void setSpeechArray(String[] speeches)
	{
		this.speeches = speeches;
	}
	public void resetDialog()
	{
		dialogLabel.setText("");
	}
}
