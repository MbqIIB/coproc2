package com.ibm.issw.plugins.datapower.coproc2.launcher;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.jdt.launching.JavaLaunchDelegate;

import com.ibm.issw.plugins.datapower.coproc2.Activator;
import com.ibm.issw.plugins.datapower.coproc2.CoProc2Constants;
import com.ibm.issw.plugins.datapower.coproc2.CoProc2Log;

public class CoProc2Launcher extends JavaLaunchDelegate {

	@Override
	public String getMainTypeName(ILaunchConfiguration config)
			throws CoreException {
	   return com.ibm.issw.plugins.datapower.coproc2.CoProc2.class.getName();
	}

	@Override
	public String getProgramArguments(ILaunchConfiguration config)	throws CoreException {
	   String result = null;
      String xmlFile = getXmlFile(config);
      String xslFile = config.getAttribute(CoProc2Constants.ATTR_INPUT_XSL, "[no XSL found]");
      String host = config.getAttribute(CoProc2Constants.ATTR_COPROC_HOST, "none");
      String port = config.getAttribute(CoProc2Constants.ATTR_COPROC_PORT, "1234");
      String url = "http://" + host + ":" + port + "/coproc/xsl";
      String headerFile = getHeaderFile(config);
      result = "\"" + xslFile + "\" \"" + xmlFile + "\" " + url + " \"" + headerFile + "\"";
      CoProc2Log.logInfo("Returning arguments = " + result);
		return result;
	}
	
   @Override
   public String[] getClasspath(ILaunchConfiguration config) throws CoreException {
      String[] result = super.getClasspath(config);
      try {
         File file = FileLocator.getBundleFile(Activator.getDefault().getBundle());
         if (file.isDirectory()) {
            CoProc2Log.logInfo("Adding directory to the classpath: " + file.toString());
            result = new String[] {file.toString() + "/bin"};
         } else {
            CoProc2Log.logInfo("Adding JAR file to the classpath: " + file.toString());
            result = new String[] {file.toString()};
         }
      } catch (IOException ioe) {
         CoProc2Log.logError("Error creating classpath: " + ioe.getLocalizedMessage(), ioe);
      }
      return result;
   }

   private String getXmlFile(ILaunchConfiguration config) throws CoreException {
	   String result = "[no XML found]";
	   boolean xmlNull = config.getAttribute(CoProc2Constants.ATTR_NULL_XML, true);
	   if (xmlNull) {
         IPath xmlPath = Activator.getDefault().getStateLocation().append("input/empty.xml");
         result = xmlPath.makeAbsolute().toOSString();
	   } else {
	      result = config.getAttribute(CoProc2Constants.ATTR_INPUT_XML, result);
      }
      return result;
	}
   
   private String getHeaderFile(ILaunchConfiguration config) throws CoreException {
      String result = "";
      PrintWriter writer = null;
      Map<String, String> values = config.getAttribute(CoProc2Constants.ATTR_HEADER_VALUES, (Map<String, String>)null);
      if (values != null) {
         IPath headerPath = Activator.getDefault().getStateLocation().append("input/header.props");
         File headerFile = headerPath.toFile();
         result = headerFile.getAbsolutePath();
         try {
            writer = new PrintWriter(new FileWriter(headerFile));
            for (String name : values.keySet()) {
               writer.println(name + '=' + values.get(name));
            }
         } catch (IOException e) {
            CoProc2Log.logError("Failed to open new header file for writing: " + result, e);
         } finally {
            writer.close();
         }
      }
      return result;
   }
}