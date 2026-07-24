import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

// Claude Grey Button
public class GreyButton extends JButton {

    private Color fillColor = new Color(21, 21, 21);
    private Color borderColor = new Color(52, 52, 52);
    private int arc = 20;

    public GreyButton(String text) {
        super(text);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(false);
        setForeground(Color.WHITE);
        setBackground(fillColor);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // slightly lighter fill on hover/press, for feedback
        if (getModel().isPressed()) {
            g2.setColor(fillColor.brighter());
        } else if (getModel().isRollover()) {
            g2.setColor(fillColor.brighter());
        } else {
            g2.setColor(fillColor);
        }

        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
        g2.dispose();

        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(borderColor);
        g2.setStroke(new BasicStroke(1.5f)); // thin outline like the screenshot
        g2.draw(new RoundRectangle2D.Float(0.5f, 0.5f, getWidth() - 1.5f, getHeight() - 1.5f, arc, arc));

        g2.dispose();
    }
}