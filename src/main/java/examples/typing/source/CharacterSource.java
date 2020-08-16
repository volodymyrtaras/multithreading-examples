package examples.typing.source;

import examples.typing.listener.CharacterListener;

public interface CharacterSource {

    void addCharacterListener(CharacterListener listener);

    void removeCharacterListener(CharacterListener listener);
}
