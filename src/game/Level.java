package game;
import city.cs.engine.*;
/*
    Abstract base class for all levels
    Every level extends this and gets a world, robot and view for free
    Each level must implement populate() to place its own bodies in the world
*/
public abstract class Level {
    private World tworld;
    private Robot tee;
    private MyView view;
    private Game game; //ref back to game class so we can trigger level transitions

    public Level(Game game) {
        this.game= game;
        this.tworld= new World();//the world is created too
        this.tee = new Robot(tworld, game); //tee is created in the world
    }
    // each level must implement this to place its own platforms, spikes, credits etc
    public abstract void populate();

    // called after populate() — starts the physics
    public void start() {
        populate();
        tworld.start();
    }

    // stop and clean up this level
    public void stop() {
        tworld.stop();
    }

    // getters and setters for the world, robot and view so the level can access them
    public World getWorld() { return tworld; }
    public Robot getRobot() { return tee; }
    public Game getGame() { return game; }
// view is set by the Game class when it creates the view, but we need a getter and setter for it so the level can access it to trigger level transitions
    public void setView(MyView view) { this.view = view; }
    public MyView getView() { return view; }
}
