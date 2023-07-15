package plan.vue;

import plan.controleur.SommetControleur;
import plan.modele.Graphe;
import plan.modele.Sommet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SommetGraphique extends JPanel {

    public Graphe graphe;
    private String nomSommet;
    private int position_x;
    private int position_y;

    private int id;

    private JButton buttonSave;
    private InterfaceGraphique interfaceGraphique;



    public SommetGraphique(Sommet s, Graphe graphe,InterfaceGraphique interfaceGraphique){
        this.graphe = graphe;
        this.nomSommet = s.getLieu();
        this.position_x = s.getPos_x();
        this.position_y = s.getPos_y();
        this.id = s.getId();
        this.interfaceGraphique=interfaceGraphique;
        setPreferredSize(new Dimension(100, 100)); // Définir une taille préférée pour le SommetGraphique
        setOpaque(false);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);

        // Définition de l'épaisseur du trait
        float strokeWidth = 3.0f; // Épaisseur souhaitée
        g2d.setStroke(new BasicStroke(strokeWidth));

        int largeur = 80;
        int hauteur = 80;

        g2d.drawOval(0, 0, largeur, hauteur);

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        String texte = nomSommet;
        FontMetrics fm = g.getFontMetrics();
        int largeurTexte = fm.stringWidth(texte);
        int hauteurTexte = fm.getHeight();
        int x =  (largeur - largeurTexte) / 2;
        int y =  (hauteur - hauteurTexte) /2 + fm.getAscent();
        g.drawString(texte, x, y);
    }



    public JFrame afficherInfo() {
        JFrame frame = new JFrame();
        frame.setTitle("La ville de " + this.nomSommet);
        frame.setSize(350, 250);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        // Création du conteneur avec un GridBagLayout
        JPanel panel = new JPanel(new GridBagLayout());
        frame.add(panel);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(5, 5, 5, 5);

        // Label et champ de texte pour le lieu
        JLabel labelLieu = new JLabel("Lieu");
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(labelLieu, constraints);

        final JTextField fieldLieu = new JTextField(this.nomSommet, 20);
        fieldLieu.setEditable(true);
        constraints.gridx = 1;
        panel.add(fieldLieu, constraints);

        // Label et champ de texte pour la position x
        JLabel labelX = new JLabel("Position x");
        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(labelX, constraints);

        final JTextField fieldX = new JTextField(String.valueOf(this.position_x), 20);
        fieldX.setEditable(true);
        constraints.gridx = 1;
        panel.add(fieldX, constraints);

        // Label et champ de texte pour la position y
        JLabel labelY = new JLabel("Position y");
        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(labelY, constraints);

        final JTextField fieldY = new JTextField(String.valueOf(this.position_y), 20);
        fieldY.setEditable(true);
        constraints.gridx = 1;
        panel.add(fieldY, constraints);

        // Bouton "Sauvegarder"
        buttonSave = new JButton("Sauvegarder");
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        constraints.insets = new Insets(10, 0, 0, 0);
        panel.add(buttonSave, constraints);
        SommetControleur sommetControleur = new SommetControleur(this, this.graphe,this.interfaceGraphique);
        buttonSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nouveauLieu = fieldLieu.getText();
                int nouvellePositionX = Integer.parseInt(fieldX.getText());
                int nouvellePositionY = Integer.parseInt(fieldY.getText());


                setNomSommet(nouveauLieu);
                setPosition_x(nouvellePositionX);
                setPosition_y(nouvellePositionY);

                frame.dispose();
            }

        });

        frame.setVisible(true);
        return frame;
    }



    public int getPosition_x() {
        return position_x;
    }

    public int getPosition_y() {
        return position_y;
    }

    public String getNomSommet() {
        return nomSommet;
    }

    public JButton getButtonSave() {
        return this.buttonSave;
    }

    public int getId(){return this.id;}

    public void setPosition_x(int position_x) {
        this.position_x = position_x;
    }

    public void setPosition_y(int position_y) {
        this.position_y = position_y;
    }

    public void setId(int id) {this.id = id;}

    public void setNomSommet(String nomSommet) {
        this.nomSommet = nomSommet;
    }
}
