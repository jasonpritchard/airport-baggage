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

package com.flydenver.bagrouter.lexer.section;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.flydenver.bagrouter.lexer.section.SectionHeaderTokenizer.SectionHeaderToken;

/**
 * Tests for section header tokens.
 */
public class SectionHeaderTokenizerTest {
	private final static String sectionStart = "#";
	private final static String sectionHeader = "Section";
	private final static String sectionSplit = ":";

	private final static String conveyorSystem = "# Section: Conveyor System";
	private final static String departures = "# Section: Departures";
	private final static String bags = "# Section: Bags";

	//=[ good tests ]==========================================================

	@Test
	public void testGoodSectionTokens() {
		assertEquals( SectionHeaderToken.fromIdentifier( sectionStart ), SectionHeaderToken.SECTION_START );
		assertEquals( SectionHeaderToken.fromIdentifier( sectionHeader ), SectionHeaderToken.SECTION_HEADER );
		assertEquals( SectionHeaderToken.fromIdentifier( sectionSplit ), SectionHeaderToken.SECTION_SPLIT );
	}

	@Test
	public void testGoodSectionHeader() {
		SectionHeaderTokenizer tokenizer = SectionHeaderTokenizer.checkLineForSectionHeader( conveyorSystem );
		assertNotNull( tokenizer );
		assertEquals( tokenizer.getSectionHeader(), "Conveyor System" );

		tokenizer = SectionHeaderTokenizer.checkLineForSectionHeader( departures );
		assertNotNull( tokenizer );
		assertEquals( tokenizer.getSectionHeader(), "Departures" );

		tokenizer = SectionHeaderTokenizer.checkLineForSectionHeader( bags );
		assertNotNull( tokenizer );
		assertEquals( tokenizer.getSectionHeader(), "Bags" );

		tokenizer = SectionHeaderTokenizer.checkLineForSectionHeader( "# Section  : Another Longer Section Header With  More   Spaces    " );
		assertNotNull( tokenizer );
		assertEquals( tokenizer.getSectionHeader(), "Another Longer Section Header With  More   Spaces" );
	}

	@Test
	public void testLeadingAndTrailingSpace() {
		assertEquals( SectionHeaderTokenizer.checkLineForSectionHeader( " " + conveyorSystem + " " ).getSectionHeader(), "Conveyor System" );
		assertEquals( SectionHeaderTokenizer.checkLineForSectionHeader( " " + departures + " " ).getSectionHeader(), "Departures" );
		assertEquals( SectionHeaderTokenizer.checkLineForSectionHeader( " " + bags + " " ).getSectionHeader(), "Bags" );
	}

	//=[ bad arguments tests ]==========================================================
	
	@Test
	public void testBadHeaders() {
		assertNull( SectionHeaderTokenizer.checkLineForSectionHeader( null ) );
		assertNull( SectionHeaderTokenizer.checkLineForSectionHeader( "" ) );
		assertNull( SectionHeaderTokenizer.checkLineForSectionHeader( " " ) );
		assertNull( SectionHeaderTokenizer.checkLineForSectionHeader( "A random string" ) );
		assertNull( SectionHeaderTokenizer.checkLineForSectionHeader( "Section: Conveyor System" ) );
		assertNull( SectionHeaderTokenizer.checkLineForSectionHeader( "Section:" ) );
	}
	
	@Test
	public void testTypeNull() {
		assertEquals( SectionHeaderToken.fromIdentifier( null ), SectionHeaderToken.UNKNOWN );
		assertEquals( SectionHeaderToken.fromIdentifier( "" ), SectionHeaderToken.UNKNOWN );
	}

	//=[ id tests ]==========================================================

	@Test
	public void testBadTokens() {
		assertEquals( SectionHeaderToken.fromIdentifier( "&" ), SectionHeaderToken.UNKNOWN );
		assertEquals( SectionHeaderToken.fromIdentifier( "*" ), SectionHeaderToken.UNKNOWN );
		assertEquals( SectionHeaderToken.fromIdentifier( "%" ), SectionHeaderToken.UNKNOWN );
	}

}
