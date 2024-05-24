//Obtenemos el id del articulo desde el link de la URL
/*const urlParams = new URLSearchParams(window.location.search)
const id = urlParams.get('id')

console.log("Este producto tiene el id:", id)*/


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
    } catch (error) {
        console.error('Hubo un error al obtener los datos del producto:', error);
    }
});
