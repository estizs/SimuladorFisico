# Práctica Simulador Físico de la asignatura de Tecnología de la Programación II

## Aquí los cambios que se van realizando a lo largo del desarrollo de dicha práctica

**22/03/2021** Terminadas las clases Main, Controller y PhysicsSimulator. Aniversario de un mes con la mejor práctica que nos han dado en la carrera aunque Elena diga que no. Yo sigo pensando que no estoy en el grado de videojuegos.

**Dudas para Puri** - ¿Qué comprobaciones hay que hacer a la hora de leer JSONs? - ¿Tenemos que comprobar si los tipos están bien o solo si están las keys?

**17/03/2021** Terminados todos los builders.

**16/03/2021** Modificadas y terminadas las clases Body y sus hijas, las ForceLaws y sus hijas y los StateComparators, tanto MassEqualStates como EpsilonEqualStates.

**08/03/2021** Terminado la clase Controller y las clases NewtonUniversalGravitation, MovingTowardsFixedPoint y NoForce. Hemos empezado con las factorías y los builders pero teníamos dudas y le hemos preguntado a Puri <3. Además mañana es mi cumple.

**01/03/2021** Terminado el PhysicsSimulator. Terminada la clase Body.java y MassLosingBody.java que hereda de esta. 

**28/02/2021** Creadas y terminadas las clases que implementan el StateComparator: MassEqualStates y EpsilonEqualStates. Creada la clase Controller, implementado el constructor y el método loadBodies.

**27/02/2021.** Terminados los parse de los comandos anteriores. Finalizado método init del Main.java. Comienzo del método startBatchMode en Main.java. Creados todos los builders y la clase PhysicsSimulator.

 **Dudas para Puri:** - ¿Es necesario poner throws en las cabeceras de los métodos que hagan throw de la excepción IllegalArgumentException? - Las comprobaciones de la constructora de PhysicsSimulator se hacen en la propia constructora o se hacen en el parse de los comandos? - ¿Cómo pasamos por parámetros a PhysicsSimulator un ForceLaws cuando lo que tenemos en el Main.java es un JSONObject?

**22/02/2021** Añadir los comandos -o, -eo y -s en Main.java
