/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ventanas;

import clases.Conexion;
import com.toedter.calendar.JSpinnerDateEditor;
import com.toedter.calendar.JTextFieldDateEditor;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.text.JTextComponent;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author mb
 */
public class Salidas extends javax.swing.JFrame {

    DefaultTableModel modelo_productos;
    DefaultTableModel modelo_salidas;

    public int id_salida;

    DecimalFormat d = new DecimalFormat("####.##");
    Locale region = Locale.getDefault();
    NumberFormat formato = NumberFormat.getCurrencyInstance(region);
    NumberFormat format = NumberFormat.getCurrencyInstance();
    private final List productos;
    public String medida = "";

    /**
     * Creates new form Ventas
     */
    public Salidas() {
        this.productos = TraerProductos();
        initComponents();
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        jLabel_mostrarFecha.setText(dateFormat.format(date));

        CargarSugerencias();
        LLenarCombobox(productos);

        jComboBox_BuscarProducto.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent event) {
                if (event.getKeyChar() == KeyEvent.VK_ENTER) {
                    if (((JTextComponent) ((JComboBox) ((Component) event
                            .getSource()).getParent()).getEditor()
                            .getEditorComponent()).getText().isEmpty()) {
                        //vacio
                    } else {
                        Mostrar(productos);
                        //ActualizarStock();
                    }
                }
            }
        });

        ((JTextField) jComboBox_BuscarProducto.getEditor().getEditorComponent()).setOpaque(true);
        jTextField_Cantidad.setEnabled(false);
//
    }

    private void LLenarCombobox(List tuLista) {
        for (int i = 0; i < tuLista.size(); i++) {
            ArrayList<String> regs = new ArrayList<String>();
            regs = (ArrayList<String>) tuLista.get(i);
            jComboBox_BuscarProducto.addItem(regs.get(1).toString());
        }
    }

    private void Mostrar(List productos) {
        System.out.println(productos);
        ArrayList tuLista = new ArrayList();
        for (int i = 0; i < productos.size(); i++) {
            tuLista = (ArrayList) productos.get(i);
            if (jComboBox_BuscarProducto.getSelectedIndex() > 0) {
                if (tuLista.get(1) == jComboBox_BuscarProducto.getSelectedItem()) {
                    jTextField_codigoProducto.setText((String) tuLista.get(0));
                    if (tuLista.get(4).equals("2")) {
                        System.out.println("asdasdas");
                        BigDecimal nuevo = new BigDecimal((String) tuLista.get(2));
                        //BigDecimal nuevo = (BigDecimal) tuLista.get(2);
                        int xa = nuevo.intValue();
                        String s = Integer.toString(xa);
                        jTextField_stock.setText(s);
                    } else {
                        jTextField_stock.setText((String) tuLista.get(2));
                    }

                    jTextField_precioVenta.setText((String) tuLista.get(3));
                    ActualizarStock();
                    medida = (String) tuLista.get(4);
                }
            }
            if (!jTextField_codigoProducto.equals("")) {
                if (tuLista.get(0) == jTextField_codigoProducto.getText()) {
                    jComboBox_BuscarProducto.setSelectedItem((String) tuLista.get(1));
                    jTextField_stock.setText((String) tuLista.get(2));
                    jTextField_precioVenta.setText((String) tuLista.get(3));
                }
            }

        }
        System.out.println(medida);
    }

    private void ActualizarStock() {
        for (int i = 0; i < jTable_salidas.getRowCount(); i++) {
            if (jTextField_codigoProducto.getText().toString().trim().equals(jTable_salidas.getValueAt(i, 0).toString().trim())) {

                BigDecimal stock = new BigDecimal(jTextField_stock.getText());
                BigDecimal cantidad = new BigDecimal(jTable_salidas.getValueAt(i, 2).toString());
                BigDecimal actStock = stock.subtract(cantidad);
                //String convertirCadena = String.valueOf(actStock);
                jTextField_stock.setText(actStock.toString());
            }
        }
    }

    private List TraerProductos() {
        ArrayList tuLista = new ArrayList();
        try {
            Connection cn = Conexion.conectar();
            PreparedStatement pst2 = cn.prepareStatement(
                    "SELECT id, nombre, cantidad, precio_venta, medida FROM productos");
            ResultSet rs = pst2.executeQuery();
            while (rs.next()) {
                ArrayList reg = new ArrayList();
                reg.add(rs.getObject(1).toString());
                reg.add(rs.getObject(2).toString());
                reg.add(rs.getObject(3).toString());
                reg.add(rs.getObject(4).toString());
                reg.add(rs.getObject(5).toString());
                tuLista.add(reg);
            }
        } catch (SQLException e) {
            System.err.println("Error en registrar detalle " + e);
            JOptionPane.showMessageDialog(null, "Error al registrar, contacte al administrador");
        }

        return tuLista;
    }

    private void CargarSugerencias() {
        AutoCompleteDecorator.decorate(jComboBox_BuscarProducto);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog_productos = new javax.swing.JDialog();
        jLabel14 = new javax.swing.JLabel();
        cmb_buscar = new javax.swing.JComboBox<>();
        txt_buscar = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable_productos = new javax.swing.JTable();
        jButton_agregar = new javax.swing.JButton();
        jLabel_WallpaperHeader2 = new javax.swing.JLabel();
        jLabel_Wallpaper1 = new javax.swing.JLabel();
        jTextField_recibido = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextField_precioVenta = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jButton_agregarProducto = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_salidas = new javax.swing.JTable();
        jLabel_vuelto = new javax.swing.JLabel();
        jButton_Guardar = new javax.swing.JButton();
        jLabel_total = new javax.swing.JLabel();
        jTextField_codigoProducto = new javax.swing.JTextField();
        jLabel_mostrarFecha = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jTextField_stock = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jButton_eliminar = new javax.swing.JButton();
        jButton_limpiar = new javax.swing.JButton();
        jButton_Historial = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jComboBox_BuscarProducto = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        jTextField_Cantidad = new javax.swing.JTextField();
        jLabel_WallpaperHeader = new javax.swing.JLabel();
        jLabel_WallpaperFooter = new javax.swing.JLabel();
        jLabel_Wallpaper = new javax.swing.JLabel();

        jDialog_productos.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel14.setForeground(new java.awt.Color(0, 0, 0));
        jLabel14.setText("Buscar por:");
        jDialog_productos.getContentPane().add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, -1));

        cmb_buscar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nombre", "Codigo" }));
        cmb_buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_buscarActionPerformed(evt);
            }
        });
        jDialog_productos.getContentPane().add(cmb_buscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 70, 160, -1));

        txt_buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_buscarActionPerformed(evt);
            }
        });
        txt_buscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_buscarKeyReleased(evt);
            }
        });
        jDialog_productos.getContentPane().add(txt_buscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 70, 140, -1));

        jTable_productos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Nombre", "Cantidad", "Precio"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable_productos);

        jDialog_productos.getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 590, 200));

        jButton_agregar.setText("Agregar");
        jButton_agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_agregarActionPerformed(evt);
            }
        });
        jDialog_productos.getContentPane().add(jButton_agregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 310, 170, 30));

        jLabel_WallpaperHeader2.setBackground(new java.awt.Color(153, 153, 153));
        jLabel_WallpaperHeader2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel_WallpaperHeader2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_WallpaperHeader2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel_WallpaperHeader2.setText("     Buscar Producto");
        jLabel_WallpaperHeader2.setOpaque(true);
        jDialog_productos.getContentPane().add(jLabel_WallpaperHeader2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 630, 60));

        jLabel_Wallpaper1.setBackground(new java.awt.Color(204, 204, 204));
        jLabel_Wallpaper1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel_Wallpaper1.setOpaque(true);
        jDialog_productos.getContentPane().add(jLabel_Wallpaper1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 630, 350));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTextField_recibido.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextField_recibido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_recibidoActionPerformed(evt);
            }
        });
        jTextField_recibido.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField_recibidoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_recibidoKeyTyped(evt);
            }
        });
        getContentPane().add(jTextField_recibido, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, 160, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Recibido:");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, -1, -1));

        jTextField_precioVenta.setEditable(false);
        jTextField_precioVenta.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextField_precioVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_precioVentaActionPerformed(evt);
            }
        });
        getContentPane().add(jTextField_precioVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 170, 100, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Cantidad:");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 150, -1, -1));

        jButton_agregarProducto.setText("+");
        jButton_agregarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_agregarProductoActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_agregarProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 170, 50, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Precio Venta:");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 150, 90, -1));

        jTable_salidas.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTable_salidas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cod. Prod.", "Nombre", "Cantidad", "Precio venta", "Importe"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable_salidas);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 210, 870, 330));

        jLabel_vuelto.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jLabel_vuelto.setForeground(new java.awt.Color(255, 255, 255));
        getContentPane().add(jLabel_vuelto, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 610, 190, 50));

        jButton_Guardar.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton_Guardar.setText("GUARDAR");
        jButton_Guardar.setEnabled(false);
        jButton_Guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_GuardarActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_Guardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 610, 190, 60));

        jLabel_total.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jLabel_total.setForeground(new java.awt.Color(255, 255, 255));
        getContentPane().add(jLabel_total, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 610, 210, 50));

        jTextField_codigoProducto.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextField_codigoProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_codigoProductoActionPerformed(evt);
            }
        });
        jTextField_codigoProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField_codigoProductoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_codigoProductoKeyTyped(evt);
            }
        });
        getContentPane().add(jTextField_codigoProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 170, 60, -1));

        jLabel_mostrarFecha.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel_mostrarFecha.setForeground(new java.awt.Color(255, 255, 255));
        getContentPane().add(jLabel_mostrarFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 20, 190, 20));

        jSeparator1.setBackground(new java.awt.Color(153, 153, 153));
        jSeparator1.setForeground(new java.awt.Color(204, 204, 204));
        getContentPane().add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 140, 870, 10));

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Código:");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 150, -1, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Producto:");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 150, -1, -1));

        jTextField_stock.setEditable(false);
        jTextField_stock.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextField_stock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_stockActionPerformed(evt);
            }
        });
        getContentPane().add(jTextField_stock, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 170, 60, -1));

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("Stock:");
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 150, -1, -1));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("VUELTO:");
        getContentPane().add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 610, -1, 50));

        jButton_eliminar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton_eliminar.setText("Eliminar");
        jButton_eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_eliminarActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_eliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 550, 120, 30));

        jButton_limpiar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton_limpiar.setText("Limpiar");
        jButton_limpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_limpiarActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_limpiar, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 550, 120, 30));

        jButton_Historial.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton_Historial.setText("Historial");
        jButton_Historial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_HistorialActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_Historial, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 100, 110, -1));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 32)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("TOTAL:");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 600, 120, 70));

        jComboBox_BuscarProducto.setEditable(true);
        jComboBox_BuscarProducto.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jComboBox_BuscarProducto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-" }));
        jComboBox_BuscarProducto.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox_BuscarProductoItemStateChanged(evt);
            }
        });
        jComboBox_BuscarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox_BuscarProductoActionPerformed(evt);
            }
        });
        jComboBox_BuscarProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComboBox_BuscarProductoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jComboBox_BuscarProductoKeyReleased(evt);
            }
        });
        getContentPane().add(jComboBox_BuscarProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 170, 150, -1));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Fecha:");
        getContentPane().add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 20, -1, 20));

        jTextField_Cantidad.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextField_Cantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_CantidadActionPerformed(evt);
            }
        });
        jTextField_Cantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField_CantidadKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_CantidadKeyTyped(evt);
            }
        });
        getContentPane().add(jTextField_Cantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 170, 70, -1));

        jLabel_WallpaperHeader.setBackground(new java.awt.Color(153, 153, 153));
        jLabel_WallpaperHeader.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel_WallpaperHeader.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_WallpaperHeader.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel_WallpaperHeader.setText("     Salidas");
        jLabel_WallpaperHeader.setOpaque(true);
        getContentPane().add(jLabel_WallpaperHeader, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 970, 60));

        jLabel_WallpaperFooter.setBackground(new java.awt.Color(0, 0, 0));
        jLabel_WallpaperFooter.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel_WallpaperFooter.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_WallpaperFooter.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel_WallpaperFooter.setOpaque(true);
        getContentPane().add(jLabel_WallpaperFooter, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 600, 970, 80));

        jLabel_Wallpaper.setBackground(new java.awt.Color(204, 204, 204));
        jLabel_Wallpaper.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel_Wallpaper.setOpaque(true);
        getContentPane().add(jLabel_Wallpaper, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 950, 670));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField_recibidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_recibidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_recibidoActionPerformed

    private void jTextField_precioVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_precioVentaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_precioVentaActionPerformed
    public boolean validarCodProducto() {
        boolean completo = true;
        if (jTextField_codigoProducto.getText().equals("")) {
            completo = false;
            jTextField_codigoProducto.setBackground(Color.orange);
        } else {
            jTextField_codigoProducto.setBackground(Color.white);
        }
        return completo;
    }

    public boolean validarBuscarProducto() {
        boolean completo = true;
        if (jComboBox_BuscarProducto.getSelectedItem().equals("-")) {
            completo = false;
            ((JTextField) jComboBox_BuscarProducto.getEditor().getEditorComponent()).setBackground(Color.orange);
            jTextField_Cantidad.setEnabled(false);
            jTextField_codigoProducto.setText("");            
        } else {
            ((JTextField) jComboBox_BuscarProducto.getEditor().getEditorComponent()).setBackground(Color.white);
            jTextField_Cantidad.setEnabled(true);
        }
        return completo;
    }

    public int validarCantidad() {

        int validado = 0;
        if (!jTextField_Cantidad.getText().equals("")) {
            if (Float.parseFloat(jTextField_Cantidad.getText()) <= 0) {
                validado = 1;
                jTextField_Cantidad.setBackground(Color.orange);
            } else {
                jTextField_Cantidad.setBackground(Color.white);
            }
        } else {
            validado = 1;
            jTextField_Cantidad.setBackground(Color.orange);
        }
        return validado;
    }

    public boolean validaciones() {
        boolean validado = true;
        boolean completo = true;
        boolean stockSuficiente = true;
        System.out.println("-----7");
                System.out.println(jTextField_Cantidad.getText());
        if (validarCantidad() == 1) {
            completo = false;
        } else if (new BigDecimal(jTextField_Cantidad.getText()).compareTo(new BigDecimal(jTextField_stock.getText())) == 1) {
            stockSuficiente = false;
        }
        System.out.println("-----8");
                System.out.println(jTextField_Cantidad.getText());
        if (!validarCodProducto()) {
            completo = false;
        }
        System.out.println("-----5555");
                System.out.println(jTextField_Cantidad.getText());
        if (!validarBuscarProducto()) {
            completo = false;
        }
        System.out.println("-----19");
                System.out.println(jTextField_Cantidad.getText());
        if (!completo) {
            JOptionPane.showMessageDialog(null, "Complete los campos resaltados.");
        } else if (!stockSuficiente) {
            JOptionPane.showMessageDialog(null, "Stock insuficiente.");
        }
        if (!completo || !stockSuficiente) {
            validado = false;
        }
         System.out.println("-----9");
                System.out.println(jTextField_Cantidad.getText());
        return validado;
    }

    private void jButton_agregarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_agregarProductoActionPerformed
System.out.println("-----1");
                System.out.println(jTextField_Cantidad.getText());
        if (validaciones()) {
            System.out.println("-----2");
                System.out.println(jTextField_Cantidad.getText());
            BigDecimal importe = new BigDecimal(0);
            BigDecimal pventa = new BigDecimal(jTextField_precioVenta.getText()).setScale(2, RoundingMode.HALF_UP);

            int b = 0;
            for (int i = 0; i < jTable_salidas.getRowCount(); i++) {
                if (Integer.parseInt(jTextField_codigoProducto.getText()) == Integer.parseInt((String) jTable_salidas.getValueAt(i, 0))) {
                    b = 1;
                    BigDecimal cantidadTabla = new BigDecimal(jTable_salidas.getValueAt(i, 2).toString());
                    BigDecimal cantidadNueva = new BigDecimal(jTextField_Cantidad.getText());
                    jTable_salidas.setValueAt(cantidadTabla.add(cantidadNueva), i, 2);
                }
            }
            if (b != 1) {
                modelo_salidas = (DefaultTableModel) jTable_salidas.getModel();
                System.out.println("-----");
                System.out.println(jTextField_Cantidad.getText());
                System.out.println(jTextField_precioVenta.getText());
                importe = BigDecimal.valueOf(Double.parseDouble(jTextField_Cantidad.getText())).multiply(BigDecimal.valueOf(Double.parseDouble(jTextField_precioVenta.getText())));
                modelo_salidas.addRow(new Object[]{jTextField_codigoProducto.getText(), jComboBox_BuscarProducto.getSelectedItem(), jTextField_Cantidad.getText(), formato.format(pventa), formato.format(importe.setScale(2, RoundingMode.HALF_UP))});
            }

            limpiarEntradas();

            //vuelto
            if (vuelto() != null) {
                jLabel_vuelto.setText(formato.format(vuelto()));
            }
            jLabel_total.setText(formato.format(total()));

            // Habilitar o deshabilitar boton guardar
            if (jTable_salidas.getRowCount() > 0) {
                jButton_Guardar.setEnabled(true);
            } else {
                jButton_Guardar.setEnabled(false);
                jLabel_total.setText("");
            }
        }
    }//GEN-LAST:event_jButton_agregarProductoActionPerformed

    public BigDecimal vuelto() {
        if (!jTextField_recibido.getText().equals("")) {
            BigDecimal recibido = new BigDecimal(jTextField_recibido.getText()).setScale(2, RoundingMode.HALF_UP);
            if (recibido.signum() > 0) {
                BigDecimal vuelto = recibido.subtract((BigDecimal) total());
                return vuelto;
            }
        }
        return null;
    }

    public BigDecimal total() {
        BigDecimal importeSuma = new BigDecimal(0);
        BigDecimal total = new BigDecimal(0);
        for (int i = 0; i < jTable_salidas.getRowCount(); i++) {
            Number number;
            try {
                number = format.parse(jTable_salidas.getValueAt(i, 3).toString());
                importeSuma = BigDecimal.valueOf(Double.parseDouble(jTable_salidas.getValueAt(i, 2).toString())).multiply(BigDecimal.valueOf(Double.parseDouble(number.toString())));
                total = total.add(importeSuma);
            } catch (ParseException ex) {
                Logger.getLogger(Entradas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return total;
    }

    private void Clear_Table(int filas, DefaultTableModel modelo) {
        for (int i = 0; filas > i; i++) {
            modelo.removeRow(0);
        }
    }

    public void eliminarProducto() {
        if (jTable_salidas.getSelectedRow() >= 0) {
            int confirmado = JOptionPane.showConfirmDialog(null, "¿Esta seguro?",
                    "Borrar", JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.INFORMATION_MESSAGE);
            if (JOptionPane.OK_OPTION == confirmado) {
                int numRows = jTable_salidas.getSelectedRows().length;
                for (int i = 0; i < numRows; i++) {
                    modelo_salidas.removeRow(jTable_salidas.getSelectedRow());
                }
                limpiarEntradas();
            }
        }
    }

    public void limpiarEntradas() {
        jComboBox_BuscarProducto.setSelectedItem("-");
        jTextField_precioVenta.setText("");
        jTextField_stock.setText("");
        jTextField_codigoProducto.setText("");
        jTextField_Cantidad.setText("");
        jTextField_Cantidad.setBackground(Color.white);
        ((JTextField) jComboBox_BuscarProducto.getEditor().getEditorComponent()).setBackground(Color.white);
        jTextField_codigoProducto.setBackground(Color.white);
    }
    private void jButton_GuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_GuardarActionPerformed
        if (jTable_salidas.getRowCount() > 0) {
            try {

                BigDecimal total = new BigDecimal(Float.parseFloat(format.parse(jLabel_total.getText()).toString()));

                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                String date = dateFormat.format(new Date());
                Connection cn = Conexion.conectar();

                String query = "INSERT INTO `salidas`(`fecha`, `importe_total`) VALUES (?,?)";
                PreparedStatement pst1 = null;

                pst1 = cn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                pst1.setString(1, date);
                pst1.setBigDecimal(2, total.setScale(2, RoundingMode.HALF_UP));
                pst1.executeUpdate();

                ResultSet res = (ResultSet) pst1.getGeneratedKeys();

                if (res.next()) {
                    id_salida = res.getInt(1);
                }

                PreparedStatement pst2 = cn.prepareStatement(
                        "INSERT INTO `salidas_detalle` (`id_producto`, `cantidad`, `importe`, `id_salida`) VALUES (?,?,?,?)");

                PreparedStatement pst3 = cn.prepareStatement(
                        "UPDATE productos SET cantidad = cantidad - ? WHERE id = ?");

                for (int i = 0; i < jTable_salidas.getRowCount(); i++) {
                    BigDecimal importe = new BigDecimal(format.parse((String) jTable_salidas.getValueAt(i, 4)).toString());

                    pst2.setString(1, jTable_salidas.getValueAt(i, 0).toString());
                    pst2.setString(2, jTable_salidas.getValueAt(i, 2).toString());
                    pst2.setBigDecimal(3, importe.setScale(2, RoundingMode.HALF_UP));
                    pst2.setInt(4, id_salida);
                    pst2.executeUpdate();

                    pst3.setString(1, jTable_salidas.getValueAt(i, 2).toString());
                    pst3.setString(2, jTable_salidas.getValueAt(i, 0).toString());
                    pst3.executeUpdate();
                }
                cn.close();
                JOptionPane.showMessageDialog(null, "Registro exitoso!");
                Clear_Table(jTable_salidas.getRowCount(), modelo_salidas);
                jLabel_total.setText("");
                jLabel_vuelto.setText("");
                jTextField_recibido.setText("");
                jButton_Guardar.setEnabled(false);
                limpiarEntradas();
            } catch (SQLException e) {
                System.err.println("Error en registrar detalle " + e);
                JOptionPane.showMessageDialog(null, "Error al registrar, contacte al administrador");
            } catch (ParseException ex) {
                Logger.getLogger(Salidas.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "La tabla no puede estar vacia.");
        }

    }//GEN-LAST:event_jButton_GuardarActionPerformed

    private void cmb_buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_buscarActionPerformed

    }//GEN-LAST:event_cmb_buscarActionPerformed

    private void txt_buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_buscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_buscarActionPerformed

    private void txt_buscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_buscarKeyReleased

    }//GEN-LAST:event_txt_buscarKeyReleased

    private void jButton_agregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_agregarActionPerformed

        int prod_selec = jTable_productos.getSelectedRow();
        if (prod_selec != -1) {
            if (Integer.parseInt(jTable_productos.getValueAt(prod_selec, 2).toString()) > 0 && jTable_productos.getValueAt(prod_selec, 2).toString() != null) {
                if (Double.parseDouble(jTable_productos.getValueAt(prod_selec, 3).toString()) > 0 && jTable_productos.getValueAt(prod_selec, 3).toString() != null) {
                    String nombre, codigo, cantidad, precio;
                    codigo = jTable_productos.getValueAt(prod_selec, 0).toString();
                    nombre = jTable_productos.getValueAt(prod_selec, 1).toString();
                    cantidad = jTable_productos.getValueAt(prod_selec, 2).toString();
                    precio = jTable_productos.getValueAt(prod_selec, 3).toString();
                    jTextField_codigoProducto.setText(codigo);
                    //jTextField_nombreProducto.setText(nombre);
                    jTextField_stock.setText(cantidad);
                    jTextField_precioVenta.setText(precio);
                    jDialog_productos.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Este producto no posee precio.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "No hay stock disponible de este producto");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un producto.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }

    }//GEN-LAST:event_jButton_agregarActionPerformed

    private void jTextField_codigoProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_codigoProductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_codigoProductoActionPerformed

    private void jTextField_stockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_stockActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_stockActionPerformed

    private void jTextField_recibidoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_recibidoKeyReleased
        if (!jTextField_recibido.getText().equals("")) {
            BigDecimal recibido = new BigDecimal(jTextField_recibido.getText()).setScale(2, RoundingMode.HALF_UP);
            if (recibido.signum() > 0) {
                BigDecimal vuelto = recibido.subtract(total());
                jLabel_vuelto.setText(formato.format(vuelto));
            }
        } else {
            jLabel_vuelto.setText("");
        }
    }//GEN-LAST:event_jTextField_recibidoKeyReleased

    private void jButton_limpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_limpiarActionPerformed
        Clear_Table(jTable_salidas.getRowCount(), modelo_salidas);
    }//GEN-LAST:event_jButton_limpiarActionPerformed

    private void jButton_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_eliminarActionPerformed
        eliminarProducto();
    }//GEN-LAST:event_jButton_eliminarActionPerformed

    private void jButton_HistorialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_HistorialActionPerformed
        Salidas_Historial salidas_Historial = new Salidas_Historial();
        salidas_Historial.setVisible(true);

        salidas_Historial.setResizable(false);
        salidas_Historial.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        salidas_Historial.setSize(890, 710);
        salidas_Historial.setLocationRelativeTo(salidas_Historial);
        //actualizarTablaHistorial();
    }//GEN-LAST:event_jButton_HistorialActionPerformed

    private void jComboBox_BuscarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox_BuscarProductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox_BuscarProductoActionPerformed

    private void jTextField_codigoProductoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_codigoProductoKeyReleased

        try {
            Connection cn = Conexion.conectar();
            String valor = jTextField_codigoProducto.getText();
            PreparedStatement pst = cn.prepareStatement(
                    "SELECT `nombre` FROM productos WHERE id= '" + valor + "'");
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                jComboBox_BuscarProducto.setSelectedItem(rs.getObject(1));
            } else {
                jComboBox_BuscarProducto.setSelectedItem("-");
            }
            cn.close();
        } catch (SQLException e) {
            System.err.println("Error al llenar tabla." + e);
            JOptionPane.showMessageDialog(null, "Error al mostrar informacion, contacte al administrador");
        } finally {
            validarCodProducto();
        }
    }//GEN-LAST:event_jTextField_codigoProductoKeyReleased

    private void jComboBox_BuscarProductoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox_BuscarProductoItemStateChanged
        Mostrar(productos);
        validarBuscarProducto();
        validarCodProducto();
    }//GEN-LAST:event_jComboBox_BuscarProductoItemStateChanged

    private void jComboBox_BuscarProductoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboBox_BuscarProductoKeyReleased
        validarBuscarProducto();
    }//GEN-LAST:event_jComboBox_BuscarProductoKeyReleased

    private void jComboBox_BuscarProductoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboBox_BuscarProductoKeyPressed

    }//GEN-LAST:event_jComboBox_BuscarProductoKeyPressed

    private void jTextField_CantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_CantidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_CantidadActionPerformed

    private void jTextField_CantidadKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_CantidadKeyReleased
        validarCantidad();
    }//GEN-LAST:event_jTextField_CantidadKeyReleased

    private void jTextField_CantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_CantidadKeyTyped
        char caracter = evt.getKeyChar();
        if (medida.equals("4") || medida.equals("3")) {
            if ((caracter < '0' || caracter > '9') && (caracter != '.' || jTextField_Cantidad.getText().contains(".")) && caracter != KeyEvent.VK_BACK_SPACE) {
                evt.consume();
            }
            validarDecimales(evt, jTextField_Cantidad, 3);
        } else if (medida.equals("2")) {
            if ((caracter < '0' || caracter > '9') && caracter != KeyEvent.VK_BACK_SPACE && !jTextField_Cantidad.getText().contains(".")) {
                evt.consume();
            }
        }
        puntoAlPrincipio(evt, jTextField_Cantidad, caracter);
    }//GEN-LAST:event_jTextField_CantidadKeyTyped

    private void jTextField_recibidoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_recibidoKeyTyped
        char caracter = evt.getKeyChar();
        if ((caracter < '0' || caracter > '9') && (caracter != '.' || jTextField_Cantidad.getText().contains(".")) && caracter != KeyEvent.VK_BACK_SPACE) {
            evt.consume();
        }
        validarDecimales(evt, jTextField_recibido, 2);
    }//GEN-LAST:event_jTextField_recibidoKeyTyped

    private void jTextField_codigoProductoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_codigoProductoKeyTyped
        char caracter = evt.getKeyChar();
        controlIngresoSinPunto(evt, jTextField_codigoProducto, caracter);
    }//GEN-LAST:event_jTextField_codigoProductoKeyTyped

    public void puntoAlPrincipio(java.awt.event.KeyEvent evt, JTextField cuadrito, char caracter) {
        if (cuadrito.getText().length() <= 0) {
            if (caracter == '.') {
                evt.consume();
            }
        }
    }

    public void controlIngresoSinPunto(java.awt.event.KeyEvent evt, JTextField cuadrito, char caracter) {
        controlIngreso(evt, cuadrito, caracter);
        if (caracter == '.') {
            evt.consume();
        }
    }

    public void controlIngreso(java.awt.event.KeyEvent evt, JTextField cuadrito, char caracter) {

        if ((caracter < '0' || caracter > '9')
                && (caracter != '.' || jTextField_Cantidad.getText().contains("."))
                && caracter != KeyEvent.VK_BACK_SPACE) {
            evt.consume();
        }
        if (jTextField_Cantidad.getText().contains(".")) {
            String n = jTextField_Cantidad.getText();
            String aux;
            String resul = "";
            aux = n;
            for (int i = 0; i < aux.length(); i++) {
                if (aux.substring(i, i + 1).equals(".")) {
                    for (int j = i + 1; j < aux.length(); j++) {
                        resul = resul.concat(aux.substring(j, j + 1));
                    }
                }
            }
            if (resul.length() >= 3) {
                evt.consume();
            }
        }
        if (cuadrito.getText().length() <= 0) {
            if (caracter == '.') {
                evt.consume();
            }
        }
    }

    public void validarDecimales(java.awt.event.KeyEvent evt, JTextField cuadrito, int cantDecimales) {
        if (cuadrito.getText().contains(".")) {
            String n = cuadrito.getText();
            String aux;
            String resul = "";
            aux = n;
            for (int i = 0; i < aux.length(); i++) {
                if (aux.substring(i, i + 1).equals(".")) {
                    for (int j = i + 1; j < aux.length(); j++) {
                        resul = resul.concat(aux.substring(j, j + 1));
                    }
                }
            }
            if (resul.length() >= cantDecimales) {
                evt.consume();
            }
        }
    }

    public void actualizarTablaProductos() {
        Clear_Table(jTable_productos.getRowCount(), modelo_productos);
        if (jTable_productos.getRowCount() == 0) {
            try {
                Connection cn = Conexion.conectar();
                PreparedStatement pst = cn.prepareStatement(
                        "SELECT `id`, `nombre`, `cantidad`, `precio_venta` FROM productos");
                ResultSet rs = pst.executeQuery();

                modelo_productos = (DefaultTableModel) jTable_productos.getModel();

                while (rs.next()) {
                    Object[] fila = new Object[4];
                    for (int i = 0; i < 4; i++) {
                        fila[i] = rs.getObject(i + 1);
                    }
                    modelo_productos.addRow(fila);
                }
                jTable_productos.setModel(modelo_productos);
                cn.close();
            } catch (SQLException e) {
                System.err.println("Error al llenar tabla." + e);
                JOptionPane.showMessageDialog(null, "Error al mostrar informacion, contacte al administrador");
            }
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
            java.util.logging.Logger.getLogger(Salidas.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Salidas.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Salidas.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Salidas.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Salidas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cmb_buscar;
    private javax.swing.JButton jButton_Guardar;
    private javax.swing.JButton jButton_Historial;
    private javax.swing.JButton jButton_agregar;
    private javax.swing.JButton jButton_agregarProducto;
    private javax.swing.JButton jButton_eliminar;
    private javax.swing.JButton jButton_limpiar;
    private javax.swing.JComboBox<String> jComboBox_BuscarProducto;
    private javax.swing.JDialog jDialog_productos;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel_Wallpaper;
    private javax.swing.JLabel jLabel_Wallpaper1;
    private javax.swing.JLabel jLabel_WallpaperFooter;
    private javax.swing.JLabel jLabel_WallpaperHeader;
    private javax.swing.JLabel jLabel_WallpaperHeader2;
    private javax.swing.JLabel jLabel_mostrarFecha;
    private javax.swing.JLabel jLabel_total;
    private javax.swing.JLabel jLabel_vuelto;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable_productos;
    private javax.swing.JTable jTable_salidas;
    private javax.swing.JTextField jTextField_Cantidad;
    private javax.swing.JTextField jTextField_codigoProducto;
    private javax.swing.JTextField jTextField_precioVenta;
    private javax.swing.JTextField jTextField_recibido;
    private javax.swing.JTextField jTextField_stock;
    private javax.swing.JTextField txt_buscar;
    // End of variables declaration//GEN-END:variables

}
