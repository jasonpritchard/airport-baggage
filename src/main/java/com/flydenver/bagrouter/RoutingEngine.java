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


import com.flydenver.bagrouter.domain.PassengerBag;
import com.flydenver.bagrouter.domain.TerminalGate;
import com.flydenver.bagrouter.lexer.ParseException;
import com.flydenver.bagrouter.lexer.RoutingEvaluator;
import com.flydenver.bagrouter.lexer.RoutingInput;
import com.flydenver.bagrouter.lexer.section.MultiSectionParser;
import com.flydenver.bagrouter.lexer.section.SectionType;
import com.flydenver.bagrouter.lexer.section.bag.BagEntry;
import com.flydenver.bagrouter.lexer.section.bag.BagRowParser;
import com.flydenver.bagrouter.lexer.section.conveyor.ConveyorRoute;
import com.flydenver.bagrouter.lexer.section.conveyor.ConveyorRowParser;
import com.flydenver.bagrouter.lexer.section.departure.Departure;
import com.flydenver.bagrouter.lexer.section.departure.DepartureRowParser;
import com.flydenver.bagrouter.routing.Node;
import com.flydenver.bagrouter.routing.RoutingException;
import com.flydenver.bagrouter.routing.WeightedGraph;
import com.flydenver.bagrouter.routing.search.GraphSearchStrategy;
import com.flydenver.bagrouter.routing.search.NodePath;
import com.flydenver.bagrouter.routing.search.SearchableGraph;
import com.flydenver.bagrouter.routing.search.dijkstra.DijkstraSearchStrategy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * Routing engine.
 */
public class RoutingEngine {

	public final static String searchStrategyClass = System.getProperty( "router.search.strategy", DijkstraSearchStrategy.class.getName() );

	private final int defaultCollectionSize = 100;
	private String baggageClaimId = "BaggageClaim";
	private Router router = new Router();



	/**
	 * Baggage claim ID
	 */
	public void setBaggageClaimId( String baggageClaimId ) {
		this.baggageClaimId = baggageClaimId;
	}


	/**
	 * Run the {@code RoutingEngine}. This expects that the input and output have both
	 * been set with the output. Otherwise an exception is thrown.
	 */
	public void executeSearch( RoutingInput input, BagRouteOutput output ) throws RoutingException {
		router.setRoutingOutput( output );
		router.setRoutingInput( input );
		router.execute();
	}


	/**
	 * Cleanup any resources used while parsing.
	 */
	public void cleanup() {
		router.cleanup();
		router.closeIO();
	}


	//	Inner class to help force re-creating IO. 
	protected final class Router {

		private RoutingInput routingInput;
		private BagRouteOutput routingOutput;
		private Node<TerminalGate> baggageClaim;
		private Map<String, Departure> departures = new HashMap<>( defaultCollectionSize );
		private Map<String, BagEntry> passengerBags = new LinkedHashMap<>( defaultCollectionSize );
		//private Map<String, BagEntry> passengerBags = new TreeMap<>( ( o1, o2 ) -> o2.toLowerCase().compareTo( o1.toLowerCase() ) );
		private WeightedGraph<TerminalGate> conveyorRoutes = new WeightedGraph<>();
		private GraphSearchStrategy<TerminalGate, WeightedGraph<TerminalGate>> searchStrategy;


		/**
		 * Run the {@code RoutingEngine}. This expects that the input and output have both
		 * been set with the output. Otherwise an exception is thrown.
		 */
		protected void execute() throws RoutingException {
			if ( routingInput == null ) {
				throw new RoutingException( "Routing input not set." );
			}
			if ( routingOutput == null ) {
				throw new RoutingException( "Routing output not set." );
			}

			try {
				cleanup();
				parseInput();
				writeRoutes( performSearch() );
			}
			catch ( IOException | ParseException e ) {
				throw new RoutingException( e.getMessage(), e );
			}
		}


		/**
		 * Write a list of passenger bag routes
		 */
		protected void writeRoutes( List<BagRoute> routes ) throws IOException {
			for ( BagRoute route : routes ) {
				routingOutput.writeln( route );
			}
			routingOutput.flush();
		}


		/**
		 * Search for the list of routes for passenger bags
		 */
		protected List<BagRoute> performSearch() {
			SearchableGraph<TerminalGate> searchableGraph = new SearchableGraph<>( conveyorRoutes );
			searchableGraph.setSearchStrategy( getSearchStrategy() );

			List<BagRoute> bagRoutes = new ArrayList<>( passengerBags.size() );
			passengerBags.forEach( ( bagid, entry ) -> {
				final PassengerBag bag = entry.getBag();
				final boolean isFinal = bag.getBagState().equals( PassengerBag.BagState.ARRIVAL );

				Node<TerminalGate> endNode;
				if ( ! isFinal ) {
					Departure departure = departures.get( entry.getFlight().getFlightId().getId() );
					endNode = new Node<>( departure.getFlightGate() );
				}
				else {
					endNode = baggageClaim;
				}
				Node<TerminalGate> startNode = new Node<>( entry.getEntryPoint() );
				NodePath<TerminalGate> path = searchableGraph.findOptimalPath( startNode, endNode );
				bagRoutes.add( new BagRoute( bag, path ) );
			});

			return bagRoutes;
		}


		/**
		 * {@link BagRouteOutput} setter.
		 */
		protected void setRoutingOutput( BagRouteOutput routingOutput ) {
			this.routingOutput = routingOutput;
		}

		/**
		 * {@link RoutingInput} setter.
		 */
		protected void setRoutingInput( RoutingInput input ) {
			this.routingInput = input;
		}

		/**
		 * Cleanup any resources
		 */
		protected void cleanup() {
			baggageClaim = null;
			departures.clear();
			passengerBags.clear();
			conveyorRoutes.cleanup();
		}

		/**
		 * Close IO resources
		 */
		protected void closeIO() {
			routingInput.closeQuietly();
			routingOutput.closeQuietly();
		}

		/**
		 * Do the route parsing.
		 */
		protected void parseInput() throws ParseException {
			MultiSectionParser parser = RoutingEvaluator.multiSectionParser( routingInput );
			parser.addRowParser( SectionType.BAGS, new BagRowParser() );
			parser.addRowParser( SectionType.DEPARTURES, new DepartureRowParser() );
			parser.addRowParser( SectionType.CONVEYOR_SYSTEM, new ConveyorRowParser() );

			parser.addSectionConsumer( SectionType.BAGS, entry -> passengerBags.put( ((BagEntry) entry).getBag().getId(), ((BagEntry) entry) ) );
			parser.addSectionConsumer( SectionType.DEPARTURES, entry -> departures.put( ((Departure) entry).getFlight().getFlightId().getId(), ((Departure) entry) ) );

			parser.addSectionConsumer( SectionType.CONVEYOR_SYSTEM, entry -> {
				ConveyorRoute conveyor = (ConveyorRoute) entry;
				Node<TerminalGate> node1 = new Node<>( conveyor.getFirstTerminal() );
				Node<TerminalGate> node2 = new Node<>( conveyor.getSecondTerminal() );
				conveyorRoutes.addEdge( node1, node2, conveyor.getTravelTime() );

				if (node1.getNodeId().toString().equalsIgnoreCase( baggageClaimId )) {
					baggageClaim = node1;
				}
				if (node2.getNodeId().toString().equalsIgnoreCase( baggageClaimId )) {
					baggageClaim = node2;
				}
			});

			parser.parseSections();

			if ( baggageClaim == null ) {
				throw new ParseException( "Baggage claim node was not found." );
			}

		}


		/**
		 * Load a search strategy from the system property
		 */
		@SuppressWarnings( "unchecked" )
		protected GraphSearchStrategy<TerminalGate, WeightedGraph<TerminalGate>> getSearchStrategy() {
			if ( searchStrategy != null ) { return searchStrategy; }
			try {
				Class<?> clazz = Class.forName( searchStrategyClass );
				searchStrategy = (GraphSearchStrategy<TerminalGate, WeightedGraph<TerminalGate>>)clazz.newInstance();
				return searchStrategy;
			}
			catch ( ClassNotFoundException | InstantiationException | IllegalAccessException e ) {
				throw new RuntimeException( "Error loading search strategy." );
			}
		}

	}

}
