package pk.pokeclone.pokemon;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class StatStages {
    public static Map<Integer, Double> MULTIPLIERS = Map.ofEntries(
        Map.entry(-6, 0.25),
        Map.entry(-5, 0.28),
        Map.entry(-4, 0.33),
        Map.entry(-3, 0.4),
        Map.entry(-2, 0.5),
        Map.entry(0, 1.0),
        Map.entry(-1, 0.66),
        Map.entry(1, 1.5),
        Map.entry(2, 2.0),
        Map.entry(3, 2.5),
        Map.entry(4, 3.0),
        Map.entry(5, 3.5),
        Map.entry(6, 4.0)
    );

    private int attack = 0;
    private int defense = 0;
    private int spAttack = 0;
    private int spDefense = 0;
    private int speed = 0;

    public void changeAttack(int stage) {
        if(attack + stage > 6) {
            attack = 6;
            return;
        }
        if(attack + stage < -6) {
            attack = -6;
            return;
        }

        attack += stage;
    }

    public void changeDefense(int stage) {
        if(defense + stage > 6) {
            defense = 6;
            return;
        }
        if(defense + stage < -6) {
            defense = -6;
            return;
        }

        defense += stage;
    }

    public void changeSpAttack(int stage) {
        if(spAttack + stage > 6) {
            spAttack = 6;
            return;
        }
        if(spAttack + stage < -6) {
            spAttack = -6;
            return;
        }

        spAttack += stage;
    }

    public void changeSpDefense(int stage) {
        if(spDefense + stage > 6) {
            spDefense = 6;
            return;
        }
        if(spDefense + stage < -6) {
            spDefense = -6;
            return;
        }

        spDefense += stage;
    }

    public void changeSpeed(int stage) {
        if(speed + stage > 6) {
            speed = 6;
            return;
        }
        if(speed + stage < -6) {
            speed = -6;
            return;
        }

        speed += stage;
    }

    public void clear() {
        spAttack = 0;
        attack = 0;
        defense = 0;
        spDefense = 0;
        speed = 0;
    }
}
