//Code I found online that opens a window with buttons and other things
package Javamon;

//libraries to help build the GUI handling events and managing layout
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

//importing libraryies to add sound
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

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
    private MyPanel3 panel3;

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
        panel3 = new MyPanel3(contentPane);
        contentPane.add(panel1, "Panel 1"); 
        contentPane.add(panel2, "Panel 2");
        contentPane.add(panel3, "Panel 3");
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

    // private JTextField How;
    // private JLabel jcomp2;
    // private JLabel jcomp3;
    private JButton playButton;
    private JPanel contentPane;

    public MyPanel(JPanel panel) {

        contentPane = panel;
        //'How' is a text box component; allow users to insert text; we probably don't need but keep just in case
        // How = new JTextField (3);
        //construct text components 
        // jcomp2 = new JLabel ("How long were you parked?");
        // jcomp3 = new JLabel ("Minutes");
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
        // How.setBounds (245, 50, 60, 25);
        // jcomp2.setBounds (35, 30, 185, 50);
        // jcomp3.setBounds (250, 30, 60, 20);
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

        //add components
        // add (How);
        // add (jcomp2);
        // add (jcomp3);
        add (playButton);  
        add (label);   
        
        //plays music when panel 1 loads
        javamon.playMusic("audio-files/panel1.wav", -20.f);
    }
}

// Panel to select starter pokemon
class MyPanel2 extends JPanel {
    private JButton jcomp1;
    private JButton jcomp2;
    private JButton jcomp3;
    // private JTextField jcomp4;
    private JPanel contentPane;

    public MyPanel2(JPanel panel) {
        contentPane = panel;
        ImageIcon pokeballLeft = new ImageIcon("buttons/panel-2/waterPokeball.jpeg");
        ImageIcon pokeballMiddle = new ImageIcon("buttons/panel-2/grassPokeball.jpeg");
        ImageIcon pokeballRight = new ImageIcon("buttons/panel-2/firePokeball.jpeg");
        //construct components
        jcomp1 = new JButton (pokeballLeft);
        jcomp2 = new JButton (pokeballMiddle);
        jcomp3 = new JButton (pokeballRight);
        // jcomp4 = new JTextField (5);

        //adjust size and set layout of window
        setPreferredSize (new Dimension (1280, 720));
        setLayout (null);

        //set component bounds (only needed by Absolute Positioning)

        ImageIcon imageIcon = new ImageIcon("panels/Selection_Panel.png");
        JLabel label = new JLabel(imageIcon);

        label.setBounds(0, 0, imageIcon.getIconWidth(), imageIcon.getIconHeight());

        jcomp1.setBounds (183, 237, 277, 339);
        jcomp2.setBounds (505, 237, 277, 339);
        jcomp3.setBounds (830, 237, 277, 339);
        // jcomp4.setBounds (105, 115, 100, 25);

        //sends Javamon's Name to selectJavamon
        jcomp1.addActionListener(e -> selectJavamon("Petermon"));
        jcomp2.addActionListener(e -> selectJavamon("Sprouty"));
        jcomp3.addActionListener(e -> selectJavamon("Bowchelle"));

        //add components
        add (jcomp1);
        add (jcomp2);
        add (jcomp3);
        add (label);
        // add (jcomp4);       
    }

    //what happens when the buttons are pressed and sends the selected Javamon's Name
    private void selectJavamon(String javamonName) {
        javamon.playClickSound("audio-files/click.wav", -20.f);
        MyPanel3 panel3 = (MyPanel3) contentPane.getComponent(2);
        panel3.setPlayerJavamonName(javamonName);
        CardLayout cardLayout = (CardLayout) contentPane.getLayout();
        cardLayout.next(contentPane);
    }
}

//Battle Screen Panel
class MyPanel3 extends JPanel {
    
    private JButton tackleButton;
    private JPanel contentPane;
    private JLabel playerHealth;
    private JLabel enemyHealth;
    private JLabel playerName;
    private JLabel enemyName;

    public MyPanel3(JPanel panel) {
        
        contentPane = panel;
    
        final JLabel playerHealth = new JLabel("30/30");
        final JLabel enemyHealth = new JLabel("30/30");

        //Tackle button
        ImageIcon tackleButtonIcon = new ImageIcon("buttons/PlayButton.png");
        tackleButton = new JButton (tackleButtonIcon);
        //makes button image transparent
        tackleButton.setContentAreaFilled(false);

        //battle Screen Image
        ImageIcon imageIcon = new ImageIcon("panels/battleScreen.png");
        JLabel battleScreen = new JLabel(imageIcon);

        //adjust size and set layout of window
        setPreferredSize (new Dimension (1280, 720));
        setLayout (null);

        battleScreen.setBounds(0, 0, imageIcon.getIconWidth(), imageIcon.getIconHeight());

        // Set up Javamon name
        //playerName is dynamically set so it can be updated
        playerName = new JLabel();
        playerName.setFont(new Font("Serif", Font.BOLD, 35));
        playerName.setOpaque(false);
        playerName.setForeground(Color.RED);
        playerName.setBounds(150, 150, 200, 50);

        enemyName = new JLabel("Hitman-Thom");
        enemyName.setFont(new Font("Serif", Font.BOLD, 35));
        enemyName.setOpaque(false);
        enemyName.setForeground(Color.RED);
        enemyName.setBounds(1000, 150, 200, 50);

        //health text set up
        playerHealth.setFont(new Font("Serif", Font.BOLD, 35));
        playerHealth.setOpaque(false);
        playerHealth.setForeground(Color.RED); 
        playerHealth.setBounds (150, 200, 200, 50);

        enemyHealth.setFont(new Font("Serif", Font.BOLD, 35));
        enemyHealth.setOpaque(false);
        enemyHealth.setForeground(Color.RED);
        enemyHealth.setBounds (1000, 200, 200, 50);

        //tackleButton set up
        tackleButton.setLocation(520, 550);
        tackleButton.setSize(350, 150);
        
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

                // wait 1 second before player gets attacked
                Timer timer = new Timer(1000, new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        // Update player's HP
                        playerHP -= 10;
                        playerHealth.setText(playerHP + "/30");
                    }
                });

                // Make sure the timer only fires once, then stop
                timer.setRepeats(false);
                timer.start();
            }
            
            
        });

        add (playerHealth);
        add (enemyHealth);
        add (tackleButton);
        add (battleScreen);
        add (playerName);
        add (enemyName);
    }
    //Updates text of playerName to chosen name based on which button is pressed on MyPanel2
    public void setPlayerJavamonName(String name) {
        playerName.setText(name);
    }
}