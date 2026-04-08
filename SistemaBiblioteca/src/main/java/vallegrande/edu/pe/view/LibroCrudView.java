package vallegrande.edu.pe.view;

import vallegrande.edu.pe.controller.LibroController;
import vallegrande.edu.pe.model.Libro;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class LibroCrudView extends JFrame {

    private LibroController controller = new LibroController();
    private JTable tabla;
    private DefaultTableModel modelo;

    private JTextField txtTitulo, txtAutor, txtCategoria, txtEstado, txtBuscar;

    public LibroCrudView() {
        setTitle("Gestión de Libros");
        setSize(820, 560);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(true);

        JPanel raiz = new JPanel(new BorderLayout(0, 0));
        raiz.setBackground(EstiloUI.COLOR_FONDO);

        // ── HEADER ──────────────────────────────────────────────────────────
        raiz.add(EstiloUI.crearHeader("📚  Gestión de Libros"), BorderLayout.NORTH);

        // ── PANEL IZQUIERDO: Formulario + Búsqueda ───────────────────────────
        JPanel izquierdo = new JPanel();
        izquierdo.setLayout(new BoxLayout(izquierdo, BoxLayout.Y_AXIS));
        izquierdo.setBackground(EstiloUI.COLOR_FONDO);
        izquierdo.setBorder(new EmptyBorder(12, 14, 12, 8));
        izquierdo.setPreferredSize(new Dimension(250, 0));

        // Formulario
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(180, 200, 220), 1, true),
                        " Datos del Libro ", 0, 0,
                        new Font("Segoe UI", Font.BOLD, 12), EstiloUI.COLOR_PRIMARIO),
                new EmptyBorder(8, 10, 10, 10)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.weightx = 1.0;

        txtTitulo    = EstiloUI.crearTextField();
        txtAutor     = EstiloUI.crearTextField();
        txtCategoria = EstiloUI.crearTextField();
        txtEstado    = EstiloUI.crearTextField();

        String[][] campos = {{"Título:", "0"}, {"Autor:", "1"}, {"Categoría:", "2"}, {"Estado:", "3"}};
        JTextField[] fields = {txtTitulo, txtAutor, txtCategoria, txtEstado};
        for (int i = 0; i < campos.length; i++) {
            gbc.gridy = Integer.parseInt(campos[i][1]) * 2;
            gbc.gridx = 0;
            form.add(EstiloUI.crearLabel(campos[i][0]), gbc);
            gbc.gridy++;
            form.add(fields[i], gbc);
        }

        // Búsqueda
        JPanel panelBuscar = new JPanel(new GridBagLayout());
        panelBuscar.setBackground(Color.WHITE);
        panelBuscar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(180, 200, 220), 1, true),
                        " 🔍 Buscar ", 0, 0,
                        new Font("Segoe UI", Font.BOLD, 12), EstiloUI.COLOR_SECUNDARIO),
                new EmptyBorder(8, 10, 10, 10)
        ));
        GridBagConstraints gbcB = new GridBagConstraints();
        gbcB.fill = GridBagConstraints.HORIZONTAL;
        gbcB.weightx = 1.0;
        gbcB.insets = new Insets(3, 3, 3, 3);

        gbcB.gridy = 0;
        panelBuscar.add(EstiloUI.crearLabel("Título o Autor:"), gbcB);
        gbcB.gridy = 1;
        txtBuscar = EstiloUI.crearTextField();
        txtBuscar.setToolTipText("Filtra en tiempo real por título o autor");
        panelBuscar.add(txtBuscar, gbcB);
        gbcB.gridy = 2;
        gbcB.insets = new Insets(6, 3, 3, 3);
        JButton btnLimpiarBusqueda = EstiloUI.crearBoton("✖ Limpiar", new Color(120, 140, 160));
        panelBuscar.add(btnLimpiarBusqueda, gbcB);

        izquierdo.add(form);
        izquierdo.add(Box.createVerticalStrut(10));
        izquierdo.add(panelBuscar);

        raiz.add(izquierdo, BorderLayout.WEST);

        // ── TABLA ────────────────────────────────────────────────────────────
        modelo = new DefaultTableModel(new String[]{"Título", "Autor", "Categoría", "Estado"}, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla = new JTable(modelo);
        EstiloUI.estilizarTabla(tabla);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.setBackground(Color.WHITE);
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
        JButton btnAgregar  = EstiloUI.crearBoton("➕  Agregar", EstiloUI.COLOR_PRIMARIO);
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

        // Búsqueda en tiempo real
        txtBuscar.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e)  { filtrarTabla(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e)  { filtrarTabla(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filtrarTabla(); }
        });

        btnLimpiarBusqueda.addActionListener(e -> {
            txtBuscar.setText("");
            cargarTabla();
        });

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
                        txtTitulo.getText().trim(),
                        txtAutor.getText().trim(),
                        txtCategoria.getText().trim(),
                        txtEstado.getText().trim()
                );
                limpiarFormulario();
                cargarTabla();
                JOptionPane.showMessageDialog(this, "✅ Libro agregado correctamente.",
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
            // Convertir índice de fila visible al índice real si hay filtro
            int indiceReal = obtenerIndiceReal(fila);
            if (indiceReal < 0) return;

            Libro l = controller.listar().get(indiceReal);

            String t  = pedirInput("Título:",    l.getTitulo());    if (t  == null) return;
            String a  = pedirInput("Autor:",     l.getAutor());     if (a  == null) return;
            String c  = pedirInput("Categoría:", l.getCategoria()); if (c  == null) return;
            String es = pedirInput("Estado:",    l.getEstado());    if (es == null) return;

            if (t.isBlank() || a.isBlank() || c.isBlank() || es.isBlank()) {
                JOptionPane.showMessageDialog(this, "⚠️ Campos vacíos no permitidos.",
                        "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }
            controller.editar(indiceReal, t.trim(), a.trim(), c.trim(), es.trim());
            cargarTabla();
        });

        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila < 0) {
                JOptionPane.showMessageDialog(this, "⚠️ Seleccione un registro para eliminar.",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int indiceReal = obtenerIndiceReal(fila);
            if (indiceReal < 0) return;

            int confirm = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro de eliminar este libro?", "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (confirm == JOptionPane.YES_OPTION) {
                controller.eliminar(indiceReal);
                txtBuscar.setText("");
                cargarTabla();
            }
        });

        btnLimpiar.addActionListener(e -> limpiarFormulario());
    }

    // ── MÉTODOS AUXILIARES ────────────────────────────────────────────────

    private boolean validar() {
        if (txtTitulo.getText().isBlank() || txtAutor.getText().isBlank() ||
                txtCategoria.getText().isBlank() || txtEstado.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "⚠️ Complete todos los campos.",
                    "Validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private void limpiarFormulario() {
        txtTitulo.setText(""); txtAutor.setText("");
        txtCategoria.setText(""); txtEstado.setText("");
    }

    private void cargarTabla() {
        modelo.setRowCount(0);
        for (Libro l : controller.listar()) {
            modelo.addRow(new Object[]{l.getTitulo(), l.getAutor(), l.getCategoria(), l.getEstado()});
        }
    }

    private void filtrarTabla() {
        String filtro = txtBuscar.getText().trim().toLowerCase();
        modelo.setRowCount(0);
        for (Libro l : controller.listar()) {
            if (l.getTitulo().toLowerCase().contains(filtro) ||
                    l.getAutor().toLowerCase().contains(filtro)) {
                modelo.addRow(new Object[]{l.getTitulo(), l.getAutor(), l.getCategoria(), l.getEstado()});
            }
        }
    }

    /** Devuelve el índice real en la lista del controlador para la fila visible. */
    private int obtenerIndiceReal(int filaVisible) {
        String titulo = (String) modelo.getValueAt(filaVisible, 0);
        String autor  = (String) modelo.getValueAt(filaVisible, 1);
        List<Libro> lista = controller.listar();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getTitulo().equals(titulo) &&
                    lista.get(i).getAutor().equals(autor)) {
                return i;
            }
        }
        return -1;
    }

    private String pedirInput(String campo, String valorActual) {
        return JOptionPane.showInputDialog(this, campo, valorActual);
    }
}