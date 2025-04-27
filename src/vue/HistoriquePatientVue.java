package vue;

import modele.RendezVous;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class HistoriquePatientVue extends JFrame {
    private DefaultListModel<RendezVous> modelListe;
    private JList<RendezVous> listeRdv;
    private JButton retourButton;

    public HistoriquePatientVue(String nomPatient) {
        setTitle("Historique de " + nomPatient);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        BackgroundPanel backgroundPanel = new BackgroundPanel("Images/4.png");

        Color rouge = new Color(208, 56, 56);
        Color transparentBlanc = new Color(255, 255, 255, 220);
        Font buttonFont = new Font("SansSerif", Font.BOLD, 20);

        Font titleFont = new Font("SansSerif", Font.BOLD, 48);
        Font listFont = new Font("SansSerif", Font.PLAIN, 18);

        JLabel titre = new JLabel("\ud83d\udcdc Historique des Rendez-vous \ud83d\udcdc", SwingConstants.CENTER);
        titre.setFont(titleFont);
        titre.setForeground(rouge);
        titre.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));

        retourButton = new JButton("Retour");
        retourButton.setFont(buttonFont);
        retourButton.setBackground(rouge);
        retourButton.setForeground(Color.WHITE);
        retourButton.addActionListener(e -> dispose()); // Ferme la fenêtre

        modelListe = new DefaultListModel<>();
        listeRdv = new JList<>(modelListe);
        listeRdv.setFont(listFont);
        listeRdv.setOpaque(false);

        JScrollPane scrollPane = new JScrollPane(listeRdv);
        scrollPane.setPreferredSize(new Dimension(600, 300));
        scrollPane.setBorder(BorderFactory.createLineBorder(rouge, 3, true));

        JPanel container = new JPanel(new GridBagLayout());
        container.setOpaque(false);



        // Placement du bouton "Retour" (en haut à gauche)
        GridBagConstraints retourGbc = new GridBagConstraints();
        retourGbc.insets = new Insets(20, 20, 20, 20); // Espacement autour du bouton
        retourGbc.gridx = 0;
        retourGbc.gridy = 0;
        retourGbc.anchor = GridBagConstraints.NORTH; // Position en haut à gauche
        container.add(retourButton, retourGbc);

        // Placement du titre, un peu plus bas
        GridBagConstraints titreGbc = new GridBagConstraints();
        titreGbc.gridx = 0;
        titreGbc.gridy = 1;  // Le titre est placé à une ligne plus bas que le bouton
        titreGbc.insets = new Insets(20, 0, 20, 0); // Espacement autour du titre
        container.add(titre, titreGbc);

        // Placement de la liste de rendez-vous
        GridBagConstraints listeGbc = new GridBagConstraints();
        listeGbc.gridx = 0;
        listeGbc.gridy = 2;
        listeGbc.insets = new Insets(0, 0, 20, 0); // Espacement sous la liste
        container.add(scrollPane, listeGbc);

        backgroundPanel.add(container);
        setContentPane(backgroundPanel);
    }

    public void afficherHistorique(List<RendezVous> rdvList) {
        modelListe.clear();

        if (rdvList.isEmpty()) {
            modelListe.addElement(new RendezVous(-1, null, "Aucun rendez-vous pass\u00e9."));
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