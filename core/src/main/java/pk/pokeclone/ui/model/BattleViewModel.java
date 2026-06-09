package pk.pokeclone.ui.model;

import lombok.Getter;
import lombok.Setter;
import pk.pokeclone.PokeClone;
import pk.pokeclone.item.Antidote;
import pk.pokeclone.item.Pokeball;
import pk.pokeclone.item.Potion;
import pk.pokeclone.pokemon.Pokemon;
import pk.pokeclone.pokemon.attack.AttackDefinition;
import pk.pokeclone.pokemon.attack.PokemonAttack;
import pk.pokeclone.pokemon.attack.StatusEffect;
import pk.pokeclone.screen.GameScreen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BattleViewModel extends ViewModel {
    @Getter
    private Pokemon currentPlayerPokemon;
    @Getter
    private final Pokemon attackerPokemon;
    @Setter
    private Pokemon swapPokemon = null;
    @Getter
    private boolean forceSwap = false;
    @Setter
    private Rerender rerender;
    @Getter
    private final List<String> battleCommunicates = new ArrayList<>();
    @Setter
    private PokemonAttack playerAttack;

    private final Random random = new Random();

    public BattleViewModel(PokeClone game, Pokemon attackerPokemon) {
        super(game);
        this.attackerPokemon = attackerPokemon;

        for(Pokemon pokemon : game.getPlayerState().getPokemons()) {
            if(pokemon.getHealth() > 0) {
                currentPlayerPokemon = pokemon;
                break;
            }
        }
    }

    private void statusEffectTick() {
        if(currentPlayerPokemon.getStatusEffect() != null) {
            int hpPrev = currentPlayerPokemon.getHealth();
            currentPlayerPokemon.getStatusEffect().effect(currentPlayerPokemon);
            battleCommunicates.add(String.format("%s lost %d hp due to %s",
                currentPlayerPokemon.getDescription().getName(),
                hpPrev - currentPlayerPokemon.getHealth(),
                currentPlayerPokemon.getStatusEffect().getName()));
        }

        if(attackerPokemon.getStatusEffect() != null) {
            int hpPrev = attackerPokemon.getHealth();
            attackerPokemon.getStatusEffect().effect(attackerPokemon);
            battleCommunicates.add(String.format("%s lost %d hp due to %s",
                attackerPokemon.getDescription().getName(),
                hpPrev - attackerPokemon.getHealth(),
                attackerPokemon.getStatusEffect().getName()));
        }
    }

    private boolean checkPokemonStatus() {
        if(currentPlayerPokemon.getHealth() < 1) {
            battleCommunicates.add(String.format("%s fainted", currentPlayerPokemon.getDescription().getName()));
            if(getPlayerPokemons().stream().allMatch(p -> p.getHealth() < 1)) {
                game.battleEnded(null, 0);
                return true;
            }
            forceSwap = true;
        }
        if(attackerPokemon.getHealth() < 1) {
            game.battleEnded(currentPlayerPokemon, attackerPokemon.expYield());
        }

        return false;
    }

    private AttackDefinition randomEnemyAttack() {
        List<PokemonAttack> attacks = attackerPokemon.getAttacks();

        return attacks.get(random.nextInt(0, attacks.size())).getDefinition();
    }

    public List<Pokemon> getPlayerPokemons() {
        return game.getPlayerState().getPokemons();
    }

    public List<PokemonAttack> getCurrentAttacks() {
        return currentPlayerPokemon.getAttacks();
    }


    public void battleRound(PlayerAction playerAction) {
        battleCommunicates.clear();
        if(playerAction == PlayerAction.HEAL) {
            if(currentPlayerPokemon.getHealth() >= currentPlayerPokemon.getMaxHealth()) {
                return;
            }

            Potion potion = game.getPlayerState().withdrawItem(Potion.class);

            if(potion == null) {
                return;
            }

            potion.use(currentPlayerPokemon);

            battleCommunicates.add(String.format("%s healed to %d hp",
                currentPlayerPokemon.getDescription().getName(),
                currentPlayerPokemon.getHealth()));
        } else if(playerAction == PlayerAction.UNPOISON) {
            if(currentPlayerPokemon.getStatusEffect() == null
                || !StatusEffect.POISON.equals(currentPlayerPokemon.getStatusEffect().getName())) {
                return;
            }

            Antidote antidote = game.getPlayerState().withdrawItem(Antidote.class);

            if(antidote == null) {
                return;
            }

            antidote.use(currentPlayerPokemon);

            battleCommunicates.add(String.format("%s healed POISON status",
                currentPlayerPokemon.getDescription().getName()));
        } else if(playerAction == PlayerAction.CATCH) {
            Pokeball pokeball = game.getPlayerState().withdrawItem(Pokeball.class);
            if(pokeball == null) {
                return;
            }

            if(pokeball.use(attackerPokemon)) {
                game.getPlayerState().addPokemon(attackerPokemon);

                game.battleEnded(currentPlayerPokemon, 0);
                return;
            }

            battleCommunicates.add(String.format("%s broke from pokeball", attackerPokemon.getDescription().getName()));
        } else if(playerAction == PlayerAction.SWAP) {
            if(swapPokemon == null || swapPokemon.getHealth() < 1) {
                return;
            }

            forceSwap = false;
            currentPlayerPokemon = swapPokemon;
            swapPokemon = null;
        } else if(playerAction == PlayerAction.ATTACK) {

            if(currentPlayerPokemon.getSpeed() > attackerPokemon.getSpeed()) {
                int attackerHpPrev = attackerPokemon.getHealth();
                playerAttack.getDefinition().attack(currentPlayerPokemon, attackerPokemon);
                playerAttack.setUses(playerAttack.getUses() - 1);

                battleCommunicates.add(String.format("%s used %s and dealt %d damage",
                    currentPlayerPokemon.getDescription().getName(),
                    playerAttack.getDefinition().getName(),
                    attackerHpPrev - attackerPokemon.getHealth()));

                if(checkPokemonStatus()) {
                    return;
                }

                AttackDefinition enemyAttack = randomEnemyAttack();

                int playerHpPrev = currentPlayerPokemon.getHealth();
                enemyAttack.attack(attackerPokemon, currentPlayerPokemon);

                battleCommunicates.add(String.format("%s used %s and dealt %d damage",
                    attackerPokemon.getDescription().getName(),
                    enemyAttack.getName(),
                    playerHpPrev - currentPlayerPokemon.getHealth()));
            } else {

                AttackDefinition enemyAttack = randomEnemyAttack();

                int playerHpPrev = currentPlayerPokemon.getHealth();
                enemyAttack.attack(attackerPokemon, currentPlayerPokemon);
                battleCommunicates.add(String.format("%s used %s and dealt %d damage",
                    attackerPokemon.getDescription().getName(),
                    enemyAttack.getName(),
                    playerHpPrev - currentPlayerPokemon.getHealth()));

                if(checkPokemonStatus()) {
                    return;
                }

                int attackerHpPrev = attackerPokemon.getHealth();
                playerAttack.getDefinition().attack(currentPlayerPokemon, attackerPokemon);
                battleCommunicates.add(String.format("%s used %s and dealt %d damage",
                    currentPlayerPokemon.getDescription().getName(),
                    playerAttack.getDefinition().getName(),
                    attackerHpPrev - attackerPokemon.getHealth()));

                playerAttack.setUses(playerAttack.getUses() - 1);
            }
        }

        if(checkPokemonStatus()) {
            return;
        }

        if(playerAction != PlayerAction.ATTACK) {
            AttackDefinition enemyAttack = randomEnemyAttack();

            int playerHpPrev = currentPlayerPokemon.getHealth();
            enemyAttack.attack(attackerPokemon, currentPlayerPokemon);
            battleCommunicates.add(String.format("%s used %s and dealt %d damage",
                attackerPokemon.getDescription().getName(),
                enemyAttack.getName(),
                playerHpPrev - currentPlayerPokemon.getHealth()));
        }

        if(checkPokemonStatus()) {
            return;
        }

        statusEffectTick();

        if(checkPokemonStatus()) {
            return;
        }

        rerender.rerender();
    }


    public void run() {
        game.setScreen(GameScreen.class);
    }

    @FunctionalInterface
    public interface Rerender {
        void rerender();
    }

    public enum PlayerAction {
        ATTACK,
        HEAL,
        UNPOISON,
        CATCH,
        SWAP
    }
}
