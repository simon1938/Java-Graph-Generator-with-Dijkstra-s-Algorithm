package plan.controleur;

import plan.modele.Arc;
import plan.modele.Fichier;
import plan.modele.Graphe;
import plan.vue.InterfaceGraphique;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ArcControleur implements ActionListener {

    private Graphe graphe;
    private InterfaceGraphique interfaceGraphique;
    private Fichier fichier;
    public ArcControleur(Graphe graphe, InterfaceGraphique interfaceGraphique, Fichier fichier){
        this.graphe = graphe;
        this.interfaceGraphique = interfaceGraphique;
        this.fichier = fichier;
        this.interfaceGraphique.getBoutonSupprimerArc().addActionListener(this);
        this.interfaceGraphique.getBoutonAjouterArc().addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == interfaceGraphique.getBoutonSupprimerArc()) {
            //formulaire pour modifier un arc
            String nomSommetDepart = JOptionPane.showInputDialog("Nom du sommet de départ de l'arc à supprimer:");
            String nomSommetArrivee = JOptionPane.showInputDialog("Nom du sommet d'arrivée de l'arc à supprimer:");

            if (nomSommetDepart != null && !nomSommetDepart.isEmpty() && nomSommetArrivee != null && !nomSommetArrivee.isEmpty()) {
                int idSommetDepart = graphe.getIdSommetParNom(nomSommetDepart);
                int idSommetArrivee = graphe.getIdSommetParNom(nomSommetArrivee);

                if (idSommetDepart == -1 || idSommetArrivee == -1) {
                    JOptionPane.showMessageDialog(null, "Les sommets sélectionnés n'existent pas!", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                // Supprimer l'arc du graphe
                graphe.supprimerArc(idSommetDepart, idSommetArrivee);
                fichier.sauvegarder_graphes();

                interfaceGraphique.setZoneGraphe(interfaceGraphique.dessinerGraphe(graphe, interfaceGraphique.getZoneGraphe()));
                interfaceGraphique.getZoneGraphe().revalidate();
                interfaceGraphique.getZoneGraphe().repaint();
            }
        }else if(e.getSource() == interfaceGraphique.getBoutonAjouterArc()){
            //formulaire pour ajouter un arc
            String nomSommetDepart = JOptionPane.showInputDialog( "Nom du sommet de départ:");
            String nomSommetArrivee = JOptionPane.showInputDialog( "Nom du sommet d'arrivée:");
            String distanceStr = JOptionPane.showInputDialog( "Distance de l'arc:");

            if (nomSommetDepart != null && !nomSommetDepart.isEmpty() && nomSommetArrivee != null && !nomSommetArrivee.isEmpty() && distanceStr != null && !distanceStr.isEmpty()) {
                int idSommetDepart = graphe.getIdSommetParNom(nomSommetDepart);
                int idSommetArrivee = graphe.getIdSommetParNom(nomSommetArrivee);

                if (idSommetDepart == -1 || idSommetArrivee == -1) {
                    JOptionPane.showMessageDialog(null, "Les sommets sélectionnés n'existent pas!", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int distance = Integer.parseInt(distanceStr);

                Arc nouvelArc = new Arc("test", idSommetDepart, idSommetArrivee, distance);
                graphe.setArcs(nouvelArc);
                graphe.update_id_arc(-1);
                fichier.sauvegarder_graphes();

                interfaceGraphique.setZoneGraphe(interfaceGraphique.dessinerGraphe(graphe, interfaceGraphique.getZoneGraphe()));
                interfaceGraphique.getZoneGraphe().revalidate();
                interfaceGraphique.getZoneGraphe().repaint();
            }
        }
    }
}
