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
package com.aeonium.javafx.behaviour.handler;

import com.aeonium.javafx.behaviour.annotations.FXEditMask;
import java.lang.reflect.Field;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Robert Rohm&lt;r.rohm@aeonium-systems.de&gt;
 */
public class EditMaskHandlerTest {
  
  public EditMaskHandlerTest() {
  }
  
  @BeforeClass
  public static void setUpClass() {
  }
  
  @AfterClass
  public static void tearDownClass() {
  }
  
  @Before
  public void setUp() {
  }
  
  @After
  public void tearDown() {
  }

  /**
   * Test of handle method, of class EditMaskHandler.
   */
  @Test
  public void testHandle() {
    System.out.println("handle");
    Object controller = null;
    Field field = null;
    FXEditMask annotation = null;
    EditMaskHandler instance = new EditMaskHandler();
//    instance.handle(controller, field, annotation);
    // TODO review the generated test code and remove the default call to fail.
  }

  @Test(expected = NullPointerException.class)
  public void testHandle_NPE() {
    System.out.println("handle");
    Object controller = null;
    Field field = null;
    FXEditMask annotation = null;
    EditMaskHandler instance = new EditMaskHandler();
    instance.handle(controller, field, annotation);
  }
  
}
