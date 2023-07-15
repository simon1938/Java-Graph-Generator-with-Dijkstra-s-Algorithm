package plan.controleur;

import plan.modele.Fichier;
import plan.modele.Graphe;
import plan.vue.InterfaceGraphique;
import plan.modele.Sommet;
import plan.vue.SommetGraphique;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class SommetControleur implements MouseListener {

    Sommet sommet;

    private Graphe graphe;
    private Fichier fichier;
    private InterfaceGraphique interfaceGraphique;

    private JComboBox<String> listeGraphes;
    private ArrayList<String> nomGraphes;

    private SommetGraphique sommetGraphique;

    public SommetControleur(Graphe graphe, Fichier fichier, InterfaceGraphique interfaceGraphique, JComboBox<String> listeGraphes, ArrayList<String> nomGraphes){
        this.graphe = graphe;
        this.fichier = fichier;
        this.interfaceGraphique = interfaceGraphique;
        this.listeGraphes = listeGraphes;
        this.nomGraphes = nomGraphes;
        this.interfaceGraphique.getBoutonAjouterSommet().addActionListener(e -> ajouterSommet());
        this.interfaceGraphique.getBoutonSupprimerSommet().addActionListener(e -> supprimerSommet());
    }

    public SommetControleur(Sommet sommet){
        this.sommet = sommet;
    }

    public SommetControleur(SommetGraphique sommetGraphique, Graphe graphe,InterfaceGraphique interfaceGraphique){
        this.graphe = graphe;
        this.sommetGraphique = sommetGraphique;
        this.interfaceGraphique=interfaceGraphique;
        this.sommetGraphique.getButtonSave().addActionListener(e -> modifierSommet());
    }

    public void ajouterSommet() {

        //formulaire pour ajouter un sommet
        String nomSommet = JOptionPane.showInputDialog("Nom du sommet:");

        if (nomSommet != null && !nomSommet.isEmpty()) {
            // Boîte de dialogue de saisie pour la coordonnée x
            String xString = JOptionPane.showInputDialog("Coordonnée X du sommet:");
            int x = 0;
            try {
                x = Integer.parseInt(xString);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Coordonnée X invalide!", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String yString = JOptionPane.showInputDialog("Coordonnée Y du sommet:");
            int y = 0;
            try {
                y = Integer.parseInt(yString);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Coordonnée Y invalide!", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Création du sommet avec les valeurs saisies
            Sommet nouveauSommet = new Sommet(nomSommet, x, y);
            graphe.setSommet(nouveauSommet);
            graphe.update_id_sommet();
            graphe.update_id_arc(-1);
            fichier.sauvegarder_graphes();

            // Ajouter le nom du nouvel élément à la liste nomGraphes et Mettre à jour la liste déroulante
            nomGraphes.add(nouveauSommet.getLieu());
            listeGraphes.setModel(new DefaultComboBoxModel<>(nomGraphes.toArray(new String[0])));

            interfaceGraphique.setZoneGraphe(interfaceGraphique.dessinerGraphe(graphe, interfaceGraphique.getZoneGraphe()));
            interfaceGraphique.getZoneGraphe().revalidate();
            interfaceGraphique.getZoneGraphe().repaint();
        }
    }

    public void supprimerSommet(){
        // Ajoutez ici le code pour supprimer le nœud sélectionné
        String nomGrapheSelectionne = (String) listeGraphes.getSelectedItem();
        int id_graphe_supprimer = graphe.getIdSommetParNom(nomGrapheSelectionne);
        graphe.supprimerSommet(id_graphe_supprimer);
        graphe.update_id_sommet();
        graphe.update_id_arc(id_graphe_supprimer);
        fichier.sauvegarder_graphes();

        // Mettre à jour la liste nomGraphes
        nomGraphes.remove(nomGrapheSelectionne);

        // Mettre à jour la liste déroulante
        listeGraphes.setModel(new DefaultComboBoxModel<>(nomGraphes.toArray(new String[0])));

        interfaceGraphique.setZoneGraphe(interfaceGraphique.dessinerGraphe(graphe, interfaceGraphique.getZoneGraphe()));
        interfaceGraphique.getZoneGraphe().revalidate();
        interfaceGraphique.getZoneGraphe().repaint();
    }

    public void modifierSommet() {
        // Récupérer les valeurs des champs de texte
        String lieu = this.sommetGraphique.getNomSommet();
        int positionX = this.sommetGraphique.getPosition_x();
        int positionY = this.sommetGraphique.getPosition_y();
        int id_sommet_to_update= this.sommetGraphique.getId();

       graphe.getSommets().get(id_sommet_to_update).setPos_x(positionX);
       graphe.getSommets().get(id_sommet_to_update).setPos_y(positionY);
       graphe.getSommets().get(id_sommet_to_update).setLieu(lieu);

       graphe.getFichier().sauvegarder_graphes();

        interfaceGraphique.setZoneGraphe(interfaceGraphique.dessinerGraphe(graphe, interfaceGraphique.getZoneGraphe()));
        interfaceGraphique.getZoneGraphe().revalidate();
        interfaceGraphique.getZoneGraphe().repaint();



    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (this.sommetGraphique != null) {
            this.sommetGraphique.afficherInfo();
        } else {
            System.out.println("sommetGraphique is null");
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public void setSommetGraphique(SommetGraphique sommetGraphique) {
        this.sommetGraphique = sommetGraphique;
    }
}
