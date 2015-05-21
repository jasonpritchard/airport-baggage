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

package com.flydenver.bagrouter.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


public class FlightTest {
	private Flight flight1;
	private Flight flight2;
	private Flight flight3;

	@Before
	public void setup() {
		flight1 = new Flight( new FlightId( "UA101" ) );
		flight1.setGate( new TerminalGate( "A1" ) );
		flight1.setDestination( new Airport( "DFW" ) );

		flight2 = new Flight( new FlightId( "FF101" ) );
		flight2.setGate( new TerminalGate( "A1" ) );
		flight2.setDestination( new Airport( "JFK" ) );

		flight3 = new Flight( );
		flight3.setFlightId( new FlightId( "UA101" ) );
		flight3.setGate( new TerminalGate( "A1" ) );
		flight3.setDestination( new Airport( "DFW" ) );
	}

	@Test
	public void testFlightEquals() {
		assertEquals( flight1, flight3 );
		assertNotEquals( flight1, flight2 );
		assertNotEquals( flight3, flight2 );

		assertNotEquals( flight1, null );
		assertNotEquals( flight2, null );
		assertNotEquals( flight3, null );
	}

}
