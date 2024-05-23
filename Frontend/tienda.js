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

      const imagen = document.createElement("img");
      imagen.src = producto.imagen;
      imagen.classList.add("product-img"); // Agrega la clase "product-img"
  
      const titulo = document.createElement("h2");
      titulo.textContent = producto.titulo;
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
  }
  
  // Llama a la función para cargar los productos al cargar la página
  crearProductos();
  
  
  