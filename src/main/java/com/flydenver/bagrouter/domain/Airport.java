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
 * Domain object for the airport
 */
public class Airport implements Identifiable<String> {

	private String airportId;
	//private List<TerminalGate> gates = new ArrayList<>( 20 );;

	//public Airport() {}
	public Airport(String airportId) {
		setAirportId(airportId);
	}

	/**
	 * Set the airport ID.
	 */
	public void setAirportId( String airportId ) {
		this.airportId = airportId;
	}

	/**
	 * Get the airport id.
	 */
	public String getAirportId() {
		return airportId;
	}

	@Override
	public String getId() {
		return getAirportId();
	}

	@Override
	public boolean equals( Object obj ) {
		return ( getAirportId() == null || ! ( obj instanceof Airport) ) ? super.equals( obj ) : ((Airport) obj).getAirportId().equals( getAirportId() );
	}

	@Override
	public String toString() {
		return getId();
	}

}
