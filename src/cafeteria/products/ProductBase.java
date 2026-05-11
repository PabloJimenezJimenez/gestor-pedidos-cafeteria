package cafeteria.products;

public abstract class ProductBase implements Product{
  private final static Double DEFAULT_PRICE = 100.0;
  private final static String DEFAULT_NAME = "N/A";
  private final String name;
  private Double basePrice;
  protected ProductBase(String name, Double basePrice) {
    this.name = name;
    this.basePrice = basePrice;
  }
  protected ProductBase(String name) {
    this(name, ProductBase.DEFAULT_PRICE);
  }
  protected ProductBase(Double basePrice) {
    this(ProductBase.DEFAULT_NAME, basePrice);
  }
  protected ProductBase() {
    this(ProductBase.DEFAULT_NAME,  ProductBase.DEFAULT_PRICE);
  }


  @Override
  public Double getPrice() {
    return this.basePrice;
  }

  public void setBasePrice(Double price) {
    this.basePrice = price;
  }

  @Override
  public String getDescription() {
    return this.name;
  }

  protected void reserveStation(){
    System.out.println("Se ha inicializado la reserva del producto");
    
  }

  protected abstract void prepareIngredients();
  protected abstract void cook();
  protected void plate(){
    System.out.println("Se ha emplatado el producto "+this.getDescription());
    
  }
  protected void notifyReady(){
    System.out.println("El producto "+ this.getDescription() + " está listo");
    
  }

  @Override
  public final void prepare() {
    this.reserveStation();
    this.prepareIngredients();
    this.cook();
    this.plate();
    this.notifyReady();
    
  }
}
