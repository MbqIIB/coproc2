package com.ibm.issw.plugins.datapower.coproc2.headers;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;


public class HeaderLabelProvider extends LabelProvider implements
      ITableLabelProvider {

   public Image getColumnImage(Object element, int columnIndex) {
      return null;
   }

   public String getColumnText(Object element, int columnIndex) {
      String result = null;
      if (element instanceof HeaderEntry) {
         HeaderEntry header = (HeaderEntry)element;
         switch (columnIndex) {
            case 0: result = header.getName();
               break;
            case 1: result = header.getValue();
               break;
         }
      }
      return result;
   }

}
