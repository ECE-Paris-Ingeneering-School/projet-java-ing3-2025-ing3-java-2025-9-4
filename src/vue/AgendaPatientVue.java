package vue;

import modele.RendezVous;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class AgendaPatientVue extends JFrame {
    private DefaultListModel<RendezVous> modelListe;
    private JList<RendezVous> listeRdv;

    public AgendaPatientVue(String nomPatient) {
        setTitle("Agenda de " + nomPatient);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        BackgroundPanel backgroundPanel = new BackgroundPanel("Images/6.png");
        backgroundPanel.setLayout(new GridBagLayout());

        Color rouge = new Color(208, 56, 56);
        Color noir = new Color(0, 0, 0);
        Color semiTransparentWhite = new Color(255, 255, 255, 220);

        // Titre
        JLabel titre = new JLabel("\uD83D\uDCC5 Rendez-vous à venir \uD83D\uDCC5", SwingConstants.CENTER);
        titre.setFont(new Font("SansSerif", Font.BOLD, 48));
        titre.setForeground(noir);
        titre.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 0));

        // Liste des rendez-vous
        modelListe = new DefaultListModel<>();
        listeRdv = new JList<>(modelListe);
        listeRdv.setFont(new Font("SansSerif", Font.PLAIN, 18));
        listeRdv.setForeground(Color.BLACK);

        JScrollPane scrollPane = new JScrollPane(listeRdv);
        scrollPane.setPreferredSize(new Dimension(600, 300));
        scrollPane.setBorder(BorderFactory.createLineBorder(rouge, 3, true));

        JPanel container = new JPanel(new GridBagLayout());
        container.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 20, 0);
        container.add(titre, gbc);

        gbc.gridy = 1;
        container.add(scrollPane, gbc);

        backgroundPanel.add(container);
        setContentPane(backgroundPanel);
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

    static class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(String imagePath) {
            try {
                backgroundImage = ImageIO.read(new File(imagePath));
            } catch (IOException e) {
                System.err.println("Erreur chargement image : " + e.getMessage());
            }
            setLayout(new GridBagLayout());
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }
}
