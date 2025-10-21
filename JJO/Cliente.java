package JJO;

public class Cliente {
    private int id;
    private String nombre;
    private String email;
    private String telefono;
    private boolean esUrgente;

    public Cliente(int id, String nombre, String email, String telefono, boolean esUrgente) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.esUrgente = esUrgente;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getEmail() { return email; }
    public String getTelefono() { return telefono; }
    public boolean isUrgente() { return esUrgente; }

    @Override
    public String toString() {
        return "[" + id + "] " + nombre + (esUrgente ? " (Urgente)" : " (Normal)");
    }
}