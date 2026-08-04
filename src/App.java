import javax.swing.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class App implements Runnable{
    static Color bg = new Color(11, 11, 11);
    static double balance = 100;
    static String username = "";

    static int switchs = 2; // 0 for over, 1 for under, 2 for none

    static Font geistmono6 = null;
    static Font geistmono9 = null;
    static Font geistmono10 = null;
    static Font geistmono12 = null;
    static Font geistmono20 = null;
    static Font instrument48 = null;

    static int dealerRNG;
    static int userRNG;

    static Timer loop;

    public static void main(String[] args) throws Exception {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");

            // claude icon support
            Image icon = Toolkit.getDefaultToolkit().getImage("res/img/icons/icon.png");

            if (Taskbar.isTaskbarSupported()) {
                Taskbar taskbar = Taskbar.getTaskbar();
                if (taskbar.isSupported(Taskbar.Feature.ICON_IMAGE)) {
                    taskbar.setIconImage(icon);
                }
            }

            FileInputStream fileInput = new FileInputStream("res/user/raw.txt");
            ObjectInputStream objectInput = new ObjectInputStream(fileInput);

            User currentUser = (User) objectInput.readObject();

            balance = currentUser.getBalance();

            objectInput.close();
            fileInput.close();

            System.out.println("Debug: User Balance successfully set!");

        } catch (Exception e) {
            e.printStackTrace();
        }

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

        App main = new App();
        Thread thread = new Thread(main);
        thread.start();

        menuScreen();
    }

    public void run() {
        while (true) {
            try {
                User u = new User(balance);

                u.setBalance(balance);

                FileOutputStream fileOutput = new FileOutputStream("res/user/raw.txt");
                ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);

                objectOutput.writeObject(u);

                objectOutput.close();
                fileOutput.close();

                System.out.println("Debug: User Balance Saved in: res/user/raw.txt");
                Thread.sleep(1000); // autosave per second
            } catch (Exception _) {}
        }
    }

    public static void menuScreen() {
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
        Font instrument64 = null;

        try {
            geistmono6 = Font.createFont(Font.TRUETYPE_FONT, new File("res/font/geistmono.ttf")).deriveFont(6f);
            geistmono9 = Font.createFont(Font.TRUETYPE_FONT, new File("res/font/geistmono.ttf")).deriveFont(9f);
            geistmono10 = Font.createFont(Font.TRUETYPE_FONT, new File("res/font/geistmono.ttf")).deriveFont(10f);
            geistmono12 = Font.createFont(Font.TRUETYPE_FONT, new File("res/font/geistmono.ttf")).deriveFont(12f);
            geistmono20 = Font.createFont(Font.TRUETYPE_FONT, new File("res/font/geistmono.ttf")).deriveFont(20f);
            instrument48 = Font.createFont(Font.TRUETYPE_FONT, new File("res/font/instrumentserif.ttf")).deriveFont(48f);
            instrument64 = Font.createFont(Font.TRUETYPE_FONT, new File("res/font/instrumentserif.ttf")).deriveFont(64f);

            GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
            graphicsEnvironment.registerFont(geistmono6);
            graphicsEnvironment.registerFont(geistmono9);
            graphicsEnvironment.registerFont(geistmono10);
            graphicsEnvironment.registerFont(geistmono12);
            graphicsEnvironment.registerFont(geistmono20);
            graphicsEnvironment.registerFont(instrument48);
            graphicsEnvironment.registerFont(instrument64);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        ImageIcon glassPanelImg = new ImageIcon("res/img/figma/glassyPanel.png");
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(null);
        panel.setBounds(0, 0, 136, 42);

        JLabel glassPanel = new JLabel(glassPanelImg);
        glassPanel.setBackground(null);
        glassPanel.setBounds(0, 0, 136, 42);

        JLabel title = new JLabel("obv");
        title.setFont(geistmono12);
        title.setForeground(Color.WHITE);
        title.setBounds(21, 13, 58, 16);

        JLabel userBalance = new JLabel("$"+ (int) balance);
        userBalance.setFont(geistmono12);
        userBalance.setForeground(Color.WHITE);
        userBalance.setBounds(653, 13, 300, 16);

        RoundedButton topUp = new RoundedButton("buy");
        topUp.setFont(geistmono9);
        topUp.setBackground(Color.WHITE);
        topUp.setForeground(bg);
        topUp.setBounds(51, 13, 69, 16);

        // topUp button event (needs functionality)
        topUp.addActionListener(e -> {
            frame.setVisible(false);
            frame.dispose();

            topUpScreen();
        });

        JLabel blackjackLabel = new JLabel("BLACKJACK");
        blackjackLabel.setFont(geistmono9);
        blackjackLabel.setForeground(new Color(55, 55, 55));
        blackjackLabel.setBounds(51, 78, 235, 83);

        RoundedPanel panel1 = new RoundedPanel(14);
        panel1.setBackgroundColor(new Color(21, 21, 21));
        panel1.setLayout(null);
        panel1.setBounds(51, 125, 277, 150);

        ImageIcon bjDesign = new ImageIcon("res/img/figma/blackjackDesign.png");
        JLabel bj = new JLabel(bjDesign);
        bj.setBackground(null);
        bj.setBounds(21, 2, 236, 150);

        RoundedButton confirmBlackjack = new RoundedButton("confirm");
        confirmBlackjack.setFont(geistmono6);
        confirmBlackjack.setBackground(Color.WHITE);
        confirmBlackjack.setForeground(bg);
        confirmBlackjack.setBounds(189, 119, 77, 21);

        // confirmBlackjack event
        confirmBlackjack.addActionListener(e ->{
            frame.setVisible(false);
            frame.dispose();

            blackjackScreen();
        });

        JLabel diceLabel = new JLabel("DICE");
        diceLabel.setFont(geistmono9);
        diceLabel.setForeground(new Color(55, 55, 55));
        diceLabel.setBounds(371, 78, 235, 83);

        RoundedPanel panel2 = new RoundedPanel(14);
        panel2.setBackgroundColor(new Color(21, 21, 21));
        panel2.setLayout(null);
        panel2.setBounds(371, 125, 277, 150);

        ImageIcon diceDesign = new ImageIcon("res/img/figma/diceDesign.png");
        JLabel dice = new JLabel(diceDesign);
        dice.setBounds(21, 2, 236, 150);

        RoundedButton confirmDice = new RoundedButton("confirm");
        confirmDice.setFont(geistmono6);
        confirmDice.setBackground(Color.WHITE);
        confirmDice.setForeground(bg);
        confirmDice.setBounds(189, 119, 77, 21);

        // confirmBlackjack event
        confirmDice.addActionListener(e -> {
            frame.setVisible(false);
            frame.dispose();
            diceScreen();
        });

        contentpane.add(userBalance);
        contentpane.add(panel);
        panel.add(title);
        panel.add(topUp);
        panel.add(glassPanel);

        contentpane.add(blackjackLabel);
        contentpane.add(panel1);
        panel1.add(confirmBlackjack);
        panel1.add(bj);

        contentpane.add(diceLabel);
        contentpane.add(panel2);
        panel2.add(confirmDice);
        panel2.add(dice);

        frame.setVisible(true);
    }

    public static void blackjackScreen() {
        JFrame frame = new JFrame();
        Container contentpane = frame.getContentPane();

        frame.setTitle("oblivion");
        frame.setSize(700, 400);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        contentpane.setBackground(bg);
        contentpane.setLayout(null);

        ImageIcon glassPanelImg = new ImageIcon("res/img/figma/glassyPanel.png");
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(null);
        panel.setBounds(0, 0, 136, 42);

        JLabel glassPanel = new JLabel(glassPanelImg);
        glassPanel.setBackground(null);
        glassPanel.setBounds(0, 0, 136, 42);

        ImageIcon menuIcon = new ImageIcon("res/img/icons/menuIcon21px.png");
        JButton menu = new JButton(menuIcon);
        menu.setBorderPainted(false);
        menu.setFocusPainted(false);
        menu.setContentAreaFilled(false);
        menu.setBackground(null);
        menu.setBounds(20, 11, 21, 21);

        // menu button event
        menu.addActionListener(e -> {
            System.out.println(glassPanelImg.getImageLoadStatus());
            frame.setVisible(false);
            frame.dispose();

            menuScreen();
        });

        JLabel userBalance = new JLabel("$"+ (int) balance);
        userBalance.setFont(geistmono12);
        userBalance.setForeground(Color.WHITE);
        userBalance.setBounds(655, 16, 700, 16);

        RoundedButton topUp = new RoundedButton("buy");
        topUp.setFont(geistmono9);
        topUp.setBackground(Color.WHITE);
        topUp.setForeground(bg);
        topUp.setBounds(51, 13, 69, 16);

        // topUp button event (needs functionality)
        topUp.addActionListener(e -> {
            frame.setVisible(false);
            frame.dispose();

            topUpScreen();
        });

        JLabel dealerInfo = new JLabel("DEALER MUST STAND ON ALL 17'S");
        dealerInfo.setFont(geistmono9);
        dealerInfo.setForeground(Color.WHITE);
        dealerInfo.setBounds(271, 48, 157, 12);

        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(null);
        tablePanel.setBackground(null);
        tablePanel.setBounds(83, 50, 533, 290);

        ImageIcon blackjackTableImg = new ImageIcon("res/img/figma/blackjackTable.png");
        JLabel blackjackTable = new JLabel(blackjackTableImg);
        blackjackTable.setBackground(null);
        blackjackTable.setBounds(0, 0, 533, 290);

        JLabel dealerNum = new JLabel("");
        dealerNum.setFont(geistmono9);
        dealerNum.setForeground(Color.WHITE);
        dealerNum.setBounds(261, 37, 533, 12);

        JLabel userCards = new JLabel("");
        userCards.setFont(geistmono9);
        userCards.setForeground(Color.WHITE);
        userCards.setBounds(261, 223, 60, 12);

        RoundedButton betPlacer = new RoundedButton("bet");
        betPlacer.setFont(geistmono9);
        betPlacer.setBackground(Color.WHITE);
        betPlacer.setForeground(bg);
        betPlacer.setBounds(319, 334, 61, 16);

        // i dont know how i made it work
        betPlacer.addActionListener(e -> {
            betPlacer.setVisible(false);

            dealerRNG = (int)(Math.random() * 11);
            userRNG = (int)(Math.random() * 11);

            dealerNum.setText(String.valueOf(dealerRNG));
            userCards.setText(String.valueOf(userRNG));

            RoundedButton hitButton = new RoundedButton("hit");
            hitButton.setFont(geistmono9);
            hitButton.setBackground(Color.WHITE);
            hitButton.setForeground(bg);
            hitButton.setBounds(193, 240, 61, 16);

            hitButton.addActionListener(k -> {
                userRNG += (int) (Math.random() * 11);

                dealerNum.setText(String.valueOf(dealerRNG));
                userCards.setText(String.valueOf(userRNG));

                if (userRNG == 21) {
                    balance *= 1.2;
                    System.out.println("Debug: won the game");

                    frame.setVisible(false);
                    frame.dispose();

                    blackjackScreen();
                } else if (userRNG > 21) {
                    balance -= 20;

                    frame.setVisible(false);
                    frame.dispose();

                    blackjackScreen();
                }

                System.out.println("Debug: User Cards: "+ userRNG + "\nDealer Cards: " + dealerRNG);
            });

            RoundedButton standButton = new RoundedButton("stand");
            standButton.setFont(geistmono9);
            standButton.setBackground(Color.WHITE);
            standButton.setForeground(bg);
            standButton.setBounds(271, 240, 69, 16);

            standButton.addActionListener(k -> {
                loop = new Timer(1000, j -> {
                    dealerRNG += (int) (Math.random() * 10) + 1;
                    dealerNum.setText(String.valueOf(dealerRNG));

                    if (dealerRNG >= 17) {
                        loop.stop();

                        if (dealerRNG > 21) {
                            balance *= 1.2;
                        } else if (dealerRNG == 21) {
                            balance -= 2000;
                        } else if (dealerRNG < userRNG) {
                            balance += 1.2;
                        } else if (dealerRNG > userRNG) {
                            balance -= 200;
                        }

                        System.out.println("Debug: User cards: " + userRNG + "\nDealer Cards: " + dealerRNG);

                        frame.setVisible(false);
                        frame.dispose();
                        blackjackScreen();
                    }
                });
                loop.start();
            });

            tablePanel.add(hitButton, 0);
            tablePanel.add(standButton, 0);

            tablePanel.repaint();
            tablePanel.revalidate();
            contentpane.repaint();
            contentpane.revalidate();
        });

        contentpane.add(panel);
        panel.add(menu);
        panel.add(topUp);
        panel.add(glassPanel);

        contentpane.add(userBalance);
        contentpane.add(dealerInfo);

        contentpane.add(betPlacer);

        contentpane.add(tablePanel);
        tablePanel.add(dealerNum);
        tablePanel.add(userCards);
        tablePanel.add(blackjackTable);

        frame.setVisible(true);
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

        ImageIcon glassPanelImg = new ImageIcon("res/img/figma/glassyPanel.png");
        JPanel gPanel = new JPanel();
        gPanel.setLayout(null);
        gPanel.setBackground(null);
        gPanel.setBounds(0, 0, 136, 42);

        JLabel glassPanel = new JLabel(glassPanelImg);
        glassPanel.setBackground(null);
        glassPanel.setBounds(0, 0, 136, 42);

        ImageIcon menuIcon = new ImageIcon("res/img/icons/menuIcon21px.png");
        JButton menu = new JButton(menuIcon);
        menu.setBorderPainted(false);
        menu.setFocusPainted(false);
        menu.setContentAreaFilled(false);
        menu.setBackground(null);
        menu.setBounds(20, 11, 21, 21);

        // menu button event
        menu.addActionListener(e -> {
            frame.setVisible(false);
            frame.dispose();

            menuScreen();
        });

        RoundedButton topUp = new RoundedButton("buy");
        topUp.setFont(geistmono9);
        topUp.setBackground(Color.WHITE);
        topUp.setForeground(bg);
        topUp.setBounds(51, 13, 69, 16);

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
            question2.setBounds(103, 37, 300, 16);
        } else if (pending == 1000) {
            question2.setBounds(99, 37, 300, 16);
        } else if (pending == 10000) {
            question2.setBounds(96, 37, 300, 16);
        } else if (pending == 100000) {
            question2.setBounds(92, 37, 300, 16);
        } else if (pending == 1000000) {
            question2.setBounds(88, 37, 300, 16);
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

            diceScreen();
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

        contentpane.add(gPanel);
        gPanel.add(menu);
        gPanel.add(topUp);
        gPanel.add(glassPanel);

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

        ImageIcon glassPanelImg = new ImageIcon("res/img/figma/glassyPanel.png");
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(null);
        menuPanel.setBackground(null);
        menuPanel.setBounds(0, 0, 136, 42);

        JLabel glassPanel = new JLabel(glassPanelImg);
        glassPanel.setBackground(null);
        glassPanel.setBounds(0, 0, 136, 42);

        ImageIcon menuIcon = new ImageIcon("res/img/icons/menuIcon21px.png");
        JButton menu = new JButton(menuIcon);
        menu.setBorderPainted(false);
        menu.setFocusPainted(false);
        menu.setContentAreaFilled(false);
        menu.setBackground(null);
        menu.setBounds(20, 11, 21, 21);

        // menu button event
        menu.addActionListener(e -> {
            frame.setVisible(false);
            frame.dispose();

            menuScreen();
        });

        JLabel userBalance = new JLabel("$"+ (int) balance);
        userBalance.setFont(geistmono12);
        userBalance.setForeground(Color.WHITE);
        userBalance.setBounds(653, 13, 300, 16);

        RoundedButton topUp = new RoundedButton("buy");
        topUp.setFont(geistmono9);
        topUp.setBackground(Color.WHITE);
        topUp.setForeground(bg);
        topUp.setBounds(51, 13, 69, 16);

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

        menuPanel.add(menu);
        menuPanel.add(topUp);
        menuPanel.add(glassPanel);

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

        contentpane.add(menuPanel);
        contentpane.add(userBalance);
        contentpane.add(paymentsLabel);
        contentpane.add(panel);

        frame.setVisible(true);
    }

    public static void diceScreen() {
        JFrame frame = new JFrame();
        Container contentpane = frame.getContentPane();

        frame.setTitle("oblivion");
        frame.setSize(700, 400);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        contentpane.setBackground(bg);
        contentpane.setLayout(null);

        ImageIcon glassPanelImg = new ImageIcon("res/img/figma/glassyPanel.png");
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(null);
        panel.setBounds(0, 0, 136, 42);

        JLabel glassPanel = new JLabel(glassPanelImg);
        glassPanel.setBackground(null);
        glassPanel.setBounds(0, 0, 136, 42);

        ImageIcon menuIcon = new ImageIcon("res/img/icons/menuIcon21px.png");
        JButton menu = new JButton(menuIcon);
        menu.setBorderPainted(false);
        menu.setFocusPainted(false);
        menu.setContentAreaFilled(false);
        menu.setBackground(null);
        menu.setBounds(20, 11, 21, 21);

        // menu button event
        menu.addActionListener(e -> {
            System.out.println(glassPanelImg.getImageLoadStatus());
            frame.setVisible(false);
            frame.dispose();

            menuScreen();
        });

        JLabel userBalance = new JLabel("$"+ (int) balance);
        userBalance.setFont(geistmono12);
        userBalance.setForeground(Color.WHITE);
        userBalance.setBounds(655, 16, 700, 16);

        RoundedButton topUp = new RoundedButton("buy");
        topUp.setFont(geistmono9);
        topUp.setBackground(Color.WHITE);
        topUp.setForeground(bg);
        topUp.setBounds(51, 13, 69, 16);

        // topUp button event (needs functionality)
        topUp.addActionListener(e -> {
            frame.setVisible(false);
            frame.dispose();

            topUpScreen();
        });

        JLabel dice = new JLabel("Dice");
        dice.setFont(instrument48);
        dice.setForeground(Color.WHITE);
        dice.setBounds(316, 143, 70, 62);

        JLabel zero = new JLabel("0");
        zero.setFont(geistmono12);
        zero.setForeground(new Color(55, 55, 55));
        zero.setBounds(92, 197, 8, 16);

        JLabel hundred = new JLabel("100");
        hundred.setFont(geistmono12);
        hundred.setForeground(new Color(55, 55, 55));
        hundred.setBounds(587, 197, 22, 16);

        ImageIcon backgroundImg = new ImageIcon("res/img/figma/sliderBackground.png");
        JLabel background = new JLabel(backgroundImg);
        background.setBackground(null);
        background.setBounds(92, 218, 517, 14);

        JSlider slider = new JSlider(0, 100, 35);
        slider.setUI(new sliderUI(slider));
        slider.setOpaque(false);
        slider.setBounds(85, 218, 525, 14);

        slider.addChangeListener(e -> slider.repaint());

        RoundedButton over = new RoundedButton("over");
        over.setFont(geistmono9);
        over.setBackground(Color.WHITE);
        over.setForeground(bg);
        over.setBounds(92, 240, 69, 16);

        over.addActionListener(e -> {
            switchs = 0;
        });

        RoundedButton under = new RoundedButton("under");
        under.setFont(geistmono9);
        under.setBackground(Color.WHITE);
        under.setForeground(bg);
        under.setBounds(170, 240, 69, 16);

        under.addActionListener(e -> {
            switchs = 1;
        });

        RoundedButton betPlacer = new RoundedButton("bet");
        betPlacer.setFont(geistmono9);
        betPlacer.setBackground(Color.WHITE);
        betPlacer.setForeground(bg);
        betPlacer.setBounds(548, 240, 61, 16);

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

                userBalance.setText("$" + String.valueOf((int) balance));

                contentpane.repaint();
                contentpane.revalidate();
            } // else do nothing
        });

        JLabel informative = new JLabel("1.2x multiplier upon win");
        informative.setFont(geistmono6);
        informative.setForeground(new Color(55, 55, 55));
        informative.setBounds(310, 345, 105, 8);

        contentpane.add(panel);
        panel.add(menu);
        panel.add(topUp);
        panel.add(glassPanel);

        contentpane.add(userBalance);
        contentpane.add(dice);
        contentpane.add(zero);
        contentpane.add(hundred);
        contentpane.add(slider);   
        contentpane.add(background);
        contentpane.add(over);
        contentpane.add(under);
        contentpane.add(betPlacer);
        contentpane.add(informative);

        frame.setVisible(true);
    }
}