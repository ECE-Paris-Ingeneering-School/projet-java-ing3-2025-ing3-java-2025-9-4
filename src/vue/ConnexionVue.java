package vue;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ConnexionVue extends JFrame {
    private JTextField emailField;
    private JPasswordField motDePasseField;
    private JButton connexionButton;
    private JButton inscriptionButton; 

    public ConnexionVue() {
        setTitle("Connexion Patient");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(new JLabel("Email :"));
        emailField = new JTextField();
        panel.add(emailField);

        panel.add(new JLabel("Mot de passe :"));
        motDePasseField = new JPasswordField();
        panel.add(motDePasseField);

        connexionButton = new JButton("Se connecter");
        panel.add(connexionButton);

        inscriptionButton = new JButton("Créer un compte"); 
        panel.add(inscriptionButton);

        add(panel);
    }

    public String getEmail() {
        return emailField.getText();
    }

    public String getMotDePasse() {
        return new String(motDePasseField.getPassword());
    }

    public void afficherErreur(String message) {
        JOptionPane.showMessageDialog(this, message, "Erreur", JOptionPane.ERROR_MESSAGE);
    }

    public void setConnexionListener(ActionListener listener) {
        connexionButton.addActionListener(listener);
    }

    public void setInscriptionListener(ActionListener listener) {
        inscriptionButton.addActionListener(listener);
    }
}
