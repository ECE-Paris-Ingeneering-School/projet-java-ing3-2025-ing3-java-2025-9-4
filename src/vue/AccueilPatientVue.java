package vue;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BackgroundPanel(String imagePath) {
        try {
            backgroundImage = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            System.err.println("Image de fond introuvable : " + e.getMessage());
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


public class AccueilPatientVue extends JFrame {
    private JButton rdvButton;
    private JButton agendaButton;
    private JButton historiqueButton;
    private JButton deconnexionButton;

    public AccueilPatientVue(String nomPatient) {
        setTitle("Bienvenue " + nomPatient);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Plein écran
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // --- Fond d'écran ---
        BackgroundPanel backgroundLabel = new BackgroundPanel("Images/fond_accueil.jpg");


        // --- Conteneur principal ---
        JPanel containerPanel = new JPanel(new BorderLayout());
        containerPanel.setOpaque(false);

        // --- Titre ---
        JLabel titre = new JLabel("\uD83D\uDEE0\uFE0F Espace Client \uD83D\uDEE0\uFE0F", SwingConstants.CENTER);
        titre.setFont(new Font("SansSerif", Font.BOLD, 48));
        titre.setForeground(Color.WHITE);
        titre.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));

        // --- Formulaire stylé avec coins arrondis ---
        RoundedPanel formPanel = new RoundedPanel(30);
        formPanel.setLayout(new GridLayout(5, 1, 20, 20));
        formPanel.setPreferredSize(new Dimension(500, 450));
        formPanel.setBackground(new Color(20, 20, 50, 180)); // violet/bleu sombre semi-transparent
        formPanel.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

        Font buttonFont = new Font("SansSerif", Font.BOLD, 20);
        Color mainColor = new Color(102, 0, 204);         // Violet néon
        Color hoverMainColor = new Color(153, 51, 255);   // Plus clair
        Color yellowColor = new Color(255, 215, 0);
        Color hoverYellowColor = new Color(255, 235, 59);


        rdvButton = createStyledButton("\uD83D\uDCC5 Prendre un rendez-vous", buttonFont, mainColor, hoverMainColor);
        agendaButton = createStyledButton("\uD83D\uDDD3\uFE0F Voir mon agenda", buttonFont, mainColor, hoverMainColor);
        historiqueButton = createStyledButton("\uD83D\uDCDC Voir mon historique", buttonFont, mainColor, hoverMainColor);
        deconnexionButton = createStyledButton("🚪 Se déconnecter", buttonFont, yellowColor, hoverYellowColor);

        formPanel.add(rdvButton);
        formPanel.add(agendaButton);
        formPanel.add(historiqueButton);
        formPanel.add(deconnexionButton);

        containerPanel.add(titre, BorderLayout.NORTH);
        containerPanel.add(formPanel, BorderLayout.CENTER);

        backgroundLabel.add(containerPanel, new GridBagConstraints());

        setContentPane(backgroundLabel);
    }

    private JButton createStyledButton(String text, Font font, Color bgColor, Color hoverColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                super.paintComponent(g2);
                g2.dispose();
            }

            @Override
            protected void paintBorder(Graphics g) {
                // Pas de bordure
            }
        };

        button.setFont(font);
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

    // --- Panel à coins arrondis ---
    static class RoundedPanel extends JPanel {
        private final int radius;

        public RoundedPanel(int radius) {
            this.radius = radius;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    // --- Listeners publics ---
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
