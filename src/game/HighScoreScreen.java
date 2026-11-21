package game;
import javax.swing.*;
import java.awt.*;
import java.util.List;
/*
    Shows the top 5 high scores with a back button to return to the menu.
*/

public class HighScoreScreen extends JPanel{ //using the jpanel class to create a new screen for the high scores, this will be added to the main frame when the user clicks the high score button in the menu
    private SoundManager soundManager;

    public HighScoreScreen(JFrame frame, SoundManager soundManager, HighScoreManager highScoreManager){
        this.soundManager = soundManager; // Assign the passed-in SoundManager
        setLayout(new BorderLayout());
        setBackground(new Color(20,20, 30));

        //title
        JLabel title= new JLabel("HIGH SCORES", SwingConstants.CENTER);
        title.setFont(new Font("Courier", Font.BOLD, 48));
        title.setForeground(new Color(255, 200, 0));
        title.setBorder(BorderFactory.createEmptyBorder(50, 0, 30, 0));

        //scores list
        JPanel scoresPanel= new JPanel();
        scoresPanel.setLayout(new BoxLayout(scoresPanel, BoxLayout.Y_AXIS));
        scoresPanel.setBackground(new Color(20, 20, 30));
        scoresPanel.setBorder(BorderFactory.createEmptyBorder(0, 150, 0, 150));

        List<Integer> scores= highScoreManager.loadScores();
        // when its empty
        if (scores.isEmpty()){
            JLabel none= new JLabel("No scores yet — play the game!", SwingConstants.CENTER);
            none.setFont(new Font("Courier", Font.ITALIC, 22));
            none.setForeground(new Color(150, 150, 150));
            none.setAlignmentX(Component.CENTER_ALIGNMENT);
            scoresPanel.add(none);
        } else {
            String[] medals= {"1st", "2nd", "3rd", "4th", "5th"};
            for (int i= 0; i< scores.size(); i++) {
                JLabel row= new JLabel(medals[i]+ "   " +scores.get(i) +" credits", SwingConstants.CENTER);
                row.setFont(new Font("Courier", Font.BOLD, 28));
                row.setForeground(i== 0 ? new Color(255, 200, 0) : Color.WHITE);
                row.setAlignmentX(Component.CENTER_ALIGNMENT);
                row.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
                scoresPanel.add(row);
            }
        }
       //buttons to return to menu
        JButton backButton= new JButton("BACK");
        backButton.setFont(new Font("Courier", Font.BOLD, 20));
        backButton.setForeground(Color.WHITE);
        backButton.setBackground(new Color(40, 40, 60));
        backButton.setFocusPainted(false);
        backButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 200, 255), 2),
                BorderFactory.createEmptyBorder(10, 30, 10, 30)
        ));

        backButton.addActionListener(e ->{ //when pressed, it will play the menu click sound and return to the main menu
            frame.getContentPane().removeAll();
            frame.add(new MainMenu(frame, this.soundManager, highScoreManager));
            frame.revalidate();
            frame.repaint();
        });
//layout for the back button
        JPanel bottom= new JPanel();
        bottom.setBackground(new Color(20, 20, 30));
        bottom.setBorder(BorderFactory.createEmptyBorder(30, 0, 40, 0));
        bottom.add(backButton);
//add everything to the panel
        add(title, BorderLayout.NORTH);
        add(scoresPanel, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);
    }
}