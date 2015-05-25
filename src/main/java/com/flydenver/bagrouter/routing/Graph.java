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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;


/**
 * Graph of {@link Node}s connected by {@link Edge}s.
 */
public abstract class Graph<T, E extends Edge<T>>  {

	private int defaultCollectionSize = 100;
	private final Set<E> graphEdges = new java.util.LinkedHashSet<>( defaultCollectionSize );
	private final Map<Object, Node<T>> graphNodes = new HashMap<>( defaultCollectionSize );


	/**
	 * Add an edge to the graph. Nodes are derived from the edges. The nodes of 
	 * the edge are required to be set.
	 * @param edge edge to add.
	 */
	public void addEdge( E edge ) {
		if ( edge.getFirstNode() == null || edge.getSecondNode() == null ) {
			throw new IllegalArgumentException( "Null nodes." );
		}

		if ( ! graphEdges.contains( edge ) ) {
			graphEdges.add( edge );
		}

		addNodesFromEdge( edge );
	}

	/**
	 * Add an edge to the graph derived from the two nodes, and the assigned weight.
	 */
	abstract public void addEdge( Node<T> firstNode, Node<T> secondNode, int weight );


	//	Extract the nodes from the edge and add them
	//	It's not really necessary to keep the nodes since they can
	//	be derived from the edges, but this make them easier to get
	protected void addNodesFromEdge( E edge ) {
		if ( ! graphNodes.containsKey( edge.getFirstNode().getNodeId() ) ) {
			graphNodes.put( edge.getFirstNode().getNodeId(), edge.getFirstNode() );
		}

		if ( ! graphNodes.containsKey( edge.getSecondNode().getNodeId() ) ) {
			graphNodes.put( edge.getSecondNode().getNodeId(), edge.getSecondNode() );
		}

		graphNodes.get( edge.getFirstNode().getNodeId() ).addEdge( edge );
		graphNodes.get( edge.getSecondNode().getNodeId() ).addEdge( edge );
	}


	/**
	 * Get the list of {@link Graph} {@link Edge}s.
	 */
	public Collection<E> edges() {
		return graphEdges;
	}


	/**
	 * Get the list of {@link Graph} {@link Node}s.
	 */
	public void forEachNode( Consumer<Node<T>> consumer ) {
		nodes().forEach( ( nodeId, node ) -> consumer.accept( node ) );
	}


	/**
	 * Find a node in the graph by the node id.
	 */
	public Node<T> getNode( Object nodeId ) {
		return nodes().get( nodeId );
	}


	/**
	 * Get the collection of nodes.
	 */
	public Map<Object, Node<T>> nodes() {
		return graphNodes;
	}


	/**
	 * Cleanup after use.
	 */
	public void cleanup() {
		edges().clear();
		nodes().clear();
	}

}
