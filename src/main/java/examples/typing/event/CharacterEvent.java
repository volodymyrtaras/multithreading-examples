package examples.typing.event;

import examples.typing.source.CharacterSource;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CharacterEvent {

    public CharacterSource source;

    public int character;
}
