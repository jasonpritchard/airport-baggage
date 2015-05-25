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

package com.flydenver.bagrouter.util;

import com.flydenver.bagrouter.routing.Node;
import org.junit.Test;

import com.flydenver.bagrouter.domain.TerminalGate;
import com.flydenver.bagrouter.routing.Graph;
import com.flydenver.bagrouter.routing.WeightedEdge;
import com.flydenver.bagrouter.routing.WeightedGraph;

import static org.junit.Assert.assertTrue;


public class GenericUtilsTest {

	@Test
	public void testTypeCheck() {
		WeightedGraph<TerminalGate> t1 = new WeightedGraph<>();
		Graph<TerminalGate, WeightedEdge<TerminalGate>> t2 = new WeightedGraph<>();
		Graph<TerminalGate, WeightedEdge<TerminalGate>> t3 = new Graph<TerminalGate, WeightedEdge<TerminalGate>>() {
			@Override public void addEdge( Node<TerminalGate> firstNode, Node<TerminalGate> secondNode, int weight ) {}
		};


		assertTrue( GenericUtils.checkType( t1, WeightedEdge.class ) );
		assertTrue( GenericUtils.checkType( t2, WeightedEdge.class ) );
		assertTrue( GenericUtils.checkType( t3, TerminalGate.class ) );

	}


	@Test (expected = NullPointerException.class)
	public void testTypeNull1() {
		assertTrue( GenericUtils.checkType( new WeightedGraph<>(), null ) );

	}

	@Test (expected = NullPointerException.class)
	public void testTypeNull2() {
		assertTrue( GenericUtils.checkType( null, WeightedEdge.class ) );

	}

}
