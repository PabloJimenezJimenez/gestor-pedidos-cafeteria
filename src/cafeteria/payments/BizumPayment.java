package cafeteria.payments;

public class BizumPayment implements PaymentMethod{
  private final String phoneNumber;

  public BizumPayment(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  @Override
  public Boolean charge(Double amount) {
    System.out.println("Solicitando Bizum de " + amount + " € al teléfono " + this.phoneNumber);
    System.out.println("Esperando confirmación del cliente");
    System.out.println("Bizum confirmado");
    return true;
  }
}
