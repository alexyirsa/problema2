package restaurante;

import java.util.List;

public class Pedido {
    private Usuario usuario;
    private List<Producto> productos;

    public Pedido(Usuario usuario, List<Producto> productos) {
        this.usuario = usuario;
        this.productos = productos;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public int calcularTotal() {
        return productos.stream().mapToInt(Producto::getPrecio).sum();
    }

    public void generarReporte() {
        System.out.println("Pedido de " + usuario.getNombre() + ":");
        for (Producto producto : productos) {
            System.out.println(" - " + producto.getNombre() + ": $" + producto.getPrecio());
        }
        System.out.println("Total: $" + calcularTotal());
        System.out.println("------------------------");
    }
}

