document.addEventListener('DOMContentLoaded', async function() {
    const usuario = localStorage.getItem('userId');
    console.log("El usuario es: " + usuario);

    let cantidadesSeleccionadas = [];
    
    if (usuario == null || usuario == 0 || usuario == undefined) {
        Swal.fire({
            title: 'Error',
            text: 'Debe iniciar sesión',
            icon: 'error',
            confirmButtonText: 'Aceptar'
        }).then((result) => {
            if (result.value) {
                window.location.href = 'acceder.html';
            }
        });
        return; // Termina la ejecución si el usuario no está definido
    }

    try {
        const urlCarrito = `http://localhost:8080/api/carrito?idUsuario=${usuario}`;
        const responseCarrito = await fetch(urlCarrito, {
            method: "GET",
            headers: {
                "Content-Type": "application/json"
            },
        });

        const carritoData = await responseCarrito.json();
        console.log(carritoData.message);
        const numeroProductos = carritoData.data.length;
        const listaProductos = carritoData.data.map(item => item.idProducto);
        const respuesta = listaProductos.map(idProducto => ({
            idUsuario: usuario,
            idProducto,
            cantidadComprada: "2"
        }));

        console.log(respuesta);

        const cartContent = document.querySelector('.cart-content');
        if (!cartContent) {
            console.error('El elemento .cart-content no se encontró en el DOM.');
            return;
        }
        cartContent.innerHTML = ''; // Clear existing content

        for (const idProducto of listaProductos) {
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
                quantityInput.setAttribute('data-id', idProducto);
                quantityInput.classList.add('cart-quantity');

                const quantityButton = document.createElement('button');
                quantityButton.type = 'button';
                quantityButton.classList.add('quantity-button');
                quantityButton.textContent = 'Confirmar cantidad';

                // Agrega un eventListener al botón de confirmación de cantidad
                quantityButton.addEventListener('click', async function() {
                    console.log(`ID del producto: ${idProducto}`);
                    console.log(`Cantidad seleccionada: ${quantityInput.value}`);

                    try {
                        const url = `http://localhost:8080/api/carrito/${idProducto}`;
                        const data = {
                            idUsuario: usuario,
                            cantidadCompra: quantityInput.value
                        };

                        const response = await fetch(url, {
                            method: "PUT",
                            headers: {
                                "Content-Type": "application/json"
                            },
                            body: JSON.stringify(data)
                        });

                        const responseData = await response.json();
                        if(responseData.code === 422){
                            swal.fire({
                                title: 'Espera !!',
                                text: 'La cantidad del producto : "' + articulo.nombre + '" que deseas sobrepasa a la cantidad existente',
                                icon: 'warning',
                                confirmButtonText: 'Aceptar'
                            }) 
                        } else {
                            cantidadesSeleccionadas[idProducto] = quantityInput.value

                            swal.fire({
                                title: 'Exito',
                                text: 'Has agregado ' + quantityInput.value + ' artículos del producto "' + articulo.nombre + '" con éxito',
                                icon: 'success',
                                confirmButtonText: 'Aceptar'
                            });
                        }         
                        
                        

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

                    try {
                        const url = `http://localhost:8080/api/carrito/${idProducto}`;
                        const data = {
                            idUsuario: usuario,
                        };

                        const response = await fetch(url, {
                            method: "DELETE",
                            headers: {
                                "Content-Type": "application/json"
                            },
                            body: JSON.stringify(data)
                        });

                        const responseData = await response.json();
                        console.log(responseData); // Imprimir el valor del json "message"
                  
                        if (response.ok) {
                            cartBox.remove();
                            swal.fire({
                                title: 'Listo',
                                text: 'Artículo eliminado',
                                icon: 'success',
                                confirmButtonText: 'Aceptar'
                            })
                        }
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

        // Añadir event listener al botón de compra
        const buyButton = document.querySelector('.btn-buy');
        buyButton.addEventListener('click', async function() {
            console.log("Id del Usuario:" + usuario);

            try {
                const compraData = await obtenerDatosCompra(usuario);
            
                let total = 0;
                let detalleCompra = ''; // String para almacenar los detalles de la compra
            
                for (const item of compraData) {
                    if (cantidadesSeleccionadas[item.idProducto]) {
                        const urlArticulo = `http://localhost:8080/api/articulos/${item.idProducto}`;
                        const responseArticulo = await fetch(urlArticulo, {
                            method: "GET",
                            headers: {
                                "Content-Type": "application/json"
                            },
                        });
                        const articuloData = await responseArticulo.json();
                        const articulo = articuloData.data[0];
                        const subtotal = articulo.precio * item.cantidadCompra;
                        total += subtotal;
            
                        // Agregar los detalles de cada producto al string de detalleCompra
                        detalleCompra += `Producto: ${articulo.nombre}, Cantidad: ${item.cantidadCompra}, Subtotal: $${subtotal.toFixed(2)}\n`;
                    }
                }
            
                if (total === 0) {
                    swal.fire({
                        title: 'Espera !!',
                        text: 'Probablemente olvidaste confirmar la cantidad de los artículos que deseas comprar',
                        icon: 'warning',
                        confirmButtonText: 'Aceptar'
                    });
                    return;
                }
            
                // Mostrar los detalles de la compra y el total
                swal.fire({
                    title: 'Detalle de su compra',
                    html: `<pre>${detalleCompra}</pre>Total: $${total.toFixed(2)}`,
                    icon: 'info',
                    confirmButtonText: 'Aceptar',
                    customClass: {
                        popup: 'mi-clase-personalizada'
                    }
                }).then((result) => {
                    if (result.value) {
                        window.location.href = 'index.html';
                    }
                });

                const urlCompra = "http://localhost:8080/api/carrito";
                const responseCompra = await fetch(urlCompra, {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify(compraData)
                });

                const compraResponseData = await responseCompra.json();
                console.log(compraResponseData);
                console.log(compraResponseData.message);

            } catch (error) {
                console.error("Hubo un error al realizar la solicitud:", error);
            }
        });

        async function obtenerDatosCompra(usuario) {
            try {
                const urlCarrito = `http://localhost:8080/api/carrito?idUsuario=${usuario}`;
                const responseCarrito = await fetch(urlCarrito, {
                    method: "GET",
                    headers: {
                        "Content-Type": "application/json"
                    },
                });
        
                const carritoData = await responseCarrito.json();
        
                // Crear el JSON con el formato necesario para la compra
                const compraData = carritoData.data.map(item => ({
                    idUsuario: usuario,
                    idProducto: item.idProducto,
                    cantidadCompra: item.cantidadCompra
                }));
        
                return compraData; // Devuelve los datos de compra
        
            } catch (error) {
                console.error("Hubo un error al realizar la solicitud:", error);
                throw error; // Propaga el error
            }
        }

    } catch (error) {
        console.error("Hubo un error al realizar la solicitud:", error);
    }
});

