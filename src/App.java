import java.awt.*;
import java.io.File;
import java.io.IOException;

import javax.swing.*;

public class App {
    static Color bg = new Color(11, 11, 11);
    static int balance = 0;

    public static void main(String[] args) throws Exception {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        mainScreen();
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
        userBalanace.setBounds(589, 12, 22, 16);

        RoundedButton topUp = new RoundedButton("top up");
        topUp.setFont(geistmono9);
        topUp.setBackground(Color.WHITE);
        topUp.setForeground(bg);
        topUp.setBounds(627, 12, 61, 16);

        // topUp button event (needs functionality)
        topUp.addActionListener(e -> {});

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

        RoundedButton betPlacer = new RoundedButton("bet");
        betPlacer.setFont(geistmono9);
        betPlacer.setBackground(Color.WHITE);
        betPlacer.setForeground(bg);
        betPlacer.setBounds(319, 246, 61, 16);

        // betPlacer button event (needs functionality)
        betPlacer.addActionListener(e -> {});

        JLabel informative = new JLabel("2x multiplier upon win");
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
        contentpane.add(betPlacer);
        contentpane.add(informative);

        frame.setVisible(true);
    }
}
