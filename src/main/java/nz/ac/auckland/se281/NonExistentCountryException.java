package nz.ac.auckland.se281;

public class NonExistentCountryException extends Exception{
  
  public NonExistentCountryException(String input) {
    super();
    MessageCli.INVALID_COUNTRY.printMessage(input);
  }

  
}
