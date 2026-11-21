package game;
import city.cs.engine.*;
/*
        A credit that the robot can pick up.
        Credits are DynamicBodies so they can be affected by forces and collisions.
        However, set their gravity scale to 0 so they won't fall to the floor.
 */
public class Credit extends DynamicBody{
    private static final Shape energyShape= new PolygonShape(-0.34f,1.42f, 0.31f,1.42f, 0.68f,0.98f, 0.7f,-1.18f, -0.69f,-1.2f, -0.71f,0.99f, -0.4f,1.38f);
    private static final BodyImage energyImage= new BodyImage("data/credit.png",3.0f);

    public Credit(World Tworld) {
        super(Tworld,energyShape); // create the body with the custom polygon shape
        addImage(energyImage);
        this.setGravityScale(0);// We don't want the credits to fall to the floor
    }
}

