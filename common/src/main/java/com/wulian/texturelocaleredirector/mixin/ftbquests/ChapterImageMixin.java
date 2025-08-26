package com.wulian.texturelocaleredirector.mixin.ftbquests;

import com.wulian.texturelocaleredirector.LangTextureCache;
import com.wulian.texturelocaleredirector.TextureLocaleRedirector;
import dev.ftb.mods.ftblibrary.icon.Icon;
import dev.ftb.mods.ftbquests.quest.ChapterImage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value = ChapterImage.class, remap = false)
public abstract class ChapterImageMixin {

    @ModifyVariable(
            method = "setImage(Ldev/ftb/mods/ftblibrary/icon/Icon;)Ldev/ftb/mods/ftbquests/quest/ChapterImage;",
            at = @At("HEAD"),
            argsOnly = true
    )
    private Icon injectLocalizedImage(Icon image) {
        String currentLang = LangTextureCache.getCurrentLanguage();

        if ("en_us".equals(currentLang) || image == null) {
            return image;
        }

        String originalPath = image.toString();
        String texturePrefix = "textures/";
        String textureInsert = currentLang + "/";

        if (originalPath.contains(texturePrefix + currentLang)) {
            TextureLocaleRedirector.LOGGER.warn("{} already exists in {} path.", currentLang, originalPath);
            TextureLocaleRedirector.LOGGER.info("ChapterImage icon {}", originalPath);
            return image;
        } else {
            int index = originalPath.indexOf(texturePrefix) + texturePrefix.length();
            String localizedPath = originalPath.substring(0, index) + textureInsert + originalPath.substring(index);

            Icon localizedIcon = Icon.getIcon(localizedPath);

            if (!localizedIcon.isEmpty()) {
                TextureLocaleRedirector.LOGGER.info("Redirected ChapterImage icon {} -> {}", originalPath, localizedPath);
                return localizedIcon;
            } else {
                return Icon.getIcon(originalPath.substring(0, index) + originalPath.substring(index));
            }
        }
    }
}
