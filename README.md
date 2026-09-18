# Gestor de pedidos — Cafetería "La Terminal"

Sistema de gestión de pedidos para una cafetería, desarrollado en Java de consola como práctica de Diseño de Software. Simula la terminal de autoservicio de un local: el cliente compone su pedido, lo personaliza, elige el método de pago y la cocina lo procesa.

## Funcionalidad

- **Catálogo de productos** organizado en bebidas (cafés, tés y refrescos) y comidas (bollería, panadería y *launch food*).
- **Terminal de autoservicio** dirigida por menús: nueva orden, añadir productos y consultar pedidos pendientes.
- **Personalización de bebidas** con complementos (azúcar, miel, leche sin lactosa y leche de avena); cada bebida admite únicamente los complementos válidos y las combinaciones inválidas se rechazan.
- **Platos combinados**: los productos de *launch food* se pueden combinar (p. ej. "huevos con bacon"); su precio es la suma de los componentes y la descripción los refleja todos.
- **Pago** en efectivo, por Bizum o con tarjeta a través de un TPV bancario externo adaptado.
- **Ciclo de vida del pedido**: `Pendiente → En preparación → Listo → Entregado`, con transiciones validadas por cada estado.
- **Cocina**: procesa la cola de pedidos siguiendo siempre la misma secuencia de preparación.
- **Notificaciones**: varios observadores (pantalla de la terminal, panel informativo y registro de auditoría) reciben los cambios de estado del pedido de forma independiente.

## Tecnologías

| Tecnología | Uso |
|---|---|
| Java 17 o superior (probado con JDK 24) | Lenguaje. Solo Java estándar: sin Maven, Gradle ni dependencias externas |
| `javac` / `java` | Compilación y ejecución |
| PlantUML | Diagramas UML de clases (`docs/UML.puml` y `docs/UML/`) |
| IntelliJ IDEA | IDE de desarrollo (su configuración no se versiona) |

## Patrones de diseño

El proyecto está construido alrededor de 11 patrones GoF combinados:

- **Creacionales**: Factory Method, Abstract Factory, Singleton.
- **Estructurales**: Decorator, Composite, Adapter, Facade.
- **De comportamiento**: Template Method, State, Observer, Strategy.

Cada patrón está documentado con su propósito, los roles GoF y un ejemplo de código en la memoria del proyecto.

## Estructura del proyecto

```
src/cafeteria/
├── Main.java
├── system/        # Fachada (CafeteriaManager), catálogo y terminal (Singleton)
├── kitchen/       # Cocina (Singleton)
├── products/      # Productos, decoradores (toppings) y composite (platos combinados)
├── factories/     # Factorías de productos y de toppings
├── orders/        # Pedido, estados y observadores
└── payments/      # Métodos de pago y adaptador del TPV externo
docs/
├── memoria.pdf    # Memoria del proyecto
├── UML.puml       # Diagrama UML completo
└── UML/           # Diagramas UML por patrón
```

## Compilación y ejecución

Requiere JDK 17 o superior.

```bash
# Compilar
javac -d out $(find src -name "*.java")

# Ejecutar
java -cp out cafeteria.Main
```

## Documentación

La memoria completa del proyecto (contexto, decisiones de diseño y ejemplos de código por patrón) está en [`docs/memoria.pdf`](docs/memoria.pdf).
