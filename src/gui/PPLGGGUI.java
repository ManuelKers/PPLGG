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
    CloudMapPanel cloudMap;
    Generator gen;
    
    public static void main(String[] args) {
        PPLGGGUI frame = new PPLGGGUI();
        frame.setSize(1200,600); // Standaard = .setSize(0,0) 
        frame.createGUI();
        frame.setVisible(true); // Standaard = setVisible(false)
    }

    void createGUI() {
        //JFrame.setDefaultLookAndFeelDecorated(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Anders blijft het programma op de achtergrond lopen tot je ctrl-alt-delete typt
        Container window = this.getContentPane();
        GridLayout layout = new GridLayout(0,1,0,5);
        window.setLayout(layout);
        JLabel pplggLabel = new JLabel("PPLGG");
        pplggLabel.setFont( new Font("Verdana",0, 48) );
        window.add(pplggLabel);
        
        Generator.width = 150;
        Generator.height = 15;
        //gen  = Generator.randomGenerator();
        
        createButtons(window);
        
        cloudMap = new CloudMapPanel();
        window.add( cloudMap );
        
        mapPanel = new MapPanel[3];
        mapPanel[0] = new MapPanel();
        mapPanel[1] = new MapPanel();
        mapPanel[2] = new MapPanel();
        for (int i = 0; i<3; i++) {
            window.add( mapPanel[i] );
        }
        
        window.setVisible(true);
        window.repaint();
    }

    private void createButtons(Container window) {
        JButton redoButton = new JButton("new Map Samples");
        redoButton.setActionCommand("newmaps");
        redoButton.addActionListener( this );
        window.add( redoButton );
        
        JButton newGeneratorButton = new JButton("New Generator");
        newGeneratorButton.setActionCommand("newgen");
        newGeneratorButton.addActionListener( this );
        window.add( newGeneratorButton );
    }
    
    public void actionPerformed(ActionEvent e) {
        if ("newmaps".equals(e.getActionCommand())) {
            mapPanel[0].setMap(gen.generateMap( 0 ));
            mapPanel[1].setMap(gen.generateMap( 0 ));
            mapPanel[2].setMap(gen.generateMap( 0 ));
        } else if ("newgen".equals(e.getActionCommand())) {
            gen = Generator.randomGenerator();
            cloudMap.setGenerator( gen );
            cloudMap.clear();
            cloudMap.addMaps(80);
        }
    } 
    
    
    
}


