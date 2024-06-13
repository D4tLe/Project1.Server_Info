/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test.GUI;

/**
 *
 * @author leunaut
 */

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class ProcessPane extends JScrollPane {
    private JTable procTable;
    
    public ProcessPane() {
        procTable = new JTable();
        procTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
                "Name", "PID", "Status", "Memory", "Architect"
            }
        ) {
            Class[] types = new Class [] {
                String.class, Integer.class, Object.class, Double.class, Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        
        procTable.setAutoCreateRowSorter(true);
        
        DefaultTableModel model = (DefaultTableModel) procTable.getModel();
        
        ArrayList<Object[]> procInfo = ProcInfo.getProcessInfo();
        for (Object[] o: procInfo) {
            model.addRow(o);
        }
        
        setViewportView(procTable);
    }
}
