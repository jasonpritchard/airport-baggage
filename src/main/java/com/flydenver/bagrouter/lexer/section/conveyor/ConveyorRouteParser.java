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

package com.flydenver.bagrouter.lexer.section.conveyor;

import com.flydenver.bagrouter.lexer.section.SectionParser;
import com.flydenver.bagrouter.lexer.section.SectionType;


/**
 * {@link SectionParser} for parsing the conveyor route section. Conveyor routes are given by
 * the rules:
 * <pre>
 * Section 1: A weighted bi-directional graph describing the conveyor system.
 *     Format: &lt;Node 1&gt; &lt;Node 2&gt; &lt;travel_time&gt;
 * </pre><pre>
 * Example:
 *    A5 A1 6
 * </pre>
 */
public class ConveyorRouteParser extends SectionParser<ConveyorRoute> {

	/**
	 * Create a parser for parsing the {@link ConveyorRoute} section.
	 */
	public ConveyorRouteParser() {
		setSectionType( SectionType.CONVEYOR_SYSTEM );
		setRowParser( new ConveyorRowParser() );
	}

}
