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

import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Mixin(NamespaceResourceManager.class)
public abstract class NamespaceResourceManagerMixin implements ResourceManager {

    @Inject(method = "findResources", at = @At("RETURN"))
    private void onFindResources(String startingPath, Predicate<Identifier> allowedPathPredicate, CallbackInfoReturnable<Map<Identifier, Resource>> cir) {
        String currentLang = MinecraftClient.getInstance().getLanguageManager().getLanguage();
        if (LanguageTextureManager.isReplacingAtlasTextures() || "en_us".equals(currentLang)) {
            return;
        }

        Map<Identifier, Resource> originalResources = cir.getReturnValue();

        LanguageTextureManager.setReplacingAtlasTextures(true);
        try {
            Map<Identifier, Resource> replacements = originalResources.keySet().stream()
                    .map(originalId -> {
                        Identifier langId = LanguageTextureManager.getLanguageSpecificIdForAtlas(originalId, currentLang);
                        if (langId != null) {
                            return this.getResource(langId).map(langResource -> Map.entry(originalId, langResource)).orElse(null);
                        }
                        return null;
                    })
                    .filter(java.util.Objects::nonNull)
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

            if (!replacements.isEmpty()) {
                originalResources.putAll(replacements);
            }
        } finally {
            LanguageTextureManager.setReplacingAtlasTextures(false);
        }
    }
}