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
import com.flydenver.bagrouter.domain.PassengerBag;
import com.flydenver.bagrouter.domain.TerminalGate;
import com.flydenver.bagrouter.lexer.section.SectionEntry;

/**
 * Bag route mapping described in the bag list section for the
 * route input files.
 */
public class BagRoute implements SectionEntry {
	private PassengerBag bag;
	private TerminalGate entryPoint;
	private Flight flight;

	/**
	 * Get the bag.
	 */
	public PassengerBag getBag() {
		return bag;
	}

	/**
	 * Set the bag.
	 */
	public void setBag(PassengerBag bag) {
		this.bag = bag;
	}

	/**
	 * Get the entry point for the bag.
	 */
	public TerminalGate getEntryPoint() {
		return entryPoint;
	}

	/**
	 * Set the entry gate for the bag.
	 */
	public void setEntryPoint(TerminalGate entryPoint) {
		this.entryPoint = entryPoint;
	}

	/**
	 * Get the bag's flight.
	 */
	public Flight getFlight() {
		return flight;
	}

	/**
	 * Set the flight the bag is on.
	 */
	public void setFlight(Flight flight) {
		this.flight = flight;
	}

}
