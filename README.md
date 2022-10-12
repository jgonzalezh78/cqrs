Proyecto demostrativo del uso de CQRS
=====================================

## Alcance demostrativo
El ejercicio consta de dos proyectos inicialmente uno muy básico de uso de axon, introduciendo al uso de comandos
El segundo ejercicio es mas complejo, indicando el uso de comando, se complementa con la integración de microservicios

## Proyecto Ejemplo 1

Ejecutar el comando

```
clean compile package install
```


Una vez compilado correctamente es tiempo de ejecutarlo, para ello descarga  [Axon Server](https://developer.axoniq.io/download#), descomprime para que puedas ejecutar el archivo *.jar en una terminal

```
java -jar axonserver.jar
```


Correr el proyecto example_1 con el siguiente comando 

```
spring-boot:run
```

El proyecto es levantado por el puerto 9080, ahora podemos enviarle peticiones a la URL localhost:9080/products con el json deejemplo acontinuación

{
    "title":"iPhone 12",
    "price":3000,
    "quantity":1
}

Podemos consultar los comandos que han llegado a Axon en la siguiente URL http://localhost:8024/#query

Al consultar la consola de H2 puedes identificar los productos dados de alta en el sistema en la URL http://localhost:9080/h2-console con la siguiente consulta

```
SELECT * FROM PRODUCTS 
```

## Proyecto Ejemplo 2

Consta de varios proyectos (microsservicios con spring cloud) los cuales se tienen que ejecutar en el siguiente orden

* Discovery server 

```
clean compile package install
spring-boot:run
```

Al terminar de levantar consultar la siguiente URL donde se registran los servicios disponibles http://localhost:8761/

* Gateway server

```
clean compile package install
spring-boot:run
```

Al terminar de levantar consultar la siguiente URL donde se registran los servicios disponibles http://localhost:8761/, se identificara que se registro con Discovery Server

* Servicio de negocio OrderService

```
clean compile package install
spring-boot:run
```

Al terminar de levantar consultar la siguiente URL donde se registran los servicios disponibles http://localhost:8761/, se identificara que se registro con Discovery Server


* Servicio de negocio PaymentsService

```
clean compile package install
spring-boot:run
```

Al terminar de levantar consultar la siguiente URL donde se registran los servicios disponibles http://localhost:8761/, se identificara que se registro con Discovery Server


* Servicio de negocio ProductsService

```
clean compile package install
spring-boot:run
```

Al terminar de levantar consultar la siguiente URL donde se registran los servicios disponibles http://localhost:8761/, se identificara que se registro con Discovery Server

* Servicio de negocio UsersService

```
clean compile package install
spring-boot:run
```



