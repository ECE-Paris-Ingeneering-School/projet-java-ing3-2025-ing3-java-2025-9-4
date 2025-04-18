package vue;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class AccueilPatientVue extends JFrame {
    private JButton rdvButton;
    private JButton agendaButton;
    private JButton historiqueButton;
    private JButton deconnexionButton;

    public AccueilPatientVue(String nomPatient) {
        setTitle("Bienvenue " + nomPatient);
        setSize(400, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // --- Titre ---
        JLabel titre = new JLabel("Espace Patient");
        titre.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titre.setHorizontalAlignment(SwingConstants.CENTER);
        titre.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        // --- Boutons ---
        rdvButton = new JButton("Prendre un rendez-vous");
        agendaButton = new JButton("Voir mon agenda");
        historiqueButton = new JButton("Voir mon historique");
        deconnexionButton = new JButton("Se déconnecter");

        JButton[] boutons = {rdvButton, agendaButton, historiqueButton, deconnexionButton};
        for (JButton b : boutons) {
            b.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            b.setFocusPainted(false);
            b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            b.setPreferredSize(new Dimension(280, 35));
        }

        // --- Panel central vertical ---
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
        centerPanel.setBackground(new Color(245, 245, 245));

        centerPanel.add(center(rdvButton));
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        centerPanel.add(center(agendaButton));
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        centerPanel.add(center(historiqueButton));
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        centerPanel.add(center(deconnexionButton));

        // --- Mise en page finale ---
        setLayout(new BorderLayout());
        add(titre, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
    }

    private JPanel center(JButton button) {
        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrapper.setOpaque(false);
        wrapper.add(button);
        return wrapper;
    }

    public void setPrendreRdvListener(ActionListener listener) {
        rdvButton.addActionListener(listener);
    }

    public void setAgendaListener(ActionListener listener) {
        agendaButton.addActionListener(listener);
    }

    public void setHistoriqueListener(ActionListener listener) {
        historiqueButton.addActionListener(listener);
    }

    public void setDeconnexionListener(ActionListener listener) {
        deconnexionButton.addActionListener(listener);
    }
}
