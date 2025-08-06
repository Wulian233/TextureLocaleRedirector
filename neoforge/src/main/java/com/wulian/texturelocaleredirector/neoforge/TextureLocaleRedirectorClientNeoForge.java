package com.wulian.texturelocaleredirector.neoforge;

import com.wulian.texturelocaleredirector.TextureLocaleRedirector;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLLoader;


@Mod(value = TextureLocaleRedirector.MOD_ID, dist = Dist.CLIENT)
public class TextureLocaleRedirectorClientNeoForge {
    public TextureLocaleRedirectorClientNeoForge() {
        if (FMLLoader.getDist().isClient()) {

        }
    }
}