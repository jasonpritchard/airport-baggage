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

package com.flydenver.bagrouter.lexer.section.bag;

import com.flydenver.bagrouter.domain.PassengerBag;
import com.flydenver.bagrouter.lexer.ParseException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * Tests for the bag route parser.
 */
public class BagRouteTest {

	private BagRowParserStrategy parser;
	private final String goodFormat = "0003 A2 UA10";
	private final String tooManyElements = "0003 A2 UA10 A3";
	private final String tooFewElements = "0003 A2";

	@Before
	public void setup() {
		parser = new BagRowParserStrategy();
	}


	//=[ good tests ]==========================================================

	
	@Test
	public void testParseGoodFormat() {
		try {
			assertNotNull( parser.parseSectionRow( goodFormat ) );
		}
		catch ( ParseException e ) {
			fail( e.getMessage() );
		}
	}

	
	//=[ bad arguments tests ]==========================================================

	
	@Test( expected = IllegalArgumentException.class )
	public void testTooManyLines() throws ParseException {
		parser.parseSectionRow( goodFormat + "\n" + goodFormat );
	}

	@Test( expected = IllegalArgumentException.class )
	public void testTooManyLinesWindows() throws ParseException {
		parser.parseSectionRow( goodFormat + "\r\n" + goodFormat );
	}

	@Test( expected = IllegalArgumentException.class )
	public void testNullLine() throws ParseException {
		parser.parseSectionRow( null );
	}

	
	//=[ parsing tests ]==========================================================

	
	@Test( expected = ParseException.class )
	public void testTooManyParts() throws ParseException {
		parser.parseSectionRow( tooManyElements );
	}

	@Test( expected = ParseException.class )
	public void testTooFewParts() throws ParseException {
		parser.parseSectionRow( tooFewElements );
	}

	@Test( expected = ParseException.class )
	public void testInvalidBagId() throws ParseException {
		parser.parseSectionRow( "0a03 A2 UA10" );
	}

	@Test
	public void testValidArrivalState() throws ParseException {
		BagRoute route = parser.parseSectionRow( "0003 A2 ARRIVAL" ).getWrappedRow();
		assertNotNull( route );
		assertEquals( PassengerBag.BagState.ARRIVAL, route.getBag().getBagState() );
	}

	@Test
	public void testValidInTransitState() throws ParseException {
		BagRoute route = parser.parseSectionRow( goodFormat ).getWrappedRow();
		assertNotNull( route );
		assertEquals( PassengerBag.BagState.IN_TRANSIT, route.getBag().getBagState() );
	}

}
