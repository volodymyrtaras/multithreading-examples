package examples.typing;

import examples.typing.gui.CharacterDisplayCanvas;
import examples.typing.handler.CharacterEventHandler;
import examples.typing.listener.CharacterListener;
import examples.typing.source.CharacterSource;
import examples.typing.tasks.RandomCharacterGenerator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
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
    private CharacterEventHandler handler;

    public SwingTypeTester() {
        initComponents();
    }

    private void initComponents() {
        handler = new CharacterEventHandler();
        displayCanvas = new CharacterDisplayCanvas();
        feedbackCanvas = new CharacterDisplayCanvas(this);
        quitButton = new JButton();
        startButton = new JButton();
        add(displayCanvas, BorderLayout.NORTH);
        add(feedbackCanvas, BorderLayout.CENTER);
        JPanel panel = new JPanel();
        quitButton.setText("Quit");
        startButton.setText("Start");
        panel.add(quitButton);
        panel.add(startButton);
        add(panel, BorderLayout.SOUTH);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                quit();
            }
        });
        feedbackCanvas.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent event) {
                char typed = event.getKeyChar();
                if (typed != KeyEvent.CHAR_UNDEFINED) {
                    newCharacter(typed);
                }
            }
        });
        startButton.addActionListener(event -> {
            producer = new RandomCharacterGenerator();
            displayCanvas.setCharacterSource(producer);
            producer.start();
            startButton.setEnabled(false);
            feedbackCanvas.setEnabled(true);
            feedbackCanvas.requestFocus();
        });
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
