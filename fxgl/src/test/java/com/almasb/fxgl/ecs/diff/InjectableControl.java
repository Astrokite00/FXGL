/*
 * FXGL - JavaFX Game Library. The MIT License (MIT).
 * Copyright (c) AlmasB (almaslvl@gmail.com).
 * See LICENSE for details.
 */

package com.almasb.fxgl.ecs.diff;

import com.almasb.fxgl.ecs.Control;
import com.almasb.fxgl.ecs.Entity;
import com.almasb.fxgl.ecs.EntityTest;

/**
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
public class InjectableControl extends Control {

    private EntityTest.CustomDataComponent component;

    private EntityTest.CustomDataControl control;

    @Override
    public void onAdded(Entity entity) {
        if (component == null || !"Inject".equals(component.getData())) {
            throw new RuntimeException("Injection failed!");
        }

        if (control == null || !"InjectControl".equals(control.getData())) {
            throw new RuntimeException("Injection failed!");
        }
    }

    @Override
    public void onUpdate(Entity entity, double tpf) {

    }
}
