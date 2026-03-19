# AppMascotas

Aplicación Android (Java) para gestionar mascotas consumiendo un Web Service REST.

## Requisito importante

Este proyecto necesita que el servicio `wsmascotas` esté levantado y accesible en red.

La app consume estos endpoints:

- `GET /mascotas/`
- `POST /mascotas/`
- `PUT /mascotas/{id}`
- `DELETE /mascotas/{id}`

Actualmente la URL base está hardcodeada como:

- `http://192.168.X.X:3000/mascotas/`

Si tu IP/puerto es diferente, actualiza la constante `URL` en estos archivos:

- `app/src/main/java/com/example/appmascotas/Registrar.java`
- `app/src/main/java/com/example/appmascotas/Listar.java`
- `app/src/main/java/com/example/appmascotas/ListarCustom.java`
- `app/src/main/java/com/example/appmascotas/Actualizar.java`

## Ejecución rápida

1. Clonar el proyecto:

```bash
git clone https://github.com/CarlosEvCode/AppMascotas
cd AppMascotas
```

2. Clonar y ejecutar `wsmascotas` (en otra carpeta/terminal):

```bash
git clone https://github.com/CarlosEvCode/wsmascotas
cd wsmascotas
# instalar dependencias y levantar servicio (según su README de ese repo)
```

3. Verificar que el Web Service responda antes de abrir la app:

```bash
curl http://<IP_DEL_WS>:3000/mascotas/
```

4. Abrir `AppMascotas` en Android Studio.
5. Sincronizar Gradle y ejecutar en emulador/dispositivo Android (minSdk 30).

## Qué puede hacer la app

- Registrar mascota:
  Usa formulario con validaciones (`tipo`, `nombre`, `color`, `peso`) y envía `POST` con Volley.
- Listar mascotas:
  Consulta `GET /mascotas/` y muestra un listado simple en `ListView`.
- Listar personalizado (RecyclerView):
  Consulta `GET /mascotas/`, mapea la respuesta a objetos `Mascota` y muestra tarjetas en `RecyclerView` con adapter personalizado.

## Stack usado

- Android Studio + Java 11
- Gradle 9.3.1
- Volley para consumo del Web Service
- ListView y RecyclerView
