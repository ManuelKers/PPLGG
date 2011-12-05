package gui;

import ga.GeneticAlgorithm;
import ga.Individual;
import ga.Population;

import javax.swing.*;

import mario.MarioLevel;
import mario.MarioWindow;

import dk.itu.mario.engine.MarioComponent;
import dk.itu.mario.engine.PlayCustomized;
import dk.itu.mario.level.Level;

import pplgg.Generator;
import pplgg.Map;
import pplgg.PPLGG.Terrain;
import pplgg.ga.BlindFitness;
import pplgg.ga.GeneratorIndividual;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PPLGGGUI extends JFrame implements ActionListener {

    JButton newGeneratorButton;
    JButton redoButton;
    JButton nextGenButton;
    MapPanel mapSamples[];
    CloudMapPanel generators[];
    CloudMapThread mapAdders[];
    CloudMapPanel inspectedGenerator;
    private JPanel generatorGrid;
    private JPanel inspectGrid;
    private Population gaPop;
    private BlindFitness fitFunc;
    private static final int noGenerators = 20;

    MarioComponent marioComponent;


    private enum State {
        OVERVIEW,
        INSPECT;
    }

    public static void main(String[] args) {
        PPLGGGUI frame = new PPLGGGUI();
        frame.setSize(1600,800); // Standaard = .setSize(0,0) 
        frame.createGUI();
        frame.setVisible(true); // Standaard = setVisible(false)
        frame.setResizable( false );
        frame.startGA();
    }

    private void startGA() {
        gaPop = new Population(20);
        gaPop.initializeRandomly();
        setGeneratorsToPopulation();
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
        
        JLabel creditsLabel = new JLabel("Created by Jeppe Tuxen and Manuel Kerssemakers @ ITU");
        creditsLabel.setFont( new Font("Verdana",0, 10) );
        window.add(creditsLabel);

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
        Generator.height = 14;
        generatorGrid = new JPanel();
        GridLayout layout = new GridLayout(0,2,20,5);
        generatorGrid.setLayout(layout);
        generators = new CloudMapPanel[noGenerators];
        for (int i=0; i<generators.length; i++) {
            generators[i] = new CloudMapPanel(this);
            generatorGrid.add( generators[i] );
        }

        this.getContentPane().add( generatorGrid );
    }

    private void newMapSamples() {
        for (int i=0; i<mapSamples.length; i++) {
            mapSamples[i].setMap( inspectedGenerator.getGenerator().generateMap( 0 ));
        }
    }

    public void inspect( CloudMapPanel cloudMapPanel ) {
        newGeneratorButton.setEnabled(false);
        redoButton.setEnabled(true);

        inspectedGenerator = cloudMapPanel;
        System.out.println(cloudMapPanel.getGenerator().toString());
        inspectGrid = new JPanel();
        GridLayout layout = new GridLayout(0,2,20,5);
        inspectGrid.setLayout(layout);


        //find index of inspected generator
        int index;
        for (index=0; index<generators.length; index++) {
            if (generators[index]==cloudMapPanel)
                break;
        }

        mapSamples = new MapPanel[noGenerators-1];
        int count = 0;
        for (int i=0; i<generators.length; i++) {
            if (i==index) 
                inspectGrid.add( cloudMapPanel );
            else {
                mapSamples[count] = new MapPanel(this);
                inspectGrid.add( mapSamples[count] );
                count++;
            }
        }
        newMapSamples();

        this.getContentPane().remove( generatorGrid );
        //this.getContentPane().add( inspectGrid );
        this.getContentPane().add( inspectGrid, getContentPane().getComponentCount()-1 );
        validate();
    }

    public void viewOverView() {
        newGeneratorButton.setEnabled(true);
        redoButton.setEnabled(false);
        if (marioFrame!= null)
            closeMario();
        generatorGrid.removeAll();
        for (int i=0; i<generators.length; i++) {
            generatorGrid.add( generators[i] );
        }
        this.getContentPane().remove( inspectGrid );
        this.getContentPane().add( generatorGrid, getContentPane().getComponentCount()-1 );
        validate();
    } 

    private void createButtons() {
        Container window = this.getContentPane();
        redoButton = new JButton("new Map Samples");
        redoButton.setActionCommand("newmaps");
        redoButton.addActionListener( this );
        redoButton.setEnabled( false );
        window.add( redoButton );

        newGeneratorButton = new JButton("New Generators");
        newGeneratorButton.setActionCommand("newgens");
        newGeneratorButton.addActionListener( this );
        window.add( newGeneratorButton );
        
        nextGenButton = new JButton("Next Generation");
        nextGenButton.setActionCommand("nextgen");
        nextGenButton.addActionListener( this );
        window.add( nextGenButton );
    }

    @Override
    public void repaint() {
        super.repaint();
        System.out.println("Repainting "+Math.random());
    }

    public void actionPerformed(ActionEvent e) {
        if ("newmaps".equals(e.getActionCommand())) {
            if (marioFrame!= null)
                closeMario();
            newMapSamples();
        } else if ("newgens".equals(e.getActionCommand())) {
            
        } else if ("nextgen".equals(e.getActionCommand())) {
            nextGeneration();
        }
    }
    
    private void setGeneratorsToPopulation() {
        if (mapAdders!=null) {
            for (int i=0; i<mapAdders.length; i++) {
                mapAdders[i].stopAdding();
            }
        }
        ArrayList<Individual> individuals = gaPop.getIndividuals();
        mapAdders = new CloudMapThread[generators.length];
        for (int i=0; i<generators.length; i++) {
            generators[i].setGenerator( ((GeneratorIndividual)individuals.get( i )).getGenerator() );
            generators[i].clear();
            mapAdders[i] = new CloudMapThread(generators[i]);
            mapAdders[i].setPriority( Thread.MIN_PRIORITY );
            mapAdders[i].start();
        }
    }
    
    private void nextGeneration() {
        for (int i=0; i<generators.length; i++) 
            if (generators[i].isSelected()) {
                gaPop.setIndividualFitness( i, 1 );
                generators[i].deselect();
            }
        gaPop.nextGeneration();
        setGeneratorsToPopulation();
    }

    public static MarioWindow marioFrame;

    public void playMario(MapPanel mapPanel)
    {
        this.setAlwaysOnTop( false );
        if (marioFrame!= null)
            closeMario();

        marioFrame = new MarioWindow(mapPanel);

    }
    private void closeMario() {
        marioFrame.mario.stop();
        marioFrame.dispose();
    }

}


