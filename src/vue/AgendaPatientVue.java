package vue;

import modele.RendezVous;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AgendaPatientVue extends JFrame {
    private DefaultListModel<RendezVous> modelListe;
    private JList<RendezVous> listeRdv;

    public AgendaPatientVue(String nomPatient) {
        setTitle("Agenda de " + nomPatient);
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        modelListe = new DefaultListModel<>();
        listeRdv = new JList<>(modelListe);
        JScrollPane scroll = new JScrollPane(listeRdv);

        JLabel titre = new JLabel("Rendez-vous à venir", SwingConstants.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 18));

        setLayout(new BorderLayout());
        add(titre, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }

    public void afficherListeRdv(List<RendezVous> rdvList) {
        modelListe.clear();
        if (rdvList.isEmpty()) {
            modelListe.addElement(new RendezVous(-1, null, "Aucun rendez-vous à venir."));
        } else {
            for (RendezVous rdv : rdvList) {
                modelListe.addElement(rdv);
            }
        }
    }
}
