import javax.swing.*;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;

// Claude sliderUI
public class sliderUI extends BasicSliderUI {

    private String theme;
    private Color fillColor;
    private Color trackColor;
    private Color thumbColor;
    private int thumbDiameter = 16;
    private int trackHeight = 10;

    public sliderUI(JSlider slider, String theme) {
        super(slider);
        this.theme = theme;
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        if (theme.equals("DARK")) {
            fillColor = new Color(46, 46, 46);
            trackColor = Color.WHITE;
            thumbColor = new Color(140, 140, 140);
        } else {
            fillColor = new Color(208, 208, 208);
            trackColor = new Color(26, 26, 26);
            thumbColor = new Color(176, 176, 176);
        }

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int trackY = trackRect.y + (trackRect.height - trackHeight) / 2;

        // full white track, using trackRect (already accounts for thumb insets)
        g2.setColor(trackColor);
        g2.fillRoundRect(trackRect.x, trackY, trackRect.width, trackHeight, trackHeight, trackHeight);

        // position matches Swing's own drag math exactly -- this is the key fix
        int thumbCenterX = xPositionForValue(slider.getValue());

        // dark fill from the left up to the thumb
        int fillWidth = thumbCenterX - trackRect.x;
        g2.setColor(fillColor);
        g2.fillRoundRect(trackRect.x, trackY, fillWidth, trackHeight, trackHeight, trackHeight);

        // thumb circle, vertically centered on the track
        int thumbY = trackY + trackHeight / 2 - thumbDiameter / 2;
        g2.setColor(thumbColor);
        g2.fillOval(thumbCenterX - thumbDiameter / 2, thumbY, thumbDiameter, thumbDiameter);

        g2.dispose();
    }

    @Override
    public void paintThumb(Graphics g) {
        // empty -- handled above
    }

    @Override
    protected Dimension getThumbSize() {
        return new Dimension(thumbDiameter, thumbDiameter);
    }

    @Override
    public Dimension getPreferredSize(JComponent c) {
        return new Dimension(200, 24);
    }
}