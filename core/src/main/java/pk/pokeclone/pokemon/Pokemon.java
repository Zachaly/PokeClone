package pk.pokeclone.pokemon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pk.pokeclone.pokemon.attack.PokemonAttack;
import pk.pokeclone.pokemon.attack.StatusEffect;

import java.util.List;

@AllArgsConstructor
@Getter
public class Pokemon {
    private final PokemonDescription description;
    private int level;
    private int health;
    private int pokedexIndex;
    private StatStages statStages;
    private List<PokemonAttack> attacks;
    @Setter
    private StatusEffect statusEffect = null;
    private int experience = 0;

    public Pokemon(int level, PokemonDescription description, int pokedexIndex) {
        this.pokedexIndex = pokedexIndex;
        this.description = description;
        this.level = level;
        health = getMaxHealth();
        statStages = new StatStages();
        attacks = description.getAttacksForLevel(level);
    }

    public static Pokemon load(int pokedexIndex, int level) {
        Json json = new Json();
        PokemonDescription desc = json.fromJson(PokemonDescription.class, Gdx.files.internal(String.format("pokemon\\%d.json", pokedexIndex)));

        return new Pokemon(level, desc, pokedexIndex);
    }

    public int getMaxHealth() {
        return 10 + level + (int)Math.ceil(description.getBaseStats().getHp() * level / 50f);
    }

    public int getAttack() {
        return (int)(getBaseStat(description.getBaseStats().getAttack()) * StatStages.MULTIPLIERS.get(statStages.getAttack()));
    }

    public int getDefense() {
        return (int)(getBaseStat(description.getBaseStats().getDefense()) * StatStages.MULTIPLIERS.get(statStages.getDefense()));
    }

    public int getSpAttack() {
        return (int)(getBaseStat(description.getBaseStats().getSpAttack()) * StatStages.MULTIPLIERS.get(statStages.getSpAttack()));
    }

    public int getSpDefense() {
        return (int)(getBaseStat(description.getBaseStats().getSpDefense()) * StatStages.MULTIPLIERS.get(statStages.getSpDefense()));
    }

    public int getSpeed() {
        return (int)(getBaseStat(description.getBaseStats().getSpeed()) * StatStages.MULTIPLIERS.get(statStages.getSpeed()));
    }

    private int getBaseStat(int stat) {
        return 5 + (stat * level / 50);
    }

    public void takeDamage(int damage) {
        if(health - damage < 0) {
            health = 0;
        }

        health -= damage;
    }

    public void addExperience(int exp) {
        experience += exp;

        int nextLevelCap = (int)Math.ceil(level / 5f) * 100;
        while(experience >= nextLevelCap) {
            level++;
            experience -= nextLevelCap = (int)Math.ceil(level / 5f) * 100;;
            nextLevelCap = (int)Math.ceil(level / 5f) * 100;
        }

        attacks = description.getAttacksForLevel(level);
    }

    public void heal() {
        for(PokemonAttack attack : attacks) {
            attack.setUses(attack.getDefinition().getMaxUses());
        }

        health = getMaxHealth();
        statusEffect = null;
    }

    public void heal(int health) {
        if(this.health == 0) {
            return;
        }

        this.health += health;

        if(this.health > getMaxHealth()) {
            this.health = getMaxHealth();
        }
    }

    public void clearStages() {
        statStages.clear();
    }

    public int expYield() {
        return level * 25;
    }
}
