package View;

import javax.swing.*;
import java.awt.*;

public class RoundButtonView extends JButton {
    private int cornerRadius;
    boolean colorBrighter;

    public RoundButtonView(int cornerRadius, String text) {
        super(text);
        this.cornerRadius = cornerRadius;
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(false);
    }
    public RoundButtonView(int cornerRadius,String text, boolean colorBrighter) {
        super(text);
        this.cornerRadius = cornerRadius;
        this.colorBrighter = colorBrighter;
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(false);
    }
    public RoundButtonView(int cornerRadius, boolean colorBrighter) {
        this.cornerRadius = cornerRadius;
        this.colorBrighter = colorBrighter;
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (colorBrighter) {
            if (getModel().isPressed()) {
                g2.setColor(getBackground().darker());
            } else if (getModel().isRollover()) {
                g2.setColor(getBackground().brighter());
            } else {
                g2.setColor(getBackground());
            }
        } else
            g2.setColor(getBackground());

        if (cornerRadius > 0) {
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
        }
        g2.dispose();
        super.paintComponent(g);
    }

    @Override
    public Insets getInsets() {
        return new Insets(0, 0, 0, 0);
    }
}
