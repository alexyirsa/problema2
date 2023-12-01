package restaurante;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Restaurante {
    private ArrayList<Producto> productos;
    private ArrayList<Pedido> pedidos;
    private ArrayList<Usuario> usuarios;

    public Restaurante(ArrayList<Producto> productos, ArrayList<Pedido> pedidos, ArrayList<Usuario> usuarios) {
        this.productos = productos;
        this.pedidos = pedidos;
        this.usuarios = usuarios;
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public void setProductos(ArrayList<Producto> productos) {
        this.productos = productos;
    }

    public void generarReporte() {
        int total = calcularTotal();
        System.out.println("------------------------");
        System.out.println("El total de ventas para el restaurante es: " + total);

        // Crear una copia de la lista de pedidos antes de ordenarla
        ArrayList<Pedido> pedidosCopia = new ArrayList<>(pedidos);
        ArrayList<Pedido> pedidosOrdenados = pedidosPorPrecio(pedidosCopia);

        for (Pedido pedido : pedidosOrdenados) {
            pedido.generarReporte();
        }
    }

    private int calcularTotal() {
        int total = 0;
        for (Pedido pedido : pedidos) {
            for (Producto producto : pedido.getProductos()) {
                total += producto.getPrecio();
            }
        }
        return total;
    }

    public ArrayList<Pedido> pedidosPorPrecio(ArrayList<Pedido> pedidos) {
        ArrayList<Pedido> pedidosCopia = new ArrayList<>(pedidos);
        ArrayList<Pedido> pedidosOrdenados = ordenarPedidosPorPrecioHelper(pedidosCopia);
        Collections.reverse(pedidosOrdenados);
        return pedidosOrdenados;
    }

    private ArrayList<Pedido> ordenarPedidosPorPrecioHelper(ArrayList<Pedido> pedidos) {
        if (pedidos.size() <= 1) {
            return pedidos;
        }

        ArrayList<Pedido> izquierda = new ArrayList<>();
        ArrayList<Pedido> derecha = new ArrayList<>();

        for(int i = 0; i < pedidos.size(); i++) {
            if (i < pedidos.size() / 2) {
                izquierda.add(pedidos.get(i));
            } else {
                derecha.add(pedidos.get(i));
            }
        }

        izquierda = ordenarPedidosPorPrecioHelper(izquierda);
        derecha = ordenarPedidosPorPrecioHelper(derecha);

        return merge(izquierda, derecha);
    }

    private ArrayList<Pedido> merge(ArrayList<Pedido> izquierda, ArrayList<Pedido> derecha) {
        ArrayList<Pedido> resultado = new ArrayList<>();

        while (!izquierda.isEmpty() && !derecha.isEmpty()) {
            if (izquierda.get(0).calcularTotal() < derecha.get(0).calcularTotal()) {
                resultado.add(izquierda.get(0));
                izquierda.remove(0);
            } else {
                resultado.add(derecha.get(0));
                derecha.remove(0);
            }
        }

        while (!izquierda.isEmpty()) {
            resultado.add(izquierda.get(0));
            izquierda.remove(0);
        }

        while (!derecha.isEmpty()) {
            resultado.add(derecha.get(0));
            derecha.remove(0);
        }

        return resultado;
    }
    public static void main(String[] args) {
        List<Producto> productos = List.of(
                new Producto("Hamburguesa", 100),
                new Producto("Papas", 50),
                new Producto("Refresco", 30),
                new Producto("Helado", 20)
        );

        List<Usuario> usuarios = List.of(
                new Usuario("Juan", "Calle 1", new ArrayList<>()),
                new Usuario("Pedro", "Calle 2", new ArrayList<>())
        );

        List<Producto> productosPedido1 = List.of(productos.get(0), productos.get(1));
        List<Producto> productosPedido2 = List.of(productos.get(2), productos.get(3));
        List<Producto> productosPedido3 = List.of(productos.get(0), productos.get(1), productos.get(2));

        List<Pedido> pedidos = List.of(
                new Pedido(usuarios.get(0), new ArrayList<>(productosPedido1)),
                new Pedido(usuarios.get(0), new ArrayList<>(productosPedido2)),
                new Pedido(usuarios.get(1), new ArrayList<>(productosPedido3))
        );

        usuarios.get(0).setPedidos(List.of(pedidos.get(0), pedidos.get(1)));
        usuarios.get(1).setPedidos(List.of(pedidos.get(2)));

        Restaurante restaurante = new Restaurante(new ArrayList<>(productos), new ArrayList<>(pedidos), new ArrayList<>(usuarios));

        restaurante.generarReporte();
        usuarios.forEach(Usuario::generarReporte);
    }
}
