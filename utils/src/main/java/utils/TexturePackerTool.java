package utils;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class TexturePackerTool {
    public static void main(String[] args) {
        String inputDir = "assets_raw/objects";
        String outputDir = "assets/graphics";
        String packageFile = "objects.atlas";

        TexturePacker.process(inputDir, outputDir, packageFile);
    }
}
