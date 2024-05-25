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
  
            //Agregar el listener al div del producto para redirigirlo a una pagina con sus especificaciones
            item.addEventListener("click", () => {
                window.location.href = `articulo.html?id=${producto.id}`;
            });

            //Agregar el listener al icono de agregar al carrito
            botonCarrito.addEventListener("click", () => {
                event.stopPropagation(); // Evitar que se active el evento click de la tarjeta
                //console.log("Agregado al carrito:", producto);
                if(responseData.data.existencia == 0){
                    Swal.fire({
                        title: 'Error',
                        text: 'El producto no se encuentra disponible',
                        icon: 'error',
                        confirmButtonText: 'Aceptar'
                    });
                    botonCarrito.disable = true;
                }
                else{
                    Swal.fire({
                        title: '¡Agregado al carrito!',
                        text: 'El producto ha sido agregado al carrito',
                        icon: 'success',
                        confirmButtonText: 'Aceptar'
                    });
                const idProducto = producto.id;
                agregarAlCarrito(idProducto);
                }
                
            });
  
            
          });

         async function agregarAlCarrito(producto) {
            try{

                const userId = localStorage.getItem('userId');
                console.log(userId)

                    if(userId == null || userId == 0){
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
        console.error("Hubo un error al realizar la solicitud:", error);
    }
  });



  





  /*function agregarAlCarrito(producto) {
    const carrito = JSON.parse(localStorage.getItem("carrito")) || [];
    carrito.push(producto);
    localStorage.setItem("carrito", JSON.stringify(carrito));
  }*/

