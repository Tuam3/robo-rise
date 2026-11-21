package game;
import city.cs.engine.*;
import org.jbox2d.common.Vec2;

/*
    Level 3 — Rooftop.
    The hardest level. Narrow platforms, spikes everywhere, and credits
    in very exposed positions. Background changes to a night sky rooftop.
*/
public class Level3 extends Level{
    public Level3(Game game){
        super(game);
    }

    @Override
    public void populate(){
        World tworld = getWorld();
        Robot tee = getRobot();

        tee.setPosition(new Vec2(-12, -9f));
        tee.resetHealth();

        //GROUND
        StaticBody ground = new StaticBody(tworld, new BoxShape(30, 0.9f));
        ground.setPosition(new Vec2(0, -11.5f));
        ground.setFillColor(new java.awt.Color(50, 50, 60));
        ground.setLineColor(new java.awt.Color(30, 30, 40));

        //MASSIVE MINEFIELD (Ground is covered in spikes)
        for (int i = 0; i < 7; i++) {
            Spike s = new Spike(tworld);
            s.setPosition(new Vec2(-5f + (i * 2.5f), -10.5f));
            s.addCollisionListener(new SpikeCollision(getGame()));
        }

        //CRATE BRIDGES (Using your original crate rules)
        Crate crate1 = new Crate(tworld);
        crate1.setPosition(new Vec2(-7f, -10f));
        Crate crate2 = new Crate(tworld);
        crate2.setPosition(new Vec2(5f, -10f));

        //THE CLIMB (Width = 3f for perfect image mapping)
        Platform p1 = new Platform(tworld, 3f, -8f, -2f);
        Platform p2 = new Platform(tworld, 3f, 7f, 3f);
        Platform p3 = new Platform(tworld, 3f, -4f, 8f);
        Platform p4 = new Platform(tworld, 3f, 3f, 14f);

        // TRAPS
        Spike trapSpike = new Spike(tworld);
        trapSpike.setPosition(new Vec2(-4f, 9f)); // On P3
        trapSpike.addCollisionListener(new SpikeCollision(getGame()));

        //CREDITS
        Credit credit1 = new Credit(tworld);
        credit1.setPosition(new Vec2(-8f, 0f)); // On P1
        credit1.addCollisionListener(new CreditPickup(tee, getGame()));

        Credit credit2 = new Credit(tworld);
        credit2.setPosition(new Vec2(7f, 5f)); // On P2
        credit2.addCollisionListener(new CreditPickup(tee, getGame()));

        Credit credit3 = new Credit(tworld);
        credit3.setPosition(new Vec2(3f, 16f)); // On P4 (Final Win)
        credit3.addCollisionListener(new CreditPickup(tee, getGame()));
    }
}