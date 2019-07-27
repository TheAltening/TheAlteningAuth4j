/*
 * Copyright (C) 2019 TheAltening
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package com.thealtening.authlibapi;

import com.thealtening.authlibapi.reflection.ReflectionClass;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Trol
 * @since 07/27/2019
 */
class ServiceSwitcher {
    private final ReflectionClass minecraftSessionServer = new ReflectionClass("com.mojang.authlib.yggdrasil.YggdrasilMinecraftSessionService");

    private final ReflectionClass userAuthentication = new ReflectionClass("com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication");

    void initialize() {
        try {
            minecraftSessionServer.getField("WHITELISTED_DOMAINS").setFieldValue(new String[]{".minecraft.net", ".mojang.com", ".thealtening.com"});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    TheAlteningAuthLibAPI.EnumAltService switchToService(TheAlteningAuthLibAPI.EnumAltService service) {
        try {
            this.userAuthentication.getField("BASE_URL").setFieldValue(getAuthServerLinkWithServiceType(service));
            this.userAuthentication.getField("ROUTE_AUTHENTICATE").setFieldValue(makeURLFromString(getAuthServerLinkWithServiceType(service) + "authenticate"));
            this.userAuthentication.getField("ROUTE_INVALIDATE").setFieldValue(makeURLFromString(getAuthServerLinkWithServiceType(service) + "invalidate"));
            this.userAuthentication.getField("ROUTE_REFRESH").setFieldValue(makeURLFromString(getAuthServerLinkWithServiceType(service) + "refresh"));
            this.userAuthentication.getField("ROUTE_VALIDATE").setFieldValue(makeURLFromString(getAuthServerLinkWithServiceType(service) + "validate"));
            this.userAuthentication.getField("ROUTE_SIGNOUT").setFieldValue(makeURLFromString(getAuthServerLinkWithServiceType(service) + "signout"));

            this.minecraftSessionServer.getField("BASE_URL").setFieldValue(getSessionServerLinkWithServiceType(service) + "session/minecraft/");
            this.minecraftSessionServer.getField("JOIN_URL").setFieldValue(makeURLFromString(getSessionServerLinkWithServiceType(service) + "session/minecraft/join"));
            this.minecraftSessionServer.getField("CHECK_URL").setFieldValue(makeURLFromString(getSessionServerLinkWithServiceType(service) + "session/minecraft/hasJoined"));

        } catch (Exception ignored) {
            ignored.printStackTrace();
            return TheAlteningAuthLibAPI.EnumAltService.MOJANG;
        }
        return service;
    }

    private String getAuthServerLinkWithServiceType(TheAlteningAuthLibAPI.EnumAltService service) {
        final String prefix = service == TheAlteningAuthLibAPI.EnumAltService.THEALTENING ? "http" : "https";
        return prefix + "://authserver." + service.name().toLowerCase() + ".com/";
    }

    private String getSessionServerLinkWithServiceType(TheAlteningAuthLibAPI.EnumAltService service) {
        final String prefix = service == TheAlteningAuthLibAPI.EnumAltService.THEALTENING ? "http" : "https";
        return prefix + "://sessionserver." + service.name().toLowerCase() + ".com/";
    }

    private URL makeURLFromString(String url) throws MalformedURLException {
        return new URL(url);
    }
}
