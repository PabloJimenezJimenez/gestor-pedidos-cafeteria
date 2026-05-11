package cafeteria.products.food.launch;

public class Sausages extends LaunchProduct {
  private static final String NAME = "Salchichas";
  public Sausages() {
    super(Sausages.NAME, 1.60);
  }

  @Override
  protected void prepareIngredients() {
    System.out.println("Sacando salchichas del frigorífico");
  }

  @Override
  protected void cook() {
    System.out.println("Asando salchichas en la plancha");
  }
}