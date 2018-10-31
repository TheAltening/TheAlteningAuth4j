# The Altening Java API for AuthLib


<h3>Implementation.</h3>
<p>Gradle Groovy DSL:</p>

```
repositories {

    maven{ url = 'https://jitpack.io'}
}
dependencies {

    implementation 'com.github.TheAltening:API-Java:-SNAPSHOT'
    implementation 'com.github.TheAltening:API-Java-AuthLib:-SNAPSHOT'
}
```

<p>Gradle Kotlin DSL: </p>

```
repositories {
    maven(url = "https://jitpack.io")
}

dependencies {
    implementation("com.github.TheAltening:API-Java:-SNAPSHOT")
    implementation("com.github.TheAltening:API-Java-AuthLib:-SNAPSHOT")
}
```

<p>Maven: </p>

```
<repositories>
    <repository>
        <url>https://jitpack.io</url>
        <id>jitpack</id>
        <name>Jitpack Library Hoster</name>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.github.TheAltening</groupId>
        <artifactId>API-Java</artifactId>
        <version>-SNAPSHOT</version>
    </dependency>
    
    <dependency>
        <groupId>com.github.TheAltening</groupId>
        <artifactId>API-Java-AuthLib</artifactId>
        <version>-SNAPSHOT</version>
    </dependency>
</dependencies>
```

<p>Usage of the API:</p>

`
SSLVerification sslVerification = new SSLVerification();
`

`SSLVerification#verify();`
This verifies all of the SSL, and needs for the TheAltening servers to work.


`AltService altService = new AltService();`<br>
This is the main alt service switcher. <br>
Needs to be in your main class, never re-initialized.

`AltService#switchService(EnumAltService);`
<br>
Switches the service to the available services (`EnumAltService.MOJANG`, `EnumAltService.THEALTENING`)

`AltService#currentService();`
<br>
Returns the current service.