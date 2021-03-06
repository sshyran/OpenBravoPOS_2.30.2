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

package com.openbravo.pos.forms;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Locale;
import java.util.logging.Level;

import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.api.SubstanceSkin;

import com.openbravo.format.Formats;
import com.openbravo.pos.instance.InstanceQuery;
import org.apache.log4j.Logger;

/**
 *
 * @author adrianromero
 */
public class StartPOS {

    private static final Logger logger = Logger.getLogger(StartPOS.class.getName());
    
    private static final String FULL_SCREEN = "fullscreen";
    
    /** Creates a new instance of StartPOS */
    private StartPOS() {
    }
    
    
    public static boolean registerApp() {
                       
        // vemos si existe alguna instancia        
        InstanceQuery i = null;
        try {
            i = new InstanceQuery();
            i.getAppMessage().restoreWindow();
            return false;
        } catch (RemoteException | NotBoundException e) {
            return true;
        }  
    }
    
    public static void main (String args[]) {
        logger.info("Hola inicio el uso de Logger");
        // log4j.appender.
        
                
        java.awt.EventQueue.invokeLater(() -> {
            if (!registerApp()) {
                System.exit(1);
            }
            //args[0] = System.getProperty("user.home") + File.separator + AppLocal.APP_ID + ".properties";
            AppConfig config = new AppConfig(args);
            config.load();
            
            // set Locale.
            String slang = config.getProperty("user.language");
            String scountry = config.getProperty("user.country");
            String svariant = config.getProperty("user.variant");
            if (slang != null && !slang.equals("") && scountry != null && svariant != null) {
                Locale.setDefault(new Locale(slang, scountry, svariant));
            }
            
            // Set the format patterns
            Formats.setIntegerPattern(config.getProperty("format.integer"));
            Formats.setDoublePattern(config.getProperty("format.double"));
            Formats.setCurrencyPattern(config.getProperty("format.currency"));
            Formats.setPercentPattern(config.getProperty("format.percent"));
            Formats.setDatePattern(config.getProperty("format.date"));
            Formats.setTimePattern(config.getProperty("format.time"));
            Formats.setDateTimePattern(config.getProperty("format.datetime"));
            
            // Set the look and feel.
            try {
                Object laf = Class.forName(config.getProperty("swing.defaultlaf")).newInstance();                
                if (laf instanceof LookAndFeel){
                    UIManager.setLookAndFeel((LookAndFeel) laf);
                } else if (laf instanceof SubstanceSkin) {
                    SubstanceLookAndFeel.setSkin((SubstanceSkin) laf);
                }
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
               // logger.log(Level.WARNING, "Cannot set look and feel", e);
               logger.info("Cannot set look and feel", e);
            }
            String screenmode = config.getProperty("machine.screenmode");
            JRootFrame rootframe = new JRootFrame(FULL_SCREEN.equals(screenmode));
            rootframe.initFrame(config);
        });    
    }    
}
