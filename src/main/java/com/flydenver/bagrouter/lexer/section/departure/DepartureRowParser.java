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
import com.flydenver.bagrouter.lexer.ParseException;
import com.flydenver.bagrouter.lexer.section.RowParsingDelegate;
import com.flydenver.bagrouter.lexer.section.SectionRowWrapper;

import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * {@link RowParsingDelegate} implementation for parsing Departures section rows.
 */
public class DepartureRowParser implements RowParsingDelegate<Departure> {

	//	departure row should match this format
	private final static Pattern departureRowPattern = Pattern.compile( "^(\\w+\\s+)(\\w+\\s+)(\\w+\\s+)(\\d{2}:\\d{2})$" );

	//	string to extract flight time
	private final static SimpleDateFormat dateFormat = new SimpleDateFormat( "HH:mm" );

	@Override
	public SectionRowWrapper<Departure> parseSectionRow( String sectionLine ) throws ParseException {
		if ( sectionLine == null ) {
			throw new IllegalArgumentException( "Invalid line (null)." );
		}

		sectionLine = sectionLine.trim();

		if ( sectionLine.contains("\n") || sectionLine.contains("\r\n") ) {
			throw new IllegalArgumentException( "Too many lines." );
		}

		Matcher matcher = departureRowPattern.matcher( sectionLine );
		if ( !matcher.find() ) {
			throw new ParseException( "Departure line doesn't match pattern " + departureRowPattern.toString() );
		}

		Flight flight = new Flight();
		flight.setGate( new TerminalGate( matcher.group( 2 ).trim() ) );
		flight.setFlightId( new FlightId( matcher.group( 1 ).trim() ) );
		flight.setDestination( new Airport( matcher.group( 3 ).trim() ) );

		try {
			flight.setFlightTime( dateFormat.parse( matcher.group( 4 ).trim() ) );
		}
		catch ( java.text.ParseException e ) {
			throw new ParseException( "Invalid flight time. " + matcher.group( 4 ).trim() );
		}

		return new SectionRowWrapper<>( Departure.fillInFromFlightInfo( flight ) );
	}

}
