package modele;


public class Lieu {
    private int id; // Initialisation variables
    private String adresse;
    private String ville;

    public Lieu(int id, String adresse, String ville) { // Création du constructeur lieu
        this.id = id;
        this.adresse = adresse;
        this.ville = ville;
    }

    public int getId() { return id; } // Renvoie l'ID
    public String getAdresse() { return adresse; } // Renvoie l'adresse
    public String getVille() { return ville; } // renvoie la ville

    @Override
    public String toString() {
        return ville + " - " + adresse;
    } // Renvoie la ville et l'adresse
}
