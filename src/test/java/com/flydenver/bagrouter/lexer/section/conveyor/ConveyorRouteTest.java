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

package com.flydenver.bagrouter.lexer.section.conveyor;

import com.flydenver.bagrouter.lexer.ParseException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.fail;

/**
 * Tests for the converyor route parsing.
 */
public class ConveyorRouteTest {

	private final String goodFormaat = "A1 A2 3";

	private ConveyorRowParserStrategy parser;

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Before
	public void setup() {
		parser = new ConveyorRowParserStrategy();
	}

	
	//=[ good tests ]==========================================================


	@Test
	public void testConveyorRouteParseGood() {
		try {
			parser.parseSectionRow(goodFormaat);
		}
		catch ( ParseException e ) {
			fail( e.getMessage() );
		}
	}


	//=[ bad argument tests ]==========================================================

	
	@Test( expected = IllegalArgumentException.class )
	public void testTooManyLines() throws ParseException {
		parser.parseSectionRow( goodFormaat + "\n" + goodFormaat );
	}

	@Test( expected = IllegalArgumentException.class )
	public void testNullLine() throws ParseException {
		parser.parseSectionRow( null );
	}

	
	//=[ parsing tests ]==========================================================
	

	@Test( expected = NumberFormatException.class )
	public void testInvalidFlightTime() throws ParseException {
		parser.parseSectionRow( "A1 A2 " + Long.toString( (long) Integer.MAX_VALUE + 1000l ) );
	}

	@Test
	public void testTooManyParts() throws ParseException {
		String tooManyParts = goodFormaat + " 99";
		exception.expect( ParseException.class );
		exception.expectMessage( "Conveyor route line doesn't match pattern ^(\\w+\\s+)(\\w+\\s+)(\\d+)$" );
		parser.parseSectionRow( tooManyParts );
	}

	@Test
	public void testTooFewParts() throws ParseException {
		exception.expect( ParseException.class );
		exception.expectMessage( "Conveyor route line doesn't match pattern ^(\\w+\\s+)(\\w+\\s+)(\\d+)$" );
		parser.parseSectionRow( "A1 A2" );
	}

}
