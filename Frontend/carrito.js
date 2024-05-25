document.addEventListener('DOMContentLoaded', async function() {
    try{
        const url = "http://localhost:8080/api/carrito?idUsuario=1";
  
        const response = await fetch(url, {
            method: "GET",
            headers: {
                "Content-Type": "application/json"
            },
        });

        const responseData = await response.json();
        console.log(responseData.message)//Imprimir el valor del json "message"
        console.log(responseData.data)
  

    } catch (error) {
        console.error("Hubo un error al realizar la solicitud:", error);
    }

});