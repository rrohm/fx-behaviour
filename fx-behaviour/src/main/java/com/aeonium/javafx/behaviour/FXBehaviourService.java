/*
 * Copyright (C) 2020 Robert Rohm&lt;r.rohm@aeonium-systems.de&gt;.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package com.aeonium.javafx.behaviour;

import com.aeonium.javafx.actions.FXActionManager;
import com.aeonium.javafx.actions.annotations.AnnotationHandler;
import com.aeonium.javafx.behaviour.annotations.FXEditMask;
import com.aeonium.javafx.behaviour.annotations.FXFadeInOnVisible;
import com.aeonium.javafx.behaviour.handler.EditMaskHandler;
import com.aeonium.javafx.behaviour.handler.FadeInOnVisibleHandler;
import java.util.Map;

/**
 * Use this class as a factory for the default action manager, with additinonal
 * handlers for special behaviour annotations.
 *
 * @author Robert Rohm&lt;r.rohm@aeonium-systems.de&gt;
 */
public class FXBehaviourService {

  /**
   * Creates a new instance of the FXActionManager, with anotation handlers for
   * the default FXBehaviour annotations.
   *
   * @return The new FXActionManager instance.
   */
  public static FXActionManager getDefaultManager() {
    FXActionManager fxActionManager = new FXActionManager();
    fxActionManager.addHandler(FXFadeInOnVisible.class, new FadeInOnVisibleHandler());
    fxActionManager.addHandler(FXEditMask.class, new EditMaskHandler());
    return fxActionManager;
  }

  /**
   * Creates a new instance of the FXActionManager, with anotation handlers for
   * the default FXBehaviour annotations – and the annotations plus mapped
   * handlers supplied in the
   * {@link #getDefaultManager(java.util.Map) additionalHandlers} map.
   *
   * @param additionalHandlers A map of classes and responsive annotation
   * handlers. Class is an annotation type. AnnotationHandler needs to be a
   * handler exactly for this type of annotation.
   * @return The new FXActionManager instance.
   */
  public static FXActionManager getDefaultManager(Map<Class, AnnotationHandler> additionalHandlers) {
    FXActionManager fxActionManager = new FXActionManager(additionalHandlers);
    fxActionManager.addHandler(FXFadeInOnVisible.class, new FadeInOnVisibleHandler());
    fxActionManager.addHandler(FXEditMask.class, new EditMaskHandler());
    return fxActionManager;
  }
}
