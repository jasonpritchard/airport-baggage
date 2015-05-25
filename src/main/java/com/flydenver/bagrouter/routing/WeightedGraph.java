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


/**
 * Implementation of {@link Graph} that uses weighted {@link Edge}s.
 */
public class WeightedGraph<T> extends Graph<T, WeightedEdge<T>> {

	/**
	 * Add an edge to the graph derived from the two nodes, and the assigned weight.
	 */
	public void addEdge( Node<T> firstNode, Node<T> secondNode, int weight ) {
		if ( firstNode == null || secondNode == null ) {
			throw new IllegalArgumentException( "Null nodes." );
		}

		if ( ! nodes().containsKey( firstNode.getNodeId() ) ) {
			nodes().put( firstNode.getNodeId(), firstNode );
		}

		if ( ! nodes().containsKey( secondNode.getNodeId() ) ) {
			nodes().put( secondNode.getNodeId(), secondNode );
		}

		Node<T> first  = nodes().get( firstNode.getNodeId() );
		Node<T> second = nodes().get( secondNode.getNodeId() );
		WeightedEdge<T> edge = new WeightedEdge<>( first, second, weight );

		first.addEdge( edge );
		second.addEdge( edge );

		if ( ! edges().contains( edge ) ) {
			edges().add( edge );
		}

		addNodesFromEdge( edge );

	}

}
