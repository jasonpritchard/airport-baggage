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

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Section header splitter.
 */
public class SectionHeaderTokenizer {

	private final String fullLine;

	// this is overkill for a one-token section header, but maybe headers change
	private Map<String, String> tokens = new HashMap<>( 4 );
	protected Map<String, String> getTokens() { return tokens; }
	
	//	token keys
	private final static String sectionMarkerTokenKey = "sectionMarker";
	private final static String headerTokenKey        = "sectionHeader";
	
	private final static Pattern sectionHeaderStartPattern =
			Pattern.compile(
					"^(?:[" + SectionHeaderToken.SECTION_START  + "]" +
					"\\s*"  + SectionHeaderToken.SECTION_HEADER +
					"\\s*[" + SectionHeaderToken.SECTION_SPLIT  + "]" +
					"\\s*)((\\w+\\s*)+)$");


	/**
	 * Check the line to see if it's a section header. If the line contains a section
	 * header, a tokenizer is returned. Otherwise, null is returned.
	 *
	 * @param line line to check
	 * @return a tokenizer for the given line
	 */
	public static SectionHeaderTokenizer checkLineForSectionHeader( String line ) {
		if ( line == null ) {
			return null;
		}

		line = line.trim();
		return sectionHeaderStartPattern.matcher( line ).matches() ? new SectionHeaderTokenizer( line ) : null;
	}

	/**
	 * Tokenize a section header line.
	 */
	protected SectionHeaderTokenizer( String fullLine ) {
		this.fullLine = fullLine;
		tokenizeSectionHeader();
	}

	/**
	 * Split up the header
	 */
	protected void tokenizeSectionHeader() {
		Matcher matcher = sectionHeaderStartPattern.matcher( this.fullLine );
		if ( matcher.find() && matcher.groupCount() > 0 ) {
			String token = matcher.group( 0 );
			if ( token != null ) { 
				getTokens().put( sectionMarkerTokenKey, token.trim() );
			}

			token = matcher.group( 1 );
			if ( token != null ) {
				getTokens().put( headerTokenKey, token.trim() );
			}
		}
	}

	/**
	 * Get the section header name, or null if none was found  
	 * in the original line from {@code #checkLineForSectionHeader(String)}.
	 * 
	 * @return header name, or null
	 */
	public String getSectionHeader() {
		return getTokens().containsKey( headerTokenKey ) ? getTokens().get( headerTokenKey ).trim() : null;
	}


	/**
	 * Section tokens identify different parts of the routing input.
	 */
	public enum SectionHeaderToken {

		/**
		 * Section header start marker.
		 */
		SECTION_START( "#" ),

		/**
		 * Section header marker.
		 */
		SECTION_HEADER( "Section" ),

		/**
		 * Separator that splits section header and section title.
		 */
		SECTION_SPLIT( ":" ),

		/**
		 * Invalid token.
		 */
		UNKNOWN( "" );

		/**
		 * Fetch the token by the identifier.
		 * @param identifier string identifier of the token
		 * @return the token, or unknown
		 */
		public static SectionHeaderToken fromIdentifier( String identifier ) {
			if ( identifier != null && ! identifier.trim().equals("") ) {
				for ( SectionHeaderToken token : values() ) {
					if ( token.getToken().equalsIgnoreCase( identifier.trim() ) ) {
						return token;
					}
				}
			}

			return SectionHeaderToken.UNKNOWN;
		}

		//	section header token
		private final String token;
		public String getToken() { return this.token; }
		public String toString() { return getToken(); }
		SectionHeaderToken( String token ) { this.token = token; }

	}

}
