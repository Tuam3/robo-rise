package game;
import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;
/*
        Attached to every Spike. When the robot touches a spike it loses 1 health.
 */
public class SpikeCollision implements CollisionListener{

    private Game game;

    public SpikeCollision(Game game) {
        this.game = game;
    }

    // called by the physics engine whenever the spike touches another body
    @Override
    public void collide(CollisionEvent e) { //in the event that a collision happens, this method is called by the physics engine automatically
        if (e.getOtherBody() instanceof Robot) {
            Robot tee= (Robot) e.getOtherBody(); //getotherbody is the robot, we need to cast it to a Robot so we can call the takeDamage method on it
            tee.takeDamage(1);
            game.notifyDamage(); // play the hurt sound
        }
    }
}