package com.wulian.texturelocaleredirector.neoforge;

import com.wulian.texturelocaleredirector.TextureLocaleRedirector;
import net.neoforged.fml.IExtensionPoint.DisplayTest;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;

@Mod(value = TextureLocaleRedirector.MOD_ID)
public class TextureLocaleRedirectorClientNeoForge {
    public TextureLocaleRedirectorClientNeoForge() {
        ModContainer container = ModList.get().getModContainerById(TextureLocaleRedirector.MOD_ID).orElseThrow();
        container.registerExtensionPoint(DisplayTest.class, () -> new DisplayTest(() -> DisplayTest.IGNORESERVERONLY, (a, b) -> true));
    }
}