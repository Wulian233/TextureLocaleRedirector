package com.wulian.texturelocaleredirector;


import net.minecraft.util.Identifier;

public class LanguageTextureManager {
    private static final String TEXTURES_PREFIX = "textures/";
    private static final int TEXTURES_PREFIX_LENGTH = TEXTURES_PREFIX.length();

    private static final ThreadLocal<Boolean> IS_REPLACING_ATLAS_TEXTURES = ThreadLocal.withInitial(() -> false);

    public static boolean isReplacingAtlasTextures() {
        return IS_REPLACING_ATLAS_TEXTURES.get();
    }

    public static void setReplacingAtlasTextures(boolean replacing) {
        IS_REPLACING_ATLAS_TEXTURES.set(replacing);
    }

    public static Identifier getLanguageSpecificIdForAtlas(Identifier originalId, String currentLang) {
        if ("en_us".equals(currentLang)) {
            return null;
        }

        String path = originalId.getPath();
        if (path.startsWith(TEXTURES_PREFIX)) {
            return createLanguageSpecificId(originalId, currentLang, path);
        }
        return null;
    }

    private static Identifier createLanguageSpecificId(Identifier original, String language, String path) {
        String langSpecificPath = TEXTURES_PREFIX + language + "/" + path.substring(TEXTURES_PREFIX_LENGTH);
        return Identifier.of(original.getNamespace(), langSpecificPath);
    }
}