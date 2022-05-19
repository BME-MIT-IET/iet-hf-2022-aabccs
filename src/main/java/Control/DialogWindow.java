package Control;

import javax.swing.*;
import java.awt.*;

/**
 * Felugró ablak, mely bekéri a Settlerek számát
 */
public class DialogWindow extends JFrame {

    MenuController parent;

    public DialogWindow(MenuController parent){
        this.parent = parent;
        initialize();
    }


    public void initialize(){
        this.setBackground(Color.BLACK);
        JPanel panel = new JPanel();
        this.getContentPane().add(panel);
        panel.setLayout(new FlowLayout());

        JLabel inst = new JLabel("Add meg a settlerek szamat!");
        JTextField numberField = new JTextField("2",2);
        JButton okButton = new JButton("OK");
        okButton.addMouseListener(new java.awt.event.MouseAdapter()
        {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt)
            {
                try{
                    int val = Integer.parseInt(numberField.getText());
                    dispose();
                    parent.StartGame(val);
                }catch (Exception e){
                    System.out.println(e);
                }
            }
        });

        this.setSize(250,100);
        this.setResizable(false);
        panel.add(inst);
        panel.add(numberField);
        panel.add(okButton);
        setDefaultCloseOperation(0);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
