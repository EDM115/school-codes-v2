public class WoodBuilder extends HouseBuilder {
  public House createHouse() {
    house = new WoodHouse();
    System.out.println(house.getRepresentation());
    return house;
  }

  public Floor createFloor() {
    floor = new WoodFloor();
    System.out.println(floor.getRepresentation());
    return floor;
  }

  public Roof createRoof() {
    roof = new WoodRoof();
    System.out.println(roof.getRepresentation());
    return roof;
  }

  public Walls createWalls() {
    walls = new WoodWalls();
    System.out.println(walls.getRepresentation());
    return walls;
  }
}
