package examples.typing.tasks;

import examples.typing.handler.CharacterEventHandler;
import examples.typing.listener.CharacterListener;
import examples.typing.source.CharacterProvider;
import examples.typing.source.CharacterSource;

import java.util.Random;

public class RandomCharacterGenerator extends Thread implements CharacterSource, CharacterProvider {

    private volatile boolean done = false;

    private static char[] chars;
    private static final String typingSymbols = "abcdefghijklmnopqrstuvwxyz0123456789";

    static {
        chars = typingSymbols.toCharArray();
    }

    private Random random;
    private CharacterEventHandler handler;

    public RandomCharacterGenerator() {
        random = new Random();
        handler = new CharacterEventHandler();
    }

    @Override
    public void addCharacterListener(CharacterListener listener) {
        handler.addCharacterListener(listener);
    }

    @Override
    public void removeCharacterListener(CharacterListener listener) {
        handler.removeCharacterListener(listener);
    }

    @Override
    public void nextCharacter() {
        handler.fireNewCharacter(this, chars[random.nextInt(chars.length)]);
    }

    @Override
    public void run() {
        while (!done) {
            nextCharacter();

            try {
                Thread.sleep(getPauseTime());
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    public void setDone() {
        done = true;
    }

    private int getPauseTime() {
        return (int) Math.max(1000, 5000 * random.nextDouble());
    }
}
