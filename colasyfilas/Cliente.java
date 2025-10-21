package colasyfilas;
public class Cliente {
    private final String id;
    private final String nombre;
    private final String email;
    private final String telefono;

    public Cliente(String id, String nombre, String email, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
    }

    public String getId() { return id; }
    public String getNombre() { return nombre; }

    @Override
    public String toString() {
        return id + " - " + nombre;
    }
}