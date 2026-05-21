package pk.pokeclone.pokemon;

import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

public class TypeResistancesUtil {

    static Map<PokemonType, List<PokemonType>> superEffective = Map.ofEntries(
        entry(PokemonType.NORMAL, List.of()),
        entry(PokemonType.FIRE, List.of(PokemonType.GRASS, PokemonType.ICE, PokemonType.BUG, PokemonType.STEEL)),
        entry(PokemonType.WATER, List.of(PokemonType.FIRE, PokemonType.GROUND, PokemonType.ROCK)),
        entry(PokemonType.ELECTRIC, List.of(PokemonType.WATER, PokemonType.FLYING)),
        entry(PokemonType.GRASS, List.of(PokemonType.WATER, PokemonType.GROUND, PokemonType.ROCK)),
        entry(PokemonType.ICE, List.of(PokemonType.GRASS, PokemonType.GROUND, PokemonType.FLYING, PokemonType.DRAGON)),
        entry(PokemonType.FIGHTING, List.of(PokemonType.NORMAL, PokemonType.ICE, PokemonType.ROCK, PokemonType.DARK, PokemonType.STEEL)),
        entry(PokemonType.POISON, List.of(PokemonType.GRASS)),
        entry(PokemonType.GROUND, List.of(PokemonType.FIRE, PokemonType.ELECTRIC, PokemonType.POISON, PokemonType.ROCK, PokemonType.STEEL)),
        entry(PokemonType.FLYING, List.of(PokemonType.GRASS, PokemonType.FIGHTING, PokemonType.BUG)),
        entry(PokemonType.PSYCHIC, List.of(PokemonType.FIGHTING, PokemonType.POISON)),
        entry(PokemonType.BUG, List.of(PokemonType.GRASS, PokemonType.PSYCHIC, PokemonType.DARK)),
        entry(PokemonType.ROCK, List.of(PokemonType.FIRE, PokemonType.ICE, PokemonType.FLYING, PokemonType.BUG)),
        entry(PokemonType.GHOST, List.of(PokemonType.PSYCHIC, PokemonType.GHOST)),
        entry(PokemonType.DRAGON, List.of(PokemonType.DRAGON)),
        entry(PokemonType.DARK, List.of(PokemonType.PSYCHIC, PokemonType.GHOST)),
        entry(PokemonType.STEEL, List.of(PokemonType.ICE, PokemonType.ROCK))
    );

    static Map<PokemonType, List<PokemonType>> notEffective = Map.ofEntries(
        entry(PokemonType.NORMAL, List.of(PokemonType.ROCK, PokemonType.STEEL)),
        entry(PokemonType.FIRE, List.of(PokemonType.FIRE, PokemonType.WATER, PokemonType.ROCK, PokemonType.DRAGON)),
        entry(PokemonType.WATER, List.of(PokemonType.WATER, PokemonType.GRASS, PokemonType.DRAGON)),
        entry(PokemonType.ELECTRIC, List.of(PokemonType.ELECTRIC, PokemonType.GRASS, PokemonType.DRAGON)),
        entry(PokemonType.GRASS, List.of(PokemonType.FIRE, PokemonType.GRASS, PokemonType.POISON, PokemonType.FLYING, PokemonType.BUG, PokemonType.DRAGON, PokemonType.STEEL)),
        entry(PokemonType.ICE, List.of(PokemonType.FIRE, PokemonType.WATER, PokemonType.ICE, PokemonType.STEEL)),
        entry(PokemonType.FIGHTING, List.of(PokemonType.POISON, PokemonType.FLYING, PokemonType.PSYCHIC, PokemonType.BUG)),
        entry(PokemonType.POISON, List.of(PokemonType.POISON, PokemonType.GROUND, PokemonType.ROCK, PokemonType.GHOST)),
        entry(PokemonType.GROUND, List.of(PokemonType.GRASS, PokemonType.BUG)),
        entry(PokemonType.FLYING, List.of(PokemonType.ELECTRIC, PokemonType.ROCK, PokemonType.STEEL)),
        entry(PokemonType.PSYCHIC, List.of(PokemonType.PSYCHIC, PokemonType.STEEL)),
        entry(PokemonType.BUG, List.of(PokemonType.FIRE, PokemonType.FIGHTING, PokemonType.POISON, PokemonType.FLYING, PokemonType.GHOST, PokemonType.STEEL)),
        entry(PokemonType.ROCK, List.of(PokemonType.FIGHTING, PokemonType.GROUND, PokemonType.STEEL)),
        entry(PokemonType.GHOST, List.of(PokemonType.DARK)),
        entry(PokemonType.DRAGON, List.of(PokemonType.STEEL)),
        entry(PokemonType.DARK, List.of(PokemonType.FIGHTING, PokemonType.DARK)),
        entry(PokemonType.STEEL, List.of(PokemonType.FIRE, PokemonType.WATER, PokemonType.ELECTRIC, PokemonType.STEEL))
    );

    static float getAttackEffectiveness(PokemonType attackType, List<PokemonType> defenderTypes) {
        if((attackType == PokemonType.ELECTRIC && defenderTypes.contains(PokemonType.GROUND)) ||
            ((attackType == PokemonType.NORMAL && defenderTypes.contains(PokemonType.GHOST)) || (attackType == PokemonType.GHOST && defenderTypes.contains(PokemonType.NORMAL))) ||
            (attackType == PokemonType.GROUND && defenderTypes.contains(PokemonType.FLYING)) ||
            (attackType == PokemonType.PSYCHIC && defenderTypes.contains(PokemonType.DARK)) ||
            (attackType == PokemonType.FIGHTING && defenderTypes.contains(PokemonType.GHOST)) ||
            (attackType == PokemonType.POISON && defenderTypes.contains(PokemonType.STEEL))) {
            return 0;
        }

        float multiplier = 1;

        for(PokemonType t : defenderTypes) {
            if(superEffective.get(attackType).contains(t)) {
                multiplier *= 2f;
            }
            if(notEffective.get(attackType).contains(t)) {
                multiplier *= 0.5f;
            }
        }

        return multiplier;
    }
}
