package plan.modele;

public class Sommet {

    private  int id;
    private static int dernier_id = 0;
    private String lieu;
    private int pos_x;
    private int pos_y;

    public Sommet(int id, String lieu, int pos_x, int pos_y){
        this.id = id;
        this.lieu = lieu;
        this.pos_x = pos_x;
        this.pos_y = pos_y;
    }

    public Sommet(String lieu, int pos_x, int pos_y){
        this.id = this.dernier_id + 1;
        this.setDernier_id(this.id);
        this.lieu = lieu;
        this.pos_x = pos_x;
        this.pos_y = pos_y;
    }

    public int getId() {
        return id;
    }

    public int getPos_x() {
        return pos_x;
    }

    public int getPos_y() {
        return pos_y;
    }

    public String getLieu() {
        return lieu;
    }

    public void setId(int id){
        this.id=id;
    }
    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public void setPos_x(int pos_x) {
        this.pos_x = pos_x;
    }

    public void setPos_y(int pos_y) {
        this.pos_y = pos_y;
    }

    public void setDernier_id(int dernier_id) {
        this.dernier_id = dernier_id;
    }
}
