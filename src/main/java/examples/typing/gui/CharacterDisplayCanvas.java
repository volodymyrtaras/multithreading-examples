package examples.typing.gui;

import examples.typing.event.CharacterEvent;
import examples.typing.listener.CharacterListener;
import examples.typing.source.CharacterSource;

import javax.swing.JComponent;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;

public class CharacterDisplayCanvas extends JComponent implements CharacterListener {

    protected FontMetrics fontMetrics;
    protected char[] tmpChar = new char[1];
    protected int fontHeight;

    public CharacterDisplayCanvas() {
        setFont(new Font("Monospaced", Font.BOLD, 18));
        fontMetrics = Toolkit.getDefaultToolkit().getFontMetrics(getFont());
        fontHeight = fontMetrics.getHeight();
    }

    public CharacterDisplayCanvas(CharacterSource characterSource) {
        this();
        setCharacterSource(characterSource);
    }

    @Override
    public synchronized void newCharacter(CharacterEvent event) {
        tmpChar[0] = (char) event.getCharacter();
        repaint();
    }

    @Override
    protected synchronized void paintComponent(Graphics graphics) {
        Dimension dimension = getSize();
        graphics.clearRect(0, 0, dimension.width, dimension.height);
        if (tmpChar[0] == 0) {
            return;
        }

        int charWidth = fontMetrics.charWidth((int) tmpChar[0]);
        graphics.drawChars(tmpChar, 0, 1, (dimension.width - charWidth) / 2, fontHeight);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(fontMetrics.getMaxAscent() + 10, fontMetrics.getMaxAdvance() + 10);
    }

    public void setCharacterSource(CharacterSource source) {
        source.addCharacterListener(this);
    }
}
