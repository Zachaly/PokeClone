package pk.pokeclone.pokemon.attack;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AttackStatStage {
    public enum AttackStatStageStat {
        SPEED,
        ATTACK,
        DEFENSE,
        SP_DEFENSE,
        SP_ATTACK
    }

    private AttackStatStageStat affectedStat;
    private AttackStatStageTarget target;
    private int value;
}
