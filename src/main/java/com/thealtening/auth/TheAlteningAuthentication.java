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

package com.thealtening.auth;

import com.thealtening.auth.service.AlteningServiceType;
import com.thealtening.auth.service.ServiceSwitcher;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

/**
 * @author Vladymyr
 * @since 10/08/2019
 */
public final class TheAlteningAuthentication {

    private final ServiceSwitcher serviceSwitcher = new ServiceSwitcher();
    private static TheAlteningAuthentication instance;
    private AlteningServiceType service;

    private TheAlteningAuthentication(AlteningServiceType service) {
        this.disableCertificateValidation();
        this.updateService(service);
    }

    public void updateService(AlteningServiceType service) {
        if (service == null || this.service == service) {
            return;
        }

        this.service = this.serviceSwitcher.switchToService(service);
    }

    private void disableCertificateValidation() {
        TrustManager[] trustManager = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] x509Certificates, String s) {
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                }
        };

        SSLContext sc = null;
        try {
            sc = SSLContext.getInstance("SSL");
            sc.init(null, trustManager, new SecureRandom());

            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }

        HttpsURLConnection.setDefaultHostnameVerifier((hostname, sslSession) -> hostname.equals("authserver.thealtening.com") || hostname.equals("sessionserver.thealtening.com"));
    }

    public AlteningServiceType getService() {
        return service;
    }

    public static TheAlteningAuthentication mojang() {
        return withService(AlteningServiceType.MOJANG);
    }

    public static TheAlteningAuthentication theAltening() {
        return withService(AlteningServiceType.THEALTENING);
    }

    private static TheAlteningAuthentication withService(AlteningServiceType service) {
        if (instance == null) {
            instance = new TheAlteningAuthentication(service);
        } else if (instance.getService() != service) {
            instance.updateService(service);
        }

        return instance;
    }
}
