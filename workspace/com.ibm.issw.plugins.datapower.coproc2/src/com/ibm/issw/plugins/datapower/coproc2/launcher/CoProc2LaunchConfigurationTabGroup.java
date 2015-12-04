package com.ibm.issw.plugins.datapower.coproc2.launcher;

import org.eclipse.debug.ui.AbstractLaunchConfigurationTabGroup;
import org.eclipse.debug.ui.CommonTab;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;

public class CoProc2LaunchConfigurationTabGroup extends
      AbstractLaunchConfigurationTabGroup {

   public void createTabs(ILaunchConfigurationDialog dialog, String mode) {
      ILaunchConfigurationTab[] tabs = new ILaunchConfigurationTab[] {
        new CoProc2LaunchConfigurationMainTab(),
        new CommonTab()
      };
      setTabs(tabs);
   }

}
