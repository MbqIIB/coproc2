package com.ibm.issw.plugins.datapower.coproc2.pref;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import com.ibm.issw.plugins.datapower.coproc2.Activator;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault(PreferenceConstants.PREF_NULL_INPUT, true);
		store.setDefault(PreferenceConstants.PREF_HOST, "CoProc2 host name");
		store.setDefault(PreferenceConstants.PREF_PORT, "2223");
	}

}
