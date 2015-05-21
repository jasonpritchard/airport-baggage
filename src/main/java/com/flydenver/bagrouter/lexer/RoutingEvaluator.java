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

import com.flydenver.bagrouter.lexer.section.SectionEntry;
import com.flydenver.bagrouter.lexer.section.SectionParser;
import com.flydenver.bagrouter.lexer.section.bag.BagRoute;
import com.flydenver.bagrouter.lexer.section.conveyor.ConveyorRoute;
import com.flydenver.bagrouter.lexer.section.departure.Departure;

import java.io.InputStream;
import java.io.Reader;
import java.util.function.Consumer;


/**
 * Main entry point for parsing bag router files. This is just a facade for the
 * underlying {@link RoutingInput} and {@link SectionParser} pieces.
 */
public class RoutingEvaluator {

	/**
	 * Parse an input source for data of the type {@code T}. {@code T} is one of the subtypes of
	 * {@link SectionEntry}:
	 * <ul>
	 *     <li>{@link BagRoute}</li>
	 *     <il>{@link ConveyorRoute}</il>
	 *     <li>{@link Departure}</li>
	 * </ul>
	 * This is a facade for the {@link SectionParser#parseSectionLines(Consumer)}.
	 *
	 * @see {@link SectionParser#parseSectionLines(Consumer)}
	 * @param routingFile relative path to file to parse
	 * @param clazz section type of data to parse
	 * @param consumer parsing callback for data of the type {@code clazz}
	 * @param <T> one of the {@link SectionEntry} types
	 */
	public static <T extends SectionEntry> void parseRouting( String routingFile,  Class<T> clazz, Consumer<T> consumer ) throws ParseException {
		parseRouting( new RoutingInput( routingFile ), clazz, consumer );
	}


	/**
	 * Parse an input source for data of the type {@code T}. {@code T} is one of the subtypes of
	 * {@link SectionEntry}:
	 * <ul>
	 *     <li>{@link BagRoute}</li>
	 *     <il>{@link ConveyorRoute}</il>
	 *     <li>{@link Departure}</li>
	 * </ul>
	 * This is a facade for the {@link SectionParser#parseSectionLines(Consumer)}.
	 *
	 * @see {@link SectionParser#parseSectionLines(Consumer)}
	 * @param routingStream input stream of routing data
	 * @param clazz section type of data to parse
	 * @param consumer parsing callback for data of the type {@code clazz}
	 * @param <T> one of the {@link SectionEntry} types
	 * @throws ParseException
	 */
	public static <T extends SectionEntry> void parseRouting( InputStream routingStream, Class<T> clazz, Consumer<T> consumer ) throws ParseException {
		parseRouting( new RoutingInput( routingStream ), clazz, consumer );
	}


	/**
	 * Parse an input source for data of the type {@code T}. {@code T} is one of the subtypes of
	 * {@link SectionEntry}:
	 * <ul>
	 *     <li>{@link BagRoute}</li>
	 *     <il>{@link ConveyorRoute}</il>
	 *     <li>{@link Departure}</li>
	 * </ul>
	 * This is a facade for the {@link SectionParser#parseSectionLines(Consumer)}.
	 *
	 * @see {@link SectionParser#parseSectionLines(Consumer)}
	 * @param routingReader input reader of routing data
	 * @param clazz section type of data to parse
	 * @param consumer parsing callback for data of the type {@code clazz}
	 * @param <T> one of the {@link SectionEntry} types
	 * @throws ParseException
	 */
	public static <T extends SectionEntry> void parseRouting( Reader routingReader, Class<T> clazz, Consumer<T> consumer ) throws ParseException {
		parseRouting( new RoutingInput( routingReader ), clazz, consumer );
	}


	//	Convenience for the above parse methods.
	//	This will handle closing the input.
	private static <T extends SectionEntry> void parseRouting( RoutingInput input, Class<T> clazz, Consumer<T> consumer ) throws ParseException {
		try {
			SectionParser<T> parser = getParserForParsedType( clazz );
			parser.setSectionInput( input );
			parser.parseSectionLines( consumer );
		}
		catch ( Exception e ) {
			if ( e instanceof ParseException ) {
				throw e;
			}
			throw new ParseException( e.getMessage(), e );
		}
		finally {
			input.closeQuietly();
		}
	}

	//	Get the parser for the type of section we want to parse. This takes advantage
	//	of the fact that all of the parser classes have the same name as the type of
	//	thing parsed with "Parser" at the end. This is fairly brittle.
	private static <T extends SectionEntry> SectionParser<T> getParserForParsedType( Class<T> clazz ) {
		SectionParser<T> parser ;

		try {
			@SuppressWarnings( "unchecked" )
			Class<? extends SectionParser<T>> parserClass =
					(Class<? extends SectionParser<T>>)Class.forName( clazz.getTypeName() + "Parser" );

			parser = parserClass.newInstance();
		}
		catch ( ClassNotFoundException | IllegalAccessException | InstantiationException e ) {
			throw new IllegalArgumentException( "Cannot build parser. " + e.getMessage(), e );
		}

		return parser;
	}

}
