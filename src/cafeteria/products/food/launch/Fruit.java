package cafeteria.products.food.launch;

public class Fruit extends LaunchProduct {
  private static final String NAME = "Fruta";

  public Fruit() {
    super(Fruit.NAME, 1.40);
  }

  @Override
  protected void prepareIngredients() {
    System.out.println("Lavando fruta");
  }

  @Override
  protected void cook() {
    System.out.println("Cortando fruta en trozos");
  }
}
