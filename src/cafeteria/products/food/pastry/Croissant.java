package cafeteria.products.food.pastry;

public class Croissant extends Pastry {
  private static final String NAME = "Croissant";
  public Croissant() {
    super(Croissant.NAME, 1.50);
  }

  @Override
  protected void prepareIngredients() {
    System.out.println("Cogiendo croissant de la vitrina");
  }

  @Override
  protected void cook() {
    System.out.println("Calentando croissant");
  }
}
