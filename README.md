# Proyecto Colectivo
### Descripción General
Colectivo es una aplicación de simulación de un sistema de transporte público basado en colectivos (autobuses), desarrollada en Java. El objetivo principal es modelar el funcionamiento de líneas de colectivos, sus paradas y el flujo de pasajeros, permitiendo observar cómo se distribuyen y mueven los pasajeros a lo largo de los recorridos de los colectivos.

La simulación permite cargar la configuración y los datos desde archivos externos, generar pasajeros aleatorios, crear colectivos para cada línea y simular el recorrido de los colectivos mostrando en consola las subidas y bajadas de pasajeros en cada parada.

---

### Estructura del Proyecto
El proyecto está organizado en varios paquetes, cada uno con responsabilidades claras:

**1. colectivo.modelo**
	Contiene  las clases que representan los elementos principales del sistema:

- Colectivo: Representa un colectivo que pertenece a una línea específica y transporta pasajeros. Mantiene una referencia a la línea y una lista de los pasajeros a bordo.
- Linea: Representa una línea de colectivo, identificada por un código y una lista ordenada de paradas que recorre.
- Parada: Representa una parada del colectivo, identificada por un id único y una dirección.
- Pasajero: Representa un pasajero, con un identificador, una parada de origen y una de destino.

**2. colectivo.datos**
    Incluye clases utilitarias para la carga de datos desde archivos externos:

- Dato: Permite cargar las líneas y las paradas desde archivos de texto. Se encarga de interpretar el formato de los archivos y construir los objetos correspondientes.
- CargarParametros: Permite cargar los parámetros de configuración desde un archivo config.properties, como la cantidad de pasajeros y las rutas de los archivos de datos.

**3. colectivo.utils**
Contiene utilidades para la simulación:

- GeneradorPasajeros: Genera una lista de pasajeros aleatorios, asegurando que cada pasajero tenga una parada de origen y una de destino válidas y distintas.

**4. colectivo.aplicacion**
Contiene la lógica principal de la simulación:

- Simulador: Clase principal que ejecuta la simulación. Se encarga de leer la configuración, cargar los datos, generar los pasajeros, crear los colectivos y simular el recorrido de cada colectivo mostrando el movimiento de los pasajeros.

---

### Funcionamiento Detallado
**1. Configuración**
La simulación utiliza un archivo ```config.properties``` para definir los parámetros principales:

- cantidadPasajeros: Número total de pasajeros a generar para la simulación.
- parada: Ruta del archivo de paradas.
- linea: Ruta del archivo de líneas.

Esto permite modificar fácilmente la cantidad de pasajeros y los datos de entrada sin cambiar el código fuente.

**2. Carga de Datos**
- Paradas: Se cargan desde un archivo de texto, donde cada línea representa una parada con su id y dirección.
- Líneas: Se cargan desde otro archivo de texto, donde cada línea representa una línea de colectivo, identificada por un código y una secuencia de ids de paradas que recorre.

La relación entre líneas y paradas se establece al cargar las líneas, asociando cada id de parada a un objeto Parada previamente cargado.

**3. Generación de Pasajeros**
El sistema genera pasajeros aleatorios, asignando a cada uno una parada de origen y una de destino distintas, seleccionadas de entre las paradas disponibles. Esto simula el comportamiento real de los usuarios del transporte público.

**4. Creación de Colectivos**
Por cada línea cargada, se crea un objeto ``Colectivo`` que representa el colectivo que recorre esa línea. Cada colectivo mantiene su propia lista de pasajeros a bordo.

**5. Asignación de Pasajeros a Colectivos**
Cada pasajero es asignado a un colectivo cuya línea incluya tanto su parada de origen como su parada de destino. Esto asegura que los pasajeros solo suban a colectivos que realmente pueden llevarlos a su destino.

**6. Simulación del Recorrido**
La simulación recorre cada colectivo por todas las paradas de su línea. En cada parada, se realiza lo siguiente:

- Se determina qué pasajeros suben (aquellos cuyo origen es la parada actual).
- Se determina qué pasajeros bajan (aquellos cuyo destino es la parada actual).
- Se actualiza la lista de pasajeros a bordo.
- Se imprime en consola el estado de la parada: cuántos suben, cuántos bajan y cuántos quedan a bordo.

Al finalizar el recorrido de cada colectivo, si quedan pasajeros a bordo (por ejemplo, si su destino no estaba en la línea), se informa y se los baja forzosamente.

---

### Archivos de Entrada
``config.properties``
Archivo de configuración con las siguientes claves:
```
#Colocar en este archivo todos los parametos del sistema

#nombre de archivos
linea=linea.txt
parada=parada.txt

#cantidad de pasajeros
cantidadPasajeros=100
```

``parada.txt``
Archivo de paradas, cada línea con el formato:
``idParada;direccion;``
Ejemplo:
```
#id de la parada;direccion
1;1 De Marzo, 405;
2;1 De Marzo, 499;
3;25 De Mayo, 299;
```

``linea.txt``
Archivo de líneas, cada línea con el formato:
codigoLinea;idParada1;idParada2;...;idParadaN;

Ejemplo:
```
#Codigo de la linea;paradas
L1I;88;97;44;43;47;58;37;74;77;25;24;5;52;14;61;35;34;89;
L1R;89;84;83;90;16;17;53;26;3;4;36;73;96;95;46;102;42;62;45;100;
L2I;48;13;79;7;84;83;90;16;17;55;53;26;3;4;19;21;9;30;31;
L2R;31;8;33;20;25;24;5;54;2;51;15;81;82;80;48;
L3I;92;71;11;1;6;75;76;46;39;49;94;91;
L3R;91;85;98;93;50;39;5;2;10;71;92;
L4I;1;6;75;76;29;27;87;86;103;70;60;
L4R;60;70;88;63;65;64;77;25;5;1;
L5I;48;69;59;55;53;26;3;4;46;102;42;62;68;
L5R;67;91;104;72;85;57;56;98;41;44;43;47;99;24;5;54;28;101;18;78;13;
L6I;1;6;75;76;38;40;66;
L6R;66;32;39;12;22;33;20;23;25;24;5;1;
```

---

> .[!IMPORTANT]
> Creado por Alexis Mamani 
> @Alexis01-hub



