package game;
import city.cs.engine.*;
import java.awt.*;
import javax.swing.ImageIcon;
/*
 * Custom view that renders the game world and draws a HUD overlay.

 * HUD shows:
 *   Credit count (battery icon + number)   top-left
 *   Health bar (heart icons, one per HP)   top-right
 *   "GAME OVER" screen when the robot dies
 *  "YOU WON" screen when the robot collects enough credits
 */
public class MyView extends UserView {
    private Robot tee;
    private int levelNumber;
    private Game game;

    // Load icons once so we're not re-loading every frame (60 fps)
    private final Image batteryIcon= new ImageIcon("data/battery.png").getImage();
    private final Image heartIcon= new ImageIcon("data/heart.png").getImage();
    private final Image bg1= new ImageIcon("data/background.png").getImage();       // factory
    private final Image bg2= new ImageIcon("data/background2.png").getImage();      // mine
    private final Image bg3= new ImageIcon("data/background3.png").getImage();      // rooftop

    public MyView(World Tworld, Robot tee,int width,int height,int levelNumber, Game game){ // takes the world, robot, dimensions of the view, and and the level number
        super(Tworld,width,height);
        this.tee= tee;
        this.levelNumber = levelNumber;
        this.game = game;
    }

// override the paintBackground and paintForeground methods to draw my custom HUD elements on top of the game world
@Override
protected void paintBackground(Graphics2D g) {
    // pick the right background for this level
    Image bg;
    if (levelNumber== 1) {
        bg = bg1;
    } else if (levelNumber== 2) {
        bg = bg2;
    } else {
        bg = bg3;
    }
    g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
}

    @Override
    protected void paintForeground(Graphics2D g){

        //a dark box behind the credit counter
        g.setColor(new Color(0, 0, 0, 160));
        g.fillRoundRect(10,10,160,45,12,12);

        //the battery icon and credit counter
        g.drawImage(batteryIcon, 18,16,30,30, this);
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Courier", Font.BOLD,22));
        g.drawString("x " + tee.getCredits(), 58,39);

        //level indicator (top-centre)
        g.setColor(new Color(0, 0, 0, 160));
        g.fillRoundRect(230, 10, 130, 45, 12, 12);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Courier", Font.BOLD, 20));
        g.drawString("LEVEL " + levelNumber, 248, 39);

        // draw a dark box behind the hearts
        g.setColor(new Color(0,0, 0,160));
        g.fillRoundRect(350,10,190,45,12,12);

        // draw 5 hearts, greyed out if health is lost
        int heartX = 358; // the x position of the first heart, the rest will be spaced 34 pixels apart (30 for the heart + 4 for padding)
        for (int i = 0; i < 5; i++) { // a loop so that the heart dissapears one by one as health is lost
            if (i < tee.getHealth()) { // if the current heart index is less than the player's health
                g.drawImage(heartIcon, heartX, 16,28,28,this); //draw a normal heart
            }
            else {
                g.drawImage(heartIcon, heartX, 16, 28, 28, this); //
                g.setColor(new Color(0, 0, 0, 150));//dark overlay on top
                g.fillRect(heartX, 16, 28, 28);//covers it to make it look empty
            }
            heartX = heartX + 34;
        }

        //level complete screen when they move on
        if (tee.getCredits() >= 3 && levelNumber < 3){
            g.setColor(new Color(0, 0, 0, 180));
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.GREEN);
            g.setFont(new Font("Courier", Font.BOLD, 52));
            g.drawString("LEVEL COMPLETE!", 60, 250);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Courier", Font.PLAIN, 26));
            g.drawString("Loading next level...", 160, 310);
        }

        // show win screen if player collected all credits
        if (tee.getCredits() >= 3 && levelNumber == 3) { //if the player has completed all 3 levels, show the win screen
            g.setColor(new Color(0, 0, 0, 180));
            g.fillRect(0, 0, getWidth(), getHeight());

            g.setColor(Color.GREEN);
            g.setFont(new Font("Courier", Font.BOLD, 72));
            g.drawString("YOU WON!!!", 100, 250);

            g.setColor(Color.YELLOW);
            g.setFont(new Font("Courier", Font.PLAIN, 32));
            g.drawString("Energy collected: " + tee.getCredits(), 120, 310);
        }

        // the Timer box (Top Center)
        g.setColor(new Color(0, 0, 0, 160));
        g.fillRoundRect(230, 60, 130, 40, 12, 12); // Put it under the level indicator
        // Change color to red if less than 10 seconds left
        if (game.getTimeLeft() < 10) {
            g.setColor(Color.RED);
        } else {
            g.setColor(Color.WHITE);
        }
        g.setFont(new Font("Courier", Font.BOLD, 20));
        g.drawString("TIME: " + game.getTimeLeft(), 255, 87);
    }
}