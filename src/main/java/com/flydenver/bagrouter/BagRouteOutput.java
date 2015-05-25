package com.flydenver.bagrouter;/*
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

import com.flydenver.bagrouter.routing.search.SearchRouteException;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;


/**
 * Class for writing out the bag path.
 */
public class BagRouteOutput {

	private final static String NEWLINE = System.getProperty( "line.separator" );
	private final Writer writer;


	/** Construct an output using the given writer. */
	public BagRouteOutput( Writer output ) {
		if ( output == null ) {
			throw new IllegalArgumentException( "Null writer" );
		}
		this.writer = output;
	}


	/** Construct an output using the given stream. */
	public BagRouteOutput( OutputStream output ) {
		if ( output == null ) {
			throw new IllegalArgumentException( "Null writer" );
		}
		this.writer = new OutputStreamWriter( output );
	}


	/**
	 * Close the route output.
	 */
	public void close() throws IOException {
		writer.close();
	}


	/**
	 * Close the route output ignoring any errors
	 * @return true if the output succeeds, false otherwise
	 */
	public boolean closeQuietly() {
		boolean ret = false;
		try {
			close();
			ret = true;
		}
		catch ( IOException e ) { /* ignore failure */ }
		return ret;
	}


	/**
	 * Write a single {@link BagRoute} to the output.
	 * @param route single {@link BagRoute} entry
	 */
	public void write( BagRoute route ) throws IOException {
		if ( route == null || route.getBag() == null || route.getBagPath() == null ) {
			throw new IllegalArgumentException( "Null route data." );
		}

		final Writer writer = getWriter();
		try {
			writer.write( route.getBag().getBagNumber() );
			writer.write( " " );

			route.getBagPath().forEachNode( gate -> {
				writer.write( gate.getGateNumber() );
				writer.write( " " );
			});

			writer.write( ": " );
			writer.write( String.valueOf( route.getBagPath().getTotalDistance() ) );

		}
		catch ( SearchRouteException | RuntimeException e ) {
			throw new IOException( "Error writing bag route.", e );
		}
	}


	/**
	 * Same as {@link #write(BagRoute)}, but a line separator is added.
	 */
	public void writeln( BagRoute route ) throws IOException {
		write( route );
		getWriter().write( NEWLINE );
	}


	/**
	 * Flush the output.
	 */
	public void flush() throws IOException {
		getWriter().flush();
	}


	protected Writer getWriter() {
		return writer;
	}

}
