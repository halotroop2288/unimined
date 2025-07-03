# Unimined for Gradle

The main successor to Unimined 1.x. An implementation of the Unimined 2.x API.

This Gradle plugin can be used in a similar fashion the original implementation.

It includes all built-in Unimined plugins to quickstart a modern Minecraft modding environment.

- [Remapper Patcher](../remapperPlugin)
- [Minecraft + Mojmap + Parchment](../minecraft/README.md)
- [Minecraft Forge toolchain](../lexforgePlugin/README.md)
- [NeoForge toolchain](../neoforgedPlugin/README.md)
- [FabricMC toolchain](../fabricmcPlugin)

## Installation

```kts
// settings.gradle.kts
pluginManagement {
	repositories {
		// Pick either release or snapshot
		maven("https://maven.wagyourtail.xyz/releases")
		maven("https://maven.wagyourtail.xyz/snapshots")
	}
}
```

Latest Release: ![Latest Release][Release] Latest Snapshot: ![Latest Snapshot][Snapshot]

```kts
// build.gradle.kts
plugins {
	id("com.unimined") version("2.0.0")
}
```

## Basic Usage

[//]: # (TODO: Basic usage documentation for a recent version of Minecraft, with Fabric Loader)

For more information, see [Usage](../../Writerside/topics/Usage.md)

[Release]:https://img.shields.io/maven-metadata/v?metadataUrl=https%3A%2F%2Fmaven.wagyourtail.xyz%2Freleases%2Fcom%2Funimined%2Fgradle%2Fmaven-metadata.xml&strategy=highestVersion&label=Latest%20Release&labelColor=darkgreen
[Snapshot]:https://img.shields.io/maven-metadata/v?metadataUrl=https%3A%2F%2Fmaven.wagyourtail.xyz%2Fsnapshots%2Fcom%2Funimined%2Fgradle%2Fmaven-metadata.xml&strategy=highestVersion&label=Latest%20Release&labelColor=darkgreen
