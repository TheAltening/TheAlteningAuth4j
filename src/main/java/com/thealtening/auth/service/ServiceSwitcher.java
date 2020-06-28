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

package com.thealtening.auth.service;

import com.mojang.authlib.Environment;
import com.mojang.authlib.yggdrasil.YggdrasilEnvironment;
import sun.reflect.ConstructorAccessor;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;

/**
 * @author Vladymyr
 * @since 10/08/2019
 */
public final class ServiceSwitcher {

    private final String YGGDRASIL_ENVIRONMENT = "com.mojang.authlib.yggdrasil.YggdrasilEnvironment";


    private final FieldAdapter yggdrasilEnvironment = new FieldAdapter(YGGDRASIL_ENVIRONMENT);

    public ServiceSwitcher() {
    }

    public AlteningServiceType switchToService(AlteningServiceType service, AlteningServiceType previousService) {
        try {
            this.yggdrasilEnvironment.updateFieldIfPresent("PROD", newYggdrasilEnvironment(service.getAuthServer(), service.getAccountsHost(), service.getSessionServer()));
        } catch (Exception exception) {
            exception.printStackTrace();
            return previousService;
        }

        return service;
    }
    private YggdrasilEnvironment newYggdrasilEnvironment(final String authHost, final String accountsHost, final String sessionHost) throws Exception{
        System.out.println();
        Constructor<?>[] declaredConstructors = YggdrasilEnvironment.class.getDeclaredConstructors();
        Constructor<YggdrasilEnvironment> declaredConstructor = (Constructor<YggdrasilEnvironment>) declaredConstructors[0];
        declaredConstructor.setAccessible(true);
        Field constructorAccessorField = Constructor.class.getDeclaredField("constructorAccessor");
        constructorAccessorField.setAccessible(true);
        ConstructorAccessor constructorAccessor = (ConstructorAccessor) constructorAccessorField.get(declaredConstructor);
        if (constructorAccessor == null) {
            Method acquireConstructorAccessorMethod = Constructor.class.getDeclaredMethod("acquireConstructorAccessor");
            acquireConstructorAccessorMethod.setAccessible(true);
            constructorAccessor = (ConstructorAccessor) acquireConstructorAccessorMethod.invoke(declaredConstructor);
        }
        return (YggdrasilEnvironment) constructorAccessor.newInstance(new Object[] {"PROD", 0, authHost, accountsHost, sessionHost});
    }
}
