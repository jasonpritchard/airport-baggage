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
import com.flydenver.bagrouter.lexer.section.bag.BagRoute;
import com.flydenver.bagrouter.lexer.section.conveyor.ConveyorRoute;
import com.flydenver.bagrouter.lexer.section.departure.Departure;
import org.junit.Test;

import java.io.Reader;


public class RoutingEvaluatorTest {

	@Test
	public void testEvaluatorInput() throws ParseException {

		RoutingEvaluator.parseRouting( "routing-input.txt", BagRoute.class, ( bag ) -> {
			assertNotNull( bag.getBag() );
			assertNotNull( bag.getEntryPoint() );
			if (!PassengerBag.BagState.ARRIVAL.equals( bag.getBag().getBagState() )) {
				assertNotNull( bag.getFlight() );
			}
		});

		RoutingEvaluator.parseRouting( "routing-input.txt", Departure.class, ( departure ) -> {
			assertNotNull( departure.getFlight() );
			assertNotNull( departure.getDestination() );
			assertNotNull( departure.getFlightGate() );
			assertNotNull( departure.getFlightTime() );
		});

		RoutingEvaluator.parseRouting( "routing-input.txt", ConveyorRoute.class, ( conveyor ) -> {
			assertNotNull( conveyor.getFirstTerminal() );
			assertNotNull( conveyor.getSecondTerminal() );
			assertNotNull( conveyor.getTravelTime() );
		});

	}

	@Test(expected = ParseException.class)
	public void testNullInputBag() throws ParseException {
		RoutingEvaluator.parseRouting("routing-input.txt", null, (bag) -> { });
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullInputDeparture() throws ParseException {
		RoutingEvaluator.parseRouting((Reader) null, Departure.class, (departure) -> { });
	}

	@Test(expected = ParseException.class)
	public void testNullInputConveyorRoute() throws ParseException {
		RoutingEvaluator.parseRouting(getClass().getResourceAsStream( "/routing-input.txt" ), ConveyorRoute.class, null);
	}

}
