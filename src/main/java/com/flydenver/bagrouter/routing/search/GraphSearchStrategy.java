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

import com.flydenver.bagrouter.routing.Edge;
import com.flydenver.bagrouter.routing.Graph;
import com.flydenver.bagrouter.routing.Node;


/**
 * Describes a strategy for searching a graph.
 */
public interface GraphSearchStrategy<T, G extends Graph<T, ? extends Edge<T>>> {

	/**
	 * Search the given {@link Graph} for the "optimal" path from the first
	 * {@link Node} to the second {@link Node}. It is up to the implantation of
	 * the strategy to determine what is meant by "optimal"
	 *
	 * @param graph     {@link Graph} to search
	 * @param startNode starting {@link Node}
	 * @param endNode   {@link Node} to search for
	 * @return the node path starting with startNode, and ending with endNode.
	 */
	NodePath<T> findPath( G graph, Node<T> startNode, Node<T> endNode );

}
