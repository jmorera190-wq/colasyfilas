package colasyfilas;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.stream.Collectors;

public class SimuladorGUI extends JFrame {
    private AtencionController controller;

    private JTextArea colaArea;
    private JTextArea pilaArea;
    private JLabel atendiendoAhoraLabel;
    private JLabel metricasLabel;

    private String clienteId = null;
    private String clienteNombre = null;
    private boolean esUrgente = false;
    private JButton btnRegistrar;

    public SimuladorGUI(AtencionController controller) {
        this.controller = controller;
        this.setTitle("Simulador de Turnos (LinkedList) - MVC");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setLayout(new BorderLayout(10, 10));

        JPanel topPanel = createTopPanel();
        JPanel centerPanel = createCenterPanel();
        JPanel bottomPanel = createBottomPanel();

        this.add(topPanel, BorderLayout.NORTH);
        this.add(centerPanel, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);

        updateView();
    }

    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton btnCapturar = new JButton("Capturar datos");
        btnRegistrar = new JButton("Registrar");
        JButton btnAtender = new JButton("Atender");

        btnCapturar.addActionListener(e -> handleCapturar());

        btnRegistrar.addActionListener(e -> handleRegistrar());
        btnAtender.addActionListener(e -> handleAtender());

        atendiendoAhoraLabel = new JLabel("Atendiendo ahora (sin atención en curso)");

        panel.add(btnCapturar);
        panel.add(btnRegistrar);
        panel.add(btnAtender);
        panel.add(atendiendoAhoraLabel);

        btnRegistrar.setEnabled(false);

        return panel;
    }

    private JPanel createCenterPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 10, 0));

        colaArea = new JTextArea("[vacia]");
        colaArea.setBorder(BorderFactory.createTitledBorder("COLA (head->tail)"));
        colaArea.setEditable(false);
        panel.add(new JScrollPane(colaArea));

        pilaArea = new JTextArea("[vacia]");
        pilaArea.setBorder(BorderFactory.createTitledBorder("PILA (top->...)"));
        pilaArea.setEditable(false);
        panel.add(new JScrollPane(pilaArea));

        return panel;
    }

    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 10, 0));

        metricasLabel = new JLabel("Métricas en vivo | Urgentes: 0 Cola: 0 | Máx COLA: 0 Máx PILA: 0");
        panel.add(metricasLabel);

        JTextArea historialArea = new JTextArea("Historial de atenciones (en vivo)");
        historialArea.setEditable(false);
        panel.add(new JScrollPane(historialArea));

        return panel;
    }

    private void handleCapturar() {
        String inputId = null;
        String inputNombre = null;
        boolean idValido = false;

        // Bucle de validación para ID (Numérico, No Vacío, No Repetido)
        while (!idValido) {
            inputId = JOptionPane.showInputDialog(this, "Ingrese ID del cliente (solo números):");

            if (inputId == null) {
                btnRegistrar.setEnabled(false);
                return;
            }

            inputId = inputId.trim();

            if (inputId.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El ID no puede estar vacío.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                continue;
            }

            try {
                Long.parseLong(inputId); // Verifica que sea solo números
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "El ID debe contener solo números.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                continue;
            }

            if (controller.getManager().existeId(inputId)) {
                JOptionPane.showMessageDialog(this, "El ID " + inputId + " ya está registrado en el sistema de turnos.", "Error de Unicidad", JOptionPane.ERROR_MESSAGE);
                continue;
            }

            idValido = true;
        }

        // Bucle de validación para Nombre (No Vacío)
        while (true) {
            inputNombre = JOptionPane.showInputDialog(this, "Ingrese Nombre del cliente:");
            if (inputNombre == null) {
                btnRegistrar.setEnabled(false);
                return;
            }
            if (!inputNombre.trim().isEmpty()) {
                break;
            }
            JOptionPane.showMessageDialog(this, "El Nombre no puede estar vacío. Inténtelo de nuevo.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
        }

        // Si llegamos aquí, los datos son válidos
        clienteId = inputId;
        clienteNombre = inputNombre.trim();

        int opcionUrgente = JOptionPane.showConfirmDialog(this, "¿Es atención urgente?", "Tipo de Turno", JOptionPane.YES_NO_OPTION);
        esUrgente = opcionUrgente == JOptionPane.YES_OPTION;

        btnRegistrar.setEnabled(true);
        JOptionPane.showMessageDialog(this, "Datos capturados.\nCliente: " + clienteNombre + " (ID: " + clienteId + ")" + (esUrgente ? " (URGENTE)" : " (NORMAL)"), "Captura Exitosa", JOptionPane.INFORMATION_MESSAGE);
    }

    private void handleRegistrar() {
        if (clienteId != null) {
            controller.registrarCliente(clienteId, clienteNombre, "", "", esUrgente);

            clienteId = null;
            clienteNombre = null;
            btnRegistrar.setEnabled(false);

            updateView();
        }
    }

    private void handleAtender() {
        controller.atender();
        updateView();
    }

    public void updateView() {
        TurnosManager manager = controller.getManager();

        String colaContent = manager.getColaTurnos().stream()
                .map(Cliente::toString)
                .collect(Collectors.joining("\n"));
        colaArea.setText(manager.getColaTurnos().isEmpty() ? "[vacia]" : colaContent);

        String pilaContent = manager.getPilaUrgentes().stream()
                .map(Cliente::toString)
                .collect(Collectors.joining("\n"));
        pilaArea.setText(manager.getPilaUrgentes().isEmpty() ? "[vacia]" : pilaContent);

        Cliente atendiendo = manager.getClienteEnAtencion();
        atendiendoAhoraLabel.setText("Atendiendo ahora: " + (atendiendo == null ? "(sin atención en curso)" : atendiendo.toString()));

        String metricas = String.format("Urgentes: %d Cola: %d | Máx COLA: %d Máx PILA: %d",
                manager.getAtendidosUrgentes(),
                manager.getAtendidosNormal(),
                manager.getMaxCola(),
                manager.getMaxPila());
        metricasLabel.setText("Métricas en vivo | " + metricas);
    }

    public static void main(String[] args) {
        TurnosManager manager = new TurnosManager();
        AtencionController controller = new AtencionController(manager);
        SwingUtilities.invokeLater(() -> new SimuladorGUI(controller).setVisible(true));
    }
}