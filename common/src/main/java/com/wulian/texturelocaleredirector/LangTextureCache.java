package com.wulian.texturelocaleredirector;

import net.minecraft.util.Identifier;

import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LangTextureCache {

    private static final Map<Identifier, SoftReference<Boolean>> existsCache = new ConcurrentHashMap<>();
    private static volatile String currentLanguage = "en_us"; // 默认

    public static Boolean get(Identifier id) {
        SoftReference<Boolean> ref = existsCache.get(id);
        return ref != null ? ref.get() : null;
    }

    public static void put(Identifier id, boolean exists) {
        existsCache.put(id, new SoftReference<>(exists));
    }

    public static void clear() {
        existsCache.clear();
    }

    public static void setCurrentLanguage(String lang) {
        currentLanguage = lang;
    }

    public static String getCurrentLanguage() {
        return currentLanguage;
    }
}
