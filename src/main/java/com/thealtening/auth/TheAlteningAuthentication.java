/*
 * Copyright (C) 2019 TheAltening
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.thealtening.auth;

import com.mojang.authlib.yggdrasil.YggdrasilEnvironment;
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
import java.util.function.Consumer;

/**
 * @author Vladymyr
 * @since 10/08/2019
 */
public final class TheAlteningAuthentication {

    private final ServiceSwitcher serviceSwitcher = new ServiceSwitcher();
    private final SSLController sslController = new SSLController();
    private final Consumer<YggdrasilEnvironment> afterSwitching;
    private static TheAlteningAuthentication instance;
    private AlteningServiceType service;

    private TheAlteningAuthentication(AlteningServiceType service, Consumer<YggdrasilEnvironment> afterSwitching) {
        this.afterSwitching = afterSwitching;
        this.updateService(service);
    }

    public void updateService(AlteningServiceType service) {
        if (service == null || this.service == service) {
            return;
        }

        switch (service) {
            case MOJANG:
                this.sslController.enableCertificateValidation();
                break;

            case THEALTENING:
                this.sslController.disableCertificateValidation();
                break;
        }

        this.service = this.serviceSwitcher.switchToService(service, this.service, afterSwitching);
    }

    public AlteningServiceType getService() {
        return service;
    }

    public static TheAlteningAuthentication mojang(final Consumer<YggdrasilEnvironment> afterSwitching) {
        return withService(AlteningServiceType.MOJANG, afterSwitching);
    }

    public static TheAlteningAuthentication theAltening(final Consumer<YggdrasilEnvironment> afterSwitching) {
        return withService(AlteningServiceType.THEALTENING, afterSwitching);
    }

    private static TheAlteningAuthentication withService(AlteningServiceType service, Consumer<YggdrasilEnvironment> afterSwitching) {
        if (instance == null) {
            instance = new TheAlteningAuthentication(service, afterSwitching);
        } else if (instance.getService() != service) {
            instance.updateService(service);
        }

        return instance;
    }
}
