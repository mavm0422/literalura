Literalura App
Descripción
Esta aplicación es un proyecto de estudio con Alura que interactúa con una API externa para gestionar información sobre libros y autores. Permite realizar búsquedas, listar datos registrados y filtrar información basada en autores y libros. Además, está integrada con una base de datos PostgreSQL (pgAdmin 4) llamada literature para almacenar los datos.

Menú Principal
El menú ofrece las siguientes opciones:

Buscar libro por título
Realiza una búsqueda de libros en la API GutenDex utilizando un término de búsqueda.

Estructura de búsqueda:


https://gutendex.com/books/?search=<título_del_libro>
Si encuentra un libro, lo guarda en la base de datos y muestra el siguiente mensaje:

Si encuentra un libro, lo guarda en la base de datos y muestra el siguiente mensaje:
******* LIBRO GUARDADO ***********
Título: <Título del libro>
Autor: <Autor del libro>
Idioma: <Idioma del libro>
Número de descargas: <Número de descargas>
***********************************
Listar libros registrados
Muestra todos los libros almacenados en la base de datos.

Listar autores registrados
Muestra una lista de todos los autores registrados en la base de datos.

Listar autores vivos en un determinado año
Permite filtrar los autores que estaban vivos en un año específico.

Listar libros por idioma
Filtra y muestra los libros almacenados en la base de datos por idioma.

Estructura de Clases
1. Clases Principales
Principal
Punto de entrada de la aplicación. Controla el menú y la interacción con el usuario.

Author
Representa un autor en el sistema. Incluye atributos como nombre, fecha de nacimiento y fecha de fallecimiento.

Book
Representa un libro registrado en el sistema, con información como título, autor, idioma y número de descargas.

2. Clases para Manejo de Datos
DatosAutor (record)
Contiene los datos básicos de un autor provenientes de la API.

DatosBook (record)
Contiene la información básica de un libro para manipulación interna.

DatosBookResponse (record)
Modela la respuesta de la API para la búsqueda de libros.

3. Repositorios
AuthorRepository
Interfaz para operaciones CRUD con la tabla de autores en la base de datos.

BookRepository
Interfaz para operaciones CRUD con la tabla de libros en la base de datos.

4. Manejo de API
ConsumoAPI
Clase para realizar solicitudes a la API GutenDex y obtener datos sobre libros.

ConvierteDatos
Clase que implementa la interfaz IconvierteDatos para convertir datos obtenidos de la API en objetos que puedan ser utilizados o almacenados.

IconvierteDatos (Interface)
Define los métodos necesarios para convertir datos de la API en objetos del sistema.

Base de Datos
La aplicación utiliza una base de datos PostgreSQL llamada literalura.
Tablas principales:

authors: Almacena los datos de los autores registrados.
books: Almacena los datos de los libros registrados.

Requisitos:
Java (JDK 17 o superior).
Spring Boot (versión 3.3 o superior).
PostgreSQL y pgAdmin 4.
Dependencias de Maven:
Spring Data JPA
Spring Web
PostgreSQL Driver
Gson (para manejar datos JSON)
Cómo Ejecutar
Configurar la base de datos:

Crear una base llamada literature en PostgreSQL.
Asegurarse de que las credenciales de conexión están configuradas correctamente en el archivo application.properties o application.yml.

Ejecutar la aplicación:
mvn spring-boot:run

Interacción con el menú: Siga las instrucciones del menú para buscar libros, listar datos o realizar filtros.
