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

import com.flydenver.bagrouter.domain.Airport;
import com.flydenver.bagrouter.domain.Flight;
import com.flydenver.bagrouter.domain.FlightId;
import com.flydenver.bagrouter.domain.TerminalGate;
import com.flydenver.bagrouter.lexer.section.departure.Departure;
import org.junit.Test;

import java.text.SimpleDateFormat;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;


/**
 * Tests for the Departure domain. Not really trying to test accessors
 * here, just testing the builder methods.
 */
public class DepartureTest {


	@Test( expected = IllegalArgumentException.class )
	public void testDepartureFromFlightNull() {
		Departure.fillInFromFlightInfo( null );
	}


	@Test
	public void testDepartureFromFlightGood() {
		Flight flight = new Flight();
		flight.setFlightId( new FlightId( "UA111" ) );
		flight.setGate( new TerminalGate( "A1" ) );
		flight.setDestination( new Airport( "DFW" ) );

		try {
			flight.setFlightTime( new SimpleDateFormat( "HH:mm" ).parse( "00:00" ) );
		}
		catch ( java.text.ParseException e ) {
			fail( e.getMessage() );
		}

		assertNotNull( Departure.fillInFromFlightInfo( flight ) );
		assertEquals( Departure.fillInFromFlightInfo( flight ).getDestination().getAirportId(), flight.getDestination().getAirportId() );
	}

}
