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

import com.flydenver.bagrouter.domain.Flight;
import com.flydenver.bagrouter.domain.FlightId;
import com.flydenver.bagrouter.domain.PassengerBag;
import com.flydenver.bagrouter.domain.TerminalGate;
import com.flydenver.bagrouter.lexer.ParseException;
import com.flydenver.bagrouter.lexer.section.RowParsingStrategy;
import com.flydenver.bagrouter.lexer.section.SectionRowWrapper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * {@link RowParsingStrategy} implementation for parsing Bag section rows.
 */
class BagRowParserStrategy implements RowParsingStrategy<BagRoute> {

	//	conveyor row should match this format
	private final static Pattern bagRowPattern = Pattern.compile( "^(\\d+\\s+)(\\w+\\s+)(\\w+)$" );


	@Override
	public SectionRowWrapper<BagRoute> parseSectionRow( String sectionLine ) throws ParseException {
		if ( sectionLine == null ) {
			throw new IllegalArgumentException( "Invalid line (null)." );
		}

		sectionLine = sectionLine.trim();

		if ( sectionLine.contains("\n") || sectionLine.contains("\r\n") ) {
			throw new IllegalArgumentException( "Too many lines." );
		}

		Matcher matcher = bagRowPattern.matcher( sectionLine );
		if ( !matcher.find() ) {
			throw new ParseException( "Bag route line doesn't match pattern " + bagRowPattern.toString() );
		}

		BagRoute route = new BagRoute();
		route.setBag( new PassengerBag( matcher.group( 1 ).trim() ) );
		route.setEntryPoint( new TerminalGate( matcher.group( 2 ).trim() ) );

		String flightId = matcher.group( 3 ).trim();
		if ( "ARRIVAL".equalsIgnoreCase( flightId ) ) {
			route.getBag().setBagState( PassengerBag.BagState.ARRIVAL );
		}
		else {
			route.getBag().setBagState( PassengerBag.BagState.IN_TRANSIT );

			route.setFlight( new Flight() );
			route.getFlight().setGate( route.getEntryPoint() );
			route.getFlight().setFlightId( new FlightId( matcher.group(3).trim() ) );
		}

		return new SectionRowWrapper<>( route );
	}

}
