package Javamon;

//libraries to help build the GUI handling events and managing layout
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

//importing libraries to add sound
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class javamon //main class where displayGUI contructs the window
{
    private JPanel contentPane;
    private MyPanel panel1; 
    private MyPanel2 panel2;

    private void displayGUI()
    {
        //set title of window to Java-Mon and store in frame
        JFrame frame = new JFrame("Java-Mon"); 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setSize(400,400);
        JPanel contentPane = new JPanel(); 
        contentPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); //border thickness
        contentPane.setLayout(new CardLayout()); //card layout allows for multiple windows
        //create different windows for different panels
        panel1 = new MyPanel(contentPane);
        panel2 = new MyPanel2(contentPane);
        contentPane.add(panel1, "Panel 1"); 
        contentPane.add(panel2, "Panel 2");
        frame.setContentPane(contentPane);
        //contains pack to minimize, expand, and close window
        frame.pack();  
        //makes window visible
        frame.setVisible(true);
    }

    public static void main(String... args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                //show window
                new javamon().displayGUI();
            }
        });
    }

    //method to play audio lol
    public static void playMusic(String filePath, float volume) 
    {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(filePath));
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);

            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(volume);

            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY); // continously loops music
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    //plays clicking sound lol
    public static void playClickSound(String filePath, float volume)
    {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(filePath));
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);

            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(volume);

            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
        e.printStackTrace();
        }
    }
}

//Class for first Opening Panel
class MyPanel extends JPanel {
    private JButton playButton;
    private JPanel contentPane;

    public MyPanel(JPanel panel) {

        contentPane = panel;
        ImageIcon playButtonIcon = new ImageIcon("buttons/PlayButton.png");
        playButton = new JButton (playButtonIcon);
        playButton.setBorderPainted(false);
        playButton.setFocusPainted(false);
        //makes button image transparent
        playButton.setContentAreaFilled(false);


        //adjust size and set layout of window
        setPreferredSize (new Dimension (1280, 720));
        setLayout (null);

        //add opening window image
        ImageIcon imageIcon = new ImageIcon("panels/javamon_openingScreen.png");
        JLabel label = new JLabel(imageIcon);

        //set component bounds (only needed by Absolute Positioning)
        label.setBounds(0, 0, imageIcon.getIconWidth(), imageIcon.getIconHeight());
        playButton.setLocation(520, 550);
        playButton.setSize(350, 150);

        //Makes buttom usable, jumps it to next content frame
        playButton.addActionListener( new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                javamon.playClickSound("audio-files/click.wav", -20.f);
                CardLayout cardLayout = (CardLayout) contentPane.getLayout();
                cardLayout.next(contentPane);
            }
        });

        add (playButton);  
        add (label);   
        
        //plays music when panel 1 loads
        javamon.playMusic("audio-files/panel1.wav", -20.f);
    }
}

//class for selected pokemon to import from panel 2 to panel 3
class Pokemon {
    private String name;
    private String spritePath;
    private String specialAttack;
    private String specialText;

    public Pokemon(String name, String spritePath, String specialAttack, String specialText) {
        this.name = name;
        this.spritePath = spritePath;
        this.specialAttack = specialAttack;
        this.specialText = specialText;
    }

    public String getName() {
        return name;
    }

    public String getSpritePath() {
        return spritePath;
    }

    public String getSpecialAttack() {
        return specialAttack;
    }

    public String getSpecialText(){
        return specialText;
    }
}

// Panel to select starter pokemon
class MyPanel2 extends JPanel {
    private JPanel contentPane;

    public MyPanel2(JPanel panel) {
        contentPane = panel;

        //creates pokemon object with sprite path
        Pokemon waterPokemon = new Pokemon("Water", "panels/battleScreens/petermonPanel.png", "buttons/watergun.png", "Petermon used Water Gun! Hitman-thom HP -10");
        Pokemon grassPokemon = new Pokemon("Grass", "panels/battleScreens/sproutyPanel.png", "buttons/vinewhip.png", "Sprouty used Vine Whip! Hitman-thom HP -10");
        Pokemon firePokemon = new Pokemon("Fire", "panels/battleScreens/bowchellePanel.png", "buttons/ember.png", "Bowchelle used Ember! Hitman-thom HP -10");
        
        //creates each button in one line to reduce clutter
        JButton waterButton = new JButton(new ImageIcon("buttons/panel-2/waterPokeball.jpeg"));
        JButton grassButton = new JButton(new ImageIcon("buttons/panel-2/grassPokeball.jpeg"));
        JButton fireButton = new JButton(new ImageIcon("buttons/panel-2/firePokeball.jpeg"));
        waterButton.setBorderPainted(false);
        grassButton.setBorderPainted(false);
        fireButton.setBorderPainted(false);

        //adjust size and set layout of window
        setPreferredSize (new Dimension (1280, 720));
        setLayout (null);

        //set component bounds (only needed by Absolute Positioning)

        ImageIcon imageIcon = new ImageIcon("panels/Selection_Panel.png");
        JLabel label = new JLabel(imageIcon);

        label.setBounds(0, 0, imageIcon.getIconWidth(), imageIcon.getIconHeight());

        waterButton.setBounds (183, 237, 277, 339);
        grassButton.setBounds (505, 237, 277, 339);
        fireButton.setBounds (830, 237, 277, 339);

        waterButton.addActionListener(e -> {
            javamon.playClickSound("audio-files/click.wav", -20.f);
            contentPane.add(new MyPanel3(contentPane, waterPokemon), "Panel 3");
            ((CardLayout) contentPane.getLayout()).show(contentPane, "Panel 3");
        });
        grassButton.addActionListener(e -> {
            javamon.playClickSound("audio-files/click.wav", -20.f);
            contentPane.add(new MyPanel3(contentPane, grassPokemon), "Panel 3");
            ((CardLayout) contentPane.getLayout()).show(contentPane, "Panel 3");
        });
        fireButton.addActionListener(e -> {
            javamon.playClickSound("audio-files/click.wav", -20.f);
            contentPane.add(new MyPanel3(contentPane, firePokemon), "Panel 3");
            ((CardLayout) contentPane.getLayout()).show(contentPane, "Panel 3");
        });

        //add components
        add (waterButton);
        add (grassButton);
        add (fireButton);
        add (label);
        // add (jcomp4);       
    }
}

class MyPanel3 extends JPanel {

    private JButton tackleButton;
    private JButton specialButton;
    private JPanel contentPane;
    private JLabel battleMessageLabel;
    private JLabel playerHealth;
    private JLabel enemyHealth;

    private int enemyHP = 30;
    private int playerHP = 30;

    public MyPanel3(JPanel panel, Pokemon selectedPokemon) {
        contentPane = panel;

        // Initialize components
        playerHealth = new JLabel("30/30");
        enemyHealth = new JLabel("30/30");
        battleMessageLabel = new JLabel("");
        //battle message label
        battleMessageLabel.setFont(new Font("Algerian", Font.BOLD, 24));
        battleMessageLabel.setForeground(Color.WHITE);
        battleMessageLabel.setBounds(400, 50, 480, 50);

        //tackleButton
        ImageIcon tackleButtonIcon = new ImageIcon("buttons/tackle.png");
        tackleButton = new JButton(tackleButtonIcon);
        tackleButton.setBounds(43, 563, 798, 57);

        
        //specialButton
        ImageIcon specialButtonIcon = new ImageIcon(selectedPokemon.getSpecialAttack());
        specialButton = new JButton(specialButtonIcon);
        specialButton.setBounds(43, 643, 798, 56);

        // // Removes the border
         tackleButton.setBorderPainted(false); 
         specialButton.setBorderPainted(false);
    

        ImageIcon imageIcon = new ImageIcon(selectedPokemon.getSpritePath());
        JLabel battleScreen = new JLabel(imageIcon);

        // Adjust layout
        setPreferredSize(new Dimension(1280, 720));
        setLayout(null);
        battleScreen.setBounds(0, 0, imageIcon.getIconWidth(), imageIcon.getIconHeight());

        playerHealth.setFont(new Font("Serif", Font.BOLD, 28));
        playerHealth.setForeground(Color.RED);
        playerHealth.setBounds(1205, 530, 150, 150);

        enemyHealth.setFont(new Font("Serif", Font.BOLD, 28));
        enemyHealth.setForeground(Color.RED);
        enemyHealth.setBounds(1205, 600, 150, 150);


        battleMessageLabel.setBounds(150, 560, 650, 75);
        // Tackle button logic
        tackleButton.addActionListener(e -> {
            javamon.playClickSound("audio-files/click.wav", -20.f);
            handleBattleSequence("Player used Tackle! Enemy HP -10", true);
        });

        specialButton.addActionListener(e -> {
            javamon.playClickSound("audio-files/click.wav", -20.f);
            handleBattleSequence(selectedPokemon.getSpecialText(), true);
        });

        add(battleMessageLabel);
        add(playerHealth);
        add(enemyHealth);
        add(battleScreen);
        add(specialButton);
        add(tackleButton);
    }

    private void handleBattleSequence(String message, boolean isPlayerTurn) {
        // Hide buttons and display the message
        tackleButton.setVisible(false);
        specialButton.setVisible(false);
        battleMessageLabel.setText(message);

        Timer timer = new Timer(3000, evt -> {
            if (isPlayerTurn) {
                // Deduct enemy health
                enemyHP -= 10;
                enemyHealth.setText(enemyHP + "/30");

                if (enemyHP <= 0) {
                    battleMessageLabel.setText("Enemy Pokémon fainted!");
                    showEndScreen(true);
                    return;
                }

                // Prepare for enemy turn
                handleBattleSequence("Enemy used Attack! Player HP -10", false);
            } else {
                // Deduct player health
                playerHP -= 10;
                playerHealth.setText(playerHP + "/30");

                if (playerHP <= 0) {
                    battleMessageLabel.setText("Your Pokémon fainted!");
                    showEndScreen(false);
                    return;
                }

                // Re-enable buttons for player's turn
                battleMessageLabel.setText("");
                tackleButton.setVisible(true);
                specialButton.setVisible(true);
            }
        });

        timer.setRepeats(false);
        timer.start();
    }

    private void showEndScreen(boolean playerWon) {
        Timer timer = new Timer(3000, evt -> {
            contentPane.add(playerWon ? new MyPanel4() : new MyPanel5(), playerWon ? "Win Screen" : "Lose Screen");
            ((CardLayout) contentPane.getLayout()).show(contentPane, playerWon ? "Win Screen" : "Lose Screen");
        });
        timer.setRepeats(false);
        timer.start();
    }
}



// Win Screen Panel
class MyPanel4 extends JPanel {
    public MyPanel4() {
        // Set layout and dimensions
        setPreferredSize(new Dimension(1280, 720));
        setLayout(null);
        
        // Add win screen image
        ImageIcon winImageIcon = new ImageIcon("panels/winScreen.png");
        JLabel winLabel = new JLabel(winImageIcon);
        winLabel.setBounds(0, 0, winImageIcon.getIconWidth(), winImageIcon.getIconHeight());
        
        add(winLabel);
    }
}

// Lose Screen Panel
class MyPanel5 extends JPanel {
    public MyPanel5() {
        //Set layout and dimensions
        setPreferredSize(new Dimension(1280, 720));
        setLayout(null);

        // Add lose screen image
        ImageIcon loseImageIcon = new ImageIcon("panels/loseScreen.png");
        JLabel loseLabel = new JLabel(loseImageIcon);
        loseLabel.setBounds(0, 0, loseImageIcon.getIconWidth(), loseImageIcon.getIconHeight());

        add(loseLabel);
    }
}
