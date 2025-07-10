📚 LiteraturaAlura
Proyecto desarrollado en Java con Spring Boot que consume la API de Gutenberg para buscar, almacenar y gestionar libros y autores.

🧩 Tecnología
Java 17+

Spring Boot 3.5.3

Spring Data JPA

H2 o PostgreSQL (configurable)

Cliente REST interno (ConsumoAPI)

GutenDex API para búsqueda de libros

🚀 Características principales
Buscar libros vía API pública de Gutenberg.

Guardar libros y autores en la base de datos evitando duplicados.

Obtener autores creados o ya existentes en la BD según búsqueda.

Manejo de errores como DataIntegrityViolationException, ObjectOptimisticLockingFailureException y NullPointerException.
