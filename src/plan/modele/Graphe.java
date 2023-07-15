package plan.modele;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graphe {
    public List<Sommet> sommets = new ArrayList<>();
    public List<Arc> arcs = new ArrayList<>();

    private Fichier fichier;

    public Graphe(List<Sommet> S, List<Arc> A) {
        this.sommets = S;
        this.arcs = A;
    }



    public Graphe getGraphe(){
        return this;
    }
    public List<Sommet> getSommets() {
        return sommets;
    }

    public List<Arc> getArcs() {
        return arcs;
    }

    public void setSommet(Sommet sommet) {
        this.sommets.add(sommet);
    }

    public void setArcs(Arc arc) {
        this.arcs.add(arc);
    }

    public Sommet getSommet(int index) {
        return sommets.get(index);
    }

    public void afficherGraphe() {
        System.out.println("Voici le graphe :");
        System.out.println("Liste des sommets :");
        for (Sommet sommet : sommets) {
            System.out.println("Sommet " + sommet.getId() + " - Lieu : " + sommet.getLieu());
            System.out.println("    Position (x, y) : (" + sommet.getPos_x() + ", " + sommet.getPos_y() + ")");
        }
        System.out.println("Liste des arcs :");
        for (Arc arc : arcs) {
            System.out.println("Arc " + arc.getId() + " - Rue : " + arc.getRue());
            System.out.println("    Origine : Sommet " + arc.getOrigine());
            System.out.println("    Destination : Sommet " + arc.getDestination());
            System.out.println("    Distance : " + arc.getDistance() + " km");
        }
        System.out.println("Fin du graphe.");
    }

    public List<String> get_liste_name_sommet() {
        List<String> listeNoms = new ArrayList<>();
        for (Sommet sommet : sommets) {
            listeNoms.add(sommet.getLieu());
        }
        return listeNoms;
    }

    public void supprimerSommet(int id) {
        // Supprimer le sommet avec l'ID spécifié
        Sommet sommetASupprimer = null;
        for (Sommet sommet : sommets) {
            if (sommet.getId() == id) {
                sommetASupprimer = sommet;
                break;
            }
        }
        if (sommetASupprimer != null) {
            int index = sommets.indexOf(sommetASupprimer);
            sommets.remove(sommetASupprimer);

            // Mettre à jour les indices des sommets suivants dans la liste
            for (int i = index; i < sommets.size(); i++) {
                sommets.get(i).setId(i);
            }

            // Supprimer les arcs associés au sommet supprimé
            arcs.removeIf(arc -> arc.getOrigine() == id || arc.getDestination() == id);

            //System.out.println("Le sommet avec l'ID " + id + " a été supprimé du graphe.");
        } else {
            //System.out.println("Aucun sommet avec l'ID " + id + " trouvé dans le graphe.");
        }
    }
    // renvois l'id du sommet en fonction de son nom
    public int getIdSommetParNom(String nomSite) {
        for (Sommet sommet : sommets) {
            if (sommet.getLieu().equals(nomSite)) {
                return sommet.getId();
            }
        }
        return -1; // Retourne -1 si aucun sommet correspondant n'est trouvé
    }

    // Met à jour les ID des sommets dans l'ordre croissant
    public void update_id_sommet() {
        int newId = 0;
        for (Sommet sommet : this.sommets) {
            sommet.setId(newId++);
        }
    }

    // Met à jour les ID des arcs dans l'ordre croissant
    public void update_id_arc(int removedId) {
        int newId = 1;
        for (Arc arc : this.arcs) {
            arc.setId(newId++);
        }

        if (removedId != -1) {
            for (Arc arc : arcs) {
                if (arc.getOrigine() > removedId) {
                    arc.setOrigine(arc.getOrigine() - 1);
                }
                if (arc.getDestination() > removedId) {
                    arc.setDestination(arc.getDestination() - 1);
                }
            }
        }
    }

    public void supprimerArc(int idSommetDepart, int idSommetArrivee) {
        // Rechercher l'arc à supprimer en fonction des sommets de départ et d'arrivée
        Arc arcASupprimer = null;
        for (Arc arc : arcs) {
            if (arc.getOrigine() == idSommetDepart && arc.getDestination() == idSommetArrivee) {
                arcASupprimer = arc;
                break;
            }
        }
        if (arcASupprimer != null) {
            arcs.remove(arcASupprimer);
            //System.out.println("L'arc entre les sommets " + idSommetDepart + " et " + idSommetArrivee + " a été supprimé du graphe.");
        } else {
            //System.out.println("Aucun arc entre les sommets " + idSommetDepart + " et " + idSommetArrivee + " trouvé dans le graphe.");
        }
    }
    // fonction de calcul de chemin le plus court
    public Map<String, Object> dijkstra(int source, int destination) {
        Map<Integer, Integer> distances = new HashMap<>();
        Map<Integer, Integer> predecesseurs = new HashMap<>();
        List<Integer> nonVisites = new ArrayList<>();

        for (Sommet sommet : sommets) {
            distances.put(sommet.getId(), Integer.MAX_VALUE);
            predecesseurs.put(sommet.getId(), null);
            nonVisites.add(sommet.getId());
        }

        distances.put(source, 0);

        while (!nonVisites.isEmpty()) {
            int sommetCourant = getSommetMinimum(distances, nonVisites);
            nonVisites.remove((Integer) sommetCourant);

            if (sommetCourant == destination) {
                break;
            }

            for (Arc arc : arcs) {
                if (arc.getOrigine() == sommetCourant) {
                    int sommetVoisin = arc.getDestination();
                    int distanceTotale = distances.get(sommetCourant) + arc.getDistance();

                    if (distanceTotale < distances.get(sommetVoisin)) {
                        distances.put(sommetVoisin, distanceTotale);
                        predecesseurs.put(sommetVoisin, sommetCourant);
                    }
                }
            }
        }

        // Construction du plus court chemin
        List<Integer> chemin = new ArrayList<>();
        int sommetActuel = destination;
        while (predecesseurs.get(sommetActuel) != null) {
            chemin.add(0, sommetActuel);
            sommetActuel = predecesseurs.get(sommetActuel);
        }
        chemin.add(0, sommetActuel); // Ajouter le sommet source

        Map<String, Object> resultat = new HashMap<>();
        resultat.put("distance", distances.get(destination));
        resultat.put("chemin", chemin);

        return resultat;
    }



    private int getSommetMinimum(Map<Integer, Integer> distances, List<Integer> nonVisites) {
        int minSommet = nonVisites.get(0);
        int minDistance = distances.get(minSommet);

        for (int sommet : nonVisites) {
            int distance = distances.get(sommet);
            if (distance < minDistance) {
                minDistance = distance;
                minSommet = sommet;
            }
        }

        return minSommet;
    }

    public void setFichier(Fichier fichier){
        this.fichier = fichier;
    }

    public Fichier getFichier(){
        return  this.fichier;
    }
    public Graphe() {}


}
