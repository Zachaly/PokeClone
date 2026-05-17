package pk.pokeclone.asset;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public enum MapAsset implements Asset<TiledMap> {
    START("startmap.tmx");

    private final AssetDescriptor<TiledMap> descriptor;

    MapAsset(String name) {
        TmxMapLoader.Parameters parameters = new TmxMapLoader.Parameters();
        parameters.projectFilePath = "maps/gamemaps.tiled-project";
        descriptor = new AssetDescriptor<>("maps/" + name, TiledMap.class, parameters);
    }

    @Override
    public AssetDescriptor<TiledMap> getDescriptor() {
        return this.descriptor;
    }
}
