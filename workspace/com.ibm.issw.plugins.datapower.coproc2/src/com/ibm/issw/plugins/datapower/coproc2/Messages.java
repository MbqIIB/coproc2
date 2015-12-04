package com.ibm.issw.plugins.datapower.coproc2;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
   private static final String BUNDLE_NAME = "com.ibm.issw.plugins.datapower.coproc2.messages"; //$NON-NLS-1$
   public static String Activator_IOExceptionMessage;
   static {
      // initialize resource bundle
      NLS.initializeMessages(BUNDLE_NAME, Messages.class);
   }

   private Messages() {
   }
}
