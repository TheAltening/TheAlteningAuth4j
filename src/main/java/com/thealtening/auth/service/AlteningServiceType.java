package com.thealtening.auth.service;

/**
 * @author Vladymyr
 * @since 10/08/2019
 */
public enum AlteningServiceType {

    MOJANG("mojang"),
    THEALTENING("thealtening");

    private final String authServer;
    private final String sessionServer;
    private final String type;

    AlteningServiceType(String type) {
        this.type = type;
        this.authServer = "https://authserver." + type + ".com/";
        this.sessionServer = "http://sessionserver." + type + ".com/";
    }

    public String getAuthServer() {
        return this.authServer;
    }

    public String getSessionServer() {
        return this.sessionServer;
    }

    public String getType() {
        return this.type;
    }
}