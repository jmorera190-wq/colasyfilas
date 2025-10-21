package colasyfilas;
public class AtencionController {
    private final TurnosManager manager;

    public AtencionController(TurnosManager manager) {
        this.manager = manager;
    }

    public void registrarCliente(String id, String nombre, String email, String telefono, boolean esUrgente) {
        Cliente nuevoCliente = new Cliente(id, nombre, email, telefono);

        if (esUrgente) {
            manager.registrarTurnoUrgente(nuevoCliente);
        } else {
            manager.registrarTurnoNormal(nuevoCliente);
        }
    }

    public Cliente atender() {
        return manager.atenderSiguiente();
    }

    public boolean moverUltimoFrente() {
        return manager.moverUltimoAlFrente();
    }

    public TurnosManager getManager() {
        return manager;
    }
}