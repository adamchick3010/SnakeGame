import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;


public class GameOverScene  extends JFrame  {

    public static final int FRAME_WIDTH = 250;
    public static final int FRAME_HEIGTH = 250;
    public static final int BUTTON_WIDTH = 200;
    public static final int BUTTON_HEIGTH = 30;
    public static final int TEXTFIELD_WIDTH = 200;
    public static final int TEXTFIELD_HEIGTH = 30;
    public static final String file = "scores.txt";
    private int score;

    JLabel scoreLabel;
    JButton saveButton;
    JTextField nameTextField;


    GameOverScene(int score,KeyL keyListener) {
        this.score = score;
        this.setTitle("Game Over");
        this.setSize(FRAME_WIDTH, FRAME_HEIGTH);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(null);
        this.layoutComponents();
        this.setVisible(true);
        this.getContentPane().setBackground(new Color(164, 55, 189));
    }


    private void layoutComponents() {

        this.scoreLabel = new JLabel("<html>GAME OVER!<br/>Your score is: " + Integer.toString(this.score)+"<br/> Enter your name and save score</html>");
        this.scoreLabel.setForeground(Color.WHITE);
        this.scoreLabel.setBounds(FRAME_WIDTH/2-TEXTFIELD_WIDTH/2, 30, TEXTFIELD_WIDTH, 50);
        this.add(scoreLabel);
        this.nameTextField = new JTextField();
        this.nameTextField.setBounds(FRAME_WIDTH/2-TEXTFIELD_WIDTH/2, 90, TEXTFIELD_WIDTH, TEXTFIELD_HEIGTH);
        this.add(nameTextField);
        this.saveButton = new JButton();
        this.saveButton.setBounds(FRAME_WIDTH/2-BUTTON_WIDTH/2, 120, BUTTON_WIDTH, BUTTON_HEIGTH);
        this.saveButton.setText("SAVE");
        this.saveButton.addActionListener(e -> submitScore());
        this.add(saveButton);
    }


    private void submitScore() {
        this.saveScore(nameTextField.getText(), this.score);
        this.dispose();
    }


    private void saveScore(String name, int points) {
        try {
            FileWriter myWriter = new FileWriter(file, true);
            myWriter.write(name + "," + points + "\n");
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
