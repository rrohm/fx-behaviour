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
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Robert Rohm&lt;r.rohm@aeonium-systems.de&gt;
 */
public class FXBehaviourServiceTest {

  public FXBehaviourServiceTest() {
    // no op
  }

  /**
   * Test of getDefaultManager method, of class FXBehaviourService.
   */
  @Test
  public void testGetDefaultManager() {
    System.out.println("getDefaultManager");
    FXActionManager result = FXBehaviourService.getDefaultManager();

    assertNotNull(result);
  }

}
