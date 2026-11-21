package game;
import city.cs.engine.*;
import org.jbox2d.common.Vec2;
/*
        A heavy crate the robot can push around
        Being a DynamicBody, it reacts to physics (gravity, collisions)
        The player can shove it to create a makeshift step for reaching higher platforms
 */
public class Crate extends DynamicBody{
    private static final Shape crateShape= new PolygonShape(-1.64f,1.16f, 1.7f,1.2f, 2.58f,0.56f, 2.6f,-0.74f, 1.83f,-1.37f, -1.81f,-1.35f, -2.61f,-0.75f, -2.63f,0.56f); //its a bit long but had to be done to fit the image
    private static final BodyImage crateImage= new BodyImage("data/crate.png",2.5f);

    public Crate(World world){
        super(world, crateShape);
        addImage(crateImage);

        SolidFixture fixture= new SolidFixture(this,crateShape); //this will make the crate heavier and harder to push, making it more of a challenge to use as a step
        fixture.setFriction(0.8f); //some friction to prevent it from sliding too much
        fixture.setDensity(3f);//more mass

// Lock the y position so the crate only moves horizontally
        final float groundY= -10f;
// The steplistner will allow us to set the y position and vertical velocity to 0 every step, effectively locking the crate to the ground
        world.addStepListener(new StepListener(){
            @Override
            public void preStep(StepEvent e){ //in the event that the crate is pushed up or down, this will reset it to the ground level and stop any vertical movement
                setPosition(new Vec2(getPosition().x,groundY)); //setting the y position to the ground level every step
                setLinearVelocity(new Vec2(getLinearVelocity().x,0)); //setting the vertical velocity to 0 every step to prevent it from moving up or down
            }
            @Override
            public void postStep(StepEvent e){} //nothing to be done later, but we need to implement this method to satisfy the StepListener interface
        });
    }
}