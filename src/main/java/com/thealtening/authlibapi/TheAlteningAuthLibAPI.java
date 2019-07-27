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

import com.thealtening.authlibapi.reflection.ReflectionField;

/**
 * @author Trol
 * @since 07/27/2019
 */
public class TheAlteningAuthLibAPI {
    private final SSLHotfix sslHotfix = new SSLHotfix();
    private final ServiceSwitcher serviceSwitcher = new ServiceSwitcher();
    private EnumAltService service;

    public void initialize() {
        this.sslHotfix.attemptVerify();
        this.service = EnumAltService.MOJANG;
        this.serviceSwitcher.initialize();

    }

    public void setService(EnumAltService service) {
        if (this.service == service) {
            return;
        }
        this.service = this.serviceSwitcher.switchToService(service);
    }

    public EnumAltService getService() {
        return service;
    }

    public enum EnumAltService {
        MOJANG, THEALTENING
    }
}
