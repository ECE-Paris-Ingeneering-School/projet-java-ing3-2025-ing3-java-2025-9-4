package vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class AdminDashboardVue extends JFrame {
    private JButton specialistesButton;
    private JButton disponibilitesButton;
    private JButton patientsButton;
    private JButton deconnexionButton;

    public AdminDashboardVue() {
        setTitle("Dashboard Administrateur");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel titre = new JLabel("Espace Admin", SwingConstants.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 20));

        specialistesButton = new JButton("Gérer les spécialistes");
        disponibilitesButton = new JButton("Gérer les créneaux");
        patientsButton = new JButton("Gérer les patients");
        deconnexionButton = new JButton("Se déconnecter");

        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
        panel.add(titre);
        panel.add(specialistesButton);
        panel.add(disponibilitesButton);
        panel.add(patientsButton);
        panel.add(deconnexionButton);

        add(panel);
    }

    public void setSpecialistesListener(ActionListener listener) {
        specialistesButton.addActionListener(listener);
    }

    public void setDisponibilitesListener(ActionListener listener) {
        disponibilitesButton.addActionListener(listener);
    }

    public void setPatientsListener(ActionListener listener) {
        patientsButton.addActionListener(listener);
    }

    public void setDeconnexionListener(ActionListener listener) {
        deconnexionButton.addActionListener(listener);
    }
}
