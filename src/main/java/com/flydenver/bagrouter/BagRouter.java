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

package com.flydenver.bagrouter;

import com.flydenver.bagrouter.lexer.ParseException;
import com.flydenver.bagrouter.lexer.RoutingInput;
import com.flydenver.bagrouter.routing.RoutingException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;


/**
 * This is just a main runner class.
 */
public class BagRouter {

	public static void main( String [] args ) {
		String inputFile = ( args.length < 1 ) ? "routing-input.txt" : args[0];

		try {
			InputStream fis1, fis2;
			File file = new File( inputFile );
			fis1 = file.exists() ? new FileInputStream( file ) : BagRouter.class.getResourceAsStream( "/" + inputFile );
			fis2 = file.exists() ? new FileInputStream( file ) : BagRouter.class.getResourceAsStream( "/" + inputFile );

			if ( fis1 == null ) {
				throw new FileNotFoundException( inputFile );
			}

			System.out.println();
			System.out.println( "[*] Dumping input " + inputFile );
			System.out.println( "[*] ------------------" );
			new RoutingInput( fis1 ).forEachLine( ( type, line ) -> System.out.println( line ) );
			System.out.println( "[*] ------------------" );

			System.out.println();
			System.out.println();
			System.out.println( "[*] Routing Table" );
			System.out.println( "[*] ------------------" );
			RoutingInput input = new RoutingInput( fis2 );
			BagRouteOutput output = new BagRouteOutput( System.out );
			RoutingEngine engine = new RoutingEngine();
			engine.executeSearch( input, output );
			System.out.println( "[*] ------------------" );

			engine.cleanup();
		}
		catch ( RoutingException | ParseException e ) {
			System.err.println( "Error routing. " + e.getMessage() );
		}
		catch ( FileNotFoundException e ) {
			System.err.println( "Could not find file. " + e.getMessage() );
		}

	}

}
