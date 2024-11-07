//Code I found online that opens a window with buttons and other things
package Javamon;

//libraries to help build the GUI handling events and managing layout
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

//importing libraryies to add sound
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
        panel2 = new MyPanel2();
        // panel3 = new MyPanel3();
        contentPane.add(panel1, "Panel 1"); 
        contentPane.add(panel2, "Panel 2");
        // contentPane.add(panel3, "Panel 3");
        frame.setContentPane(contentPane);
        //contains pack to minimize, expand, and close window
        frame.pack();  
        //makes window pop up at a random location, w/o line, window will pop up top left corner 
        //frame.setLocationByPlatform(true);
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

//Class for first Panel
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
        ImageIcon playButtonIcon = new ImageIcon("PlayButton.png");
        playButton = new JButton (playButtonIcon);
        playButton.setContentAreaFilled(false);


        //adjust size and set layout of window
        setPreferredSize (new Dimension (1280, 720));
        setLayout (null);

        //add opening window image
        ImageIcon imageIcon = new ImageIcon("javamon_openingScreen.png");
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
                javamon.playClickSound("audio-files\\click.wav", -20.f);
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
        javamon.playMusic("audio-files\\panel1.wav", -20.f);
    }
}

class MyPanel2 extends JPanel {
    private JButton jcomp1;
    private JButton jcomp2;
    private JButton jcomp3;
    // private JTextField jcomp4;

    public MyPanel2() {

        ImageIcon pokeballLeft = new ImageIcon("pokeball_button_left.jpeg");
        ImageIcon pokeballMiddle = new ImageIcon("pokeball_button_middle.jpeg");
        ImageIcon pokeballRight = new ImageIcon("pokeball_button_right.jpeg");
        //construct components
        jcomp1 = new JButton (pokeballLeft);
        jcomp2 = new JButton (pokeballMiddle);
        jcomp3 = new JButton (pokeballRight);
        // jcomp4 = new JTextField (5);

        //adjust size and set layout
        setPreferredSize (new Dimension (1280,720));
        setLayout (null);
        //set component bounds (only needed by Absolute Positioning)

        ImageIcon imageIcon = new ImageIcon("Selection_Panel.png");
        JLabel label = new JLabel(imageIcon);

        label.setBounds(0, 0, imageIcon.getIconWidth(), imageIcon.getIconHeight());

        jcomp1.setBounds (183, 237, 277, 339);
        jcomp2.setBounds (505, 237, 277, 339);
        jcomp3.setBounds (830, 237, 277, 339);
        // jcomp4.setBounds (105, 115, 100, 25);

        //add components
        add (jcomp1);
        add (jcomp2);
        add (jcomp3);
        add (label);
        // add (jcomp4);       
    }
}

// class MyPanel3 extends JPanel {  THIS WILL BE BATTLE SCREEN
//     public MyPanel2() {

//     }

// }


// Opens a empty Window

// public class javamon extends Frame{
//     static Image img;

//     javamon() {
      
//         //Using ToolKit image getImage to get the Image
//         img = Toolkit.getDefaultToolkit().getImage("pok");
      
//         //Mention the path or save the image in the same folder
//         MediaTracker track = new MediaTracker(this);
      
//         this.addWindowListener(new WindowAdapter() {
//             public void windowClosing(WindowEvent e) {
//                 System.exit(0);
//             }
//         });
      
//         //Use a unique id for the image object
//         track.addImage(img,0);
      
//         try {
//             track.waitForID(0);
//         }
//         catch(InterruptedException ae){ 
//         }
//         //The JVM waits until the image is completely loaded

//     }
  
//     public void paint(Graphics g){
//         g.drawImage(img,150,200,200,200,null);
//     }
  
//     public  static void main(String args[]){
      
//         javamon g = new javamon();
      
//         g.setTitle("Geeks For Geeks");
//         g.setSize(500,500);
      
//         //Setting the IconImage in the Frame
//         g.setIconImage(img);
//         g.setVisible(true);
      
//     }
  
// }
