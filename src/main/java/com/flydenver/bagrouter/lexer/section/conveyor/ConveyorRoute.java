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

import com.flydenver.bagrouter.domain.TerminalGate;
import com.flydenver.bagrouter.lexer.section.SectionEntry;


/**
 * One route in the the conveyor system. Conveyor system is described
 * by the conveyor section of the routing files.
 */
public class ConveyorRoute implements SectionEntry {

	private TerminalGate firstNode;
	private TerminalGate secondNode;
	private int flightTime;

	/**
	 * Get the first gate node.
	 */
	public TerminalGate getFirstTerminal() {
		return firstNode;
	}

	/**
	 * Set the first gate node.
	 */
	public void setFirstTerminal( TerminalGate firstNode ) {
		this.firstNode = firstNode;
	}

	/**
	 * Get the second gate node.
	 */
	public TerminalGate getSecondTerminal() {
		return secondNode;
	}

	/**
	 * Set the second gate node.
	 */
	public void setSecondTermina( TerminalGate secondNode ) {
		this.secondNode = secondNode;
	}

	/**
	 * Get the route flight time.
	 */
	public int getFlightTime() {
		return flightTime;
	}

	/**
	 * Set the route flight time.
	 */
	public void setFlightTime(int flightTime) {
		this.flightTime = flightTime;
	}

}
