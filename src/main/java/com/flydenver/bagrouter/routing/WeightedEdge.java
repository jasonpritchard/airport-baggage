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
 * {@link Edge} implementation for {@link Edge}s with weights.
 * @param <T> Type of {@link Node} data that the {@link Edge} contains.
 */
public class WeightedEdge<T> implements Edge<T>, Comparable<WeightedEdge<T>> {
	private final Node<T> firstNode;
	private final Node<T> secondNode;
	private final int weight;


	/**
	 * Construct an edge as a line between two nodes. This will automatically
	 * add this edge to the nodes so the nodes are aware of the edge that
	 * connects them.
	 */
	public WeightedEdge( Node<T> firstNode, Node<T> secondNode, int weight ) {
		if ( weight < 0 ) { throw new IllegalArgumentException( "Weight must not be negative" ); }
		if ( firstNode == null || secondNode == null || firstNode == secondNode || firstNode.equals( secondNode ) ) {
			throw new IllegalArgumentException( "Nodes must not be null or equal" );
		}

		this.weight = weight;
		
		this.firstNode = firstNode;
		this.firstNode.addEdge( this );

		this.secondNode = secondNode;
		this.secondNode.addEdge( this );
	}


	/** Edge's weight */
	public int getWeight() {
		return weight;
	}


	@Override
	public Node<T> getFirstNode() {
		return firstNode;
	}


	@Override
	public Node<T> getSecondNode() {
		return secondNode;
	}


	@Override
	public Node<T> getOtherNode( Node<T> thisNode ) {
		return getFirstNode().equals( thisNode ) ? getSecondNode() : getFirstNode();
	}


	@Override
	public String toString() {
		return "[" + getFirstNode() + "-" + getSecondNode() + " {" + getWeight() + "}]";
	}


	@Override
	@SuppressWarnings( "unchecked" )
	public boolean equals( Object obj ) {
		return obj != null &&
				obj instanceof Edge &&
				getFirstNode () != null &&
				getSecondNode() != null &&
				((getFirstNode().equals( ((Edge<T>) obj).getFirstNode() ) &&
				getSecondNode().equals( ((Edge<T>) obj).getSecondNode())) ||
				(getFirstNode().equals( ((Edge<T>) obj).getSecondNode() ) &&
				getSecondNode().equals( ((Edge<T>) obj).getFirstNode()) ));
	}


	@Override
	public int compareTo( WeightedEdge<T> o ) {
		return Integer.compare( getWeight(), o.getWeight() );
	}

}
