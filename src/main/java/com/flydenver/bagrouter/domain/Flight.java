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

package com.flydenver.bagrouter.domain;

import java.util.Date;

/**
 * Domain object for flights.
 */
public class Flight implements Identifiable<FlightId> {

	private FlightId flightId;
	private Airport destination;
	private TerminalGate gate;
	private Date flightTime;

	public Flight() {}
	public Flight(FlightId flightId) {
		setFlightId(flightId);
	}

	/**
	 * Get the flight ID
	 */
	public FlightId getFlightId() {
		return flightId;
	}

	/**
	 * Set the flight ID
	 */
	public void setFlightId(FlightId flightId) {
		this.flightId = flightId;
	}

	/**
	 * Check if the flight id is set.
	 */
	public boolean isSetFlightId() {
		return flightId != null;
	}

	/**
	 * Get the destination airport
	 */
	public Airport getDestination() {
		return destination;
	}

	/**
	 * Set the destination airport
	 */
	public void setDestination(Airport destination) {
		this.destination = destination;
	}

	/**
	 * Check if the destination is set.
	 */
	public boolean isSetDestination() {
		return destination != null;
	}

	/**
	 * Set the flight gate
	 */
	public TerminalGate getGate() {
		return gate;
	}

	/**
	 * Get the arrival gate
	 */
	public void setGate(TerminalGate gate) {
		this.gate = gate;
	}

	/**
	 * Check if the gate is set.
	 */
	public boolean isSetGate() {
		return gate != null;
	}

	/**
	 * Get the flight ID
	 */
	public Date getFlightTime() {
		return flightTime;
	}

	/**
	 * Set the flight ID
	 */
	public void setFlightTime(Date flightTime) {
		this.flightTime = flightTime;
	}

	/**
	 * Check if the flight time is set.
	 */
	public boolean isSetFlightTime() {
		return flightTime != null;
	}

	@Override
	public FlightId getId() {
		return getFlightId();
	}

	@Override
	public boolean equals( Object obj ) {
		return ( !isSetFlightId() || ! ( obj instanceof Flight ) ) ?
			   super.equals( obj ) :
			   ((Flight) obj).getFlightId().equals( getFlightId() );
	}

	@Override
	public String toString() {
		return getId().getId();
	}

}
