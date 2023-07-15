package plan.modele;

public class Arc {
    private int id;
    private static int dernier_id = 0;
    private int origine;
    private int destination;
    private int distance;
    private String rue;

    public Arc(int id, String rue, int origine, int destination, int distance){
        this.id = id;
        this.rue = rue;
        this.destination = destination;
        this.origine = origine;
        this.distance = distance;
    }

    public Arc(String rue, int origine, int destination, int distance){
        this.id = this.dernier_id + 1;
        this.setDernierArc_id(this.id);
        this.rue = rue;
        this.destination = destination;
        this.origine = origine;
        this.distance = distance;
    }

    public int getId() {
        return id;
    }

    public int getDistance() {
        return distance;
    }

    public int getOrigine() {
        return origine;
    }

    public String getRue() {
        return rue;
    }

    public int getDestination() {
        return destination;
    }
    public void setId(int id){
        this.id=id;
    }
    public void setOrigine(int origine){
        this.origine=origine;
    }
    public void setDestination(int destination){
        this.destination=destination;
    }


    public void setDernierArc_id(int dernier_id) {
        this.dernier_id = dernier_id;
    }



}