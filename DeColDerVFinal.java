/***************************************
 * CSCI 185 M05 Spring 2022
 * Ivan Yu, Benjamin Cheung, Albert Chen
 * Final Programming Project
 * 5/3/2022
****************************************/

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DeColDerVFinal extends JFrame {
    private JPanel[][] spaces = new JPanel[5][5];
    private JLabel[][] labels = new JLabel[5][5];
    private Color[] guess = new Color[5];
    private ArrayList<Color> answerSequence = new ArrayList<Color>();

    private final ArrayList<Color> COLOR_SET_ONE = new ArrayList<Color>(Arrays.asList(Color.RED, Color.GREEN, Color.ORANGE, Color.YELLOW));
    private final ArrayList<Color> redSecretColors = new ArrayList<Color>(Arrays.asList(Color.RED, Color.BLUE, Color.MAGENTA));
    private final ArrayList<Color> greenSecretColors = new ArrayList<Color>(Arrays.asList(Color.GREEN, Color.GRAY, Color.MAGENTA));
    private final ArrayList<Color> orangeSecretColors = new ArrayList<Color>(Arrays.asList(Color.ORANGE, Color.PINK, Color.GRAY));
    private final ArrayList<Color> yellowSecretColors = new ArrayList<Color>(Arrays.asList(Color.YELLOW, Color.BLUE, Color.PINK));
    private final ArrayList<ArrayList<Color>> setOneSecretColors = new ArrayList<ArrayList<Color>>(Arrays.asList(redSecretColors, greenSecretColors, orangeSecretColors, yellowSecretColors));
    private final ArrayList<Color> COLOR_SET_TWO = new ArrayList<Color>(Arrays.asList(Color.BLUE, Color.MAGENTA, Color.PINK, Color.GRAY));
    
    private JButton pinkButton = new JButton("Pink");
    private JButton redButton = new JButton("Red");
    private JButton orangeButton = new JButton("Orange");
    private JButton yellowButton = new JButton("Yellow");
    private JButton greenButton = new JButton("Green");
    private JButton blueButton = new JButton("Blue");
    private JButton magentaButton = new JButton("Magenta");
    private JButton grayButton = new JButton("Gray");
    private JButton deleteButton = new JButton("Delete");
    private JButton enterButton = new JButton("Enter");

    private ArrayList<Color> dynColorList = new ArrayList<Color>(Arrays.asList(Color.RED, Color.GREEN, Color.ORANGE, Color.YELLOW, Color.BLUE, Color.GRAY));
    private Color[] colors  = {Color.PINK, Color.RED, Color.ORANGE, Color.YELLOW, Color.green, Color.BLUE, Color.MAGENTA, Color.GRAY};
    private JButton[] buttons = {pinkButton, redButton, orangeButton, yellowButton, greenButton, blueButton, magentaButton, grayButton};
    
    private int rowNum = 0, colNum = 0, con = 3, guesses = 0;
    private Random rand = new Random();

    public DeColDerVFinal() {
        super("DeColDer");
        setLocation(100, 50);
        setSize(900, 750);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel northPanel = new JPanel();
        JLabel title = new JLabel("De-Col-Der");
        title.setForeground(Color.WHITE);
        northPanel.add(title);
        northPanel.setBackground(Color.BLACK);
        add(northPanel, BorderLayout.NORTH);

        JPanel eastPanel = new JPanel();
        eastPanel.setBackground(Color.BLACK);
        JPanel westPanel = new JPanel();
        westPanel.setBackground(Color.BLACK);
        add(eastPanel, BorderLayout.EAST);
        add(westPanel, BorderLayout.WEST);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(5, 5, 10, 10));
        centerPanel.setBackground(Color.BLACK);
        for (int i = 0; i < spaces.length; i++) {
            for (int j = 0; j < spaces[0].length; j++) {
                spaces[i][j] = new JPanel();
                labels[i][j] = new JLabel();
                labels[i][j].setFont(new Font("", Font.BOLD, 20));
                spaces[i][j].add(labels[i][j]);
                centerPanel.add(spaces[i][j]);
            }
        }
        add(centerPanel, BorderLayout.CENTER);

        JPanel southPanel = new JPanel();
        southPanel.setBackground(Color.BLACK);

        ButtonListener listener = new ButtonListener();
        for(int j = 0; j < buttons.length; j++) {
            buttons[j].setBackground(colors[j]);
            buttons[j].addActionListener(listener);
            southPanel.add(buttons[j]);
        }

        deleteButton.addActionListener(listener);
        southPanel.add(deleteButton);

        enterButton.addActionListener(listener);
        southPanel.add(enterButton);

        add(southPanel, BorderLayout.SOUTH);  

        JOptionPane.showMessageDialog(null, "Welcome to DeColder!\nYour objective is to find out what the secret color sequence is.");
        JOptionPane.showMessageDialog(null, "However, each color has a secret rule that belongs to one of two categories:\nThe color can only be with a specific color(s)\nThe color can only be in (a) specific position(s)");
        JOptionPane.showMessageDialog(null, "You'll learn each color's rule over time and colors can REPEAT, so keep playing and good luck!");
        
        createAnswerSequence();
    }

    public void createAnswerSequence() {
        for (int i = 0; i < 5; i++) {
            answerSequence.add(dynColorList.get(rand.nextInt(dynColorList.size())));
            dynColorList.clear();
            for (int j = 0; j < answerSequence.size(); j++) {
                if (COLOR_SET_ONE.contains(answerSequence.get(j))) {
                    dynColorList.addAll(setOneSecretColors.get(COLOR_SET_ONE.indexOf(answerSequence.get(j))));
                    positionCheck(i);
                    break;
                }

                for (int k = 0; k < answerSequence.size(); k++) {
                    if (COLOR_SET_TWO.contains(answerSequence.get(k))) {
                        if (colorSetCheck()) {
                            positionCheck(i);
                            break;
                        } else {
                            colorCheck(i);
                            positionCheck(i);
                        }
                    }
                }
            }
        }
    }

    public void colorCheck(int idx) {
        if (answerSequence.get(idx).equals(Color.BLUE)) {
            dynColorList.add(colors[6]);
            dynColorList.add(colors[1]);
            dynColorList.add(colors[3]);
            dynColorList.add(colors[0]);
        } else if (answerSequence.get(idx).equals(Color.MAGENTA)) {
            dynColorList.add(colors[1]);
            dynColorList.add(colors[5]);
            dynColorList.add(colors[4]);
            dynColorList.add(colors[7]);
        } else if (answerSequence.get(idx).equals(Color.PINK)) {
            dynColorList.add(colors[5]);
            dynColorList.add(colors[3]);
            dynColorList.add(colors[7]);
            dynColorList.add(colors[2]);
        } else if (answerSequence.get(idx).equals(Color.GRAY)) {
            dynColorList.add(colors[0]);
            dynColorList.add(colors[2]);
            dynColorList.add(colors[6]);
            dynColorList.add(colors[4]);
        }
    }

    public void positionCheck(int idx) {
        if (idx == 0) {
            for (int i = 0; i < dynColorList.size(); i++) {
                if (!dynColorList.get(i).equals(COLOR_SET_TWO.get(1)) && COLOR_SET_TWO.contains(dynColorList.get(i))) {
                    dynColorList.remove(i);
                    i--;
                }
            }
        } else if (idx == 1) {
            for (int j = 0; j < dynColorList.size(); j++) {
                if (!dynColorList.get(j).equals(COLOR_SET_TWO.get(2)) && !dynColorList.get(j).equals(COLOR_SET_TWO.get(3)) && COLOR_SET_TWO.contains(dynColorList.get(j))) {
                    dynColorList.remove(j);
                    j--;
                }
            }
        } else if (idx == 2) {
            for (int k = 0; k < dynColorList.size(); k++) {
                if (!dynColorList.get(k).equals(COLOR_SET_TWO.get(1)) && COLOR_SET_TWO.contains(dynColorList.get(k))) {
                    dynColorList.remove(k);
                    k--;
                }
            }
        } else if (idx == 3) {
            for (int l = 0; l < dynColorList.size(); l++) {
                if (!dynColorList.get(l).equals(COLOR_SET_TWO.get(0)) && !dynColorList.get(l).equals(COLOR_SET_TWO.get(2)) && COLOR_SET_TWO.contains(dynColorList.get(l))) {
                    dynColorList.remove(l);
                    l--;
                }
            }
        }
    }

    public boolean colorSetCheck() {
        if (answerSequence.contains(colors[6]) && answerSequence.contains(colors[5])) {
            dynColorList.addAll(redSecretColors);
            return true;
        } else if (answerSequence.contains(colors[6]) && answerSequence.contains(colors[7])) {
            dynColorList.addAll(greenSecretColors);
            return true;
        } else if (answerSequence.contains(colors[0]) && answerSequence.contains(colors[7])) {
            dynColorList.addAll(orangeSecretColors);
            return true;
        } else if (answerSequence.contains(colors[5]) && answerSequence.contains(colors[0])) {
            dynColorList.addAll(yellowSecretColors);
            return true;
        }

        return false;
    }

    public void compareGuess() {
        Color cGreen = new Color(0, 153, 0);
        Color nYellow = new Color(204, 204, 0);
        Color iRed = new Color(204, 0, 0);
        for (int i = 0; i < guess.length; i++) {
            if (guess[i].equals(answerSequence.get(i))) {
                labels[rowNum][i].setText("Correct!");
                labels[rowNum][i].setForeground(cGreen);
            } else if (answerSequence.contains(guess[i])) {
                labels[rowNum][i].setText("Wrong Position");
                labels[rowNum][i].setForeground(nYellow);
            } else {
                labels[rowNum][i].setText("Not in sequence");
                labels[rowNum][i].setForeground(iRed);
            } 
        }
    }

    public boolean checkforWin() {
        int tmp = 0;
        for (int i = 0; i < guess.length; i++) {
            if (guess[i].equals(answerSequence.get(i))) {
                tmp++;
            }
        }

        if (tmp == 5)
            return true;

        return false;
    }

    public void resetGame() {
        if (con == 3 || con == 0) {
            rowNum = 0;
            colNum = 0;

            for (int i = 0; i < spaces.length; i++) {
                for (int j = 0; j < spaces[0].length; j++) {
                    spaces[i][j].setBackground(Color.WHITE);
                    labels[i][j].setText("");
                }
            }

            for (int k = 0; k < guess.length; k++) {
                guess[k] = null;
            }

            answerSequence.clear();
            createAnswerSequence();
        } else if (con == 1) {
            System.exit(0);
        }
    }

    private class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == pinkButton && colNum < 5 && rowNum < 5) {
                spaces[rowNum][colNum].setBackground(Color.PINK);
                guess[colNum] = Color.PINK;
                colNum++;
            } else if (e.getSource() == redButton && colNum < 5 && rowNum < 5) {
                spaces[rowNum][colNum].setBackground(Color.RED);
                guess[colNum] = Color.RED;
                colNum++;
            } else if (e.getSource() == orangeButton && colNum < 5 && rowNum < 5) {
                spaces[rowNum][colNum].setBackground(Color.ORANGE);
                guess[colNum] = Color.ORANGE;
                colNum++;
            } else if (e.getSource() == yellowButton && colNum < 5 && rowNum < 5) {
                spaces[rowNum][colNum].setBackground(Color.YELLOW);
                guess[colNum] = Color.YELLOW;
                colNum++;
            } else if (e.getSource() == greenButton && colNum < 5 && rowNum < 5) {
                spaces[rowNum][colNum].setBackground(Color.GREEN);
                guess[colNum] = Color.GREEN;
                colNum++;
            } else if (e.getSource() == blueButton && colNum < 5 && rowNum < 5) {
                spaces[rowNum][colNum].setBackground(Color.BLUE);
                guess[colNum] = Color.BLUE;
                colNum++;
            } else if (e.getSource() == magentaButton && colNum < 5 && rowNum < 5) {
                spaces[rowNum][colNum].setBackground(Color.MAGENTA);
                guess[colNum] = Color.MAGENTA;
                colNum++;
            } else if (e.getSource() == grayButton && colNum < 5 && rowNum < 5) {
                spaces[rowNum][colNum].setBackground(Color.GRAY);
                guess[colNum] = Color.GRAY;
                colNum++;
            } else if (e.getSource() == deleteButton && colNum > 0 && rowNum < 5) {
                colNum -= 1;
                spaces[rowNum][colNum].setBackground(Color.WHITE);
                guess[colNum] = null;
            } else if (e.getSource() == enterButton && rowNum < 5) {
                try {
                    for (int i = 0; i < guess.length; i++) {
                        if (guess[i] == null) {
                            throw new notFullException();
                        }
                    }

                    compareGuess();
                    if (checkforWin()) {
                        con = JOptionPane.showConfirmDialog(null, "You Won!\nWould you like to play again?");
                        resetGame();
                    } else if (guesses == 5) {
                        con = JOptionPane.showConfirmDialog(null, "Nice try!\nWould you like to play again?");
                        resetGame();
                    } else {
                        colNum = 0;
                        rowNum++;
                        guesses++;
                    }
                } catch (notFullException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        }
    }

    private class notFullException extends Exception {
        public notFullException() {
            super("Your guess isn't full! Please enter a five color sequence!");
        }
    }

    public static void main(String args[]) {
        DeColDerVFinal dcd = new DeColDerVFinal();
        dcd.setVisible(true);
    }
}