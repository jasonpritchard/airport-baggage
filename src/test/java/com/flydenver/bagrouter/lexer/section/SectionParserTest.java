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

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import com.flydenver.bagrouter.lexer.RoutingInput;
import com.flydenver.bagrouter.lexer.section.bag.BagRoute;
import com.flydenver.bagrouter.lexer.section.bag.BagRouteParser;
import com.flydenver.bagrouter.lexer.section.conveyor.ConveyorRoute;
import com.flydenver.bagrouter.lexer.section.conveyor.ConveyorRouteParser;
import com.flydenver.bagrouter.lexer.section.departure.Departure;
import com.flydenver.bagrouter.lexer.section.departure.DepartureParser;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.flydenver.bagrouter.domain.PassengerBag.BagState;
import com.flydenver.bagrouter.lexer.ParseException;

import static org.junit.Assert.*;


/**
 * Section parsing tests.
 */
public class SectionParserTest {
	
	private final static String departureRow = "UA17 A4 MHT 09:15";
	private final static String conveyorRow = "A3 A4 1";
	private final static String bagRow = "0004 A8 UA18";

	private DepartureParser validDepartureParser;
	private BagRouteParser validBagParser;
	private ConveyorRouteParser validConveyorParser;

	@Rule
	public ExpectedException nullReaderException = ExpectedException.none();
	
	
	@Before
	public void startup() {
		validBagParser = new BagRouteParser();
		validConveyorParser = new ConveyorRouteParser();
		validDepartureParser = new DepartureParser();

		validBagParser.setSectionInput( new RoutingInput( "/routing-input.txt" ) );
		validConveyorParser.setSectionInput( new RoutingInput( getClass().getResourceAsStream( "/routing-input.txt" ) ) );
		validDepartureParser.setSectionInput( new RoutingInput( getClass().getResourceAsStream( "/routing-input.txt" ) ) );
	}
	
	@After
	public void teardown() {
		validBagParser.closeQuietly();
		validConveyorParser.closeQuietly();
		validDepartureParser.closeQuietly();
	}

	@Test
	public void testGoodSectionLineParsing() {
		try {
			validBagParser.parseSectionLines(bagRoute -> {
				assertNotNull( bagRoute.getBag() );
				assertNotNull( bagRoute.getEntryPoint() );
				if ( ! BagState.ARRIVAL.equals( bagRoute.getBag().getBagState() ) ) {
					assertNotNull( bagRoute.getFlight() );
				}
				//System.out.println(bagRoute.getBag().getBagNumber());
			});
			//System.out.println("--------");

			validDepartureParser.parseSectionLines(departure -> {
				assertNotNull( departure.getFlight() );
				assertNotNull( departure.getDestination() );
				assertNotNull( departure.getFlightGate() );
				assertNotNull( departure.getFlightTime() );
				//System.out.println(departure.getFlight().getFlightId());
			});
			//System.out.println("--------");

			validConveyorParser.parseSectionLines(conveyor -> {
				assertNotNull( conveyor.getFirstTerminal() );
				assertNotNull( conveyor.getSecondTerminal() );
				assertNotNull( conveyor.getTravelTime() );
				//System.out.println(conveyor.getFirstTerminal().getGateNumber());
			});
		}
		catch ( ParseException e ) {
			e.printStackTrace();
			fail( e.getMessage() );
		}
	}


	@Test
	public void testNullSectionReaderDeparture() throws ParseException {
		nullReaderException.expect( IllegalArgumentException.class );
		nullReaderException.expectMessage( "Null reader" );
		DepartureParser departureParser = new DepartureParser();
		departureParser.setSectionInput( null );
        departureParser.parseSectionLines(departure -> {});
	}

	@Test
	public void testNullSectionReaderBag() throws ParseException {
		nullReaderException.expect( IllegalArgumentException.class );
		nullReaderException.expectMessage( "Null reader" );
        BagRouteParser bagParser = new BagRouteParser();
        bagParser.setSectionInput( null );
        bagParser.parseSectionLines(bag -> {});
	}

	@Test
	public void testNullSectionReaderConveyor() throws ParseException {
		nullReaderException.expect( IllegalArgumentException.class );
		nullReaderException.expectMessage( "Null reader" );
        ConveyorRouteParser conveyorParser = new ConveyorRouteParser();
        conveyorParser.setSectionInput( null );
        conveyorParser.parseSectionLines(row -> {});
	}

	@Test
	public void testEmptySectionReaderDeparture() throws ParseException {
		nullReaderException.expect( ParseException.class );
		nullReaderException.expectMessage( "Reader not set" );
		new DepartureParser().parseSectionLines( departure -> {} );
	}

	@Test
	public void testEmptySectionReaderBag() throws ParseException {
		nullReaderException.expect( ParseException.class );
		nullReaderException.expectMessage( "Reader not set" );
        new BagRouteParser().parseSectionLines(bag -> {});
	}

	@Test
	public void testEmptySectionReaderConveyor() throws ParseException {
		nullReaderException.expect( ParseException.class );
		nullReaderException.expectMessage( "Reader not set" );
        new ConveyorRouteParser().parseSectionLines(row -> {});
	}

	@Test
	public void testSectionParsingException() {
		final IllegalArgumentException testError = new IllegalArgumentException( "Trigger catch block" );
		ParseException pe = null;
		try {
			validDepartureParser.setSectionInput( new RoutingInput( new StringReader( "# Section: Departures\n" + departureRow ) ) );
			validDepartureParser.parseSectionLines( row -> { throw testError; } );
		}
		catch ( ParseException e ) {
			pe = e;
			assertNotNull( e );
			assertEquals( testError, e.getCause() );
		}
		assertTrue( validDepartureParser.getSectionInput().wasExceptionIterating() );
		assertEquals( pe, validDepartureParser.getSectionInput().getParseException() );
	}

	@Test( expected = ParseException.class )
	public void testNullSectionTypeDeparture() throws ParseException {
		DepartureParser departureParser = new DepartureParser();
		departureParser.setSectionInput( new RoutingInput( new StringReader( departureRow ) ) );
		departureParser.setSectionType( null );
        departureParser.parseSectionLines(row -> {});
	}

	@Test( expected = ParseException.class )
	public void testNullSectionTypeBag() throws ParseException {
        BagRouteParser bagParser = new BagRouteParser();
        bagParser.setSectionInput( new RoutingInput( new StringReader( bagRow ) ) );
		bagParser.setSectionType( null );
		bagParser.parseSectionLines(row -> {});
	}

	@Test( expected = ParseException.class )
	public void testNullSectionTypeConveyor() throws ParseException {
        ConveyorRouteParser conveyorParser = new ConveyorRouteParser();
        conveyorParser.setSectionInput( new RoutingInput( new StringReader( conveyorRow ) ) );
        conveyorParser.setSectionType( null );
        conveyorParser.parseSectionLines(departure -> {});
	}

	@Test( expected = ParseException.class )
	public void testInvalidSectionTypeDeparture() throws ParseException {
		DepartureParser departureParser = new DepartureParser();
		departureParser.setSectionInput( new RoutingInput( new StringReader( departureRow ) ) );
		departureParser.setSectionType( SectionType.UNKNOWN );
        departureParser.parseSectionLines(departure -> {});
	}
	
	@Test( expected = ParseException.class )
	public void testInvalidSectionTypeBag() throws ParseException {
        BagRouteParser bagParser = new BagRouteParser();
        bagParser.setSectionInput( new RoutingInput( new StringReader( bagRow ) ) );
        bagParser.setSectionType( SectionType.UNKNOWN );
        bagParser.parseSectionLines(bag -> {});
	}
	
	@Test( expected = ParseException.class )
	public void testInvalidSectionTypeConveyor() throws ParseException {
        ConveyorRouteParser conveyorParser = new ConveyorRouteParser();
        conveyorParser.setSectionInput( new RoutingInput( new StringReader( conveyorRow ) ) );
		conveyorParser.setSectionType( SectionType.UNKNOWN );
		conveyorParser.parseSectionLines(conveyor -> {});
	}
	
	@Test( expected = IllegalArgumentException.class )
	public void testNullConsumerDeparture() throws ParseException {
		DepartureParser departureParser = new DepartureParser();
		departureParser.setSectionInput( new RoutingInput( new StringReader( departureRow ) ) );
        departureParser.parseSectionLines( null );
	}

	@Test( expected = IllegalArgumentException.class )
	public void testNullConsumerBag() throws ParseException {
        BagRouteParser bagParser = new BagRouteParser();
        bagParser.setSectionInput( new RoutingInput( new StringReader( bagRow ) ) );
		bagParser.parseSectionLines( null );
	}

	@Test( expected = IllegalArgumentException.class )
	public void testNullConsumerConveyor() throws ParseException {
        ConveyorRouteParser conveyorParser = new ConveyorRouteParser();
        conveyorParser.setSectionInput( new RoutingInput( new StringReader( conveyorRow ) ) );
        conveyorParser.parseSectionLines( null );
	}

	@Test
	public void testMixedSectionTypeDeparture() throws ParseException {
		DepartureParser departureParser = new DepartureParser();
		departureParser.setSectionInput( new RoutingInput( new StringReader( departureRow ) ) );
		departureParser.setSectionType( SectionType.CONVEYOR_SYSTEM );
		List<Departure> departures = new ArrayList<>( 2 );
        departureParser.parseSectionLines( departures::add );
		assertEquals( departures.size(), 0 );
	}

	@Test
	public void testMixedSectionTypeBag() throws ParseException {
        BagRouteParser bagParser = new BagRouteParser();
        bagParser.setSectionInput( new RoutingInput( new StringReader( bagRow ) ) );
        bagParser.setSectionType( SectionType.BAGS );
		List<BagRoute> routes = new ArrayList<>( 2 );
        bagParser.parseSectionLines( routes::add );
		assertEquals( routes.size(), 0 );
	}

	@Test
	public void testMixedSectionTypeConveyor() throws ParseException {
        ConveyorRouteParser conveyorParser = new ConveyorRouteParser();
        conveyorParser.setSectionInput( new RoutingInput( new StringReader( conveyorRow ) ) );
		conveyorParser.setSectionType( SectionType.DEPARTURES );
		List<ConveyorRoute> routes = new ArrayList<>( 2 );
		conveyorParser.parseSectionLines( routes::add );
		assertEquals( routes.size(), 0 );
	}

	public final static void main( String [] args ) throws ParseException {
		SectionParserTest st = new SectionParserTest();
		st.startup();
		st.testSectionParsingException();
		st.teardown();
	}

}
