package vallegrande.edu.pe.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Clase utilitaria con estilos visuales compartidos para toda la aplicación.
 * Garantiza consistencia visual en todas las vistas.
 */
public class EstiloUI {

    // ── Paleta de colores ──────────────────────────────────────────────────
    public static final Color COLOR_PRIMARIO      = new Color(13, 71, 161);   // azul oscuro
    public static final Color COLOR_SECUNDARIO    = new Color(25, 118, 210);  // azul medio
    public static final Color COLOR_ACENTO        = new Color(66, 165, 245);  // azul claro
    public static final Color COLOR_EXITO         = new Color(46, 125, 50);   // verde
    public static final Color COLOR_PELIGRO       = new Color(183, 28, 28);   // rojo
    public static final Color COLOR_ADVERTENCIA   = new Color(230, 81, 0);    // naranja
    public static final Color COLOR_FONDO         = new Color(236, 239, 241); // gris muy claro
    public static final Color COLOR_FONDO_PANEL   = Color.WHITE;
    public static final Color COLOR_TEXTO_HEADER  = Color.WHITE;
    public static final Color COLOR_FOOTER        = new Color(200, 210, 220);
    public static final Color COLOR_FILA_PAR      = new Color(232, 240, 254);
    public static final Color COLOR_FILA_IMPAR    = Color.WHITE;
    public static final Color COLOR_SELECCION     = new Color(187, 222, 251);

    // ── Fuentes ────────────────────────────────────────────────────────────
    public static final Font FUENTE_HEADER   = new Font("Segoe UI", Font.BOLD, 22);
    public static final Font FUENTE_TITULO   = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font FUENTE_NORMAL   = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FUENTE_BOTON    = new Font("Segoe UI", Font.BOLD, 13);
    public static final Font FUENTE_TABLA    = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FUENTE_CABECERA = new Font("Segoe UI", Font.BOLD, 13);

    // ── Dimensiones ───────────────────────────────────────────────────────
    public static final int ALTO_FILA     = 30;
    public static final int ALTO_HEADER   = 70;
    public static final int ALTO_FOOTER   = 32;
    public static final int RADIO_BORDE   = 10;

    /**
     * Crea un botón estilizado con el color y texto indicados.
     */
    public static JButton crearBoton(String texto, Color fondo) {
        JButton btn = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isPressed() ? fondo.darker() :
                        getModel().isRollover() ? fondo.brighter() : fondo);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), RADIO_BORDE, RADIO_BORDE);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setForeground(Color.WHITE);
        btn.setFont(FUENTE_BOTON);
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(new EmptyBorder(8, 18, 8, 18));
        return btn;
    }

    /**
     * Crea el panel de encabezado con degradado.
     */
    public static JPanel crearHeader(String texto) {
        JPanel header = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(
                        0, 0, COLOR_PRIMARIO,
                        getWidth(), 0, COLOR_SECUNDARIO
                );
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };
        header.setPreferredSize(new Dimension(0, ALTO_HEADER));

        JLabel lbl = new JLabel(texto, SwingConstants.CENTER);
        lbl.setForeground(COLOR_TEXTO_HEADER);
        lbl.setFont(FUENTE_HEADER);
        lbl.setBorder(new EmptyBorder(0, 20, 0, 20));
        header.add(lbl, BorderLayout.CENTER);
        return header;
    }

    /**
     * Aplica estilos a una JTable.
     */
    public static void estilizarTabla(JTable tabla) {
        tabla.setFont(FUENTE_TABLA);
        tabla.setRowHeight(ALTO_FILA);
        tabla.setGridColor(new Color(220, 230, 240));
        tabla.setShowGrid(true);
        tabla.setSelectionBackground(COLOR_SELECCION);
        tabla.setSelectionForeground(COLOR_PRIMARIO);
        tabla.setIntercellSpacing(new Dimension(1, 1));
        tabla.getTableHeader().setFont(FUENTE_CABECERA);
        tabla.getTableHeader().setBackground(COLOR_SECUNDARIO);
        tabla.getTableHeader().setForeground(Color.WHITE);
        tabla.getTableHeader().setPreferredSize(new Dimension(0, 34));
        tabla.getTableHeader().setReorderingAllowed(false);
        tabla.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object v,
                                                           boolean sel, boolean foc, int row, int col) {
                Component c = super.getTableCellRendererComponent(t, v, sel, foc, row, col);
                if (!sel) {
                    c.setBackground(row % 2 == 0 ? COLOR_FILA_PAR : COLOR_FILA_IMPAR);
                }
                ((JLabel) c).setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
                return c;
            }
        });
    }

    /**
     * Crea un campo de texto estilizado.
     */
    public static JTextField crearTextField() {
        JTextField tf = new JTextField();
        tf.setFont(FUENTE_NORMAL);
        tf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 200, 220), 1, true),
                new EmptyBorder(5, 8, 5, 8)
        ));
        return tf;
    }

    /**
     * Crea una etiqueta de formulario.
     */
    public static JLabel crearLabel(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(FUENTE_TITULO);
        lbl.setForeground(new Color(40, 60, 90));
        return lbl;
    }
}