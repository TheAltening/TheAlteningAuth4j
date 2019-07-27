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

package com.thealtening.authlibapi.reflection;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * @author Trol
 * @since 07/27/2019
 */
public class ReflectionClass {
    private final Class<?> clazz;

    private final HashMap<String, ReflectionField> fields = new HashMap<>();

    public ReflectionClass(String className)  {
        this(className, null);
    }

    public ReflectionClass(String className, Object instance)  {
        try {
            this.clazz = Class.forName(className);
            for (Field declaredField : clazz.getDeclaredFields()) {
                fields.put(declaredField.getName(), new ReflectionField(clazz, declaredField, instance));
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("AuthLib not located in the classpath");
        }
    }

    public ReflectionClass(Class<?> clazz) {
        this(clazz, null);
    }

    public ReflectionClass(Class<?> clazz, Object instance) {
        this.clazz = clazz;
        for (Field declaredField : clazz.getDeclaredFields()) {
            fields.put(declaredField.getName(), new ReflectionField(clazz, declaredField, instance));
        }
    }

    public HashMap<String, ReflectionField> getFields() {
        return fields;
    }

    public ReflectionField getField(String name) {
        return fields.get(name);
    }
}
