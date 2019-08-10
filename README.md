# The Altening Auth API

[![Java 8+][java-badge]](https://java.oracle.com/)
[![Maven Central][maven-badge]](https://search.maven.org/artifact/pw.stamina/pubsub4k)

[java-badge]: https://img.shields.io/badge/Java-8%2B-informational.svg
[maven-badge]: https://img.shields.io/maven-central/v/pw.stamina/pubsub4k.svg

A fork of The Altening Auth API 2.0 made by [Trol](https://github.com/Trol1337). The objective of this fork is to improve the performance and readability for its users.

## Prerequisites
 * Use JDK 1.8+
 
## Usage

1. Create a new `TheAlteningAuthentication` instance depending on the service wanted:
```java
import com.thealtening.auth.TheAlteningAuthentication

TheAlteningAuthentication mojang = TheAlteningAuthentication.mojang();
TheAlteningAuthentication theAltening = TheAlteningAuthentication.theAltening();
```
2. Switch to another service
If you want to switch to another service, use the ``updateService`` method from your auth instance.

```java
theAlteningAuth.updateService(AlteningServiceType.MOJANG);
theAlteningAuth.updateService(AlteningServiceType.THEALTENING);
```

Note: if the given service type is ``null`` or the same as the current, no change will be made.

## License
The fork as the original repository requires it is under [GNU GPLv3](https://choosealicense.com/licenses/gpl-3.0/).
