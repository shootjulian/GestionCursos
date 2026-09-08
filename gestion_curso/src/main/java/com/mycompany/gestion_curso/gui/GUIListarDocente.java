package com.mycompany.gestion_curso.gui;

import com.mycompany.gestion_curso.model.Docente;
import com.mycompany.gestion_curso.servicios.ServicioDocente;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * Ventana sencilla para listar docentes.
 * Se tomó la funcionalidad que estaba adelantada en la otra versión,
 * pero se corrigió para trabajar con Docente y ServicioDocente.
 */
public class GUIListarDocente extends JFrame {

    private final JTable tblDocentes;

    public GUIListarDocente() {
        setTitle("Listar docentes");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(650, 430);
        setLocationRelativeTo(null);

        DefaultTableModel modelo = new DefaultTableModel(
                new Object[]{"Código", "Nombre", "Salario", "Planta", "Estado"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblDocentes = new JTable(modelo);
        add(new JScrollPane(tblDocentes), BorderLayout.CENTER);

        JButton btnSalir = new JButton("Salir");
        JButton btnListar = new JButton("Listar");

        btnSalir.addActionListener(e -> dispose());
        btnListar.addActionListener(e -> listarDocentes());

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.add(btnSalir);
        panelBotones.add(btnListar);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private void listarDocentes() {
        try {
            List<Docente> docentes = ServicioDocente.listarDocentes();
            DefaultTableModel modelo = (DefaultTableModel) tblDocentes.getModel();
            modelo.setRowCount(0);

            for (Docente docente : docentes) {
                modelo.addRow(new Object[]{
                    docente.getCodigoDocente(),
                    docente.getNombre(),
                    docente.getSalario(),
                    docente.isPlanta() ? "Sí" : "No",
                    docente.getEstado()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
}
