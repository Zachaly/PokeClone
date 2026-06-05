package pk.pokeclone.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pk.pokeclone.pokemon.Pokemon;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public abstract class Item {
    private String name;

    public abstract boolean use(Pokemon pokemon);
}
