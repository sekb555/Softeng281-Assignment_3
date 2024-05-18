package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/** This class is the main entry point. */
public class MapEngine {

  private List<Country> countryList;
  private Graph graph;
  private String sourceCountry = null;
  private String destinationCountry = null;
  private String countryInput;

  public MapEngine() {
    graph = new Graph();
    // add other code here if you want
    loadMap(); // keep this mehtod invocation
  }

  /** invoked one time only when constracting the MapEngine class. */
  private void loadMap() {
    List<String> countries = Utils.readCountries();
    countryList = new LinkedList<>();
    for (int i = 0; i < countries.size(); i++) {
      String[] data = countries.get(i).split(",");
      countryList.add(new Country(String.valueOf(i), data[0], data[1], Integer.parseInt(data[2])));
    }

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
    try {
      MessageCli.INSERT_COUNTRY.printMessage();
      countryInput = Utils.capitalizeFirstLetterOfEachWord(Utils.scanner.nextLine());
      countryInput = checkCountryInput(countryInput);
    } catch (NonExistentCountryException e) {
      showInfoCountry();
      return;
    }

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
    if (sourceCountry.equals(destinationCountry)) {
      MessageCli.NO_CROSSBORDER_TRAVEL.printMessage();
      return;
    }

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

    List<Country> route = graph.routeFinder(start, end);
    List<String> shortPath = new ArrayList<>();
    for (Country country : route) {
      if (country != null) {
        shortPath.add(country.getName());
      }
    }
    MessageCli.ROUTE_INFO.printMessage(shortPath.toString());

    sourceCountry = null;
    destinationCountry = null;
  }

  /**
   * This method is used to check if the country input is valid and exists in the
   * country set.
   * 
   * @return the country input if it is valid and an exception if it is not.
   * @throws NonExistentCountryException
   */
  public String checkCountryInput(String input) throws NonExistentCountryException {
    for (Country country : countryList) {
      if (country.getName().equals(input)) {
        return input;
      }
    }
    throw new NonExistentCountryException(input);
  }

}
