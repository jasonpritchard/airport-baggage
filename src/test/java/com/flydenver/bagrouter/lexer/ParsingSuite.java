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

import com.flydenver.bagrouter.lexer.section.bag.BagEntryTest;
import com.flydenver.bagrouter.lexer.section.conveyor.ConveyorRouteTest;
import com.flydenver.bagrouter.lexer.section.departure.DepartureParserTest;
import com.flydenver.bagrouter.lexer.section.departure.DepartureTest;
import com.flydenver.bagrouter.lexer.section.SectionHeaderTokenizerTest;
import com.flydenver.bagrouter.lexer.section.SectionTokenTest;
import com.flydenver.bagrouter.lexer.section.SectionTypeTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Suite for parsing tests
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
		DepartureParserTest.class,
		ConveyorRouteTest.class,
		BagEntryTest.class,
		SectionTypeTest.class,
		SectionTokenTest.class,
		DepartureTest.class,
		SectionHeaderTokenizerTest.class,
		RoutingEvaluatorTest.class
})
public class ParsingSuite {}
