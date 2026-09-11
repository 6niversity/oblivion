import javax.swing.*;
import javax.swing.plaf.basic.BasicTextFieldUI;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.geom.RoundRectangle2D;

// Claude Rounded TextField
public class RoundedTextField extends JTextField {
    private int arc = 15;
    private Color borderColor = new Color(140, 140, 140);
    private int borderThickness = 1;
    private boolean paintBorder = true;

    public RoundedTextField() {
        this("", 0);
    }

    public RoundedTextField(int columns) {
        this("", columns);
    }

    public RoundedTextField(String text) {
        this(text, 0);
    }

    public RoundedTextField(Color borderColor, int borderThickness) {
        this("", 0);
        this.borderColor = borderColor;
        this.borderThickness = borderThickness;
    }

    public RoundedTextField(String text, int columns) {
        super(text, columns);

        setUI(new BasicTextFieldUI()); // <-- bypass Nimbus's own painting

        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        setForeground(new Color(21, 21, 21));
        setBackground(Color.WHITE);
        setCaretColor(new Color(21, 21, 21));
        setFont(new Font("res/font/geistmono.ttf", Font.PLAIN, 7));

        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);

        g2.dispose();

        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        if (!paintBorder) return;

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(borderColor);
        g2.setStroke(new BasicStroke(borderThickness));

        float inset = borderThickness / 2f;
        g2.draw(new RoundRectangle2D.Float(
            inset, inset,
            getWidth() - borderThickness, getHeight() - borderThickness,
            arc, arc
        ));

        g2.dispose();
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setClip(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), arc, arc));
        super.paint(g2);
        g2.dispose();
    }

    public void setArc(int arc) {
        this.arc = arc;
        repaint();
    }

    public void setBorderColor(Color color) {
        this.borderColor = color;
        repaint();
    }

    public void setBorderThickness(int thickness) {
        this.borderThickness = thickness;
        repaint();
    }

    public void setPaintBorder(boolean paint) {
        this.paintBorder = paint;
        repaint();
    }
}