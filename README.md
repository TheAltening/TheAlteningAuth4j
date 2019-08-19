# The Altening Auth API

[![Java 8+][java-badge]](https://java.oracle.com/)
[![Maven Central][maven-badge]](https://search.maven.org/artifact/com.thealtening.auth/auth)

[java-badge]: https://img.shields.io/badge/Java-8%2B-informational.svg
[maven-badge]: https://img.shields.io/maven-central/v/com.thealtening.auth/auth.svg
[![GitHub license](https://img.shields.io/github/license/TheAltening/TheAlteningAuth4j)](https://github.com/TheAltening/TheAlteningAuth4j/blob/4.0/LICENSE)

TheAltening Auth API made by [Vladymyr](https://github.com/Vladymyr) now branded under the official repo

## Gradle
```groovy
repositories {
	mavenCentral()
}
dependencies {
	implementation 'com.thealtening.auth:auth:@BADGE-VERSION@'
}
```

## Maven
```xml
<dependencies>
	<dependency>
		<groupId>com.thealtening.auth</groupId>
		<artifactId>auth</artifactId>
		<version>@BADGE-VERSION@</version>
	</dependency>
</dependencies>
```

## Prerequisites
 * Use JDK 1.8+
 
## Usage

1. Create a new `TheAlteningAuthentication` instance depending on the service wanted:
```java
import com.thealtening.auth.TheAlteningAuthentication

TheAlteningAuthentication mojang = TheAlteningAuthentication.mojang();
TheAlteningAuthentication theAltening = TheAlteningAuthentication.theAltening();
```
2. I case that you want to switch to another service, use the ``updateService`` method from your auth instance.

```java
theAlteningAuth.updateService(AlteningServiceType.MOJANG);
theAlteningAuth.updateService(AlteningServiceType.THEALTENING);
```

Note: if the given service type is ``null`` or the same as the current, no change will be made.
