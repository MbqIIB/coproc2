package com.ibm.issw.plugins.datapower.coproc2;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

public class CoProc2Log {

   public static void logInfo(String msg) {
      IStatus status = createStatus(IStatus.INFO, IStatus.OK, msg, null);
      log(status);
   }
   
   public static void logWarn(String msg) {
      IStatus status = createStatus(IStatus.WARNING, IStatus.OK, msg, null);
      log(status);
   }
   
   public static void logError(String msg, Throwable ex) {
      IStatus status = createStatus(IStatus.ERROR, IStatus.OK, msg, ex);
      log(status);
   }
   
   private static IStatus createStatus(int severity, int code, String msg, Throwable ex) {
      IStatus result = new Status(severity, CoProc2Constants.BUNDLE_NAME, code, msg, ex);
      return result;
   }
   
   private static void log(IStatus status) {
      Activator.getDefault().getLog().log(status);
   }
}
