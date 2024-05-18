package nz.ac.auckland.se281;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** This class is the main entry point. */
public class MapEngine {

  private Set<Country> countrySet;
  private String countryInput;

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
    try {
      countryInput = checkCountryInput();
    } catch (NonExistentCountryException e) {
      MessageCli.INVALID_COUNTRY.printMessage(countryInput);
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
  }

  /**
   * This method is used to check if the country input is valid and exists in the
   * country set.
   * 
   * @return the country input if it is valid and an exception if it is not.
   * @throws NonExistentCountryException 
   */
  public String checkCountryInput() throws NonExistentCountryException{
    MessageCli.INSERT_COUNTRY.printMessage();
    countryInput = Utils.capitalizeFirstLetterOfEachWord(Utils.scanner.nextLine());
    for (Country country : countrySet) {
      if (country.getName().equals(countryInput)) {
        return countryInput;
      }
    }
      throw new NonExistentCountryException();
  }
}
