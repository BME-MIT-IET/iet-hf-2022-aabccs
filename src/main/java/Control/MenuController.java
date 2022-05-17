package Control;

import javax.swing.*;
import java.awt.*;

/**
 * Menü kirajzolásáért felel
 */
public class MenuController extends JFrame{

    DialogWindow numOfSettlersFrame;
    boolean started = false;
    int numOfPlayers = 2;


    /**
     * Inicializálás, kirajzolás és mouselistenerek
     */
    public void initialize(){
        this.setBackground(Color.BLACK);
        JPanel panel = new JPanel();
        this.getContentPane().add(panel);
        panel.setLayout(null);
        panel.setBackground(Color.BLACK);

        JButton newgame = new JButton(new ImageIcon("res/ujjj.png"));
        newgame.setBounds(200, 210, 196, 97);
        MenuController forInnerAccess = this;
        newgame.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mousePressed(java.awt.event.MouseEvent evt)
            {
                numOfSettlersFrame = new DialogWindow(forInnerAccess);
            }
        });
        JButton settings = new JButton(new ImageIcon("res/exxx.png"));
        settings.setBounds(240, 330, 116, 83);
        settings.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mousePressed(java.awt.event.MouseEvent evt)
            {
                dispose();
            }
        });

        JLabel szoveg = new JLabel(new ImageIcon("res/naa.png"));
        JLabel hatter = new JLabel(new ImageIcon("res/spa.png"));

        this.setTitle("Projlab");
        this.setSize(600,500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setLocationRelativeTo(null);

        panel.add(szoveg);
        hatter.setBounds(0,0,600,500);
        panel.add(newgame);
        panel.add(settings);
        panel.add(hatter);
        szoveg.setBounds(80, 40,420, 116);

        this.setVisible(true);

    }

    public void StartGame(int n){
        started = true;
        numOfPlayers = n;
        dispose();
    }

    public boolean getStarted(){
        return started;
    }

    public int getNumOfPlayers(){
        return numOfPlayers;
    }
}