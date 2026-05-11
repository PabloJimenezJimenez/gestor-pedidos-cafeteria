package cafeteria.products.food.launch;

public class Bacon extends LaunchProduct {
  private static final String NAME = "Bacon";
  public Bacon() {
    super(Bacon.NAME, 1.50);
  }

  @Override
  protected void prepareIngredients() {
    System.out.println("Cortando lonchas de bacon");
  }

  @Override
  protected void cook() {
    System.out.println("Friendo bacon en la plancha");
  }
}