package GTN;
import javax.swing.*;
import javax.swing.border.Border;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
public class GuessTheNumberBetter extends JFrame{

    private JLabel title, subtitle, tries, info;
    private JTextField userInput;
    private String message = "";
    int computerNumber = (int) (Math.random()*20 + 1);
    int userAnswer = 0; 
    int count = 0;
    ArrayList<Player> leaderboard;
    public GuessTheNumberBetter () {
        leaderboard =  HighScoreDAO.getLeaderboard();
        message = createMessage(leaderboard);
        initUi();
        
    }

    private String createMessage(ArrayList<Player> players) {
        String message = "\n\nCURRENT   LEADERBOARD   (TOP 10)\nName   -   Score\n--------------------------\n";
        int c = 1;
        for (Player p : leaderboard) {
            message += c + ".   " + p.getName() + "   -   " + p.getScore() + "\n\n";
            c++;
        }
        message += "--------------------------\n";
        return message;
    }

    private void initUi() {
        System.out.println(computerNumber + " ");
        computerNumber = (int) (Math.random()*20 + 1);
        userAnswer = 0; 
        count = 0;
        ImageIcon img = new ImageIcon("D:\\eclipse-workspace\\GuessTheNumber\\src\\GTN\\icon.png");
        setIconImage(img.getImage());
        setTitle("Guess the number");
        setSize(400, 600);
        GridLayout layout = new GridLayout(10, 1);
        GridLayout inp = new GridLayout(1, 3);
        inp.setHgap(20);
        setLayout(layout);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JPanel input = new JPanel();
        JPanel bottom = new JPanel();
        JButton submit = new JButton("Go!");
        JButton leader = new JButton("Leaderboard");
        JButton reset = new JButton("Reset Game.");

        input.setLayout(inp);
        Border padding = BorderFactory.createEmptyBorder(8, 8, 8, 8);
        input.setBorder(padding);
        bottom.setLayout(inp);
        bottom.setBorder(padding);
        title = new JLabel("Guess the Number!", JLabel.CENTER);
        info = new JLabel("", JLabel.CENTER);
        subtitle = new JLabel("Enter a number between 1 and 20", JLabel.CENTER);
        tries = new JLabel("Tries till now: 0", JLabel.CENTER);
        userInput = new JTextField();
        userInput.setToolTipText("A number between 0 and 20.");
        userInput.setHorizontalAlignment(JTextField.CENTER);
        input.add(userInput);
        input.add(submit);
        JLabel logo = new JLabel(img);
        add(logo);
        add(title);
        add(subtitle);
        add(info);
        add(input);
        add(tries);
        bottom.add(leader);
        bottom.add(reset);
        add(bottom);
        setVisible(true);

        submit.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if (userInput.getText().length() == 0) {
                    info.setText("Empty Response. Please Enter a Number");
                    info.setForeground(Color.RED);
                } else {
                    try {
                        count++;
                        tries.setText("Tries till now: " + count);
                        int ans = Integer.parseInt(userInput.getText().toString());
                        if (ans == computerNumber) {
                            boolean isHighscore= HighScoreDAO.isHighScore(count);
                            info.setForeground(Color.GREEN);
                            String m = isHighscore ? "Correct!"+ "\n And that's a high score!" : "Correct!";
                            info.setText("");
                            info.setText(m + " Click the reset button to play again");
                            showUserNameOption(m);
                        } else {
                            info.setText("");
                            info.setText(determineGuess(ans, computerNumber, count));
                        }
                        
                    } catch (NumberFormatException excep) {
                        info.setText("Invalid Input. Please Enter a number between 1 and 20");
                        info.setForeground(Color.RED);
                    }
                    
                }
                userInput.setText("");
                
            }
        });


        leader.addActionListener((event) -> {
            leaderboard = HighScoreDAO.getLeaderboard();
            JOptionPane.showMessageDialog(GuessTheNumberBetter.this, createMessage(leaderboard), "Leaderboard", JOptionPane.PLAIN_MESSAGE);
        });

        reset.addActionListener((event) -> {
            resetAll();
        });
    }

    private void resetAll() {
        computerNumber = (int) (Math.random()*20 + 1);
        userAnswer = 0; 
        count = 0;
        info.setText("");
        userInput.setText("");
    }

    private String determineGuess(int userAnswer, int computerNumber, int count){
        int diff = computerNumber - userAnswer;
        if (userAnswer <=0 || userAnswer >20) {
            info.setForeground(Color.RED);
            return "Your guess is invalid";
        } else if (Math.abs(diff)  <= 1) {
            info.setForeground(Color.ORANGE);
            return "You are close! Try again.";
        } else if (userAnswer > computerNumber) {
            info.setForeground(Color.BLUE);
            return "Your guess, "+ userAnswer + " is too high, try again.";
        } else if (userAnswer < computerNumber) {
            info.setForeground(Color.BLUE);
            return "Your guess, "+ userAnswer + " is too low, try again.";
        } else {
            return "Your guess is incorrect\n" ;
        }
    }

    private void showUserNameOption (String m) {
        String name = JOptionPane.showInputDialog(null, 
                                m + "\nEnter your name", "You Won!", 3);
        if (name != null && name.length() > 1) {
            HighScoreDAO.addScore(new Player(name, count));
            resetAll();
        }
    }
}