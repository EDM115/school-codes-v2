public class HouseDirector {

  public House constructHouse(HouseBuilder builder) {
    builder.createHouse();
    builder.createFloor();
    builder.createWalls();
    builder.createRoof();

    return builder.house;
  }
}
