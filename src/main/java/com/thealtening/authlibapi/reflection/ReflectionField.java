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
import java.lang.reflect.Modifier;

/**
 * @author Trol
 * @since 07/27/2019
 */
public class ReflectionField {
    private final Class<?> parent;
    private final Object instance;
    private final Field field;

    public ReflectionField(Class<?> parent, Field field, final Object instance) {
        this.parent = parent;
        this.field = field;
        this.instance = instance;
    }
    private void allowStaticFieldManipulation() throws IllegalAccessException, NoSuchFieldException {
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
    }
    public boolean setFieldValue(Object value) throws Exception {
        boolean accessible = field.isAccessible();
        field.setAccessible(true);
        int previousModifier = -1;
        try {
            if(Modifier.isFinal(field.getModifiers())) {
                previousModifier = field.getModifiers();
                allowStaticFieldManipulation();
            }
            field.set(instance, value);
            field.setAccessible(accessible);
            if(previousModifier != -1) {
                revertStaticFieldManipulation(previousModifier);
            }
        } catch (IllegalAccessException | NoSuchFieldException e) {
            field.setAccessible(accessible);
            throw new Exception(e);
        }
        return true;
    }
    private void revertStaticFieldManipulation(int modifier) throws IllegalAccessException, NoSuchFieldException {
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, modifier);
    }
}
