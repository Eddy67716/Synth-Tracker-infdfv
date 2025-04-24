/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.view.pattens;

import java.awt.BorderLayout;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTable;

/**
 *
 * @author Edward Jenkins
 */
public class OrderView extends JPanel {
    
    // instance variables
    private int modType;
    private short[] orders;
    private JList<Short> orderList;
    
    // constructor
    public OrderView(int modType, short[] orders) {
        this.modType = modType;
        this.orders = orders;
        init();
    }
    
    // getters
    
    public void init() {
        setLayout(new BorderLayout());
        orderList = new JList<>();
        Short[] listData = new Short[orders.length];
        for (int i = 0; i < orders.length; i++) {
            listData[i] = orders[i];
        }
        orderList.setListData(listData);
        add(orderList, BorderLayout.CENTER);
    }
}
