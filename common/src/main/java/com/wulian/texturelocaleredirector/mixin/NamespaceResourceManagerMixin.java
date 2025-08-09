package com.wulian.texturelocaleredirector.mixin;

import com.wulian.texturelocaleredirector.TextureLocaleRedirector;
import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.NamespaceResourceManager;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

@Mixin(NamespaceResourceManager.class)
public abstract class NamespaceResourceManagerMixin {

    @Unique
    private static final MinecraftClient textureLocaleRedirector$client = MinecraftClient.getInstance();

    @Inject(method = "findResources", at = @At("RETURN"))
    private void onFindResources(String startingPath, Predicate<Identifier> allowedPathPredicate, CallbackInfoReturnable<Map<Identifier, Resource>> cir) {
        String currentLang = textureLocaleRedirector$client.getLanguageManager().getLanguage();
        if ("en_us".equals(currentLang) || !startingPath.startsWith("textures/")) {
            return;
        }

        Map<Identifier, Resource> originalResources = cir.getReturnValue();
        Map<Identifier, Resource> langSpecificResources = new HashMap<>();
        String texturePrefix = "textures/";
        int prefixLength = texturePrefix.length();

        for (Map.Entry<Identifier, Resource> entry : originalResources.entrySet()) {
            Identifier originalId = entry.getKey();
            String originalPath = originalId.getPath();

            if (originalPath.endsWith(".mcmeta")) {
                continue;
            }

            String langSpecificPath = texturePrefix + currentLang + '/' + originalPath.substring(prefixLength);
            Identifier langId = IdentifierAccessor.create(originalId.getNamespace(), langSpecificPath);

            try {
                Optional<Resource> langResource = ((ResourceManager) this).getResource(langId);
                langResource.ifPresent(resource -> {
                    langSpecificResources.put(originalId, resource);
                    TextureLocaleRedirector.LOGGER.info("Redirected texture: {} -> {}", originalId, langId);
                });
            } catch (Exception ignored) {
            }
        }

        //noinspection ConstantConditions
        if (!langSpecificResources.isEmpty()) {
            originalResources.putAll(langSpecificResources);
        }
    }
}