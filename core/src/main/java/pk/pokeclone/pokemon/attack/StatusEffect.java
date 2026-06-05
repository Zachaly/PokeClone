package pk.pokeclone.pokemon.attack;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pk.pokeclone.pokemon.Pokemon;

@AllArgsConstructor
@Getter
public class StatusEffect {
    public static String POISON = "POISON";
    public static String BURN = "BURN";

    private String name;

    void effect(Pokemon target) {
        target.takeDamage(target.getMaxHealth() / 8);
    }
}
