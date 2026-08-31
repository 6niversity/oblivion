import javax.swing.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class App implements Runnable {
    static String theme = "DARK"; // default: dark
    static String themePath = "res/img/figma/dark/"; // default theme path

    static Color bg; // background colour of the application
    static Color buttonBackground; // button background colour
    static Color lightBlack; // for smaller texts
    static Color panelBackground; // panel colour

    static double balance = 100; // user balance to start out
    static String username; // username (not used yet)

    static int switchs = 2; // 0 for over, 1 for under, 2 for none

    // fonts & sizes
    static Font geistmono6 = null;
    static Font geistmono9 = null;
    static Font geistmono10 = null;
    static Font geistmono12 = null;
    static Font geistmono20 = null;
    static Font geistmono96 = null;
    static Font instrument48 = null;

    // random generated numbers
    static int dealerRNG;
    static int userRNG;
    static boolean hasWon;

    // loop and 
    static Timer loop;
    static int num = 100;

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

            File rawFile = new File("res/user/raw.txt");

            if (rawFile.length() > 0) {
                FileInputStream fileInput = new FileInputStream("res/user/raw.txt");
                ObjectInputStream objectInput = new ObjectInputStream(fileInput);

                User currentUser = (User) objectInput.readObject();

                balance = currentUser.getBalance();
                theme = currentUser.getTheme();

                objectInput.close();
                fileInput.close();

                System.out.println("Debug: User Balance successfully set!");
            } else {
                User u = new User(balance, theme);

                u.setBalance(balance);
                u.setTheme(theme);

                FileOutputStream fileOutput = new FileOutputStream("res/user/raw.txt");
                ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);

                objectOutput.writeObject(u);

                objectOutput.close();
                fileOutput.close();

                System.out.println("Debug: User Balance & Theme Saved in: res/user/raw.txt");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            geistmono6 = Font.createFont(Font.TRUETYPE_FONT, new File("res/font/geistmono.ttf")).deriveFont(6f);
            geistmono9 = Font.createFont(Font.TRUETYPE_FONT, new File("res/font/geistmono.ttf")).deriveFont(9f);
            geistmono10 = Font.createFont(Font.TRUETYPE_FONT, new File("res/font/geistmono.ttf")).deriveFont(10f);
            geistmono12 = Font.createFont(Font.TRUETYPE_FONT, new File("res/font/geistmono.ttf")).deriveFont(12f);
            geistmono20 = Font.createFont(Font.TRUETYPE_FONT, new File("res/font/geistmono.ttf")).deriveFont(20f);
            geistmono96 = Font.createFont(Font.TRUETYPE_FONT, new File("res/font/geistmono.ttf")).deriveFont(96f);
            instrument48 = Font.createFont(Font.TRUETYPE_FONT, new File("res/font/instrumentserif.ttf")).deriveFont(48f);

            GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
            graphicsEnvironment.registerFont(geistmono6);
            graphicsEnvironment.registerFont(geistmono9);
            graphicsEnvironment.registerFont(geistmono10);
            graphicsEnvironment.registerFont(geistmono12);
            graphicsEnvironment.registerFont(geistmono20);
            graphicsEnvironment.registerFont(geistmono96);
            graphicsEnvironment.registerFont(instrument48);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        // load dark/light theme
        if (theme.equals("DARK")) {
            themePath = "res/img/figma/dark/";
            bg = new Color(11, 11, 11); // background colour of the application
            buttonBackground = new Color(255, 255, 255); // button background colour
            lightBlack = new Color(55, 55, 55); // for smaller texts
            panelBackground = new Color(21, 21, 21); // panel colour
        } else {
            themePath = "res/img/figma/light/";
            bg = new Color(245, 245, 245);
            buttonBackground = new Color(26, 26, 26);
            panelBackground = new Color(230, 230, 227);
            lightBlack = new Color(155, 155, 152);
        }

        App main = new App();
        Thread thread = new Thread(main);
        thread.start();

        menuScreen();
    }

    public void run() {
        while (true) {
            try {
                User u = new User(balance, theme);

                u.setBalance(balance);

                FileOutputStream fileOutput = new FileOutputStream("res/user/raw.txt");
                ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);

                objectOutput.writeObject(u);

                objectOutput.close();
                fileOutput.close();

                System.out.println("Debug: User Balance & Theme Saved in: res/user/raw.txt");
                Thread.sleep(200); // autosave every 2 miliseconds
            } catch (Exception _) {}
        }
    }

    public static void settingScreen() {
        JFrame frame = new JFrame();
        Container contentpane = frame.getContentPane();

        frame.setTitle("oblivion");
        frame.setSize(700, 400);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        contentpane.setBackground(bg);
        contentpane.setLayout(null);
        
        ImageIcon glassPanelImg = new ImageIcon(themePath + "glassyPanel.png");
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

        ImageIcon glassyPanelBalance = new ImageIcon(themePath + "glassyPanelBalance.png");
        JLabel glassPanelBal = new JLabel(glassyPanelBalance);
        glassPanelBal.setBounds(629, 0, 71, 42);

        JLabel userBalance = new JLabel();
        userBalance.setFont(geistmono12);
        userBalance.setForeground(buttonBackground);

        // userBalance display
        if (balance >= 1000 && balance < 10000) { // 1k to 10k
            userBalance.setText("$" + String.valueOf((int) balance).substring(0, 1) + "K");
            userBalance.setBounds(654, 13, 700, 16);
        } else if (balance >= 10000 && balance < 100000) { // 10k to 100k
            userBalance.setText("$" + String.valueOf((int) balance).substring(0, 2) + "K");
            userBalance.setBounds(649, 13, 700, 16);
        } else if (balance >= 100000 && balance < 1000000) { // 100k to 1m
            userBalance.setText("$" + String.valueOf((int) balance).substring(0, 3) + "K");
            userBalance.setBounds(647, 13, 700, 16);
        } else if (balance >= 1000000 && balance < 10000000) { // 1m to 10m
            userBalance.setText("$" + String.valueOf((int) balance).substring(0, 1) + "M");
            userBalance.setBounds(653, 13, 700, 16);
        } else if (balance >= 10000000 && balance < 100000000) { // 10m to 100m
            userBalance.setText("$" + String.valueOf((int) balance).substring(0, 2) + "M");
            userBalance.setBounds(649, 13, 700, 16);
        } else if (balance >= 100000000 && balance < 1000000000) { // 100m to 1b
            userBalance.setText("$" + String.valueOf((int) balance).substring(0, 3) + "M");
            userBalance.setBounds(643, 13, 700, 16);
        } else if (balance >= 1000000000) {
            userBalance.setText("$" + String.valueOf((int) balance).substring(0, 3) + "M");
            userBalance.setBounds(647, 13, 700, 16);
        } else {
            userBalance.setText("$" + String.valueOf((int) balance));

            if (balance >= 0 && balance < 10) {
                userBalance.setBounds(657, 13, 15, 16);
            } else if (balance >= 10 && balance < 100) {
                userBalance.setBounds(654, 13, 15, 16);
            } else if (balance >= 100 && balance < 1000) {
                userBalance.setBounds(650, 13, 700, 16);
            }
        }

        RoundedButton topUp = new RoundedButton("buy");
        topUp.setFont(geistmono9);
        topUp.setBackground(buttonBackground);
        topUp.setForeground(bg);
        topUp.setBounds(51, 13, 69, 16);

        // topUp button event
        topUp.addActionListener(e -> {
            frame.setVisible(false);
            frame.dispose();

            topUpScreen();
        });

        ImageIcon glass = new ImageIcon(themePath + "settingsPanel.png");
        JLabel settingsPanel = new JLabel(glass);
        settingsPanel.setBackground(null);
        settingsPanel.setBounds(271, 121, 157, 157);

        JLabel themeLabel = new JLabel("THEME");
        themeLabel.setFont(geistmono9);
        themeLabel.setForeground(buttonBackground);
        themeLabel.setBounds(336, 157, 27, 12);

        RoundedButton themeSwitcher = new RoundedButton(theme);
        themeSwitcher.setFont(geistmono6);
        themeSwitcher.setBackground(buttonBackground);
        themeSwitcher.setForeground(bg);
        themeSwitcher.setBounds(315, 173, 69, 16);

        // themeSwitcher event
        themeSwitcher.addActionListener(e -> {
            if (theme.equals("DARK")) {
                theme = "LIGHT";

                try {Thread.sleep(500);} catch (Exception _) {}

                System.exit(0);
            } else {
                theme = "DARK";

                try {Thread.sleep(500);} catch (Exception _) {}
                System.exit(0);
            }
        });

        JLabel resetLabel = new JLabel("RESET PROGRESS");
        resetLabel.setFont(geistmono9);
        resetLabel.setForeground(buttonBackground);
        resetLabel.setBounds(312, 211, 76, 12);

        RoundedButton resetProgress = new RoundedButton("RESET");
        resetProgress.setFont(geistmono6);
        resetProgress.setForeground(bg);
        resetProgress.setBackground(buttonBackground);
        resetProgress.setBounds(315, 226, 69, 16);

        // resetProgress event
        resetProgress.addActionListener(e -> {
            balance = 200;

            userBalance.setText("$" + String.valueOf((int) balance));

            contentpane.revalidate();
            contentpane.repaint();
        });

        contentpane.add(panel);
        contentpane.add(userBalance);
        contentpane.add(glassPanelBal);
        panel.add(menu);
        panel.add(topUp);
        panel.add(glassPanel);

        contentpane.add(themeLabel);
        contentpane.add(themeSwitcher);
        contentpane.add(resetLabel);
        contentpane.add(resetProgress);
        contentpane.add(settingsPanel);

        frame.setVisible(true);
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

        ImageIcon glassPanelImg = new ImageIcon(themePath + "glassyPanel.png");
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(null);
        panel.setBounds(0, 0, 136, 42);

        JLabel glassPanel = new JLabel(glassPanelImg);
        glassPanel.setBackground(null);
        glassPanel.setBounds(0, 0, 136, 42);

        JLabel title = new JLabel("obv");
        title.setFont(geistmono12);
        title.setForeground(buttonBackground);
        title.setBounds(21, 13, 58, 16);

        ImageIcon glassyPanelBalance = new ImageIcon(themePath + "glassyPanelBalance.png");
        JLabel glassPanelBal = new JLabel(glassyPanelBalance);
        glassPanelBal.setBounds(629, 0, 71, 42);

        JLabel userBalance = new JLabel();
        userBalance.setFont(geistmono12);
        userBalance.setForeground(buttonBackground);

        // userBalance display
        if (balance >= 1000 && balance < 10000) { // 1k to 10k
            userBalance.setText("$" + String.valueOf((int) balance).substring(0, 1) + "K");
            userBalance.setBounds(654, 13, 700, 16);
        } else if (balance >= 10000 && balance < 100000) { // 10k to 100k
            userBalance.setText("$" + String.valueOf((int) balance).substring(0, 2) + "K");
            userBalance.setBounds(649, 13, 700, 16);
        } else if (balance >= 100000 && balance < 1000000) { // 100k to 1m
            userBalance.setText("$" + String.valueOf((int) balance).substring(0, 3) + "K");
            userBalance.setBounds(647, 13, 700, 16);
        } else if (balance >= 1000000 && balance < 10000000) { // 1m to 10m
            userBalance.setText("$" + String.valueOf((int) balance).substring(0, 1) + "M");
            userBalance.setBounds(653, 13, 700, 16);
        } else if (balance >= 10000000 && balance < 100000000) { // 10m to 100m
            userBalance.setText("$" + String.valueOf((int) balance).substring(0, 2) + "M");
            userBalance.setBounds(649, 13, 700, 16);
        } else if (balance >= 100000000 && balance < 1000000000) { // 100m to 1b
            userBalance.setText("$" + String.valueOf((int) balance).substring(0, 3) + "M");
            userBalance.setBounds(643, 13, 700, 16);
        } else if (balance >= 1000000000) {
            userBalance.setText("$" + String.valueOf((int) balance).substring(0, 3) + "M");
            userBalance.setBounds(647, 13, 700, 16);
        } else {
            userBalance.setText("$" + String.valueOf((int) balance));

            if (balance >= 0 && balance < 10) {
                userBalance.setBounds(657, 13, 15, 16);
            } else if (balance >= 10 && balance < 100) {
                userBalance.setBounds(654, 13, 15, 16);
            } else if (balance >= 100 && balance < 1000) {
                userBalance.setBounds(650, 13, 700, 16);
            }
        }

        RoundedButton topUp = new RoundedButton("buy");
        topUp.setFont(geistmono9);
        topUp.setBackground(buttonBackground);
        topUp.setForeground(bg);
        topUp.setBounds(51, 13, 69, 16);

        // topUp button event (needs functionality)
        topUp.addActionListener(e -> {
            frame.setVisible(false);
            frame.dispose();

            topUpScreen();
        });

        ImageIcon settingsIcon = new ImageIcon("res/img/icons/settingsIcon.png");
        JButton settingsButton = new JButton(settingsIcon);
        settingsButton.setBackground(null);
        settingsButton.setBorderPainted(false);
        settingsButton.setFocusPainted(false);
        settingsButton.setOpaque(false);
        settingsButton.setBounds(12, 335, 24, 24);

        // settingsButton event
        settingsButton.addActionListener(e -> {
            frame.setVisible(false);
            frame.dispose();

            settingScreen();
        });

        JLabel blackjackLabel = new JLabel("BLACKJACK");
        blackjackLabel.setFont(geistmono9);
        blackjackLabel.setForeground(lightBlack);
        blackjackLabel.setBounds(94, 42, 235, 12);

        RoundedPanel panel1 = new RoundedPanel(14);
        panel1.setBackgroundColor(panelBackground);
        panel1.setLayout(null);
        panel1.setBounds(94, 54, 245, 132);

        ImageIcon bjDesign = new ImageIcon(themePath + "blackjackDesign.png");
        JLabel bj = new JLabel(bjDesign);
        bj.setBounds(15, 7, 216, 117);

        RoundedButton confirmBlackjack = new RoundedButton("confirm");
        confirmBlackjack.setFont(geistmono6);
        confirmBlackjack.setBackground(buttonBackground);
        confirmBlackjack.setForeground(bg);
        confirmBlackjack.setBounds(161, 106, 74, 16);

        // confirmBlackjack event
        confirmBlackjack.addActionListener(e ->{
            frame.setVisible(false);
            frame.dispose();

            blackjackScreen();
        });

        JLabel diceLabel = new JLabel("DICE");
        diceLabel.setFont(geistmono9);
        diceLabel.setForeground(lightBlack);
        diceLabel.setBounds(360, 42, 235, 12);

        RoundedPanel panel2 = new RoundedPanel(14);
        panel2.setBackgroundColor(panelBackground);
        panel2.setLayout(null);
        panel2.setBounds(360, 54, 245, 132);

        ImageIcon diceDesign = new ImageIcon(themePath + "diceDesign.png");
        JLabel dice = new JLabel(diceDesign);
        dice.setBounds(15, 7, 216, 117);

        RoundedButton confirmDice = new RoundedButton("confirm");
        confirmDice.setFont(geistmono6);
        confirmDice.setBackground(buttonBackground);
        confirmDice.setForeground(bg);
        confirmDice.setBounds(161, 106, 74, 16);

        // confirmDice event
        confirmDice.addActionListener(e -> {
            frame.setVisible(false);
            frame.dispose();
            diceScreen();
        });

        JLabel crashLabel = new JLabel("CRASH");
        crashLabel.setFont(geistmono9);
        crashLabel.setForeground(lightBlack);
        crashLabel.setBounds(94, 193, 235, 12);

        RoundedPanel panel3 = new RoundedPanel(14);
        panel3.setBackgroundColor(panelBackground);
        panel3.setLayout(null);
        panel3.setBounds(94, 205, 245, 132);

        ImageIcon crashDesign = new ImageIcon(themePath + "crashDesign.png");
        JLabel crash = new JLabel(crashDesign);
        crash.setBounds(15, 7, 216, 117);

        RoundedButton confirmCrash = new RoundedButton("confirm");
        confirmCrash.setFont(geistmono6);
        confirmCrash.setBackground(buttonBackground);
        confirmCrash.setForeground(bg);
        confirmCrash.setBounds(161, 106, 74, 16);

        // confirmCrash event
        confirmCrash.addActionListener(e -> {
            frame.setVisible(false);
            frame.dispose();

            crashScreen();
        });

        JLabel towersLabel = new JLabel("TOWERS");
        towersLabel.setFont(geistmono9);
        towersLabel.setForeground(lightBlack);
        towersLabel.setBounds(360, 193, 50, 12);

        RoundedPanel panel4 = new RoundedPanel(14);
        panel4.setBackgroundColor(panelBackground);
        panel4.setLayout(null);
        panel4.setBounds(360, 205, 245, 132);

        ImageIcon towersDesign = new ImageIcon(themePath + "towersDesign.png");
        JLabel towers = new JLabel(towersDesign);
        towers.setBounds(15, 7, 216, 117);

        RoundedButton confirmTowers = new RoundedButton("confirm");
        confirmTowers.setFont(geistmono6);
        confirmTowers.setBackground(buttonBackground);
        confirmTowers.setForeground(bg);
        confirmTowers.setBounds(161, 106, 74, 16);

        // confirmTowers event
        confirmTowers.addActionListener(e -> {
            frame.setVisible(false);
            frame.dispose();

            towersScreen();
        });

        contentpane.add(userBalance);
        contentpane.add(glassPanelBal);
        contentpane.add(settingsButton);

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

        contentpane.add(crashLabel);
        contentpane.add(panel3);
        panel3.add(confirmCrash);
        panel3.add(crash);

        contentpane.add(towersLabel);
        contentpane.add(panel4);
        panel4.add(confirmTowers);
        panel4.add(towers);

        frame.setVisible(true);
    }

    public static void towersScreen() {
        JFrame frame = new JFrame();
        Container contentpane = frame.getContentPane();

        frame.setTitle("oblivion");
        frame.setSize(700, 400);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        contentpane.setBackground(bg);
        contentpane.setLayout(null);

        ImageIcon glassPanelImg = new ImageIcon(themePath + "glassyPanel.png");
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

        ImageIcon glassyPanelBalance = new ImageIcon(themePath + "glassyPanelBalance.png");
        JLabel glassPanelBal = new JLabel(glassyPanelBalance);
        glassPanelBal.setBounds(629, 0, 71, 42);

        JLabel userBalance = new JLabel();
        userBalance.setFont(geistmono12);
        userBalance.setForeground(buttonBackground);

        // userBalance display
        if (balance >= 1000 && balance < 10000) { // 1k to 10k
            userBalance.setText("$" + String.valueOf((int) balance).substring(0, 1) + "K");
            userBalance.setBounds(654, 13, 700, 16);
        } else if (balance >= 10000 && balance < 100000) { // 10k to 100k
            userBalance.setText("$" + String.valueOf((int) balance).substring(0, 2) + "K");
            userBalance.setBounds(649, 13, 700, 16);
        } else if (balance >= 100000 && balance < 1000000) { // 100k to 1m
            userBalance.setText("$" + String.valueOf((int) balance).substring(0, 3) + "K");
            userBalance.setBounds(647, 13, 700, 16);
        } else if (balance >= 1000000 && balance < 10000000) { // 1m to 10m
            userBalance.setText("$" + String.valueOf((int) balance).substring(0, 1) + "M");
            userBalance.setBounds(653, 13, 700, 16);
        } else if (balance >= 10000000 && balance < 100000000) { // 10m to 100m
            userBalance.setText("$" + String.valueOf((int) balance).substring(0, 2) + "M");
            userBalance.setBounds(649, 13, 700, 16);
        } else if (balance >= 100000000 && balance < 1000000000) { // 100m to 1b
            userBalance.setText("$" + String.valueOf((int) balance).substring(0, 3) + "M");
            userBalance.setBounds(643, 13, 700, 16);
        } else if (balance >= 1000000000) {
            userBalance.setText("$" + String.valueOf((int) balance).substring(0, 3) + "M");
            userBalance.setBounds(647, 13, 700, 16);
        } else {
            userBalance.setText("$" + String.valueOf((int) balance));

            if (balance >= 0 && balance < 10) {
                userBalance.setBounds(657, 13, 15, 16);
            } else if (balance >= 10 && balance < 100) {
                userBalance.setBounds(654, 13, 15, 16);
            } else if (balance >= 100 && balance < 1000) {
                userBalance.setBounds(650, 13, 700, 16);
            }
        }

        RoundedButton topUp = new RoundedButton("buy");
        topUp.setFont(geistmono9);
        topUp.setBackground(buttonBackground);
        topUp.setForeground(bg);
        topUp.setBounds(51, 13, 69, 16);

        // topUp button event
        topUp.addActionListener(e -> {
            frame.setVisible(false);
            frame.dispose();

            topUpScreen();
        });

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(bg);
        mainPanel.setBounds(226, 36, 248, 336);

        ImageIcon towersGlass = new ImageIcon(themePath + "towersGlassPanel.png");
        JLabel towersPanel = new JLabel(towersGlass);
        towersPanel.setBounds(0, 0, 248, 336);

        JLabel towers = new JLabel("Towers");
        towers.setFont(instrument48);
        towers.setForeground(buttonBackground);
        towers.setBounds(67, 17, 150, 62);

        // Hard code buttons
        RoundedButton RNGButton1 = new RoundedButton("");
        RNGButton1.setFont(geistmono10);
        RNGButton1.setBackground(buttonBackground);
        RNGButton1.setForeground(buttonBackground);
        RNGButton1.setBounds(38, 78, 81, 22);

        RoundedButton RNGButton2 = new RoundedButton("");
        RNGButton2.setFont(geistmono10);
        RNGButton2.setBackground(buttonBackground);
        RNGButton2.setForeground(buttonBackground);
        RNGButton2.setBounds(129, 78, 81, 22);

        RoundedButton RNGButton3 = new RoundedButton("");
        RNGButton3.setFont(geistmono10);
        RNGButton3.setBackground(buttonBackground);
        RNGButton3.setForeground(buttonBackground);
        RNGButton3.setEnabled(false);
        RNGButton3.setBounds(38, 118, 81, 22);

        RoundedButton RNGButton4 = new RoundedButton("");
        RNGButton4.setFont(geistmono10);
        RNGButton4.setBackground(buttonBackground);
        RNGButton4.setForeground(buttonBackground);
        RNGButton4.setEnabled(false);
        RNGButton4.setBounds(129, 118, 81, 22);

        RoundedButton RNGButton5 = new RoundedButton("");
        RNGButton5.setFont(geistmono10);
        RNGButton5.setBackground(buttonBackground);
        RNGButton5.setForeground(buttonBackground);
        RNGButton5.setEnabled(false);
        RNGButton5.setBounds(38, 158, 81, 22);

        RoundedButton RNGButton6 = new RoundedButton("");
        RNGButton6.setFont(geistmono10);
        RNGButton6.setBackground(buttonBackground);
        RNGButton6.setForeground(buttonBackground);
        RNGButton6.setEnabled(false);
        RNGButton6.setBounds(129, 158, 81, 22);

        RoundedButton RNGButton7 = new RoundedButton("");
        RNGButton7.setFont(geistmono10);
        RNGButton7.setBackground(buttonBackground);
        RNGButton7.setForeground(buttonBackground);
        RNGButton7.setEnabled(false);
        RNGButton7.setBounds(38, 198, 81, 22);

        RoundedButton RNGButton8 = new RoundedButton("");
        RNGButton8.setFont(geistmono10);
        RNGButton8.setBackground(buttonBackground);
        RNGButton8.setForeground(buttonBackground);
        RNGButton8.setEnabled(false);
        RNGButton8.setBounds(129, 198, 81, 22);

        RoundedButton RNGButton9 = new RoundedButton("");
        RNGButton9.setFont(geistmono10);
        RNGButton9.setBackground(buttonBackground);
        RNGButton9.setForeground(buttonBackground);
        RNGButton9.setEnabled(false);
        RNGButton9.setBounds(38, 238, 81, 22);

        RoundedButton RNGButton10 = new RoundedButton("");
        RNGButton10.setFont(geistmono10);
        RNGButton10.setBackground(buttonBackground);
        RNGButton10.setForeground(buttonBackground);
        RNGButton10.setEnabled(false);
        RNGButton10.setBounds(129, 238, 81, 22);

        RoundedButton startButton = new RoundedButton("start");
        startButton.setFont(geistmono6);
        startButton.setBackground(buttonBackground);
        startButton.setForeground(bg);
        startButton.setBounds(89, 277, 69, 16);

        RoundedButton restartButton = new RoundedButton("restart");
            restartButton.setFont(geistmono6);
            restartButton.setBackground(buttonBackground);
            restartButton.setForeground(bg);
            restartButton.setBounds(89, 277, 69, 16);

        JLabel message = new JLabel();
        message.setFont(geistmono9);
        message.setForeground(buttonBackground);

        // startButton event
        startButton.addActionListener(e -> {
            String[] choices = {"bomb", "safe"};
            int[][] rng = new int[5][2];

            for (int i = 0; i < 5; i++) {
                int[] window = {0, 0};
                window[0] = (int) (Math.random() * 2);
                System.out.println("first indx: " + String.valueOf(window[0]));
                    
                if (window[0] == 0) {
                    window[1] = 1;
                } else {
                    window[1] = 0;
                }

                System.out.println("second index: " + String.valueOf(window[1]));

                rng[i] = window;
            }

            restartButton.addActionListener(k -> {
                frame.setVisible(false);
                frame.dispose();
                towersScreen();
            });

            startButton.setVisible(false);
            restartButton.setVisible(false);

            // hard-coded button choices
            RNGButton1.setText(choices[rng[0][0]]);
            RNGButton2.setText(choices[rng[0][1]]);
            RNGButton3.setText(choices[rng[1][0]]);
            RNGButton4.setText(choices[rng[1][1]]);
            RNGButton5.setText(choices[rng[2][0]]);
            RNGButton6.setText(choices[rng[2][1]]);
            RNGButton7.setText(choices[rng[3][0]]);
            RNGButton8.setText(choices[rng[3][1]]);
            RNGButton9.setText(choices[rng[4][0]]);
            RNGButton10.setText(choices[rng[4][1]]);

            RNGButton1.addActionListener(k -> {
                if (RNGButton1.getText().equals("bomb")) {
                    message.setText("LOSS!");
                    message.setBounds(336, 368, 27, 12);

                    restartButton.setVisible(true);
                    RNGButton1.setForeground(Color.BLACK);
                    
                    RNGButton2.setEnabled(false);
                } else {
                    RNGButton1.setForeground(Color.BLACK);
                    RNGButton1.setEnabled(false);
                    RNGButton2.setEnabled(false);
                    RNGButton3.setEnabled(true);
                    RNGButton4.setEnabled(true);
                }
            });
            
            RNGButton2.addActionListener(k -> {
                if (RNGButton2.getText().equals("bomb")) {
                    message.setText("LOSS!");
                    message.setBounds(336, 368, 27, 12);

                    restartButton.setVisible(true);
                    RNGButton2.setForeground(Color.BLACK);

                    RNGButton1.setEnabled(false);
                } else {
                    RNGButton2.setForeground(Color.BLACK);
                    RNGButton2.setEnabled(false);
                    RNGButton1.setEnabled(false);
                    RNGButton3.setEnabled(true);
                    RNGButton4.setEnabled(true);
                }
            });

            RNGButton3.addActionListener(k -> {
                if (RNGButton3.getText().equals("bomb")) {
                    message.setText("LOSS!");
                    message.setBounds(336, 368, 27, 12);

                    restartButton.setVisible(true);
                    RNGButton3.setForeground(Color.BLACK);
                    
                    RNGButton4.setEnabled(false);;
                } else {
                    RNGButton3.setForeground(Color.BLACK);
                    RNGButton3.setEnabled(false);
                    RNGButton4.setEnabled(false);
                    RNGButton5.setEnabled(true);
                    RNGButton6.setEnabled(true);
                }
            });

            RNGButton4.addActionListener(k -> {
                if (RNGButton4.getText().equals("bomb")) {
                    message.setText("LOSS!");
                    message.setBounds(336, 368, 27, 12);

                    restartButton.setVisible(true);
                    RNGButton4.setForeground(Color.BLACK);

                    RNGButton3.setEnabled(false);
                } else {
                    RNGButton4.setForeground(Color.BLACK);
                    RNGButton4.setEnabled(false);
                    RNGButton3.setEnabled(false);
                    RNGButton5.setEnabled(true);
                    RNGButton6.setEnabled(true);
                }
            });

            RNGButton5.addActionListener(k -> {
                if (RNGButton5.getText().equals("bomb")) {
                    message.setText("LOSS!");
                    message.setBounds(336, 368, 27, 12);

                    restartButton.setVisible(true);
                    RNGButton5.setForeground(Color.BLACK);

                    RNGButton6.setEnabled(false);
                } else {
                    RNGButton5.setForeground(Color.BLACK);
                    RNGButton5.setEnabled(false);
                    RNGButton6.setEnabled(false);
                    RNGButton7.setEnabled(true);
                    RNGButton8.setEnabled(true);
                }
            });

            RNGButton6.addActionListener(k -> {
                if (RNGButton6.getText().equals("bomb")) {
                    message.setText("LOSS!");
                    message.setBounds(336, 368, 27, 12);

                    restartButton.setVisible(true);
                    RNGButton6.setForeground(Color.BLACK);

                    RNGButton5.setEnabled(false);
                } else {
                    RNGButton6.setForeground(Color.BLACK);
                    RNGButton6.setEnabled(false);
                    RNGButton5.setEnabled(false);
                    RNGButton7.setEnabled(true);
                    RNGButton8.setEnabled(true);
                }
            });

            RNGButton7.addActionListener(k -> {
                if (RNGButton7.getText().equals("bomb")) {
                    message.setText("LOSS!");
                    message.setBounds(336, 368, 27, 12);

                    restartButton.setVisible(true);
                    RNGButton7.setForeground(Color.BLACK);

                    RNGButton8.setEnabled(false);
                } else {
                    RNGButton7.setForeground(Color.BLACK);
                    RNGButton7.setEnabled(false);
                    RNGButton8.setEnabled(false);
                    RNGButton9.setEnabled(true);
                    RNGButton10.setEnabled(true);
                }
            });

            RNGButton8.addActionListener(k -> {
                if (RNGButton8.getText().equals("bomb")) {
                    message.setText("LOSS!");
                    message.setBounds(336, 368, 27, 12);

                    restartButton.setVisible(true);
                    RNGButton8.setForeground(Color.BLACK);

                    RNGButton7.setEnabled(false);
                } else {
                    RNGButton8.setForeground(Color.BLACK);
                    RNGButton8.setEnabled(false);
                    RNGButton7.setEnabled(false);
                    RNGButton9.setEnabled(true);
                    RNGButton10.setEnabled(true);
                }
            });

            RNGButton9.addActionListener(k -> {
                if (RNGButton9.getText().equals("bomb")) {
                    message.setText("LOSS!");
                    message.setBounds(336, 368, 27, 12);

                    restartButton.setVisible(true);
                    RNGButton9.setForeground(Color.BLACK);

                    RNGButton10.setEnabled(false);
                } else {
                    RNGButton9.setForeground(Color.BLACK);
                    balance *= 1.2;

                    message.setText("WON 1.2X!");
                    message.setBounds(325, 368, 49, 12);

                    restartButton.setVisible(true);
                }
            });

            RNGButton10.addActionListener(k -> {
                if (RNGButton10.getText().equals("bomb")) {
                    message.setText("LOSS!");
                    message.setBounds(336, 368, 27, 12);

                    restartButton.setVisible(true);
                    RNGButton10.setForeground(Color.BLACK);

                    RNGButton9.setEnabled(false);
                } else {
                    RNGButton10.setForeground(Color.BLACK);
                    balance *= 1.2;

                    message.setText("WON 1.2X!");
                    message.setBounds(325, 368, 49, 12);

                    restartButton.setVisible(true);
                }
            });

            contentpane.repaint();
            contentpane.revalidate();
        });

        contentpane.add(panel);
        panel.add(menu);
        panel.add(topUp);
        panel.add(glassPanel);

        contentpane.add(userBalance);
        contentpane.add(glassPanelBal);

        contentpane.add(mainPanel);
        mainPanel.add(towers);
        mainPanel.add(RNGButton1);
        mainPanel.add(RNGButton2);
        mainPanel.add(RNGButton3);
        mainPanel.add(RNGButton4);
        mainPanel.add(RNGButton5);
        mainPanel.add(RNGButton6);
        mainPanel.add(RNGButton7);
        mainPanel.add(RNGButton8);
        mainPanel.add(RNGButton9);
        mainPanel.add(RNGButton10);
        mainPanel.add(startButton);
        mainPanel.add(restartButton);
        mainPanel.add(towersPanel);

        frame.setVisible(true);
    }

    public static void crashScreen() {
        JFrame frame = new JFrame();
        Container contentpane = frame.getContentPane();

        frame.setTitle("oblivion");
        frame.setSize(700, 400);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        contentpane.setBackground(bg);
        contentpane.setLayout(null);

        ImageIcon glassPanelImg = new ImageIcon(themePath + "glassyPanel.png");
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

        ImageIcon glassyPanelBalance = new ImageIcon(themePath + "glassyPanelBalance.png");
        JLabel glassPanelBal = new JLabel(glassyPanelBalance);
        glassPanelBal.setBounds(629, 0, 71, 42);

        JLabel userBalance = new JLabel();
        userBalance.setFont(geistmono12);
        userBalance.setForeground(buttonBackground);

        // userBalance display
        if (balance >= 1000 && balance < 10000) { // 1k to 10k
            userBalance.setText("$" + String.valueOf((int) balance).substring(0, 1) + "K");
            userBalance.setBounds(654, 13, 700, 16);
        } else if (balance >= 10000 && balance < 100000) { // 10k to 100k
            userBalance.setText("$" + String.valueOf((int) balance).substring(0, 2) + "K");
            userBalance.setBounds(649, 13, 700, 16);
        } else if (balance >= 100000 && balance < 1000000) { // 100k to 1m
            userBalance.setText("$" + String.valueOf((int) balance).substring(0, 3) + "K");
            userBalance.setBounds(647, 13, 700, 16);
        } else if (balance >= 1000000 && balance < 10000000) { // 1m to 10m
            userBalance.setText("$" + String.valueOf((int) balance).substring(0, 1) + "M");
            userBalance.setBounds(653, 13, 700, 16);
        } else if (balance >= 10000000 && balance < 100000000) { // 10m to 100m
            userBalance.setText("$" + String.valueOf((int) balance).substring(0, 2) + "M");
            userBalance.setBounds(649, 13, 700, 16);
        } else if (balance >= 100000000 && balance < 1000000000) { // 100m to 1b
            userBalance.setText("$" + String.valueOf((int) balance).substring(0, 3) + "M");
            userBalance.setBounds(643, 13, 700, 16);
        } else if (balance >= 1000000000) {
            userBalance.setText("$" + String.valueOf((int) balance).substring(0, 3) + "M");
            userBalance.setBounds(647, 13, 700, 16);
        } else {
            userBalance.setText("$" + String.valueOf((int) balance));

            if (balance >= 0 && balance < 10) {
                userBalance.setBounds(657, 13, 15, 16);
            } else if (balance >= 10 && balance < 100) {
                userBalance.setBounds(654, 13, 15, 16);
            } else if (balance >= 100 && balance < 1000) {
                userBalance.setBounds(650, 13, 700, 16);
            }
        }

        RoundedButton topUp = new RoundedButton("buy");
        topUp.setFont(geistmono9);
        topUp.setBackground(buttonBackground);
        topUp.setForeground(bg);
        topUp.setBounds(51, 13, 69, 16);

        // topUp button event
        topUp.addActionListener(e -> {
            frame.setVisible(false);
            frame.dispose();

            topUpScreen();
        });

        // title label
        JLabel title = new JLabel("Crash");
        title.setFont(instrument48);
        title.setForeground(buttonBackground);
        title.setBounds(303, 100, 115, 62);

        // multiplier label
        JLabel multiplier = new JLabel("1.00%");
        multiplier.setFont(geistmono96);
        multiplier.setForeground(buttonBackground);
        multiplier.setBounds(206, 145, 350, 125);

        /// message label
        JLabel message = new JLabel();
        message.setFont(geistmono9);
        message.setForeground(buttonBackground);

        // bet button
        RoundedButton bet = new RoundedButton("bet");
        bet.setFont(geistmono6);
        bet.setBackground(buttonBackground);
        bet.setForeground(bg);
        bet.setBounds(315, 275, 69, 16);

        bet.addActionListener(e -> {
            bet.setVisible(false);

            int rng = (int) (Math.random()*1001);
            System.out.println(rng);
            message.setText(null);

            RoundedButton cashout = new RoundedButton("cash out");
            cashout.setFont(geistmono6);
            cashout.setBackground(buttonBackground);
            cashout.setForeground(bg);
            cashout.setBounds(315, 275, 69, 16);

            cashout.addActionListener(k -> {
                loop.stop(); // stop loop

                System.out.println(num);
                balance = 200 * Double.parseDouble( String.valueOf(num).substring(0, 1) + "." + String.valueOf(num).substring(1, 3));

                message.setText("CASHED OUT AT " + String.valueOf(num).substring(0, 1) + "." + String.valueOf(num).substring(1, 3) + "!");
                message.setBounds(309, 325, 125, 12);

                num = 100; // reset to default

                // userDisplay (double check after betting)
                if (balance >= 1000 && balance < 10000) { // 1k to 10k
                    userBalance.setText("$" + String.valueOf((int) balance).substring(0, 1) + "K");
                    userBalance.setBounds(654, 13, 700, 16);
                } else if (balance >= 10000 && balance < 100000) { // 10k to 100k
                    userBalance.setText("$" + String.valueOf((int) balance).substring(0, 2) + "K");
                    userBalance.setBounds(649, 13, 700, 16);
                } else if (balance >= 100000 && balance < 1000000) { // 100k to 1m
                    userBalance.setText("$" + String.valueOf((int) balance).substring(0, 3) + "K");
                    userBalance.setBounds(647, 13, 700, 16);
                } else if (balance >= 1000000 && balance < 10000000) { // 1m to 10m
                    userBalance.setText("$" + String.valueOf((int) balance).substring(0, 1) + "M");
                    userBalance.setBounds(653, 13, 700, 16);
                } else if (balance >= 10000000 && balance < 100000000) { // 10m to 100m
                    userBalance.setText("$" + String.valueOf((int) balance).substring(0, 2) + "M");
                    userBalance.setBounds(649, 13, 700, 16);
                } else if (balance >= 100000000 && balance < 1000000000) { // 100m to 1b
                    userBalance.setText("$" + String.valueOf((int) balance).substring(0, 3) + "M");
                    userBalance.setBounds(643, 13, 700, 16);
                } else if (balance >= 1000000000) {
                    userBalance.setText("$" + String.valueOf((int) balance).substring(0, 3) + "M");
                    userBalance.setBounds(647, 13, 700, 16);
                } else {
                    userBalance.setText("$" + String.valueOf((int) balance));

                    if (balance >= 0 && balance < 10) {
                        userBalance.setBounds(657, 13, 15, 16);
                    } else if (balance >= 10 && balance < 100) {
                        userBalance.setBounds(654, 13, 15, 16);
                    } else if (balance >= 100 && balance < 1000) {
                        userBalance.setBounds(650, 13, 700, 16);
                    }
                }

                bet.setVisible(true);
                cashout.setVisible(false);
            });

            contentpane.add(cashout);

            contentpane.repaint();
            contentpane.revalidate();

            loop = new Timer(250, k -> {
                if (!(num == rng)) {
                    num++;
                    String numString = String.valueOf(num);
                    
                    multiplier.setText(numString.substring(0, 1) + "." + numString.substring(1, 3) + "%");

                    contentpane.repaint();
                    contentpane.revalidate();
                } else if (num < 100) {
                    num = 100;
                }
                else {
                    loop.stop();

                    balance /= 1.2;

                    message.setText("CRASHED AT " + String.valueOf(num).substring(0, 1) + "." + String.valueOf(num).substring(1, 3) + "!");
                    message.setBounds(303, 325, 87, 12);
                }
            });
            loop.start();
        });

        JLabel information = new JLabel("cashing out grants bet x multiplier");
        information.setFont(geistmono6);
        information.setForeground(lightBlack);
        information.setBounds(282, 341, 150, 8);

        contentpane.add(panel);
        panel.add(menu);
        panel.add(topUp);
        panel.add(glassPanel);

        contentpane.add(userBalance);
        contentpane.add(glassPanelBal);
        contentpane.add(title);
        contentpane.add(multiplier);
        contentpane.add(bet);
        contentpane.add(information);
        contentpane.add(message);

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

        ImageIcon glassPanelImg = new ImageIcon(themePath + "glassyPanel.png");
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

        ImageIcon glassyPanelBalance = new ImageIcon(themePath + "glassyPanelBalance.png");
        JLabel glassPanelBal = new JLabel(glassyPanelBalance);
        glassPanelBal.setBounds(629, 0, 71, 42);

        JLabel userBalance = new JLabel();
        userBalance.setFont(geistmono12);
        userBalance.setForeground(buttonBackground);

        // userBalance display
        if (balance >= 1000 && balance < 10000) { // 1k to 10k
            userBalance.setText("$" + String.valueOf((int) balance).substring(0, 1) + "K");
            userBalance.setBounds(654, 13, 700, 16);
        } else if (balance >= 10000 && balance < 100000) { // 10k to 100k
            userBalance.setText("$" + String.valueOf((int) balance).substring(0, 2) + "K");
            userBalance.setBounds(649, 13, 700, 16);
        } else if (balance >= 100000 && balance < 1000000) { // 100k to 1m
            userBalance.setText("$" + String.valueOf((int) balance).substring(0, 3) + "K");
            userBalance.setBounds(647, 13, 700, 16);
        } else if (balance >= 1000000 && balance < 10000000) { // 1m to 10m
            userBalance.setText("$" + String.valueOf((int) balance).substring(0, 1) + "M");
            userBalance.setBounds(653, 13, 700, 16);
        } else if (balance >= 10000000 && balance < 100000000) { // 10m to 100m
            userBalance.setText("$" + String.valueOf((int) balance).substring(0, 2) + "M");
            userBalance.setBounds(649, 13, 700, 16);
        } else if (balance >= 100000000 && balance < 1000000000) { // 100m to 1b
            userBalance.setText("$" + String.valueOf((int) balance).substring(0, 3) + "M");
            userBalance.setBounds(643, 13, 700, 16);
        } else if (balance >= 1000000000) {
            userBalance.setText("$" + String.valueOf((int) balance).substring(0, 3) + "M");
            userBalance.setBounds(647, 13, 700, 16);
        } else {
            userBalance.setText("$" + String.valueOf((int) balance));

            if (balance >= 0 && balance < 10) {
                userBalance.setBounds(657, 13, 15, 16);
            } else if (balance >= 10 && balance < 100) {
                userBalance.setBounds(654, 13, 15, 16);
            } else if (balance >= 100 && balance < 1000) {
                userBalance.setBounds(650, 13, 700, 16);
            }
        }

        RoundedButton topUp = new RoundedButton("buy");
        topUp.setFont(geistmono9);
        topUp.setBackground(buttonBackground);
        topUp.setForeground(bg);
        topUp.setBounds(51, 13, 69, 16);

        // topUp button event
        topUp.addActionListener(e -> {
            frame.setVisible(false);
            frame.dispose();

            topUpScreen();
        });

        JLabel dealerInfo = new JLabel("DEALER MUST STAND ON ALL 17'S");
        dealerInfo.setFont(geistmono9);
        dealerInfo.setForeground(buttonBackground);
        dealerInfo.setBounds(271, 48, 157, 12);

        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(null);
        tablePanel.setBackground(null);
        tablePanel.setBounds(83, 50, 533, 290);

        ImageIcon blackjackTableImg = new ImageIcon(themePath + "blackjackTable.png");
        JLabel blackjackTable = new JLabel(blackjackTableImg);
        blackjackTable.setBackground(null);
        blackjackTable.setBounds(0, 0, 533, 290);

        JLabel dealerNum = new JLabel("");
        dealerNum.setFont(geistmono9);
        dealerNum.setForeground(buttonBackground);
        dealerNum.setBounds(261, 61, 533, 12);

        JLabel userCards = new JLabel("");
        userCards.setFont(geistmono9);
        userCards.setForeground(buttonBackground);
        userCards.setBounds(261, 190, 60, 12);

        RoundedButton betPlacer = new RoundedButton("bet");
        betPlacer.setFont(geistmono9);
        betPlacer.setBackground(buttonBackground);
        betPlacer.setForeground(bg);
        betPlacer.setBounds(319, 334, 61, 16);

        JLabel message = new JLabel();
        message.setFont(geistmono9);
        message.setForeground(buttonBackground);

        // i dont know how i made it work
        betPlacer.addActionListener(e -> {
            message.setText(null);
            betPlacer.setVisible(false);

            dealerRNG = (int)(Math.random() * 11);
            userRNG = (int)(Math.random() * 11);

            dealerNum.setText(String.valueOf(dealerRNG));
            userCards.setText(String.valueOf(userRNG));

            ImageIcon cardImg = new ImageIcon(themePath + "cardFrame.png");
            JLabel dealerCard = new JLabel(cardImg);
            dealerCard.setBackground(null);
            dealerCard.setBounds(245, 34, 44, 67);

            JLabel userCard = new JLabel(cardImg);
            userCard.setBackground(null);
            userCard.setBounds(245, 162, 44, 67);

            RoundedButton hitButton = new RoundedButton("hit");
            hitButton.setFont(geistmono9);
            hitButton.setBackground(buttonBackground);
            hitButton.setForeground(bg);
            hitButton.setBounds(193, 240, 61, 16);

            RoundedButton standButton = new RoundedButton("stand");
            standButton.setFont(geistmono9);
            standButton.setBackground(buttonBackground);
            standButton.setForeground(bg);
            standButton.setBounds(271, 240, 69, 16);

            hitButton.addActionListener(k -> {
                userRNG += (int) (Math.random() * 11);

                dealerNum.setText(String.valueOf(dealerRNG));
                userCards.setText(String.valueOf(userRNG));
                
                if (userRNG > 20) {
                    hitButton.setEnabled(false);
                    standButton.setEnabled(false);

                    if (userRNG == 21) {
                        balance *= 2;
                        System.out.println("Debug: user won the game");

                        message.setText("WON 1.2X!");
                        message.setBounds(325, 181, 49, 12);
                    } else if (userRNG > 21) {
                        balance -= 200;

                        message.setText("LOSS!");
                        message.setBounds(336, 181, 27, 12);
                    }

                    betPlacer.setVisible(true);
                }
    
                System.out.println("Debug: User Cards: "+ userRNG + "\nDealer Cards: " + dealerRNG);
                contentpane.repaint();
                contentpane.revalidate();
            });

            standButton.addActionListener(k -> {
                loop = new Timer(1000, j -> {
                    dealerRNG += (int) (Math.random() * 10) + 1;
                    dealerNum.setText(String.valueOf(dealerRNG));

                    if (dealerRNG >= 17) {
                        hitButton.setEnabled(false);
                        standButton.setEnabled(false);

                        betPlacer.setVisible(true);

                        loop.stop();

                        if (dealerRNG > 21) {
                            hasWon = true;
                            balance *= 1.2;

                            message.setText("WON 1.2X!");
                            message.setBounds(325, 181, 49, 12);
                        } else if (dealerRNG == 21) {
                            balance -= 2000;

                            message.setText("LOSS!");
                            message.setBounds(336, 181, 27, 12);
                        } else if (dealerRNG < userRNG) {
                            hasWon = true;
                            balance *= 1.2;

                            message.setText("WON 1.2X!");
                            message.setBounds(325, 181, 49, 12);
                        } else if (dealerRNG > userRNG) {
                            balance -= 200;

                            message.setText("LOSS!");
                            message.setBounds(336, 181, 27, 12);
                        }

                        System.out.println("Debug: User cards: " + userRNG + "\nDealer Cards: " + dealerRNG);

                    }
                });
                loop.start();

            });

            tablePanel.add(hitButton, 0);
            tablePanel.add(standButton, 0);

            tablePanel.add(dealerNum, 1);
            tablePanel.add(userCards, 2);

            tablePanel.add(dealerCard, 3);
            tablePanel.add(userCard, 4);

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
        contentpane.add(glassPanelBal);
        contentpane.add(dealerInfo);
        contentpane.add(message);

        contentpane.add(betPlacer);

        contentpane.add(tablePanel);
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

        ImageIcon glassPanelImg = new ImageIcon(themePath + "glassyPanel.png");
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
        topUp.setBackground(buttonBackground);
        topUp.setForeground(bg);
        topUp.setBounds(51, 13, 69, 16);

        JLabel confirmLabel = new JLabel("Confirm");
        confirmLabel.setFont(instrument48);
        confirmLabel.setForeground(buttonBackground);
        confirmLabel.setBounds(200, 121, 145, 47);

        ImageIcon confirmGlassPanel = new ImageIcon(themePath + "confirmationPanel.png");
        JLabel confirmPanel = new JLabel(confirmGlassPanel);
        confirmPanel.setBounds(190, 163, 319, 122);

        JLabel question1 = new JLabel("Are you sure you want to");
        question1.setFont(geistmono12);
        question1.setForeground(buttonBackground);
        question1.setBounds(263, 189, 173, 16);

        JLabel question2 = new JLabel("purchase " + pending + "?");
        question2.setFont(geistmono12);
        question2.setForeground(buttonBackground);
        if (pending == 100) {
            question2.setBounds(306, 205, 300, 16);
        } else if (pending == 1000) {
            question2.setBounds(299, 205, 300, 16);
        } else if (pending == 10000) {
            question2.setBounds(296, 205, 300, 16);
        } else if (pending == 100000) {
            question2.setBounds(292, 205, 300, 16);
        } else if (pending == 1000000) {
            question2.setBounds(288, 205, 300, 16);
        }

        RoundedButton confirm = new RoundedButton("confirm");
        confirm.setFont(geistmono6);
        confirm.setBackground(buttonBackground);
        confirm.setForeground(bg);
        confirm.setBounds(264, 237, 77, 21);

        // confirm event
        confirm.addActionListener(e -> {
            balance += pending;
            frame.setVisible(false);
            frame.dispose();

            diceScreen();
        });;

        GreyButton cancel = new GreyButton("cancel");
        cancel.setFont(geistmono6);
        cancel.setBounds(359, 237, 77, 21);

        // cancel event
        cancel.addActionListener(e -> {
            frame.setVisible(false);
            frame.dispose();

            topUpScreen();
        });

        contentpane.add(question1);
        contentpane.add(question2);
        contentpane.add(cancel);
        contentpane.add(confirm);
        contentpane.add(confirmPanel);

        contentpane.add(gPanel);
        gPanel.add(menu);
        gPanel.add(topUp);
        gPanel.add(glassPanel);

        contentpane.add(confirmLabel);

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

        ImageIcon glassPanelImg = new ImageIcon(themePath + "glassyPanel.png");
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

        ImageIcon glassyPanelBalance = new ImageIcon(themePath + "glassyPanelBalance.png");
        JLabel glassPanelBal = new JLabel(glassyPanelBalance);
        glassPanelBal.setBounds(629, 0, 71, 42);

        JLabel userBalance = new JLabel();
        userBalance.setFont(geistmono12);
        userBalance.setForeground(buttonBackground);

        // userBalance display
        if (balance >= 1000 && balance < 10000) { // 1k to 10k
            userBalance.setText("$" + String.valueOf((int) balance).substring(0, 1) + "K");
            userBalance.setBounds(654, 13, 700, 16);
        } else if (balance >= 10000 && balance < 100000) { // 10k to 100k
            userBalance.setText("$" + String.valueOf((int) balance).substring(0, 2) + "K");
            userBalance.setBounds(649, 13, 700, 16);
        } else if (balance >= 100000 && balance < 1000000) { // 100k to 1m
            userBalance.setText("$" + String.valueOf((int) balance).substring(0, 3) + "K");
            userBalance.setBounds(647, 13, 700, 16);
        } else if (balance >= 1000000 && balance < 10000000) { // 1m to 10m
            userBalance.setText("$" + String.valueOf((int) balance).substring(0, 1) + "M");
            userBalance.setBounds(653, 13, 700, 16);
        } else if (balance >= 10000000 && balance < 100000000) { // 10m to 100m
            userBalance.setText("$" + String.valueOf((int) balance).substring(0, 2) + "M");
            userBalance.setBounds(649, 13, 700, 16);
        } else if (balance >= 100000000 && balance < 1000000000) { // 100m to 1b
            userBalance.setText("$" + String.valueOf((int) balance).substring(0, 3) + "M");
            userBalance.setBounds(643, 13, 700, 16);
        } else if (balance >= 1000000000) {
            userBalance.setText("$" + String.valueOf((int) balance).substring(0, 3) + "M");
            userBalance.setBounds(647, 13, 700, 16);
        } else {
            userBalance.setText("$" + String.valueOf((int) balance));

            if (balance >= 0 && balance < 10) {
                userBalance.setBounds(657, 13, 15, 16);
            } else if (balance >= 10 && balance < 100) {
                userBalance.setBounds(654, 13, 15, 16);
            } else if (balance >= 100 && balance < 1000) {
                userBalance.setBounds(650, 13, 700, 16);
            }
        }

        RoundedButton topUp = new RoundedButton("buy");
        topUp.setFont(geistmono9);
        topUp.setBackground(buttonBackground);
        topUp.setForeground(bg);
        topUp.setBounds(51, 13, 69, 16);

        JLabel paymentsLabel = new JLabel("Payments");
        paymentsLabel.setFont(instrument48);
        paymentsLabel.setForeground(buttonBackground);
        paymentsLabel.setBounds(200, 63, 175, 47);

        RoundedPanel panel = new RoundedPanel(20);
        panel.setBackgroundColor(panelBackground);
        panel.setLayout(null);
        panel.setBounds(200, 116, 300, 221);

        JLabel hundred = new JLabel("$100");
        hundred.setFont(geistmono20);
        hundred.setForeground(buttonBackground);
        hundred.setBounds(15, 9, 175, 47);

        JLabel thousand = new JLabel("$1000");
        thousand.setFont(geistmono20);
        thousand.setForeground(buttonBackground);
        thousand.setBounds(15, 49, 200, 47);

        JLabel tenthousand = new JLabel("$10,000");
        tenthousand.setFont(geistmono20);
        tenthousand.setForeground(buttonBackground);
        tenthousand.setBounds(15, 98, 210, 26);

        JLabel hundredthousand = new JLabel("$100,000");
        hundredthousand.setFont(geistmono20);
        hundredthousand.setForeground(buttonBackground);
        hundredthousand.setBounds(15, 137, 225, 26);

        JLabel million = new JLabel("$1,000,000");
        million.setFont(geistmono20);
        million.setForeground(buttonBackground);
        million.setBounds(15, 176, 250, 26);

        RoundedButton purchasehundred = new RoundedButton("purchase");
        purchasehundred.setFont(geistmono10);
        purchasehundred.setBackground(buttonBackground);
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
        purchasethousand.setBackground(buttonBackground);
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
        purchasetenthousand.setBackground(buttonBackground);
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
        purchasehundredthousand.setBackground(buttonBackground);
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
        purchasemillion.setBackground(buttonBackground);
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

        ImageIcon glassPanelImg = new ImageIcon(themePath + "glassyPanel.png");
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

        ImageIcon glassyPanelBalance = new ImageIcon(themePath + "glassyPanelBalance.png");
        JLabel glassPanelBal = new JLabel(glassyPanelBalance);
        glassPanelBal.setBounds(629, 0, 71, 42);

        JLabel userBalance = new JLabel();
        userBalance.setFont(geistmono12);
        userBalance.setForeground(buttonBackground);

        // userBalance display
        if (balance >= 1000 && balance < 10000) { // 1k to 10k
            userBalance.setText("$" + String.valueOf((int) balance).substring(0, 1) + "K");
            userBalance.setBounds(654, 13, 700, 16);
        } else if (balance >= 10000 && balance < 100000) { // 10k to 100k
            userBalance.setText("$" + String.valueOf((int) balance).substring(0, 2) + "K");
            userBalance.setBounds(649, 13, 700, 16);
        } else if (balance >= 100000 && balance < 1000000) { // 100k to 1m
            userBalance.setText("$" + String.valueOf((int) balance).substring(0, 3) + "K");
            userBalance.setBounds(647, 13, 700, 16);
        } else if (balance >= 1000000 && balance < 10000000) { // 1m to 10m
            userBalance.setText("$" + String.valueOf((int) balance).substring(0, 1) + "M");
            userBalance.setBounds(653, 13, 700, 16);
        } else if (balance >= 10000000 && balance < 100000000) { // 10m to 100m
            userBalance.setText("$" + String.valueOf((int) balance).substring(0, 2) + "M");
            userBalance.setBounds(649, 13, 700, 16);
        } else if (balance >= 100000000 && balance < 1000000000) { // 100m to 1b
            userBalance.setText("$" + String.valueOf((int) balance).substring(0, 3) + "M");
            userBalance.setBounds(643, 13, 700, 16);
        } else if (balance >= 1000000000) {
            userBalance.setText("$" + String.valueOf((int) balance).substring(0, 3) + "M");
            userBalance.setBounds(647, 13, 700, 16);
        } else {
            userBalance.setText("$" + String.valueOf((int) balance));

            if (balance >= 0 && balance < 10) {
                userBalance.setBounds(657, 13, 15, 16);
            } else if (balance >= 10 && balance < 100) {
                userBalance.setBounds(654, 13, 15, 16);
            } else if (balance >= 100 && balance < 1000) {
                userBalance.setBounds(650, 13, 700, 16);
            }
        }

        RoundedButton topUp = new RoundedButton("buy");
        topUp.setFont(geistmono9);
        topUp.setBackground(buttonBackground);
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
        dice.setForeground(buttonBackground);
        dice.setBounds(316, 143, 70, 62);

        JLabel zero = new JLabel("0");
        zero.setFont(geistmono12);
        zero.setForeground(lightBlack);
        zero.setBounds(92, 197, 8, 16);

        JLabel hundred = new JLabel("100");
        hundred.setFont(geistmono12);
        hundred.setForeground(lightBlack);
        hundred.setBounds(587, 197, 22, 16);

        ImageIcon backgroundImg = new ImageIcon(themePath + "sliderBackground.png");
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
        over.setBackground(buttonBackground);
        over.setForeground(bg);
        over.setBounds(92, 240, 69, 16);

        over.addActionListener(e -> {
            switchs = 0;
        });

        RoundedButton under = new RoundedButton("under");
        under.setFont(geistmono9);
        under.setBackground(buttonBackground);
        under.setForeground(bg);
        under.setBounds(170, 240, 69, 16);

        under.addActionListener(e -> {
            switchs = 1;
        });

        RoundedButton betPlacer = new RoundedButton("bet");
        betPlacer.setFont(geistmono9);
        betPlacer.setBackground(buttonBackground);
        betPlacer.setForeground(bg);
        betPlacer.setBounds(548, 240, 61, 16);

        JLabel message = new JLabel();
        message.setFont(geistmono9);
        message.setForeground(buttonBackground);

        // betPlacer button event (needs functionality)
        betPlacer.addActionListener(e -> {
            // if balance is more than 1
            if (balance >= 1) {
                int rngDice = (int)(Math.random() * 101);
                int rngUser = slider.getValue();

                if (switchs == 0) {
                    if (rngDice < rngUser) {
                        balance *= 1.2;

                        message.setText("WON 1.2X!");
                        message.setBounds(323, 305, 49, 12);
                    } 
                    else {
                        balance -= 20;

                        message.setText("LOSS!");
                        message.setBounds(334, 305, 27, 12);
                    }
                } else if (switchs == 1) {
                    if (rngDice > rngUser) {
                        balance *= 1.2;

                        message.setText("WON 1.2X!");
                        message.setBounds(323, 305, 49, 12);
                    } 
                    else {
                        balance /= 1.2;

                        message.setText("LOSS!");
                        message.setBounds(334, 305, 27, 12);
                    }
                } 
                else {
                    System.out.println("Debugging: 2");
                }

                // userDisplay (double check after betting)
                if (balance >= 1000 && balance < 10000) { // 1k to 10k
                    userBalance.setText("$" + String.valueOf((int) balance).substring(0, 1) + "K");
                    userBalance.setBounds(654, 13, 700, 16);
                } else if (balance >= 10000 && balance < 100000) { // 10k to 100k
                    userBalance.setText("$" + String.valueOf((int) balance).substring(0, 2) + "K");
                    userBalance.setBounds(649, 13, 700, 16);
                } else if (balance >= 100000 && balance < 1000000) { // 100k to 1m
                    userBalance.setText("$" + String.valueOf((int) balance).substring(0, 3) + "K");
                    userBalance.setBounds(647, 13, 700, 16);
                } else if (balance >= 1000000 && balance < 10000000) { // 1m to 10m
                    userBalance.setText("$" + String.valueOf((int) balance).substring(0, 1) + "M");
                    userBalance.setBounds(653, 13, 700, 16);
                } else if (balance >= 10000000 && balance < 100000000) { // 10m to 100m
                    userBalance.setText("$" + String.valueOf((int) balance).substring(0, 2) + "M");
                    userBalance.setBounds(649, 13, 700, 16);
                } else if (balance >= 100000000 && balance < 1000000000) { // 100m to 1b
                    userBalance.setText("$" + String.valueOf((int) balance).substring(0, 3) + "M");
                    userBalance.setBounds(643, 13, 700, 16);
                } else if (balance >= 1000000000) {
                    userBalance.setText("$" + String.valueOf((int) balance).substring(0, 3) + "M");
                    userBalance.setBounds(647, 13, 700, 16);
                } else {
                    userBalance.setText("$" + String.valueOf((int) balance));

                    if (balance >= 0 && balance < 10) {
                        userBalance.setBounds(657, 13, 15, 16);
                    } else if (balance >= 10 && balance < 100) {
                        userBalance.setBounds(654, 13, 15, 16);
                    } else if (balance >= 100 && balance < 1000) {
                        userBalance.setBounds(650, 13, 700, 16);
                    }
                }
            } // else do nothing
            contentpane.repaint();
            contentpane.revalidate();
        });

        JLabel informative = new JLabel("1.2x multiplier upon win");
        informative.setFont(geistmono6);
        informative.setForeground(lightBlack);
        informative.setBounds(300, 345, 105, 8);

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
        contentpane.add(message);

        frame.setVisible(true);
    }
}