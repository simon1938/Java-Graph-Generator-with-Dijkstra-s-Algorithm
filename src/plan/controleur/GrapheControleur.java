package plan.controleur;

import plan.modele.Graphe;
import plan.vue.InterfaceGraphique;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

public class GrapheControleur implements ActionListener {
    Graphe graphe;
    InterfaceGraphique interfaceGraphique;
    public GrapheControleur(Graphe graphe, InterfaceGraphique interfaceGraphique){
        this.graphe = graphe;
        this.interfaceGraphique = interfaceGraphique;
        this.interfaceGraphique.getBoutonCalculerChemin().addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        //formulaire pour calcul plus court sommet
        String nomSommetDepart = JOptionPane.showInputDialog("Nom du sommet de départ:");
        String nomSommetArrivee = JOptionPane.showInputDialog("Nom du sommet d'arrivée:");

        if (nomSommetDepart != null && !nomSommetDepart.isEmpty() && nomSommetArrivee != null && !nomSommetArrivee.isEmpty()) {
            int idSommetDepart = graphe.getIdSommetParNom(nomSommetDepart);
            int idSommetArrivee = graphe.getIdSommetParNom(nomSommetArrivee);

            if (idSommetDepart == -1 || idSommetArrivee == -1) {
                JOptionPane.showMessageDialog(null, "Les sommets sélectionnés n'existent pas!", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Map<String, Object> resultatDijkstra = graphe.dijkstra(idSommetDepart, idSommetArrivee);

            int distance = (int) resultatDijkstra.get("distance");
            List<Integer> chemin = (List<Integer>) resultatDijkstra.get("chemin");

            String message = "Distance : " + distance + "\nChemin : ";
            for (int i = 0; i < chemin.size(); i++) {
                message += graphe.sommets.get(chemin.get(i)).getLieu();
                if (i != chemin.size() - 1) {
                    message += " -> ";
                }
            }

            JOptionPane.showMessageDialog(null, message, "Plus court chemin", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
