package game;
import javax.swing.*;
import java.awt.*;

/*
    The main menu shown when the game launches.
    Play starts the game, High Scores shows the leaderboard, Quit exits.
    Uses BorderLayout and BoxLayout to position everything.
*/

public class MainMenu extends JPanel{ //using the jpanel class since its got the needed layout managers

    private JFrame frame; //the main frame of the game, this will be used to switch between the menu, game, and high score screens by adding and removing panels from it
    private SoundManager soundManager; // ADDED: Reference to the global SoundManager
    private HighScoreManager highScoreManager; //to check high scores from the menu
    private Image MImage; //background image for menu

    public MainMenu(JFrame frame, SoundManager soundManager, HighScoreManager highScoreManager) {
        this.frame= frame;
        this.highScoreManager= new HighScoreManager();
        this.MImage = new ImageIcon("data/Mbackground.png").getImage();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        soundManager.playMusic("data/music1.wav"); // Play music when menu appears

        // title
        JLabel title=new JLabel("ROBO-RISE", SwingConstants.CENTER); //using a title rather than image
        title.setFont(new Font("Impact", Font.BOLD, 75));
        title.setForeground(new Color(228, 210, 166));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle=new JLabel("escape the factory", SwingConstants.CENTER); //level 1 is a factory, so this is the subtitle
        subtitle.setFont(new Font("Arial", Font.ITALIC, 24));
        subtitle.setForeground(Color.WHITE);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        // buttons

        JButton playButton= makeButton("PLAY");
        JButton highScoreButton= makeButton("SCORES");
        JButton quitButton= makeButton("QUIT");

        playButton.addActionListener(e -> { // when play is clicked, we clear the frame and start the game
            frame.getContentPane().removeAll();
            frame.revalidate(); //make sure the old scrn is fully cleared before we add the game view
            frame.repaint();
            new Game(frame, soundManager, highScoreManager); //from the claass Game, this will create the world, view, and controller and start the game loop
        });

        highScoreButton.addActionListener(e ->{
            frame.getContentPane().removeAll();
            frame.add(new HighScoreScreen(frame, soundManager, highScoreManager)); // Pass global instances to HighScoreScreen //inherits the high score manager so it can load the scores to display
            frame.revalidate();
            frame.repaint();
        });

        quitButton.addActionListener(e -> System.exit(0)); //exits the game when quit is clicked
        //layout
        add(Box.createVerticalGlue());
        add(title);
        add(subtitle);
        add(Box.createRigidArea(new Dimension(0, 50))); // Big gap before buttons
        add(playButton);
        add(Box.createRigidArea(new Dimension(0, 15)));
        add(highScoreButton);
        add(Box.createRigidArea(new Dimension(0, 15)));
        add(quitButton);
        add(Box.createVerticalGlue());
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the background image to fill the whole screen
        if (MImage!= null) {
            g.drawImage(MImage, 0, 0, getWidth(),getHeight(),this);
        }
        g.setColor(new Color(0, 0, 0, 130)); // 130 is the DARK transparency level
        g.fillRect(0, 0, getWidth(), getHeight());
    }

//method to create a button with the same styling for all buttons in the menu that will be used for all
    private JButton makeButton(String text){
        JButton button= new JButton(text);
        button.setFont(new Font("Arial Black", Font.BOLD, 22));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(40, 40, 60));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 200, 255), 2),
                BorderFactory.createEmptyBorder(12, 30, 12, 30)
        ));

        //the button to be centered and have a consistent size
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(300, 60));
        return button;
    }
}