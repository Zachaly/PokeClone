package pk.pokeclone.ui.model;

import pk.pokeclone.PokeClone;

import java.beans.PropertyChangeSupport;

public abstract class ViewModel {
    protected final PokeClone game;
    protected final PropertyChangeSupport propertyChangeSupport;

    public ViewModel(PokeClone game) {
        this.game = game;
        propertyChangeSupport = new PropertyChangeSupport(this);
    }
}
