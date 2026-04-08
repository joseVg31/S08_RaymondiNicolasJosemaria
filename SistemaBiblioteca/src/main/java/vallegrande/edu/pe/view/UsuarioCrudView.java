package vallegrande.edu.pe.view;

import vallegrande.edu.pe.controller.UsuarioController;
import vallegrande.edu.pe.model.Usuario;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class UsuarioCrudView extends JFrame {

    private UsuarioController controller = new UsuarioController();
    private JTable tabla;
    private DefaultTableModel modelo;

    private JTextField txtNombre, txtApellido, txtEmail, txtTelefono;

    public UsuarioCrudView() {
        setTitle("Gestión de Usuarios");
        setSize(860, 540);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(true);

        JPanel raiz = new JPanel(new BorderLayout(0, 0));
        raiz.setBackground(EstiloUI.COLOR_FONDO);

        // ── HEADER ──────────────────────────────────────────────────────────
        raiz.add(EstiloUI.crearHeader("👤  Gestión de Usuarios"), BorderLayout.NORTH);

        // ── PANEL IZQUIERDO: Formulario ──────────────────────────────────────
        JPanel izquierdo = new JPanel();
        izquierdo.setLayout(new BoxLayout(izquierdo, BoxLayout.Y_AXIS));
        izquierdo.setBackground(EstiloUI.COLOR_FONDO);
        izquierdo.setBorder(new EmptyBorder(12, 14, 12, 8));
        izquierdo.setPreferredSize(new Dimension(250, 0));

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(180, 200, 220), 1, true),
                        " Datos del Usuario ", 0, 0,
                        new Font("Segoe UI", Font.BOLD, 12), EstiloUI.COLOR_EXITO),
                new EmptyBorder(8, 10, 10, 10)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(4, 4, 4, 4);

        txtNombre   = EstiloUI.crearTextField();
        txtApellido = EstiloUI.crearTextField();
        txtEmail    = EstiloUI.crearTextField();
        txtTelefono = EstiloUI.crearTextField();

        String[][] campos = {{"Nombre:", "0"}, {"Apellido:", "1"}, {"Email:", "2"}, {"Teléfono:", "3"}};
        JTextField[] fields = {txtNombre, txtApellido, txtEmail, txtTelefono};
        for (int i = 0; i < campos.length; i++) {
            gbc.gridy = Integer.parseInt(campos[i][1]) * 2;
            gbc.gridx = 0;
            form.add(EstiloUI.crearLabel(campos[i][0]), gbc);
            gbc.gridy++;
            form.add(fields[i], gbc);
        }

        izquierdo.add(form);
        raiz.add(izquierdo, BorderLayout.WEST);

        // ── TABLA ────────────────────────────────────────────────────────────
        modelo = new DefaultTableModel(new String[]{"Nombre", "Apellido", "Email", "Teléfono"}, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla = new JTable(modelo);
        EstiloUI.estilizarTabla(tabla);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        JPanel centroPanel = new JPanel(new BorderLayout());
        centroPanel.setBorder(BorderFactory.createCompoundBorder(
                new EmptyBorder(12, 0, 0, 12),
                BorderFactory.createLineBorder(new Color(200, 215, 230), 1, true)
        ));
        centroPanel.add(scroll, BorderLayout.CENTER);
        raiz.add(centroPanel, BorderLayout.CENTER);

        // ── BOTONES ──────────────────────────────────────────────────────────
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 10));
        botones.setBackground(EstiloUI.COLOR_FONDO);
        botones.setBorder(new EmptyBorder(0, 10, 6, 10));

        JButton btnVolver   = EstiloUI.crearBoton("⬅  Menú",    new Color(90, 110, 130));
        JButton btnAgregar  = EstiloUI.crearBoton("➕  Agregar", EstiloUI.COLOR_EXITO);
        JButton btnEditar   = EstiloUI.crearBoton("✏️  Editar",  EstiloUI.COLOR_SECUNDARIO);
        JButton btnEliminar = EstiloUI.crearBoton("🗑️  Eliminar", EstiloUI.COLOR_PELIGRO);
        JButton btnLimpiar  = EstiloUI.crearBoton("🔄  Limpiar", new Color(100, 120, 140));

        botones.add(btnVolver);
        botones.add(btnAgregar);
        botones.add(btnEditar);
        botones.add(btnEliminar);
        botones.add(btnLimpiar);
        raiz.add(botones, BorderLayout.SOUTH);

        add(raiz);

        // ── EVENTOS ──────────────────────────────────────────────────────────

        btnVolver.addActionListener(e -> {
            Point pos    = getLocation();
            Dimension sz = getSize();
            MiniPaginaView menu = new MiniPaginaView();
            menu.setLocation(pos);
            menu.setSize(sz);
            menu.setVisible(true);
            dispose();
        });

        btnAgregar.addActionListener(e -> {
            if (validar()) {
                controller.agregar(
                        txtNombre.getText().trim(),
                        txtApellido.getText().trim(),
                        txtEmail.getText().trim(),
                        txtTelefono.getText().trim()
                );
                limpiarFormulario();
                cargarTabla();
                JOptionPane.showMessageDialog(this, "✅ Usuario agregado correctamente.",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        btnEditar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila < 0) {
                JOptionPane.showMessageDialog(this, "⚠️ Seleccione un registro para editar.",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Usuario u = controller.listar().get(fila);

            String n  = pedirInput("Nombre:",    u.getNombre());    if (n  == null) return;
            String a  = pedirInput("Apellido:",  u.getApellido()); if (a  == null) return;
            String em = pedirInput("Email:",     u.getEmail());     if (em == null) return;
            String t  = pedirInput("Teléfono:",  u.getTelefono()); if (t  == null) return;

            if (n.isBlank() || a.isBlank() || em.isBlank() || t.isBlank()) {
                JOptionPane.showMessageDialog(this, "⚠️ Campos vacíos no permitidos.",
                        "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!em.contains("@")) {
                JOptionPane.showMessageDialog(this, "⚠️ El email no parece válido.",
                        "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }

            controller.editar(fila, n.trim(), a.trim(), em.trim(), t.trim());
            cargarTabla();
        });

        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila < 0) {
                JOptionPane.showMessageDialog(this, "⚠️ Seleccione un registro para eliminar.",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro de eliminar este usuario?", "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (confirm == JOptionPane.YES_OPTION) {
                controller.eliminar(fila);
                cargarTabla();
            }
        });

        btnLimpiar.addActionListener(e -> limpiarFormulario());

        // Al hacer clic en una fila, cargar datos en el formulario
        tabla.getSelectionModel().addListSelectionListener(ev -> {
            if (!ev.getValueIsAdjusting()) {
                int fila = tabla.getSelectedRow();
                if (fila >= 0 && fila < controller.listar().size()) {
                    Usuario u = controller.listar().get(fila);
                    txtNombre.setText(u.getNombre());
                    txtApellido.setText(u.getApellido());
                    txtEmail.setText(u.getEmail());
                    txtTelefono.setText(u.getTelefono());
                }
            }
        });
    }

    // ── MÉTODOS AUXILIARES ────────────────────────────────────────────────

    private boolean validar() {
        if (txtNombre.getText().isBlank() || txtApellido.getText().isBlank() ||
                txtEmail.getText().isBlank()  || txtTelefono.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "⚠️ Complete todos los campos.",
                    "Validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (!txtEmail.getText().contains("@")) {
            JOptionPane.showMessageDialog(this,
                    "⚠️ Ingrese un email válido (debe contener @).",
                    "Validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private void limpiarFormulario() {
        txtNombre.setText(""); txtApellido.setText("");
        txtEmail.setText(""); txtTelefono.setText("");
        tabla.clearSelection();
    }

    private void cargarTabla() {
        modelo.setRowCount(0);
        for (Usuario u : controller.listar()) {
            modelo.addRow(new Object[]{u.getNombre(), u.getApellido(), u.getEmail(), u.getTelefono()});
        }
    }

    private String pedirInput(String campo, String valorActual) {
        return JOptionPane.showInputDialog(this, campo, valorActual);
    }
}