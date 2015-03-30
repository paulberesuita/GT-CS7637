package project4;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by paulberesuita on 3/9/15.
 */
public class ImageVisible extends JPanel {

    private BufferedImage img;

    public ImageVisible(BufferedImage img) {

        this.img = img;
        Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);
        setLayout(null); }

    public void paintComponent(Graphics g) {

        g.drawImage(img, 0, 0, null);
    }
}
