package com.mygdx.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

public class JSONDialogReader 
{
    private final JsonReader reader;
    private JsonValue file;
    private JsonValue npc;
    private JsonValue npcDialog;    
    public JSONDialogReader()
    {
        reader = new JsonReader();
    }
    public String[] setActualSpeech(String npcId)
    {
        file = reader.parse(Gdx.files.internal("dialogs.json"));
        npc = file.get(npcId);
        npcDialog = npc.get("dialogs");
        return npcDialog.asStringArray();
    }
}