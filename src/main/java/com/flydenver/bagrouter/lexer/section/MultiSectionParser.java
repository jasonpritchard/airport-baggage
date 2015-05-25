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

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;


/**
 * Wrapper for parsing multiple section types.
 */
public class MultiSectionParser {

	private final Map<SectionType, RowParsingStrategy<? extends SectionEntry>> parserList = new HashMap<>( 100 );
	private final Map<SectionType, SectionParsingConsumer<SectionEntry>> consumerList = new HashMap<>( 100 );

	//	Input we plan on parsing
	private RoutingInput routingInput;


	/** Add a row parsing strategy for a given type. */
	public <T extends SectionEntry> void addRowParser( SectionType type, RowParsingStrategy<T> parsingStrategy ) {
		parserList.put( type, parsingStrategy );
	}


	/** Add a row parsing consumer for a given type. */
	public void addSectionConsumer( SectionType type, SectionParsingConsumer<SectionEntry> consumer ) {
		consumerList.put( type, consumer );
	}


	/**
	 * Parse through the {@link RoutingInput} looking for the {@link SectionType}.
	 * Once a section is found, give each line to the {@link Consumer} for processing.
	 */
	public void parseSections( ) throws ParseException {
		checkReadyToParse();

		getSectionInput().forEachLine( ( type, line ) -> {
			if ( parserList.containsKey( type ) && consumerList.containsKey( type ) ) {
				RowParsingStrategy<?> sConsumer = parserList.get( type );
				consumerList.get( type ).accept( sConsumer.parseSectionRow( line ).getWrappedRow() );
			}
		});

		getSectionInput().closeQuietly();
	}


	/** Set the reader input. */
	public void setSectionInput( RoutingInput sectionReader ) {
		if ( sectionReader == null ) {
			throw new IllegalArgumentException( "Null reader" );
		}

		this.routingInput = sectionReader;
	}


	/** Get the input reader. */
	private RoutingInput getSectionInput() {
		return routingInput;
	}


	/**
	 * Checks that all of the necessary parts are ready for parsing. This will
	 * throw a {@link ParseException} if something is missing.
	 */
	protected void checkReadyToParse() throws ParseException {
		if ( getSectionInput() == null ) {
			throw new ParseException( "Reader not set." );
		}
	}

}
