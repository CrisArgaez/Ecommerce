document.addEventListener('DOMContentLoaded', async function() {
    try {
        const url = "http://localhost:8080/api/articulos";

        const response = await fetch(url, {
            method: "GET",
            headers: {
                "Content-Type": "application/json"
            },
        });

        const responseData = await response.json();
        console.log(responseData.message)//Imprimir el valor del json "message"
        console.log(responseData.data)

        const containerItems = document.querySelector(".container-items"); //Contenedor de los productos

        responseData.data.forEach(producto => {
            const item = document.createElement("div"); // Crea el contenedor de cada producto  
            item.classList.add("product-box"); // Agrega la clase "producto"

            const imagen = document.createElement("img");
            imagen.src = producto.urlImagen;
            imagen.classList.add("product-img"); // Agrega la clase "product-img"
        
            const titulo = document.createElement("h2");
            titulo.textContent = producto.nombre;
            titulo.classList.add("product-title"); // Agrega la clase "product-title"
        
            const precio = document.createElement("span");
            precio.textContent = `$${producto.precio.toFixed(2)}`;
            precio.classList.add("price"); // Agrega la clase "price"
        
            const botonCarrito = document.createElement("i");
            botonCarrito.classList.add("bx", "bxs-cart-add", "add-cart");
        
            // Agrega los elementos al contenedor
            item.appendChild(titulo);
            item.appendChild(imagen);
            item.appendChild(precio);
            item.appendChild(botonCarrito);
        
            containerItems.appendChild(item);
          });
      

    } catch (error) {
        console.error("Hubo un error al realizar la solicitud:", error);
    }
});
