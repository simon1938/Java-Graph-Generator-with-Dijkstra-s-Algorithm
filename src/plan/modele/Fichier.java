package plan.modele;

import java.io.*;
import java.util.Scanner;


public class Fichier {
    private Scanner scanner;
    private File file;
    Graphe graphe;

    public Fichier(Graphe graphe) {
        this.graphe=graphe;

        try {
            //Récupération du fichier qui nous permettra d'initialiser le graphe
            file = new File("src/plan/file.txt");
            scanner = new Scanner(file);

            boolean lireSommets = false;
            boolean lireArcs = false;

            while (scanner.hasNextLine()){
                String ligne = scanner.nextLine();

                if(ligne.equals("Sommets:")){
                    lireSommets = true;
                    lireArcs = false;
                } else if (ligne.equals("Arcs:")) {
                    lireSommets = false;
                    lireArcs = true;
                } else if (lireSommets && !ligne.isEmpty()) {
                    String[] parties = ligne.split(":");
                    String id = parties[0].trim();
                    String ville = parties[1].trim();
                    String pos_x = parties[2].trim();
                    String pos_y = parties[3].trim();
                    Sommet sommet = new Sommet(Integer.parseInt(id), ville, Integer.parseInt(pos_x), Integer.parseInt(pos_y));
                    sommet.setDernier_id(Integer.parseInt(id));
                    graphe.setSommet(sommet);
                } else if (lireArcs && !ligne.isEmpty()) {
                    String[] parties = ligne.split("->");
                    String id = parties[0].trim();
                    String rue = parties[1].trim();
                    String source = parties[2].trim();
                    String destination = parties[3].trim();
                    String distance = parties[4].trim();
                    Arc arc = new Arc(Integer.parseInt(id), rue, Integer.parseInt(source), Integer.parseInt(destination), Integer.parseInt(distance));
                    arc.setDernierArc_id(Integer.parseInt(id));
                    graphe.setArcs(arc);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
   // fonction de sauvegarde du graphe actuel dans le fichier
    public void sauvegarder_graphes() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("src/plan/file.txt", false)); // Ajout de l'argument 'false' pour écraser le contenu du fichier

            // mettre les sommets dans le fichier
            writer.write("Sommets:\n");
            for (Sommet s : graphe.getSommets()) {
                writer.write(s.getId() + " : " + s.getLieu() + " : " + s.getPos_x() + " : " + s.getPos_y() + "\n");
            }
            // mettre les arcs dans le fichier
            writer.write("\nArcs:\n");
            for (Arc a : graphe.getArcs()) {
                writer.write(a.getId() + " -> " + a.getRue() + " -> " + a.getOrigine() + " -> " + a.getDestination() + " -> " + a.getDistance() + "\n");
            }

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

