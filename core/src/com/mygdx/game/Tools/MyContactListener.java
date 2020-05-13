package com.mygdx.game.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.objects.Npc;
import com.mygdx.game.objects.Player;

public class MyContactListener implements ContactListener {

    public Boolean hasNpcPlayerColision = false;
    public String activeNpc;

    @Override
    public void beginContact(Contact contact) 
    {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();
        if(fa == null || fb == null || fa.getUserData() == null || fb.getUserData() == null) return;
        hasNpcPlayerColision = validateColisionPlayerNpc(fa, fb);
        setActiveNpcId(fa,fb);
    }

    @Override
    public void endContact(Contact contact) 
    {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();
        if(fa == null || fb == null || fa.getUserData() == null || fb.getUserData() == null) return;
        if(hasNpcPlayerColision && validateColisionPlayerNpc(fa, fb))
        {
            hasNpcPlayerColision = !hasNpcPlayerColision;
        }
        activeNpc = "";
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) 
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) 
    {
        // TODO Auto-generated method stub
    }

    private boolean validateColisionPlayerNpc(Fixture fa, Fixture fb)
    {
        return fa.getUserData() instanceof Player && fb.getUserData() instanceof Npc || fa.getUserData() instanceof Npc && fb.getUserData() instanceof Player;
    }

    private void setActiveNpcId(Fixture fa, Fixture fb)
    {
        if(fa.getUserData() instanceof Npc)
        {
            Npc npc = (Npc) fa.getUserData();
            activeNpc = npc.id;
        } 
        else
        {
            Npc npc = (Npc) fb.getUserData();
            activeNpc = npc.id;
        } 
    }
    
}