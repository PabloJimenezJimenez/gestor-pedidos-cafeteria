package cafeteria.payments.tpv;

//Representa a un componente externo simulando una api de un proveedor externo
// Necesario para poder aplicar el patrón adapter
public class BankTPV {
  private Double currentTransactionAmount;

  public void initiateTransaction() {
    System.out.println("[TPV] Inicializando transacción");
    this.currentTransactionAmount = null;
  }

  public void readCard() {
    System.out.println("[TPV] Leyendo banda magnética / chip");
  }

  public void confirmAmount(Double amount) {
    System.out.println("[TPV] Confirmando importe: " + amount + " €");
    this.currentTransactionAmount = amount;
  }

  public String getAuthorizationCode() {
    String code = "AUTH-" + System.currentTimeMillis();
    System.out.println("[TPV] Código de autorización: " + code);
    return code;
  }
}
