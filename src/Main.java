import plan.modele.Fichier;
import plan.modele.Graphe;
import plan.vue.InterfaceGraphique;


public class Main {
    public static void main(String[] args){
        Graphe graphe = new Graphe();
        Fichier file = new Fichier(graphe);
        graphe.setFichier(file);
        InterfaceGraphique gui = new InterfaceGraphique(graphe,file);
        //sauvegarde
        file.sauvegarder_graphes();
    }
}

