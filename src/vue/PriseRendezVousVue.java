package vue;

import modele.Disponibilite;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class PriseRendezVousVue extends JFrame {
    private JTextField specialisationField;
    private JTextField villeField;
    private JTextField dateField;
    private JButton rechercherButton;
    private JButton reserverButton;
    private DefaultListModel<Disponibilite> listeModel;
    private JList<Disponibilite> listeDispos;

    public PriseRendezVousVue() {
        setTitle("Prise de Rendez-vous - EKSASOTE");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Couleurs
        Color bleu = new Color(0x002366);
        Color jaune = new Color(230, 200, 80);

        // Fonts
        Font labelFont = new Font("SansSerif", Font.BOLD, 20);
        Font inputFont = new Font("SansSerif", Font.PLAIN, 18);
        Font buttonFont = new Font("SansSerif", Font.BOLD, 20);

        // Titre
        JLabel titreLabel = new JLabel("Prise de Rendez-vous", SwingConstants.CENTER);
        titreLabel.setFont(new Font("SansSerif", Font.BOLD, 48));
        titreLabel.setForeground(bleu);
        titreLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));

        // Formulaire de recherche
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setPreferredSize(new Dimension(600, 400));
        formPanel.setBackground(new Color(255, 255, 255, 230));
        formPanel.setBorder(BorderFactory.createLineBorder(jaune, 4, true));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1;

        // Spécialisation
        gbc.gridy = 0;
        formPanel.add(createLabel("Spécialisation :", labelFont), gbc);
        gbc.gridy++;
        specialisationField = new JTextField();
        specialisationField.setFont(inputFont);
        formPanel.add(specialisationField, gbc);

        // Ville
        gbc.gridy++;
        formPanel.add(createLabel("Ville :", labelFont), gbc);
        gbc.gridy++;
        villeField = new JTextField();
        villeField.setFont(inputFont);
        formPanel.add(villeField, gbc);

        // Date
        gbc.gridy++;
        formPanel.add(createLabel("Date (yyyy-mm-dd) :", labelFont), gbc);
        gbc.gridy++;
        dateField = new JTextField();
        dateField.setFont(inputFont);
        formPanel.add(dateField, gbc);

        // Boutons
        gbc.gridy++;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setOpaque(false);

        rechercherButton = new JButton("Rechercher");
        rechercherButton.setFont(buttonFont);
        rechercherButton.setBackground(bleu);
        rechercherButton.setForeground(Color.WHITE);
        buttonPanel.add(rechercherButton);

        reserverButton = new JButton("Prendre ce rendez-vous");
        reserverButton.setFont(buttonFont);
        reserverButton.setBackground(jaune);
        reserverButton.setForeground(Color.BLACK);
        buttonPanel.add(reserverButton);

        formPanel.add(buttonPanel, gbc);

        // Liste des disponibilités
        listeModel = new DefaultListModel<>();
        listeDispos = new JList<>(listeModel);
        listeDispos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listeDispos.setFont(new Font("SansSerif", Font.PLAIN, 16));
        JScrollPane scrollPane = new JScrollPane(listeDispos);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Créneaux disponibles"));

        // Conteneur principal
        JPanel container = new JPanel(new BorderLayout(20, 20));
        container.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        container.add(titreLabel, BorderLayout.NORTH);
        container.add(formPanel, BorderLayout.WEST);
        container.add(scrollPane, BorderLayout.CENTER);

        setContentPane(container);
    }

    private JLabel createLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(Color.BLACK);
        return label;
    }

    public String getSpecialisation() {
        return specialisationField.getText().trim();
    }

    public String getVille() {
        return villeField.getText().trim();
    }

    public String getDate() {
        return dateField.getText().trim();
    }

    public void setRechercherListener(ActionListener listener) {
        rechercherButton.addActionListener(listener);
    }

    public void setReserverListener(ActionListener listener) {
        reserverButton.addActionListener(listener);
    }

    public void afficherResultats(List<Disponibilite> disponibilites) {
        listeModel.clear();
        if (disponibilites.isEmpty()) {
            afficherMessage("Aucun créneau trouvé.");
        } else {
            for (Disponibilite d : disponibilites) {
                listeModel.addElement(d);
            }
        }
    }

    public Disponibilite getSelection() {
        return listeDispos.getSelectedValue();
    }

    public void afficherMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public void afficherErreur(String message) {
        JOptionPane.showMessageDialog(this, message, "Erreur", JOptionPane.ERROR_MESSAGE);
    }
}
