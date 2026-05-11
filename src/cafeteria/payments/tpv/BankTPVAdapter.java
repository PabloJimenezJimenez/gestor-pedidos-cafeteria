package cafeteria.payments.tpv;

import cafeteria.payments.PaymentMethod;

public class BankTPVAdapter implements PaymentMethod {
  private final BankTPV tpv;

  public BankTPVAdapter(BankTPV tpv) {
    this.tpv = tpv;
  }

  @Override
  public Boolean charge(Double amount) {
    this.tpv.initiateTransaction();
    this.tpv.readCard();
    this.tpv.confirmAmount(amount);
    String authCode = this.tpv.getAuthorizationCode();
    System.out.println("Cobro con tarjeta autorizado: " + authCode);
    return true;
  }
}
