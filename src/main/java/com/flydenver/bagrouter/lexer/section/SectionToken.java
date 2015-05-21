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

/**
 * Section tokens identify different parts of the routing input.
 */
public enum SectionToken {

	/**
	 * Section marker for the Conveyor System.
	 */
	CONVEYOR_SYSTEM( "Conveyor System" ),
	
	/**
	 * Section marker for Departures.
	 */
	DEPARTURES( "Departures" ),
	
	/**
	 * Section marker for bag routes.
	 */
	BAGS( "Bags" ),
	
	/**
	 * Invalid token.
	 */
	UNKNOWN( "" );
	
	/**
	 * Fetch the token by the identifier.
	 * @param identifier string identifier of the token
	 * @return the token, or unknown
	 */
	public static SectionToken fromIdentifier( String identifier ) {
		if ( identifier != null && ! identifier.trim().equals("") ) {
			for ( SectionToken token : values() ) {
				if ( identifier.equalsIgnoreCase( token.getIdentifier() ) ) {
					return token;
				}
			}
		}
		
		return SectionToken.UNKNOWN;
	}
	
	
	//	ID string
	private final String identifier;
	public String getIdentifier() { return this.identifier; }
	SectionToken( String identifier ) { this.identifier = identifier; }

}
