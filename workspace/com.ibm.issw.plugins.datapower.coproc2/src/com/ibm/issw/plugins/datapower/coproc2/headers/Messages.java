package com.ibm.issw.plugins.datapower.coproc2.headers;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
   private static final String BUNDLE_NAME = "com.ibm.issw.plugins.datapower.coproc2.headers.messages"; //$NON-NLS-1$
   public static String HeaderTable_AddButtonName;
   public static String HeaderTable_DefaultNameValue;
   public static String HeaderTable_DefaultValueValue;
   public static String HeaderTable_DeleteButtonName;
   public static String HeaderTable_ExportButtonName;
   public static String HeaderTable_ImportButtonName;
   public static String HeaderTable_NameColumnTitle;
   public static String HeaderTable_ValueColumnTitle;
   static {
      // initialize resource bundle
      NLS.initializeMessages(BUNDLE_NAME, Messages.class);
   }

   private Messages() {
   }
}
