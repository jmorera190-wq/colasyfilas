package ControladorJJO;
import JJO.Cliente;
import java.util.LinkedList;

public class ControladorAtencion {

    private LinkedList<Cliente> colaTurnos = new LinkedList<>();
    private LinkedList<Cliente> pilaUrgentes = new LinkedList<>();

    private int atendidosNormales = 0;
    private int atendidosUrgentes = 0;
    private int maxCola = 0;
    private int maxPila = 0;
    private Cliente clienteActual = null;

    public void agregarCliente(Cliente c) {
        if (c.isUrgente()) {
            pilaUrgentes.push(c);
            if (pilaUrgentes.size() > maxPila) maxPila = pilaUrgentes.size();
        } else {
            colaTurnos.offer(c);
            if (colaTurnos.size() > maxCola) maxCola = colaTurnos.size();
        }
    }

    public Cliente atenderSiguiente() {
        if (!pilaUrgentes.isEmpty()) {
            clienteActual = pilaUrgentes.pop();
            atendidosUrgentes++;
        } else if (!colaTurnos.isEmpty()) {
            clienteActual = colaTurnos.poll();
            atendidosNormales++;
        } else {
            clienteActual = null;
        }
        return clienteActual;
    }

    public Cliente getClienteActual() {
        return clienteActual;
    }

    public int getAtendidosNormales() {
        return atendidosNormales;
    }

    public int getAtendidosUrgentes() {
        return atendidosUrgentes;
    }

    public int getMaxCola() {
        return maxCola;
    }

    public int getMaxPila() {
        return maxPila;
    }

    public LinkedList<Cliente> getColaTurnos() {
        return colaTurnos;
    }

    public LinkedList<Cliente> getPilaUrgentes() {
        return pilaUrgentes;
    }
}