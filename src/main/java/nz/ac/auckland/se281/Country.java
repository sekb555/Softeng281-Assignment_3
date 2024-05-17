package nz.ac.auckland.se281;

public class Country {
  private String id;
  private String name;
  private String continent;
  private int cost;

  public Country(String id, String name, String continent, int cost) {
    this.id = id;
    this.name = name;
    this.continent = continent;
    this.cost = cost;
  }

  public String getName() {
    return name;
  }

  public String getContinent() {
    return continent;
  }

  public int getCost() {
    return cost;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

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
