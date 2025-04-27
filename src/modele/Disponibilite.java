package modele;

import java.sql.Date;
import java.sql.Time;

public class Disponibilite {
    private int id;
    private Specialiste specialiste;
    private Lieu lieu;
    private Date date;
    private Time heure;
    private boolean disponible;

    public Disponibilite(int id, Specialiste specialiste, Lieu lieu, Date date, Time heure, boolean disponible) {
        this.id = id;
        this.specialiste = specialiste;
        this.lieu = lieu;
        this.date = date;
        this.heure = heure;
        this.disponible = disponible;
    }

    public int getId() { return id; }
    public Specialiste getSpecialiste() { return specialiste; }
    public Lieu getLieu() { return lieu; }
    public Date getDate() { return date; }
    public Time getHeure() { return heure; }
    public boolean isDisponible() { return disponible; }

    @Override
    public String toString() {
        return date + " | " + heure + " | " + specialiste + " | " + lieu;
    }
}
