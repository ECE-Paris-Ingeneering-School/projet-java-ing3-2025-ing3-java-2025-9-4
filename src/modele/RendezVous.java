package modele;

public class RendezVous {
    private int id;
    private Disponibilite disponibilite;
    private String note;

    public RendezVous(int id, Disponibilite disponibilite, String note) {
        this.id = id;
        this.disponibilite = disponibilite;
        this.note = note;
    }

    // Getters
    public int getId() {
        return id;
    }

    public Disponibilite getDisponibilite() {
        return disponibilite;
    }

    public String getNote() {
        return note;
    }

    // Setters (optionnels)
    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        if (disponibilite == null) {
            return note != null ? note : "Rendez-vous (données manquantes)";
        }

        return disponibilite.getDate() + " à " + disponibilite.getHeure() +
                " avec " + disponibilite.getSpecialiste().getNom() +
                " (" + disponibilite.getSpecialiste().getSpecialisation() + ")" +
                " - " + disponibilite.getLieu().getVille();
    }
}
