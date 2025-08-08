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

## 核心原理

* **语言特定路径映射**：通过拦截资源加载流程，将纹理请求重定向到 `textures/<language>/` 目录下的对应资源。
* **缓存机制加速加载**：为常用语言资源构建缓存，避免重复查找和加载。
* **动态刷新与回退策略**：语言切换时清空缓存，重新加载对应语言纹理；若目标语言资源缺失，自动回退到默认英文纹理，保证无缝体验。
* **线程安全控制**：利用线程本地变量防止资源重定向时的递归调用和状态冲突，确保稳定运行。

## 性能优化

* **减少无效调用**：仅在非英语（en_us）环境触发替换逻辑，避免对英语玩家造成负担。
* **统一状态切换**：批量资源替换时统一开启/关闭替换状态，降低线程状态切换开销。
* **复制返回数据**：避免直接修改游戏底层资源映射，使用本地缓存安全合并替换资源，兼容性更强。
* **预筛选替换资源**：先过滤出所有待替换资源，减少多余的磁盘访问和资源查找次数。

## 许可证

本项目采用 [MIT 许可证](LICENSE) 开源。

欢迎提交 Issue 反馈问题或提出建议，欢迎贡献 PR 。
