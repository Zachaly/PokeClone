package pk.pokeclone.pokemon.attack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pk.pokeclone.pokemon.Pokemon;
import pk.pokeclone.pokemon.PokemonType;
import pk.pokeclone.pokemon.StatStages;

import java.util.List;
import java.util.Random;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AttackDefinition {
    private String name;
    private PokemonType type;
    private int power;
    private AttackType attackType;
    private int maxUses;
    private StatusEffect statusEffect = null;
    private List<AttackStatStage> stageEffects;

    public void attack(Pokemon attacker, Pokemon defender) {
        double typeResistance = TypeResistancesUtil.getAttackEffectiveness(type, defender.getDescription().getTypes());
        double damage = getDamage(attacker, defender, typeResistance);

        defender.takeDamage((int)Math.floor(damage));

        if(statusEffect != null) {
            Random rand = new Random();

            if(rand.nextInt(100) < 33 && defender.getStatusEffect() == null) {
                defender.setStatusEffect(statusEffect);
            }
        }

        for(AttackStatStage stageEffect : stageEffects) {
            Pokemon target = AttackStatStageTarget.ATTACKER == stageEffect.getTarget() ? attacker : defender;

            StatStages stages = target.getStatStages();
            int value = stageEffect.getValue();
            switch (stageEffect.getAffectedStat()) {
                case ATTACK -> stages.changeAttack(value);
                case SP_ATTACK -> stages.changeSpAttack(value);
                case DEFENSE -> stages.changeDefense(value);
                case SP_DEFENSE -> stages.changeSpDefense(value);
                case SPEED -> stages.changeSpeed(value);
            }
        }
    }

    private double getDamage(Pokemon attacker, Pokemon defender, double typeResistance) {
        int attack = attackType == AttackType.SPECIAL ? attacker.getSpAttack() : attacker.getAttack();
        int defense = attackType == AttackType.SPECIAL ? defender.getSpDefense() : defender.getDefense();

        if(power == 0) {
            return 0;
        }

        double damage = (((2 * attacker.getLevel()) / 5. + 2.) * power * attack / defense) / 50. + 2;

        double critical = 1;
        double stab = 1;

        if(attacker.getDescription().getTypes().contains(type)) {
            stab = 1.5;
        }

        damage *= critical * stab * typeResistance;
        return damage;
    }

    public static AttackDefinition load(String name)
    {
        Json json = new Json();
        return json.fromJson(AttackDefinition.class, Gdx.files.internal("pokemon/attacks/" + name + ".json"));
    }
}
