package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Graph {
  private Map<Country, List<Country>> adjNodes;

  public Graph(){
    this.adjNodes = new HashMap<>();
  }

  public void addNode(Country node){
    adjNodes.putIfAbsent(node, new ArrayList<>());
  }

  public void addEdge(Country node1, Country node2){
    addNode(node1);
    addNode(node2);
    adjNodes.get(node1).add(node2);
    adjNodes.get(node2).add(node1);
  }

  public void removeNode(Country node){
    adjNodes.remove(node);
    for(Country country : adjNodes.keySet()){
      adjNodes.get(country).remove(node);
    }
  }

  public void removeEdge(Country node1, Country node2){
    adjNodes.getOrDefault(node1, new ArrayList<>()).remove(node2);
    adjNodes.getOrDefault(node2, new ArrayList<>()).remove(node1);
  }


  public List<Country> routeFinder(Country root){
    List<Country> visited = new ArrayList<>();
    List<Country> queue = new LinkedList<>();
    visited.add(root);
    queue.add(root);
    while(!queue.isEmpty()){
      Country node = queue.remove(0);
      for(Country n : adjNodes.get(node)){
        if(!visited.contains(n)){
          visited.add(n);
          queue.add(n);
        }
      }
    }
    return visited;

  }
}
