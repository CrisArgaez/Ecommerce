const currentUrl = window.location.href;

if (currentUrl.includes("registro.html")) {
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

    //Peticion a la API para registro de usuario
    async function registrarUsuario(nombreUsuario, password, nombreCompleto, correoElectronico) {
        try {
            const url = "http://localhost:8080/api/register";
            const data = {//Creacion del objeto con los valores obtenidos del formulario
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

} else if (currentUrl.includes("acceder.html")) {
    const btnLogin = document.getElementById('btnLogin');

    btnLogin.addEventListener('click', () => {
        event.preventDefault(); // Evita que el formulario se envíe
    
        // Obtiene los valores de los campos
        const correoElectronico = document.querySelector('[placeholder="Correo_Electronico"]').value;
        const passwordEntrada = document.querySelector('[placeholder="Contraseña_Entrada"]').value;
        verificarUsuario(correoElectronico, passwordEntrada);
    });
    
    //Peticion a la API para registro de usuario
    async function verificarUsuario( correoElectronico, password) {
        try {
            const url = "http://localhost:8080/api/login";
            const data = {//Creacion del objeto con los valores obtenidos del formulario
                password : password,
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
            //console.log(response)
        } catch (error) {
            console.error("Hubo un error al realizar la solicitud:", error);
        }
    }
} 