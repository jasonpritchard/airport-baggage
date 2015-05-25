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

package com.flydenver.bagrouter.routing.search;

import com.flydenver.bagrouter.routing.Node;
import com.flydenver.bagrouter.routing.WeightedEdge;
import com.flydenver.bagrouter.routing.WeightedGraph;

import java.util.Collection;
import java.util.Map;
import java.util.function.Consumer;


/**
 * Weighted graph decorator to add searching.
 */
public class SearchableGraph<T> extends WeightedGraph<T> implements Searchable<T> {

	private final WeightedGraph<T> graph;
	private GraphSearchStrategy<T, WeightedGraph<T>> searchDelegate;


	/**
	 * Wrap the given {@link WeightedGraph} with {@link Searchable} features.
	 * {@code graph} must not be null
	 * @param graph graph to decorate
	 */
	public SearchableGraph( WeightedGraph<T> graph ) {
		if ( graph == null ) {
			throw new IllegalArgumentException( "Null graph" );
		}
		this.graph = graph;
	}


	/**
	 * Set the search strategy to be used.
	 */
	public void setSearchStrategy( GraphSearchStrategy<T, WeightedGraph<T>> searchStrategy ) {
		this.searchDelegate = searchStrategy;
	}


	/**
	 * Getter for search strategy.
	 */
	public GraphSearchStrategy<T, WeightedGraph<T>> getSearchDelegate() {
		return searchDelegate;
	}


	@Override
	public NodePath<T> findOptimalPath( Node<T> startNode, Node<T> endNode ) {
		if ( getSearchDelegate() == null ) {
			throw new IllegalArgumentException( "Search strategy not set" );
		}

		if ( startNode == null || endNode == null ) {
			throw new IllegalArgumentException( "Cannot search null nodes." );
		}

		//	now delegate the searching
		return getSearchDelegate().findPath( graph, startNode, endNode );
	}


	@Override
	public void addEdge( WeightedEdge<T> edge ) {
		graph.addEdge( edge );
	}

	@Override
	public void addEdge( Node<T> first, Node<T> second, int weight ) {
		graph.addEdge( first, second, weight );
	}

	@Override
	public Collection<WeightedEdge<T>> edges() {
		return graph.edges();
	}

	@Override
	public void forEachNode( Consumer<Node<T>> consumer ) {
		graph.forEachNode( consumer );
	}

	@Override
	public Node<T> getNode( Object nodeId ) {
		return graph.getNode( nodeId );
	}

	@Override
	public Map<Object, Node<T>> nodes() {
		return graph.nodes();
	}

	@Override
	public void cleanup() {
		graph.cleanup();
	}

}
