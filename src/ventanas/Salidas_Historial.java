/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ventanas;

import clases.Conexion;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author mb
 */
public class Salidas_Historial extends javax.swing.JFrame {

    DefaultTableModel modelo_productos;
    DefaultTableModel modelo_entradas;
    DefaultTableModel modelo_proveedores;
    DefaultTableModel modelo_detalle;
    DefaultTableModel modelo_historial;

    public int id_entrada;

    DecimalFormat d = new DecimalFormat("####.##");
    Locale region = Locale.getDefault();
    NumberFormat formato = NumberFormat.getCurrencyInstance(region);
    NumberFormat format = NumberFormat.getCurrencyInstance();

    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy'T'HH:mm");

    /**
     * Creates new form Entradas_Historial
     */
    public Salidas_Historial() {
        initComponents();
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        Date date = new Date();
        jDateChooser_desde.setDate(date);
        jDateChooser_hasta.setDate(date);
        actualizarTablaHistorial();
        // jDateChooser_fecha.setDate(date);
        //  JTextFieldDateEditor editor = (JTextFieldDateEditor) jDateChooser_fecha.getDateEditor();
        // editor.setEditable(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog_detalle = new javax.swing.JDialog();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable_detalle = new javax.swing.JTable();
        jLabel_importe_total = new javax.swing.JLabel();
        jLabel_Total = new javax.swing.JLabel();
        jLabel_WallpaperHeader4 = new javax.swing.JLabel();
        jLabel_Wallpaper5 = new javax.swing.JLabel();
        jDateChooser_desde = new com.toedter.calendar.JDateChooser();
        jDateChooser_hasta = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable_historial = new javax.swing.JTable();
        jButton_detalle = new javax.swing.JButton();
        jButton_aplicar = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel_total = new javax.swing.JLabel();
        jLabel_WallpaperFooter1 = new javax.swing.JLabel();
        jLabel_Wallpaper4 = new javax.swing.JLabel();

        jDialog_detalle.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable_detalle.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Producto", "Cantidad", "Importe"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane5.setViewportView(jTable_detalle);

        jDialog_detalle.getContentPane().add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 620, 290));

        jLabel_importe_total.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel_importe_total.setForeground(new java.awt.Color(0, 0, 0));
        jDialog_detalle.getContentPane().add(jLabel_importe_total, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 370, 110, 30));

        jLabel_Total.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel_Total.setForeground(new java.awt.Color(0, 0, 0));
        jLabel_Total.setText("TOTAL:");
        jDialog_detalle.getContentPane().add(jLabel_Total, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 370, -1, -1));

        jLabel_WallpaperHeader4.setBackground(new java.awt.Color(153, 153, 153));
        jLabel_WallpaperHeader4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel_WallpaperHeader4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_WallpaperHeader4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel_WallpaperHeader4.setText("     Detalle de la salida");
        jLabel_WallpaperHeader4.setOpaque(true);
        jDialog_detalle.getContentPane().add(jLabel_WallpaperHeader4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 670, 60));

        jLabel_Wallpaper5.setBackground(new java.awt.Color(204, 204, 204));
        jLabel_Wallpaper5.setOpaque(true);
        jDialog_detalle.getContentPane().add(jLabel_Wallpaper5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 670, 440));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jDateChooser_desde.setDateFormatString("yyyy-MM-dd");
        jDateChooser_desde.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        getContentPane().add(jDateChooser_desde, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 80, 150, -1));

        jDateChooser_hasta.setDateFormatString("yyyy-MM-dd");
        jDateChooser_hasta.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        getContentPane().add(jDateChooser_hasta, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 80, 150, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Hasta:");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 70, -1, 40));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Desde:");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, -1, 20));

        jTable_historial.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTable_historial.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Fecha", "Importe Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(jTable_historial);

        getContentPane().add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, 820, 500));

        jButton_detalle.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton_detalle.setText("Ver Detalle");
        jButton_detalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_detalleActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_detalle, new org.netbeans.lib.awtextra.AbsoluteConstraints(727, 80, 120, -1));

        jButton_aplicar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton_aplicar.setText("Aplicar");
        jButton_aplicar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_aplicarActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_aplicar, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 80, 100, -1));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 32)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("TOTAL:");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 610, 120, 70));

        jLabel_total.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jLabel_total.setForeground(new java.awt.Color(0, 0, 0));
        getContentPane().add(jLabel_total, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 620, 160, 50));

        jLabel_WallpaperFooter1.setBackground(new java.awt.Color(153, 153, 153));
        jLabel_WallpaperFooter1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel_WallpaperFooter1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_WallpaperFooter1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel_WallpaperFooter1.setText("     HISTORIAL DE SALIDAS");
        jLabel_WallpaperFooter1.setOpaque(true);
        getContentPane().add(jLabel_WallpaperFooter1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 880, 60));

        jLabel_Wallpaper4.setBackground(new java.awt.Color(204, 204, 204));
        jLabel_Wallpaper4.setForeground(new java.awt.Color(153, 153, 153));
        jLabel_Wallpaper4.setOpaque(true);
        getContentPane().add(jLabel_Wallpaper4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 880, 680));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_detalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_detalleActionPerformed
        Clear_Table(jTable_detalle.getRowCount(), modelo_detalle);
        verDetalle();
    }//GEN-LAST:event_jButton_detalleActionPerformed

    private void jButton_aplicarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_aplicarActionPerformed

        if (jDateChooser_desde.getDate().after(jDateChooser_hasta.getDate())) {
            JOptionPane.showMessageDialog(null, "Error, fechas invalidas");
        } else {
            actualizarTablaHistorial();
        }

    }//GEN-LAST:event_jButton_aplicarActionPerformed

    public void actualizarTablaHistorial() {
        Clear_Table(jTable_historial.getRowCount(), modelo_historial);
        try {
            Date hoy = new Date();
            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement(
                    "SELECT s.id, s.fecha, s.importe_total FROM salidas s WHERE s.fecha BETWEEN ? AND ? || s.fecha+1 ='" + formatoFecha.format(hoy) + "' ORDER BY s.fecha asc");

            pst.setDate(1, new java.sql.Date(jDateChooser_desde.getDate().getTime()));
            pst.setDate(2, new java.sql.Date(jDateChooser_hasta.getDate().getTime() + 1000 * 60 * 60 * 24));

            ResultSet rs = pst.executeQuery();
            modelo_historial = (DefaultTableModel) jTable_historial.getModel();

            Object[] fila = new Object[3];
            BigDecimal total = new BigDecimal(0);

            if (rs.next()) {
                for (int i = 0; i < 3; i++) {
                    fila[i] = rs.getObject(i + 1);
                }
                String fecha = fila[1].toString().replaceAll("T", " ");
                fila[1] = fecha;
                 BigDecimal filaImporta = new BigDecimal(fila[2].toString());
                total = total.add(filaImporta);
                fila[2] = formato.format(fila[2]);
               
                modelo_historial.addRow(fila);
                while (rs.next()) {
                    for (int i = 0; i < 3; i++) {
                        fila[i] = rs.getObject(i + 1);
                    }
                    String fecha2 = fila[1].toString().replaceAll("T", " ");
                    fila[1] = fecha2;                    
                    total = total.add(filaImporta);
                    fila[2] = formato.format(fila[2]);
                    modelo_historial.addRow(fila);
                }
            } else {
                modelo_historial.addRow(new Object[]{"No hay registros disponibles.", ""});
            }
            jLabel_total.setText(formato.format(total).toString());
            jTable_historial.setModel(modelo_historial);
            cn.close();

        } catch (SQLException e) {
            System.err.println("Error al llenar tabla." + e);
            JOptionPane.showMessageDialog(null, "Error al mostrar informacion, contacte al administrador");
        }
    }

    private void Clear_Table(int filas, DefaultTableModel modelo) {
        for (int i = 0; filas > i; i++) {
            modelo.removeRow(0);
        }
    }

    public void verDetalle() {

        int fila_point = jTable_historial.getSelectedRow();

        if (fila_point > -1) {
            String codigo = modelo_historial.getValueAt(fila_point, 0).toString();

            try {
                String SQL = "SELECT s.id, p.nombre, s.cantidad, s.importe FROM salidas_detalle s, productos p WHERE s.id_salida = '" + codigo + "' AND s.id_producto = p.id";
                Connection cn = Conexion.conectar();
                Statement st;
                st = cn.createStatement();
                ResultSet rs = st.executeQuery(SQL);
                modelo_detalle = (DefaultTableModel) jTable_detalle.getModel();

                BigDecimal importe = new BigDecimal(0);
                BigDecimal importe_total = new BigDecimal(0);

                while (rs.next()) {
                    String id = rs.getString("s.id");
                    String id_producto = rs.getString("p.nombre");
                    String cantidad = rs.getString("s.cantidad");
                    importe = BigDecimal.valueOf(Double.parseDouble(rs.getString("s.importe")));
                    importe_total = importe_total.add(importe);

                    modelo_detalle.addRow(new Object[]{id, id_producto, cantidad, formato.format(importe.setScale(2, RoundingMode.HALF_UP))});
                }
                System.out.println(importe_total);
                jDialog_detalle.setVisible(true);
                jDialog_detalle.setResizable(false);
                jDialog_detalle.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                jDialog_detalle.setSize(675, 455);
                Entradas entradas = new Entradas();
                jDialog_detalle.setLocationRelativeTo(entradas);

                jLabel_importe_total.setText(formato.format(importe_total).toString());

            } catch (SQLException ex) {
                Logger.getLogger(Entradas.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            JOptionPane.showMessageDialog(null, "Primero seleccione un registro de la tabla");
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Salidas_Historial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Salidas_Historial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Salidas_Historial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Salidas_Historial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Salidas_Historial().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_aplicar;
    private javax.swing.JButton jButton_detalle;
    private com.toedter.calendar.JDateChooser jDateChooser_desde;
    private com.toedter.calendar.JDateChooser jDateChooser_hasta;
    private javax.swing.JDialog jDialog_detalle;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel_Total;
    private javax.swing.JLabel jLabel_Wallpaper4;
    private javax.swing.JLabel jLabel_Wallpaper5;
    private javax.swing.JLabel jLabel_WallpaperFooter1;
    private javax.swing.JLabel jLabel_WallpaperHeader4;
    private javax.swing.JLabel jLabel_importe_total;
    private javax.swing.JLabel jLabel_total;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable jTable_detalle;
    private javax.swing.JTable jTable_historial;
    // End of variables declaration//GEN-END:variables
}