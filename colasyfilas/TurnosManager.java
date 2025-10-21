package colasyfilas;
import java.util.LinkedList;
import java.util.List;

public class TurnosManager {
    private final LinkedList<Cliente> colaTurnos = new LinkedList<>();
    private final LinkedList<Cliente> pilaUrgentes = new LinkedList<>();
    private final List<RegistroAtencion> historial = new LinkedList<>();

    private Cliente clienteEnAtencion;
    private int atendidosUrgentes = 0;
    private int atendidosNormal = 0;
    private int maxCola = 0;
    private int maxPila = 0;

    public void registrarTurnoNormal(Cliente cliente) {
        colaTurnos.offer(cliente);
        if (colaTurnos.size() > maxCola) {
            maxCola = colaTurnos.size();
        }
    }

    public void registrarTurnoUrgente(Cliente cliente) {
        pilaUrgentes.push(cliente);
        if (pilaUrgentes.size() > maxPila) {
            maxPila = pilaUrgentes.size();
        }
    }

    // MÉTODO AÑADIDO: Verifica si el ID ya está en alguna de las listas activas
    public boolean existeId(String id) {
        return colaTurnos.stream().anyMatch(c -> c.getId().equals(id)) ||
                pilaUrgentes.stream().anyMatch(c -> c.getId().equals(id));
    }

    public Cliente atenderSiguiente() {
        Cliente clienteAtendido = null;
        String tipoAtencion = null;

        if (!pilaUrgentes.isEmpty()) {
            clienteAtendido = pilaUrgentes.pop();
            tipoAtencion = "URGENTE";
            atendidosUrgentes++;
        } else if (!colaTurnos.isEmpty()) {
            clienteAtendido = colaTurnos.poll();
            tipoAtencion = "NORMAL";
            atendidosNormal++;
        }

        if (clienteAtendido != null) {
            clienteEnAtencion = clienteAtendido;
            historial.add(new RegistroAtencion(tipoAtencion, clienteAtendido));
        } else {
            clienteEnAtencion = null;
        }
        return clienteAtendido;
    }

    public boolean moverUltimoAlFrente() {
        if (colaTurnos.size() > 1) {
            Cliente ultimo = colaTurnos.pollLast();
            colaTurnos.addFirst(ultimo);
            return true;
        }
        return false;
    }

    public LinkedList<Cliente> getColaTurnos() { return colaTurnos; }
    public LinkedList<Cliente> getPilaUrgentes() { return pilaUrgentes; }
    public List<RegistroAtencion> getHistorial() { return historial; }
    public Cliente getClienteEnAtencion() { return clienteEnAtencion; }
    public int getAtendidosUrgentes() { return atendidosUrgentes; }
    public int getAtendidosNormal() { return atendidosNormal; }
    public int getMaxCola() { return maxCola; }
    public int getMaxPila() { return maxPila; }
    public int getSizeCola() { return colaTurnos.size(); }
    public int getSizePila() { return pilaUrgentes.size(); }

    public Cliente peekNormal() {
        return colaTurnos.peek();
    }
}