package cafeteria.payments;

public interface PaymentMethod {
  Boolean charge(Double amount);
}