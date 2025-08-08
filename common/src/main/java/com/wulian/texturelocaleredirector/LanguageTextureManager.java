package com.wulian.texturelocaleredirector;

import net.minecraft.util.Identifier;

public class LanguageTextureManager {
    private static final String TEXTURES_PREFIX = "textures/";

    private static final ThreadLocal<Boolean> IS_REPLACING_ATLAS_TEXTURES = ThreadLocal.withInitial(() -> false);

    public static boolean isReplacingAtlasTextures() {
        return IS_REPLACING_ATLAS_TEXTURES.get();
    }

    public static void setReplacingAtlasTextures(boolean replacing) {
        IS_REPLACING_ATLAS_TEXTURES.set(replacing);
    }

    public static Identifier getLanguageSpecificIdForAtlas(Identifier originalId, String currentLang) {
        if ("en_us".equals(currentLang) || !originalId.getPath().startsWith(TEXTURES_PREFIX)) {
            return null;
        }

        String originalPath = originalId.getPath();
        String langSpecificPath = TEXTURES_PREFIX + currentLang + "/" + originalPath.substring(TEXTURES_PREFIX.length());

        return Identifier.of(originalId.getNamespace(), langSpecificPath);
    }
}