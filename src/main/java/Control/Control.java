package Control;

import Model.*;
import View.ViewController;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;

public class Control extends JFrame{

    Game game;
    ViewController viewController;

    Canvas canvas;
    JPanel buttonPanel;
    JPanel listPanel;

    JLabel materialsLabel;
    JLabel teleportgateslabel;
    JLabel neighboursLabel;
    JLabel sunCounterLabel;

    private DefaultListModel<Material> materialListModel;
    private DefaultListModel<TeleportGate> teleportGateListModel;
    private DefaultListModel<Location> neighbourListModel;

    JList<Material> materialList;
    JList<TeleportGate> teleportGateList;
    JList<Location> neighbourList;

    JScrollPane materialScroll;
    JScrollPane teleportScroll;
    JScrollPane neighbourScroll;

    JButton moveButton;
    JButton mineButton;
    JButton drillButton;
    JButton hideButton;
    JButton placeButton;
    JButton teleportButton;
    JButton buildRobotButton;
    JButton buildTeleportGateButton;
    JButton exitButton;

    Point startPoint = new Point();

    /**
     * Inicializálás
     */
    public void initialize(){

        canvas = new Canvas();

        this.setLayout(new BorderLayout());
        controlElements();

        this.add(listPanel, BorderLayout.WEST);
        this.add(buttonPanel, BorderLayout.SOUTH);
        this.add(canvas, BorderLayout.CENTER);

        this.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
        this.setResizable(false);
        this.setUndecorated(true);
        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }

    public void controlElements(){
        sunCounterLabel = new JLabel("0");
        sunCounterLabel.setSize(120, 40);
        sunCounterLabel.setForeground(Color.yellow);
        setSunCounterLabel();

        listPanel = new JPanel(new GridLayout(6,1));
        listPanel.setBackground(Color.black);
        listPanel.setBorder(new LineBorder(new Color(168, 114, 208), 5));

        materialListModel = new DefaultListModel<>();
        teleportGateListModel = new DefaultListModel<>();
        neighbourListModel = new DefaultListModel<>();

        teleportGateList = new JList<>();
        teleportGateList.setBackground(Color.BLACK);
        teleportGateList.setForeground(Color.white);
        teleportGateList.setSelectionBackground(new Color(168, 114, 208));
        teleportGateList.setSelectionForeground(Color.BLACK);

        materialList = new JList<>();
        materialList.setBackground(Color.BLACK);
        materialList.setForeground(Color.white);
        materialList.setSelectionBackground(new Color(168, 114, 208));
        materialList.setSelectionForeground(Color.BLACK);

        neighbourList = new JList<>();
        neighbourList.setBackground(Color.BLACK);
        neighbourList.setForeground(Color.white);
        neighbourList.setSelectionBackground(new Color(168, 114, 208));
        neighbourList.setSelectionForeground(Color.BLACK);

        /**
         * Bal oldali panel feliratok betöltése
         */
        materialsLabel = new JLabel(new ImageIcon( new ImageIcon("res/materials1.png").getImage().getScaledInstance(262,170,0)));
        teleportgateslabel = new JLabel(new ImageIcon( new ImageIcon("res/teleportgates1.png").getImage().getScaledInstance(262,170,0)));
        neighboursLabel = new JLabel(   new ImageIcon( new ImageIcon("res/neighbours1.png").getImage().getScaledInstance(262,170,0)));

        /**
         * Material-ok listái scroll Panel
         */
        materialScroll = new JScrollPane(materialList);
        materialScroll.setBackground(Color.BLACK);
        materialScroll.setBorder(BorderFactory.createEmptyBorder());
        materialScroll.getVerticalScrollBar().setBackground(Color.BLACK);
        materialScroll.getVerticalScrollBar().setForeground(Color.BLACK);

        /**
         * TeleportGate-k listái scroll Panel
         */
        teleportScroll = new JScrollPane(teleportGateList);
        teleportScroll.setBackground(Color.BLACK);
        teleportScroll.setBorder(BorderFactory.createEmptyBorder());
        teleportScroll.getVerticalScrollBar().setBackground(Color.BLACK);
        teleportScroll.getVerticalScrollBar().setForeground(Color.BLACK);

        /**
         * Neighbour-k listái scroll Panel
         */
        neighbourScroll = new JScrollPane(neighbourList);
        neighbourScroll.setBackground(Color.BLACK);
        neighbourScroll.setBorder(BorderFactory.createEmptyBorder());
        neighbourScroll.getVerticalScrollBar().setBackground(Color.BLACK);
        neighbourScroll.getVerticalScrollBar().setForeground(Color.BLACK);


        //Listák eseménykezelői
        materialList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                teleportGateList.clearSelection();
            }
        });

        teleportGateList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                materialList.clearSelection();
            }
        });

        listPanel.add(materialsLabel);
        listPanel.add(materialScroll);
        listPanel.add(teleportgateslabel);
        listPanel.add(teleportScroll);
        listPanel.add(neighboursLabel);
        listPanel.add(neighbourScroll);

        buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.setBorder(new LineBorder(new Color(168, 114, 208), 5));

        /**
         * Move button betöltése, paraméterek beállítása
         */
        moveButton = new JButton(new ImageIcon( new ImageIcon("res/move1.png").getImage().getScaledInstance(120,40,0)));
        moveButton.setBackground(Color.BLACK);
        moveButton.setBorderPainted(false);
        moveButton.setSize(10, 10);

        /**
         * Mine button betöltése, paraméterek beállítása
         */
        mineButton = new JButton(new ImageIcon( new ImageIcon("res/mine1.png").getImage().getScaledInstance(120,40,0)));
        mineButton.setBackground(Color.BLACK);
        mineButton.setBorderPainted(false);
        mineButton.setSize(10, 10);

        /**
         * Hide button betöltése, paraméterek beállítása
         */
        hideButton = new JButton(new ImageIcon( new ImageIcon("res/hide1.png").getImage().getScaledInstance(120,40,0)));
        hideButton.setBackground(Color.BLACK);
        hideButton.setBorderPainted(false);
        hideButton.setSize(10, 10);

        /**
         * Place button betöltése, paraméterek beállítása
         */
        placeButton = new JButton(new ImageIcon( new ImageIcon("res/place1.png").getImage().getScaledInstance(120,40,0)));
        placeButton.setBackground(Color.BLACK);
        placeButton.setBorderPainted(false);
        placeButton.setSize(10, 10);

        /**
         * Drill button betöltése, paraméterek beállítása
         */
        drillButton = new JButton(new ImageIcon( new ImageIcon("res/drill1.png").getImage().getScaledInstance(120,40,0)));
        drillButton.setBackground(Color.BLACK);
        drillButton.setBorderPainted(false);
        drillButton.setSize(10, 10);

        /**
         * Teleport button betöltése, paraméterek beállítása
         */
        teleportButton = new JButton(new ImageIcon( new ImageIcon("res/teleport1.png").getImage().getScaledInstance(120,40,0)));
        teleportButton.setBackground(Color.BLACK);
        teleportButton.setBorderPainted(false);
        teleportButton.setSize(10, 10);

        /**
         * BuildRobot button betöltése, paraméterek beállítása
         */
        buildRobotButton = new JButton(new ImageIcon( new ImageIcon("res/buildrobot1.png").getImage().getScaledInstance(120,40,0)));
        buildRobotButton.setBackground(Color.BLACK);
        buildRobotButton.setBorderPainted(false);
        buildRobotButton.setSize(10, 10);

        /**
         * BuildTeleportGate button betöltése, paraméterek beállítása
         */
        buildTeleportGateButton = new JButton(new ImageIcon( new ImageIcon("res/buildteleportgate1.png").getImage().getScaledInstance(120,40,0)));
        buildTeleportGateButton.setBackground(Color.BLACK);
        buildTeleportGateButton.setBorderPainted(false);
        buildTeleportGateButton.setSize(10, 10);

        /**
         * Exit button betöltése, paraméterek beállítása
         */
        exitButton = new JButton(new ImageIcon( new ImageIcon("res/exit1.png").getImage().getScaledInstance(120,40,0)));
        exitButton.setBackground(Color.BLACK);
        exitButton.setBorderPainted(false);
        exitButton.setSize(10, 10);


        //Gombok eseménykezelői

        /**
         * Move button kattintásra reagál
         */
        moveButton.addMouseListener(new java.awt.event.MouseAdapter()
        {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt)
            {
                if(game.getCurrentSettler() == null) return;
                Location dest = neighbourList.getSelectedValue();
                if(dest != null){
                    game.getCurrentSettler().Move(dest);
                    game.setCurrentSettler(null);
                }
            }
        });

        /**
         * Mine button kattintásra reagál
         */
        mineButton.addMouseListener(new java.awt.event.MouseAdapter()
        {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt)
            {
                if(game.getCurrentSettler() == null) return;
                game.getCurrentSettler().Mine();
                game.setCurrentSettler(null);
            }
        });

        /**
         * Hide button kattintásra reagál
         */
        hideButton.addMouseListener(new java.awt.event.MouseAdapter()
        {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt)
            {
                if(game.getCurrentSettler() == null) return;
                game.getCurrentSettler().Hide();
                game.setCurrentSettler(null);
            }
        });

        /**
         * Place button kattintásra reagál
         */
        placeButton.addMouseListener(new java.awt.event.MouseAdapter()
        {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt)
            {
                if(game.getCurrentSettler() == null) return;
                Material material = materialList.getSelectedValue();
                TeleportGate teleportGate = teleportGateList.getSelectedValue();
                if(material != null || teleportGate != null){
                    if(material != null) game.getCurrentSettler().PlaceMaterial(material);
                    else game.getCurrentSettler().PlaceTeleportGate(teleportGate);
                    game.setCurrentSettler(null);
                }
            }
        });

        /**
         * Drill button kattintásra reagál
         */
        drillButton.addMouseListener(new java.awt.event.MouseAdapter()
        {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt)
            {
                if(game.getCurrentSettler() == null) return;
                game.getCurrentSettler().Drill();
                game.setCurrentSettler(null);
            }
        });

        /**
         * Teleport button kattintásra reagál
         */
        teleportButton.addMouseListener(new java.awt.event.MouseAdapter()
        {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt)
            {
                if(game.getCurrentSettler() == null) return;
                game.getCurrentSettler().Teleport();
                game.setCurrentSettler(null);
            }
        });

        /**
         * BuildRobot button kattintásra reagál
         */
        buildRobotButton.addMouseListener(new java.awt.event.MouseAdapter()
        {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt)
            {
                if(game.getCurrentSettler() == null) return;
                game.getCurrentSettler().BuildRobot();
                game.setCurrentSettler(null);
            }
        });

        /**
         * BuildTeleportGate button kattintásra reagál
         */
        buildTeleportGateButton.addMouseListener(new java.awt.event.MouseAdapter()
        {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt)
            {
                if(game.getCurrentSettler() == null) return;
                game.getCurrentSettler().BuildTeleportGate();
                game.setCurrentSettler(null);
            }
        });

        /**
         * Exit button kattintásra reagál
         */
        exitButton.addMouseListener(new java.awt.event.MouseAdapter()
        {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt)
            {
                game.EndGame();
                game.setCurrentSettler(null);
                dispose();
            }
        });

        /**
         * Scroll érzékelésére zoom in/zoom out
         */
        canvas.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {
                    int notches = e.getWheelRotation();
                    double rot = e.getScrollAmount();
                    if(notches > 0) rot = -rot;
                    viewController.Scale(rot * 0.01);
                    viewController.DrawAll();
                }
            }
        });

        /**
         * Mouse Pressed a pályán
         */
        canvas.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    if(e.getButton() == MouseEvent.BUTTON3){
                        viewController.ResetTransformations();
                        viewController.DrawAll();
                    }else{
                        startPoint = e.getPoint();
                    }
                }
            });

        /**
         * Draggel lehet a pályát mozgatni
         */
        canvas.addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseDragged(MouseEvent e) {
                int dx = (e.getX() - startPoint.x) / 50;
                int dy =  (e.getY() - startPoint.y) / 50;
                viewController.Translate(new Point(dx, dy));
                viewController.DrawAll();
            }


            /**
            * Interfész implementálás miatt üres
            */
            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });


        //Panelekhez gombok hozzáadása

        buttonPanel.add(sunCounterLabel);
        buttonPanel.add(moveButton);
        buttonPanel.add(mineButton);
        buttonPanel.add(drillButton);
        buttonPanel.add(hideButton);
        buttonPanel.add(placeButton);
        buttonPanel.add(teleportButton);
        buttonPanel.add(buildRobotButton);
        buttonPanel.add(buildTeleportGateButton);
        buttonPanel.add(exitButton);

    }

    /**
     * Setter
     * Listek beállítása
     */
    public void setLists(){
        materialListModel.clear();
        teleportGateListModel.clear();
        neighbourListModel.clear();

        Settler currentSettler = game.getCurrentSettler();

        for(Material m : currentSettler.getInventory()){
            materialListModel.addElement(m);
        }

        for(TeleportGate tg : currentSettler.GetTeleportGate()){
            teleportGateListModel.addElement(tg);
        }

        for(Location loc : currentSettler.getLocation().GetNeighbours()){
            neighbourListModel.addElement(loc);
        }

        materialList.setModel(materialListModel);
        teleportGateList.setModel(teleportGateListModel);
        neighbourList.setModel(neighbourListModel);
    }

    /**
     * Sunstormig hátralévő kör kiírása
     */
    public void setSunCounterLabel(){
        sunCounterLabel.setText("Until sunstorm: " + Integer.toString(game.getSpace().GetSun().getCounter()));
    }

    /**
     * Setter
     * @param g beállítani kívánt Game
     */
    public void setGame(Game g){
        game = g;
    }

    /**
     * Setter
     * @param vc beállítani kívánt ViewController
     */
    public void setViewController(ViewController vc){
        viewController = vc;
    }

    /**
     * Getter
     * @return canvas lekérése
     */
    public Canvas getCanvas(){
        return canvas;
    }

}
