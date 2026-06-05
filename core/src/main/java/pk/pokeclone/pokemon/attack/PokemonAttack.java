package pk.pokeclone.pokemon.attack;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pk.pokeclone.pokemon.Pokemon;

@AllArgsConstructor
@Getter
@Setter
public class PokemonAttack {
    private int uses;
    private AttackDefinition definition;

    public void use(Pokemon attacker, Pokemon target) {
        uses--;
        definition.attack(attacker, target);
    }
}
