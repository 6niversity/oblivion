import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundedPanel extends JPanel {
    private int cornerRadius;
    private Color backgroundColor = Color.WHITE;
    private Color borderColor = null; // null = no border
    private int borderThickness = 1;

    public RoundedPanel(int cornerRadius) {
        this.cornerRadius = cornerRadius;
        setOpaque(false);
    }

    public void setBackgroundColor(Color color) {
        this.backgroundColor = color;
        repaint();
    }

    public void setRoundedBorder(Color color, int thickness) {
        this.borderColor = color;
        this.borderThickness = thickness;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();
        int arc = cornerRadius;

        // Fill background
        g2.setColor(backgroundColor);
        g2.fill(new RoundRectangle2D.Double(0, 0, w - 1, h - 1, arc, arc));

        // Draw border, inset by half the stroke width so it isn't clipped
        if (borderColor != null) {
            g2.setColor(borderColor);
            g2.setStroke(new BasicStroke(borderThickness));
            float inset = borderThickness / 2f;
            g2.draw(new RoundRectangle2D.Double(
                inset, inset,
                w - 1 - borderThickness,
                h - 1 - borderThickness,
                arc, arc));
        }

        g2.dispose();
        super.paintComponent(g);
    }
}