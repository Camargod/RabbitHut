package com.mygdx.game.objects;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class B2DSteering implements Steerable<Vector2> 
{
    Body body;
    float boundRadius;
    boolean tagger;
    float maxLinearSpeed,maxLinearAcceleration, maxAngularSpeed, maxAngularAcceleration;

    SteeringBehavior<Vector2> behavior;
    SteeringAcceleration<Vector2> steeringOutput;

    public B2DSteering(Body body,float boundRadius)
    {
        this.body = body;
        this.boundRadius = boundRadius;

        this.maxLinearSpeed = 5000;
        this.maxLinearAcceleration = 5000;
        this.maxAngularSpeed = 30;
        this.maxAngularAcceleration = 5;

        this.tagger = false;

        this.steeringOutput = new SteeringAcceleration<Vector2>(new Vector2());
    }
    public void update(float delta)
    {
        if(behavior != null)
        {
            behavior.calculateSteering(steeringOutput);
            applySteering(delta);
        }
    }

    public void applySteering(float delta)
    {
        boolean anyAccelerations = false;

        if(!steeringOutput.linear.isZero())
        {
            Vector2 force = steeringOutput.linear.scl(delta);
            body.applyForceToCenter(force, true);
            anyAccelerations = true;
        }

        if(anyAccelerations)
        {
            Vector2 velocity = body.getLinearVelocity();
            float currentSpeedSquare = velocity.len2();
            if(currentSpeedSquare > maxAngularSpeed * maxLinearSpeed)
            {
                body.setLinearVelocity(velocity.scl(maxAngularSpeed / (float) Math.sqrt(currentSpeedSquare)));
            }
        }
    }

    @Override
    public Vector2 getPosition() {
        // TODO Auto-generated method stub
        return body.getPosition();
    }

    @Override
    public float getOrientation() {
        // TODO Auto-generated method stub
        return body.getAngle();
    }

    @Override
    public void setOrientation(float orientation) {
        // TODO Auto-generated method stub

    }

    @Override
    public float vectorToAngle(Vector2 vector) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Vector2 angleToVector(Vector2 outVector, float angle) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Location<Vector2> newLocation() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public float getZeroLinearSpeedThreshold() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setZeroLinearSpeedThreshold(float value) {
        // TODO Auto-generated method stub

    }

    @Override
    public float getMaxLinearSpeed() {
        // TODO Auto-generated method stub
        return this.maxLinearSpeed;
    }

    @Override
    public void setMaxLinearSpeed(float maxLinearSpeed) {
        this.maxLinearSpeed = maxLinearSpeed;

    }

    @Override
    public float getMaxLinearAcceleration() {
        // TODO Auto-generated method stub
        return this.maxLinearAcceleration;
    }

    @Override
    public void setMaxLinearAcceleration(float maxLinearAcceleration) {
        this.maxLinearAcceleration = maxLinearAcceleration;
    }

    @Override
    public float getMaxAngularSpeed() {
        // TODO Auto-generated method stub
        return this.maxAngularSpeed;
    }

    @Override
    public void setMaxAngularSpeed(float maxAngularSpeed) {
        this.maxAngularSpeed = maxAngularSpeed;

    }

    @Override
    public float getMaxAngularAcceleration() {
        return this.maxAngularAcceleration;
    }

    @Override
    public void setMaxAngularAcceleration(float maxAngularAcceleration) 
    {
        this.maxAngularAcceleration = maxAngularAcceleration;
    }

    @Override
    public Vector2 getLinearVelocity() {
        // TODO Auto-generated method stub
        return body.getLinearVelocity();
    }

    @Override
    public float getAngularVelocity() {
        // TODO Auto-generated method stub
        return body.getAngularVelocity();
    }

    @Override
    public float getBoundingRadius() {
        // TODO Auto-generated method stub
        return boundRadius;
    }

    @Override
    public boolean isTagged() {
        // TODO Auto-generated method stub
        return tagger;
    }

    @Override
    public void setTagged(boolean tagged) {
       this.tagger = tagged;

    }

    public Body getBody()
    {
        return body;
    }

    public void setBehavior(SteeringBehavior<Vector2> behavior)
    {
        this.behavior = behavior;
    }

    public SteeringBehavior<Vector2> getBehavior()
    {
        return behavior;
    }
    
}