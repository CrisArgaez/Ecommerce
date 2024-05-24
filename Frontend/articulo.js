
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
<<<<<<< HEAD
        console.log(responseData.data) //Imprimir el valor del json "message"
=======
        //console.log(responseData.data) //Imprimir el valor del json "message"
        const articulo = responseData.data[0];

         // Almacena la URL de la imagen principal actual
         let imagenPrincipalURL = articulo.urlImagen;


        //Aquí es donde insertamos los datos en el HTML
        document.getElementById('nombre-producto').textContent = articulo.nombre;
        document.getElementById('precio-producto').textContent = `$${articulo.precio.toFixed(2)}`,
        document.getElementById('cantidad-disponible').textContent = articulo.existencia;
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
        
    
>>>>>>> d3955a8d84b321827b442ca03e5ef88f8f0519e7
    } catch (error) {
        console.error('Hubo un error al obtener los datos del producto:', error);
    }
});

/*
//Cambio de imagen
var MainImg = document.getElementById("MainImg");
var smallimg = document.getElementsByClassName("small-img");

smallimg[0].onclick = function() {
    MainImg.src = smallimg[0].src;  
}

smallimg[1].onclick = function() {
    MainImg.src = smallimg[1].src;  
}

smallimg[2].onclick = function() {
    MainImg.src = smallimg[2].src;  
}

smallimg[3].onclick = function() {
    MainImg.src = smallimg[3].src;  
}*/