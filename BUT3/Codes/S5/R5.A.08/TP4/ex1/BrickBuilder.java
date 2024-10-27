public class BrickBuilder extends HouseBuilder {
  public House createHouse() {
    house = new BrickHouse();
    System.out.println(house.getRepresentation());
    return house;
  }

  public Floor createFloor() {
    floor = new BrickFloor();
    System.out.println(floor.getRepresentation());
    return floor;
  }

  public Roof createRoof() {
    roof = new BrickRoof();
    System.out.println(roof.getRepresentation());
    return roof;
  }

  public Walls createWalls() {
    walls = new BrickWalls();
    System.out.println(walls.getRepresentation());
    return walls;
  }
}
