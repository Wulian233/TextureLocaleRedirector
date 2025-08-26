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

        String path = image.toString();
        int idx = path.indexOf("textures/");
        if (idx != -1) {
            String before = path.substring(0, idx + 9); // 包含 "textures/"
            String after = path.substring(idx + 9);
            String localizedPath = before + currentLang + "/" + after;

            Icon localizedIcon = Icon.getIcon(localizedPath);

            if (!localizedIcon.isEmpty()) {
                TextureLocaleRedirector.LOGGER.info("Redirected ChapterImage icon {} -> {}", path, localizedPath);
                return localizedIcon;
            }
        }

        return image;
    }
}
