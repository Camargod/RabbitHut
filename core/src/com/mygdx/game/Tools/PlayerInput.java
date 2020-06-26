package com.mygdx.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.game.Scenes.Hud;
import com.mygdx.game.objects.Player;

public class PlayerInput 
{
    public static void InputHandleMovement(Player player, Hud hud)
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
    }    
    public static void InputHandleTriggerDialogs(Player player, Hud hud, MyContactListener colisionTrigger, JSONDialogReader dialogReader)
    {
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
}