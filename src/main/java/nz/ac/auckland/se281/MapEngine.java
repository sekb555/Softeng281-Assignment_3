package nz.ac.auckland.se281;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** This class is the main entry point. */
public class MapEngine {

  private Set<Country> countrySet;

  public MapEngine() {
    // add other code here if you want
    loadMap(); // keep this mehtod invocation
  }

  /** invoked one time only when constracting the MapEngine class. */
  private void loadMap() {
    List<String> countries = Utils.readCountries();
    List<String> adjacencies = Utils.readAdjacencies();

    countrySet = new HashSet<>();
    for (int i = 0; i < countries.size(); i++) {
      String[] data = countries.get(i).split(",");
      countrySet.add(new Country(String.valueOf(i), data[0], data[1], Integer.parseInt(data[2])));
    }

  }

  /** this method is invoked when the user run the command info-country. */
  public void showInfoCountry() {
    MessageCli.INSERT_COUNTRY.printMessage();
    String countryInput = Utils.scanner.nextLine();
    for (Country country : countrySet) {
      if (country.getName().equalsIgnoreCase(countryInput)) {
        MessageCli.COUNTRY_INFO.printMessage(country.getName(), country.getContinent(),
            String.valueOf(country.getCost()));
        return;
      }
    }
  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {
  }
}
