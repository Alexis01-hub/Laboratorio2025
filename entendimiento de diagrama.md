## Entidades Principales
### Línea:

Representa una ruta de autobús (ej: "Línea 12")

Tiene un código identificador

Contiene múltiples paradas en su recorrido

### Parada:

Punto físico donde el colectivo se detiene

Puede tener varios pasajeros esperando

Tiene una dirección/orientación (ej: "Norte", "Centro")

### Colectivo (autobús):

Existe uno por línea (cada línea tiene su propio colectivo)

Transporta múltiples pasajeros

Pertenece a una línea específica

### Pasajero:

Persona que usa el servicio

Tiene un destino (una parada específica a la que quiere llegar)

## Relaciones Claras
### Línea - Parada:

Una línea tiene muchas paradas (1 a muchos)

Ej: Línea 12 tiene paradas en Av. Principal, Calle Secundaria, etc.

### Línea - Colectivo:

Cada línea tiene un colectivo asignado (1 a 1)

### Colectivo - Pasajero:

Un colectivo transporta muchos pasajeros (1 a muchos)

### Parada - Pasajero:

En una parada pueden esperar muchos pasajeros (1 a muchos)

Los pasajeros llegan a la parada para tomar el colectivo

### Pasajero - Destino:

Cada pasajero tiene una parada destino específica

Ej: Un pasajero en Av. Principal quiere ir a Calle Secundaria

## Flujo del Sistema
1. Los pasajeros llegan a una parada esperando un colectivo de cierta línea

2. El colectivo de esa línea recoge pasajeros en cada parada

3. Los pasajeros bajan cuando el colectivo llega a su parada destino

## Atributos Clave (Datos esenciales de cada clase)
**Línea:**
- Código/identificador (String o int)
- Nombre descriptivo (opcional)
- Lista de paradas en orden de recorrido

**Parada:**
- ID único
- Nombre/dirección física
- Sentido/dirección (norte/sur, este/oeste)
- Lista de pasajeros esperando

**Colectivo:**
- ID o patente
- Capacidad máxima de pasajeros
- Parada actual (dónde está ubicado)
- Lista de pasajeros abordo
- Estado (en movimiento, detenido, etc.)

**Pasajero:**

- ID único
- Parada destino
- Estado (esperando, abordando, viajando)

## Comportamientos/Métodos Clave
**Línea:**

- Agregar/eliminar paradas
- Calcular tiempo entre paradas

**Colectivo:**

- Abordar pasajeros (de una parada)
- Descargar pasajeros (en su parada destino)
- Moverse a siguiente parada

**Parada:**

- Agregar/remover pasajeros esperando
- Notificar llegada de colectivo

**Pasajero:**

- Subir a colectivo
- Bajar de colectivo