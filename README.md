<div align="center"> 
   <img height="100px" width="100px" alt="logo" src="./common/src/main/resources/assets/texturelocaleredirector/icon.png"/> 
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

## 未完成！目前存在较大问题需要完善，性能优化也比较低效

Texture Locale Redirector 是一个轻量的 Minecraft 模组，支持按游戏语言重定向资源路径，实现多语言的纹理加载。

本模组通过原版的资源包实现纹理的本地化替换，为本地化翻译过程中替换带有文字的纹理贴图而不破坏其他语言的体验提供了解决方案。

## 工作原理

1. 当游戏启动加载纹理资源时，模组会检查资源包内是否存在本模组新增的语言文件夹
2. 如果找到匹配的语言文件夹，且当前游戏语言与资源包设定的一致，则自动把原始纹理替换为设定的特定纹理
3. 对于英语（en_us）环境，自动跳过检查以提升性能

## 使用说明

1. 加载本模组
2. 创建资源包结构：`assets/<namespace>/textures/<language>/`
3. 将本地化纹理放入对应语言文件夹中，保持与原纹理相同的相对路径
4. 在游戏中选择对应语言即可看到效果

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
