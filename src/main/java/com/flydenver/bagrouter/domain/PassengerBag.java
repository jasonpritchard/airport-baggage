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

/**
 * Domain object for passenger's bag.
 */
public class PassengerBag {

	private String bagNumber;
	private TerminalGate entryGate;
	private Flight flight;
	private BagState state = BagState.UNKNOWN;

	public PassengerBag(String bagNumber) {
		setBagNumber(bagNumber);
	}

	/**
	 * Get the bag ID
	 */
	public String getBagNumber() {
		return this.bagNumber;
	}

	/**
	 * Set the bag ID
	 */
	public void setBagNumber( String bagNumber ) {
		this.bagNumber = bagNumber;
	}

	/**
	 * Get the entry gate.
	 */
	public TerminalGate getEntryGate() {
		return this.entryGate;
	}

	/**
	 * Set the entry gate.
	 */
	public void setEntryGate( TerminalGate entryGate ) {
		this.entryGate = entryGate;
	}

	/**
	 * Get the flight this bag is on.
	 */
	public Flight getFlight() {
		return this.flight;
	}

	/**
	 * Set the flight this bag is on.
	 */
	public void setFlight( Flight flight ) {
		this.flight = flight;
	}

	/**
	 * Get the current bag state.
	 */
	public BagState getBagState() {
		return this.state;
	}

	/**
	 * Set the current bag state.
	 */
	public void setBagState( PassengerBag.BagState state ) {
		this.state = state;
	}


	/**
	 * Bag state
	 */
	public enum BagState {
		/**
		 * Oops, we don't know where your bag is.
		 */
		UNKNOWN,

		/**
		 * Bag is in a holding stage.
		 */
		WAITING,

		/**
		 * Bag is on the flight.
		 */
		IN_TRANSIT,

		/**
		 * Bag is read for arrival.
		 */
		ARRIVAL,

		/**
		 * Bag has been picked up.
		 */
		CLAIMED,

		/**
		 * Bag wasn't picked up, and is held.
		 */
		UNCLAIMED
	}


	@Override
	public boolean equals( Object obj ) {
		return ( getBagNumber() == null || ! (obj instanceof PassengerBag) ) ?
			   super.equals( obj ) :
			   ((PassengerBag) obj).getBagNumber().equals( getBagNumber() );
	}
}
