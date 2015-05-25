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

import com.flydenver.bagrouter.lexer.section.SectionHeaderTokenizer;
import com.flydenver.bagrouter.lexer.section.SectionType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;


/**
 * This is a container for the input of sections into the parsers.
 */
public class RoutingInput {

	private final Reader reader;


	/**
	 * Construct an input using a relative file path.
	 */
	public RoutingInput( String relativePath ) {
		if ( relativePath == null ) { throw new IllegalArgumentException( "Null routing input" ); }
		relativePath = relativePath.startsWith( "/" ) ? relativePath : "/".concat( relativePath );
		this.reader = new InputStreamReader( getClass().getResourceAsStream( relativePath ) );
	}

	/**
	 * Construct an input using an input stream.
	 */
	public RoutingInput( InputStream in ) {
		if ( in == null ) { throw new IllegalArgumentException( "Null routing input" ); }
		this.reader = new InputStreamReader( in );
	}

	/**
	 * Construct an input using a reader.
	 */
	public RoutingInput( Reader reader ) {
		if ( reader == null ) { throw new IllegalArgumentException( "Null routing input" ); }
		this.reader = reader;
	}

	/**
	 * Get this input as a {@link Reader}.
	 */
	public Reader getAsReader() {
		return reader;
	}

	/**
	 * Close the routing input.
	 */
	public void close() throws IOException{
		if ( reader != null ) {
			reader.close();
		}
	}

	/**
	 * Close the parser and swallow the exceptions.
	 * @return true if the input was closed, false if there was a problem
	 *          closing the input.
	 */
	public boolean closeQuietly() {
		boolean ret = false;
		try {
			close();
			ret = true;
		}
		catch ( IOException e ) { /* ignore */ }
		return ret;
	}

	/**
	 * For line-based inputs, this iterates through the reader and issues the
	 * callback for each line in the input. If an exception occurs in the consumer,
	 * this will re-throw that exception at the end.
	 *
	 * @param lineConsumer consumer of the input's lines
	 */
	public void forEachLine( SectionConsumer<String> lineConsumer ) throws ParseException {
		if ( lineConsumer == null ) { throw new IllegalArgumentException( "Null consumer" ); }
		if ( getAsReader() == null ) { throw new ParseException( "Must set reader first." ); }

		try ( BufferedReader reader = new BufferedReader( getAsReader() ) ) {
			exceptionIterating = null;
			//reader.reset();

			String line;
			SectionType currentType = SectionType.UNKNOWN;

			while( (line = reader.readLine()) != null && ! line.equals( "" ) ) {
				SectionType itType = getSectionTokenForLine( line );
				if ( ! SectionType.UNKNOWN.equals( itType ) ) {
					currentType = itType;
					continue;
				}

				lineConsumer.accept( currentType, line );
			}

		}
		catch ( Exception e ) {
			ParseException pe = new ParseException( "Error parsing section input. " + e.getMessage(), e );
			exceptionIterating = pe;
			throw pe;
		}
	}


	private ParseException exceptionIterating;
	public ParseException getParseException() { return exceptionIterating; }
	public boolean wasExceptionIterating() { return exceptionIterating != null; }


	/**
	 * Consumer for sections of the routing input.
	 * @param <T> type of data to be fed by the routing input.
	 */
	public interface SectionConsumer<T> {
		void accept( SectionType section, T data ) throws Exception;
	}

	/**
	 * Try to find a section id based on a given line. This will return the
	 * {@link SectionType} for a given line, or UNKNOWN if the type cannot
	 * be found.
	 *
	 * @param line line to check for a section ID.
	 * @return The associated {@link SectionType}, or {@code SectionType.UNKNOWN}
	 */
	protected SectionType getSectionTokenForLine( String line ) {
		if ( line == null || line.trim().equals( "" ) ) {
			return SectionType.UNKNOWN;
		}

		SectionHeaderTokenizer tokenizer = SectionHeaderTokenizer.checkLineForSectionHeader( line );
		return ( tokenizer != null && tokenizer.getSectionHeader() != null ) ?
			   SectionType.fromIdentifier( tokenizer.getSectionHeader() ) :
			   SectionType.UNKNOWN;
	}

}
