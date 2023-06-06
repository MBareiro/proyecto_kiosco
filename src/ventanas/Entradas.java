/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ventanas;

import java.math.BigDecimal;
import clases.Conexion;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author mb
 */
public class Entradas extends javax.swing.JFrame {

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

    public List productos;
    public List proveedores;
    public List categorias;

    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");

    Date date = new Date();
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    DateFormat labelFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");

    public String medida = "";

    public Entradas() {
        this.productos = TraerProductos();
        this.proveedores = TraerProveedores();
        this.categorias = TraerCategorias();
        initComponents();
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        jDialog_crearProducto.setSize(230, 290);
        jLabel_mostrarFecha.setText(labelFormat.format(date));

        jTextField_precioCompra.setEnabled(false);
        jTextField_precioVenta.setEnabled(false);
        jTextField_Cantidad.setEnabled(false);
        jTextField_Reserva.setEnabled(false);

        ((JTextField) jComboBox_proveedor.getEditor().getEditorComponent()).setOpaque(true);
        ((JTextField) jComboBox_Producto.getEditor().getEditorComponent()).setOpaque(true);

        CargarSugerenciasProds();
        CargarSugerenciasProvs();

        LLenarComboboxProds(productos);
        LLenarComboboxProvs(proveedores);
        LLenarComboboxCats(categorias);

        jComboBox_Producto.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent event) {
                if (event.getKeyChar() == KeyEvent.VK_ENTER) {
                    if (((JTextComponent) ((JComboBox) ((Component) event
                            .getSource()).getParent()).getEditor()
                            .getEditorComponent()).getText().isEmpty()) {
                        //vacio
                    } else {
                        MostrarProds(productos);
                    }
                }
            }
        });
        jComboBox_proveedor.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent event) {
                if (event.getKeyChar() == KeyEvent.VK_ENTER) {
                    if (((JTextComponent) ((JComboBox) ((Component) event
                            .getSource()).getParent()).getEditor()
                            .getEditorComponent()).getText().isEmpty()) {
                    } else {
                        MostrarProvs(proveedores);
                    }
                }
            }
        });

    }

    public void MostrarProds(List productos) {
        ArrayList tuLista = new ArrayList();
        for (int i = 0; i < productos.size(); i++) {
            tuLista = (ArrayList) productos.get(i);
            if (jComboBox_Producto.getSelectedIndex() > 0) {
                if (tuLista.get(1).toString().equals(jComboBox_Producto.getSelectedItem().toString())) {
                    jTextField_codigoProducto.setText((String) tuLista.get(0));
                }
            }
            if (!jTextField_codigoProducto.equals("")) {
                if (tuLista.get(0) == jTextField_codigoProducto.getText()) {
                    jComboBox_Producto.setSelectedItem((String) tuLista.get(1));
                }
            }
            medida = (String) tuLista.get(4);   
        }
    }

    private void MostrarProvs(List proveedores) {
        ArrayList tuLista = new ArrayList();
        for (int i = 0; i < proveedores.size(); i++) {
            tuLista = (ArrayList) proveedores.get(i);
            if (jComboBox_proveedor.getSelectedIndex() > 0) {
                if (tuLista.get(1).toString().equals(jComboBox_proveedor.getSelectedItem().toString())) {
                    jTextField_codigoProveedor.setText((String) tuLista.get(0));
                }
            }
            if (!jTextField_codigoProducto.equals("")) {
                if (tuLista.get(0) == jTextField_codigoProducto.getText()) {
                    jComboBox_Producto.setSelectedItem((String) tuLista.get(1));
                }
            }
        }
    }

    public List TraerProductos() {
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
            cn.close();
        } catch (SQLException e) {
            System.err.println("Error en registrar detalle " + e);
            JOptionPane.showMessageDialog(null, "Error al registrar, contacte al administrador");
        }
        return tuLista;
    }

    public List TraerCategorias() {
        ArrayList tuLista = new ArrayList();
        try {
            Connection cn = Conexion.conectar();
            PreparedStatement pst2 = cn.prepareStatement(
                    "SELECT id, nombre FROM categorias");
            ResultSet rs = pst2.executeQuery();
            while (rs.next()) {
                ArrayList reg = new ArrayList();
                reg.add(rs.getObject(1).toString());
                reg.add(rs.getObject(2).toString());
                tuLista.add(reg);
            }
            cn.close();
        } catch (SQLException e) {
            System.err.println("Error en TraerCategorias " + e);
            JOptionPane.showMessageDialog(null, "Error al registrar, contacte al administrador");
        }
        return tuLista;
    }

    private List TraerProveedores() {
        ArrayList tuLista = new ArrayList();
        try {

            Connection cn = Conexion.conectar();
            PreparedStatement pst2 = cn.prepareStatement(
                    "SELECT id, nombre FROM proveedores");
            ResultSet rs = pst2.executeQuery();
            while (rs.next()) {
                ArrayList reg = new ArrayList();
                reg.add(rs.getObject(1).toString());
                reg.add(rs.getObject(2).toString());
                tuLista.add(reg);
            }
            cn.close();
        } catch (SQLException e) {
            System.err.println("Error en registrar detalle " + e);
            JOptionPane.showMessageDialog(null, "Error al registrar, contacte al administrador");
        }

        return tuLista;
    }

    private void LLenarComboboxProds(List tuLista) {
        for (int i = 0; i < tuLista.size(); i++) {
            ArrayList<String> regs = new ArrayList<String>();
            regs = (ArrayList<String>) tuLista.get(i);
            jComboBox_Producto.addItem(regs.get(1).toString());
        }
    }

    private void LLenarComboboxCats(List tuLista) {
        for (int i = 0; i < tuLista.size(); i++) {
            ArrayList<String> regs = new ArrayList<String>();
            regs = (ArrayList<String>) tuLista.get(i);
            cmb_categoria.addItem(regs.get(1).toString());
        }
    }

    private void LLenarComboboxProvs(List tuLista) {
        for (int i = 0; i < tuLista.size(); i++) {
            ArrayList<String> regs = new ArrayList<String>();
            regs = (ArrayList<String>) tuLista.get(i);
            jComboBox_proveedor.addItem(regs.get(1).toString());
        }
    }

    private void CargarSugerenciasProds() {
        AutoCompleteDecorator.decorate(jComboBox_Producto);
    }

    private void CargarSugerenciasProvs() {
        AutoCompleteDecorator.decorate(jComboBox_proveedor);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog_crearProducto = new javax.swing.JDialog();
        txt_nombre = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        cmb_categoria = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        cmb_medida = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jButton_Guardar_Producto = new javax.swing.JButton();
        jLabel_WallpaperHeader3 = new javax.swing.JLabel();
        jLabel_Wallpaper3 = new javax.swing.JLabel();
        jDialog_crearProveedor = new javax.swing.JDialog();
        txt_direccion = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jButton_Guardar_Proveedor = new javax.swing.JButton();
        txt_telefono = new javax.swing.JTextField();
        txt_nombreProveedor = new javax.swing.JTextField();
        jLabel_WallpaperHeader4 = new javax.swing.JLabel();
        jLabel_Wallpaper4 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        jTextField_precioVenta = new javax.swing.JTextField();
        jButton_cargarProducto = new javax.swing.JButton();
        jButton_proveedores = new javax.swing.JButton();
        jLabel_total = new javax.swing.JLabel();
        jButton_Guardar = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTextField_precioCompra = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_entradas = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        jButton_buscar = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jTextField_codigoProducto = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jButton_eliminar = new javax.swing.JButton();
        jButton_limpiar = new javax.swing.JButton();
        jButton_Historial = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        jLabel_mostrarFecha = new javax.swing.JLabel();
        jComboBox_proveedor = new javax.swing.JComboBox<>();
        jComboBox_Producto = new javax.swing.JComboBox<>();
        jTextField_codigoProveedor = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextField_Cantidad = new javax.swing.JTextField();
        jTextField_Reserva = new javax.swing.JTextField();
        jLabel_WallpaperFooter = new javax.swing.JLabel();
        jLabel_WallpaperHeader = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel_medida = new javax.swing.JLabel();
        jLabel_medida1 = new javax.swing.JLabel();
        jLabel_Wallpaper = new javax.swing.JLabel();
        jLabel_Wallpaper1 = new javax.swing.JLabel();

        jDialog_crearProducto.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_nombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_nombreActionPerformed(evt);
            }
        });
        txt_nombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_nombreKeyReleased(evt);
            }
        });
        jDialog_crearProducto.getContentPane().add(txt_nombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 170, -1));

        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Nombre:");
        jDialog_crearProducto.getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, -1, -1));

        cmb_categoria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-" }));
        cmb_categoria.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmb_categoriaItemStateChanged(evt);
            }
        });
        cmb_categoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_categoriaActionPerformed(evt);
            }
        });
        jDialog_crearProducto.getContentPane().add(cmb_categoria, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, 170, -1));

        jLabel17.setForeground(new java.awt.Color(0, 0, 0));
        jLabel17.setText("Categoria:");
        jDialog_crearProducto.getContentPane().add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, -1, -1));

        cmb_medida.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-", "Unidad", "Litros", "Kilogramos" }));
        cmb_medida.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmb_medidaItemStateChanged(evt);
            }
        });
        jDialog_crearProducto.getContentPane().add(cmb_medida, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, 170, -1));

        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Medida:");
        jDialog_crearProducto.getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, -1, -1));

        jButton_Guardar_Producto.setText("Guardar");
        jButton_Guardar_Producto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_Guardar_ProductoActionPerformed(evt);
            }
        });
        jDialog_crearProducto.getContentPane().add(jButton_Guardar_Producto, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, 170, 30));

        jLabel_WallpaperHeader3.setBackground(new java.awt.Color(153, 153, 153));
        jLabel_WallpaperHeader3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel_WallpaperHeader3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_WallpaperHeader3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel_WallpaperHeader3.setText("        Crear Producto");
        jLabel_WallpaperHeader3.setOpaque(true);
        jDialog_crearProducto.getContentPane().add(jLabel_WallpaperHeader3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 230, 60));

        jLabel_Wallpaper3.setBackground(new java.awt.Color(204, 204, 204));
        jLabel_Wallpaper3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel_Wallpaper3.setOpaque(true);
        jDialog_crearProducto.getContentPane().add(jLabel_Wallpaper3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 230, 290));

        jDialog_crearProveedor.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_direccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_direccionActionPerformed(evt);
            }
        });
        txt_direccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_direccionKeyReleased(evt);
            }
        });
        jDialog_crearProveedor.getContentPane().add(txt_direccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 140, 170, -1));

        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Nombre:");
        jDialog_crearProveedor.getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, -1, -1));

        jLabel18.setForeground(new java.awt.Color(0, 0, 0));
        jLabel18.setText("Direccion:");
        jDialog_crearProveedor.getContentPane().add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 120, -1, -1));

        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Telefono:");
        jDialog_crearProveedor.getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 170, -1, -1));

        jButton_Guardar_Proveedor.setText("Guardar");
        jButton_Guardar_Proveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_Guardar_ProveedorActionPerformed(evt);
            }
        });
        jDialog_crearProveedor.getContentPane().add(jButton_Guardar_Proveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 240, 170, 30));

        txt_telefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_telefonoActionPerformed(evt);
            }
        });
        txt_telefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_telefonoKeyReleased(evt);
            }
        });
        jDialog_crearProveedor.getContentPane().add(txt_telefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 190, 170, -1));

        txt_nombreProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_nombreProveedorActionPerformed(evt);
            }
        });
        txt_nombreProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_nombreProveedorKeyReleased(evt);
            }
        });
        jDialog_crearProveedor.getContentPane().add(txt_nombreProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 90, 170, -1));

        jLabel_WallpaperHeader4.setBackground(new java.awt.Color(153, 153, 153));
        jLabel_WallpaperHeader4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel_WallpaperHeader4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_WallpaperHeader4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel_WallpaperHeader4.setText("        Registrar Proveedor");
        jLabel_WallpaperHeader4.setOpaque(true);
        jDialog_crearProveedor.getContentPane().add(jLabel_WallpaperHeader4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 260, 60));

        jLabel_Wallpaper4.setBackground(new java.awt.Color(204, 204, 204));
        jLabel_Wallpaper4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel_Wallpaper4.setOpaque(true);
        jDialog_crearProveedor.getContentPane().add(jLabel_Wallpaper4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 260, 290));

        jSeparator1.setBackground(new java.awt.Color(153, 153, 153));
        jSeparator1.setForeground(new java.awt.Color(204, 204, 204));
        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Reserva:");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 140, -1, -1));

        jTextField_precioVenta.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextField_precioVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_precioVentaActionPerformed(evt);
            }
        });
        jTextField_precioVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField_precioVentaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_precioVentaKeyTyped(evt);
            }
        });
        getContentPane().add(jTextField_precioVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 160, 110, -1));

        jButton_cargarProducto.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton_cargarProducto.setText("+");
        jButton_cargarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_cargarProductoActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_cargarProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 160, 40, -1));

        jButton_proveedores.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton_proveedores.setText("Registrar ");
        jButton_proveedores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_proveedoresActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_proveedores, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 90, 120, -1));

        jLabel_total.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel_total.setForeground(new java.awt.Color(255, 255, 255));
        getContentPane().add(jLabel_total, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 600, 500, 70));

        jButton_Guardar.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton_Guardar.setText("GUARDAR");
        jButton_Guardar.setEnabled(false);
        jButton_Guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_GuardarActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_Guardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 610, 200, 60));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Codigo:");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 70, -1, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Precio compra:");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 140, -1, -1));

        jTextField_precioCompra.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextField_precioCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_precioCompraActionPerformed(evt);
            }
        });
        jTextField_precioCompra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField_precioCompraKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField_precioCompraKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_precioCompraKeyTyped(evt);
            }
        });
        getContentPane().add(jTextField_precioCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 160, 110, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Precio venta:");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 140, -1, -1));

        jTable_entradas.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTable_entradas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cod. Prod.", "Nombre", "Cantidad", "Reserva", "Precio Compra", "Precio Venta", "Importe"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable_entradas.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                jTable_entradasComponentAdded(evt);
            }
        });
        jScrollPane1.setViewportView(jTable_entradas);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 200, 880, 340));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("TOTAL:");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 600, 180, 70));

        jButton_buscar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton_buscar.setText("Registrar");
        jButton_buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_buscarActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_buscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 90, 120, -1));

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("Proveedor:");
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 70, -1, -1));

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setText("Producto:");
        getContentPane().add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 70, -1, -1));

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
        getContentPane().add(jTextField_codigoProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 90, 70, -1));

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 0, 0));
        jLabel16.setText("Codigo:");
        getContentPane().add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, -1, -1));

        jButton_eliminar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton_eliminar.setText("Eliminar");
        jButton_eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_eliminarActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_eliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 550, 120, 30));

        jButton_limpiar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton_limpiar.setText("Limpiar");
        jButton_limpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_limpiarActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_limpiar, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 550, 120, 30));

        jButton_Historial.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton_Historial.setText("Historial");
        jButton_Historial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_HistorialActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_Historial, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 160, 110, -1));

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Fecha:");
        getContentPane().add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 20, -1, 20));

        jLabel_mostrarFecha.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel_mostrarFecha.setForeground(new java.awt.Color(255, 255, 255));
        getContentPane().add(jLabel_mostrarFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 20, 190, 20));

        jComboBox_proveedor.setEditable(true);
        jComboBox_proveedor.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jComboBox_proveedor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-" }));
        jComboBox_proveedor.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox_proveedorItemStateChanged(evt);
            }
        });
        jComboBox_proveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox_proveedorActionPerformed(evt);
            }
        });
        jComboBox_proveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComboBox_proveedorKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jComboBox_proveedorKeyReleased(evt);
            }
        });
        getContentPane().add(jComboBox_proveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 90, 150, -1));

        jComboBox_Producto.setEditable(true);
        jComboBox_Producto.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jComboBox_Producto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-" }));
        jComboBox_Producto.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox_ProductoItemStateChanged(evt);
            }
        });
        jComboBox_Producto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox_ProductoActionPerformed(evt);
            }
        });
        jComboBox_Producto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComboBox_ProductoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jComboBox_ProductoKeyReleased(evt);
            }
        });
        getContentPane().add(jComboBox_Producto, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 90, 150, -1));

        jTextField_codigoProveedor.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextField_codigoProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_codigoProveedorActionPerformed(evt);
            }
        });
        jTextField_codigoProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField_codigoProveedorKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_codigoProveedorKeyTyped(evt);
            }
        });
        getContentPane().add(jTextField_codigoProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 90, 70, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Cantidad:");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 140, -1, -1));

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
        getContentPane().add(jTextField_Cantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 160, 80, -1));

        jTextField_Reserva.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextField_Reserva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_ReservaActionPerformed(evt);
            }
        });
        jTextField_Reserva.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField_ReservaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_ReservaKeyTyped(evt);
            }
        });
        getContentPane().add(jTextField_Reserva, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 160, 80, -1));

        jLabel_WallpaperFooter.setBackground(new java.awt.Color(153, 153, 153));
        jLabel_WallpaperFooter.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel_WallpaperFooter.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_WallpaperFooter.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel_WallpaperFooter.setText("     Entradas");
        jLabel_WallpaperFooter.setOpaque(true);
        getContentPane().add(jLabel_WallpaperFooter, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 980, 60));

        jLabel_WallpaperHeader.setBackground(new java.awt.Color(0, 0, 0));
        jLabel_WallpaperHeader.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel_WallpaperHeader.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_WallpaperHeader.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel_WallpaperHeader.setOpaque(true);
        getContentPane().add(jLabel_WallpaperHeader, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 600, 980, 80));

        jSeparator2.setBackground(new java.awt.Color(153, 153, 153));
        jSeparator2.setForeground(new java.awt.Color(204, 204, 204));
        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);
        getContentPane().add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 70, 10, 50));

        jSeparator3.setBackground(new java.awt.Color(153, 153, 153));
        jSeparator3.setForeground(new java.awt.Color(204, 204, 204));
        getContentPane().add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 130, 880, 20));

        jLabel_medida.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel_medida.setForeground(new java.awt.Color(0, 0, 0));
        getContentPane().add(jLabel_medida, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 160, 30, 30));

        jLabel_medida1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel_medida1.setForeground(new java.awt.Color(0, 0, 0));
        getContentPane().add(jLabel_medida1, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 160, 30, 30));

        jLabel_Wallpaper.setBackground(new java.awt.Color(204, 204, 204));
        jLabel_Wallpaper.setForeground(new java.awt.Color(153, 153, 153));
        jLabel_Wallpaper.setOpaque(true);
        getContentPane().add(jLabel_Wallpaper, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 980, 680));

        jLabel_Wallpaper1.setBackground(new java.awt.Color(204, 204, 204));
        jLabel_Wallpaper1.setForeground(new java.awt.Color(153, 153, 153));
        jLabel_Wallpaper1.setOpaque(true);
        getContentPane().add(jLabel_Wallpaper1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 980, 680));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField_precioVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_precioVentaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_precioVentaActionPerformed

    private void jTextField_precioCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_precioCompraActionPerformed

    }//GEN-LAST:event_jTextField_precioCompraActionPerformed

    private void jButton_GuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_GuardarActionPerformed
        if (jTable_entradas.getRowCount() > 0) {
            try {
                BigDecimal total = new BigDecimal(Float.parseFloat(format.parse(jLabel_total.getText()).toString()));
                String date = dateFormat.format(new Date());
                Connection cn = Conexion.conectar();
                PreparedStatement pst2 = null;

                if (jTextField_codigoProveedor.getText().equals("")) {
                    String query = "INSERT INTO `entradas`(`fecha`, `importe_total`) VALUES (?,?)";
                    pst2 = cn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                    pst2.setString(1, date);
                    pst2.setBigDecimal(2, total.setScale(2, RoundingMode.HALF_UP));
                    pst2.executeUpdate();
                } else {
                    String query = "INSERT INTO `entradas`(`fecha`, `importe_total`, `id_proveedor`) VALUES (?,?,?)";
                    pst2 = cn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                    pst2.setString(1, date);
                    pst2.setBigDecimal(2, total.setScale(2, RoundingMode.HALF_UP));
                    pst2.setInt(3, Integer.parseInt(jTextField_codigoProveedor.getText()));
                    pst2.executeUpdate();
                }

                ResultSet res = (ResultSet) pst2.getGeneratedKeys();

                if (res.next()) {
                    id_entrada = res.getInt(1);
                }
                PreparedStatement pst3 = cn.prepareStatement(
                        "INSERT INTO `entradas_detalle`(`id_producto`, `cantidad`, `precio_compra`, `importe`, `id_entrada`) VALUES (?,?,?,?,?)");

                PreparedStatement pst4 = cn.prepareStatement(
                        "UPDATE productos SET cantidad = cantidad + ? , reserva = ?, `precio_venta` = ? WHERE id = ?");

                for (int i = 0; i < jTable_entradas.getRowCount(); i++) {
                    BigDecimal importe = new BigDecimal(format.parse((String) jTable_entradas.getValueAt(i, 6)).toString());
                    BigDecimal pcompra = new BigDecimal(format.parse((String) jTable_entradas.getValueAt(i, 4)).toString());
                    BigDecimal pventa = new BigDecimal(format.parse((String) jTable_entradas.getValueAt(i, 5)).toString());

                    pst3.setString(1, jTable_entradas.getValueAt(i, 0).toString());
                    pst3.setString(2, jTable_entradas.getValueAt(i, 2).toString());
                    pst3.setBigDecimal(3, pcompra.setScale(2, RoundingMode.HALF_UP));
                    pst3.setBigDecimal(4, importe.setScale(2, RoundingMode.HALF_UP));
                    pst3.setInt(5, id_entrada);
                    pst3.executeUpdate();

                    pst4.setString(1, jTable_entradas.getValueAt(i, 2).toString());
                    pst4.setString(2, jTable_entradas.getValueAt(i, 3).toString());
                    pst4.setBigDecimal(3, pventa.setScale(2, RoundingMode.HALF_UP));
                    pst4.setString(4, jTable_entradas.getValueAt(i, 0).toString());

                    pst4.executeUpdate();
                }

                cn.close();
                JOptionPane.showMessageDialog(null, "Registro exitoso!");
                Clear_Table(jTable_entradas.getRowCount(), modelo_entradas);
                jLabel_total.setText("");
                jButton_Guardar.setEnabled(false);

            } catch (SQLException e) {
                System.err.println("Error en registrar detalle " + e);
                JOptionPane.showMessageDialog(null, "Error al registrar, contacte al administrador " + e);
            } catch (ParseException ex) {
                Logger.getLogger(Entradas.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "La tabla no puede estar vacia.");
        }

        limpiarEntradas();
        jTextField_codigoProveedor.setText("");
        jComboBox_proveedor.setSelectedIndex(0);
        ((JTextField) jComboBox_proveedor.getEditor().getEditorComponent()).setBackground(Color.white);

        jTextField_codigoProveedor.setText("");
        jTextField_codigoProveedor.setBackground(Color.white);
    }//GEN-LAST:event_jButton_GuardarActionPerformed

    private void jButton_buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_buscarActionPerformed
        jDialog_crearProducto.setVisible(true);
        jDialog_crearProducto.setResizable(false);
        jDialog_crearProducto.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        jDialog_crearProducto.setSize(240, 325);
        Entradas entradas = new Entradas();
        jDialog_crearProducto.setLocationRelativeTo(entradas);
    }//GEN-LAST:event_jButton_buscarActionPerformed

    private void jButton_proveedoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_proveedoresActionPerformed
        jDialog_crearProveedor.setVisible(true);
        jDialog_crearProveedor.setResizable(false);
        jDialog_crearProveedor.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        jDialog_crearProveedor.setSize(275, 325);
        Entradas entradas = new Entradas();
        jDialog_crearProveedor.setLocationRelativeTo(entradas);
    }//GEN-LAST:event_jButton_proveedoresActionPerformed

    private void jButton_cargarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_cargarProductoActionPerformed

        if (validaciones()) {
            modelo_entradas = (DefaultTableModel) jTable_entradas.getModel();

            BigDecimal importe = new BigDecimal(0);
            BigDecimal pcompra = new BigDecimal(jTextField_precioCompra.getText()).setScale(2, RoundingMode.HALF_UP);
            BigDecimal pventa = new BigDecimal(jTextField_precioVenta.getText()).setScale(2, RoundingMode.HALF_UP);

            importe = BigDecimal.valueOf(Double.parseDouble(jTextField_Cantidad.getText())).multiply(BigDecimal.valueOf(Double.parseDouble(jTextField_precioCompra.getText())));

            jTable_entradas.setModel(modelo_entradas);

            BigDecimal total = new BigDecimal(0);
            BigDecimal importeSuma = new BigDecimal(0);
            boolean productoEnTabla = false;

            for (int i = 0; i < jTable_entradas.getRowCount(); i++) {
                if (jTable_entradas.getValueAt(i, 0).toString().equals(jTextField_codigoProducto.getText())) {
                    productoEnTabla = true;
                }
            }
            if (productoEnTabla) {
                JOptionPane.showMessageDialog(null, "El producto ya esta cargado en la tabla!");
            } else {
                //Se carga el registro en la tabla
                modelo_entradas.addRow(new Object[]{jTextField_codigoProducto.getText(), jComboBox_Producto.getSelectedItem(), jTextField_Cantidad.getText(), jTextField_Reserva.getText(), formato.format(pcompra), formato.format(pventa), formato.format(importe.setScale(2, RoundingMode.HALF_UP)), jComboBox_Producto.getSelectedIndex()});
            }

            for (int i = 0; i < jTable_entradas.getRowCount(); i++) {
                Number number;

                try {
                    number = format.parse(jTable_entradas.getValueAt(i, 4).toString());
                    importeSuma = BigDecimal.valueOf(Double.parseDouble(jTable_entradas.getValueAt(i, 2).toString())).multiply(BigDecimal.valueOf(Double.parseDouble(number.toString())));
                    total = total.add(importeSuma);
                } catch (ParseException ex) {
                    Logger.getLogger(Entradas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            jLabel_total.setText(formato.format(total).toString());

            if (jTable_entradas.getRowCount() > 0) {
                jButton_Guardar.setEnabled(true);
            } else {
                jButton_Guardar.setEnabled(false);
                jLabel_total.setText("");
            }
            limpiarEntradas();
        }
    }//GEN-LAST:event_jButton_cargarProductoActionPerformed

    public void limpiarEntradas() {
        jComboBox_Producto.setSelectedItem("-");
        ((JTextField) jComboBox_Producto.getEditor().getEditorComponent()).setBackground(Color.white);
        jTextField_codigoProducto.setText("");
        jTextField_codigoProducto.setBackground(Color.white);
        jTextField_codigoProveedor.setBackground(Color.white);
        ((JTextField) jComboBox_proveedor.getEditor().getEditorComponent()).setBackground(Color.white);

        jTextField_precioVenta.setText("");
        jTextField_precioCompra.setText("");
        jTextField_Cantidad.setText("");
        jTextField_Cantidad.setBackground(Color.white);
        jTextField_Reserva.setText("");
        jTextField_Reserva.setBackground(Color.white);
        jLabel_medida.setText("");
        jLabel_medida1.setText("");
    }

    private void jTable_entradasComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_jTable_entradasComponentAdded
        BigDecimal total = new BigDecimal(0);

        for (int i = 0; i < jTable_entradas.getRowCount(); i++) {
            total = BigDecimal.valueOf(Double.parseDouble(jTable_entradas.getValueAt(i, 2).toString())).multiply(BigDecimal.valueOf(Double.parseDouble(jTable_entradas.getValueAt(i, 0).toString())));
            jLabel_total.setText(total.toString());
        }
    }//GEN-LAST:event_jTable_entradasComponentAdded

    private void jTextField_codigoProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_codigoProductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_codigoProductoActionPerformed

    private void txt_nombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nombreActionPerformed

    private void txt_nombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nombreKeyReleased

    }//GEN-LAST:event_txt_nombreKeyReleased

    private void cmb_categoriaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmb_categoriaItemStateChanged

    }//GEN-LAST:event_cmb_categoriaItemStateChanged

    private void cmb_categoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_categoriaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmb_categoriaActionPerformed

    private void cmb_medidaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmb_medidaItemStateChanged

    }//GEN-LAST:event_cmb_medidaItemStateChanged

    private void jButton_Guardar_ProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_Guardar_ProductoActionPerformed
        int categoria, medida = 0;
        String nombre = null;

        if (txt_nombre.getText() != "" && cmb_categoria.getSelectedIndex() > 0 && cmb_medida.getSelectedIndex() > 0) {
            nombre = txt_nombre.getText().trim().toUpperCase().charAt(0) + txt_nombre.getText().substring(1, txt_nombre.getText().length()).toLowerCase().trim();
            categoria = cmb_categoria.getSelectedIndex() + 1;
            medida = cmb_medida.getSelectedIndex() + 1;
            try {
                Connection cn = Conexion.conectar();
                PreparedStatement pst1 = cn.prepareStatement(
                        "SELECT nombre FROM productos WHERE nombre = ?");
                pst1.setString(1, nombre.toUpperCase().charAt(0) + txt_nombre.getText().substring(1, txt_nombre.getText().length()).toLowerCase().trim());
                ResultSet rs = pst1.executeQuery();
                if (!rs.next()) {
                    PreparedStatement pst2 = cn.prepareStatement(
                            "INSERT INTO `productos`( `nombre`, `id_categoria`, `medida`, `cantidad`, `precio_venta`, `reserva` ) VALUES (?,?,?,?,?,?)",
                            Statement.RETURN_GENERATED_KEYS);
                    //pst2.setInt(1, 0);
                    pst2.setString(1, nombre);
                    pst2.setInt(2, categoria);
                    pst2.setInt(3, medida);
                    pst2.setInt(4, 0);
                    pst2.setInt(5, 0);
                    pst2.setInt(6, 0);
                    pst2.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Registro exitoso!");

                    ResultSet generatedKeys = pst2.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        String idGenerado = generatedKeys.getString(1);
                        productos = TraerProductos();
                        jComboBox_Producto.addItem(nombre);
                        jComboBox_Producto.setSelectedItem(nombre);
                        jTextField_codigoProducto.setText(idGenerado);
                        llenarTagsMedida();
                    }

                    txt_nombre.setText("");
                    cmb_medida.setSelectedIndex(0);
                    jDialog_crearProducto.setVisible(false);
                    jTextField_codigoProducto.setBackground(Color.white);
                    cn.close();
                    
                } else {
                    JOptionPane.showMessageDialog(null, "El producto ya existe.");
                }
            } catch (SQLException e) {
                System.err.println("Error en registrar usuario " + e);
                JOptionPane.showMessageDialog(null, "Error al registrar, contacte al administrador");
            } 
        } else {
            JOptionPane.showMessageDialog(null, "Debe llenar todos los campos");
        }
    }//GEN-LAST:event_jButton_Guardar_ProductoActionPerformed

    private void jButton_limpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_limpiarActionPerformed
        Clear_Table(jTable_entradas.getRowCount(), modelo_entradas);
    }//GEN-LAST:event_jButton_limpiarActionPerformed

    private void jButton_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_eliminarActionPerformed
        eliminarProducto();
    }//GEN-LAST:event_jButton_eliminarActionPerformed

    private void jButton_HistorialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_HistorialActionPerformed
        Entradas_Historial entradas_Historial = new Entradas_Historial();
        entradas_Historial.setVisible(true);

        entradas_Historial.setResizable(false);
        entradas_Historial.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        entradas_Historial.setSize(890, 710);
        entradas_Historial.setLocationRelativeTo(entradas_Historial);
    }//GEN-LAST:event_jButton_HistorialActionPerformed

    private void txt_direccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_direccionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_direccionActionPerformed

    private void txt_direccionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_direccionKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_direccionKeyReleased

    private void jButton_Guardar_ProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_Guardar_ProveedorActionPerformed

        String nombre, direccion, tel = null;

        nombre = txt_nombreProveedor.getText().trim();
        direccion = txt_direccion.getText().trim();
        tel = txt_telefono.getText().trim();
        if (!txt_nombreProveedor.getText().equals("") && !txt_direccion.getText().equals("") && !txt_telefono.getText().equals("")) {

            try {
                Connection cn = Conexion.conectar();
                PreparedStatement pst1 = cn.prepareStatement(
                        "SELECT nombre FROM proveedores WHERE nombre = '%" + nombre + "%'");
                ResultSet rs = pst1.executeQuery();

                if (!rs.next()) {
                    PreparedStatement pst2 = cn.prepareStatement(
                            "INSERT INTO `proveedores`( `nombre`, `direccion`, `telefono` ) VALUES (?,?,?)",
                            Statement.RETURN_GENERATED_KEYS);

                    pst2.setString(1, nombre.toUpperCase().charAt(0) + txt_nombreProveedor.getText().substring(1, txt_nombreProveedor.getText().length()).toLowerCase().trim());
                    pst2.setString(2, direccion.toUpperCase().charAt(0) + txt_direccion.getText().substring(1, txt_direccion.getText().length()).toLowerCase().trim());
                    pst2.setString(3, tel.toUpperCase().charAt(0) + txt_telefono.getText().substring(1, txt_telefono.getText().length()).toLowerCase().trim());
                    pst2.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Registro exitoso!");
                    proveedores = TraerProveedores();
                    LLenarComboboxProvs(proveedores);

                    ResultSet generatedKeys = pst2.getGeneratedKeys();

                    if (generatedKeys.next()) {
                        String idGenerado = generatedKeys.getString(1);
                        jComboBox_proveedor.setSelectedItem(nombre);
                        jTextField_codigoProveedor.setText(idGenerado);
                    }

                    txt_nombreProveedor.setText("");
                    txt_telefono.setText("");
                    txt_direccion.setText("");
                    jDialog_crearProveedor.setVisible(false);
                    cn.close();
                } else {
                    JOptionPane.showMessageDialog(null, "El proveedor ya existe.");
                }

            } catch (SQLException e) {
                System.err.println("Error en registrar usuario " + e);
                JOptionPane.showMessageDialog(null, "Error al registrar, contacte al administrador");
            }

        } else {
            JOptionPane.showMessageDialog(null, "Debe llenar todos los campos");
        }
    }//GEN-LAST:event_jButton_Guardar_ProveedorActionPerformed

    private void txt_telefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_telefonoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_telefonoActionPerformed

    private void txt_telefonoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_telefonoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_telefonoKeyReleased

    private void txt_nombreProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nombreProveedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nombreProveedorActionPerformed

    private void txt_nombreProveedorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nombreProveedorKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nombreProveedorKeyReleased

    private void jComboBox_proveedorItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox_proveedorItemStateChanged
        MostrarProvs(proveedores);
        validarProveedor();
        validarCodProveedor();
    }//GEN-LAST:event_jComboBox_proveedorItemStateChanged

    private void jComboBox_proveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox_proveedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox_proveedorActionPerformed

    private void jComboBox_proveedorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboBox_proveedorKeyPressed

    }//GEN-LAST:event_jComboBox_proveedorKeyPressed

    private void jComboBox_proveedorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboBox_proveedorKeyReleased

        validarBuscarProducto();
    }//GEN-LAST:event_jComboBox_proveedorKeyReleased

    private void jComboBox_ProductoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox_ProductoItemStateChanged
        MostrarProds(productos);
        validarBuscarProducto();
        validarCodProducto();
        // jComboBox_Producto.getSelectedItem()
        llenarTagsMedida();
        jTextField_precioVenta.setText("");
        jTextField_precioVenta.setBackground(Color.white);
        jTextField_precioCompra.setText("");
        jTextField_precioCompra.setBackground(Color.white);
        jTextField_Cantidad.setText("");
        jTextField_Cantidad.setBackground(Color.white);
        jTextField_Reserva.setText("");
        jTextField_Reserva.setBackground(Color.white);
    }//GEN-LAST:event_jComboBox_ProductoItemStateChanged


    private void jComboBox_ProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox_ProductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox_ProductoActionPerformed

    private void jComboBox_ProductoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboBox_ProductoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox_ProductoKeyPressed

    private void jComboBox_ProductoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboBox_ProductoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox_ProductoKeyReleased

    private void jTextField_codigoProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_codigoProveedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_codigoProveedorActionPerformed

    private void jTextField_codigoProveedorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_codigoProveedorKeyReleased
        try {
            Connection cn = Conexion.conectar();
            String valor = jTextField_codigoProveedor.getText();
            PreparedStatement pst = cn.prepareStatement(
                    "SELECT `nombre` FROM proveedores WHERE id= '" + valor + "'");
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                jComboBox_proveedor.setSelectedItem(rs.getObject(1));
            } else {
                jComboBox_proveedor.setSelectedItem("-");
            }
            cn.close();
        } catch (SQLException e) {
            System.err.println("Error al llenar tabla." + e);
            JOptionPane.showMessageDialog(null, "Error al mostrar informacion, contacte al administrador");
        } finally {
            validarCodProveedor();
        }
    }//GEN-LAST:event_jTextField_codigoProveedorKeyReleased

    private void jTextField_codigoProductoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_codigoProductoKeyReleased

        try {
            Connection cn = Conexion.conectar();
            String valor = jTextField_codigoProducto.getText();
            PreparedStatement pst = cn.prepareStatement(
                    "SELECT `nombre` FROM productos WHERE id= '" + valor + "'");
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                jComboBox_Producto.setSelectedItem(rs.getObject(1));
            } else {
                jComboBox_Producto.setSelectedItem("-");
            }
            cn.close();
        } catch (SQLException e) {
            System.err.println("Error al llenar tabla." + e);
            JOptionPane.showMessageDialog(null, "Error al mostrar informacion, contacte al administrador");
        } finally {
            validarCodProducto();
        }
    }//GEN-LAST:event_jTextField_codigoProductoKeyReleased

    private void jTextField_precioCompraKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_precioCompraKeyReleased
        validarPrecioCompra();

    }//GEN-LAST:event_jTextField_precioCompraKeyReleased

    private void jTextField_precioVentaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_precioVentaKeyReleased
        validarPrecioVenta();
    }//GEN-LAST:event_jTextField_precioVentaKeyReleased

    private void jTextField_precioCompraKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_precioCompraKeyTyped
        char caracter = evt.getKeyChar();

        if (jTextField_precioCompra.getText().length() > 0) {
            if ((caracter < '0' || caracter > '9')
                    && (caracter != '.' || jTextField_precioCompra.getText().contains("."))
                    && caracter != KeyEvent.VK_BACK_SPACE) {
                evt.consume();
            }
            if (jTextField_precioCompra.getText().contains(".")) {
                validarDecimales(evt, jTextField_precioCompra, 2);
            }
        } else {
            //Termina si empieza con un punto
            if (caracter == '.') {
                evt.consume();
            }
            if ((caracter < '0' || caracter > '9')
                    && (caracter != '.' || jTextField_precioCompra.getText().contains("."))
                    && caracter != KeyEvent.VK_BACK_SPACE) {
                evt.consume();
            }
            if (jTextField_precioCompra.getText().contains(".")) {
                validarDecimales(evt, txt_direccion, 2);
            }
        }
    }//GEN-LAST:event_jTextField_precioCompraKeyTyped


    private void jTextField_precioCompraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_precioCompraKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_precioCompraKeyPressed

    private void jTextField_precioVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_precioVentaKeyTyped
        char caracter = evt.getKeyChar();
        if ((caracter < '0' || caracter > '9')
                && (caracter != '.' || jTextField_precioVenta.getText().contains("."))
                && caracter != KeyEvent.VK_BACK_SPACE) {
            evt.consume();
        }
        if (jTextField_precioVenta.getText().contains(".")) {
            validarDecimales(evt, jTextField_precioVenta, 2);
        }
        puntoAlPrincipio(evt, jTextField_precioVenta, caracter);
    }//GEN-LAST:event_jTextField_precioVentaKeyTyped

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

    private void jTextField_ReservaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_ReservaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_ReservaActionPerformed

    private void jTextField_ReservaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_ReservaKeyReleased
        validarReserva();
    }//GEN-LAST:event_jTextField_ReservaKeyReleased

    private void jTextField_ReservaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_ReservaKeyTyped
        char caracter = evt.getKeyChar();
        if (medida.equals("4") || medida.equals("3")) {
            if ((caracter < '0' || caracter > '9')
                    && (caracter != '.' || jTextField_Reserva.getText().contains("."))
                    && caracter != KeyEvent.VK_BACK_SPACE) {
                evt.consume();
            }
            validarDecimales(evt, jTextField_Reserva, 3);
        } else if (medida.equals("2")) {
            if ((caracter < '0' || caracter > '9') && caracter != KeyEvent.VK_BACK_SPACE && !jTextField_Reserva.getText().contains(".")) {
                evt.consume();
            }
        }
        puntoAlPrincipio(evt, jTextField_Reserva, caracter);
    }//GEN-LAST:event_jTextField_ReservaKeyTyped

    private void jTextField_codigoProductoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_codigoProductoKeyTyped

        char caracter = evt.getKeyChar();
        controlIngresoSinPunto(evt, jTextField_codigoProducto, caracter);
    }//GEN-LAST:event_jTextField_codigoProductoKeyTyped

    private void jTextField_codigoProveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_codigoProveedorKeyTyped
        char caracter = evt.getKeyChar();
        controlIngresoSinPunto(evt, jTextField_codigoProducto, caracter);
    }//GEN-LAST:event_jTextField_codigoProveedorKeyTyped

    public void puntoAlPrincipio(java.awt.event.KeyEvent evt, JTextField cuadrito, char caracter) {
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

    public void masDeUnPunto(java.awt.event.KeyEvent evt, JTextField cuadrito) {

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
            if (resul.length() >= 3) {
                evt.consume();
            }
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

    public void controlIngresoSinPunto(java.awt.event.KeyEvent evt, JTextField cuadrito, char caracter) {
        controlIngreso(evt, cuadrito, caracter);
        if (caracter == '.') {
            evt.consume();
        }

    }

    private void Clear_Table(int filas, DefaultTableModel modelo) {
        //int filas = jTable_entradas.getRowCount();
        for (int i = 0; filas > i; i++) {
            modelo.removeRow(0);
        }
    }

    public void eliminarProducto() {
        if (jTable_entradas.getSelectedRow() >= 0) {
            int confirmado = JOptionPane.showConfirmDialog(null, "¿Esta seguro?",
                    "Borrar", JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.INFORMATION_MESSAGE);
            if (JOptionPane.OK_OPTION == confirmado) {
                int numRows = jTable_entradas.getSelectedRows().length;
                for (int i = 0; i < numRows; i++) {
                    modelo_entradas.removeRow(jTable_entradas.getSelectedRow());
                }
            }
        }
    }

    public boolean validaciones() {
        boolean validado = true;
        boolean completo = true;
        boolean stockSuficiente = true;
        if (validarCantidad() == 1) {
            completo = false;
        } else if (validarCantidad() == 2) {
            stockSuficiente = false;
        }
        if (!validarReserva()) {
            completo = false;
        }

        if (!validarCodProducto()) {
            completo = false;
        }
        if (!validarPrecioCompra()) {
            completo = false;
        }
        if (!validarPrecioVenta()) {
            completo = false;
        }
        if (!validarBuscarProducto()) {
            completo = false;
        }
        if (!completo) {
            JOptionPane.showMessageDialog(null, "Complete los campos resaltados.");
        } else if (!stockSuficiente) {
            JOptionPane.showMessageDialog(null, "Stock insuficiente.");
        }
        if (!completo || !stockSuficiente) {
            validado = false;
        }
        return validado;
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

    public boolean validarReserva() {
        boolean validado = true;
        if (!jTextField_Reserva.getText().equals("")) {
            if (Float.parseFloat(jTextField_Reserva.getText()) <= 0) {
                validado = false;
                jTextField_Reserva.setBackground(Color.orange);
            } else {
                jTextField_Reserva.setBackground(Color.white);
            }
        } else {
            validado = false;
            jTextField_Reserva.setBackground(Color.orange);
        }
        return validado;
    }

    public boolean validarProveedor() {
        boolean completo = true;
        if (jComboBox_proveedor.getSelectedItem().equals("-") || jComboBox_proveedor.getSelectedItem().equals("")) {
            completo = false;
            ((JTextField) jComboBox_proveedor.getEditor().getEditorComponent()).setBackground(Color.orange);
            jTextField_codigoProveedor.setText("");
        } else {
            ((JTextField) jComboBox_proveedor.getEditor().getEditorComponent()).setBackground(Color.white);
        }
        return completo;
    }

    public boolean validarCodProveedor() {
        boolean completo = true;
        if (jTextField_codigoProveedor.getText().equals("")) {
            completo = false;
            jTextField_codigoProveedor.setBackground(Color.orange);
        } else {
            jTextField_codigoProveedor.setBackground(Color.white);
        }
        return completo;
    }

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
        if (jComboBox_Producto.getSelectedItem().equals("-") || jComboBox_Producto.getSelectedItem().equals("")) {
            completo = false;
            ((JTextField) jComboBox_Producto.getEditor().getEditorComponent()).setBackground(Color.orange);
            jTextField_codigoProducto.setText("");
            deshabilitarEntradas();
        } else {
            ((JTextField) jComboBox_Producto.getEditor().getEditorComponent()).setBackground(Color.white);
            habilitarEntradas();

        }
        return completo;
    }

    public void habilitarEntradas() {
        jTextField_precioCompra.setEnabled(true);
        jTextField_precioVenta.setEnabled(true);
        jTextField_Cantidad.setEnabled(true);
        jTextField_Reserva.setEnabled(true);
    }

    public void deshabilitarEntradas() {
        jTextField_precioCompra.setEnabled(false);
        jTextField_precioVenta.setEnabled(false);
        jTextField_Cantidad.setEnabled(false);
        jTextField_Reserva.setEnabled(false);
    }

    public boolean validarPrecioCompra() {
        boolean completo = true;
        if (jTextField_precioCompra.getText().equals("")) {
            completo = false;
            jTextField_precioCompra.setBackground(Color.orange);
        } else {
            jTextField_precioCompra.setBackground(Color.white);
        }
        return completo;
    }

    public boolean validarPrecioVenta() {
        boolean completo = true;
        if (jTextField_precioVenta.getText().equals("")) {
            completo = false;
            jTextField_precioVenta.setBackground(Color.orange);
        } else {
            jTextField_precioVenta.setBackground(Color.white);
        }
        return completo;
    }

    public void llenarTagsMedida() {
        for (int i = 0; i < productos.size(); i++) {
            List lista = (List) productos.get(i);
            if (jComboBox_Producto.getSelectedItem().equals(lista.get(1))) {
                if (lista.get(4).toString().equals("1")) {
                    jLabel_medida.setText("u.");
                    jLabel_medida1.setText("u.");
                } else if (lista.get(4).toString().equals("2")) {
                    jLabel_medida.setText("lt.");
                    jLabel_medida1.setText("lt.");
                } else {
                    jLabel_medida.setText("kg.");
                    jLabel_medida1.setText("kg.");
                }
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
            java.util.logging.Logger.getLogger(Entradas.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Entradas.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Entradas.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Entradas.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Entradas().setVisible(true);

            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cmb_categoria;
    private javax.swing.JComboBox<String> cmb_medida;
    private javax.swing.JButton jButton_Guardar;
    private javax.swing.JButton jButton_Guardar_Producto;
    private javax.swing.JButton jButton_Guardar_Proveedor;
    private javax.swing.JButton jButton_Historial;
    private javax.swing.JButton jButton_buscar;
    private javax.swing.JButton jButton_cargarProducto;
    private javax.swing.JButton jButton_eliminar;
    private javax.swing.JButton jButton_limpiar;
    private javax.swing.JButton jButton_proveedores;
    private javax.swing.JComboBox<String> jComboBox_Producto;
    private javax.swing.JComboBox<String> jComboBox_proveedor;
    private javax.swing.JDialog jDialog_crearProducto;
    private javax.swing.JDialog jDialog_crearProveedor;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel_Wallpaper;
    private javax.swing.JLabel jLabel_Wallpaper1;
    private javax.swing.JLabel jLabel_Wallpaper3;
    private javax.swing.JLabel jLabel_Wallpaper4;
    private javax.swing.JLabel jLabel_WallpaperFooter;
    private javax.swing.JLabel jLabel_WallpaperHeader;
    private javax.swing.JLabel jLabel_WallpaperHeader3;
    private javax.swing.JLabel jLabel_WallpaperHeader4;
    private javax.swing.JLabel jLabel_medida;
    private javax.swing.JLabel jLabel_medida1;
    private javax.swing.JLabel jLabel_mostrarFecha;
    private javax.swing.JLabel jLabel_total;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTable jTable_entradas;
    private javax.swing.JTextField jTextField_Cantidad;
    private javax.swing.JTextField jTextField_Reserva;
    private javax.swing.JTextField jTextField_codigoProducto;
    private javax.swing.JTextField jTextField_codigoProveedor;
    private javax.swing.JTextField jTextField_precioCompra;
    private javax.swing.JTextField jTextField_precioVenta;
    private javax.swing.JTextField txt_direccion;
    private javax.swing.JTextField txt_nombre;
    private javax.swing.JTextField txt_nombreProveedor;
    private javax.swing.JTextField txt_telefono;
    // End of variables declaration//GEN-END:variables
}
