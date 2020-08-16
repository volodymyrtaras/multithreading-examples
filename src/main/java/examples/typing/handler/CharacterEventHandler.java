package examples.typing.handler;

import examples.typing.event.CharacterEvent;
import examples.typing.listener.CharacterListener;
import examples.typing.source.CharacterSource;

import java.util.Vector;

public class CharacterEventHandler {

    private Vector<CharacterListener> listeners = new Vector<>();

    public void addCharacterListener(CharacterListener listener) {
        listeners.add(listener);
    }

    public void removeCharacterListener(CharacterListener listener) {
        listeners.remove(listener);
    }

    public void fireNewCharacter(CharacterSource source, int character) {
        CharacterEvent event = new CharacterEvent(source, character);
        CharacterListener[] listenersArr = this.listeners.toArray(new CharacterListener[0]);

        for (CharacterListener listener : listenersArr) {
            listener.newCharacter(event);
        }
    }
}
