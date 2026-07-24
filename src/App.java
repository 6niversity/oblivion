import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

// import java.io.FileInputStream;
// import java.io.ObjectInputStream;

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

    public static void confirmationScreen(int pending) {
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
        Font geistmono10 = null;
        Font geistmono12 = null;
        Font geistmono20 = null;
        Font instrument48 = null;

        try {
            geistmono6 = Font.createFont(Font.TRUETYPE_FONT, new File("res/font/geistmono.ttf")).deriveFont(6f);
            geistmono9 = Font.createFont(Font.TRUETYPE_FONT, new File("res/font/geistmono.ttf")).deriveFont(9f);
            geistmono10 = Font.createFont(Font.TRUETYPE_FONT, new File("res/font/geistmono.ttf")).deriveFont(10f);
            geistmono12 = Font.createFont(Font.TRUETYPE_FONT, new File("res/font/geistmono.ttf")).deriveFont(12f);
            geistmono20 = Font.createFont(Font.TRUETYPE_FONT, new File("res/font/geistmono.ttf")).deriveFont(20f);
            instrument48 = Font.createFont(Font.TRUETYPE_FONT, new File("res/font/instrumentserif.ttf")).deriveFont(48f);

            GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
            graphicsEnvironment.registerFont(geistmono6);
            graphicsEnvironment.registerFont(geistmono9);
            graphicsEnvironment.registerFont(geistmono10);
            graphicsEnvironment.registerFont(geistmono12);
            graphicsEnvironment.registerFont(geistmono20);
            graphicsEnvironment.registerFont(instrument48);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        JLabel title = new JLabel("oblivion");
        title.setFont(geistmono12);
        title.setForeground(Color.WHITE);
        title.setBounds(14, 12, 58, 16);

        JLabel confirmLabel = new JLabel("Confirm");
        confirmLabel.setFont(instrument48);
        confirmLabel.setForeground(Color.WHITE);
        confirmLabel.setBounds(200, 121, 145, 47);

        RoundedPanel panel = new RoundedPanel(20);
        panel.setBackgroundColor(new Color(21, 21, 21));
        panel.setLayout(null);
        panel.setBounds(200, 168, 300, 111);

        JLabel question1 = new JLabel("Are you sure you want to");
        question1.setFont(geistmono12);
        question1.setForeground(Color.WHITE);
        question1.setBounds(63, 21, 173, 16);

        JLabel question2 = new JLabel("purchase " + pending + "?");
        question2.setFont(geistmono12);
        question2.setForeground(Color.WHITE);
        if (pending == 100) {
            question2.setBounds(103, 37, 173, 16);
        } else if (pending == 1000) {
            question2.setBounds(99, 37, 108, 16);
        } else if (pending == 10000) {
            question2.setBounds(96, 37, 108, 16);
        } else if (pending == 100000) {
            question2.setBounds(92, 37, 108, 16);
        } else if (pending == 1000000) {
            question2.setBounds(88, 37, 108, 16);
        }

        RoundedButton confirm = new RoundedButton("confirm");
        confirm.setFont(geistmono6);
        confirm.setBackground(Color.WHITE);
        confirm.setForeground(bg);
        confirm.setBounds(64, 69, 77, 21);

        // confirm event
        confirm.addActionListener(e -> {
            balance += pending;
            frame.setVisible(false);
            frame.dispose();

            mainScreen();
        });;

        GreyButton cancel = new GreyButton("cancel");
        cancel.setFont(geistmono6);
        cancel.setBounds(159, 69, 77, 21);

        // cancel event
        cancel.addActionListener(e -> {
            frame.setVisible(false);
            frame.dispose();

            topUpScreen();
        });

        panel.add(question1);
        panel.add(question2);
        panel.add(cancel);
        panel.add(confirm);

        contentpane.add(title);
        contentpane.add(confirmLabel);
        contentpane.add(panel);

        frame.setVisible(true);
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
        Font geistmono10 = null;
        Font geistmono12 = null;
        Font geistmono20 = null;
        Font instrument48 = null;

        try {
            geistmono6 = Font.createFont(Font.TRUETYPE_FONT, new File("res/font/geistmono.ttf")).deriveFont(6f);
            geistmono9 = Font.createFont(Font.TRUETYPE_FONT, new File("res/font/geistmono.ttf")).deriveFont(9f);
            geistmono10 = Font.createFont(Font.TRUETYPE_FONT, new File("res/font/geistmono.ttf")).deriveFont(10f);
            geistmono12 = Font.createFont(Font.TRUETYPE_FONT, new File("res/font/geistmono.ttf")).deriveFont(12f);
            geistmono20 = Font.createFont(Font.TRUETYPE_FONT, new File("res/font/geistmono.ttf")).deriveFont(20f);
            instrument48 = Font.createFont(Font.TRUETYPE_FONT, new File("res/font/instrumentserif.ttf")).deriveFont(48f);

            GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
            graphicsEnvironment.registerFont(geistmono6);
            graphicsEnvironment.registerFont(geistmono9);
            graphicsEnvironment.registerFont(geistmono10);
            graphicsEnvironment.registerFont(geistmono12);
            graphicsEnvironment.registerFont(geistmono20);
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
        userBalanace.setBounds(14, 342, 300, 16);

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
        panel.setBackgroundColor(new Color(21, 21, 21));
        panel.setLayout(null);
        panel.setBounds(200, 116, 300, 221);

        JLabel hundred = new JLabel("$100");
        hundred.setFont(geistmono20);
        hundred.setForeground(Color.WHITE);
        hundred.setBounds(15, 9, 175, 47);

        JLabel thousand = new JLabel("$1000");
        thousand.setFont(geistmono20);
        thousand.setForeground(Color.WHITE);
        thousand.setBounds(15, 49, 200, 47);

        JLabel tenthousand = new JLabel("$10,000");
        tenthousand.setFont(geistmono20);
        tenthousand.setForeground(Color.WHITE);
        tenthousand.setBounds(15, 98, 210, 26);

        JLabel hundredthousand = new JLabel("$100,000");
        hundredthousand.setFont(geistmono20);
        hundredthousand.setForeground(Color.WHITE);
        hundredthousand.setBounds(15, 137, 225, 26);

        JLabel million = new JLabel("$1,000,000");
        million.setFont(geistmono20);
        million.setForeground(Color.WHITE);
        million.setBounds(15, 176, 250, 26);

        RoundedButton purchasehundred = new RoundedButton("purchase");
        purchasehundred.setFont(geistmono10);
        purchasehundred.setBackground(Color.WHITE);
        purchasehundred.setForeground(Color.BLACK);
        purchasehundred.setBounds(185, 22, 99, 21);

        // purchasehundred event
        purchasehundred.addActionListener(e -> {
            frame.setVisible(false);
            frame.dispose();
            
            confirmationScreen(100);
        });

        RoundedButton purchasethousand = new RoundedButton("purchase");
        purchasethousand.setFont(geistmono10);
        purchasethousand.setBackground(Color.WHITE);
        purchasethousand.setForeground(Color.BLACK);
        purchasethousand.setBounds(185, 62, 99, 21);
        
        // purchasethousand event
        purchasethousand.addActionListener(e -> {
            frame.setVisible(false);
            frame.dispose();
            
            confirmationScreen(1000);
        });

        RoundedButton purchasetenthousand = new RoundedButton("purchase");
        purchasetenthousand.setFont(geistmono10);
        purchasetenthousand.setBackground(Color.WHITE);
        purchasetenthousand.setForeground(Color.BLACK);
        purchasetenthousand.setBounds(185, 102, 99, 21);

        // purchasetenthousand event
        purchasetenthousand.addActionListener(e -> {
            frame.setVisible(false);
            frame.dispose();
            
            confirmationScreen(10000);
        });

        RoundedButton purchasehundredthousand = new RoundedButton("purchase");
        purchasehundredthousand.setFont(geistmono10);
        purchasehundredthousand.setBackground(Color.WHITE);
        purchasehundredthousand.setForeground(Color.BLACK);
        purchasehundredthousand.setBounds(185, 142, 99, 21);

        // purchasehundredthousand event
        purchasehundredthousand.addActionListener(e -> {
            frame.setVisible(false);
            frame.dispose();
            
            confirmationScreen(100000);
        });

        RoundedButton purchasemillion = new RoundedButton("purchase");
        purchasemillion.setFont(geistmono10);
        purchasemillion.setBackground(Color.WHITE);
        purchasemillion.setForeground(Color.BLACK);
        purchasemillion.setBounds(185, 182, 99, 21);

        // purchasemillion
        purchasemillion.addActionListener(e -> {
            frame.setVisible(false);
            frame.dispose();
            
            confirmationScreen(1000000);
        });   

        panel.add(hundred);
        panel.add(thousand);
        panel.add(tenthousand);
        panel.add(hundredthousand);
        panel.add(million);

        panel.add(purchasehundred);
        panel.add(purchasethousand);
        panel.add(purchasetenthousand);
        panel.add(purchasehundredthousand);
        panel.add(purchasemillion);

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

        JLabel userBalanace = new JLabel("$"+ (int) balance);
        userBalanace.setFont(geistmono12);
        userBalanace.setForeground(Color.WHITE);
        userBalanace.setBounds(14, 342, 300, 16);

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