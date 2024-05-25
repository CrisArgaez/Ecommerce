document.addEventListener('DOMContentLoaded', async function() {

    const usuario = localStorage.getItem('userId');
    console.log("El usuario es: " + usuario);
    
    if (usuario == null || usuario == 0) {
        swal.fire({
            title: 'Error',
            text: 'Debe iniciar sesión',
            icon: 'error',
            confirmButtonText: 'Aceptar'
        }).then((result) => {
            if (result.value) {
                window.location.href = 'acceder.html';
            }
        })
    }

    try {

        const usuario = localStorage.getItem('userId');
        console.log("El usuario es: " + usuario);

        const urlCarrito = `http://localhost:8080/api/carrito?idUsuario=${usuario}`;
        const responseCarrito = await fetch(urlCarrito, {
            method: "GET",
            headers: {
                "Content-Type": "application/json"
            },
        });

        const carritoData = await responseCarrito.json();
        console.log(carritoData.message);

        var numeroProductos = carritoData.data.length
        var listaProductos = []

        for(var i = 0; i<numeroProductos; i++){
            listaProductos.push(carritoData.data[i].idProducto)
        }

        var respuesta = []

        for (let i = 0; i < numeroProductos; i++) {
            const objeto = {
                idUsuario: userId,
                idProducto: listaProductos[i],
                cantidadComprada: "2"
            };
            respuesta.push(objeto);
        }

        console.log(respuesta)

        const numeroArticulos = carritoData.data.length;
        const listaArticulos = carritoData.data.map(item => item.idProducto);

        const cartContent = document.querySelector('.cart-content');
        cartContent.innerHTML = ''; // Clear existing content

        for (const idProducto of listaArticulos) {
            try {
                const urlArticulo = `http://localhost:8080/api/articulos/${idProducto}`;
                const responseArticulo = await fetch(urlArticulo, {
                    method: "GET",
                    headers: {
                        "Content-Type": "application/json"
                    },
                });

                const articuloData = await responseArticulo.json();
                const articulo = articuloData.data[0];
                console.log(articulo);

                // Crear los elementos necesarios para mostrar el artículo
                const cartBox = document.createElement('div');
                cartBox.classList.add('cart-box');

                const img = document.createElement('img');
                img.src = articulo.urlImagen;
                img.classList.add('cart-image');

                const detailBox = document.createElement('div');
                detailBox.classList.add('detail-box');

                const productTitle = document.createElement('div');
                productTitle.classList.add('cart-product-title');
                productTitle.textContent = articulo.nombre;

                const price = document.createElement('div');
                price.classList.add('cart-price');
                price.textContent = `${articulo.precio}`;

                const quantityContainer = document.createElement('div');
                quantityContainer.classList.add('quantity-container');

                const quantityInput = document.createElement('input');
                quantityInput.type = 'number';
                quantityInput.value = 1;
                quantityInput.classList.add('cart-quantity');

                const quantityButton = document.createElement('button');
                quantityButton.type = 'button';
                quantityButton.classList.add('quantity-button');
                quantityButton.textContent = 'Confirmar cantidad';

                // Agrega un eventListener al botón de confirmación de cantidad
                quantityButton.addEventListener('click', async function() {
                    console.log(`ID del producto: ${idProducto}`);
                    console.log(`Cantidad seleccionada: ${quantityInput.value}`);

                    try{
                        const userId = localStorage.getItem('userId');
                        console.log(userId)
                        const url = `http://localhost:8080/api/carrito/${idProducto}`;
                        const data ={
                            idUsuario: userId,
                            cantidadCompra : quantityInput.value
                        };
                
                        const response = await fetch(url, {
                            method: "PUT",
                            headers: {
                                "Content-Type": "application/json"
                            },
                            body: JSON.stringify(data)
                        });
                
                        const responseData = await response.json();
                        console.log(responseData)//Imprimir el valor del json "message"
                    } catch (error) {
                        console.error("Hubo un error al realizar la solicitud:", error);
                    }
                });

                quantityContainer.appendChild(quantityInput);
                quantityContainer.appendChild(quantityButton);

                detailBox.appendChild(productTitle);
                detailBox.appendChild(price);
                detailBox.appendChild(quantityContainer);

                const removeIcon = document.createElement('i');
                removeIcon.classList.add('bx', 'bxs-trash-alt', 'cart-remove');

                // Agrega un eventListener al botón de eliminación
                removeIcon.addEventListener('click', async function() {
                    console.log(`ID del producto a eliminar: ${idProducto}`);

                    try{
                        const userId = localStorage.getItem('userId');
                        console.log(userId)
                        const url = `http://localhost:8080/api/carrito/${idProducto}`;
                        const data ={
                            idUsuario: userId,
                        };
                
                        const response = await fetch(url, {
                            method: "DELETE",
                            headers: {
                                "Content-Type": "application/json"
                            },
                            body: JSON.stringify(data)
                        });
                
                        const responseData = await response.json();
                        console.log(responseData)//Imprimir el valor del json "message"
                    } catch (error) {
                        console.error("Hubo un error al realizar la solicitud:", error);
                    }
                });

                cartBox.appendChild(img);
                cartBox.appendChild(detailBox);
                cartBox.appendChild(removeIcon);

                cartContent.appendChild(cartBox);
            } catch (error) {
                console.error("Hubo un error al realizar la solicitud:", error);
            }
        }
    } catch (error) {
        console.error("Hubo un error al realizar la solicitud:", error);
    }
});
