package nz.ac.auckland.se281;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/** This class is the main entry point. */
public class MapEngine {

  private List<Country> countryList;
  private Graph graph;
  private String sourceCountry = null;
  private String destinationCountry = null;
  private String countryInput;

  /** This is the constructor for the MapEngine class. */
  public MapEngine() {
    graph = new Graph();
    // add other code here if you want
    loadMap(); // keep this mehtod invocation
  }

  /** invoked one time only when constracting the MapEngine class. */
  private void loadMap() {
    List<String> countries = Utils.readCountries();
    countryList = new LinkedList<>();
    // adds all the countries to the countryList
    for (int i = 0; i < countries.size(); i++) {
      String[] data = countries.get(i).split(",");
      countryList.add(new Country(String.valueOf(i), data[0], data[1], Integer.parseInt(data[2])));
    }

    // maps out all of the adjacencies between the countries
    List<String> adjacencies = Utils.readAdjacencies();
    for (String adjacency : adjacencies) {
      String[] data = adjacency.split(",");
      Country country1 = null;
      for (Country country : countryList) {
        if (data[0].equals(country.getName())) {
          country1 = country;
          break;
        }
      }
      if (country1 != null) {
        for (int i = 1; i < data.length; i++) {
          for (Country country : countryList) {
            if (data[i].equals(country.getName())) {
              graph.addEdge(country1, country);
            }
          }
        }
      }
    }
  }

  /** this method is invoked when the user run the command info-country. */
  public void showInfoCountry() {
    // sets the country input by user and checks if it is valid if not then it asks
    // the user to input again
    try {
      MessageCli.INSERT_COUNTRY.printMessage();
      countryInput = Utils.capitalizeFirstLetterOfEachWord(Utils.scanner.nextLine());
      countryInput = checkCountryInput(countryInput);
    } catch (NonExistentCountryException e) {
      showInfoCountry();
      return;
    }

    // prints the information of the country
    for (Country country : countryList) {
      if (country.getName().equals(countryInput)) {
        MessageCli.COUNTRY_INFO.printMessage(country.getName(), country.getContinent(),
            String.valueOf(country.getCost()));
        return;
      }
    }
  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {
    // sets the source and destination countries by user input and checks if they
    // are valid if not then it asks the user to input again
    if (sourceCountry == null) {
      try {
        MessageCli.INSERT_SOURCE.printMessage();
        sourceCountry = Utils.capitalizeFirstLetterOfEachWord(Utils.scanner.nextLine());
        sourceCountry = checkCountryInput(sourceCountry);
      } catch (NonExistentCountryException e) {
        sourceCountry = null;
        showRoute();
        return;
      }
    }
    if (destinationCountry == null) {
      try {
        MessageCli.INSERT_DESTINATION.printMessage();
        destinationCountry = Utils.capitalizeFirstLetterOfEachWord(Utils.scanner.nextLine());
        destinationCountry = checkCountryInput(destinationCountry);
      } catch (NonExistentCountryException e) {
        destinationCountry = null;
        showRoute();
        return;
      }
    }
    // checks if the source and destination countries are the same then it prints
    // the appropriate message as no cross border travel is required
    if (sourceCountry.equals(destinationCountry)) {
      MessageCli.NO_CROSSBORDER_TRAVEL.printMessage();
      return;
    }

    // assigns the user input to country objects of the same name
    Country start = null;
    Country end = null;
    for (int i = 0; i < countryList.size(); i++) {
      if (countryList.get(i).getName().equals(sourceCountry)) {
        start = countryList.get(i);
        for (int j = 0; j < countryList.size(); j++) {
          if (countryList.get(j).getName().equals(destinationCountry)) {
            end = countryList.get(j);
            break;
          }
        }
      }
    }

    // finds the shortest path between the source and destination countries
    List<Country> route = graph.routeFinder(start, end);
    List<String> shortPath = new LinkedList<>();
    Set<String> continents = new LinkedHashSet<>();
    for (Country country : route) {
      if (country != null) {
        shortPath.add(country.getName());
        continents.add(country.getContinent());
      }
    }

    // calculates the taxes to be paid to cross the borders not including the source
    // country
    int taxes = 0;
    for (int i = 1; i < route.size(); i++) {
      taxes += route.get(i).getCost();
    }

    // prints the shortest path, the continents visited and the taxes to be paid
    MessageCli.ROUTE_INFO.printMessage(shortPath.toString());
    MessageCli.CONTINENT_INFO.printMessage(continents.toString());
    MessageCli.TAX_INFO.printMessage(String.valueOf(taxes));

    // resets the source and destination countries to null
    sourceCountry = null;
    destinationCountry = null;
  }

  /**
   * This method is used to check if the country input is valid and exists in the
   * country set.
   * 
   * @return the country input if it is valid and an exception if it is not.
   * @throws NonExistentCountryException if the country input is not valid.
   */
  public String checkCountryInput(String input) throws NonExistentCountryException {
    // check if the country input is valid
    for (Country country : countryList) {
      if (country.getName().equals(input)) {
        return input;
      }
    }
    // throws an exception if the country input is not valid
    throw new NonExistentCountryException(input);
  }

}
