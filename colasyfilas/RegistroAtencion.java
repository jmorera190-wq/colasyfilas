package colasyfilas;
import java.time.LocalDateTime;

public class RegistroAtencion {
    private final LocalDateTime fechaHora;
    private final String tipo;
    private final Cliente cliente;

    public RegistroAtencion(String tipo, Cliente cliente) {
        this.fechaHora = LocalDateTime.now();
        this.tipo = tipo;
        this.cliente = cliente;
    }

    public LocalDateTime getFechaHora() { return fechaHora; }
    public String getTipo() { return tipo; }
    public Cliente getCliente() { return cliente; }

    @Override
    public String toString() {
        return fechaHora.toString() + " | " + tipo + " | " + cliente.toString();
    }
}