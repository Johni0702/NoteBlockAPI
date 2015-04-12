**This API is experimental and probably is not yet fully functional.**

# NoteBlockAPI (Sponge)
## About NoteBlockAPI (Sponge)
This plugin is complete rewrite of the original [NoteBlockAPI](http://dev.bukkit.org/bukkit-plugins/noteblockapi/) for the SpongeAPI.

It allows you to play `.nbs` (NoteBlockStudio) and `.midi` files using the note block sounds in the game.

## Building
NoteBlockAPI is built using the [Java Development Kit](http://www.oracle.com/technetwork/java/javase/downloads/index.html) (Version 7 or above) and [Gradle](http://gradle.org/).

You can build NoteBlockAPI by using the command `./gradlew`. You may also use a local installation of gradle.

If everything went well, the generated jar files should be in the `noteblock-api/build/libs` folder (for the API) and in the `noteblock-impl/build/libs` folder (for the plugin itself).
All jars will also be installed into your local maven repository if you want to use them in a project of yours.

## Using
To use NoteBlockAPI, simply add it to your dependencies.
##### Gradle:
```
dependencies {
    compile 'de.johni0702.sponge:noteblock-api:0.1.0-SNAPSHOT'
}
```
##### Maven:
```
<dependency>
    <groupId>de.johni0702.sponge</groupId>
    <artifactId>noteblock-api</artifactId>
    <version>0.1.0-SNAPSHOT</version>
</dependency>
```

#### Quick Guide:

## Documentation and Support
Javadoc jars are located in the same folder as the other jars and are suffixed with `-javadoc`.

## Credits
- `xxmicloxx` and `michidk` for the original [NoteBlockAPI](https://github.com/xxmicloxx/NoteBlockAPI)
- [SpongePowered](https://github.com/SpongePowered) for obvious reasons
- [Sponge Plugin Boilerplate](https://github.com/spbp) for providing such a nice plugin boilerplate
- All the people behind [Gradle](https://gradle.org) and [Java](https://java.oracle.com)

## License
NoteBlockAPI is free software licensed under the MIT license. See `LICENSE` for more information.