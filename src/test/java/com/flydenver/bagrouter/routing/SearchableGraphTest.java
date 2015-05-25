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
import com.flydenver.bagrouter.routing.search.Searchable;
import com.flydenver.bagrouter.routing.search.SearchableGraph;
import com.flydenver.bagrouter.routing.search.dijkstra.DijkstraSearchStrategy;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;


public class SearchableGraphTest {
	private WeightedGraph<TerminalGate> wg = new WeightedGraph<>();
	private DijkstraSearchStrategy<TerminalGate> strategy;
	private Node<TerminalGate> ts  = new Node<>(new TerminalGate( "Concourse_A_Ticketing" ));
	private Node<TerminalGate> a5  = new Node<>(new TerminalGate("A5"));
	private Node<TerminalGate> bc  = new Node<>(new TerminalGate("BaggageClaim"));
	private Node<TerminalGate> a10 = new Node<>(new TerminalGate("A10"));
	private Node<TerminalGate> a1  = new Node<>(new TerminalGate("A1"));
	private Node<TerminalGate> a2  = new Node<>(new TerminalGate("A2"));
	private Node<TerminalGate> a3  = new Node<>(new TerminalGate("A3"));
	private Node<TerminalGate> a4  = new Node<>(new TerminalGate("A4"));
	private Node<TerminalGate> a9  = new Node<>(new TerminalGate("A9"));
	private Node<TerminalGate> a8  = new Node<>(new TerminalGate("A8"));
	private Node<TerminalGate> a7  = new Node<>(new TerminalGate("A7"));
	private Node<TerminalGate> a6  = new Node<>(new TerminalGate("A6"));


	@Before
	public void setup() {
		strategy = new DijkstraSearchStrategy<>();
		wg.addEdge(new WeightedEdge<>(ts,  a5,  5));
		wg.addEdge(new WeightedEdge<>(a5,  bc,  5));
		wg.addEdge(new WeightedEdge<>(a5,  a10, 4));
		wg.addEdge(new WeightedEdge<>(a5,  a1,  6));
		wg.addEdge(new WeightedEdge<>(a1,  a2,  1));
		wg.addEdge(new WeightedEdge<>(a2,  a3,  1));
		wg.addEdge(new WeightedEdge<>(a3,  a4,  1));
		wg.addEdge(new WeightedEdge<>(a10, a9,  1));
		wg.addEdge(new WeightedEdge<>(a9,  a8,  1));
		wg.addEdge(new WeightedEdge<>(a8,  a7,  1));
		wg.addEdge( new WeightedEdge<>( a7, a6, 1 ) );
	}

	@Test
	@SuppressWarnings( "unchecked" )
	public void testEdges() {
		SearchableGraph<TerminalGate> searchable = new SearchableGraph<>( wg );
		assertArrayEquals( searchable.edges().toArray(), wg.edges().toArray() );

		AtomicInteger integer = new AtomicInteger(  );
		Node<TerminalGate> [] nodes = new Node[wg.nodes().size()];
		Node<TerminalGate> [] gates =  wg.nodes().values().toArray( nodes );
		searchable.forEachNode( edge -> assertEquals( edge, gates[integer.getAndIncrement()] ) );
	}

	@Test
	public void testGetNode() {
		SearchableGraph<TerminalGate> searchable = new SearchableGraph<>( wg );
		searchable.setSearchDelegate( strategy );
		assertEquals( a5, searchable.getNode( a5.getNodeId() ) );
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullSearch() {
		Searchable<TerminalGate> search = new SearchableGraph<>( wg );
		search.findOptimalPath( null, new Node<>( new TerminalGate( "BaggageClaim" ) ) );
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullSearch1() {
		Searchable<TerminalGate> search = new SearchableGraph<>( wg );
		search.findOptimalPath( new Node<>(new TerminalGate("BaggageClaim")), null );
	}

	@Test (expected = IllegalArgumentException.class)
	public void testNullCreate() {
		new SearchableGraph<>( null );
	}

	@Test
	public void testSearchGood1() {
		SearchableGraph<TerminalGate> search = new SearchableGraph<>( wg );
		search.setSearchDelegate( strategy );

		assertArrayEquals( search.findOptimalPath( new Node<>( new TerminalGate( "A1" ) ), new Node<>( new TerminalGate( "BaggageClaim" ) ) ).nodes().toArray(), new Node[]{
				new Node<>( new TerminalGate( "A1" ) ),
				new Node<>( new TerminalGate( "A5" ) ),
				new Node<>( new TerminalGate( "BaggageClaim" ) )
		});

		assertArrayEquals( search.findOptimalPath( new Node<>( new TerminalGate( "A5" ) ), new Node<>( new TerminalGate( "A4" ) ) ).nodes().toArray(), new Node[]{
				new Node<>(new TerminalGate("A5")),
				new Node<>(new TerminalGate("A1")),
				new Node<>(new TerminalGate("A2")),
				new Node<>(new TerminalGate("A3")),
				new Node<>(new TerminalGate("A4"))
		});

		assertArrayEquals( search.findOptimalPath( new Node<>(new TerminalGate("A5")), new Node<>(new TerminalGate("A7")) ).nodes().toArray(), new Node[]{
				new Node<>(new TerminalGate("A5")),
				new Node<>(new TerminalGate("A10")),
				new Node<>(new TerminalGate("A9")),
				new Node<>(new TerminalGate("A8")),
				new Node<>(new TerminalGate("A7"))
		});

		assertArrayEquals( search.findOptimalPath( new Node<>(new TerminalGate("A5")), new Node<>(new TerminalGate("BaggageClaim")) ).nodes().toArray(), new Node[]{
				new Node<>(new TerminalGate("A5")),
				new Node<>(new TerminalGate("BaggageClaim"))
		});

		assertArrayEquals( search.findOptimalPath( new Node<>( new TerminalGate( "BaggageClaim" ) ), new Node<>( new TerminalGate( "A4")) ).nodes().toArray(), new Node[]{
				new Node<>(new TerminalGate("BaggageClaim")),
				new Node<>(new TerminalGate("A5")),
				new Node<>(new TerminalGate("A1")),
				new Node<>(new TerminalGate("A2")),
				new Node<>(new TerminalGate("A3")),
				new Node<>(new TerminalGate("A4"))
		});

	}

	@Test
	public void testSearchGood2() {
		WeightedGraph<TerminalGate> wg2 = new WeightedGraph<>();
		SearchableGraph<TerminalGate> search = new SearchableGraph<>( wg );
		search.setSearchDelegate( new DijkstraSearchStrategy<>() );

		wg2.addEdge( new Node<>( new TerminalGate( "Concourse_A_Ticketing" ) ), new Node<>( new TerminalGate( "A5" ) ), 5 );
		wg2.addEdge(new Node<>(new TerminalGate("A5")),  new Node<>(new TerminalGate("BaggageClaim")),  5);
		wg2.addEdge(new Node<>(new TerminalGate("A5")),  new Node<>(new TerminalGate("A10")), 4);
		wg2.addEdge(new Node<>(new TerminalGate("A5")),  new Node<>(new TerminalGate("A1")),  6);
		wg2.addEdge(new Node<>(new TerminalGate("A1")),  new Node<>(new TerminalGate("A2")),  1);
		wg2.addEdge(new Node<>(new TerminalGate("A2")),  new Node<>(new TerminalGate("A3")),  1);
		wg2.addEdge(new Node<>(new TerminalGate("A3")),  new Node<>(new TerminalGate("A4")),  1);
		wg2.addEdge(new Node<>(new TerminalGate("A10")), new Node<>(new TerminalGate("A9")),  1);
		wg2.addEdge(new Node<>(new TerminalGate("A9")),  new Node<>(new TerminalGate("A8")),  1);
		wg2.addEdge(new Node<>(new TerminalGate("A8")),  new Node<>(new TerminalGate("A7")),  1);
		wg2.addEdge( new Node<>( new TerminalGate( "A7" ) ), new Node<>( new TerminalGate( "A6" ) ), 1 );

		assertArrayEquals( search.findOptimalPath( new Node<>( new TerminalGate( "A1" ) ), new Node<>( new TerminalGate( "BaggageClaim" ) ) ).nodes().toArray(), new Node[]{
				new Node<>( new TerminalGate( "A1" ) ),
				new Node<>( new TerminalGate( "A5" ) ),
				new Node<>( new TerminalGate( "BaggageClaim" ) )
		} );

		assertArrayEquals( search.findOptimalPath( new Node<>( new TerminalGate( "A5" ) ), new Node<>( new TerminalGate( "A7")) ).nodes().toArray(), new Node[]{
				new Node<>(new TerminalGate("A5")),
				new Node<>(new TerminalGate("A10")),
				new Node<>(new TerminalGate("A9")),
				new Node<>(new TerminalGate("A8")),
				new Node<>(new TerminalGate("A7"))
		});

		assertArrayEquals( search.findOptimalPath( new Node<>( new TerminalGate( "A5" ) ), new Node<>( new TerminalGate( "BaggageClaim")) ).nodes().toArray(), new Node[]{
				new Node<>(new TerminalGate("A5")),
				new Node<>(new TerminalGate("BaggageClaim"))
		});

		assertArrayEquals( search.findOptimalPath( new Node<>( new TerminalGate( "BaggageClaim" ) ), new Node<>( new TerminalGate( "A4")) ).nodes().toArray(), new Node[]{
				new Node<>(new TerminalGate("BaggageClaim")),
				new Node<>(new TerminalGate("A5")),
				new Node<>(new TerminalGate("A1")),
				new Node<>(new TerminalGate("A2")),
				new Node<>(new TerminalGate("A3")),
				new Node<>(new TerminalGate("A4"))
		});
	}

}
