package game;
import city.cs.engine.*;
import org.jbox2d.common.Vec2;

/*
    Level 2 — Underground Mine.
    Tighter platforms, more spikes, and credits placed in harder to reach spots.
    Background changes to a dark mine environment.
*/
public class Level2 extends Level{
    public Level2(Game game){
        super(game);
    }

    @Override
    public void populate(){
        World tworld= getWorld();
        Robot tee= getRobot();

        tee.setPosition(new Vec2(-12, -9f));
        tee.resetHealth();

        //GROUND
        StaticBody ground= new StaticBody(tworld, new BoxShape(30, 0.9f));
        ground.setPosition(new Vec2(0, -11.5f));
        ground.setFillColor(new java.awt.Color(60, 40, 20));
        ground.setLineColor(new java.awt.Color(30, 20, 10));

        //SPIKES
        for (int i= 0; i< 4; i++) {
            Spike s= new Spike(tworld);
            s.setPosition(new Vec2(-2f+ (i * 3f), -10.5f));
            s.addCollisionListener(new SpikeCollision(getGame()));
        }

        //ORIGINAL CRATE
        Crate crate1= new Crate(tworld);
        crate1.setPosition(new Vec2(-7f, -10f));
        Crate crate2= new Crate(tworld);
        crate2.setPosition(new Vec2(5f, -10f));

        //PLATFORM Spaced out nicely for the 12f jump velocity.
        Platform p1= new Platform(tworld,3f,-8f,-3f);
        Platform p2= new Platform(tworld,3f,6f,1f);
        Platform p3= new Platform(tworld,3f,-5f,6f);
        Platform p4= new Platform(tworld,3f,4f,11f);

        //TRAPS
        Spike trapSpike= new Spike(tworld);
        trapSpike.setPosition(new Vec2(4f, 2f)); // On Platform 2
        trapSpike.addCollisionListener(new SpikeCollision(getGame()));

        //CREDITS
        Credit credit1= new Credit(tworld);
        credit1.setPosition(new Vec2(-8f, -1f)); // On P1
        credit1.addCollisionListener(new CreditPickup(tee, getGame()));

        Credit credit2= new Credit(tworld);
        credit2.setPosition(new Vec2(-5f, 8f)); // On P3
        credit2.addCollisionListener(new CreditPickup(tee, getGame()));

        Credit credit3= new Credit(tworld);
        credit3.setPosition(new Vec2(4f, 13f)); // On P4 (Win)
        credit3.addCollisionListener(new CreditPickup(tee, getGame()));
    }
}