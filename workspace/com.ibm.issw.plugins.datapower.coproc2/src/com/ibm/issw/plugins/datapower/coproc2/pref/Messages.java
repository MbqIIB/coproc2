package com.ibm.issw.plugins.datapower.coproc2.pref;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
   private static final String BUNDLE_NAME = "com.ibm.issw.plugins.datapower.coproc2.pref.messages"; //$NON-NLS-1$
   public static String CoProc2PreferencePage_Description;
   public static String CoProc2PreferencePage_HostFieldLabel;
   public static String CoProc2PreferencePage_PortFieldLabel;
   public static String CoProc2PreferencePage_SetInputToNullFieldLabel;
   static {
      // initialize resource bundle
      NLS.initializeMessages(BUNDLE_NAME, Messages.class);
   }

   private Messages() {
   }
}
