# Sistema de Simulación de Colectivos

## Descripción General

Este proyecto implementa un **simulador de sistema de transporte público de colectivos** en Java. El programa simula el recorrido de colectivos por diferentes líneas, gestionando el embarque y desembarque de pasajeros en cada parada del recorrido.

El sistema lee la configuración desde archivos de texto, genera pasajeros aleatorios, asigna colectivos a líneas específicas y permite visualizar el recorrido completo parada por parada de forma interactiva.

## Características Principales

- **Gestión de líneas y paradas**: Carga dinámica desde archivos de configuración
- **Generación automática de pasajeros**: Creación aleatoria de pasajeros con orígenes y destinos válidos
- **Simulación interactiva**: Visualización paso a paso del recorrido del colectivo
- **Asignación inteligente**: Los pasajeros solo se asignan a colectivos cuyas líneas contengan tanto su origen como su destino
- **Estructuras de datos eficientes**: Utiliza `ChainHashMap` para operaciones frecuentes de inserción y eliminación

## Estructura del Proyecto

```
Colectivo/
├── config.properties          # Archivo de configuración del sistema
├── linea.txt                  # Definición de líneas y sus paradas
├── parada.txt                 # Catálogo de paradas disponibles
└── colectivo/
    ├── aplicacion/
    │   └── Simulador.java     # Clase principal de ejecución
    ├── datos/
    │   ├── CargadorDatos.java
    │   └── CargarParametros.java
    ├── modelo/
    │   ├── Colectivo.java
    │   ├── Linea.java
    │   ├── Parada.java
    │   └── Pasajero.java
    └── utils/
        ├── GeneradorPasajeros.java
        └── ImprimirRecorrido.java
```

---

## Documentación de Clases y Métodos

### Paquete: `colectivo.aplicacion`

#### **Clase: `Simulador`**

Clase principal que ejecuta la simulación completa del sistema de colectivos.

**Método:**
- **`public static void main(String[] args)`**
  - **Descripción**: Punto de entrada del programa. Coordina todo el flujo de la simulación.
  - **Flujo de ejecución**:
    1. Carga la configuración desde `config.properties`
    2. Lee los archivos de paradas y líneas
    3. Genera pasajeros aleatorios según la cantidad configurada
    4. Crea un colectivo por cada línea disponible
    5. Asigna pasajeros a colectivos (solo si la línea incluye origen y destino)
    6. Ejecuta la simulación interactiva del recorrido
  - **Excepciones**: `throws Exception` - Maneja errores de lectura de archivos

---

### Paquete: `colectivo.modelo`

#### **Clase: `Colectivo`**

Representa un vehículo de transporte público que pertenece a una línea específica y transporta pasajeros.

**Atributos:**
- `private Linea linea` - Línea a la que pertenece el colectivo
- `private List<Pasajero> pasajeros` - Lista de pasajeros actualmente en el colectivo

**Métodos:**
- **`public Colectivo(Linea linea)`**
  - Constructor que crea un colectivo asociado a una línea
  - **Parámetros**: `linea` - La línea a la que pertenece

- **`public Linea getLinea()`**
  - Obtiene la línea del colectivo
  - **Retorna**: Objeto `Linea`

- **`public List<Pasajero> getPasajeros()`**
  - Obtiene la lista de pasajeros a bordo
  - **Retorna**: Lista de objetos `Pasajero`

- **`public void subirPasajero(Pasajero p)`**
  - Agrega un pasajero al colectivo
  - **Parámetros**: `p` - Pasajero a subir

- **`public void bajarPasajero(Pasajero p)`**
  - Remueve un pasajero del colectivo
  - **Parámetros**: `p` - Pasajero a bajar

---

#### **Clase: `Linea`**

Representa una línea de colectivo con un código identificador y una secuencia ordenada de paradas.

**Atributos:**
- `private String codigo` - Código identificador de la línea (ej: "L1I", "L2R")
- `List<Parada> paradas` - Lista ordenada de paradas del recorrido

**Métodos:**
- **`public Linea(String codigo)`**
  - Constructor que crea una línea con su código
  - **Parámetros**: `codigo` - Identificador de la línea

- **`public void agregarParada(Parada parada)`**
  - Agrega una parada al final del recorrido
  - **Parámetros**: `parada` - Parada a agregar

- **`public String getCodigo()`**
  - Obtiene el código de la línea
  - **Retorna**: String con el código

- **`public List<Parada> getParadas()`**
  - Obtiene todas las paradas de la línea en orden
  - **Retorna**: Lista de objetos `Parada`

- **`public String toString()`**
  - Representación textual de la línea
  - **Retorna**: String con código y paradas

---

#### **Clase: `Parada`**

Representa una parada física del sistema de colectivos.

**Atributos:**
- `private int id` - Identificador único de la parada
- `private String direccion` - Ubicación física de la parada

**Métodos:**
- **`public Parada(int id, String direccion)`**
  - Constructor que crea una parada
  - **Parámetros**: 
    - `id` - Identificador único
    - `direccion` - Dirección física

- **`public int getId()`**
  - Obtiene el ID de la parada
  - **Retorna**: int con el identificador

- **`public void setId(int id)`**
  - Establece el ID de la parada
  - **Parámetros**: `id` - Nuevo identificador

- **`public String getDireccion()`**
  - Obtiene la dirección de la parada
  - **Retorna**: String con la dirección

- **`public void setDireccion(String direccion)`**
  - Establece la dirección de la parada
  - **Parámetros**: `direccion` - Nueva dirección

- **`public String toString()`**
  - Representación textual de la parada
  - **Retorna**: String con ID y dirección

---

#### **Clase: `Pasajero`**

Representa un pasajero del sistema con origen y destino definidos.

**Atributos:**
- `private int id` - Identificador único del pasajero
- `private Parada origen` - Parada donde sube el pasajero
- `private Parada destino` - Parada donde baja el pasajero

**Métodos:**
- **`public Pasajero(int id, Parada origen, Parada destino)`**
  - Constructor que crea un pasajero
  - **Parámetros**:
    - `id` - Identificador único
    - `origen` - Parada de inicio del viaje
    - `destino` - Parada de fin del viaje

- **`public int getId()`**
  - Obtiene el ID del pasajero
  - **Retorna**: int con el identificador

- **`public Parada getOrigen()`**
  - Obtiene la parada de origen
  - **Retorna**: Objeto `Parada` de origen

- **`public Parada getDestino()`**
  - Obtiene la parada de destino
  - **Retorna**: Objeto `Parada` de destino

- **`public void setDestino(Parada destino)`**
  - Establece una nueva parada de destino
  - **Parámetros**: `destino` - Nueva parada de destino

- **`public String toString()`**
  - Representación textual del pasajero
  - **Retorna**: String con ID, origen y destino

---

### Paquete: `colectivo.datos`

#### **Clase: `CargadorDatos`**

Clase utilitaria para cargar datos desde archivos de texto externos.

**Métodos:**
- **`public static Map<String, Linea> cargarLineas(String fileName, Map<Integer, Parada> paradas)`**
  - Carga las líneas de colectivos desde archivo
  - **Parámetros**:
    - `fileName` - Ruta del archivo de líneas
    - `paradas` - Mapa de paradas previamente cargadas
  - **Retorna**: Mapa con código de línea como clave y objeto `Linea` como valor
  - **Formato esperado**: `CodigoLinea;idParada1;idParada2;...;`
  - **Excepciones**: `FileNotFoundException` si el archivo no existe
  - **Características**:
    - Ignora líneas vacías y comentarios (líneas que empiezan con `#`)
    - Usa `;` como delimitador
    - Asocia cada parada ID con su objeto correspondiente

- **`public static Map<Integer, Parada> cargarParadas(String fileName)`**
  - Carga las paradas desde archivo
  - **Parámetros**: `fileName` - Ruta del archivo de paradas
  - **Retorna**: Mapa con ID de parada como clave y objeto `Parada` como valor
  - **Formato esperado**: `idParada;direccion;`
  - **Excepciones**: `FileNotFoundException` si el archivo no existe
  - **Características**:
    - Ignora líneas vacías y comentarios
    - Valida que el ID sea un número entero válido
    - Maneja casos donde no hay dirección especificada

---

#### **Clase: `CargarParametros`**

Gestiona la configuración del sistema desde el archivo `config.properties`.

**Atributos estáticos:**
- `private static String archivoLineas` - Ruta del archivo de líneas
- `private static String archivoParadas` - Ruta del archivo de paradas
- `private static int cantidadPasajeros` - Cantidad de pasajeros a generar

**Métodos:**
- **`public static Properties cargar()`**
  - Carga y valida la configuración desde `config.properties`
  - **Retorna**: Objeto `Properties` con la configuración
  - **Excepciones**: `IOException` si hay error al leer o falta algún parámetro
  - **Validaciones**:
    - Verifica que todas las propiedades requeridas existan
    - Valida que `cantidadPasajeros` sea un número entero válido
    - Lanza excepción detallada en caso de error

- **`public static String getArchivoLineas()`**
  - Obtiene la ruta del archivo de líneas
  - **Retorna**: String con la ruta

- **`public static String getArchivoParadas()`**
  - Obtiene la ruta del archivo de paradas
  - **Retorna**: String con la ruta

- **`public static int getCantidadPasajeros()`**
  - Obtiene la cantidad configurada de pasajeros
  - **Retorna**: int con la cantidad

---

### Paquete: `colectivo.utils`

#### **Clase: `GeneradorPasajeros`**

Utilitario para generar pasajeros con origen y destino aleatorios.

**Métodos:**
- **`public static List<Pasajero> generar(int cantidad, Map<Integer, Parada> paradas)`**
  - Genera una lista de pasajeros aleatorios
  - **Parámetros**:
    - `cantidad` - Número de pasajeros a generar
    - `paradas` - Mapa de paradas disponibles
  - **Retorna**: Lista de objetos `Pasajero` generados
  - **Lógica**:
    - Convierte el mapa de paradas en una lista para acceso aleatorio
    - Genera cada pasajero con origen y destino aleatorios
    - **Garantiza que origen ≠ destino** para cada pasajero
    - Asigna IDs secuenciales (0, 1, 2, ...)

---

#### **Clase: `ImprimirRecorrido`**

Gestiona la visualización interactiva del recorrido de los colectivos.

**Métodos:**
- **`public static void imprimirRecorrido(List<Colectivo> colectivos, Map<String, Linea> lineas)`**
  - Ejecuta la interfaz interactiva para ver recorridos
  - **Parámetros**:
    - `colectivos` - Lista de colectivos disponibles
    - `lineas` - Mapa de líneas del sistema
  - **Funcionalidad**:
    1. Muestra lista de líneas disponibles
    2. Solicita al usuario seleccionar una línea (ignora mayúsculas/minúsculas)
    3. Busca el colectivo correspondiente a la línea
    4. **Simula el recorrido parada por parada**:
       - Muestra número de parada y dirección
       - Calcula cuántos pasajeros suben (origen = parada actual)
       - Calcula cuántos pasajeros bajan (destino = parada actual)
       - Muestra total de pasajeros a bordo
       - **Pausa y espera ENTER** para avanzar a la siguiente parada
    5. Alerta si quedan pasajeros al final del recorrido
    6. Permite ver otra línea o finalizar
  - **Características especiales**:
    - Interfaz paso a paso (requiere ENTER para continuar)
    - Validación de entrada del usuario
    - Formato visual limpio con separadores
    - Opción de repetir simulación con otra línea

---

## Formato de Archivos de Configuración

### **config.properties**
```properties
# Rutas de archivos de datos
linea=Colectivo/linea.txt
parada=Colectivo/parada.txt

# Parámetros de simulación
cantidadPasajeros=100
```

### **parada.txt**
Formato: `idParada;direccion;`

Ejemplo:
```
1;1 De Marzo, 405;
2;1 De Marzo, 499;
3;25 De Mayo, 299;
```

### **linea.txt**
Formato: `CodigoLinea;idParada1;idParada2;idParada3;...;`

Ejemplo:
```
L1I;88;97;44;43;47;58;37;74;77;25;
L1R;89;84;83;90;16;17;53;26;3;4;
L2I;48;13;79;7;84;83;90;16;17;55;
```

---

## Flujo de Ejecución

1. **Inicialización**
   - Carga `config.properties`
   - Lee archivos de paradas y líneas
   - Valida datos

2. **Generación**
   - Crea pasajeros aleatorios con la cantidad especificada
   - Crea un colectivo por cada línea

3. **Asignación**
   - Para cada pasajero, verifica si su origen y destino están en alguna línea
   - Sube el pasajero al colectivo correspondiente

4. **Simulación Interactiva**
   - Usuario selecciona una línea
   - Sistema muestra recorrido parada por parada
   - Informa subidas, bajadas y ocupación
   - Permite repetir con otra línea

---

## Estructura de Datos Utilizada

- **`ChainHashMap`**: Implementación de tabla hash con encadenamiento
  - Usada para paradas y líneas
  - Optimizada para operaciones frecuentes de inserción/búsqueda
  - Complejidad promedio O(1) para `get` y `put`

- **`ArrayList`**: Lista dinámica
  - Usada para pasajeros y paradas de una línea
  - Permite acceso aleatorio eficiente
  - Ideal para iteración secuencial

---

## Requisitos del Sistema

- **Java**: JDK 21 (version con la cual fue probada y creada)
- **Dependencias**: Incluye estructuras de datos personalizadas del paquete `net.datastructures`
- **Sistema operativo**: Multiplataforma (Windows, Linux, macOS)

---

## Ejemplo de Salida

```
Líneas disponibles:
  Código: L1I
  Código: L1R
  Código: L2I

Ingrese el código de la línea que desea ver: L1I

============================================================
Recorrido colectivo línea: L1I (paradas: 18)
Presione ENTER para avanzar a la siguiente parada...
============================================================

------------------------------------------------------------
  Parada 1/18: Bouchard, 880
  Suben: 3
  Bajan: 0
  Pasajeros a bordo: 3
------------------------------------------------------------

Presione ENTER para continuar a la siguiente parada...

------------------------------------------------------------
  Parada 2/18: Bouchard, 1285
  Suben: 2
  Bajan: 1
  Pasajeros a bordo: 4
------------------------------------------------------------
...
```

---

## Autor

Proyecto desarrollado como trabajo final para la materia Algoritmos y Programación 2.

---

## Licencia

Este proyecto es de uso académico. No robar causa, no seas malo :v
