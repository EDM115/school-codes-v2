public abstract class House {
  protected Floor floor;
  protected Walls walls;
  protected Roof roof;

  public Floor getFloor() {
    return floor;
  }
  public void setFloor(Floor floor) {
    this.floor = floor;
  }
  public Walls getWalls() {
    return walls;
  }
  public void setWalls(Walls walls) {
    this.walls = walls;
  }
  public Roof getRoof() {
    return roof;
  }
  public void setRoof(Roof roof) {
    this.roof = roof;
  }
  public abstract String getRepresentation();
}