import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class WhacAMole {
    private int boardWidth = 600;
    private int boardHeight = 650; // 50px for text panel on top

    JFrame frame = new JFrame("Mario: WhacAMole");
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();
    JButton[] board = new JButton[9];

    ImageIcon moleIcon;
    ImageIcon plantIcon;

    JButton currMoleTile;
    JButton currPlantTile;

    Random random = new Random();

    Timer setMoleTimer;
    Timer setPlantTimer;

    int score = 0;

    // Constructor
    public WhacAMole(){
        // Properties of frame
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());


        // Properties of textLabel
        textLabel.setFont(new Font("Arial",Font.PLAIN,50));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Score: 0");
        textLabel.setOpaque(true);

        // Properties of textPanel
        textPanel.setLayout(new BorderLayout());

        // Properties of boardPanel
        boardPanel.setLayout(new GridLayout(3,3));


        textPanel.add(textLabel); // Adding textLabel to textPanel
        frame.add(textPanel, BorderLayout.NORTH); // Adding textPanel to frame

        Image plantImg = new ImageIcon(getClass().getResource("./piranha.png")).getImage();
        plantIcon = new ImageIcon(plantImg.getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH));

        Image moleImg = new ImageIcon(getClass().getResource("./monty.png")).getImage();
        moleIcon = new ImageIcon(moleImg.getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH));

        for (int i = 0; i < 9; i++){ // Adding buttons to boardPanel and board
            JButton tile = new JButton();
            board[i] = tile;
            boardPanel.add(tile);
            tile.setFocusable(false);

            tile.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JButton tile = (JButton) e.getSource();
                    if (tile == currMoleTile){
                        score += 10;
                        textLabel.setText("Score: " + Integer.toString(score));
                    } else if (tile == currPlantTile) {
                        textLabel.setText("Game Over: " + Integer.toString(score));
                        setMoleTimer.stop();
                        setPlantTimer.stop();
                        // Displaying all the buttons
                        for (int i = 0; i < 9; i++){
                            board[i].setEnabled(false);
                        }
                    }
                }
            });
        }
        frame.add(boardPanel); // Adding boardPanel to frame

        setMoleTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currMoleTile != null){
                    currMoleTile.setIcon(null);
                    currMoleTile = null;
                }
                int num = random.nextInt(9); // 0-8
                JButton tile = board[num];
                if (currPlantTile == tile) return; // To avoid conflict
                currMoleTile = tile;
                currMoleTile.setIcon(moleIcon);
            }
        });
        setPlantTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currPlantTile != null){
                    currPlantTile.setIcon(null);
                    currPlantTile = null;
                }
                int num = random.nextInt(9); // 0-8
                JButton tile = board[num];
                if (tile == currMoleTile) return; // To avoid conflict
                currPlantTile = tile;
                currPlantTile.setIcon(plantIcon);
            }
        });

        setMoleTimer.start();
        setPlantTimer.start();
        frame.setVisible(true);

    }
}