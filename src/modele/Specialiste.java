package modele; //appartient au package modele 

public class Specialiste {
    //attributs
    private int id;
    private String nom;
    private String specialisation;
    private String qualification;
    
    //constructeur
    public Specialiste(int id, String nom, String specialisation, String qualification) {
        this.id = id;
        this.nom = nom;
        this.specialisation = specialisation;
        this.qualification = qualification;
    }

    //getters
    public int getId() { return id; }
    public String getNom() { return nom; }
    public String getSpecialisation() { return specialisation; }
    public String getQualification() { return qualification; }

    @Override //affichage personnalisé
    public String toString() {
        return nom + " (" + specialisation + ")";
    }
}
