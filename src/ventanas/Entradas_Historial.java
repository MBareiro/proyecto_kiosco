/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ventanas;

import clases.Conexion;
import com.toedter.calendar.JTextFieldDateEditor;
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
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author mb
 */
public class Entradas_Historial extends javax.swing.JFrame {

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

    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");

    /**
     * Creates new form Entradas_Historial
     */
    public Entradas_Historial() {
        initComponents();
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        Date date = new Date();
        jDateChooser_desde.setDate(date);
        jDateChooser_hasta.setDate(date);
        actualizarTablaHistorial();
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
        jLabel_NombreProveedor = new javax.swing.JLabel();
        jLabel_WallpaperHeader4 = new javax.swing.JLabel();
        jLabel_Wallpaper5 = new javax.swing.JLabel();
        jDialog_historial = new javax.swing.JDialog();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable_historial1 = new javax.swing.JTable();
        jLabel_WallpaperFooter2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jDateChooser3 = new com.toedter.calendar.JDateChooser();
        jDateChooser4 = new com.toedter.calendar.JDateChooser();
        jButton_detalle1 = new javax.swing.JButton();
        jLabel_Wallpaper6 = new javax.swing.JLabel();
        jLabel_WallpaperFooter3 = new javax.swing.JLabel();
        jDateChooser_desde = new com.toedter.calendar.JDateChooser();
        jDateChooser_hasta = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable_historial = new javax.swing.JTable();
        jButton_detalle = new javax.swing.JButton();
        jButton_aplicar = new javax.swing.JButton();
        jLabel_total = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel_WallpaperFooter1 = new javax.swing.JLabel();
        jLabel_Wallpaper4 = new javax.swing.JLabel();

        jDialog_detalle.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable_detalle.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
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
        jDialog_detalle.getContentPane().add(jLabel_importe_total, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 370, 190, 30));

        jLabel_Total.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel_Total.setForeground(new java.awt.Color(0, 0, 0));
        jLabel_Total.setText("TOTAL:");
        jDialog_detalle.getContentPane().add(jLabel_Total, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 370, -1, -1));

        jLabel_NombreProveedor.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel_NombreProveedor.setForeground(new java.awt.Color(255, 255, 255));
        jDialog_detalle.getContentPane().add(jLabel_NombreProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 220, 40));

        jLabel_WallpaperHeader4.setBackground(new java.awt.Color(153, 153, 153));
        jLabel_WallpaperHeader4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel_WallpaperHeader4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_WallpaperHeader4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel_WallpaperHeader4.setOpaque(true);
        jDialog_detalle.getContentPane().add(jLabel_WallpaperHeader4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 670, 60));

        jLabel_Wallpaper5.setBackground(new java.awt.Color(204, 204, 204));
        jLabel_Wallpaper5.setOpaque(true);
        jDialog_detalle.getContentPane().add(jLabel_Wallpaper5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 670, 440));

        jDialog_historial.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable_historial1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane6.setViewportView(jTable_historial1);

        jDialog_historial.getContentPane().add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, 820, 520));

        jLabel_WallpaperFooter2.setBackground(new java.awt.Color(153, 153, 153));
        jLabel_WallpaperFooter2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel_WallpaperFooter2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_WallpaperFooter2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel_WallpaperFooter2.setText("     HISTORIAL DE VENTAS");
        jLabel_WallpaperFooter2.setOpaque(true);
        jDialog_historial.getContentPane().add(jLabel_WallpaperFooter2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 880, 60));

        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Desde:");
        jDialog_historial.getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, -1, -1));

        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Hasta:");
        jDialog_historial.getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 80, -1, -1));
        jDialog_historial.getContentPane().add(jDateChooser3, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 80, 120, -1));
        jDialog_historial.getContentPane().add(jDateChooser4, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 80, 120, -1));

        jButton_detalle1.setText("Ver Detalle");
        jButton_detalle1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_detalle1ActionPerformed(evt);
            }
        });
        jDialog_historial.getContentPane().add(jButton_detalle1, new org.netbeans.lib.awtextra.AbsoluteConstraints(727, 80, 120, -1));

        jLabel_Wallpaper6.setBackground(new java.awt.Color(204, 204, 204));
        jLabel_Wallpaper6.setForeground(new java.awt.Color(153, 153, 153));
        jLabel_Wallpaper6.setOpaque(true);
        jDialog_historial.getContentPane().add(jLabel_Wallpaper6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 880, 680));

        jLabel_WallpaperFooter3.setBackground(new java.awt.Color(153, 153, 153));
        jLabel_WallpaperFooter3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel_WallpaperFooter3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_WallpaperFooter3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel_WallpaperFooter3.setText("     NUEVA ENTRADA");
        jLabel_WallpaperFooter3.setOpaque(true);
        jDialog_historial.getContentPane().add(jLabel_WallpaperFooter3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 880, 60));

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
                "ID", "Fecha", "Proveedor", "Importe Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
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

        jLabel_total.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jLabel_total.setForeground(new java.awt.Color(0, 0, 0));
        getContentPane().add(jLabel_total, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 620, 160, 50));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 32)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("TOTAL:");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 610, 120, 70));

        jLabel_WallpaperFooter1.setBackground(new java.awt.Color(153, 153, 153));
        jLabel_WallpaperFooter1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel_WallpaperFooter1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_WallpaperFooter1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel_WallpaperFooter1.setText("     Historial de Entradas");
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

    private void jButton_detalle1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_detalle1ActionPerformed
        Clear_Table(jTable_detalle.getRowCount(), modelo_detalle);
        verDetalle();
    }//GEN-LAST:event_jButton_detalle1ActionPerformed

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
                    "SELECT * FROM entradas e WHERE e.fecha BETWEEN ? AND ? || e.fecha+1 ='" + formatoFecha.format(hoy) + "' ORDER BY e.fecha asc");

            pst.setDate(1, new java.sql.Date(jDateChooser_desde.getDate().getTime()));
            pst.setDate(2, new java.sql.Date(jDateChooser_hasta.getDate().getTime() + 1000 * 60 * 60 * 24));

            ResultSet rs = pst.executeQuery();
            modelo_historial = (DefaultTableModel) jTable_historial.getModel();

            Object[] fila = new Object[4];
            String nombreProv = "";
            BigDecimal total = new BigDecimal(0);

            if (rs.next()) {
                for (int i = 0; i < 4; i++) {
                    if (rs.getObject(i + 1) == null) {
                        fila[i] = "";
                    } else {
                        if (i == 2) {
                            PreparedStatement pst2 = cn.prepareStatement(
                                    "SELECT nombre FROM proveedores WHERE id = '" + rs.getObject(3) + "'");
                            ResultSet rs2 = pst2.executeQuery();
                            if (rs2.next()) {
                                nombreProv = rs2.getString("nombre");
                                fila[2] = nombreProv;
                            }
                        } else {
                            fila[i] = rs.getObject(i + 1);
                        }
                    }
                }
                String fecha = fila[1].toString().replaceAll("T", " ");
                fila[1] = fecha;
                BigDecimal filaImporta = new BigDecimal(fila[3].toString());
                total = total.add(filaImporta);
                fila[3] = formato.format(fila[3]);

                modelo_historial.addRow(fila);

                while (rs.next()) {
                    for (int i = 0; i < 4; i++) {
                        if (rs.getObject(i + 1) == null) {
                            fila[i] = "";
                        } else {
                            if (i == 2) {
                                PreparedStatement pst2 = cn.prepareStatement(
                                        "SELECT nombre FROM proveedores WHERE id = '" + rs.getObject(3) + "'");
                                ResultSet rs2 = pst2.executeQuery();
                                if (rs2.next()) {
                                    nombreProv = rs2.getString("nombre");
                                    System.out.println(nombreProv);
                                    fila[2] = nombreProv;
                                }
                            } else {
                                fila[i] = rs.getObject(i + 1);
                            }
                        }
                    }
                    String fecha2 = fila[1].toString().replaceAll("T", " ");
                    fila[1] = fecha2;
                    total = total.add((BigDecimal) fila[3]);
                    fila[3] = formato.format(fila[3]);

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
            //JOptionPane.showMessageDialog(null, "Error al mostrar informacion, contacte al administrador");
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
            String nombre_proveedor = modelo_historial.getValueAt(fila_point, 2).toString();

            try {
                String SQL = "SELECT e.id, p.nombre, p.medida, e.cantidad, e.importe FROM entradas_detalle e, productos p WHERE e.id_entrada = '" + codigo + "' AND e.id_producto = p.id";
                Connection cn = Conexion.conectar();
                Statement st;
                st = cn.createStatement();
                ResultSet rs = st.executeQuery(SQL);
                modelo_detalle = (DefaultTableModel) jTable_detalle.getModel();

                BigDecimal importe = new BigDecimal(0);
                BigDecimal importe_total = new BigDecimal(0);

                while (rs.next()) {
                    String medida = rs.getString("medida");
                    String cantidad = rs.getString("cantidad");
                    String cantidadString = ""; // Variable para almacenar la cantidad convertida a String
                    importe = BigDecimal.valueOf(Double.parseDouble(rs.getString("importe")));
                    String id = rs.getString("id");
                    String id_producto = rs.getString("p.nombre");
                    
                    System.out.println(medida);
                    if (medida.equals("1")) {
                        // Encontrar la posición del punto decimal
                        int posicionPunto = cantidad.indexOf(".");
                        // Extraer la parte entera del número
                        String parteEntera = cantidad.substring(0, posicionPunto);
                        // Formatear la parte entera sin decimales
                        DecimalFormat formato2 = new DecimalFormat("#");
                        cantidad = formato2.format(Double.parseDouble(parteEntera)) + " u.";
                    } else if (medida.equals("2")) {
                        cantidad += " lt.";
                    } else if (medida.equals("3")) {
                        cantidad += " kg.";
                    }
                    importe_total = importe_total.add(importe);
                    modelo_detalle.addRow(new Object[]{id, id_producto, cantidad, formato.format(importe.setScale(2, RoundingMode.HALF_UP))});
                }

                jDialog_detalle.setVisible(true);
                jDialog_detalle.setResizable(false);
                jDialog_detalle.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                jDialog_detalle.setSize(675, 455);
                Entradas entradas = new Entradas();
                jDialog_detalle.setLocationRelativeTo(entradas);

                jLabel_importe_total.setText(formato.format(importe_total).toString());
                jDialog_detalle.setTitle("Detalle");
                jLabel_NombreProveedor.setText(nombre_proveedor);
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
            java.util.logging.Logger.getLogger(Entradas_Historial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Entradas_Historial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Entradas_Historial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Entradas_Historial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Entradas_Historial().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_aplicar;
    private javax.swing.JButton jButton_detalle;
    private javax.swing.JButton jButton_detalle1;
    private com.toedter.calendar.JDateChooser jDateChooser3;
    private com.toedter.calendar.JDateChooser jDateChooser4;
    private com.toedter.calendar.JDateChooser jDateChooser_desde;
    private com.toedter.calendar.JDateChooser jDateChooser_hasta;
    private javax.swing.JDialog jDialog_detalle;
    private javax.swing.JDialog jDialog_historial;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel_NombreProveedor;
    private javax.swing.JLabel jLabel_Total;
    private javax.swing.JLabel jLabel_Wallpaper4;
    private javax.swing.JLabel jLabel_Wallpaper5;
    private javax.swing.JLabel jLabel_Wallpaper6;
    private javax.swing.JLabel jLabel_WallpaperFooter1;
    private javax.swing.JLabel jLabel_WallpaperFooter2;
    private javax.swing.JLabel jLabel_WallpaperFooter3;
    private javax.swing.JLabel jLabel_WallpaperHeader4;
    private javax.swing.JLabel jLabel_importe_total;
    private javax.swing.JLabel jLabel_total;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTable jTable_detalle;
    private javax.swing.JTable jTable_historial;
    private javax.swing.JTable jTable_historial1;
    // End of variables declaration//GEN-END:variables
}
