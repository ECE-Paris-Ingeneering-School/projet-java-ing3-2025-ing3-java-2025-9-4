package vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class InscriptionVue extends JFrame {
    private JTextField nomField, prenomField, emailField;
    private JPasswordField motDePasseField;
    private JButton inscrireButton;

    public InscriptionVue() {
        setTitle("Inscription Patient");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 2));
        panel.add(new JLabel("Nom :"));
        nomField = new JTextField();
        panel.add(nomField);

        panel.add(new JLabel("Prénom :"));
        prenomField = new JTextField();
        panel.add(prenomField);

        panel.add(new JLabel("Email :"));
        emailField = new JTextField();
        panel.add(emailField);

        panel.add(new JLabel("Mot de passe :"));
        motDePasseField = new JPasswordField();
        panel.add(motDePasseField);

        inscrireButton = new JButton("S'inscrire");
        panel.add(new JLabel());
        panel.add(inscrireButton);

        add(panel);
    }

    public String getNom() { return nomField.getText(); }
    public String getPrenom() { return prenomField.getText(); }
    public String getEmail() { return emailField.getText(); }
    public String getMotDePasse() { return new String(motDePasseField.getPassword()); }

    public void setInscriptionListener(ActionListener listener) {
        inscrireButton.addActionListener(listener);
    }

    public void afficherMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public void afficherErreur(String message) {
        JOptionPane.showMessageDialog(this, message, "Erreur", JOptionPane.ERROR_MESSAGE);
    }
}
