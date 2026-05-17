package pk.pokeclone.asset;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Disposable;

public class AssetService implements Disposable {
    private final AssetManager assetManager;

    public AssetService(FileHandleResolver fileHandleResolver) {
        assetManager = new AssetManager(fileHandleResolver);
        assetManager.setLoader(TiledMap.class, new TmxMapLoader());
    }

    public <T> T load(Asset<T> asset) {
        assetManager.load(asset.getDescriptor());
        assetManager.finishLoading();
        return assetManager.get(asset.getDescriptor());
    }

    public <T> void queue(Asset<T> asset) {
        assetManager.load(asset.getDescriptor());
    }

    public <T> T get(Asset<T> asset) {
        return assetManager.get(asset.getDescriptor());
    }

    public <T> void unload(Asset<T> asset) {
        assetManager.unload(asset.getDescriptor().fileName);
    }

    public boolean update() {
        return assetManager.update();
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }
}
