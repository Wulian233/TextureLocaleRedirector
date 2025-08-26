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
        if ("en_us".equals(currentLang)) {
            return image;
        }

        String path = image.toString();
        if (path.startsWith("textures/")) {
            String localizedPath = "textures/" + currentLang + "/" + path.substring(9);
            Icon localizedIcon = Icon.getIcon(localizedPath);

            if (!localizedIcon.isEmpty()) {
                TextureLocaleRedirector.LOGGER.info("Redirected ChapterImage icon {} -> {}", path, localizedPath);
                return localizedIcon;
            }
        }

        return image;
    }
}
