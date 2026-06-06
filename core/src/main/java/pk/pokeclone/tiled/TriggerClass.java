package pk.pokeclone.tiled;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum TriggerClass {
    MAP_CHANGE("MapChangeTrigger"),
    BATTLE("BattleTrigger"),
    ADD_INTERACTION("AddInteractionTrigger");

    private final String value;
}
