# The Altening AuthLib Hotfix
<p>
Allows you to switch the current alt service between Mojang & TheAltening
</p>

Example:
```java
private final TheAlteningAuthLibAPI api = new TheAlteningAuthLibAPI();

private void initialize() {
    api.initialize();
    api.setService(TheAlteningAuthLibAPI.EnumAltService.THEALTENING);
}
```