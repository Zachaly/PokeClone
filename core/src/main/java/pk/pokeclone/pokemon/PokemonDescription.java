package pk.pokeclone.pokemon;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pk.pokeclone.pokemon.attack.AttackDefinition;
import pk.pokeclone.pokemon.attack.PokemonAttack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PokemonDescription {
    private String name;
    private List<PokemonType> types;
    private PokemonStats baseStats;
    private HashMap<String, String> learnset;

    public ArrayList<PokemonAttack> getAttacksForLevel(int level) {
        ArrayList<PokemonAttack> attacks = new ArrayList<>();

        for(int i = level; i > 0; i--) {
            String attackName = learnset.get(String.valueOf(i));

            if(attackName == null) {
                continue;
            }

            AttackDefinition attackDefinition = AttackDefinition.load(attackName);

            attacks.add(new PokemonAttack(attackDefinition.getMaxUses(), attackDefinition));
            if(attacks.size() == 4) {
                break;
            }
        }

        return attacks;
    }
}
