package nz.ac.auckland.se281;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** This class is the main entry point. */
public class MapEngine {

  private Set<Country> countrySet;
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
    countrySet = new HashSet<>();
    for (int i = 0; i < countries.size(); i++) {
      String[] data = countries.get(i).split(",");
      countrySet.add(new Country(String.valueOf(i), data[0], data[1], Integer.parseInt(data[2])));
    }

    List<String> adjacencies = Utils.readAdjacencies();
    for (String adjacency : adjacencies) {
      String[] data = adjacency.split(",");
      Country country1 = null;
      Country country2 = null;
      for (Country country : countrySet) {
        if (country.getName().equals(data[0])) {
          country1 = country;
        }
        for(int i = 1; i < data.length; i++) {
          if (country.getName().equals(data[i])) {
            country2 = country;
            graph.addEdge(country1, country2);
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

    for (Country country : countrySet) {
      if (country.getName().equals(countryInput)) {
        MessageCli.COUNTRY_INFO.printMessage(country.getName(), country.getContinent(),
            String.valueOf(country.getCost()));
        return;
      }
    }
  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {
    if (sourceCountry == null){
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
    if (destinationCountry == null){
      try{
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
  public String checkCountryInput(String input) throws NonExistentCountryException{
    for (Country country : countrySet) {
      if (country.getName().equals(input)) {
        return input;
      }
    }
      throw new NonExistentCountryException(input);
  }
}
