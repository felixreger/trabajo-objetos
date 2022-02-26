# Trabajo final de Objetos

## Conexion a PostgreSQL

Instalar postgreSQL : https://www.postgresql.org/download/linux/ubuntu/

Iniciar servicio con :     pg_ctlcluster 12 main start

Crear base de datos :

## Conexion a Tomcat

Agregar en la carpeta lib el .jar del driver de PostgreSQL

## Link informe

https://docs.google.com/document/d/1xIP2hrVo1Q5P4MqWCj3Xuxe8Xq_JSxhQw1IAQF5m_zE/edit#

## Notas

> Ver si se puede heredar el contstructor privado de los DAO para GetInstances()

> Los criterios para filtrar listados deberian estar incluidos dentro del servicio?

## Decisiones

* Generalización y parametrización del dao
* Padre de elemento como atributo. En un principio el esquema de base de datos posee una columna en la tabla elemento que nos permite acceder al padre de un elemento, esto fue realizado así por la forma sql de resolver las cosas. En el modelo agregamos una lista de elementos hijo para una carpeta debido al uso del patrón composite, pero tuvimos el error de no tener en cuenta el padre, así que agregamos el atributo

## Cosas

* [X] Agregar Padre al modelo de elemento
* [ ] Admin para usuarios
* [ ] Ordenamiento ascendente y descendente
* [ ] Generalizar el getAll de catedraDao y archivo dao. Nos interesa solamente traer los elementos de la tabla elementos
* [ ] Generar naming para las carpetas y archivos al momento de insertarlo en la base de datos 

## Reunion

### To do

* [X] Hacer diagrama de clases
* [ ] Documentacion
    * [ ] Estado actual del proyecto
    * [ ] Decisiones tomadas 
    * [ ] Conplicaciones

### Perse


1. Como venimos?
1. Hay cambios necesarios (Agregados, correcciones)
1. Como manejamos el tema de permisos? Se nos ocurre que el servicio tenga como parametro el usuario que realiza la solicitud al mismo y se verifica que el usuario sea administrador. Esto como idea esta bien?
1. Debemos distinguir entre usuario y administrador a nivel clases? Consideramos que las operaciones deben estar disponibles eN servicios. El mismo servicio debe encargarse de verificar que el usuario tenga los permisos o no. El modelo es solamente una representacion de los datos en la base de datos
1. Como manejamos el tema de los filtros (Criterios)?
    * Filtros en servicio como parametro
    * En el front (main) invocandolo a partir del resultado de los servicios <** Usamos esta
    * En sql usando stored procedures "getElementosById"
1. ¿Como encarar de por si este problema? 
    
    Por ejemplo :
    
    * Generamos un objeto comentario que ya este nutrido con los datos y lo actualizamos / agregamos en la bdd
    * Generamos un servicio con dos parámetros, uno comentario y otro elemento.

    Basicamente la pregunta es si pasamos el objeto completo o dejamos que el servicio se encargue de construirlo a partir de los parametros


#### Preguntas en caso de sobrar tiempo

1. ¿Cómo calculamos la catedra de la carpeta?
No sabemos decidir qué camino tomar a la hora de retornar al usuario el catedra de un elemento. 
    * La catedra de una carpeta se puede calcular a partir de la mayoria de catedras de sus hijos. I.E
2. Que pasa cuando vos estas diseniando y te ves afectado con la base de datos? 
3. Como escalaria si una carpeta tiene un usuario que tiene una direccion que tiene una ciudad que riene un pais etc
