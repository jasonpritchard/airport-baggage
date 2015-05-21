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

public class SectionTypeTest {

	private final static String conveyorSystem = "Conveyor System";
	private final static String departures = "Departures";
	private final static String bags = "Bags";
	
	
	//=[ good tests ]==========================================================

	@Test
	public void testGoodConveyorSystem() {
		assertEquals( SectionType.fromIdentifier( conveyorSystem ), SectionType.CONVEYOR_SYSTEM );
		assertEquals( SectionType.fromIdentifier( "ConveyorSystem" ), SectionType.CONVEYOR_SYSTEM );
		assertEquals( SectionType.fromIdentifier( " Conveyor System " ), SectionType.CONVEYOR_SYSTEM );
	}
	
	@Test
	public void testGoodDepartures() {
		assertEquals( SectionType.fromIdentifier( departures ), SectionType.DEPARTURES );
		assertEquals( SectionType.fromIdentifier( " Departures " ), SectionType.DEPARTURES );
	}
	
	@Test
	public void testGoodBags() {
		assertEquals( SectionType.fromIdentifier( bags ), SectionType.BAGS );
		assertEquals( SectionType.fromIdentifier( " Bags " ), SectionType.BAGS );
	}
	
	@Test
	public void testGoodConveyorSystemCase() {
		assertEquals( SectionType.fromIdentifier( conveyorSystem.toLowerCase() ), SectionType.CONVEYOR_SYSTEM );
		assertEquals( SectionType.fromIdentifier( conveyorSystem.toUpperCase() ), SectionType.CONVEYOR_SYSTEM );
	}
	
	@Test
	public void testGoodDeparturesCase() {
		assertEquals( SectionType.fromIdentifier( departures.toLowerCase() ), SectionType.DEPARTURES );
		assertEquals( SectionType.fromIdentifier( departures.toUpperCase() ), SectionType.DEPARTURES );
	}
	
	@Test
	public void testGoodBagsCase() {
		assertEquals( SectionType.fromIdentifier( bags.toLowerCase() ), SectionType.BAGS );
		assertEquals( SectionType.fromIdentifier( bags.toUpperCase() ), SectionType.BAGS );
	}

	//=[ bad arguments tests ]==========================================================

	@Test
	public void testNull() {
		assertEquals( SectionType.fromIdentifier( null ), SectionType.UNKNOWN );
		assertEquals( SectionType.fromIdentifier( "" ), SectionType.UNKNOWN );
	}
	
	//=[ id tests ]==========================================================
	
	@Test
	public void testSlipsConveyorSystem() {
		assertEquals( SectionType.fromIdentifier( "Conveyor Sistem" ), SectionType.UNKNOWN );
		assertEquals( SectionType.fromIdentifier( "Conveyor" ), SectionType.UNKNOWN );
	}
	
	@Test
	public void testSlipsDepartures() {
		assertEquals( SectionType.fromIdentifier( "Departure" ), SectionType.UNKNOWN );
		assertEquals( SectionType.fromIdentifier( "Departuresss" ), SectionType.UNKNOWN );
		assertEquals( SectionType.fromIdentifier( "Depar turesss" ), SectionType.UNKNOWN );
	}
	
	@Test
	public void testSlipsBags() {
		assertEquals( SectionType.fromIdentifier( "Bag" ), SectionType.UNKNOWN );
		assertEquals( SectionType.fromIdentifier( "Bagsss" ), SectionType.UNKNOWN );
		assertEquals( SectionType.fromIdentifier( "Ba gsss" ), SectionType.UNKNOWN );
	}
	
}
