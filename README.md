# Práctica Simulador Físico de la asignatura de Tecnología de la Programación II

## Aquí los cambios que se van realizando a lo largo del desarrollo de dicha práctica

**01/03/2021** Terminado el PhysicsSimulator. Terminada la clase Body.java y MassLosingBody.java que hereda de esta. 

**28/02/2021** Creadas y terminadas las clases que implementan el StateComparator: MassEqualStates y EpsilonEqualStates. Creada la clase Controller, implementado el constructor y el método loadBodies.

**27/02/2021.** Terminados los parse de los comandos anteriores. Finalizado método init del Main.java. Comienzo del método startBatchMode en Main.java. Creados todos los builders y la clase PhysicsSimulator.

 **Dudas para Puri:** - ¿Es necesario poner throws en las cabeceras de los métodos que hagan throw de la excepción IllegalArgumentException? - Las comprobaciones de la constructora de PhysicsSimulator se hacen en la propia constructora o se hacen en el parse de los comandos? - ¿Cómo pasamos por parámetros a PhysicsSimulator un ForceLaws cuando lo que tenemos en el Main.java es un JSONObject?

**22/02/2021** Añadir los comandos -o, -eo y -s en Main.java
