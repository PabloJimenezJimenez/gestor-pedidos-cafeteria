package cafeteria.products.food.bread;

public class Chapata extends Bread {
  private static final String NAME = "Chapata";
  public Chapata() {
    super(Chapata.NAME, 2.20);
  }

  @Override
  protected void prepareIngredients() {
    System.out.println("Cogiendo chapata de la panera");
  }

  @Override
  protected void cook() {
    System.out.println("Cortando chapata");
  }
}