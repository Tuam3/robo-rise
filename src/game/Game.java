package game;
import city.cs.engine.*;
import org.jbox2d.common.Vec2;
import javax.swing.*; //swing used for the UI components like the win screen and main menu
import java.awt.*; // awt is used for stuff like colors, fonts and layout managers in the UI
/*
    The main GAME class is responsible for managing the overall game state, including:
. Keeping track of the current level and the player's robot (Tee)
. Handling transitions between levels when the player collects enough credits
. Managing the game timer and checking for game over conditions
. Displaying the end game screens (win/lose) and saving high scores
*/
public class Game {

    private int lastScore= 0;
    private static final int CREDITS_PER_LEVEL = 3; // credits needed to finish a level

    private Robot tee; // the player character

    private Level currentLevel;
    private int currentLevelNumber;
    private boolean isTransitioning= false;

    private JFrame frame;
    private MyView view;

    private SoundManager soundManager;//calling the sounds
    private HighScoreManager highScoreManager;

    private int timeLeft = 60; //60 seconds per level
    private Timer gameTimer;

// -----------called by MainMenu when play is clicked, receives the existing frame
public Game(JFrame frame, SoundManager soundManager, HighScoreManager highScoreManager){
        this.frame= frame;
        this.soundManager= soundManager; // Use the passed-in instance
        this.highScoreManager= highScoreManager; // Use the passed-in instance
        this.currentLevelNumber= 1;
        loadLevel(1); //start on level 1
        }

// ------stops the current level and loads the next one
    private void loadLevel(int levelNumber){

        // stop the timer for the old level if it exists, and start a new one for the new level
        if (gameTimer!= null) gameTimer.stop();
        timeLeft= 60; // Reset time for the new level
        gameTimer= new Timer(1000, e -> {
            timeLeft--;
            if (timeLeft<= 0) {
                tee.takeDamage(5); //die if time runs out
                gameTimer.stop();
            }
        });
        gameTimer.start();

// we save the total credits and health from the old level so we can apply them to the new robot in the new level
        int savedTotalCredits= 0;
        int savedHealth= 5;
        if (tee != null) {
            savedTotalCredits= tee.getTotalCredits();
            savedHealth= tee.getHealth();
        }
        if (currentLevel!= null){
            currentLevel.stop();
            frame.remove(view);
        }
        for (java.awt.event.KeyListener kl: frame.getKeyListeners()){
            frame.removeKeyListener(kl);
        }
        currentLevelNumber= levelNumber;
        // creatING the right level and play its music
        if (levelNumber== 1){ //will call the soundmanager so it can stop appropriate and play the right music for each level
            soundManager.playMusic("data/music1.wav"); //main menu and level1 music
            currentLevel= new Level1(this);
        } else if (levelNumber== 2){
            soundManager.playMusic("data/music2.wav"); //level 2 music
            currentLevel= new Level2(this);
        } else if (levelNumber== 3){
            soundManager.playMusic("data/music3.wav"); //level 3 music
            currentLevel= new Level3(this);
        } else {
            soundManager.stopMusic();
            endGame();
            return;
        }

        //NEW ROBOT AND SAVED STATS
        tee = currentLevel.getRobot();
        tee.setTotalCredits(savedTotalCredits); //Keep total score!
        tee.setHealth(savedHealth);             //Keep health!
        tee.resetLevelCredits();
        isTransitioning = false; //Unlock the transition for the new level

    //view and fram setup
        view = new MyView(currentLevel.getWorld(), tee, 600, 600, levelNumber, this);
        view.setZoom(20);

        frame.add(view);
        frame.pack();
        frame.setVisible(true);

// add controller AFTER getting tee from the level
        frame.addKeyListener(new Controller(tee));
        frame.setFocusable(true);
        frame.requestFocus();

        currentLevel.getWorld().addStepListener(new StepListener() {
            @Override
            public void preStep(StepEvent e) {
                if (!tee.isAlive()) {
                    currentLevel.stop();
                    showGameOver();
                }
            }
            @Override
            public void postStep(StepEvent e) {}
        });

        // Camera settings to follow the tee robot
        currentLevel.getWorld().addStepListener(new StepListener() {
            @Override
            public void preStep(StepEvent e) {}
            @Override
            public void postStep(StepEvent e) {
                float robotY = tee.getPosition().y; //Get the robot's current Y position
                //Set the camera center.
                float cameraY = Math.max(robotY, -5f); // doesn't go lower than the ground (e.g., -5f)
                view.setCentre(new Vec2(0, cameraY));
            }
        });

// start the level
        currentLevel.start();
    }

//------called by CreditPickup every time a credit is collected
    public void checkLevelComplete(){
        soundManager.playSound("data/collect.wav");
        lastScore= tee.getTotalCredits();
        if (tee.getCredits() >= CREDITS_PER_LEVEL && !isTransitioning){ // Check if we hit 3 credits, AND make sure we aren't ALREADY transitioning
            isTransitioning = true; // Lock it so we don't trigger this twice!
            Timer timer = new Timer(2000, e -> { //2 SECS DELAY
                loadLevel(currentLevelNumber + 1); // Load the next level after the delay
            });
            timer.setRepeats(false); // Only do this once
            timer.start();
        }
    }

//-------called by SpikeCollision when the robot takes damage
    public void notifyDamage(){
        if (tee!= null) {
            lastScore= tee.getTotalCredits(); //lastScore updated whenever something happens
        }
        soundManager.playSound("data/hurt.wav");
    }

//-------shows the win and lose screen and saves the score when all levels are done
    private void showEndScreen(String title, Color titleColour, String message,int timeAtEnd ) {
        if (gameTimer!= null) //when the game eds
            gameTimer.stop(); //stop the timer so it doesn't keep running!
        soundManager.stopMusic(); //stop any music that might be playing

        JPanel screen= new JPanel(new BorderLayout());
        screen.setBackground(new Color(20,20,30));
        screen.setPreferredSize(new Dimension(600,600));

//create the title label (win or lose)
        JLabel titleLabel= new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Impact", Font.BOLD, 72));
        titleLabel.setForeground(titleColour);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(60, 0, 20, 0));

//create the Score Label
        JLabel messageLabel= new JLabel(message, SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial Black", Font.PLAIN, 28));
        messageLabel.setForeground(Color.YELLOW);
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

//new timer Label
        JLabel timeLabel= new JLabel("Time Remaining: " + timeAtEnd + "s", SwingConstants.CENTER);
        timeLabel.setFont(new Font("Arial Black", Font.PLAIN, 22));
        timeLabel.setForeground(new Color(0, 200, 255));
        timeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

//buttons in the centre of the screen to go back to the main menu
        JButton menuButton = makeButton("MAIN MENU"); // Use your existing button style
        menuButton.addActionListener(e -> {
            frame.getContentPane().removeAll();
            frame.add(new MainMenu(frame, soundManager, highScoreManager)); // Pass global instances to MainMenu
            frame.revalidate();
            frame.repaint();
        });

// Pack it all into the center panel
        JPanel centre = new JPanel();
        centre.setLayout(new BoxLayout(centre, BoxLayout.Y_AXIS));
        centre.setBackground(new Color(20, 20, 30));

        centre.add(messageLabel);
        centre.add(Box.createRigidArea(new Dimension(0, 10)));
        centre.add(timeLabel); // Add the time label here!
        centre.add(Box.createRigidArea(new Dimension(0, 40)));
        centre.add(menuButton);

        screen.add(titleLabel, BorderLayout.NORTH);
        screen.add(centre, BorderLayout.CENTER);

        frame.getContentPane().removeAll();
        frame.add(screen);
        frame.revalidate();
        frame.repaint();
    }
//called when the player finishes all 3 levels, shows the win screen and saves the score
    private void endGame() {
        int finalScore= tee.getTotalCredits();
        highScoreManager.saveScore(finalScore);
        showEndScreen("YOU ESCAPED!", Color.GREEN, "Total credits: " + finalScore, timeLeft);
    }

//called when the player dies, shows the game over screen and saves the score
    private void showGameOver() {
        // make sure we have the latest score before showing game over
        int finalScore = tee.getTotalCredits();
        highScoreManager.saveScore(finalScore);
        showEndScreen("GAME OVER", Color.RED, "Credits collected: " + finalScore, timeLeft);
    }

    // entry point shows the main menu first before anything else
    public static void main(String[] args) {
        JFrame frame= new JFrame("Robo Rise");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);
        frame.setResizable(false);
        frame.setPreferredSize(new Dimension(600, 600));


        SoundManager globalSoundManager = new SoundManager(); //will be able to play sounds and music across the whole game, and stop them when needed
        HighScoreManager globalHighScoreManager = new HighScoreManager();
        frame.add(new MainMenu(frame, globalSoundManager, globalHighScoreManager)); // Pass them to MainMenu

        frame.pack();
        frame.setVisible(true);
    }

    public int getTimeLeft() {
        return timeLeft; //getter for the time left, used by the view to display the timer
    }

    public void playJumpSound() {
        soundManager.playSound("data/jump.wav"); //called by the Robot class when it jumps, to play the jump sound effect
    }

    //helper method styles the buttons for the Win/Loss screens
    private JButton makeButton(String text) {
        JButton button= new JButton(text);
        button.setFont(new Font("Arial Black", Font.BOLD, 22));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(40, 40, 60));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 200, 255), 2),
                BorderFactory.createEmptyBorder(12, 30, 12, 30)
        ));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        return button;
    }
}