package com.ibm.issw.plugins.datapower.coproc2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;

public class AsyncConnectionTest implements Runnable {
   
   private String host;
   private String port;
   private Label messageField;
   
   public AsyncConnectionTest(String host, String port, Label messageField) {
      this.host = host;
      this.port = port;
      this.messageField = messageField;
   }

   public void run() {
      String urlStr = "http://" + host + ':' + port + "/coproc/version"; 
      String errorStr = null;
      String versionStr = null;
      String dateStr = null;
      try {
         URL url = new URL(urlStr);
         InputStream inStream = url.openStream();
         BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));
         String line = reader.readLine();
         while (line != null) {
            System.out.println(line);
            if (line.startsWith("Version")) {
               versionStr = line;
            } else if (line.startsWith("Date")) {
               dateStr = line;
            }
            line = reader.readLine();
         }
         reader.close();
      } catch (MalformedURLException badUrlEx) {
         errorStr = badUrlEx.getLocalizedMessage();
      } catch (IOException ioe) {
         errorStr = ioe.getLocalizedMessage();
      }
      if (errorStr != null) {
         setErrorMsg(errorStr);
      } else if (versionStr != null) {
         setInfoMsg("Connection success, " + versionStr);
      } else {
         setInfoMsg("Connect success, but no version info.");
      }
   }

   private void setErrorMsg(final String errorMsg) {
      Display display = messageField.getDisplay();
      display.syncExec(new Runnable() {
         public void run() {
            if (errorMsg.length() < 50) {
               messageField.setText(errorMsg);
            } else {
               messageField.setText("Error testing connection.");
               MessageBox msgbox = new MessageBox(messageField.getShell(), SWT.ICON_WARNING);
               msgbox.setText("Extended Error Information");
               msgbox.setMessage(errorMsg);
               msgbox.open();
            }
         }
      });
   }
   
   private void setInfoMsg(final String msg) {
      Display display = messageField.getDisplay();
      display.syncExec(new Runnable() {
         public void run() {
            messageField.setText(msg);
         }
      });
   }
}
