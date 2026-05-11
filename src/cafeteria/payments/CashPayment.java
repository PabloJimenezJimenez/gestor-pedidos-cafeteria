package cafeteria.payments;

public class CashPayment implements PaymentMethod{
  @Override
  public Boolean charge(Double amount) {
    System.out.println("Cobrando " + amount + " € en efectivo");
    System.out.println("Abriendo cajón");
    System.out.println("Entregando cambio si procede");
    return true;
  }
}
