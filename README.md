# Unimined

Plugin-based, **unified** modding toolchain with support for legacy tools.

[Gradle](https://gradle.org) is already supported.
Support is planned for [IntelliJ Platform](https://www.jetbrains.com/opensource/idea)
and [Apache Maven](https://maven.apache.org) in version 2.0+.

For details on usage, see [the docs](Writerside/topics/starter-topic.md)

## LTS Branch

In order to better support buildscripts. when I plan on making big/breaking changes, I will leave an LTS branch behind.
In effect, this means that I always support the previous minor version for bugfixes.

By consequence, seeing as it's only bugfixes, snapshots on the LTS branch are considered *relatively* stable as well.

## Games / Total Conversion Mods

### Supported Games (1.x)

* [Minecraft](https://minecraft.net)
* [Minecraft: ReIndev](https://reindev.miraheze.org/wiki/Reindev_Wiki)
* [Minecraft: Better Than Adventure](https://betterthanadventure.net)

## Planned Games

* [Not So Seecret Saturday](https://www.notsoseecretsaturday.net)

## Plugins

Below is a somewhat-comprehensive list of all first-party supported major features (although we may have missed some).

Starting in 2.0.0, additional
target game providers,
obfuscation mappings providers,
mod loader patchers,
and other kinds of patchers that act on the game and other classpath files such as mods
can be added through a new plugin system.

## Games / Total Conversion Mods

### Supported Games (1.x)

* [Minecraft](https://minecraft.net)
* [Minecraft: ReIndev](https://reindev.miraheze.org/wiki/Reindev_Wiki)
* [Minecraft: Better Than Adventure](https://betterthanadventure.net)

## Planned Games

* [Not So Seecret Saturday](https://www.notsoseecretsaturday.net)

## Loaders

### Supported Loaders (1.x)

* [Fabric](https://fabricmc.net)
* [Quilt](https://quiltmc.org)
* [Minecraft Forge](https://minecraftforge.net)
* [Neoforge](https://neoforged.net)
* [Cleanroom](https://cleanroommc.com)
* [Flint Loader](https://flintloader.net)
* [JarModAgent](https://github.com/unimined/JarModAgent)
* [Rift](https://github.com/DimensionalDevelopment/Rift)
* [FoxLoader](https://github.com/Fox2Code/FoxLoader)
* [LiteLoader](https://liteloader.com)
* [CraftBukkit](https://bukkit.org)
* [Spigot](https://www.spigotmc.org)
* [Paper](https://papermc.io)
* [Risugami's ModLoader](https://mcarchive.net/mods/modloader)
* just plain jar modding

### Planned Loaders

* [Sponge](https://spongepowered.org/]Sponge)
* [NilLoader](https://git.sleeping.town/Nil/NilLoader)

## TODO (2.x)

* Finish Platform-Independent API
  - Unimined Provider
  - Game Provider
  - Mappings Provider
  - Unimined Configuration
    - Game Configuration
      - Mappings Configuration
      - Patchers Configuration
* Game Plugins *
  - [ ] Minecraft: Java Edition
    - Provider name: `Minecraft`
    - Builder name: `minecraft`
    - Artifact: `net.minecraft:minecraft`
  - [ ] Minecraft: Better Than Adventure
    - Provider name: `Better Than Adventure`
    - Builder function: `reIndev`
    - Artifact: `net.betterthanadventure:bta`
  - [ ] Minecraft: ReIndev
    - Provider name: `ReIndev`
    - Builder function: `reIndev`
    - Artifact: `net.silveros:reindev`
  - [ ] Minecraft: Not So Seecret Saturday **
    - Provider name: `Not So Secret Saturday`
    - Builder function: `nsss`
    - Artifact: `net.notsoseecretsaturday:nsss`

* Patcher Plugins *
  - [ ] Remapper:
    - Setup: Apply deobfuscation mappings, access transformation, and interface injection
      to all classpath files + game files (`*.class`, `*.java`, javadoc `*.html`?)
    - Commit: When paired with Decompiler (see below), optionally remap source before generating patches
    - Build: Remap the build output to the namespaces specified in `finalizeTo` in the mappings configuration
  - [ ] Decompiler:
    - Setup: Decompile the game classes to their respective source sets and apply source patches
    - Commit: Generate source patches from Git subproject
    - Build: Generate binary patches + jar containing them
  - [ ] AccessWidener:
    - Setup: Parse `*.accesswidener` files from all mods (incl. project)
      and try to apply transitive ones to all target files
  - [ ] AccessTransformer:
    - Setup: Parse `accesstransformer.cfg` files from all mods (incl. project)
      and try to apply to all target files
  - [ ] Fabric Loader: Add Fabric Loader to the classpath and create run configurations for Knot
    - [ ] FabricMC
    - [ ] Babric
    - [ ] Turnip Labs (BTA)
  - [ ] Quilt Loader: See Fabric Loader.
  - [ ] Minecraft Forge
    - [ ] ForgeGradle 1
    - [ ] ForgeGradle 2
    - [ ] ForgeGradle 3
    - [ ] NeoForged
    - [ ] CleanroomMC
  - [ ] FoxLoader
  - [ ] Flint Loader
  - [ ] CraftBukkit
  - [ ] Spigot
  - [ ] Paper
  - [ ] ModLoader
* Gradle Plugin
* IntelliJ Platform Plugin (for IDEA Community Edition)*
* Apache Maven Plugin
*

\* - New Feature in 2.x
\** - New Feature that was planned for 1.x

## Recommended Gradle Setup

[//]: # (TODO: Example repo)
1. Generate a repository from the [template repository]() 
2. Set the latest version for the Unimined plugin in `gradle/libs.versions.toml`
