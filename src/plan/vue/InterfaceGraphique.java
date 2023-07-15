package plan.vue;

import plan.controleur.ArcControleur;
import plan.controleur.GrapheControleur;
import plan.controleur.SommetControleur;
import plan.modele.Arc;
import plan.modele.Fichier;
import plan.modele.Graphe;
import plan.modele.Sommet;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.JComboBox;
import java.util.List;


public class InterfaceGraphique extends JFrame {
    public JPanel conteneur;
    public JPanel zoneGraphe;
    private Point origin;
    public JComboBox<String> listeGraphes;
    public JButton boutonSupprimer;
    public JButton boutonAjouter;
    public JButton boutonAjouterArc;
    public JButton boutonSupprimerArc ;
    public JButton boutonCalculerChemin;



    ArrayList<String> nomGraphes = new ArrayList<>();

    public InterfaceGraphique(Graphe graphe, Fichier fichier) {
        this.conteneur = new JPanel();
        this.zoneGraphe = new JPanel();
        this.zoneGraphe = dessinerGraphe(graphe, zoneGraphe);

        this.conteneur.setLayout(null);

        int containerWidth = 800; // Définir la largeur souhaitée du conteneur
        int containerHeight = 600; // Définir la hauteur souhaitée du conteneur
        this.conteneur.setPreferredSize(new Dimension(containerWidth, containerHeight));

        int zoneGrapheWidth = 2000; // Définir la largeur souhaitée pour le zoneGraphe
        int zoneGrapheHeight = 2000; // Définir la hauteur souhaitée pour le zoneGraphe
        this.zoneGraphe.setBounds(0, 0, zoneGrapheWidth, zoneGrapheHeight);

        this.conteneur.add(this.zoneGraphe, BorderLayout.CENTER);

        // Ajout des noms des graphes existants à la liste nomGraphes
        List<String> listeNoms = graphe.get_liste_name_sommet();
        nomGraphes.addAll(listeNoms);
        this.listeGraphes = new JComboBox<>(nomGraphes.toArray(new String[0]));
        this.listeGraphes.setBounds(containerWidth - 150, 10, 120, 30);
        this.conteneur.add(this.listeGraphes);

        // Création du bouton pour supprimer un sommet
        this.boutonSupprimer = new JButton("Supprimer Noeud");
        this.boutonSupprimer.setBounds(containerWidth - 150, 50, 120, 30);
        this.conteneur.add(this.boutonSupprimer);


        // Création du bouton pour ajouter un sommet
        this.boutonAjouter = new JButton("Ajouter élément");
        this.boutonAjouter.setBounds(containerWidth - 150, 90, 120, 30);
        this.conteneur.add(this.boutonAjouter);
        this.conteneur.add(this.zoneGraphe, BorderLayout.CENTER);
        SommetControleur mouseListener = new SommetControleur(graphe,fichier, this, listeGraphes, nomGraphes);


        // Création du bouton pour ajouter un arc
        this.boutonAjouterArc = new JButton("Ajouter arc");
        this.boutonAjouterArc.setBounds(containerWidth - 150, 130, 120, 30);
        this.conteneur.add(this.boutonAjouterArc);
        this.conteneur.add(this.zoneGraphe, BorderLayout.CENTER);


        // Création du bouton pour supprimer un arc
        this.boutonSupprimerArc = new JButton("Supprimer arc");
        this.boutonSupprimerArc.setBounds(containerWidth - 150, 170, 120, 30);
        this.conteneur.add(this.boutonSupprimerArc);
        this.conteneur.add(this.zoneGraphe, BorderLayout.CENTER);
        ArcControleur arcControleur = new ArcControleur(graphe, this, fichier);

        // Création du bouton pour calculer le plus court chemin
        this.boutonCalculerChemin = new JButton("Calculer le plus court chemin");
        this.boutonCalculerChemin.setBounds(containerWidth - 150, 210, 120, 30);
        this.conteneur.add(this.boutonCalculerChemin);
        this.conteneur.add(this.zoneGraphe, BorderLayout.CENTER);
        GrapheControleur grapheControleur = new GrapheControleur(graphe, this);

        add(conteneur);

        setTitle("Graphe Interactif");
        pack();
        setSize(900, 900);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public JPanel dessinerGraphe(Graphe graphe, JPanel panel) {
        panel.removeAll(); // Supprimer tous les composants graphiques précédents
        panel.setBackground(Color.white);
        panel.setLayout(null);

        // Dessiner tous les sommets du graphe
        for (Sommet sommet : graphe.sommets) {
            SommetGraphique sommetGraphique = new SommetGraphique(sommet, graphe,this);
            sommetGraphique.setBounds(sommet.getPos_x()*20, sommet.getPos_y()*20, 100, 100);
            SommetControleur mouseListener = new SommetControleur(sommet);
            mouseListener.setSommetGraphique(sommetGraphique);
            sommetGraphique.addMouseListener(mouseListener);
            panel.add(sommetGraphique);
        }

        // Dessiner tous les arcs entre ces sommets
        for (Arc arc : graphe.arcs) {
            int startx = graphe.sommets.get(arc.getOrigine()).getPos_x() * 20 + 40;
            int starty = graphe.sommets.get(arc.getOrigine()).getPos_y() * 20 + 40;
            int endx = graphe.sommets.get(arc.getDestination()).getPos_x() * 20 + 40;
            int endy = graphe.sommets.get(arc.getDestination()).getPos_y() * 20 + 40;

            ArcGraphique arcGraphique = new ArcGraphique(startx, starty, endx, endy);
            arcGraphique.setBounds(0, 0, 2000, 2000);
            panel.add(arcGraphique);
        }

        return panel;
    }

    public JButton getBoutonSupprimerArc(){
        return this.boutonSupprimerArc;
    }

    public JButton getBoutonAjouterArc(){
        return this.boutonAjouterArc;
    }

    public JButton getBoutonSupprimerSommet() {
        return boutonSupprimer;
    }

    public JButton getBoutonAjouterSommet(){
        return this.boutonAjouter;
    }

    public JButton getBoutonCalculerChemin() { return this.boutonCalculerChemin; }

    public void setZoneGraphe(JPanel zoneGraphe){
        this.zoneGraphe = zoneGraphe;
    }

    public JPanel getZoneGraphe(){
        return this.zoneGraphe;
    }

}