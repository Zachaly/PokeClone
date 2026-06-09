package pk.pokeclone.ui.view;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import pk.pokeclone.ui.model.ViewModel;

public abstract class View<T extends ViewModel> extends Table {

    protected final Stage stage;
    protected final Skin skin;
    protected final T viewModel;

    public View(Stage stage, Skin skin, T viewModel) {
        super(skin);
        this.stage = stage;
        this.skin = skin;
        this.viewModel = viewModel;
        setupUi();
    }

    protected abstract void setupUi();

    @Override
    protected void setStage(Stage stage) {
        super.setStage(stage);
    }

    public static void onClick(Actor actor, OnEventConsumer consumer) {
        actor.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                consumer.onEvent();
            }
        });
    }

    @FunctionalInterface
    public interface OnEventConsumer {
        void onEvent();
    }
}
