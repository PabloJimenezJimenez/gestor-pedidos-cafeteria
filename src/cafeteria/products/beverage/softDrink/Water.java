package cafeteria.products.beverage.softDrink;

public class Water extends  SoftDrinks{
  private static final String NAME = "Water";
  public Water(){
    super(Water.NAME, 1.00);
  }

  @Override
  protected void prepareIngredients() {
    System.out.println("Preparando agua");
  }

  @Override
  protected void cook() {
    System.out.println("Cooking");
  }
}
