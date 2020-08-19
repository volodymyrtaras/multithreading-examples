package examples.typing;

import examples.typing.gui.CharacterDisplayCanvas;
import examples.typing.handler.CharacterEventHandler;
import examples.typing.listener.CharacterListener;
import examples.typing.source.CharacterSource;
import examples.typing.tasks.RandomCharacterGenerator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SwingTypeTester extends JFrame implements CharacterSource {

    protected RandomCharacterGenerator producer;
    private CharacterDisplayCanvas displayCanvas;
    private CharacterDisplayCanvas feedbackCanvas;
    private JButton quitButton;
    private JButton startButton;
    private JButton stopButton;
    private CharacterEventHandler handler;

    public SwingTypeTester() {
        initComponents();
    }

    private void initComponents() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                quit();
            }
        });
        handler = new CharacterEventHandler();
        displayCanvas = new CharacterDisplayCanvas();
        add(displayCanvas, BorderLayout.NORTH);
        feedbackCanvas = new CharacterDisplayCanvas(this);
        add(feedbackCanvas, BorderLayout.CENTER);
        feedbackCanvas.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent event) {
                char typed = event.getKeyChar();
                if (typed != KeyEvent.CHAR_UNDEFINED) {
                    newCharacter(typed);
                }
            }
        });
        JPanel panel = new JPanel();
        add(panel, BorderLayout.SOUTH);
        startButton = new JButton();
        startButton.setText("Start");
        panel.add(startButton);
        startButton.addActionListener(event -> {
            producer = new RandomCharacterGenerator();
            displayCanvas.setCharacterSource(producer);
            producer.start();
            startButton.setEnabled(false);
            stopButton.setEnabled(true);
            feedbackCanvas.setEnabled(true);
            feedbackCanvas.requestFocus();
        });
        stopButton = new JButton();
        stopButton.setText("Stop");
        panel.add(stopButton);
        stopButton.addActionListener(event -> {
            startButton.setEnabled(true);
            stopButton.setEnabled(false);
            producer.interrupt();
            feedbackCanvas.setEnabled(false);
        });
        quitButton = new JButton();
        quitButton.setText("Quit");
        panel.add(quitButton);
        quitButton.addActionListener(event -> quit());
        pack();
    }

    private void newCharacter(int typed) {
        handler.fireNewCharacter(this, typed);
    }

    private void quit() {
        System.exit(0);
    }

    @Override
    public void addCharacterListener(CharacterListener listener) {
        handler.addCharacterListener(listener);
    }

    @Override
    public void removeCharacterListener(CharacterListener listener) {
        handler.removeCharacterListener(listener);
    }

    public static void main(String[] args) {
        new SwingTypeTester().setVisible(true);
    }
}
