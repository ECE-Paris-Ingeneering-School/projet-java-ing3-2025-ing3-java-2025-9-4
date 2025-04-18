package modele;

public class Patient {
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;
    private boolean ancien;
    private String role;

    public Patient(int id, String nom, String prenom, String email, String motDePasse, boolean ancien, String role) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.ancien = ancien;
        this.role = role;
    }

    public int getId() { return id; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public String getEmail() { return email; }
    public String getMotDePasse() { return motDePasse; }
    public boolean isAncien() { return ancien; }
    public String getRole() { return role; }
}
