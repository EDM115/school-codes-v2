public class HouseDirector {
  public House construcHouse(HouseBuilder builder) {
    House house = builder.createHouse();
    System.out.println(house.getRepresentation());
   
    house.setFloor(builder.createFloor());
    System.out.println(house.getFloor().getRepresentation());
    
    house.setWalls(builder.createWalls());
    System.out.println(house.getWalls().getRepresentation());
    
    house.setRoof(builder.createRoof());
    System.out.println(house.getRoof().getRepresentation());
    return house;
  }
}