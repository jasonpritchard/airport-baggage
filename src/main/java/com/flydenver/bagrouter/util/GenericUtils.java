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

package com.flydenver.bagrouter.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


/**
 * Some utility methods for doing some work with Generics.
 */
public class GenericUtils {

	/**
	 * Check an object's type parameters to see if a class name is present. Note that this
	 * only checks that the base types match. If the type is parameterized, this will only
	 * check against the base type name.
	 * 
	 * @param o object to check
	 * @param type type name to search for in the type list
	 * @return true if type is found, false otherwise
	 */
	public static boolean checkType( Object o, Class<?> type ) {
		Type genericType = o.getClass().getGenericSuperclass();
		Type [] types = ( genericType instanceof ParameterizedType ) ?
						((ParameterizedType)genericType).getActualTypeArguments() :
						o.getClass().getGenericInterfaces();

		return checkTypeList( types, type.getTypeName() );
	}


	//	search for a type name in types
	private static boolean checkTypeList( Type [] types, String typeName ) {
		boolean found = false;
	    for ( Type t : types ) {
    		if ( t instanceof ParameterizedType ) {
	    		found = checkTypeList( ((ParameterizedType) t).getActualTypeArguments(), typeName );
	    		if ( found ) { break; }
	    	}

			//System.out.println("\t- " + t.getTypeName());
	    	if ( t.getTypeName().replaceAll( "<(.*)>", "" ).equals( typeName ) ) {
	    		found = true;
	    		break;
	    	}
	    }
	    
	    return found;
	}

}
