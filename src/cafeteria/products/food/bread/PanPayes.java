package cafeteria.products.food.bread;

public class PanPayes extends Bread {
  private static final String NAME = "Pan payés";
  public PanPayes() {
    super(PanPayes.NAME, 2.50);
  }

  @Override
  protected void prepareIngredients() {
    System.out.println("Cogiendo pan payés de la panera");
  }

  @Override
  protected void cook() {
    System.out.println("Cortando pan payés");
  }
}