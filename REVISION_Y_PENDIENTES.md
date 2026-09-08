# GestiónCursos - revisión, cambios y pendientes

## 1. Base utilizada

Se tomó **GestionCursos(6)** como proyecto principal para conservar la lógica y el estilo de programación del equipo.
La versión **proyecto real de persistencia** se usó únicamente como referencia para identificar funcionalidades más adelantadas. No se copiaron automáticamente sus decisiones de diseño.

## 2. Cambios realizados

### Persistencia por registros fijos

- `Curso.TAMANO_REGISTRO = 64` bytes.
- `Docente.TAMANO_REGISTRO = 60` bytes.
- Todos los métodos de `CursoUtils` fueron alineados al registro actual de 64 bytes, incluyendo `codigoDocente` al final del registro.
- Todos los métodos de `DocenteUtils` usan el mismo formato de 60 bytes.
- Se conserva `RandomAccessFile` y la apertura/cierre explícita dentro de cada método.
- No se creó un método genérico para abrir archivos.

### Cálculo de bytes

Curso:

- `codigo`: 4
- `nombre`: `writeUTF` = 2 bytes de longitud + 31 bytes reservados
- `disponibilidad`: 1
- `creditos`: 4
- `costo`: 8
- `estado`: `writeUTF` = 2 + 8
- `codigoDocente`: 4
- Total: **64 bytes**

Docente:

- `codigoDocente`: 4
- `nombre`: `writeUTF` = 2 + 35
- `salario`: 8
- `planta`: 1
- `estado`: `writeUTF` = 2 + 8
- Total: **60 bytes**

Se hizo una prueba real escribiendo un registro de cada entidad: el archivo de curso creció exactamente 64 bytes y el de docente 60 bytes.

### Rutas de archivos

Se agregaron/mantuvieron constantes dentro de cada Utils:

- `CursoUtils.RUTA_ARCHIVO = "data//curso.txt"`
- `DocenteUtils.RUTA_ARCHIVO = "data//docente.txt"`

También se corrigió un error de la versión anterior donde `DocenteUtils` apuntaba a `departamento.txt`.

### Excepciones en lugar de booleanos

Los métodos de modificación ya no informan éxito o error mediante `true/false`.

Ejemplos:

- `ServicioCurso.agregarCurso(...)` -> `void` y lanza excepción.
- `ServicioCurso.actualizarCursoPorCodigo(...)` -> `void` y lanza excepción.
- `ServicioCurso.eliminarCursoPorCodigo(...)` -> `void` y lanza excepción.
- `CursoUtils.actualizarCursoPorCodigo(...)` -> `void` y lanza excepción si no encuentra el registro.
- `CursoUtils.eliminarCursoPorCodigo(...)` -> `void` y lanza excepción si no encuentra el registro.

Las GUI capturan las excepciones y muestran el mensaje específico. Así se puede diferenciar, por ejemplo:

- código inválido;
- curso inexistente;
- código repetido;
- nombre repetido;
- costo inválido;
- curso ya inactivo.

### Docentes

- Se conservó `GUIAdicionarDocente`.
- Se agregó `leerDocentes()` a persistencia.
- Se agregó `listarDocentes()` y `contarDocentes()` en servicio.
- Se añadió una `GUIListarDocente` funcional.
- Se añadió la opción **Docente > Listar** al menú principal.

La GUI de listar docente que existía en la versión adelantada estaba incompleta y mezclaba variables de `Curso`; por eso se rehízo de forma pequeña y funcional en vez de copiarla literalmente.

### Maven

- Se corrigió la clase principal de `Gestion_curso` a `GestionCurso`.
- Se ajustó `maven.compiler.release` a Java 21 para que coincida con la versión utilizada actualmente por el proyecto/equipo.

## 3. Relación Curso - Docente: estado actual

El modelo `Curso` ya contiene `codigoDocente` como FK y la persistencia ya reserva y conserva esos 4 bytes.

Sin embargo, la GUI actual de adicionar curso todavía no tiene un campo visual para escoger o escribir el docente. Por compatibilidad, el constructor antiguo continúa funcionando y deja `codigoDocente = 0`.

Cuando se asigne un `codigoDocente > 0`, `ServicioCurso` valida que ese docente exista antes de guardar.

### Pendiente recomendado

Agregar en `GUIAdicionarCurso` un campo o selector de docente y construir el curso con:

`new Curso(codigo, nombre, disponibilidad, creditos, costo, estado, codigoDocente)`

Después conviene mostrar también el docente en Buscar/Actualizar/Eliminar. La lista de cursos ya incluye la columna `Código docente`.

## 4. POCDataPicker del profesor

El ejemplo entregado por el profesor usa:

- dependencia Maven `com.toedter:jcalendar:1.4`;
- componente Swing `com.toedter.calendar.JDateChooser`;
- `java.util.Date`;
- `jdcFecha.getDate()` para recuperar la fecha elegida;
- un `PropertyChangeListener` para reaccionar al cambio de fecha.

### Qué falta en GestiónCursos para usarlo

Actualmente `Curso` y `Docente` no tienen ningún atributo fecha, así que **no se incorporó la librería todavía**, porque hacerlo sin definir qué fecha requiere el modelo sería agregar una dependencia sin una necesidad funcional.

Cuando el requerimiento defina una fecha (por ejemplo, fecha de vinculación del docente o fecha de inicio del curso), faltaría:

1. agregar la dependencia `jcalendar 1.4` al `pom.xml`;
2. agregar un `JDateChooser` en la GUI correspondiente;
3. definir el atributo de fecha en el modelo;
4. decidir cómo se persistirá la fecha y cuántos bytes ocupará;
5. actualizar `TAMANO_REGISTRO`;
6. actualizar TODOS los métodos de lectura/escritura/búsqueda/actualización para consumir exactamente esos nuevos bytes.

No conviene agregar la fecha únicamente en la GUI: primero debe quedar definido el atributo y su representación en el archivo.

## 5. PDF adjunto

El PDF adjunto corresponde a **Actividad 1 - Taller grupal: Sistema de gestión de cuentas bancarias**. Sus requisitos son una clase abstracta `CuentaBancaria`, las subclases `CuentaAhorro`, `CuentaCorriente` y `CuentaInversion`, una colección polimórfica en `Banco` y una clase `TestEvaluacion`.

Esos requisitos no describen el proyecto GestiónCursos, por lo que no se implementaron dentro de este código. Mezclar ambos ejercicios introduciría clases y lógica ajenas al sistema de cursos.

Si el PDF debía contener los requisitos del parcial/proyecto GestiónCursos, debe verificarse el archivo porque el adjunto actual es el taller bancario.

## 6. Pendientes principales del proyecto

Para continuar sin cambiar la arquitectura actual, el orden recomendado es:

1. terminar la asignación visual de Docente a Curso (`codigoDocente`);
2. completar Buscar/Actualizar/Eliminar docente;
3. decidir el requerimiento concreto de fechas antes de integrar JCalendar;
4. si se agrega cualquier atributo persistente, recalcular el registro fijo y actualizar todos los recorridos del archivo en la misma modificación;
5. después implementar consultas por relación, por ejemplo cursos de un docente.

## 7. Verificación técnica realizada

- Todo el código Java del proyecto actualizado fue compilado con `javac --release 21` sin errores.
- Se verificó físicamente que un docente ocupe 60 bytes.
- Se verificó físicamente que un curso con `codigoDocente` ocupe 64 bytes.
- Se verificó que la FK `codigoDocente` sobreviva a la escritura y lectura del registro.
