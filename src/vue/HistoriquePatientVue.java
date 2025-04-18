package vue;

import modele.RendezVous;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class HistoriquePatientVue extends JFrame {
    private DefaultListModel<RendezVous> modelListe;
    private JList<RendezVous> listeRdv;

    public HistoriquePatientVue(String nomPatient) {
        setTitle("Historique de " + nomPatient);
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        modelListe = new DefaultListModel<>();
        listeRdv = new JList<>(modelListe);
        JScrollPane scroll = new JScrollPane(listeRdv);

        JLabel titre = new JLabel("Rendez-vous passés", SwingConstants.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 18));

        setLayout(new BorderLayout());
        add(titre, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }

    public void afficherHistorique(List<RendezVous> rdvList) {
        modelListe.clear();
        if (rdvList.isEmpty()) {
            modelListe.addElement(new RendezVous(-1, null, "Aucun rendez-vous passé."));
        } else {
            for (RendezVous rdv : rdvList) {
                modelListe.addElement(rdv);
            }
        }
    }
}
