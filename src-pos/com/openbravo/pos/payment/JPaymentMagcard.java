//    Openbravo POS is a point of sales application designed for touch screens.
//    Copyright (C) 2007-2009 Openbravo, S.L.
//    http://www.openbravo.com/product/pos
//
//    This file is part of Openbravo POS.
//
//    Openbravo POS is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    Openbravo POS is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with Openbravo POS.  If not, see <http://www.gnu.org/licenses/>.

package com.openbravo.pos.payment;

import com.openbravo.pos.customers.CustomerInfoExt;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppView;
import java.awt.BorderLayout;
import java.awt.Component;

/**
 *
 * @author  adrianromero
 */
public class JPaymentMagcard extends javax.swing.JPanel implements JPaymentInterface {
    
    private PaymentPanel m_cardpanel;
    private final PaymentGateway m_paymentgateway;
    private final JPaymentNotifier m_notifier;
    private String transaction;
    
    /** 
     * Creates new form JPaymentMagcard
     * @param app
     * @param notifier 
     */
    public JPaymentMagcard(AppView app, JPaymentNotifier notifier) {
        
        initComponents();   
        
        m_notifier = notifier;
        
        m_paymentgateway = PaymentGatewayFac.getPaymentGateway(app.getProperties());
        
        if (m_paymentgateway == null) {
            jlblMessage.setText(AppLocal.getIntString("message.nopaymentgateway"));            
        } else {           
            // Se van a poder efectuar pagos con tarjeta

            m_cardpanel = PaymentPanelFac.getPaymentPanel(app.getProperties().getProperty("payment.magcardreader"), notifier);
            if(m_cardpanel instanceof PaymentPanelKeyboard)
                ((PaymentPanelKeyboard)m_cardpanel).setAppView(app);
            super.add(m_cardpanel.getComponent(), BorderLayout.CENTER);
            jlblMessage.setText(null);
            // jlblMessage.setText(AppLocal.getIntString("message.nocardreader"));
        }
    }
    
    @Override
    public void activate(CustomerInfoExt customerext, double dTotal, String transID) {   
        this.transaction = transID;

        if (m_cardpanel == null) {
            jlblMessage.setText(AppLocal.getIntString("message.nopaymentgateway"));  
            m_notifier.setStatus(false, false);
        } else {
            jlblMessage.setText(null);
            m_cardpanel.activate(transaction, dTotal); 
            // The cardpanel sets the status
        }
    }
    @Override
    public PaymentInfo executePayment() {
        
        jlblMessage.setText(null);

        PaymentInfoMagcard payinfo = m_cardpanel.getPaymentInfoMagcard();
        m_paymentgateway.execute(payinfo);
        if (payinfo.isPaymentOK()) {
            return payinfo;
        } else {
            jlblMessage.setText(payinfo.getMessage());
            return null;
        }
    }  
    @Override
    public Component getComponent() {
        return this;
    }
    
    public void setTransaction(String transid){
        transaction = transid;
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        jlblMessage = new javax.swing.JTextArea();

        setLayout(new java.awt.BorderLayout());

        jlblMessage.setBackground(javax.swing.UIManager.getDefaults().getColor("Label.background"));
        jlblMessage.setEditable(false);
        jlblMessage.setLineWrap(true);
        jlblMessage.setWrapStyleWord(true);
        jlblMessage.setFocusable(false);
        jlblMessage.setPreferredSize(new java.awt.Dimension(300, 72));
        jlblMessage.setRequestFocusEnabled(false);
        jPanel1.add(jlblMessage);

        add(jPanel1, java.awt.BorderLayout.SOUTH);

    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextArea jlblMessage;
    // End of variables declaration//GEN-END:variables
    
}
