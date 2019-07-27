# The Altening AuthLib Hotfix
<p>
Allows you to switch the current alt service between Mojang & TheAltening
</p>

<h3>Implementation:</h3>
<p>Gradle Groovy DSL:</p>

```
repositories {

    maven{ url = 'https://jitpack.io'}
}
dependencies {
    implementation 'com.github.TheAltening:API-Java-AuthLib:-2.0-SNAPSHOT'
}
```

<p>Gradle Kotlin DSL: </p>

```
repositories {
    maven(url = "https://jitpack.io")
}

dependencies {
    implementation("com.github.TheAltening:API-Java-AuthLib:-2.0-SNAPSHOT")
}
```

<p>Maven: </p>

```
<repositories>
    <repository>
        <url>https://jitpack.io</url>
        <id>jitpack</id>
        <name>Jitpack</name>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.github.TheAltening</groupId>
        <artifactId>API-Java-AuthLib</artifactId>
        <version>-2.0-SNAPSHOT</version>
    </dependency>
</dependencies>
```
Example:
```java
private final TheAlteningAuthLibAPI api = new TheAlteningAuthLibAPI();

private void initialize() {
    api.initialize();
    api.setService(TheAlteningAuthLibAPI.EnumAltService.THEALTENING);
}
```