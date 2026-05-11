package cafeteria.products.food.launch;

public class Yogurt extends LaunchProduct {
  private static final String NAME = "Yogur";
  public Yogurt() {
    super(Yogurt.NAME, 1.20);
  }

  @Override
  protected void prepareIngredients() {
    System.out.println("Sacando yogur del frigorífico");
  }

  @Override
  protected void cook() {
    System.out.println("Sirviendo yogur en cuenco");
  }
}
