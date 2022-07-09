import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;

class Tic_Tac_Toi extends JFrame implements ActionListener {
    Container con;
    JMenuBar menuBar;
    JMenu mainMenu;
    JMenuItem menuNewGame, menuResetScore, menuResetGame, menuChangeSigns, menuChangeStrike;
    static JLabel p_1, p_2, pl_1, pl_2, strike;
    JPanel boxsPanel;
    static JButton box[] = new JButton[9];
    SpringLayout sp;
    Font f;

    public static String nm[] = {"P_1", "P_2"};
    static int striker, c, winierStrike, i;
    static int points[] = {0, 0};
    static char sign[] = {'O', 'X'};
    static String ptext1="Player 1("+sign[0]+")  :  ";
    static String ptext2="Player 2("+sign[1]+")  :  ";
    static String ltext1=nm[0]+"  - "+points[0];
    static String ltext2=nm[1]+"  - "+points[1];
    static boolean isGameOn;
    GetNames sn = new GetNames();

    Tic_Tac_Toi() {
        super("TicTacToi - By:Hardip Muliyasiya");
        con = getContentPane();
        sp = new SpringLayout();
        con.setLayout(sp);

        // create menu
        menuBar = new JMenuBar();
        mainMenu = new JMenu("Menu");
        menuNewGame = new JMenuItem("New Game");
        menuResetScore = new JMenuItem("Reset Score");
        menuResetGame = new JMenuItem("Reset Game");
        menuChangeSigns = new JMenuItem("Change Signs");
        menuChangeStrike = new JMenuItem("Change Strike");
        mainMenu.add(menuNewGame);
        mainMenu.add(menuResetScore);
        mainMenu.add(menuResetGame);
        mainMenu.add(menuChangeSigns);
        mainMenu.add(menuChangeStrike);
        menuBar.add(mainMenu);
        setJMenuBar(menuBar);

        menuBar.setBorder(new MatteBorder(0, 0, 2, 0, new Color(242, 242, 242)));
        menuBar.setBackground(new Color(255, 255, 255));
        mainMenu.setBorder(new MatteBorder(1, 1, 1, 1, new Color(100, 100, 100)));
        mainMenu.setMnemonic((int)'M');
        menuNewGame.addActionListener(this);
        menuNewGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Event.CTRL_MASK));
        menuResetScore.addActionListener(this);
        menuResetScore.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, Event.CTRL_MASK + Event.SHIFT_MASK));
        menuResetGame.addActionListener(this);
        menuResetGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, Event.CTRL_MASK));
        menuChangeSigns.addActionListener(this);
        menuChangeSigns.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, Event.CTRL_MASK));
        menuChangeStrike.addActionListener(this);
        menuChangeStrike.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, Event.CTRL_MASK));


        // initialize components
        p_1 = new JLabel(ptext1);
        p_2 = new JLabel(ptext2);
        f = new Font("Sans serif", Font.PLAIN, 20);
        p_1.setFont(f);
        p_2.setFont(f);
        pl_1 = new JLabel(ltext1);
        pl_2 = new JLabel(ltext2);
        f = new Font("Sans serif", Font.BOLD, 20);
        pl_1.setFont(f);
        pl_2.setFont(f);

        boxsPanel = new JPanel(new GridLayout(3, 3));
        boxsPanel.setPreferredSize(new Dimension(500, 500));
        f = new Font("Sans serif", Font.BOLD, 50);
        for(i=0; i<9; i++) {
            box[i] = new JButton(" ");
            box[i].setBackground(Color.CYAN);
            box[i].setFont(f);
            box[i].setFocusPainted(false);
            box[i].addActionListener(this);
            boxsPanel.add(box[i]);
        }

        strike = new JLabel("Strike - " + nm[striker] + " (O)");
        f = new Font("Sans serif", Font.PLAIN, 18);
        strike.setFont(f);


        // add components
        con.add(p_1);
        con.add(p_2);
        con.add(pl_1);
        con.add(pl_2);
        con.add(boxsPanel);
        con.add(strike);
        

        // arrange components
        // p_1
        sp.putConstraint(SpringLayout.WEST, p_1, 15, SpringLayout.WEST, con);
        sp.putConstraint(SpringLayout.NORTH, p_1, 15, SpringLayout.NORTH, con);
        // p_2
        sp.putConstraint(SpringLayout.WEST, p_2, 15, SpringLayout.WEST, con);
        sp.putConstraint(SpringLayout.NORTH, p_2, 5, SpringLayout.SOUTH, p_1);
        // pl_1
        sp.putConstraint(SpringLayout.WEST, pl_1, 0, SpringLayout.EAST, p_1);
        sp.putConstraint(SpringLayout.NORTH, pl_1, 15, SpringLayout.NORTH, con);
        // pl_2
        sp.putConstraint(SpringLayout.WEST, pl_2, 0, SpringLayout.EAST, p_2);
        sp.putConstraint(SpringLayout.NORTH, pl_2, 5, SpringLayout.SOUTH, pl_1);
        // boxsPanel
        sp.putConstraint(SpringLayout.WEST, boxsPanel, 15, SpringLayout.WEST, con);
        sp.putConstraint(SpringLayout.NORTH, boxsPanel, 80, SpringLayout.SOUTH, p_2);
        // strike
        sp.putConstraint(SpringLayout.WEST, strike, 15, SpringLayout.WEST, con);
        sp.putConstraint(SpringLayout.NORTH, strike, 10, SpringLayout.SOUTH, boxsPanel);
    }

    public static void main(String args[]) {
        Tic_Tac_Toi t = new Tic_Tac_Toi();
        t.setDefaultCloseOperation(EXIT_ON_CLOSE);
        t.setSize(800, 800);
        t.setIconImage(new ImageIcon("ttt_icon.png").getImage());
        t.setVisible(true);
        // t.setResizable(false);
        t.setLocationRelativeTo(null);

        t.mainMenu.requestFocus();
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == menuNewGame) {
            striker=0;
            sn.showNameDialog(this);
        }
        else if(e.getSource() == menuResetScore) {
            points[0] = points[1] = 0;
            resetLabels();
        }
        else if(e.getSource() == menuResetGame) {
            resetGame();
        }
        else if(e.getSource() == menuChangeSigns) {
            changeSigns();
        }
        else if(e.getSource() == menuChangeStrike) {
            if(isGameOn) {
                JOptionPane.showMessageDialog(this, "Strike can't changed during game.");
                return;
            }
            changeStrike();
        }
        else {
            JButton b = (JButton)e.getSource();
            if(b.getText().compareTo(" ")==0) {
                if(!isGameOn)
                    isGameOn=true;
                boxClicked((JButton) e.getSource());
            }
            else
                JOptionPane.showMessageDialog(this, "This box is already selected!!!");
        }
    }

    static void resetLabels() {
        ltext1="Player 1("+sign[0]+")  :  ";
        ltext2="Player 2("+sign[1]+")  :  ";
        ptext1=nm[0]+"  - "+points[0];
        ptext2=nm[1]+"  - "+points[1];
        p_1.setText(ltext1);
        p_2.setText(ltext2);
        pl_1.setText(ptext1);
        pl_2.setText(ptext2);
        strike.setText("Strike - " + nm[striker] + " ("+sign[striker]+")");
    }

    static void resetGame() {
        for(i=0; i<9; i++) {
            box[i].setText(" ");
            box[i].setBackground(Color.CYAN);
        }
        striker=winierStrike;
        c=0;
        isGameOn=false;
        resetLabels();
    }

    private void changeSigns() {
        if(isGameOn) {
            JOptionPane.showMessageDialog(this, "Signs can't changed during game.");
            return;
        }

        if(sign[0] == 'O') {
            sign[0] = 'X';
            sign[1] = 'O';
        }
        else {
            sign[0] = 'O';
            sign[1] = 'X';
        }
        sn.l1.setText("Player 1("+sign[0]+") :              ");
        sn.l2.setText("Player 2("+sign[1]+") :              ");
        resetLabels();
    }

    private void changeStrike() {
        if(striker==0)
            striker=1;
        else
            striker=0;
        resetLabels();
    }

    private void boxClicked(JButton b) {
        b.setText(sign[striker]+"");
        if(checkForWin()) {
            JOptionPane.showMessageDialog(this, nm[striker]+" won!");
            points[striker]++;
            winierStrike=striker;
            resetGame();
            return;
        }
        if(c++ == 8) {
            JOptionPane.showMessageDialog(this, "Match draw!");
            resetGame();
        }
        changeStrike();
    }

    private boolean checkForWin() {
        if(box[0].getText().equals(box[1].getText()) && box[1].getText().equals(box[2].getText()) && !box[2].getText().equals(" ")) {
            box[0].setBackground(Color.GREEN);
            box[1].setBackground(Color.GREEN);
            box[2].setBackground(Color.GREEN);
            return true;
        }
        else if(box[3].getText().equals(box[4].getText()) && box[4].getText().equals(box[5].getText()) && !box[5].getText().equals(" ")) {
            box[3].setBackground(Color.GREEN);
            box[4].setBackground(Color.GREEN);
            box[5].setBackground(Color.GREEN);
            return true;
        }
        else if(box[6].getText().equals(box[7].getText()) && box[7].getText().equals(box[8].getText()) && !box[8].getText().equals(" ")) {
            box[6].setBackground(Color.GREEN);
            box[7].setBackground(Color.GREEN);
            box[8].setBackground(Color.GREEN);
            return true;
        }
        else if(box[0].getText().equals(box[3].getText()) && box[3].getText().equals(box[6].getText()) && !box[6].getText().equals(" ")) {
            box[0].setBackground(Color.GREEN);
            box[3].setBackground(Color.GREEN);
            box[6].setBackground(Color.GREEN);
            return true;
        }
        else if(box[1].getText().equals(box[4].getText()) && box[4].getText().equals(box[7].getText()) && !box[7].getText().equals(" ")) {
            box[1].setBackground(Color.GREEN);
            box[4].setBackground(Color.GREEN);
            box[7].setBackground(Color.GREEN);
            return true;
        }
        else if(box[2].getText().equals(box[5].getText()) && box[5].getText().equals(box[8].getText()) && !box[8].getText().equals(" ")) {
            box[2].setBackground(Color.GREEN);
            box[5].setBackground(Color.GREEN);
            box[8].setBackground(Color.GREEN);
            return true;
        }
        else if(box[0].getText().equals(box[4].getText()) && box[4].getText().equals(box[8].getText()) && !box[8].getText().equals(" ")) {
            box[0].setBackground(Color.GREEN);
            box[4].setBackground(Color.GREEN);
            box[8].setBackground(Color.GREEN);
            return true;
        }
        else if(box[2].getText().equals(box[4].getText()) && box[4].getText().equals(box[6].getText()) && !box[6].getText().equals(" ")) {
            box[2].setBackground(Color.GREEN);
            box[4].setBackground(Color.GREEN);
            box[6].setBackground(Color.GREEN);
            return true;
        }
        return false;
    }
}

class GetNames extends JFrame implements ActionListener {
    Container con;
    JLabel l1, l2, space;
    JTextField t1, t2;
    JButton b;
    Font f;

    GetNames() {
        super("TicTacToi - By:Hardip Muliyasiya");
        setIconImage(new ImageIcon("ttt_icon.png").getImage());
        con = getContentPane();
        con.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 15));

        l1 = new JLabel("Player 1("+Tic_Tac_Toi.sign[0]+") :              ");
        l2 = new JLabel("Player 2("+Tic_Tac_Toi.sign[1]+") :              ");
        space = new JLabel("                                                                   ");
        t1 = new JTextField(10);
        t1.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                t1.selectAll();
            }
        });
        t2 = new JTextField(10);
        t2.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                t2.selectAll();
            }
        });
        b = new JButton("Save");
        b.addActionListener(this);

        con.add(l1);
        con.add(t1);
        con.add(l2);
        con.add(t2);
        con.add(space);
        con.add(b);

        f = new Font("Sans serif", Font.PLAIN, 15);
        l1.setFont(f);
        l2.setFont(f);
    }

    public void actionPerformed(ActionEvent e) {
        Tic_Tac_Toi.nm[0] = t1.getText();
        Tic_Tac_Toi.nm[1] = t2.getText();
        Tic_Tac_Toi.sign[0] = 'O';
        Tic_Tac_Toi.sign[1] = 'X';
        Tic_Tac_Toi.points[0]=Tic_Tac_Toi.points[1]=0;
        Tic_Tac_Toi.strike.setText("Strike - "+t1.getText() + " (O)");

        Tic_Tac_Toi.resetLabels();
        Tic_Tac_Toi.resetGame();
        dispose();
    }

    public Insets getInsets() {
       return new Insets(50, 30, 30, 30);
    }

    public void showNameDialog(Component t) {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(313, 220);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(t);
    }    
}
