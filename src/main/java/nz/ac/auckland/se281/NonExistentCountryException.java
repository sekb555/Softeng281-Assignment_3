package nz.ac.auckland.se281;

/**
 * This class is used to handle the exception when a user inputs a country that
 * does not or is invalid
 */
public class NonExistentCountryException extends Exception {

  /**
   * This is the constructor for the NonExistentCountryException class it prints
   * the message to the user
   * 
   * @param input the input that the user has entered
   */
  public NonExistentCountryException(String input) {
    super();
    MessageCli.INVALID_COUNTRY.printMessage(input);
  }

}
