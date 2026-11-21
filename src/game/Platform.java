package game;
import city.cs.engine.*;
import org.jbox2d.common.Vec2;
/*
A simple platform that the robot can jump on. Platforms are StaticBodies so they never move.
*/
public class Platform extends StaticBody {

    public Platform(World world, float width, float x, float y) {
        super(world, new BoxShape(width, 0.5f));
        setPosition(new Vec2(x, y)); //parameters for position of the platform we can use later
        addImage(new BodyImage("data/platform.png", 1.7f));
    }
}