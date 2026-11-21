package game;
import city.cs.engine.*;
import org.jbox2d.common.Vec2;

/*
    Level 1 Factory floor.
    The robot starts at ground level and must collect 3 energy credits
    while avoiding spikes. A pushable crate can be used as a stepping stone.
*/
public class Level1 extends Level{

    public Level1(Game game) {
        super(game); // no more tee passed in
    }

    @Override
    public void populate(){
        World tworld= getWorld();
        Robot tee= getRobot();

        // place robot at starting position for this level
        tee.setPosition(new Vec2(-8, -9f));
        tee.resetHealth(); // reset health at start of each level

        // ground
        StaticBody ground= new StaticBody(tworld,new BoxShape(30, 0.9f));
        ground.setPosition(new Vec2(0, -11.5f));
        ground.setFillColor(new java.awt.Color(120, 130, 140));
        ground.setLineColor(new java.awt.Color(80, 90, 100));

        // platforms
        Platform p1= new Platform(tworld, 5, -5, -3f);
        Platform p2= new Platform(tworld, 4, 6, 3f);
        Platform p3= new Platform(tworld, 5, -4, 9f);

        // spikes
        Spike spike1= new Spike(tworld);
        spike1.setPosition(new Vec2(-3f, -2f));
        spike1.addCollisionListener(new SpikeCollision(getGame()));

        Spike spike2= new Spike(tworld);
        spike2.setPosition(new Vec2(3f, -10.5f));
        spike2.addCollisionListener(new SpikeCollision(getGame()));

        // crate
        Crate crate= new Crate(tworld);
        crate.setPosition(new Vec2(2f, -10f));

        // credits — when all 3 collected, game moves to next level
        Credit credit1= new Credit(tworld);
        credit1.setPosition(new Vec2(-8, -2f));
        credit1.addCollisionListener(new CreditPickup(tee, getGame()));

        Credit credit2= new Credit(tworld);
        credit2.setPosition(new Vec2(6, 5f));
        credit2.addCollisionListener(new CreditPickup(tee, getGame()));

        Credit credit3= new Credit(tworld);
        credit3.setPosition(new Vec2(-4, 11f));
        credit3.addCollisionListener(new CreditPickup(tee, getGame()));
    }
}