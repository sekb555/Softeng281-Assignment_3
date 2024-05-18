package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * This class represents a graph data structure that is used to store the countries and the links
 * between them It also contains a method to find the shortest path between two countries.
 */
public class Graph {
  private Map<Country, List<Country>> adjNodes;

  /** This is the constructor for the Graph class it initializes the adjNodes map. */
  public Graph() {
    this.adjNodes = new HashMap<>();
  }

  /**
   * This method adds a country to the graph as a node.
   *
   * @param node the country to add to the graph.
   */
  public void addNode(Country node) {
    adjNodes.putIfAbsent(node, new ArrayList<>());
  }

  /**
   * This method adds an edge/link between two countries and also invokes the addNode method incase
   * the nodes have not been added to the graph yet.
   *
   * @param node1 takes the first country.
   * @param node2 takes another country.
   */
  public void addEdge(Country node1, Country node2) {
    addNode(node1);
    addNode(node2);
    adjNodes.get(node1).add(node2);
  }

  /**
   * This method removes a country from the graph.
   *
   * @param node the country to be removed.
   */
  public void removeNode(Country node) {
    adjNodes.remove(node);
    for (Country country : adjNodes.keySet()) {
      adjNodes.get(country).remove(node);
    }
  }

  /**
   * This method removes an edge between two countries.
   *
   * @param node1 takes the first country.
   * @param node2 takes another country.
   */
  public void removeEdge(Country node1, Country node2) {
    adjNodes.getOrDefault(node1, new ArrayList<>()).remove(node2);
    adjNodes.getOrDefault(node2, new ArrayList<>()).remove(node1);
  }

  /**
   * This method finds the shortest path between two countries by using the breadth-first search
   * algorithm.
   *
   * @param start The country where the journey starts.
   * @param end The country where the jornery ends.
   * @return A list of countries that represent the shortest path between the start and end
   *     countries.
   */
  public List<Country> routeFinder(Country start, Country end) {
    // a list of the visited countries
    List<Country> visited = new ArrayList<>();
    // a queue to store the countries to be visited
    Queue<Country> queue = new LinkedList<>();
    // a map to store the path for later reconstruction
    Map<Country, Country> path = new HashMap<>(); // To reconstruct the path
    // add the start country to the queue and the visited list
    queue.add(start);
    visited.add(start);
    // while the queue is not empty
    while (!queue.isEmpty()) {
      // get the first country in the queue
      Country node = queue.poll();

      // if the current country is the end country then the path is reconstructed for
      // the shortest path
      if (node.equals(end)) {
        // Reconstruct the path from end to start
        List<Country> route = new ArrayList<>();
        while (node != null) {
          route.add(0, node);
          node = path.get(node);
        }
        return route;
      }
      // for each adjacent country of the current country
      for (Country n : adjNodes.get(node)) {
        if (!visited.contains(n)) {
          visited.add(n);
          queue.add(n);
          path.put(n, node);
        }
      }
    }

    // if no path is found then an empty list is returned
    return Collections.emptyList();
  }
}
