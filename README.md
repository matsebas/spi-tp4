# GlucoForecast

GlucoForecast es un sistema de gestión y monitoreo para pacientes con diabetes. Permite registrar datos de glucemia,
carbohidratos y dosis de insulina, además de generar reportes y calcular estimaciones de HbA1c. El proyecto se
desarrolla utilizando los principios de la programación orientada a objetos (POO) en Java y utiliza JDBC para la
persistencia de datos en una base de datos MySQL.

## Características Principales

- Registro de datos de glucemia, carbohidratos y dosis de insulina.
- Cálculo y visualización de estimaciones de HbA1c.
- Generación de reportes personalizados para los pacientes.
- Manejo de excepciones personalizadas.
- Menú de selección interactivo en la línea de comandos.
- Persistencia de datos en una base de datos MySQL utilizando JDBC.
- Modelo de datos basado en herencia para representar usuarios (pacientes, médicos, cuidadores).

## Requisitos

- Java Development Kit (JDK) 17 o superior.
- Servidor de base de datos MySQL.
- Driver JDBC de MySQL (incluido en la carpeta lib).

## Instalación

1. Clona el repositorio desde GitHub:
   ```bash
   git clone https://github.com/tu_usuario/glucoforecast.git
   ```
2. Navega al directorio del proyecto:
   ```bash
   cd glucoforecast
   ```
3. Configura la conexión a la base de datos:

- Abre el archivo `jdbc/ConexionBD.java`.
- Modifica las variables `url`, `usuario` y `contraseña` con los datos de tu servidor MySQL.

4. Crea la base de datos y las tablas:

- Ejecuta el script SQL proporcionado (glucoforecast.sql) en tu servidor MySQL para crear la base de datos y las tablas
  necesarias.

## Estructura del Proyecto

El proyecto está organizado en los siguientes paquetes:

- `src`: Contiene la clase principal `GlucoForecastApp` que inicia la aplicación y maneja el menú principal.
  - `controllers`: Contiene los controladores para la gestión de mediciones, cálculo de HbA1c y generación de reportes.
  - `exceptions`: Contiene la clase de excepciones personalizadas `GlucoForecastException`.
  - `jdbc`: Clase `ConexionBD` para gestionar la conexión a la base de datos e interface `Persistencia` que define los
    métodos de persistencia comunes a los modelos.
  - `models`: Contiene las clases de modelos de datos como `Paciente`, `Medicion`, `EstimacionGlicosilada` y diferentes
    tipos de reportes, que implementan la interfaz Persistencia.
  - `views`: Contiene las clases de vistas para la interacción con el usuario.
  - `utils`: Contiene utilidades como constantes para cambiar los colores del texto en la terminal.
- `lib`: Carpeta que contiene el driver JDBC de MySQL (`mysql-connector-j-8.4.0.jar`).
- `tests`: Contiene las clases de prueba para verificar el funcionamiento del sistema.
## Uso

Para ejecutar la aplicación, compila y ejecuta la clase `GlucoForecastApp`.

### Ejecución del Menú Principal

1. **Listado de Pacientes:**
    - Selecciona un paciente de la lista.
    - Muestra el menú principal para el paciente seleccionado.

2. **Opciones del Menú Principal:**
    - **Registrar datos:** Permite registrar datos de glucemia, carbohidratos y dosis de insulina.
    - **Visualizar resultado de HbA1c:** Muestra la última estimación de HbA1c calculada para el paciente.
    - **Generar Reportes:** Permite generar reportes personalizados (Rangos de Glucemia, HbA1c Histórico, Promedios de
      Glucemia).
    - **Volver:** Regresa al menú de selección de pacientes.

## Contribuir

Si deseas contribuir al proyecto, por favor sigue estos pasos:

1. Haz un fork del repositorio.
2. Crea una nueva rama (`git checkout -b feature/nueva-feature`).
3. Realiza los cambios necesarios y commitea (`git commit -am 'Agrega nueva feature'`).
4. Empuja tus cambios a la rama (`git push origin feature/nueva-feature`).
5. Crea un nuevo Pull Request.

## Licencia

Este proyecto está licenciado bajo la Licencia MIT. Consulta el archivo `LICENSE` para obtener más información.

---

Este README proporciona una visión general del proyecto GlucoForecast, destacando su estructura, características
principales y pasos para su instalación y uso.