package com.ibm.issw.plugins.datapower.coproc2.pref;

import org.eclipse.jface.preference.*;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbench;
import com.ibm.issw.plugins.datapower.coproc2.Activator;

/**
 * This class implements the CoProc2 preference page.
 * This page is used to modify preferences only. They
 * are stored in the preference store that belongs to
 * the main plug-in class. That way, preferences can
 * be accessed directly via the preference store.
 */

public class CoProc2PreferencePage	extends FieldEditorPreferencePage
                                  implements IWorkbenchPreferencePage {

	public CoProc2PreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription(Messages.CoProc2PreferencePage_Description);
	}
	
	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	public void createFieldEditors() {
	   addField(new StringFieldEditor(PreferenceConstants.PREF_HOST, Messages.CoProc2PreferencePage_HostFieldLabel, getFieldEditorParent()));
	   addField(new StringFieldEditor(PreferenceConstants.PREF_PORT, Messages.CoProc2PreferencePage_PortFieldLabel, getFieldEditorParent()));
	   addField(new BooleanFieldEditor(PreferenceConstants.PREF_NULL_INPUT, Messages.CoProc2PreferencePage_SetInputToNullFieldLabel, getFieldEditorParent()));	   
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}
	
}