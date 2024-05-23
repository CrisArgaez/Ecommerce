<<<<<<< HEAD
// Supongamos que tienes un array de objetos con los datos de tus productos
const productos = [
    {
      titulo: "Producto 1",
      imagen: "https://i.pinimg.com/564x/31/d6/3a/31d63a2d2606abf32cf7ab6a614e3075.jpg",
      precio: 29.99,
    }
  ];
  
  // Funcion que crea las tarjetas para los productos y mostralas
  function crearProductos() {
    const containerItems = document.querySelector(".container-items"); //Contenedor de los productos
  
    productos.forEach((producto) => {
      const item = document.createElement("div"); // Crea el contenedor de cada producto
      item.classList.add("product-box"); // Agrega la clase "producto"
=======
document.addEventListener('DOMContentLoaded', async function() {
    try {
        const url = "http://localhost:8080/api/articulos";

        const response = await fetch(url, {
            method: "GET",
            headers: {
                "Content-Type": "application/json"
            },
        });
>>>>>>> 57c151d9ff475fa8c8b121a249fdc0fcb3b5c473

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