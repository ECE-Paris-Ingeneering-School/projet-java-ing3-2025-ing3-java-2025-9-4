package modele;


public class Lieu {
    private int id;
    private String adresse;
    private String ville;

    public Lieu(int id, String adresse, String ville) {
        this.id = id;
        this.adresse = adresse;
        this.ville = ville;
    }

    public int getId() { return id; }
    public String getAdresse() { return adresse; }
    public String getVille() { return ville; }

    @Override
    public String toString() {
        return ville + " - " + adresse;
    }
}
