import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class Tile extends JButton {
    private Color fireOpal = new Color(231, 88, 75);
    private Color oldMauve = new Color(103, 49, 71);
    private Color isabelline = new Color(244, 240, 236);
    private Color dimGrey = new Color(107, 97, 102);
    private Color tiffanyBlue = new Color(10, 186, 181);
    private Color carnelian = new Color(179, 27, 27);

    private String tileColor;

    /**
     * Constructor for a tile
     */
    public Tile() {
        // super();
        this.tileColor = "black";
    }

    public String getColor() {
        return tileColor;
    }

    public Color actualColor() {
        if (tileColor.equals("pink")) {
            return fireOpal;
        } else if (tileColor.equals("blue")) {
            return oldMauve;
        } else if (tileColor.equals("green")) {
            return isabelline;
        } else if (tileColor.equals("yellow")) {
            return dimGrey;
        } else if (tileColor.equals("black")) {
            return Color.BLACK;
        } else if (tileColor.equals("white")) {
            return tiffanyBlue;
        } else if (tileColor.equals("orange")) {
            return carnelian;
        }
        return Color.BLACK;
    }

    public void setColor(String color) {
        this.tileColor = color;
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.PINK);
        this.setOpaque(true);
        this.setBackground(this.actualColor());
        this.setBorder(new LineBorder(Color.BLACK));
        this.setBorderPainted(false);
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }

}
