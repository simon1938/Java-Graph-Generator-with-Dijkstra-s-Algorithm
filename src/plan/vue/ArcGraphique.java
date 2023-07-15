package plan.vue;

import javax.swing.*;
import java.awt.*;

public class ArcGraphique extends JComponent {

    int origine_x;
    int origine_y;

    int destination_x;
    int destination_y;
    public ArcGraphique(int startx, int starty, int endx, int endy){
        //System.out.println(startx);
        this.origine_x = startx;
        this.origine_y = starty;
        this.destination_x = endx;
        this.destination_y = endy;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);

        // Dessin des flèches
        drawArrow(g2d, this.origine_x, this.origine_y, this.destination_x, this.destination_y);
    }
    public void drawArrow(Graphics2D g2d, int startX, int startY, int endX, int endY) {

        // Calcul de l'angle de l'arc
        double angleRadians = calculateAngle(startX, startY, endX, endY);

        int arrowSize = 8;

        // Définition de l'épaisseur du trait
        float strokeWidth = 2.0f; // Épaisseur souhaitée
        g2d.setStroke(new BasicStroke(strokeWidth));

        // Calcul du nouveau point d'origine réduit
        int newStartX = startX + (int) (40 * Math.cos(angleRadians));
        int newStartY = startY + (int) (40 * Math.sin(angleRadians));
        int newEndX = endX - (int) (40 * Math.cos(angleRadians));
        int newEndY = endY - (int) (40 * Math.sin(angleRadians));


        // Calcul des coordonnées des deux extrémités de la flèche
        int x1 = (int) (newEndX - arrowSize * Math.cos(angleRadians - Math.PI / 6));
        int y1 = (int) (newEndY - arrowSize * Math.sin(angleRadians - Math.PI / 6));
        int x2 = (int) (newEndX - arrowSize * Math.cos(angleRadians + Math.PI / 6));
        int y2 = (int) (newEndY - arrowSize * Math.sin(angleRadians + Math.PI / 6));

        // Dessin de la ligne
        g2d.drawLine(newStartX, newStartY, newEndX, newEndY);

        // Dessin de la ligne de la flèche
        g2d.drawLine(newEndX, newEndY, x1, y1);
        g2d.drawLine(newEndX, newEndY, x2, y2);
    }

    public double calculateAngle(int startX, int startY, int endX, int endY) {
        int deltaX = endX - startX;
        int deltaY = endY - startY;
        return Math.atan2(deltaY, deltaX);
    }
}