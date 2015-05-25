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
import com.flydenver.bagrouter.lexer.section.conveyor.ConveyorRoute;
import com.flydenver.bagrouter.routing.search.SearchRouteException;
import com.flydenver.bagrouter.routing.search.SearchableGraph;
import com.flydenver.bagrouter.routing.search.dijkstra.DijkstraSearchStrategy;

import org.junit.Test;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;

import static org.junit.Assert.*;


public class GraphSearchingIntegration {

	@Test
	public void testGraphAdd() throws ParseException {
		WeightedGraph<TerminalGate> gateGraph = new WeightedGraph<>();
		SearchableGraph<TerminalGate> searchableGraph = new SearchableGraph<>( gateGraph );
		searchableGraph.setSearchDelegate( new DijkstraSearchStrategy<>() );
		assertArrayEquals( fillGraph( gateGraph ).toArray(), gateGraph.edges().toArray() );
	}


	@Test
	public void testSearchGraphAdd() throws ParseException {
		WeightedGraph<TerminalGate> gateGraph = new WeightedGraph<>();
		SearchableGraph<TerminalGate> searchableGraph = new SearchableGraph<>( gateGraph );
		searchableGraph.setSearchDelegate( new DijkstraSearchStrategy<>() );
		assertArrayEquals( fillGraph( searchableGraph ).toArray(), searchableGraph.edges().toArray() );
	}


	@Test
	public void testGraphSearching() throws ParseException {
		WeightedGraph<TerminalGate> gateGraph = new WeightedGraph<>();
		SearchableGraph<TerminalGate> searchableGraph = new SearchableGraph<>( gateGraph );
		searchableGraph.setSearchDelegate( new DijkstraSearchStrategy<>() );
		fillGraph( gateGraph );

		assertArrayEquals( searchableGraph.findOptimalPath( new Node<>( new TerminalGate( "A1" ) ), new Node<>( new TerminalGate( "A2" ) ) ).nodes().toArray(), new Node[]{
				new Node<>( new TerminalGate( "A1" ) ),
				new Node<>( new TerminalGate( "A2" ) )
		});
	}


	@Test (expected = SearchRouteException.class)
	public void testRoutingException() throws SearchRouteException {
		IOException io = new IOException( "IOE" );
		SearchRouteException sre = new SearchRouteException( "SRE", io );
		SearchRouteException sre2 = new SearchRouteException( "SRE" );
		assertEquals( sre.getCause(), io );
		assertEquals( sre2.getMessage(), "SRE" );
		throw sre;
	}


	private Collection<Edge<TerminalGate>> fillGraph( WeightedGraph<TerminalGate> gateGraph ) throws ParseException {
//		java.util.PriorityQueue<Edge<TerminalGate>> addedNodes = new java.util.PriorityQueue<>( );
//		Set<Edge<TerminalGate>> addedNodes = new java.util.TreeSet<>( );
		Set<Edge<TerminalGate>> addedNodes = new java.util.LinkedHashSet<>( );
		RoutingEvaluator.parseRouting( "routing-input.txt", ConveyorRoute.class, ( conveyor ) -> {
			Node<TerminalGate> node1 = new Node<>( conveyor.getFirstTerminal() );
			Node<TerminalGate> node2 = new Node<>( conveyor.getSecondTerminal() );
			WeightedEdge<TerminalGate> gateLink = new WeightedEdge<>( node1, node2, conveyor.getTravelTime() );
			gateGraph.addEdge( gateLink );
//			gateGraph.addEdge( node1, node2, conveyor.getTravelTime() );
			addedNodes.add( gateLink );
		});
		return addedNodes;
	}

}
