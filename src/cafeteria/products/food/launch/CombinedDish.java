package cafeteria.products.food.launch;

import cafeteria.products.Product;
import java.util.ArrayList;
import java.util.List;

public class CombinedDish implements Product, ItemLaunch {

  private final List<ItemLaunch> components;

  public CombinedDish() {
    this.components = new ArrayList<>();
  }

  public void add(ItemLaunch item) {
    this.components.add(item);
  }

  @Override
  public Double getPrice() {
    Double total = 0.0;
    for (ItemLaunch component : this.components) {
      total += component.getPrice();
    }
    return total;
  }

  @Override
  public String getDescription() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < this.components.size(); i++) {
      if (i > 0) {
        sb.append(" con ");
      }
      sb.append(this.components.get(i).getDescription());
    }
    return sb.toString();
  }

  @Override
  public void prepare() {
    for (ItemLaunch component : this.components) {
      if (component instanceof Product) {
        ((Product) component).prepare();
      }
    }
  }
}
