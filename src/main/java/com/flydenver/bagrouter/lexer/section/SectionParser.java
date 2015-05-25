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

import com.flydenver.bagrouter.lexer.ParseException;
import com.flydenver.bagrouter.lexer.RoutingInput;

import java.util.function.Consumer;


/**
 * Section parsing classes. This is meant to parse certain specified sections of a
 * {@link RoutingInput} and feed them to a {@link Consumer} of those {@link SectionType}s.
 */
public abstract class SectionParser<T extends SectionEntry> {

	//	Input we plan on parsing
	private RoutingInput routingInput;
	
	//	Which section we're interested in
	private SectionType sectionType = SectionType.UNKNOWN;

	//	Strategy for parsing individual section rows
	private RowParsingDelegate<T> rowParser;


	/**
	 * Parse through the {@link RoutingInput} looking for the {@link SectionType}.
	 * Once a section is found, give each line to the {@link Consumer} for processing. 
	 *
	 * @param consumer callback to deal with section lines.
	 * @throws ParseException
	 */
	public void parseSectionLines( Consumer<T> consumer) throws ParseException {
		if ( consumer == null ) { throw new IllegalArgumentException( "Null consumer" ); }

		checkReadyToParse();

		getSectionInput().forEachLine((type, line) -> {
			if ( type.equals( getSectionType() ) ) {
				consumer.accept( getRowParser().parseSectionRow( line ).getWrappedRow() );
			}
		});
	}


	/**
	 * Row parsing getter
	 */
	protected RowParsingDelegate<T> getRowParser() {
		return rowParser;
	}

	/**
	 * Set the row parsing strategy
	 */
	protected void setRowParser( RowParsingDelegate<T> rowParser ) {
		this.rowParser = rowParser;
	}

	/**
	 * Close the parser and swallow the exceptions
	 */
	public boolean closeQuietly() {
		return routingInput != null && routingInput.closeQuietly();
	}

	/**
	 * Set the reader input.
	 */
	public void setSectionInput( RoutingInput sectionReader ) {
		if ( sectionReader == null ) {
			throw new IllegalArgumentException( "Null reader" );
		}

		this.routingInput = sectionReader;
	}

	/**
	 * Get the input reader.
	 */
	public RoutingInput getSectionInput() {
		return routingInput;
	}

	/**
	 * Set the section identifier we're looking for.
	 */
	public void setSectionType( SectionType sectionType ) {
		this.sectionType = sectionType;
	}

	/**
	 * Get the section identifier to look for.
	 */
	protected SectionType getSectionType() {
		return sectionType == null ? SectionType.UNKNOWN : sectionType;
	}

	/**
	 * Checks that all of the necessary parts are ready for parsing. This will
	 * throw a {@link ParseException} if something is missing.
	 */
	protected void checkReadyToParse() throws ParseException {
		if ( getRowParser() == null ) {
			throw new ParseException( "Row parser not set." );
		}

		if ( getSectionInput() == null ) {
			throw new ParseException( "Reader not set." );
		}

		if ( SectionType.UNKNOWN.equals( getSectionType() ) ) {
			throw new ParseException( "Must set section type first." );
		}
	}

}
