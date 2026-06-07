package pk.pokeclone.player;

import lombok.Getter;
import pk.pokeclone.item.Item;
import pk.pokeclone.pokemon.Pokemon;

import java.util.*;

@Getter
public class PlayerState {
    private final List<Pokemon> pokemons;
    private final List<Pokemon> boxedPokemons;
    private final List<Item> items;
    private int money = 1000;

    public PlayerState() {
        pokemons = new ArrayList<>();
        items = new ArrayList<>();
        boxedPokemons = new ArrayList<>();
    }

    public boolean addPokemon(Pokemon pokemon) {
        if(pokemons.size() > 5) {
            return false;
        }

        pokemons.add(pokemon);

        return true;
    }

    public <T extends Item> T withdrawItem(Class<T> itemClass) {
        T item = items.stream().filter(itemClass::isInstance)
            .map(itemClass::cast)
            .findFirst()
            .orElse(null);

        if(item != null) {
            items.remove(item);
        }

        return item;
    }

    public Map<Class<? extends Item>, Integer> getItemsCount() {

        HashMap<Class<? extends Item>, Integer> map = new HashMap<>();

        for(Item item : items) {
            map.merge(item.getClass(), 1, Integer::sum);
        }

        return map;
    }

    public void takeMoney(int count) {
        money -= count;
        if(money < 0) {
            money = 0;
        }
    }

    public void addMoney(int count) {
        money += count;
    }
}
