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

package com.flydenver.bagrouter.lexer;

import static org.junit.Assert.assertNotNull;

import com.flydenver.bagrouter.domain.PassengerBag;
import com.flydenver.bagrouter.lexer.section.SectionParser;
import com.flydenver.bagrouter.lexer.section.SectionType;
import com.flydenver.bagrouter.lexer.section.bag.BagEntry;
import com.flydenver.bagrouter.lexer.section.bag.BagRowParser;
import com.flydenver.bagrouter.lexer.section.conveyor.ConveyorRoute;
import com.flydenver.bagrouter.lexer.section.conveyor.ConveyorRowParser;
import com.flydenver.bagrouter.lexer.section.departure.Departure;
import com.flydenver.bagrouter.lexer.section.departure.DepartureRowParser;
import org.junit.Test;

import java.io.InputStreamReader;


public class RoutingEvaluatorTest {


	@Test
	public void testEvaluatorInputParsing() throws ParseException {

		SectionParser parser = RoutingEvaluator.multiSectionParser( new RoutingInput( "routing-input.txt" ) );
		parser.addSectionConsumer( SectionType.BAGS, new BagRowParser(), entry -> {
			BagEntry bag = (BagEntry) entry;
			assertNotNull( bag.getBag() );
			assertNotNull( bag.getEntryPoint() );
			if (!PassengerBag.BagState.ARRIVAL.equals( bag.getBag().getBagState() )) {
				assertNotNull( bag.getFlight() );
			}
		});

		parser.addSectionConsumer( SectionType.DEPARTURES, new DepartureRowParser(), entry -> {
			Departure departure = (Departure)entry;
			assertNotNull( departure.getFlight() );
			assertNotNull( departure.getDestination() );
			assertNotNull( departure.getFlightGate() );
			assertNotNull( departure.getFlightTime() );
		});

		parser.addSectionConsumer( SectionType.CONVEYOR_SYSTEM, new ConveyorRowParser(), entry -> {
			ConveyorRoute conveyor = (ConveyorRoute)entry;
			assertNotNull( conveyor.getFirstTerminal() );
			assertNotNull( conveyor.getSecondTerminal() );
			assertNotNull( conveyor.getTravelTime() );
		});

		parser.parseSections();

	}

	@Test
	public void testEvaluatorInputReaderParsing() throws ParseException {

		InputStreamReader reader = new InputStreamReader( getClass().getResourceAsStream( "/routing-input.txt" ) );

		SectionParser parser = RoutingEvaluator.multiSectionParser( new RoutingInput( reader ) );
		parser.addSectionConsumer( SectionType.BAGS, new BagRowParser(), entry -> {
			BagEntry bag = (BagEntry) entry;
			assertNotNull( bag.getBag() );
			assertNotNull( bag.getEntryPoint() );
			if (!PassengerBag.BagState.ARRIVAL.equals( bag.getBag().getBagState() )) {
				assertNotNull( bag.getFlight() );
			}
		});

		parser.addSectionConsumer( SectionType.DEPARTURES, new DepartureRowParser(), entry -> {
			Departure departure = (Departure)entry;
			assertNotNull( departure.getFlight() );
			assertNotNull( departure.getDestination() );
			assertNotNull( departure.getFlightGate() );
			assertNotNull( departure.getFlightTime() );
		});

		parser.addSectionConsumer( SectionType.CONVEYOR_SYSTEM, new ConveyorRowParser(), entry -> {
			ConveyorRoute conveyor = (ConveyorRoute)entry;
			assertNotNull( conveyor.getFirstTerminal() );
			assertNotNull( conveyor.getSecondTerminal() );
			assertNotNull( conveyor.getTravelTime() );
		});

		parser.parseSections();

	}

	@Test
	public void testEvaluatorInputStreamParsing() throws ParseException {

		SectionParser parser = RoutingEvaluator.multiSectionParser( new RoutingInput( getClass().getResourceAsStream( "/routing-input.txt" ) ) );
		parser.addSectionConsumer( SectionType.BAGS, new BagRowParser(), entry -> {
			BagEntry bag = (BagEntry) entry;
			assertNotNull( bag.getBag() );
			assertNotNull( bag.getEntryPoint() );
			if (!PassengerBag.BagState.ARRIVAL.equals( bag.getBag().getBagState() )) {
				assertNotNull( bag.getFlight() );
			}
		});

		parser.addSectionConsumer( SectionType.DEPARTURES, new DepartureRowParser(), entry -> {
			Departure departure = (Departure)entry;
			assertNotNull( departure.getFlight() );
			assertNotNull( departure.getDestination() );
			assertNotNull( departure.getFlightGate() );
			assertNotNull( departure.getFlightTime() );
		});

		parser.addSectionConsumer( SectionType.CONVEYOR_SYSTEM, new ConveyorRowParser(), entry -> {
			ConveyorRoute conveyor = (ConveyorRoute)entry;
			assertNotNull( conveyor.getFirstTerminal() );
			assertNotNull( conveyor.getSecondTerminal() );
			assertNotNull( conveyor.getTravelTime() );
		});

		parser.parseSections();

	}


	@Test
	public void testEvaluatorInputParsingOneSection() throws ParseException {

		SectionParser parser = RoutingEvaluator.multiSectionParser( new RoutingInput( "routing-input.txt" ) );
		parser.addSectionConsumer( SectionType.BAGS, new BagRowParser(), entry -> {
			BagEntry bag = (BagEntry) entry;
			assertNotNull( bag.getBag() );
			assertNotNull( bag.getEntryPoint() );
			if (!PassengerBag.BagState.ARRIVAL.equals( bag.getBag().getBagState() )) {
				assertNotNull( bag.getFlight() );
			}
		});
		parser.parseSections();

	}

	@Test (expected =  IllegalArgumentException.class)
	public void testEvaluatorNullInput() throws ParseException {

		SectionParser parser = RoutingEvaluator.multiSectionParser( null );
		parser.addSectionConsumer( SectionType.BAGS, new BagRowParser(), entry -> {
			BagEntry bag = (BagEntry) entry;
			assertNotNull( bag.getBag() );
			assertNotNull( bag.getEntryPoint() );
			if (!PassengerBag.BagState.ARRIVAL.equals( bag.getBag().getBagState() )) {
				assertNotNull( bag.getFlight() );
			}
		});
		parser.parseSections();

	}



}
