/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Jason Pritchard
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */
package com.flydenver.bagrouter.lexer.section;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SectionTokenTest {

	private final static String conveyorSystem = "Conveyor System";
	private final static String departures = "Departures";
	private final static String bags = "Bags";
	
	
	//=[ good tests ]==========================================================

	@Test
	public void testGoodConveyorSystem() {
		assertEquals( SectionToken.fromIdentifier( conveyorSystem ), SectionToken.CONVEYOR_SYSTEM );
	}
	
	@Test
	public void testGoodDepartures() {
		assertEquals( SectionToken.fromIdentifier( departures ), SectionToken.DEPARTURES );	
	}
	
	@Test
	public void testGoodBags() {
		assertEquals( SectionToken.fromIdentifier( bags ), SectionToken.BAGS );	
	}
	
	@Test
	public void testGoodConveyorSystemLower() {
		assertEquals( SectionToken.fromIdentifier( conveyorSystem.toLowerCase() ), SectionToken.CONVEYOR_SYSTEM );
	}
	
	@Test
	public void testGoodDeparturesLower() {
		assertEquals( SectionToken.fromIdentifier( departures.toLowerCase() ), SectionToken.DEPARTURES );	
	}
	
	@Test
	public void testGoodBagsLower() {
		assertEquals( SectionToken.fromIdentifier( bags.toLowerCase() ), SectionToken.BAGS );	
	}

	//=[ bad arguments tests ]==========================================================

	@Test
	public void testNull() {
		assertEquals( SectionToken.fromIdentifier( null ), SectionToken.UNKNOWN );	
	}
	
	//=[ id tests ]==========================================================
	
	@Test
	public void testSlipsConveyorSystem() {
		assertEquals( SectionToken.fromIdentifier( "Conveyor Sistem" ), SectionToken.UNKNOWN );	
		assertEquals( SectionToken.fromIdentifier( "ConveyorSystem" ), SectionToken.UNKNOWN );	
		assertEquals( SectionToken.fromIdentifier( "Conveyor" ), SectionToken.UNKNOWN );	
	}
	
	@Test
	public void testSlipsDepartures() {
		assertEquals( SectionToken.fromIdentifier( "Departure" ), SectionToken.UNKNOWN );	
		assertEquals( SectionToken.fromIdentifier( "Departuresss" ), SectionToken.UNKNOWN );	
		assertEquals( SectionToken.fromIdentifier( "Depar turesss" ), SectionToken.UNKNOWN );	
	}
	
	@Test
	public void testSlipsBags() {
		assertEquals( SectionToken.fromIdentifier( "Bag" ), SectionToken.UNKNOWN );	
		assertEquals( SectionToken.fromIdentifier( "Bagsss" ), SectionToken.UNKNOWN );	
		assertEquals( SectionToken.fromIdentifier( "Ba gsss" ), SectionToken.UNKNOWN );	
	}
	
}
