import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
public class Morpion extends JFrame {
    private JButton createButton;
    private JPanel joinPanel;
    private JButton joinButton;
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private JPanel trucPanel;
    private JPanel grosPanelJeu;
    private JPanel gamePanel;
    private JPanel gamePanelOption;
    private JButton[] option;
    private JPanel scorePanel;

    private JPanel globalPanel;
    private JLabel infoLabel;

    private JTextField ipField;
    
    private JLabel scoreOLabel;
    private JLabel scoreXLabel;

    private int scoreO;
    private int scoreX;
    
    private JButton[] button;

    private boolean monTour;
    private char instance;
    char[][] board = new char[3][3];

    JPanel panelTour;
    JLabel lblText;

    public Morpion() {
        setTitle("Morpion");
        setSize(400, 460);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        setLayout(new FlowLayout());
        
        globalPanel = new JPanel();
        globalPanel.setLayout(new BorderLayout());

        trucPanel = new JPanel();
        trucPanel.setLayout( new GridLayout(5, 1, 50, 50 ));

        createButton = new JButton("Rejoindre une partie");
        createButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                joinGame();
            }
        });

        trucPanel.add(new JLabel("Jeu du morpion", SwingConstants.CENTER));


        trucPanel.add(createButton);

        this.joinPanel = new JPanel();
        this.joinPanel.setLayout(new GridLayout(2, 1));
        this.joinPanel.add(new JLabel("Adresse IP du serveur à rejoindre:"));
        ipField = new JTextField();

        this.joinPanel.add(ipField);

        trucPanel.add(joinPanel);

        this.infoLabel = new JLabel("Notifications", SwingConstants.CENTER);
        globalPanel.add(infoLabel, BorderLayout.SOUTH);

        this.add(trucPanel);

        grosPanelJeu = new JPanel(new BorderLayout());
        
        gamePanel = new JPanel(new GridLayout(3, 2));
        gamePanel.setPreferredSize(new Dimension(300, 300));
        
        
        
        //Tableau des scores
        scorePanel = new JPanel();
        scorePanel.setLayout(new GridLayout(1, 3));

        scoreOLabel = new JLabel("0", SwingConstants.CENTER);
        scoreOLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        scoreOLabel.setForeground(Color.BLUE);
        scorePanel.add(scoreOLabel);

        JLabel tiretLabel = new JLabel("-", SwingConstants.CENTER);
        tiretLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        scorePanel.add(tiretLabel);

        scoreXLabel = new JLabel("0", SwingConstants.CENTER);
        scoreXLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        scoreXLabel.setForeground(Color.RED);
        scorePanel.add(scoreXLabel);

        


        grosPanelJeu.add(scorePanel, BorderLayout.NORTH);




        gamePanelOption = new JPanel();

        option = new JButton[2];
        
        option[0] = new JButton("Rejouer");
        option[0].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gameStarted();
                writer.println("replay");
            }
        });
        gamePanelOption.add(option[0]);

        option[1] = new JButton("Quitter");
        option[1].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gameEnded();
            }
        });
        gamePanelOption.add(option[1]);
        
        grosPanelJeu.add(gamePanelOption, BorderLayout.SOUTH);
        
        grosPanelJeu.add(gamePanel, BorderLayout.CENTER);

        this.panelTour = new JPanel();
        this.lblText   = new JLabel();
        this.panelTour.add(lblText);
        this.add(panelTour); 
                
        //Créer les boutons
        button = new JButton[9];
        for (int i = 0; 
i < 9; i++) {
            button[i] = new JButton();
            button[i].setOpaque(false);
            button[i].setContentAreaFilled(false);
            button[i].setBorderPainted(true);
            button[i].setPreferredSize(new Dimension(100, 100));
            button[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (monTour) boutonClique(e);
                }
            });
            gamePanel.add(button[i]);
        }

        globalPanel.add(grosPanelJeu, BorderLayout.CENTER);

        setVisible(true);

    }

    private void joinGame() {
        try {
            String serverAddress = ipField.getText();
            System.out.println("Je donne tout pour me connecter à " + serverAddress );
            socket = new Socket(serverAddress, 5001);

            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
            System.out.println("Connecté au serveur");

            instance = reader.readLine().charAt(0);

            System.out.println("Instance: " + instance);

            switch (instance) {
                case 'X':
                    monTour = false;
                    this.lblText.setText("C'est à l'autre de jouer");
                    break;
                case 'O':
                    monTour = true;
                    this.lblText.setText("C'est à vous de jouer");
                    break;
            }

            jouer();

            System.out.println("apanyan");


        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }


    public static void main(String[] args) {
        new Morpion();
    }


    private void jouer()
    {
        
        new Thread(new Runnable() {
            public void run() {
                try {
                    String move;
                    while ((move = reader.readLine()) != null) {
                        if (move.equals("replay"))
                            gameStarted();
                        else
                            autreJoueurAJoue(move);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        gameStarted();
    }

    private boolean checkWin(char player) 
    {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) {
                return true; // Check ligne
            }
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player) {
                return true; // Check colonne
            }
        }
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) {
            return true; // Check diagonale principale
        }
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) {
            return true; // Check diagonale secondaire
        }
        return false;
    }

    public boolean checkDraw()
    {
        for (int i = 0; i < 9; i++) {
            if (board[i / 3][i % 3] == 0) {
                return false;
            }
        }
        return true;
    }

    private void gameEnded()
    {
        remove(globalPanel);
        add(trucPanel);
        System.out.println(instance + " a gagné");
        
        revalidate();
        //repaint();
    }

    private void gameStarted()
    {
        this.infoLabel.setText("");
        this.add(this.globalPanel);
        this.remove(this.trucPanel);
        for (int i = 0; i < 9; i++) {
            button[i].setIcon(new ImageIcon());
            button[i].setEnabled(true);
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = 0;
            }
        }

        switch (instance) {
            case 'X':
                monTour = false;
                this.lblText.setText("C'est à l'autre de jouer");
                break;
            case 'O':
                monTour = true;
                this.lblText.setText("C'est à vous de jouer");
                break;
        }
        
        revalidate();
        //repaint();
    }

    private void boutonClique(ActionEvent e)
    {
        monTour = !monTour;

        JButton clickedButton = (JButton) e.getSource();
        
        clickedButton.setEnabled(false);
        clickedButton.setIcon(new ImageIcon(instance +".png"));
        clickedButton.setDisabledIcon(new ImageIcon(instance +".png"));

        int buttonIndex = gamePanel.getComponentZOrder(clickedButton);
        writer.println(instance + "," + buttonIndex); // Send player's move and button index to the server
        System.out.println(instance + "," + buttonIndex);
        
        board[buttonIndex / 3][buttonIndex % 3] = instance;

        this.lblText.setText("C'est à l'autre de jouer");

        if (checkWin(instance) ) {
            this.lblText.setText("Partie terminée");
            victoire(instance);
            etatBouton(false);
        }
        else
            if (checkDraw()) {
                this.lblText.setText("Partie terminée");
                egalite();
                etatBouton(false);
            }
    }

    private void victoire(char gagnant)
    {
        if (gagnant == 'O') {
            scoreO++;
        } else {
            scoreX++;    
        }
        updateScore();
    }

    private void autreJoueurAJoue(String move)
    {
        monTour = !monTour;
        
        System.out.println("Received move: " + move);
        
        String[] parts = move.split(","); //On retire la virgule
        char instanceRecu = parts[0].charAt(0); // On prend le signe
        int buttonIndex = Integer.parseInt(parts[1]); //On prend la position
        
        JButton button = (JButton) gamePanel.getComponent(buttonIndex); //On sélectionne le bouton
        
        button.setEnabled(false);
        button.setIcon(new ImageIcon(instanceRecu +".png"));
        button.setDisabledIcon(new ImageIcon(instanceRecu +".png"));
        board[buttonIndex / 3][buttonIndex % 3] = instanceRecu;

        this.lblText.setText("C'est à vous de jouer");
            
        if (checkWin(instanceRecu) ) {
            this.lblText.setText("Partie terminée");
            victoire(instanceRecu);
            etatBouton(false);
        }
        else
            if (checkDraw()) {
                this.lblText.setText("Partie terminée");
                egalite();
                etatBouton(false);
            }
    }

    private void updateScore()
    {
        scoreOLabel.setText(Integer.toString(scoreO));
        scoreXLabel.setText(Integer.toString(scoreX));
        revalidate();
        //repaint();
    }

    private void egalite()
    {
        this.infoLabel.setText("Egalité");
    }

    private void etatBouton(boolean b)
    {
        for (int i = 0; i < 9; i++) {
            button[i].setEnabled(b);
        }
    }

    
}