public class WoodBuilder extends HouseBuilder {
  public Floor createFloor() {
    floor = new WoodFloor();
    return floor;
  }
  
  public House createHouse() {
    house = new WoodHouse();
    return house;
  }
  public Roof createRoof() {
    roof = new WoodRoof();
    return roof;
  }
  public Walls createWalls() {
    walls = new WoodWalls();
    return walls;
  }
}