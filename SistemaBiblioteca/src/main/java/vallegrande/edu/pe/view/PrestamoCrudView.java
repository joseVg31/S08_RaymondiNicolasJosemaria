package vallegrande.edu.pe.view;

import vallegrande.edu.pe.controller.PrestamoController;
import vallegrande.edu.pe.model.Prestamo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PrestamoCrudView extends JFrame {

    private PrestamoController controller = new PrestamoController();
    private JTable tabla;
    private DefaultTableModel modelo;

    private JTextField txtLibro, txtUsuario, txtFecha;

    public PrestamoCrudView() {
        setTitle("Gestión de Préstamos");
        setSize(820, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(true);

        JPanel raiz = new JPanel(new BorderLayout(0, 0));
        raiz.setBackground(EstiloUI.COLOR_FONDO);

        // ── HEADER ──────────────────────────────────────────────────────────
        raiz.add(EstiloUI.crearHeader("📖  Gestión de Préstamos"), BorderLayout.NORTH);

        // ── PANEL IZQUIERDO: Formulario ──────────────────────────────────────
        JPanel izquierdo = new JPanel();
        izquierdo.setLayout(new BoxLayout(izquierdo, BoxLayout.Y_AXIS));
        izquierdo.setBackground(EstiloUI.COLOR_FONDO);
        izquierdo.setBorder(new EmptyBorder(12, 14, 12, 8));
        izquierdo.setPreferredSize(new Dimension(240, 0));

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(180, 200, 220), 1, true),
                        " Datos del Préstamo ", 0, 0,
                        new Font("Segoe UI", Font.BOLD, 12), new Color(0, 131, 143)),
                new EmptyBorder(8, 10, 10, 10)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(4, 4, 4, 4);

        txtLibro   = EstiloUI.crearTextField();
        txtUsuario = EstiloUI.crearTextField();
        txtFecha   = EstiloUI.crearTextField();
        txtFecha.setToolTipText("Formato recomendado: DD/MM/AAAA");

        String[][] campos = {{"Libro:", "0"}, {"Usuario:", "1"}, {"Fecha:", "2"}};
        JTextField[] fields = {txtLibro, txtUsuario, txtFecha};
        for (int i = 0; i < campos.length; i++) {
            gbc.gridy = Integer.parseInt(campos[i][1]) * 2;
            gbc.gridx = 0;
            form.add(EstiloUI.crearLabel(campos[i][0]), gbc);
            gbc.gridy++;
            form.add(fields[i], gbc);
        }

        // Indicación de formato de fecha
        gbc.gridy = 7;
        JLabel hint = new JLabel("  Ej.: 08/04/2026");
        hint.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        hint.setForeground(new Color(130, 150, 170));
        form.add(hint, gbc);

        izquierdo.add(form);
        raiz.add(izquierdo, BorderLayout.WEST);

        // ── TABLA ────────────────────────────────────────────────────────────
        modelo = new DefaultTableModel(new String[]{"Libro", "Usuario", "Fecha"}, 0) {
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
        JButton btnAgregar  = EstiloUI.crearBoton("➕  Agregar", new Color(0, 131, 143));
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
                        txtLibro.getText().trim(),
                        txtUsuario.getText().trim(),
                        txtFecha.getText().trim()
                );
                limpiarFormulario();
                cargarTabla();
                JOptionPane.showMessageDialog(this, "✅ Préstamo registrado correctamente.",
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
            Prestamo p = controller.listar().get(fila);

            String l = pedirInput("Libro:",   p.getLibro());   if (l == null) return;
            String u = pedirInput("Usuario:", p.getUsuario()); if (u == null) return;
            String f = pedirInput("Fecha:",   p.getFecha());   if (f == null) return;

            if (l.isBlank() || u.isBlank() || f.isBlank()) {
                JOptionPane.showMessageDialog(this, "⚠️ Campos vacíos no permitidos.",
                        "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }
            controller.editar(fila, l.trim(), u.trim(), f.trim());
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
                    "¿Está seguro de eliminar este préstamo?", "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (confirm == JOptionPane.YES_OPTION) {
                controller.eliminar(fila);
                cargarTabla();
            }
        });

        btnLimpiar.addActionListener(e -> limpiarFormulario());
    }

    // ── MÉTODOS AUXILIARES ────────────────────────────────────────────────

    private boolean validar() {
        if (txtLibro.getText().isBlank() ||
                txtUsuario.getText().isBlank() ||
                txtFecha.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "⚠️ Complete todos los campos.",
                    "Validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        // Validación mínima de fecha (no vacía)
        String fecha = txtFecha.getText().trim();
        if (fecha.length() < 6) {
            JOptionPane.showMessageDialog(this,
                    "⚠️ Ingrese una fecha válida (Ej.: 08/04/2026).",
                    "Validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private void limpiarFormulario() {
        txtLibro.setText(""); txtUsuario.setText(""); txtFecha.setText("");
    }

    private void cargarTabla() {
        modelo.setRowCount(0);
        for (Prestamo p : controller.listar()) {
            modelo.addRow(new Object[]{p.getLibro(), p.getUsuario(), p.getFecha()});
        }
    }

    private String pedirInput(String campo, String valorActual) {
        return JOptionPane.showInputDialog(this, campo, valorActual);
    }
}