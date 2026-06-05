package pk.pokeclone.asset;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import lombok.Getter;

public enum SkinAsset implements Asset<Skin> {
    DEFAULT("skin.json"),
    INVENTORY("inventory-skin.json");
    @Getter
    private final AssetDescriptor<Skin> descriptor;

    SkinAsset(String skinJson) {
        this.descriptor = new AssetDescriptor<>("ui/" + skinJson, Skin.class);
    }
}
