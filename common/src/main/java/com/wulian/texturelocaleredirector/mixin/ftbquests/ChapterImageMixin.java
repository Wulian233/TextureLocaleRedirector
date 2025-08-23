package com.wulian.texturelocaleredirector.mixin.ftbquests;

import com.wulian.texturelocaleredirector.LangTextureCache;
import com.wulian.texturelocaleredirector.TextureLocaleRedirector;
import dev.ftb.mods.ftblibrary.icon.Icon;
import dev.ftb.mods.ftbquests.quest.ChapterImage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ChapterImage.class, remap = false)
public abstract class ChapterImageMixin {
    @Shadow private Icon image;
    @Inject(method = "setImage(Ldev/ftb/mods/ftblibrary/icon/Icon;)Ldev/ftb/mods/ftbquests/quest/ChapterImage;", at = @At("HEAD"), cancellable = true)
    private void injectLocalizedImage(Icon image, CallbackInfoReturnable<ChapterImage> cir) {
        String currentLang = LangTextureCache.getCurrentLanguage();
        if ("en_us".equals(currentLang)) {
            return;
        }

        String originalPath = image.toString();
        String localizedPath = "textures/" + currentLang + "/" + originalPath.replaceFirst("^textures/", "");
        Icon localizedIcon = Icon.getIcon(localizedPath);

        if (!localizedIcon.isEmpty()) {
            this.image = localizedIcon;
            TextureLocaleRedirector.LOGGER.info("Redirected ChapterImage icon {} -> {}", originalPath, localizedPath);
            cir.setReturnValue((ChapterImage) (Object) this);
        }
    }
}