# Trabajo final de Objetos

## Conexion a PostgreSQL

Instalar postgreSQL : https://www.postgresql.org/download/linux/ubuntu/

Iniciar servicio con :     pg_ctlcluster 12 main start

Crear base de datos :

## Link informe

https://docs.google.com/document/d/1xIP2hrVo1Q5P4MqWCj3Xuxe8Xq_JSxhQw1IAQF5m_zE/edit#

## Notas

> Ver si se puede heredar el contstructor privado de los DAO para GetInstances()

> Los criterios para filtrar listados deberian estar incluidos dentro del servicio?

## Decisiones

* Generalización y parametrización del dao
* Padre de elemento como atributo. En un principio el esquema de base de datos posee una columna en la tabla elemento que nos permite acceder al padre de un elemento, esto fue realizado así por la forma sql de resolver las cosas. En el modelo agregamos una lista de elementos hijo para una carpeta debido al uso del patrón composite, pero tuvimos el error de no tener en cuenta el padre, así que agregamos el atributo

## Cosas

- [X] Agregar Padre al modelo de elemento
- [ ] Admin para usuarios
- [ ] Ordenamiento ascendente y descendente

## Preguntas
1. Como manejamos el tema de permisos? Se nos ocurre que el servicio tenga como parametro el usuario que realiza la solicitud al mismo y en primera insatncia antes de realizar la operacion se verifica que el usaurio sea administrador.2. Como manejamos el tema de los filtros (Criterios)? 
    * Filtros en servicio como parametro
    * En el front (main) invocandolo a partir del resultado de los servicios
    * En sql usando stored procedures "getElementosById"
3. ¿Cómo calculamos la catedra de la carpeta?
No sabemos decidir qué camino tomar a la hora de retornar al usuario el catedra de un elemento. 
    * La catedra de una carpeta se puede calcular a partir de la mayoria de catedras de sus hijos. I.E
4. ¿Cómo hacemos para generar un servicio que agrega un comentario? 
    * Generamos un objeto comentario que ya tengo un atributo con el id del elemento al que esta asociado (agregando este último porque antes no estaba)
    * Generamos un servicio con dos parámetros, uno comentario y otro elemento.
