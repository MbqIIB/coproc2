package com.ibm.issw.plugins.datapower.coproc2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.ibm.issw.plugins.datapower.coproc2"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;
	
	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		createState();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
	
	private void createState() throws Exception {
      IPath statePath = getStateLocation();
      IPath inputPath = statePath.append("/input");
      File stateDir = inputPath.toFile();
      stateDir.mkdir();      
      Bundle bundle = getBundle();
      
      copyToBundleState("xml/empty.xml", inputPath.append("empty.xml"), bundle);
      copyToBundleState("xsl/id.xsl",    inputPath.append("id.xsl"), bundle);
      copyToBundleState("class/coproc2.class", inputPath.append("coproc2.class"), bundle);
	}
	
	private static void copyToBundleState(String fromPath, IPath toPath, Bundle bundle) {
	   IPath path = new Path(fromPath);
	   try {
	      InputStream is = FileLocator.openStream(bundle, path, false);
	      File newFile = toPath.toFile();
	      FileOutputStream fos = new FileOutputStream(newFile);
	      copy(is, fos);
	      is.close();
	      fos.close();
	   } catch (IOException ioe) {
	      System.out.println(Messages.Activator_IOExceptionMessage + toPath.lastSegment());
	   }
	}

   // copy method from From E.R. Harold's book "Java I/O"
   public static void copy(InputStream in, OutputStream out)
         throws IOException {

      // do not allow other threads to read from the
      // input or write to the output while copying is
      // taking place

      synchronized (in) {
         synchronized (out) {

            byte[] buffer = new byte[256];
            while (true) {
               int bytesRead = in.read(buffer);
               if (bytesRead == -1)
                  break;
               out.write(buffer, 0, bytesRead);
            }
         }
      }
   }
}
