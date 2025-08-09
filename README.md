<div align="center"> 
   <img height="128px" width="128px" alt="logo" src="./icon/icon.png"/> 
   <h1>Texture Locale Redirector</h1>

<a href="https://modrinth.com/project/texture-locale-redirector">
<img alt="modrinth" height="56" src="https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy/available/modrinth_vector.svg">
</a>
<a href="https://www.curseforge.com/minecraft/mc-mods/texture-locale-redirector">
<img alt="curseforge" height="56" src="https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy/available/curseforge_vector.svg">
</a>

<img alt="forge" height="56" src="https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy/supported/forge_vector.svg">
<img alt="neoforge" height="56" src="https://raw.githubusercontent.com/KessokuTeaTime/Badges-Extra/main/assets/cozy/supported/neoforge_vector.svg">
<img alt="fabric" height="56" src="https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy/supported/fabric_vector.svg">
</div>

Texture Locale Redirector 让 Minecraft 支持按游戏语言重定向资源路径，实现多语言的纹理加载。

本模组通过扩展原版的资源包格式来实现纹理本地化替换，为本地化翻译过程中替换带有文字的纹理贴图而不破坏其他语言的体验提供了解决方案。

## 使用说明

1. 加载本模组并启用你制作的资源包
2. 创建资源包结构：`assets/<namespace>/textures/<language>/`
3. 将本地化纹理放入对应语言文件夹中，保持与原纹理相同的相对路径
4. 在游戏中选择对应语言即可看到效果

本模组进行了大量的优化，对性能影响应该比较微小。

包括：模组只在非`en_us`环境下运行，使用高效的字符串拼接和循环，以及一次性批量合并资源，从而最大限度地减少了性能开销。
如果找不到特定语言的纹理，它会自动回退到默认的英文纹理，保证游戏稳定运行。

## 资源包结构示例

```
资源包名称/
└── assets/
    └── minecraft（命名空间）/
        └── textures/
            ├── zh_cn/          # 简体中文纹理
            │   ├── block/
            │   │   └── dirt.png  # 替换的泥土纹理
            │   └── item/
            │       └── diamond_sword.png  # 替换的钻石剑纹理
            └── ja_jp/          # 日文纹理
                └── item/
                    └── diamond_sword.png  # 替换的钻石剑纹理
```

## 许可证

本项目采用 [MIT 许可证](LICENSE) 开源。

欢迎提交 Issue 反馈问题或提出建议，欢迎贡献 PR 。
