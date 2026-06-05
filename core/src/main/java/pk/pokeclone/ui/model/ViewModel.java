package pk.pokeclone.ui.model;

import pk.pokeclone.PokeClone;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public abstract class ViewModel {
    protected final PokeClone game;
    protected final PropertyChangeSupport propertyChangeSupport;

    public ViewModel(PokeClone game) {
        this.game = game;
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    public <T> void onPropertyChange(String propName, Class<T> propType, OnPropertyChange<T> consumer) {
        this.propertyChangeSupport.addPropertyChangeListener(propName, evt -> {
            consumer.onChange(propType.cast(evt.getNewValue()));
        });
    }

    public void clearPropertyChanged() {
        for(PropertyChangeListener listener : this.propertyChangeSupport.getPropertyChangeListeners()){
            this.propertyChangeSupport.removePropertyChangeListener(listener);
        }
    }

    @FunctionalInterface
    public interface OnPropertyChange<T> {
        void onChange(T value);
    }
}
