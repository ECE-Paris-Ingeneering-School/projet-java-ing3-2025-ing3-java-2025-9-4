package vue;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class AdminDashboardVue extends JFrame {
    private JButton specialistesButton;
    private JButton disponibilitesButton;
    private JButton patientsButton;
    private JButton deconnexionButton;

    public AdminDashboardVue() {
        setTitle("Dashboard Administrateur");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // --- Fond d'écran ---
        BackgroundPanel backgroundLabel = new BackgroundPanel("Images/4.png");

        // --- Couleurs principales ---
        Color grisFonce = new Color(60, 60, 60);
        Color grisFonceHover = new Color(90, 90, 90);
        Color rouge = new Color(208, 56, 56);

        // --- Fonts ---
        Font titleFont = new Font("SansSerif", Font.BOLD, 36);
        Font buttonFont = new Font("SansSerif", Font.BOLD, 18);

        // --- Conteneur principal ---
        JPanel containerPanel = new JPanel(new BorderLayout());
        containerPanel.setOpaque(false);

        // --- Formulaire stylé avec titre inclus ---
        RoundedPanel formPanel = new RoundedPanel(30);
        formPanel.setLayout(new GridLayout(5, 1, 15, 15));
        formPanel.setPreferredSize(new Dimension(600, 500));
        formPanel.setBackground(new Color(255, 255, 255, 220));
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(rouge, 3, true),
                BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));

        // --- Titre ---
        JLabel titre = new JLabel("🏢 Espace Admin 🏢", SwingConstants.CENTER);
        titre.setFont(titleFont);
        titre.setForeground(rouge);
        titre.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formPanel.add(titre);

        // --- Boutons stylés ---
        specialistesButton = createStyledButton("👨‍⚕️ Gérer les spécialistes", buttonFont, grisFonce, grisFonceHover);
        disponibilitesButton = createStyledButton("📅 Gérer les créneaux", buttonFont, grisFonce, grisFonceHover);
        patientsButton = createStyledButton("🤝‍ Gérer les patients", buttonFont, grisFonce, grisFonceHover);
        deconnexionButton = createStyledButton("🚪 Se déconnecter", buttonFont, rouge, rouge.brighter());

        formPanel.add(specialistesButton);
        formPanel.add(disponibilitesButton);
        formPanel.add(patientsButton);
        formPanel.add(deconnexionButton);

        containerPanel.add(formPanel, BorderLayout.CENTER);

        // --- Placement centré ---
        GridBagConstraints containerConstraints = new GridBagConstraints();
        containerConstraints.gridx = 0;
        containerConstraints.gridy = 0;
        containerConstraints.anchor = GridBagConstraints.CENTER;
        backgroundLabel.add(containerPanel, containerConstraints);

        setContentPane(backgroundLabel);
    }

    private JButton createStyledButton(String text, Font font, Color bgColor, Color hoverColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
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
        button.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
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

    // --- Fond d'écran avec image ---
    static class BackgroundPanel extends JPanel {
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

    // --- Listeners publics ---
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
