package com.wulian.texturelocaleredirector.mixin;

import com.wulian.texturelocaleredirector.LanguageTextureManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.NamespaceResourceManager;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

@Mixin(NamespaceResourceManager.class)
public abstract class NamespaceResourceManagerMixin implements ResourceManager {
    @Inject(method = "findResources", at = @At("RETURN"))
    private void onFindResources(String startingPath, Predicate<Identifier> allowedPathPredicate, CallbackInfoReturnable<Map<Identifier, Resource>> cir) {
        if (LanguageTextureManager.isReplacingAtlasTextures()) {
            return;
        }

        String currentLang = MinecraftClient.getInstance().getLanguageManager().getLanguage();
        if ("en_us".equals(currentLang)) {
            return;
        }

        Map<Identifier, Resource> originalMap = cir.getReturnValue();
        Map<Identifier, Resource> replacements = new HashMap<>();

        for (Identifier originalId : originalMap.keySet()) {
            Identifier langId = LanguageTextureManager.getLanguageSpecificIdForAtlas(originalId, currentLang);

            if (langId != null) {
                LanguageTextureManager.setReplacingAtlasTextures(true);
                try {
                    this.getResource(langId).ifPresent(langResource -> {
                        replacements.put(originalId, langResource);
                    });
                } finally {
                    LanguageTextureManager.setReplacingAtlasTextures(false);
                }
            }
        }

        if (!replacements.isEmpty()) {
            originalMap.putAll(replacements);
        }
    }
}