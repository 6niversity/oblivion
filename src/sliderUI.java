import javax.swing.*;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;

public class sliderUI extends BasicSliderUI {

    private Color fillColor = new Color(30, 30, 30);
    private Color trackColor = Color.WHITE;
    private Color thumbColor = new Color(140, 140, 140);
    private int thumbDiameter = 16;
    private int trackHeight = 6;

    public sliderUI(JSlider slider) {
        super(slider);
    }

    @Override
    public void paint(Graphics g, JComponent c) {
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
    public Dimension getPreferredSize() {
        return new Dimension(200, 24);
    }
}