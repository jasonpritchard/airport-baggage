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

import com.flydenver.bagrouter.lexer.section.SectionParser;


/**
 * Main entry point for parsing bag router files. This is just a facade for the
 * underlying {@link RoutingInput} and {@link com.flydenver.bagrouter.lexer.section.RowParsingDelegate} pieces.
 */
public class RoutingEvaluator {

	/**
	 * Create a {@link SectionParser}. This allows you to parse multiple types of section
	 * data on a single callback. The problem with the other consumes is that you must supply
	 * individual readers for each parser. This will handle closing the input.
	 *
	 * @param input {@link RoutingInput} input of routing data
	 */
	public static SectionParser multiSectionParser( RoutingInput input ) throws ParseException {
		SectionParser parser = new SectionParser( );
		parser.setSectionInput( input );
		return parser;
	}

}
