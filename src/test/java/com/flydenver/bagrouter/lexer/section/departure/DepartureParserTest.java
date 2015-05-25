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

package com.flydenver.bagrouter.lexer.section.departure;

import com.flydenver.bagrouter.lexer.ParseException;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Test cases for departure parsing.
 */
public class DepartureParserTest {

	private DepartureRowParser departureParser;

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Before
	public void setup() {
		departureParser = new DepartureRowParser();
	}

	@After
	public void tearDown() {}


	//=[ good tests ]==========================================================
	
	
	@Test
	public void testParseSectionGood() {
		try {
			Departure departure = departureParser.parseSectionRow( "UA10 A1 MIA 08:00" ).getWrappedRow();
			assertEquals( departure.getFlight().getFlightId().getId(), "UA10" );
		}
		catch (ParseException e) {
			fail( e.getMessage() );
		}
	}


	//=[ bad arguments tests ]==========================================================

	
	@Test( expected = IllegalArgumentException.class )
	public void testTooManyLines() throws ParseException {
		departureParser.parseSectionRow( "UA10 A1 MIA 08:00\nUA11 A2 MIA 04:00" );
		fail();
	}

	@Test( expected = IllegalArgumentException.class )
	public void testNullLine() throws ParseException {
		departureParser.parseSectionRow( null );
	}


	//=[ parsing tests ]==========================================================

	
	@Test( expected = ParseException.class )
	public void testTooManyElements() throws ParseException {
		departureParser.parseSectionRow( "UA10 A1 MIA 08:00 Extra" );
		fail();
	}

	@Test( expected = ParseException.class )
	public void testTooFewParts() throws ParseException {
		departureParser.parseSectionRow( "UA10 A1 02:00" );
		fail();
	}

	@Test( expected = ParseException.class )
	public void testInvalidMinutes() throws ParseException {
		departureParser.parseSectionRow( "UA10 A1 MIA 08:0" );
		fail();
	}

	@Test( expected = ParseException.class )
	public void testInvalidMinutesLong() throws ParseException {
		departureParser.parseSectionRow( "UA10 A1 MIA 08:011" );
		fail();
	}

	@Test( expected = ParseException.class )
	public void testInvalidHours() throws ParseException {
		departureParser.parseSectionRow( "UA10 A1 MIA 8:00" );
		fail();
	}

	@Test( expected = ParseException.class )
	public void testInvalidHoursLong() throws ParseException {
		departureParser.parseSectionRow( "UA10 A1 MIA 012:00" );
		fail();
	}

	@Test
	public void testInvalidFlightTime() throws ParseException {
		exception.expect( ParseException.class );
		exception.expectMessage( "Departure line doesn't match pattern ^(\\w+\\s+)(\\w+\\s+)(\\w+\\s+)(\\d{2}:\\d{2})$" );
		departureParser.parseSectionRow( "UA10 A1 MIA aa:00" );
	}

}
