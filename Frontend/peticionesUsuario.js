const btnRegistrar = document.getElementById('btnRegistrar');

btnRegistrar.addEventListener('click', () => {
    event.preventDefault(); // Evita que el formulario se envíe

    // Obtiene los valores de los campos
    const nombres = document.querySelector('[placeholder="Nombres"]').value;
    const apellidos = document.querySelector('[placeholder="Apellidos"]').value;
    const correo = document.querySelector('[placeholder="Correo"]').value;
    const password = document.querySelector('[placeholder="Contraseña"]').value;
    registrarUsuario(nombres, password, apellidos, correo);
});

async function registrarUsuario(nombreUsuario, password, nombreCompleto, correoElectronico) {
    try {
        const url = "http://localhost:8080/api/user-servlet";
        const data = {
            username : nombreUsuario,
            password : password,
            fullName : nombreCompleto,
            email : correoElectronico
        };

        const response = await fetch(url, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(data)
        });

        if (response.ok) {
            // La solicitud fue exitosa (código de estado 200)
            console.log("Usuario registrado correctamente");
        } else {
            // La solicitud no fue exitosa
            console.error("Error al registrar el usuario. Código de estado:", response.status);
            const errorMessage = await response.text();
            console.error("Mensaje de error:", errorMessage);
        }
    } catch (error) {
        console.error("Hubo un error al realizar la solicitud:", error);
    }
}

/*async function registrarUsuario(nombreUsuario, password, nombreCompleto, correoElectronico){
    try {
        //const res = await fetch(`https://localhost/api/v2/item/${correo}}/`);
        const res = await fetch(`http://localhost:8080/programacion-web-its-prac4/user-servlet/?username=${nombreUsuario}&password=${password}&fullName=${nombreCompleto}&email=${correoElectronico}`);
        const data = await res.json()
    } catch (error) {
        console.error('Hubo un error al obtener los datos del Objeto:', error);
    }
}*/