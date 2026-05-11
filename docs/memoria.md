# Memoria del proyecto — Cafetería "La Terminal"

> Caso de uso libre — INSO-MAIS Diseño de Software
> Sistema de gestión de pedidos para una cafetería, implementado en Java de consola y diseñado en torno a **11 patrones GoF** combinados.

---

## 1. Descripción general del caso de uso

### 1.1 Contexto

La cafetería "La Terminal" quiere automatizar la toma de pedidos a través de una **terminal de consola** que asista al camarero. Desde una única aplicación se debe poder:

- Crear un pedido vacío e ir añadiendo productos del catálogo.
- Componer productos complejos: bebidas con complementos (azúcar, miel, leches alternativas), pan en distintos formatos (tostada, media tostada, unidad) y platos de *launch* (combinaciones de huevos, bacon, salchichas, yogur, fruta).
- Cobrar el pedido por uno de tres medios: **efectivo, Bizum o TPV bancario** (este último a través de un dispositivo externo con su propia API).
- Orquestar el ciclo de vida del pedido: `Pendiente → En preparación → Listo → Entregado`, propagando los cambios de estado a varios elementos auxiliares (pantalla del cliente, panel informativo, registro de auditoría).
- Ejecutar la preparación de cada producto en la cocina siguiendo una secuencia fija de pasos.

### 1.2 Alcance

- Aplicación **Java de consola**, sin interfaz gráfica.
- Ejecutable como **un único proceso** y **una única sesión** (sin persistencia).
- Un único local, una única cocina, una única terminal.
- Catálogo de productos cerrado (los productos extensibles se añaden con clases nuevas + entrada en la factory correspondiente).

### 1.3 Restricciones

- Solo Java estándar; sin frameworks ni librerías externas.
- Las **combinaciones inválidas** de toppings (p.ej. leche en un espresso) lanzan `UnsupportedOperationException`.
- Las **transiciones de estado inválidas** (p.ej. entregar un pedido no preparado) lanzan `IllegalStateException`.
- Un único punto de entrada para el cliente (`Terminal`) que solo conoce a la fachada (`CafeteriaManager`).

### 1.4 Componentes principales identificados

Resumen de alto nivel; los detalles concretos viven en `src/cafeteria/`:

- **Productos** (`cafeteria.products.*`):
  - Componente abstracto: `Product` (interface) y `ProductBase` (clase abstracta con el algoritmo de preparación).
  - Familias concretas: `Coffee`, `Tea`, `SoftDrinks`, `Bread`, `Pastry`, `LaunchProduct` y sus hojas.
  - Composite: `CombinedDish` agrupa varios `ItemLaunch`.
  - Decoradores: `ProductDecorator` con dos familias — `Topping` (azúcar, miel, leches) y `Format` (tostada, media tostada, unidad).
- **Fábricas** (`cafeteria.factories.*`):
  - `ProductFactory` (interfaz) y sus 6 concretas (una por categoría de producto).
  - `ToppingFactory` (interfaz abstract factory) y sus 4 concretas (una por bebida que admite toppings).
- **Pedido** (`cafeteria.orders.*`):
  - `Order` (sujeto observable y contexto del State).
  - Estados concretos: `Pending`, `InPreparation`, `Ready`, `Delivered`.
  - Observadores concretos: `TerminalScreenObserver`, `InfoPanelObserver`, `AuditLogObserver`.
- **Pago** (`cafeteria.payments.*`):
  - `PaymentMethod` (strategy) y sus implementaciones `CashPayment`, `BizumPayment`, `BankTPVAdapter`.
  - `BankTPV` (adaptee externo) con API incompatible.
- **Sistema** (`cafeteria.system.*` y `cafeteria.kitchen.*`):
  - `Catalog` (singleton, mapa de fábricas).
  - `Kitchen` (singleton, cola de pedidos).
  - `CafeteriaManager` (fachada del subsistema).
  - `Terminal` (singleton, bucle de consola; único cliente de la fachada).

### 1.5 Patrones aplicados (11)

Template Method · Decorator · Composite · Factory Method · Abstract Factory · State · Observer · Strategy · Adapter · Singleton · Facade.

Cada uno se documenta y ejemplifica en la sección siguiente.

---

## 2. Código fuente — un ejemplo por patrón

Para cada patrón se incluye su propósito en el sistema, los roles GoF mapeados a clases del proyecto y **una clase Java representativa** extraída tal cual del repositorio. El resto de hojas (que solo replican el rol) no se reproducen; viven en `src/cafeteria/`.

### 2.1 Template Method

**Propósito:** fijar el algoritmo de preparación de un producto (`reserveStation → prepareIngredients → cook → plate → notifyReady`) en una superclase, dejando que las subclases solo rellenen los pasos variables.

| Rol GoF | Clase del proyecto |
|---|---|
| AbstractClass | `ProductBase` |
| ConcreteClass | `Espresso`, `RedTea`, `Croissant`, … |

```java
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
```

El `prepare()` es `final`, garantizando el orden invariante. Las subclases concretas solo proporcionan `prepareIngredients()` y `cook()`.

### 2.2 Decorator

**Propósito:** añadir responsabilidades (complementos en bebidas, formato en pan) a un `Product` en tiempo de ejecución sin tocar su jerarquía.

| Rol GoF | Clase del proyecto |
|---|---|
| Component | `Product` |
| ConcreteComponent | cualquier subclase de `ProductBase` |
| Decorator | `ProductDecorator` |
| ConcreteDecorator | `Sugar`, `Honey`, `LactoseFreeMilk`, `OatMilk`, `Toasted`, `HalfToasted`, `Unit` |

```java
package cafeteria.products.decorator;
import cafeteria.products.Product;

public abstract class ProductDecorator implements Product {

  protected final Product wrapped;

  protected ProductDecorator(Product wrapped) {
    this.wrapped = wrapped;
  }

  @Override
  public Double getPrice() {
    return this.wrapped.getPrice() + this.extraPrice();
  }

  @Override
  public String getDescription() {
    return this.wrapped.getDescription() + " " + this.extraDescription();
  }

  @Override
  public void prepare() {
    this.wrapped.prepare();
  }

  protected abstract Double extraPrice();
  protected abstract String extraDescription();
}
```

Cada decorador concreto solo implementa `extraPrice()` y `extraDescription()`. Los decoradores se pueden anidar (`new Sugar(new OatMilk(new Latte()))`).

### 2.3 Composite

**Propósito:** tratar un plato de *launch* (con varios componentes: huevos, bacon, fruta…) y un componente individual con la misma interfaz, permitiendo construir platos combinados recursivamente.

| Rol GoF | Clase del proyecto |
|---|---|
| Component | `ItemLaunch` |
| Leaf | `LaunchProduct` y sus hojas (`Eggs`, `Bacon`, `Sausages`, `Yogurt`, `Fruit`) |
| Composite | `CombinedDish` |

```java
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
```

`CombinedDish` implementa tanto `Product` como `ItemLaunch`, de modo que puede usarse en un pedido como producto y a su vez puede contener otros `CombinedDish` (recursividad).

### 2.4 Factory Method

**Propósito:** centralizar la instanciación de cada familia de productos en una clase que conoce las claves de tipo y devuelve el producto correcto.

| Rol GoF | Clase del proyecto |
|---|---|
| Product | `Product` |
| Creator | `ProductFactory` (interface) |
| ConcreteCreator | `CoffeeFactory`, `TeaFactory`, `SoftDrinkFactory`, `BreadFactory`, `PastryFactory`, `LaunchFactory` |

```java
package cafeteria.factories.products;

import cafeteria.products.Product;
import cafeteria.products.beverage.coffe.Americano;
import cafeteria.products.beverage.coffe.Cappuccino;
import cafeteria.products.beverage.coffe.Espresso;
import cafeteria.products.beverage.coffe.Latte;

public class CoffeeFactory implements ProductFactory{
  @Override
  public Product create(String type) {
    return switch (type.toLowerCase()) {
      case "espresso" -> new Espresso();
      case "cappuccino" -> new Cappuccino();
      case "latte" -> new Latte();
      case "americano" -> new Americano();
      default -> throw new IllegalArgumentException("Café desconocido: " + type);
    };
  }
}
```

El cliente (`CafeteriaManager`) solicita `factory.create("latte")` sin conocer la clase concreta.

### 2.5 Abstract Factory

**Propósito:** exponer una familia coherente de productos relacionados — los **toppings válidos para una bebida concreta**. Las combinaciones que no tienen sentido se rechazan explícitamente.

| Rol GoF | Clase del proyecto |
|---|---|
| AbstractFactory | `ToppingFactory` |
| ConcreteFactory | `EspressoToppingFactory`, `CappuccinoToppingFactory`, `LatteToppingFactory`, `TeaToppingFactory` |
| AbstractProduct | `Topping` |
| ConcreteProduct | `Sugar`, `Honey`, `LactoseFreeMilk`, `OatMilk` |

```java
package cafeteria.factories.toppings;

import cafeteria.products.Product;
import cafeteria.products.decorator.toppings.Honey;
import cafeteria.products.decorator.toppings.Sugar;
import cafeteria.products.decorator.toppings.Topping;

public class EspressoToppingFactory implements ToppingFactory {

  private static final String NOT_ALLOWED = "Complemento no válido para espresso";

  @Override
  public Topping createSugar(Product wrapped) {
    return new Sugar(wrapped);
  }

  @Override
  public Topping createHoney(Product wrapped) {
    return new Honey(wrapped);
  }

  @Override
  public Topping createLactoseFreeMilk(Product wrapped) {
    throw new UnsupportedOperationException(EspressoToppingFactory.NOT_ALLOWED);
  }

  @Override
  public Topping createOatMilk(Product wrapped) {
    throw new UnsupportedOperationException(EspressoToppingFactory.NOT_ALLOWED);
  }
}
```

Un espresso admite azúcar y miel pero rechaza leche; otras fábricas (`LatteToppingFactory`) sí la admiten. La fachada elige la fábrica adecuada en función de la bebida.

### 2.6 State

**Propósito:** modelar el ciclo de vida del pedido como objetos de estado intercambiables. Cada estado decide qué transiciones son válidas, evitando un `switch` gigante en `Order`.

| Rol GoF | Clase del proyecto |
|---|---|
| Context | `Order` |
| State | `OrderState` |
| ConcreteState | `Pending`, `InPreparation`, `Ready`, `Delivered` |

```java
package cafeteria.orders.states;

import cafeteria.orders.Order;
import cafeteria.payments.PaymentMethod;

public class Pending implements OrderState {

  private static final String NAME = "Pendiente";
  private static final String INVALID = "El pedido aún no se ha pagado";

  @Override
  public void pay(Order order, PaymentMethod method) {
    Boolean ok = method.charge(order.getTotal());
    if (ok) {
      System.out.println("[Pedido] Pago confirmado, pasando a preparación");
      order.setState(new InPreparation());
    } else {
      System.out.println("[Pedido] Pago rechazado, sigue pendiente");
    }
  }

  @Override
  public void prepare(Order order) {
    throw new IllegalStateException(Pending.INVALID);
  }

  @Override
  public void markReady(Order order) {
    throw new IllegalStateException(Pending.INVALID);
  }

  @Override
  public void deliver(Order order) {
    throw new IllegalStateException(Pending.INVALID);
  }

  @Override
  public String getName() {
    return Pending.NAME;
  }
}
```

`Pending` solo permite la transición `pay()`. Cualquier otra operación rompe la máquina y lanza `IllegalStateException`. Cada estado concreto se comporta análogamente.

### 2.7 Observer

**Propósito:** notificar simultáneamente a varios elementos (pantalla del cliente, panel del local, registro de auditoría) cuando un pedido cambia de estado, sin acoplar `Order` a esas pantallas concretas.

| Rol GoF | Clase del proyecto |
|---|---|
| Subject | `Order` |
| Observer | `OrderObserver` |
| ConcreteObserver | `TerminalScreenObserver`, `InfoPanelObserver`, `AuditLogObserver` |

```java
package cafeteria.orders.observers;


import cafeteria.orders.Order;
import cafeteria.orders.states.OrderState;

public class TerminalScreenObserver implements OrderObserver {
  @Override
  public void onStateChanged(Order order, OrderState state) {
    if (state != null) {
      System.out.println("[Terminal Screen] " + System.currentTimeMillis() + " - Pedido #" + order.getId() + " → "  + state.getName());
    } else {
      System.out.println("[Terminal Screen] " + System.currentTimeMillis() + " - Pedido #" + order.getId() + " → " + order.getStateName());
    }
  }
}
```

Cualquier nuevo "consumidor de eventos" (un KDS de cocina, una pantalla en barra, un *push* al móvil del cliente) se añade con una clase nueva sin tocar `Order`.

### 2.8 Strategy

**Propósito:** intercambiar el algoritmo de cobro en caliente. La fachada selecciona la estrategia que indique el cliente y `Order.pay(...)` la invoca sin saber cuál es.

| Rol GoF | Clase del proyecto |
|---|---|
| Strategy | `PaymentMethod` |
| ConcreteStrategy | `CashPayment`, `BizumPayment`, `BankTPVAdapter` |
| Context | `Order` |

```java
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
```

Para añadir un método nuevo (criptomoneda, transferencia, vale) basta con implementar `PaymentMethod`.

### 2.9 Adapter

**Propósito:** integrar un dispositivo externo (`BankTPV`) cuya API no encaja con `PaymentMethod`, exponiéndolo como una estrategia más sin modificar ni el dispositivo ni los clientes.

| Rol GoF | Clase del proyecto |
|---|---|
| Target | `PaymentMethod` |
| Adaptee | `BankTPV` |
| Adapter | `BankTPVAdapter` |

```java
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
```

El adaptador traduce el método único `charge(amount)` a la **secuencia** de cuatro llamadas que espera el TPV real.

### 2.10 Singleton

**Propósito:** garantizar una única instancia de los recursos globales del local (cocina, catálogo, terminal). No tiene sentido tener dos cocinas concurrentes ni dos catálogos descoordinados.

| Rol GoF | Clase del proyecto |
|---|---|
| Singleton | `Kitchen`, `Catalog`, `Terminal`, `CafeteriaManager` |

```java
package cafeteria.kitchen;

import cafeteria.orders.Order;
import java.util.LinkedList;
import java.util.Queue;

public class Kitchen {

  private static Kitchen instance;

  private final Queue<Order> queue;

  private Kitchen() {
    this.queue = new LinkedList<>();
  }

  public static Kitchen getInstance() {
    if (Kitchen.instance == null) {
      Kitchen.instance = new Kitchen();
    }
    return Kitchen.instance;
  }

  public void enqueue(Order order) {
    System.out.println("[Cocina] Pedido #" + order.getId() + " encolado");
    this.queue.add(order);
  }

  public void processNext() {
    Order next = this.queue.poll();
    if (next == null) {
      System.out.println("[Cocina] No hay pedidos pendientes");
      return;
    }
    System.out.println("[Cocina] Procesando pedido #" + next.getId());
    next.prepare();
    next.deliver();
  }

  public Boolean hasPendingOrders() {
    return !this.queue.isEmpty();
  }
}
```

Constructor privado + `getInstance()` perezoso. La cola de pedidos vive dentro de la instancia única, garantizando consistencia entre productores (terminal) y consumidor (procesador de cocina).

### 2.11 Facade

**Propósito:** ofrecer a `Terminal` un único punto de entrada que oculte la complejidad del sistema (catálogo, fábricas, cocina, pagos, estados, observadores). El cliente no instancia nada manualmente.

| Rol GoF | Clase del proyecto |
|---|---|
| Facade | `CafeteriaManager` |
| Subsystem | `Catalog`, `Kitchen`, `Order`, `ProductFactory`, `ToppingFactory`, `PaymentMethod`, `OrderObserver` |
| Client | `Terminal` |

```java
package cafeteria.system;

import cafeteria.factories.products.ProductFactory;
import cafeteria.factories.toppings.ToppingFactory;
import cafeteria.kitchen.Kitchen;
import cafeteria.orders.Order;
import cafeteria.orders.observers.OrderObserver;
import cafeteria.payments.PaymentMethod;
import cafeteria.products.Product;
import java.util.ArrayList;
import java.util.List;

public class CafeteriaManager {

  private static CafeteriaManager instance;

  private final Catalog catalog;
  private final Kitchen kitchen;
  private final List<Order> activeOrders;

  private CafeteriaManager() {
    this.catalog = Catalog.getInstance();
    this.kitchen = Kitchen.getInstance();
    this.activeOrders = new ArrayList<>();
  }

  public static CafeteriaManager getInstance() {
    if (CafeteriaManager.instance == null) {
      CafeteriaManager.instance = new CafeteriaManager();
    }
    return CafeteriaManager.instance;
  }

  public Order createNewOrder() {
    Order order = new Order();
    this.activeOrders.add(order);
    return order;
  }

  public Product createProduct(String category, String type) {
    ProductFactory factory = this.catalog.getProductFactory(category);
    return factory.create(type);
  }

  public ToppingFactory getToppingFactoryFor(String beverageName) {
    return this.catalog.getToppingFactory(beverageName);
  }

  public void addProductToOrder(Order order, Product product) {
    order.addItem(product);
  }

  public Boolean checkout(Order order, PaymentMethod method) {
    try {
      order.pay(method);
      this.kitchen.enqueue(order);
      return true;
    } catch (IllegalStateException e) {
      System.out.println("[Manager] Error al cobrar: " + e.getMessage());
      return false;
    }
  }

  public void processKitchenQueue() {
    while (this.kitchen.hasPendingOrders()) {
      this.kitchen.processNext();
    }
  }

  public void registerObserverOnOrder(Order order, OrderObserver observer) {
    order.registerObserver(observer);
  }
}
```

`Terminal` solo invoca métodos de `CafeteriaManager`. Toda la maquinaria (singletons, factories, máquina de estados, observadores, cola de cocina) queda encapsulada detrás de esta interfaz.

---

## 3. Conclusiones y próximos pasos

### 3.1 Conclusiones

Aplicar **11 patrones** combinados a un mismo caso de uso ha permitido construir un sistema en el que **cada decisión de diseño tiene un nombre**, y donde cada clase tiene una responsabilidad clara. Agrupando por familia GoF:

- **Creacionales — Factory Method, Abstract Factory, Singleton.** Aíslan al `CafeteriaManager` del conocimiento concreto de cómo se construye cada producto o complemento, y garantizan una única instancia de los recursos globales (cocina, catálogo, terminal). Añadir un nuevo café o un nuevo topping es local: una clase nueva más una entrada en la factory correspondiente.
- **Estructurales — Decorator, Composite, Adapter, Facade.** Componen comportamiento (decoradores apilables sobre cualquier `Product`), componen estructura (`CombinedDish` agrupa hojas o platos compuestos), integran lo que no encaja (`BankTPVAdapter` adapta un TPV con API ajena) y simplifican la superficie expuesta al cliente (`CafeteriaManager` oculta seis subsistemas).
- **De comportamiento — Template Method, State, Observer, Strategy.** Modelan los algoritmos del dominio: la secuencia invariante de preparación en cocina (Template Method), el ciclo de vida del pedido con transiciones legales (State), la difusión de cambios a observadores arbitrarios (Observer) y la flexibilidad para sustituir el algoritmo de cobro (Strategy).

El resultado es una arquitectura **abierta a extensión y cerrada a modificación**: cualquier nueva bebida, nuevo medio de pago, nueva pantalla o nuevo estado se introduce mediante una clase nueva sin tocar el núcleo. La complejidad no desaparece, pero queda **descompuesta** en piezas pequeñas y nombradas — lo que el patrón GoF promete.

También se evidencian las tensiones de combinar patrones: el `CafeteriaManager` actúa simultáneamente como Facade y Singleton; `BankTPVAdapter` cumple a la vez los roles de Adapter y Strategy. Esta superposición es deliberada y refleja que los patrones describen *intenciones*, no clases distintas.

### 3.2 Próximos pasos

Mejoras y caminos de evolución para una segunda iteración del proyecto:

- **Tests automatizados con JUnit 5**: una clase de tests por patrón. Casos especialmente útiles:
  - Máquina de estados completa de `Order` (todas las transiciones legales y todas las ilegales).
  - Decoradores acumulativos: `getPrice()` y `getDescription()` con dos y tres capas anidadas.
  - `EspressoToppingFactory` que rechaza leche y la acepta en `LatteToppingFactory`.
  - `BankTPVAdapter` con un *fake* `BankTPV` que simule autorización y rechazo.
- **Persistencia** del histórico de pedidos en JSON o JDBC, para no perder el estado al reiniciar y permitir reportes posteriores.
- **Integración real con TPV** sustituyendo el `BankTPV` mock por una librería bancaria (Redsys, Adyen, Stripe Terminal…). La fachada **no cambiaría**: solo el adapter.
- **Interfaz gráfica** (JavaFX, Swing o una capa web ligera) reutilizando `CafeteriaManager` como API. La fachada está diseñada precisamente para soportar otro cliente además de `Terminal`.
- **Observadores adicionales**: integración con un KDS (*Kitchen Display System*), notificaciones *push* al móvil del cliente, métricas en tiempo real a un panel del encargado.
- **Catalog dinámico**: cargar el mapeo categoría→fábrica desde un fichero de configuración (properties o YAML) para añadir productos sin recompilar.
- **Concurrencia**: hoy la cocina y la terminal son síncronas. Un siguiente paso natural es procesar la cola de cocina en un hilo aparte, con `BlockingQueue`, manteniendo la API pública intacta.
- **CI** con compilación, tests y lint estático (Checkstyle, SpotBugs) ejecutándose en cada commit.
- **Documentación del usuario final**: manual breve de uso de la terminal y guía de extensión para futuros desarrolladores ("cómo añadir un nuevo café en cinco pasos").
