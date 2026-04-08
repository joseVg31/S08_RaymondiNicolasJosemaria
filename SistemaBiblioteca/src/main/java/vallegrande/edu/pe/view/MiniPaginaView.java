package vallegrande.edu.pe.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MiniPaginaView extends JFrame {

    public MiniPaginaView() {
        setTitle("Sistema de Biblioteca - Portal Principal");
        setSize(680, 480);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        setMinimumSize(new Dimension(520, 400));

        JPanel raiz = new JPanel(new BorderLayout());
        raiz.setBackground(EstiloUI.COLOR_FONDO);

        raiz.add(crearHeaderPersonalizado("Sistema de Biblioteca"), BorderLayout.NORTH);

        JPanel centro = new JPanel();
        centro.setBackground(EstiloUI.COLOR_FONDO);
        centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));
        centro.setBorder(new EmptyBorder(20, 60, 20, 60));

        JPanel logo = crearLogoBiblioteca();
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitulo = new JLabel("Panel Principal");
        subtitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        subtitulo.setForeground(EstiloUI.COLOR_PRIMARIO);
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel desc1 = new JLabel("Gestione libros, prestamos y usuarios facilmente");
        desc1.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        desc1.setForeground(new Color(80, 100, 120));
        desc1.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel desc2 = new JLabel("Sistema desarrollado con Java Swing  -  MVC");
        desc2.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        desc2.setForeground(new Color(120, 140, 160));
        desc2.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel tarjetas = new JPanel(new GridLayout(1, 3, 18, 0));
        tarjetas.setOpaque(true);
        tarjetas.setAlignmentX(Component.CENTER_ALIGNMENT);
        tarjetas.setMaximumSize(new Dimension(Integer.MAX_VALUE, 155));

        // ── TARJETA LIBROS ────────────────────────────────────────────────────
        tarjetas.add(crearTarjeta(
                crearIconoLibros(EstiloUI.COLOR_PRIMARIO),
                "Libros", "Catalogo completo",
                EstiloUI.COLOR_PRIMARIO,
                e -> {
                    Point pos   = getLocation();
                    Dimension sz = getSize();
                    LibroCrudView v = new LibroCrudView();
                    v.setLocation(pos);
                    v.setSize(sz);
                    v.setVisible(true);
                    dispose();
                }));

        // ── TARJETA PRESTAMOS ─────────────────────────────────────────────────
        tarjetas.add(crearTarjeta(
                crearIconoPrestamos(new Color(0, 131, 143)),
                "Prestamos", "Control de prestamos",
                new Color(0, 131, 143),
                e -> {
                    Point pos   = getLocation();
                    Dimension sz = getSize();
                    PrestamoCrudView v = new PrestamoCrudView();
                    v.setLocation(pos);
                    v.setSize(sz);
                    v.setVisible(true);
                    dispose();
                }));

        // ── TARJETA USUARIOS ──────────────────────────────────────────────────
        tarjetas.add(crearTarjeta(
                crearIconoUsuario(EstiloUI.COLOR_EXITO),
                "Usuarios", "Gestion de usuarios",
                EstiloUI.COLOR_EXITO,
                e -> {
                    Point pos   = getLocation();
                    Dimension sz = getSize();
                    UsuarioCrudView v = new UsuarioCrudView();
                    v.setLocation(pos);
                    v.setSize(sz);
                    v.setVisible(true);
                    dispose();
                }));

        centro.add(logo);
        centro.add(Box.createVerticalStrut(8));
        centro.add(subtitulo);
        centro.add(Box.createVerticalStrut(4));
        centro.add(desc1);
        centro.add(desc2);
        centro.add(Box.createVerticalStrut(22));
        centro.add(tarjetas);

        raiz.add(centro, BorderLayout.CENTER);

        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footer.setBackground(EstiloUI.COLOR_FOOTER);
        footer.setPreferredSize(new Dimension(0, EstiloUI.ALTO_FOOTER));
        JLabel copy = new JLabel("(c) 2026 Sistema de Biblioteca  -  Valle Grande");
        copy.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        copy.setForeground(new Color(90, 110, 130));
        footer.add(copy);
        raiz.add(footer, BorderLayout.SOUTH);

        add(raiz);
    }

    // ── Método estático utilitario para abrir el menú desde las vistas hijas ──
    /**
     * Llama este método en el botón "Menú" de LibroCrudView,
     * PrestamoCrudView y UsuarioCrudView para conservar posición y tamaño.
     *
     * Ejemplo de uso en vista hija:
     *   btnMenu.addActionListener(e -> MiniPaginaView.abrirDesdeHija(this));
     */
    public static void abrirDesdeHija(JFrame vistaHija) {
        Point pos    = vistaHija.getLocation();
        Dimension sz = vistaHija.getSize();
        MiniPaginaView menu = new MiniPaginaView();
        menu.setLocation(pos);
        menu.setSize(sz);
        menu.setVisible(true);
        vistaHija.dispose();
    }

    // ── HEADER ───────────────────────────────────────────────────────────────
    private JPanel crearHeaderPersonalizado(String texto) {
        JPanel header = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(
                        0, 0, EstiloUI.COLOR_PRIMARIO,
                        getWidth(), 0, EstiloUI.COLOR_SECUNDARIO);
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());

                int cx = getWidth() / 2 - 145;
                int cy = getHeight() / 2;
                Color[] cs = { new Color(255,255,255,180), new Color(255,255,255,220), new Color(255,255,255,255) };
                int[][] ps = {{cx, cy+4}, {cx+5, cy-4}, {cx+10, cy-12}};
                int[] ws   = {30, 24, 18};
                for (int i = 0; i < 3; i++) {
                    g2.setColor(cs[i]);
                    g2.fillRoundRect(ps[i][0], ps[i][1], ws[i], 10, 3, 3);
                    g2.setColor(new Color(0,0,0,30));
                    g2.fillRoundRect(ps[i][0], ps[i][1], 4, 10, 2, 2);
                }
                g2.setColor(new Color(255, 215, 60));
                g2.setStroke(new BasicStroke(2.2f));
                g2.drawOval(cx+32, cy-14, 12, 12);
                g2.setStroke(new BasicStroke(2.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2.drawLine(cx+43, cy-3, cx+49, cy+3);

                g2.dispose();
            }
        };
        header.setPreferredSize(new Dimension(0, EstiloUI.ALTO_HEADER));
        JLabel lbl = new JLabel(texto, SwingConstants.CENTER);
        lbl.setForeground(Color.WHITE);
        lbl.setFont(EstiloUI.FUENTE_HEADER);
        header.add(lbl, BorderLayout.CENTER);
        return header;
    }

    // ── LOGO ─────────────────────────────────────────────────────────────────
    private JPanel crearLogoBiblioteca() {
        JPanel logo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int cx = getWidth() / 2;
                int cy = getHeight() / 2;

                g2.setColor(new Color(13, 71, 161, 22));
                g2.fillOval(cx-52, cy-52, 104, 104);
                g2.setColor(new Color(13, 71, 161, 12));
                g2.fillOval(cx-40, cy-40, 80, 80);

                Color[] cols = { new Color(13,71,161), new Color(25,118,210), new Color(66,165,245) };
                int[][] pos  = {{cx-26,cy+4},{cx-20,cy-8},{cx-14,cy-20}};
                int[] ws     = {50, 40, 30};
                for (int i = 0; i < 3; i++) {
                    g2.setColor(new Color(0,0,0,22));
                    g2.fillRoundRect(pos[i][0]+2, pos[i][1]+2, ws[i], 13, 4, 4);
                    g2.setColor(cols[i]);
                    g2.fillRoundRect(pos[i][0], pos[i][1], ws[i], 13, 4, 4);
                    g2.setColor(cols[i].darker());
                    g2.fillRoundRect(pos[i][0], pos[i][1], 6, 13, 2, 2);
                    g2.setColor(new Color(255,255,255,120));
                    g2.setStroke(new BasicStroke(1.1f));
                    g2.drawLine(pos[i][0]+10, pos[i][1]+4, pos[i][0]+ws[i]-4, pos[i][1]+4);
                    g2.drawLine(pos[i][0]+10, pos[i][1]+9, pos[i][0]+ws[i]-8, pos[i][1]+9);
                }

                g2.setColor(new Color(255, 193, 7));
                g2.setStroke(new BasicStroke(2.8f));
                g2.drawOval(cx+14, cy-30, 18, 18);
                g2.setStroke(new BasicStroke(3f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2.drawLine(cx+30, cy-13, cx+38, cy-5);

                g2.dispose();
            }
            @Override public Dimension getPreferredSize() { return new Dimension(120, 100); }
        };
        logo.setOpaque(false);
        return logo;
    }

    // ── ICONO: LIBROS ─────────────────────────────────────────────────────────
    private JPanel crearIconoLibros(Color color) {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int cx = getWidth() / 2;
                int cy = getHeight() / 2;
                Color[] cs = { color.darker(), color, color.brighter() };
                int[][] ps = {{cx-19,cy+4},{cx-14,cy-6},{cx-9,cy-16}};
                int[] ws   = {36, 28, 20};
                for (int i = 0; i < 3; i++) {
                    g2.setColor(new Color(0,0,0,20));
                    g2.fillRoundRect(ps[i][0]+2, ps[i][1]+2, ws[i], 11, 3, 3);
                    g2.setColor(cs[i]);
                    g2.fillRoundRect(ps[i][0], ps[i][1], ws[i], 11, 3, 3);
                    g2.setColor(cs[i].darker());
                    g2.fillRoundRect(ps[i][0], ps[i][1], 5, 11, 2, 2);
                    g2.setColor(new Color(255,255,255,130));
                    g2.setStroke(new BasicStroke(1f));
                    g2.drawLine(ps[i][0]+8, ps[i][1]+3, ps[i][0]+ws[i]-3, ps[i][1]+3);
                    g2.drawLine(ps[i][0]+8, ps[i][1]+7, ps[i][0]+ws[i]-6, ps[i][1]+7);
                }
                g2.dispose();
            }
            @Override public Dimension getPreferredSize() { return new Dimension(50, 44); }
        };
    }

    // ── ICONO: PRESTAMOS ─────────────────────────────────────────────────────
    private JPanel crearIconoPrestamos(Color color) {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int cx = getWidth() / 2;
                int cy = getHeight() / 2 + 2;

                g2.setColor(new Color(0,0,0,18));
                g2.fillRoundRect(cx-19+2, cy-14+2, 17, 28, 4, 4);
                g2.fillRoundRect(cx+2+2,  cy-14+2, 17, 28, 4, 4);

                g2.setColor(color.darker());
                g2.fillRoundRect(cx-19, cy-14, 17, 28, 4, 4);
                g2.setColor(color);
                g2.fillRoundRect(cx+2, cy-14, 17, 28, 4, 4);

                g2.setColor(new Color(0,0,0,50));
                g2.setStroke(new BasicStroke(2f));
                g2.drawLine(cx, cy-14, cx, cy+14);

                g2.setColor(new Color(255,255,255,150));
                g2.setStroke(new BasicStroke(1.2f));
                for (int y = cy-8; y <= cy+8; y += 5) {
                    g2.drawLine(cx-16, y, cx-4, y);
                    g2.drawLine(cx+4,  y, cx+16, y);
                }
                g2.dispose();
            }
            @Override public Dimension getPreferredSize() { return new Dimension(50, 44); }
        };
    }

    // ── ICONO: USUARIO ───────────────────────────────────────────────────────
    private JPanel crearIconoUsuario(Color color) {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int cx = getWidth() / 2;
                int cy = getHeight() / 2;

                g2.setColor(new Color(0,0,0,18));
                g2.fillOval(cx-8+1, cy-20+1, 16, 16);
                g2.setColor(color);
                g2.fillOval(cx-8, cy-20, 16, 16);

                g2.setColor(new Color(0,0,0,18));
                g2.fillRoundRect(cx-14+1, cy+1, 28, 14, 10, 10);
                g2.setColor(color.darker());
                g2.fillRoundRect(cx-14, cy, 28, 14, 10, 10);

                g2.setColor(color);
                g2.fillRect(cx-4, cy-5, 8, 7);

                g2.dispose();
            }
            @Override public Dimension getPreferredSize() { return new Dimension(50, 44); }
        };
    }

    // ── TARJETA ───────────────────────────────────────────────────────────────
    private JPanel crearTarjeta(JPanel iconoPanel, String titulo, String desc,
                                Color color, java.awt.event.ActionListener accion) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(0,0,0,16));
                g2.fillRoundRect(3, 3, getWidth()-3, getHeight()-3, 14, 14);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 14, 14);
                g2.setColor(new Color(200, 215, 230));
                g2.setStroke(new BasicStroke(1.2f));
                g2.drawRoundRect(0, 0, getWidth()-2, getHeight()-2, 14, 14);
                g2.setColor(color);
                g2.fillRoundRect(0, 0, getWidth()-1, 7, 7, 7);
                g2.fillRect(0, 4, getWidth()-1, 4);
                g2.dispose();
            }
        };
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setOpaque(true);
        card.setPreferredSize(new Dimension(150, 145));
        card.setBorder(new EmptyBorder(16, 12, 12, 12));
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        iconoPanel.setOpaque(false);
        iconoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblTitulo = new JLabel(titulo, SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTitulo.setForeground(color);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblDesc = new JLabel("<html><center>" + desc + "</center></html>", SwingConstants.CENTER);
        lblDesc.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblDesc.setForeground(new Color(100, 120, 140));
        lblDesc.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btn = EstiloUI.crearBoton("Abrir", color);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.addActionListener(accion);

        card.add(iconoPanel);
        card.add(Box.createVerticalStrut(4));
        card.add(lblTitulo);
        card.add(Box.createVerticalStrut(3));
        card.add(lblDesc);
        card.add(Box.createVerticalStrut(8));
        card.add(btn);

        return card;
    }
}