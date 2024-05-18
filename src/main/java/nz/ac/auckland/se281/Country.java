package nz.ac.auckland.se281;

/**
 * This class allows for the creation of country objects that store information
 * about a country
 */
public class Country {
  private String id;
  private String name;
  private String continent;
  private int cost;

  /**
   * This is the constructor for the Country class it initializes the country
   * object
   * 
   * @param id        the hashcode/id of the country
   * @param name      the name of the country
   * @param continent the continent the country is located in
   * @param cost      the amount of taxes that need to be paid to cross the border
   *                  to the country
   */
  public Country(String id, String name, String continent, int cost) {
    this.id = id;
    this.name = name;
    this.continent = continent;
    this.cost = cost;
  }

  /**
   * This method returns the name of the country
   * 
   * @return returns the name of the country as a string
   */
  public String getName() {
    return name;
  }

  /**
   * This method returns the continent the country is located in
   * 
   * @return returns the continent the country is located in as a string
   */
  public String getContinent() {
    return continent;
  }

  /**
   * This method returns the cost of crossing the border to the country
   * 
   * @return returns the cost of crossing the border to the country as an integer
   */
  public int getCost() {
    return cost;
  }

  /**
   * This method returns the id of the country
   * 
   * @return returns the id of the country as a string
   */
  public String getId() {
    return id;
  }

  /**
   * This method overrides the hashcode method to set the hashcode of this country
   * object
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  /**
   * This method overrides the equals method to compare two country objects based
   * on their id
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Country other = (Country) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

}
