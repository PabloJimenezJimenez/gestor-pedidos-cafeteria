package cafeteria.products.beverage.softDrink;

public class Juice extends SoftDrinks {
  private static final String NAME = "Juice";
  public Juice(){
    super(Juice.NAME, 2.20);
  }
  @Override
  protected void prepareIngredients() {
    System.out.println("Preparando zumo");
  }

  @Override
  protected void cook() {
    System.out.println("Exprimiendo...");
  }
}
