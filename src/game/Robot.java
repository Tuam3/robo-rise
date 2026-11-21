package game;
import city.cs.engine.*;
/*
        The main character of the game
        The player controls the robot and tries to collect credits and avoid hazards
        The robot can move left and right and jump
        It has health and can take damage from hazards
        It can also collect credits
 */

public class Robot extends Walker{

    private static final Shape robotShape= new BoxShape(1,2);

    private static final BodyImage robotRight= new BodyImage("data/robot_idle.png",4f);
    private static final BodyImage robotLeft= new BodyImage("data/robot_idle_left.png",4f);
    private static final BodyImage robotJumpRight= new BodyImage("data/robot_jump.png",4f);
    private static final BodyImage robotJumpLeft= new BodyImage("data/robot_jump_left.png",4f);

    private boolean facingLeft= false; //setting the default facing direction to right
    private boolean isJumping= false; // robot starts on the ground

    private int credits= 0;
    private int totalCredits = 0; // credits across all levels
    private int health= 5;
    private boolean alive= true; // robot starts alive

    private int jumpCount = 0;
    private Game game;

    public Robot(World Tworld, Game game) { //getting the world as a parameter so we can create the robot in the world
        super(Tworld, robotShape);
        addImage(robotRight);
        this.game= game;
    }

    public void faceLeft() { //method to change the robot's facing direction to left
        facingLeft= true;
        if (!isJumping) { //only change the image if the robot is not jumping, otherwise it will mess with the jump animation
            removeAllImages(); //removes everything so the new image can take place
            addImage(robotLeft);
        }
    }

    public void faceRight() {//same for right as left but with the right images
        facingLeft= false;
        if (!isJumping) {
            removeAllImages();
            addImage(robotRight);
        }
    }

    public void showJump() { //method to change the robot's image to the jumping image when it jumps
        isJumping= true;
        removeAllImages();
        if (facingLeft) { //if the robot is facing left, show the left jump image, otherwise show the right jump image
            addImage(robotJumpLeft);
        } else {
            addImage(robotJumpRight);
        }
    }

    public void land(){ //method to change the robot's image back to the idle image when it lands
        isJumping= false; //set to false
        removeAllImages();
        if (facingLeft){
            addImage(robotLeft);
        } else{
            addImage(robotRight);
        }
    }


    // ──Credits ───────────────────────────────────────────────────────────────
    public int getCredits(){
        return credits; } // credits collected in the current level (shown in the HUD)

    public int getTotalCredits() {
        return totalCredits; } // returns the total credits across all levels, we can use this to show the final score

    public void addCredits(int amount){ //method to add credits when the robot collects them
        credits += amount;
        totalCredits+= amount; //add to total too

    }

    public void resetLevelCredits() { //resets level credits when a new level loads — total stays
        credits= 0;
    }

    // ── Health ────────────────────────────────────────────────────────────────
    public int getHealth() {
        return health; } // shown as hearts in the HUD

    public boolean isAlive(){
        return alive; }

    public void resetHealth() {
        health = 5; //resets health back to 5, can be used when restarting the game or starting a new level
        alive  = true;
    }

//to take damage when the robot is hit by an enemy or hazard
    public void takeDamage(int amount){
        if (!alive) return; //if dead already, don't take more damage
        health-= amount; //subtract the damage amount from health

        if (health<= 0) { //if health is 0 or less, the robot dies
            health= 0; //set health to 0 so it doesn't go negative
            alive = false; //not alive anymore
            destroy(); //then destroy the robot so it disappears from the world and can't interact with anything else

        }
    }
    // restore health carried over from the previous level
    public void setHealth(int savedHealth) {
        this.health = savedHealth;
        if (this.health <= 0) {
            this.alive = false;
        }
    }

    public void setTotalCredits(int savedTotalCredits) {
        this.totalCredits = savedTotalCredits;
    }

    public Game getGame() {
        return game;
    }
}