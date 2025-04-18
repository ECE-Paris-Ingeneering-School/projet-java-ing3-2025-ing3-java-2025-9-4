package modele;

public class Specialiste {
    private int id;
    private String nom;
    private String specialisation;
    private String qualification;

    public Specialiste(int id, String nom, String specialisation, String qualification) {
        this.id = id;
        this.nom = nom;
        this.specialisation = specialisation;
        this.qualification = qualification;
    }

    public int getId() { return id; }
    public String getNom() { return nom; }
    public String getSpecialisation() { return specialisation; }
    public String getQualification() { return qualification; }

    @Override
    public String toString() {
        return nom + " (" + specialisation + ")";
    }
}
