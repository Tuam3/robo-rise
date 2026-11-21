package game;
import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;
/*
        Attached to every Credit.
        When the robot touches a credit it gains 1 credit and the credit disappears.
 */
public class CreditPickup implements CollisionListener{

    private Robot tee;
    private Game game;

    public CreditPickup(Robot tee, Game game){ //it will ref the robot and the game
        this.tee= tee;
        this.game= game;
    }
    // This method is CALLED by the physics engine automatically
    // every time the object this listener is attached to hits something.
    @Override
    public void collide(CollisionEvent e){
        if (e.getOtherBody() instanceof Robot){
            tee.addCredits(1);//add credits to the robot
            e.getReportingBody().destroy(); // remove the collected credit
            game.checkLevelComplete(); //check if the level is complete
        }
    }
}

