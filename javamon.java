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

    public Pokemon(String name, String spritePath) {
        this.name = name;
        this.spritePath = spritePath;
    }

    public String getName() {
        return name;
    }

    public String getSpritePath() {
        return spritePath;
    }
}

// Panel to select starter pokemon
class MyPanel2 extends JPanel {
    private JPanel contentPane;

    public MyPanel2(JPanel panel) {
        contentPane = panel;

        //creates pokemon object with sprite path
        Pokemon waterPokemon = new Pokemon("Water", "panels/battleScreens/petermonPanel.png");
        Pokemon grassPokemon = new Pokemon("Grass", "panels/battleScreens/sproutyPanel.png");
        Pokemon firePokemon = new Pokemon("Fire", "panels/battleScreens/bowchellePanel.png");
        
        //creates each button in one line to reduce clutter
        JButton waterButton = new JButton(new ImageIcon("buttons/panel-2/waterPokeball.jpeg"));
        JButton grassButton = new JButton(new ImageIcon("buttons/panel-2/grassPokeball.jpeg"));
        JButton fireButton = new JButton(new ImageIcon("buttons/panel-2/firePokeball.jpeg"));

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

//Battle Screen Panel
class MyPanel3 extends JPanel {
    
    private JButton tackleButton;
    private JButton specialButton;
    private JPanel contentPane;
    // private JLabel playerSprite;
    private JLabel playerNameLabel;

    public MyPanel3(JPanel panel, Pokemon selectedPokemon) {
        contentPane = panel;
    
        final JLabel playerHealth = new JLabel("30/30");
        final JLabel enemyHealth = new JLabel("30/30");

         // Display Pokémon name
         playerNameLabel = new JLabel(selectedPokemon.getName());
         playerNameLabel.setFont(new Font("Serif", Font.BOLD, 35));
         playerNameLabel.setForeground(Color.WHITE);
         playerNameLabel.setBounds(150, 100, 200, 50);

        // Load Pokémon sprite
        // ImageIcon playerSpriteIcon = new ImageIcon(selectedPokemon.getSpritePath());
        // playerSprite = new JLabel(playerSpriteIcon);
        // playerSprite.setBounds(150, 300, playerSpriteIcon.getIconWidth(), playerSpriteIcon.getIconHeight());


        //Tackle and Special move button
        ImageIcon tackleButtonIcon = new ImageIcon("buttons/PlayButton.png");
        tackleButton = new JButton (tackleButtonIcon);
        ImageIcon specialButtonIcon = new ImageIcon("buttons/PlayButton.png");
        specialButton = new JButton (specialButtonIcon);
        //makes button image transparent
        tackleButton.setContentAreaFilled(false);
        specialButton.setContentAreaFilled(false);

        //battle Screen Image

        ImageIcon imageIcon = new ImageIcon(selectedPokemon.getSpritePath());
        JLabel battleScreen = new JLabel(imageIcon);

        //adjust size and set layout of window
        setPreferredSize (new Dimension (1280, 720));
        setLayout (null);
        battleScreen.setBounds(0, 0, imageIcon.getIconWidth(), imageIcon.getIconHeight());

        //health text set up
        playerHealth.setFont(new Font("Serif", Font.BOLD, 28));
        playerHealth.setOpaque(false);
        playerHealth.setForeground(Color.RED); 
        playerHealth.setBounds (1205, 530, 150, 150);

        enemyHealth.setFont(new Font("Serif", Font.BOLD, 28));
        enemyHealth.setOpaque(false);
        enemyHealth.setForeground(Color.RED); 
        enemyHealth.setBounds (1205, 600, 150, 150);

        //tackle Button and Special move button set up
        tackleButton.setLocation(43, 563);
        tackleButton.setSize(798, 57);
        specialButton.setLocation(43, 642);
        specialButton.setSize(798, 57);
        
        //Makes buttom usable, when clicked, enemy health - 10
        tackleButton.addActionListener(new ActionListener()
        {
            private int enemyHP = 30;
            private int playerHP = 30;
            public void actionPerformed(ActionEvent e)
            {
                javamon.playClickSound("audio-files/click.wav", -20.f);
                enemyHP = enemyHP-10;
                enemyHealth.setText(enemyHP+"/30");

                // Switches to Win Screen when enemy javamon's hp reaches 0
                if (enemyHP <= 0) {
                    contentPane.add(new MyPanel4(), "Win Screen");
                    ((CardLayout) contentPane.getLayout()).show(contentPane, "Win Screen");
                    return;
                }

                // wait 1 second before player gets attacked
                Timer timer = new Timer(1000, new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        // Update player's HP
                        playerHP -= 10;
                        playerHealth.setText(playerHP + "/30");

                        // Switches to Lose Screen when player javamon's hp reaches 0
                        if (playerHP <= 0) {
                        contentPane.add(new MyPanel5(), "Lose Screen");
                        ((CardLayout) contentPane.getLayout()).show(contentPane, "Lose Screen");
                        return;
                        }
                    }
                });

                // Make sure the timer only fires once, then stop
                timer.setRepeats(false);
                timer.start();
            }
            
            
        });

        add (playerHealth);
        add (enemyHealth);
        add (battleScreen);
        // add (playerSprite);
        add (tackleButton);
        add (specialButton);
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