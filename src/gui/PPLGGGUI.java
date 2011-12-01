package gui;

import javax.swing.*;

import pplgg.Generator;
import pplgg.Map;
import pplgg.PPLGG.Terrain;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PPLGGGUI extends JFrame implements ActionListener {

    MapPanel mapPanel[];
    CloudMapPanel generators[];
    CloudMapThread mapAdders[];
    CloudMapPanel cloudMap;
    
    
    public static void main(String[] args) {
        PPLGGGUI frame = new PPLGGGUI();
        frame.setSize(1600,900); // Standaard = .setSize(0,0) 
        frame.createGUI();
        frame.setVisible(true); // Standaard = setVisible(false)
        frame.setResizable( false );
    }

    void createGUI() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Container window = this.getContentPane();
        this.setLayout( new FlowLayout() );
        
        JLabel pplggLabel = new JLabel("PPLGG");
        pplggLabel.setFont( new Font("Verdana",0, 48) );
        window.add(pplggLabel);
        
        
        
        createButtons();
        createGeneratorGrid();
        
        /*
        mapPanel = new MapPanel[3];
        for (int i = 0; i<3; i++) {
            mapPanel[0] = new MapPanel();
            window.add( mapPanel[i] );
        }
        */
        
        window.setVisible(true);
        window.repaint();
    }

    private void createGeneratorGrid() {
        Generator.width = 180;
        Generator.height = 15;
        JPanel generatorGrid = new JPanel();
        GridLayout layout = new GridLayout(0,2,20,5);
        generatorGrid.setLayout(layout);
        generators = new CloudMapPanel[20];
        for (int i=0; i<generators.length; i++) {
            generators[i] = new CloudMapPanel();
            generatorGrid.add( generators[i] );
        }
        
        this.getContentPane().add( generatorGrid );
    }

    private void createButtons() {
        Container window = this.getContentPane();
        JButton redoButton = new JButton("new Map Samples");
        redoButton.setActionCommand("newmaps");
        redoButton.addActionListener( this );
        window.add( redoButton );
        
        JButton newGeneratorButton = new JButton("New Generator");
        newGeneratorButton.setActionCommand("newgens");
        newGeneratorButton.addActionListener( this );
        window.add( newGeneratorButton );
    }
    
    public void actionPerformed(ActionEvent e) {
        if ("newmaps".equals(e.getActionCommand())) {
        } else if ("newgens".equals(e.getActionCommand())) {
            if (mapAdders!=null) {
                for (int i=0; i<mapAdders.length; i++) {
                    mapAdders[i].stopAdding();
                }
            }
            mapAdders = new CloudMapThread[generators.length];
            for (int i=0; i<generators.length; i++) {
                generators[i].setGenerator( Generator.randomGenerator() );
                generators[i].clear();
                mapAdders[i] = new CloudMapThread(generators[i]);
                mapAdders[i].setPriority( Thread.MIN_PRIORITY );
                mapAdders[i].start();
                //generators[i].addMaps( 50 );
            }
        }
    } 
    
    
    
}


