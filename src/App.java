import java.awt.*;
import java.io.File;
import java.io.IOException;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

import javax.swing.*;

public class App {
    static Color bg = new Color(11, 11, 11);
    static double balance = 100;
    static String username = "";

    static int switchs = 2; // 0 for over, 1 for under, 2 for none

    public static void main(String[] args) throws Exception {
        /* 
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");

            FileInputStream fileInput = new FileInputStream("res/user/raw.txt");
            ObjectInputStream objectInput = new ObjectInputStream(fileInput);

            User currentUser = (User) objectInput.readObject();

            objectInput.close();
            fileInput.close();

            mainScreen();

        } catch (Exception _) {
            // Insert sign up screen
        }

        */
       mainScreen();
    }

    public static void topUpScreen() {
        JFrame frame = new JFrame();
        Container contentpane = frame.getContentPane();

        frame.setTitle("oblivion");
        frame.setSize(700, 400);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        contentpane.setBackground(bg);
        contentpane.setLayout(null);
        
        Font geistmono6 = null;
        Font geistmono9 = null;
        Font geistmono12 = null;
        Font instrument48 = null;

        try {
            geistmono6 = Font.createFont(Font.TRUETYPE_FONT, new File("res/font/geistmono.ttf")).deriveFont(6f);
            geistmono9 = Font.createFont(Font.TRUETYPE_FONT, new File("res/font/geistmono.ttf")).deriveFont(9f);
            geistmono12 = Font.createFont(Font.TRUETYPE_FONT, new File("res/font/geistmono.ttf")).deriveFont(12f);
            instrument48 = Font.createFont(Font.TRUETYPE_FONT, new File("res/font/instrumentserif.ttf")).deriveFont(48f);

            GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
            graphicsEnvironment.registerFont(geistmono6);
            graphicsEnvironment.registerFont(geistmono9);
            graphicsEnvironment.registerFont(geistmono12);
            graphicsEnvironment.registerFont(instrument48);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        JLabel title = new JLabel("oblivion");
        title.setFont(geistmono9);
        title.setForeground(Color.WHITE);
        title.setBounds(14, 12, 58, 16);

        JLabel userBalanace = new JLabel("$"+ balance);
        userBalanace.setFont(geistmono12);
        userBalanace.setForeground(Color.WHITE);
        userBalanace.setBounds(582, 12, 30, 16);

        RoundedButton topUp = new RoundedButton("buy");
        topUp.setFont(geistmono9);
        topUp.setBackground(Color.WHITE);
        topUp.setForeground(bg);
        topUp.setBounds(620, 12, 69, 16);

        JLabel paymentsLabel = new JLabel("Payments");
        paymentsLabel.setFont(instrument48);
        paymentsLabel.setForeground(Color.WHITE);
        paymentsLabel.setBounds(200, 63, 175, 47);

        RoundedPanel panel = new RoundedPanel(20);
        panel.setBackgroundColor(bg);
        panel.setRoundedBorder(new Color(49, 49, 49), 1);
        panel.setBounds(200, 116, 300, 221);

        contentpane.add(title);
        contentpane.add(userBalanace);
        contentpane.add(topUp);
        contentpane.add(paymentsLabel);
        contentpane.add(panel);

        frame.setVisible(true);
    }

    public static void mainScreen() {
        JFrame frame = new JFrame();
        Container contentpane = frame.getContentPane();

        frame.setTitle("oblivion");
        frame.setSize(700, 400);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        contentpane.setBackground(bg);
        contentpane.setLayout(null);
        
        Font geistmono6 = null;
        Font geistmono9 = null;
        Font geistmono12 = null;
        Font instrument48 = null;

        try {
            geistmono6 = Font.createFont(Font.TRUETYPE_FONT, new File("res/font/geistmono.ttf")).deriveFont(6f);
            geistmono9 = Font.createFont(Font.TRUETYPE_FONT, new File("res/font/geistmono.ttf")).deriveFont(9f);
            geistmono12 = Font.createFont(Font.TRUETYPE_FONT, new File("res/font/geistmono.ttf")).deriveFont(12f);
            instrument48 = Font.createFont(Font.TRUETYPE_FONT, new File("res/font/instrumentserif.ttf")).deriveFont(48f);

            GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
            graphicsEnvironment.registerFont(geistmono6);
            graphicsEnvironment.registerFont(geistmono9);
            graphicsEnvironment.registerFont(geistmono12);
            graphicsEnvironment.registerFont(instrument48);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        JLabel title = new JLabel("oblivion");
        title.setFont(geistmono9);
        title.setForeground(Color.WHITE);
        title.setBounds(14, 12, 58, 16);

        JLabel userBalanace = new JLabel("$"+ balance);
        userBalanace.setFont(geistmono12);
        userBalanace.setForeground(Color.WHITE);
        userBalanace.setBounds(582, 12, 30, 16);

        RoundedButton topUp = new RoundedButton("buy");
        topUp.setFont(geistmono9);
        topUp.setBackground(Color.WHITE);
        topUp.setForeground(bg);
        topUp.setBounds(620, 12, 69, 16);

        // topUp button event (needs functionality)
        topUp.addActionListener(e -> {
            frame.setVisible(false);
            frame.dispose();

            topUpScreen();
        });

        JLabel dice = new JLabel("Dice");
        dice.setFont(instrument48);
        dice.setForeground(Color.WHITE);
        dice.setBounds(315, 138, 70, 62);

        JLabel zero = new JLabel("0");
        zero.setFont(geistmono12);
        zero.setForeground(Color.WHITE);
        zero.setBounds(91, 192, 8, 16);

        JLabel hundred = new JLabel("100");
        hundred.setFont(geistmono12);
        hundred.setForeground(Color.WHITE);
        hundred.setBounds(586, 192, 22, 16);

        JSlider slider = new JSlider(0, 100, 35);
        slider.setUI(new sliderUI(slider));
        slider.setOpaque(false);
        slider.setBounds(91, 213, 517, 14);

        slider.addChangeListener(e -> slider.repaint());

        RoundedButton over = new RoundedButton("over");
        over.setFont(geistmono9);
        over.setBackground(Color.WHITE);
        over.setForeground(bg);
        over.setBounds(276, 241, 69, 16);

        over.addActionListener(e -> {
            switchs = 0;
        });

        RoundedButton under = new RoundedButton("under");
        under.setFont(geistmono9);
        under.setBackground(Color.WHITE);
        under.setForeground(bg);
        under.setBounds(354, 241, 69, 16);

        under.addActionListener(e -> {
            switchs = 1;
        });

        RoundedButton betPlacer = new RoundedButton("bet");
        betPlacer.setFont(geistmono6);
        betPlacer.setBackground(Color.WHITE);
        betPlacer.setForeground(bg);
        betPlacer.setBounds(319, 305, 61, 16);

        // betPlacer button event (needs functionality)
        betPlacer.addActionListener(e -> {
            // if balance is more than 1
            if (balance >= 1) {
                int rngDice = (int)(Math.random() * 101);
                int rngUser = slider.getValue();

                if (switchs == 0) {
                    if (rngDice < rngUser) {
                        balance *= 1.2;
                    } 
                    else {
                        balance -= 20;
                    }
                } else if (switchs == 1) {
                    if (rngDice > rngUser) {
                        balance *= 1.2;
                    } 
                    else {
                        balance /= 1.2;
                    }
                } 
                else {
                    System.out.println("Debugging: 2");
                }

                userBalanace.setText("$" + String.valueOf((int) balance));

                contentpane.repaint();
                contentpane.revalidate();
            } // else do nothing
        });

        JLabel informative = new JLabel("1.2x multiplier upon win");
        informative.setFont(geistmono6);
        informative.setForeground(new Color(75, 75, 75));
        informative.setBounds(310, 330, 95, 8);

        contentpane.add(title);
        contentpane.add(userBalanace);
        contentpane.add(topUp);
        contentpane.add(dice);
        contentpane.add(zero);
        contentpane.add(hundred);
        contentpane.add(slider);
        contentpane.add(over);
        contentpane.add(under);
        contentpane.add(betPlacer);
        contentpane.add(informative);

        frame.setVisible(true);
    }
}
