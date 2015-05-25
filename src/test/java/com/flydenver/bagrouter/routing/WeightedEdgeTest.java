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

package com.flydenver.bagrouter.routing;

import com.flydenver.bagrouter.domain.TerminalGate;
import com.flydenver.bagrouter.lexer.ParseException;
import com.flydenver.bagrouter.lexer.RoutingEvaluator;
import com.flydenver.bagrouter.lexer.RoutingInput;
import com.flydenver.bagrouter.lexer.section.SectionParser;
import com.flydenver.bagrouter.lexer.section.SectionType;
import com.flydenver.bagrouter.lexer.section.conveyor.ConveyorRoute;

import com.flydenver.bagrouter.lexer.section.conveyor.ConveyorRowParser;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;


public class WeightedEdgeTest {

	private Node<TerminalGate> node1 = new Node<>( new TerminalGate( "A1" ) );
	private Node<TerminalGate> node2 = new Node<>( new TerminalGate( "A2" ) );

	@Test
	public void testCreateEdges() throws ParseException {
		SectionParser parser = RoutingEvaluator.multiSectionParser( new RoutingInput( "routing-input.txt" ) );
		parser.addSectionConsumer( SectionType.CONVEYOR_SYSTEM, new ConveyorRowParser(), entry -> {
			ConveyorRoute conveyor = (ConveyorRoute)entry;
			Node<TerminalGate> node1 = new Node<>( conveyor.getFirstTerminal() );
			Node<TerminalGate> node2 = new Node<>( conveyor.getSecondTerminal() );
			WeightedEdge<TerminalGate> gateLink = new WeightedEdge<>( node1, node2, conveyor.getTravelTime() );

			assertNotNull( gateLink.getFirstNode() );
			assertNotNull( gateLink.getSecondNode() );
			assertNotEquals( gateLink.getWeight(), 0 );

			assertEquals( gateLink.getFirstNode().getNodeItem(), conveyor.getFirstTerminal() );
			assertEquals( gateLink.getSecondNode().getNodeItem(), conveyor.getSecondTerminal() );
		});
	}

	@Test (expected = IllegalArgumentException.class)
	public void testNegativeWeight() {
		new WeightedEdge<>( node1, node2, -1 );
		fail();
	}

	@Test (expected = IllegalArgumentException.class)
	public void testNullNode1() {
		new WeightedEdge<>( null, node2, -1 );
	}

	@Test (expected = IllegalArgumentException.class)
	public void testNullNode2() {
		new WeightedEdge<>( node1, null, -1 );
	}

	@Test (expected = IllegalArgumentException.class)
	public void testEqualNode() {
		Node<String> node1 = new Node<>( "A1" );
		Node<String> node2 = new Node<>( "A1" );
		new WeightedEdge<>( node1, node2, 1 );
	}

	@Test
	public void equalsCheck() {
		Node<String> node1 = new Node<>( "A1" );
		Node<String> node2 = new Node<>( "A2" );
		WeightedEdge<String> link1 = new WeightedEdge<>( node1, node2, 2 );
		WeightedEdge<String> link2 = new WeightedEdge<>( node2, node1, 2 );
		WeightedEdge<String> link3 = new WeightedEdge<>( node1, node2, 2 );

		assertEquals( link1, link3 );
		assertEquals( link1, link2 );
		assertEquals( link2, link3 );

		assertEquals( link1.compareTo( link2 ), 0 );
		assertEquals( link1.compareTo( link3 ), 0 );
		assertEquals( link1.compareTo( new WeightedEdge<>( node1, node2, 3 ) ), -1 );
	}

	@Test
	public void compareCheck() {
		Node<String> node1 = new Node<>( "A1" );
		Node<String> node2 = new Node<>( "A2" );
		WeightedEdge<String> link1 = new WeightedEdge<>( node1, node2, 2 );
		WeightedEdge<String> link2 = new WeightedEdge<>( node2, node1, 2 );
		WeightedEdge<String> link3 = new WeightedEdge<>( node1, node2, 2 );
		assertEquals( link1.compareTo( link2 ), 0 );
		assertEquals( link1.compareTo( link3 ), 0 );
		assertEquals( link2.compareTo( link3 ), 0 );
		assertEquals( link1.compareTo( new WeightedEdge<>( node1, node2, 4 ) ), -1 );
	}

	@Test
	public void otherNodeCheck() {
		Node<String> node1 = new Node<>( "A1" );
		Node<String> node2 = new Node<>( "A2" );
		WeightedEdge<String> link1 = new WeightedEdge<>( node1, node2, 2 );
		WeightedEdge<String> link2 = new WeightedEdge<>( node2, node1, 2 );
		assertEquals( link1.getOtherNode( node2 ), node1 );
		assertEquals( link2.getOtherNode( node1 ), node2 );
	}

}
