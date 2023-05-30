/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author mb
 */
public class LimpiarTabla {
    public void Clear_Table(int filas, DefaultTableModel modelo) {
        //int filas = jTable_entradas.getRowCount();
        for (int i = 0; filas > i; i++) {
            modelo.removeRow(0);
        }
    }
}
