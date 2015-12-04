package com.ibm.issw.plugins.datapower.coproc2.launcher;

import static com.ibm.issw.plugins.datapower.coproc2.CoProc2Constants.ATTR_COPROC_HOST;
import static com.ibm.issw.plugins.datapower.coproc2.CoProc2Constants.ATTR_COPROC_PORT;
import static com.ibm.issw.plugins.datapower.coproc2.CoProc2Constants.ATTR_INPUT_XML;
import static com.ibm.issw.plugins.datapower.coproc2.CoProc2Constants.ATTR_INPUT_XSL;
import static com.ibm.issw.plugins.datapower.coproc2.CoProc2Constants.ATTR_NULL_XML;
import static com.ibm.issw.plugins.datapower.coproc2.CoProc2Constants.ATTR_HEADER_VALUES;
import static com.ibm.issw.plugins.datapower.coproc2.CoProc2Constants.ATTR_HEADER_STATUS;
import static com.ibm.issw.plugins.datapower.coproc2.CoProc2Constants.DFLT_COPROC_HOST;
import static com.ibm.issw.plugins.datapower.coproc2.CoProc2Constants.DFLT_COPROC_PORT;
import static com.ibm.issw.plugins.datapower.coproc2.CoProc2Constants.DFLT_INPUT_XML;
import static com.ibm.issw.plugins.datapower.coproc2.CoProc2Constants.DFLT_INPUT_XSL;
import static com.ibm.issw.plugins.datapower.coproc2.CoProc2Constants.DFLT_NULL_XML;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.ibm.issw.plugins.datapower.coproc2.Activator;
import com.ibm.issw.plugins.datapower.coproc2.AsyncConnectionTest;
import com.ibm.issw.plugins.datapower.coproc2.headers.HeaderEntry;
import com.ibm.issw.plugins.datapower.coproc2.headers.HeaderTable;
import com.ibm.issw.plugins.datapower.coproc2.pref.PreferenceConstants;


public class CoProc2LaunchConfigurationMainTab extends
		AbstractLaunchConfigurationTab {
   
   private Map<String, String> defaultHeaderValues;
   private Map<String, String> defaultHeaderStatus;
   
   private Text xslFileText;
   private Button browseXslButton;
   
   private Text xmlFileText;
   private Button setXmlInputNull;
   private Button browseXmlButton;
   
   private Text coprocHostText;
   private Text coprocPortText;
   private Button testUrl;
   private Label statusLabel;
   
   private HeaderTable headerTable;
   
   private ModifyListener modifyListener = new ModifyListener() {
      public void modifyText(ModifyEvent e) {
         setDirty(true);
         updateLaunchConfigurationDialog();
      }      
   };
   
   public CoProc2LaunchConfigurationMainTab() {
      super();
      defaultHeaderValues = new HashMap<String, String>();
      defaultHeaderValues.put("Content-Type", "text/xml; charset=utf-8"); //$NON-NLS-1$ //$NON-NLS-2$
      
      defaultHeaderStatus = new HashMap<String, String>();
      defaultHeaderStatus.put("Content-Type", "true"); //$NON-NLS-1$ //$NON-NLS-2$
   }
   
   public void createControl(Composite parent) {
	   GridLayout layout = new GridLayout();
	   layout.numColumns = 1;
	   GridData gd = new GridData();
	   gd.grabExcessHorizontalSpace = true;
	   
		Composite composite = new Composite(parent, SWT.NULL);
		composite.setFont(parent.getFont());
		composite.setLayout(layout);
		composite.setLayoutData(gd);
		setControl(composite);
		addUrlGroup(composite);
		addXslGroup(composite);
		addXmlGroup(composite);
		addHeaderGroup(composite);
	}

	public void setDefaults(ILaunchConfigurationWorkingCopy config) {
	   IPreferenceStore store = Activator.getDefault().getPreferenceStore();
	   String dftHost = store.getString(PreferenceConstants.PREF_HOST);
	   String dftPort = store.getString(PreferenceConstants.PREF_PORT);
	   boolean dftNullInput = store.getBoolean(PreferenceConstants.PREF_NULL_INPUT);
	   config.setAttribute(ATTR_COPROC_HOST, dftHost);
	   config.setAttribute(ATTR_COPROC_PORT, dftPort);
	   config.setAttribute(ATTR_INPUT_XML,   DFLT_INPUT_XML);
	   config.setAttribute(ATTR_INPUT_XSL,   DFLT_INPUT_XSL);
	   config.setAttribute(ATTR_NULL_XML,    dftNullInput);
	   
	   config.setAttribute(ATTR_HEADER_VALUES, defaultHeaderValues);
	   config.setAttribute(Messages.CoProc2LaunchConfigurationMainTab_HeaderStatusColumnTitle, defaultHeaderStatus);
	}

	public void initializeFrom(ILaunchConfiguration config) {
	   String propStr = null;
	   try {
	      propStr = config.getAttribute(ATTR_COPROC_HOST, DFLT_COPROC_HOST);
	   } catch (CoreException ce) {
	      propStr = DFLT_COPROC_HOST;
	   }
	   coprocHostText.setText(propStr);
	   
	   try {
	      propStr = config.getAttribute(ATTR_COPROC_PORT, DFLT_COPROC_PORT);
	   } catch (CoreException ce) {
	      propStr = DFLT_COPROC_PORT;
	   }
	   coprocPortText.setText(propStr);
	   
	   try {
	      propStr = config.getAttribute(ATTR_INPUT_XML, DFLT_INPUT_XML);
	   } catch (CoreException ce) {
	      propStr = DFLT_INPUT_XML;
	   }
	   xmlFileText.setText(propStr);
	   
	   try {
	      propStr = config.getAttribute(ATTR_INPUT_XSL, DFLT_INPUT_XSL);
	   } catch (CoreException ce) {
	      propStr = DFLT_INPUT_XSL;
	   }
	   xslFileText.setText(propStr);
	   
	   boolean noXml = DFLT_NULL_XML;
	   try {
	      noXml = config.getAttribute(ATTR_NULL_XML, DFLT_NULL_XML);
	   } catch (CoreException ce) {
	      noXml = DFLT_NULL_XML;
	   }
	   setXmlInputNull.setSelection(noXml);
	   xmlFileText.setEnabled(!noXml);
	   browseXmlButton.setEnabled(!noXml);
	   
	   ArrayList<HeaderEntry> entries = new ArrayList<HeaderEntry>();
	   HeaderEntry entry = null;
	   boolean enabled = true;
	   try {
	      Map<String, String> values = config.getAttribute(ATTR_HEADER_VALUES, defaultHeaderValues);
	      Map<String, String> status = config.getAttribute(ATTR_HEADER_STATUS, defaultHeaderStatus);
	      for (String name : values.keySet()) {
	         enabled = Boolean.valueOf(status.get(name));
	         entry = new HeaderEntry(name, values.get(name), enabled);
	         entries.add(entry);
	      }
	   } catch (CoreException ce) {
	      // If there is a core exception from the configuration.
	      // set values to their defaults.
	      //
         Map<String, String> values = defaultHeaderValues;
         Map<String, String> status = defaultHeaderStatus;
         for (String name : values.keySet()) {
            enabled = Boolean.valueOf(status.get(name));
            entry = new HeaderEntry(name, values.get(name), enabled);
            entries.add(entry);
         }	      
	   }
	   headerTable.setInput(entries);
	}

	public void performApply(ILaunchConfigurationWorkingCopy config) {
	   config.setAttribute(ATTR_COPROC_HOST, coprocHostText.getText());
	   config.setAttribute(ATTR_COPROC_PORT, coprocPortText.getText());
	   config.setAttribute(ATTR_INPUT_XML,   xmlFileText.getText());
	   config.setAttribute(ATTR_INPUT_XSL,   xslFileText.getText());
	   config.setAttribute(ATTR_NULL_XML,    setXmlInputNull.getSelection());
	   config.setAttribute(ATTR_HEADER_VALUES,     headerTable.getEntries());
	}

	public String getName() {
		return Messages.CoProc2LaunchConfigurationMainTab_TabName;
	}
	
	@Override
   public boolean isValid(ILaunchConfiguration launchConfig) {
	   setErrorMessage(null);
	   boolean result = true;
	   if (xslFileText.getText().length() == 0) {
	      result = false;
	      setErrorMessage(Messages.CoProc2LaunchConfigurationMainTab_MustSpecifyXslFile);
	   }
	   if (!setXmlInputNull.getSelection()  &&  xmlFileText.getText().length() == 0) {
	      result = false;
	      setErrorMessage(Messages.CoProc2LaunchConfigurationMainTab_MustSpecifyXmlFile);
	   }
	   if (coprocHostText.getText().length() == 0) {
	      result = false;
	      setErrorMessage(Messages.CoProc2LaunchConfigurationMainTab_MustSpecifyCoProc2Host);
	   } else if (coprocHostText.getText().indexOf(' ') != -1  ||
	              coprocHostText.getText().indexOf('\t') != -1) {
	      result = false;
	      setErrorMessage(Messages.CoProc2LaunchConfigurationMainTab_NoSpaceInHost);
	   }
	   try {
	      int port = Integer.parseInt(coprocPortText.getText());
	      if (port < 1  ||  port > 65535) {
	         result = false;
	         setErrorMessage(Messages.CoProc2LaunchConfigurationMainTab_PortOutOfRange);
	      }
	   } catch (NumberFormatException nfe) {
	      result = false;
	      setErrorMessage(Messages.CoProc2LaunchConfigurationMainTab_PortNotAnInteger);
	   }
      return result;
   }

   @Override
   public boolean canSave() {
      return true;
   }

   private void addXslGroup(Composite parent) {
	   Group group = new Group(parent, SWT.NONE);
	   group.setText(Messages.CoProc2LaunchConfigurationMainTab_StylesheetGroupName);
	   GridLayout layout = new GridLayout();
	   layout.numColumns = 3;
	   group.setLayout(layout);
	   GridData gd = new GridData();
	   gd.grabExcessHorizontalSpace = true;
	   gd.horizontalAlignment = SWT.FILL;
	   group.setLayoutData(gd);
	   
	   Label label = new Label(group, SWT.NONE);
	   label.setText(Messages.CoProc2LaunchConfigurationMainTab_FileField + ':');
	   
	   xslFileText = new Text(group, SWT.BORDER);
	   gd = new GridData();
	   gd.horizontalAlignment = SWT.FILL;
	   gd.grabExcessHorizontalSpace = true;
	   xslFileText.setLayoutData(gd);
	   xslFileText.addModifyListener(modifyListener);
	   
	   browseXslButton = new Button(group, SWT.PUSH);
	   browseXslButton.setText(Messages.CoProc2LaunchConfigurationMainTab_BrowseButton);
	   browseXslButton.addSelectionListener(new SelectionAdapter() {
         public void widgetSelected(SelectionEvent evt) {
            String[] extensions = new String[] {"*.xsl", "*.xslt"}; //$NON-NLS-1$ //$NON-NLS-2$
            String fileName = browseForSourceFile(extensions);
            if (fileName != null) {
               xslFileText.setText(fileName);
            }
         }
	   });
	}
	
	private void addXmlGroup(Composite parent) {
	   Group group = new Group(parent, SWT.NONE);
	   group.setText(Messages.CoProc2LaunchConfigurationMainTab_InputXmlGroupName);
	   GridLayout layout = new GridLayout();
	   layout.numColumns = 3;
	   group.setLayout(layout);
	   GridData gd = new GridData();
	   gd.grabExcessHorizontalSpace = true;
	   gd.horizontalAlignment = SWT.FILL;
	   group.setLayoutData(gd);
	   
	   setXmlInputNull = new Button(group, SWT.CHECK);
	   setXmlInputNull.setText(Messages.CoProc2LaunchConfigurationMainTab_SetInputToNullCheckbox);
	   setXmlInputNull.setToolTipText(Messages.CoProc2LaunchConfigurationMainTab_SetInputToNullTooltip);
	   gd = new GridData();
	   gd.horizontalSpan = 3;
	   setXmlInputNull.setLayoutData(gd);
	   setXmlInputNull.addSelectionListener(new SelectionAdapter() {
	      public void widgetSelected(SelectionEvent evt) {
            xmlFileText.setEnabled(!setXmlInputNull.getSelection());
            browseXmlButton.setEnabled(!setXmlInputNull.getSelection());
            setDirty(true);
            updateLaunchConfigurationDialog();
	      }
	   });
	   
	   Label label = new Label(group, SWT.NONE);
	   label.setText(Messages.CoProc2LaunchConfigurationMainTab_FileField + ':');
	   
	   xmlFileText = new Text(group, SWT.BORDER);
	   gd = new GridData();
	   gd.grabExcessHorizontalSpace = true;
	   gd.horizontalAlignment = SWT.FILL;
	   xmlFileText.setLayoutData(gd);
	   xmlFileText.addModifyListener(modifyListener);
	   
	   browseXmlButton = new Button(group, SWT.PUSH);
	   browseXmlButton.setText(Messages.CoProc2LaunchConfigurationMainTab_BrowseButton);
	   browseXmlButton.addSelectionListener(new SelectionAdapter() {
	      public void widgetSelected(SelectionEvent evt) {
	         String[] extensions = new String[] {"*.xml"}; //$NON-NLS-1$
	         String fileName = browseForSourceFile(extensions);
	         if (fileName != null) {
               xmlFileText.setText(fileName);
            }
	      }
	   });
	}
	
	private void addUrlGroup(Composite parent) {
	   Group group = new Group(parent, SWT.NONE);
	   group.setText(Messages.CoProc2LaunchConfigurationMainTab_ConnectionGroupName);
	   GridLayout layout = new GridLayout();
	   layout.numColumns = 4;
	   group.setLayout(layout);
	   GridData gd = new GridData();
	   gd.horizontalAlignment = SWT.FILL;
	   group.setLayoutData(gd);
	   
	   Label label = new Label(group, SWT.NONE);
	   label.setText(Messages.CoProc2LaunchConfigurationMainTab_HostField + ':');
	   
	   coprocHostText = new Text(group, SWT.BORDER);
	   gd = new GridData();
	   gd.horizontalSpan = 3;
	   gd.horizontalAlignment = SWT.FILL;
	   gd.grabExcessHorizontalSpace = true;
	   coprocHostText.setLayoutData(gd);
	   coprocHostText.addModifyListener(modifyListener);
	   
	   label = new Label(group, SWT.NONE);
	   label.setText(Messages.CoProc2LaunchConfigurationMainTab_PortField + ':');
	   
	   coprocPortText = new Text(group, SWT.BORDER);
	   gd = new GridData();
	   gd.horizontalAlignment = SWT.FILL;
	   gd.widthHint = 40;
	   coprocPortText.setLayoutData(gd);
	   coprocPortText.addModifyListener(modifyListener);
	   
	   testUrl = new Button(group, SWT.PUSH);
	   testUrl.setText(Messages.CoProc2LaunchConfigurationMainTab_TestConnectionButton);
	   testUrl.addSelectionListener(new SelectionAdapter() {
	      public void widgetSelected(SelectionEvent evt) {
	         statusLabel.setText(Messages.CoProc2LaunchConfigurationMainTab_TestingConnectionStatus);
	         AsyncConnectionTest test = new AsyncConnectionTest(coprocHostText.getText(), 
	                                                            coprocPortText.getText(), 
	                                                            statusLabel);
	         new Thread(test).start();
	      }
	   });
	   
	   statusLabel = new Label(group, SWT.NONE);
	   gd = new GridData();
	   gd.grabExcessHorizontalSpace = true;
	   gd.horizontalAlignment = SWT.FILL;
	   statusLabel.setLayoutData(gd);
	   statusLabel.setText(Messages.CoProc2LaunchConfigurationMainTab_ConnectionNotTested);
	}
	
	private void addHeaderGroup(Composite parent) {
	   Group group = new Group(parent, SWT.NONE);
	   group.setText(Messages.CoProc2LaunchConfigurationMainTab_HeaderGroupTitle);
	   GridLayout layout = new GridLayout();
	   layout.numColumns = 2;
	   group.setLayout(layout);
	   GridData gd = new GridData();
	   gd.grabExcessHorizontalSpace = true;
	   gd.horizontalAlignment = SWT.FILL;
	   group.setLayoutData(gd);
	   
	   headerTable = new HeaderTable(group);
	   headerTable.addModifyListener(modifyListener);
	}
	
	private String browseForSourceFile(String[] extensions) {
	   FileDialog dialog = new FileDialog(getShell(), SWT.OPEN);
	   String wkspRoot = ResourcesPlugin.getWorkspace().getRoot().getLocation().toString();
	   dialog.setFileName(wkspRoot);
	   dialog.setFilterExtensions(extensions);
	   String result = dialog.open();
	   return result;
	}
}
