
document.addEventListener('DOMContentLoaded', async function() {
    try {
        const urlParams = new URLSearchParams(window.location.search);
        const id = urlParams.get('id'); //obtenemos el id
        console.log("Este producto tiene el id:", id)

        const apiUrl = `http://localhost:8080/api/articulos/${id}`;
        console.log(apiUrl)

        const response = await fetch(apiUrl, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            },
        });

        const responseData = await response.json();
        console.log(responseData.data) //Imprimir el valor del json "message"



        //console.log(responseData.data) //Imprimir el valor del json "message"
        const articulo = responseData.data[0];
        console.log(articulo)

         // Almacena la URL de la imagen principal actual
         let imagenPrincipalURL = articulo.urlImagen;


        //Aquí es donde insertamos los datos en el HTML
        document.getElementById('nombre-producto').textContent = articulo.nombre;
        document.getElementById('precio-producto').textContent = `$${articulo.precio.toFixed(2)}`,
        document.getElementById('cantidad-disponible').textContent = 'Cantidades disponibles: ' + articulo.existencia;
        document.getElementById('descripción-producto').textContent = articulo.descripcion;
        

        // Obtener las especificaciones como un array
        const especificacionesArray = articulo.especificacion.split(",");

        // Actualizar los elementos HTML con las especificaciones
        const especificacionesContainer = document.getElementById('especificaciones-producto');
        especificacionesArray.forEach((especificacion) => {
        const especificacionElement = document.createElement("li");
        especificacionElement.textContent = especificacion.trim(); // Elimina espacios en blanco
        especificacionesContainer.appendChild(especificacionElement);
        });

        // Obtener las URLs de las imágenes de la galería como un array (suponiendo que están separadas por comas)
        const galeriaFotosArray = articulo.galeriaFotos.split(",");

        // Actualizar la imagen principal
        document.getElementById('MainImg').src = articulo.urlImagen;

        // Actualizar las imágenes de la galería
        const smallImgGroup = document.querySelector('.small-img-group');
        galeriaFotosArray.forEach((url) => {
            const imgCol = document.createElement('div');
            imgCol.classList.add('small-img-col');

            const img = document.createElement('img');
            img.src = url.trim(); // Elimina espacios en blanco
            img.width = '100%';
            img.classList.add('small-img');

            imgCol.appendChild(img);
            smallImgGroup.appendChild(imgCol);
        });

        const miniaturas = document.querySelectorAll('.small-img');

        miniaturas.forEach((miniatura) => {
            miniatura.addEventListener('click', (event) => {
                const nuevaUrl = event.target.src; // Obtén la URL de la miniatura clicada
                if (nuevaUrl !== imagenPrincipalURL) {
                    document.getElementById('MainImg').src = nuevaUrl; // Actualiza la imagen principal solo si es diferente
                    imagenPrincipalURL = nuevaUrl; // Almacena la nueva URL
                }
            });
        });

        if(articulo.existencia == 0){
            Swal.fire({
                title: 'Error',
                text: 'El producto no se encuentra disponible',
                icon: 'error',
                confirmButtonText: 'Aceptar'
            }).then((result) => {
                if (result.value) {
                    window.location.href = 'index.html';
                }
            });
            botonCarrito.disabled = true;
        }
        //Anadir el listener al icono de agregar al carrito
        const botonCarrito = document.querySelector('.buy-btn');
        botonCarrito.addEventListener('click', (event) => {
            event.stopPropagation(); 
                if(responseData.code == 409 || responseData.code == 422){ //Validar si el producto ya se encuentra en el carrito
                    Swal.fire({
                        title: 'Error',
                        text: 'El producto ya se encuentra en el carrito',
                        icon: 'Warning',
                        confirmButtonText: 'Aceptar'
                    });
                }
                else{
                    Swal.fire({
                        title: '¡Agregado al carrito!',
                        text: 'El producto ha sido agregado al carrito',
                        icon: 'success',
                        confirmButtonText: 'Aceptar'
                    });
                const idProducto = articulo.id;
                agregarAlCarrito(idProducto);
                }
        });

        //Anadir la funcion de agregar al carrito
        async function agregarAlCarrito(producto) {
            try{

                const userId = localStorage.getItem('userId');
                console.log(userId)

                    if(userId == null || userId == 0 || userId == undefined){
                        swal.fire({
                            title: 'Error',
                            text: 'Debe iniciar sesión',
                            icon: 'error',
                            confirmButtonText: 'Aceptar'
                        }).then((result) => {
                            if (result.value) {
                                window.location.href = 'acceder.html';
                            }
                        });
                    }
                    else{
                const url = `http://localhost:8080/api/articulos/${producto}`;
                const data ={
                    id: userId
                };
        
                const response = await fetch(url, {
                    method: "PUT",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify(data)
                });
        
                const responseData = await response.json();
                console.log(responseData.message)//Imprimir el valor del json "message"
                }
            } catch (error) {
                console.error("Hubo un error al realizar la solicitud:", error);
            }
        
          }
        

    } catch (error) {
        console.error('Hubo un error al obtener los datos del producto:', error);
    }
});
