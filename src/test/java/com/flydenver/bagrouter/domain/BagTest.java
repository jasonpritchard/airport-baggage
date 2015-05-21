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


import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


public class BagTest {

	private final PassengerBag bag1 = new PassengerBag( "001" );
	private final PassengerBag bag2 = new PassengerBag( "002" );
	private final PassengerBag bag3 = new PassengerBag( "003" );
	private final PassengerBag bag4 = new PassengerBag( "001" );

	@Test
	public void testBagsEqual() {
		assertEquals( bag1, bag4 );
		assertNotEquals( bag1, bag2 );
		assertNotEquals( bag1, bag3 );
		assertNotEquals( bag2, bag3 );
		assertNotEquals( bag2, bag4 );
		assertNotEquals( bag4, bag3 );

		assertNotEquals( bag1, null );
		assertNotEquals( bag2, null );
		assertNotEquals( bag3, null );
		assertNotEquals( bag4, null );
	}

}
