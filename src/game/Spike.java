package game;
import city.cs.engine.*;
/*
        A stationary spike hazard The robot loses health on contact
        Spikes are StaticBodies so they never move
 */
public class Spike extends StaticBody {
    private static final Shape spikeShape= new BoxShape(1f,0.5f); // spikes never move
    private static final BodyImage spikeImage= new BodyImage("data/spike.png", 2.0f);

    public Spike(World Tworld) {
        super(Tworld);  // no shape here
        addImage(spikeImage);

        SolidFixture fixture = new SolidFixture(this,spikeShape); // attach the shape as a solid fixture
        fixture.setFriction(1.5f);
        fixture.setDensity(5f);
    }
}