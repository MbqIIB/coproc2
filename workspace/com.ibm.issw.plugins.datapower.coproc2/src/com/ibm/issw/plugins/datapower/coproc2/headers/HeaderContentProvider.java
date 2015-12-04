package com.ibm.issw.plugins.datapower.coproc2.headers;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;

public class HeaderContentProvider implements IStructuredContentProvider {
   
   private List<HeaderEntry> entries;
   private TableViewer viewer;
   
   public HeaderContentProvider(TableViewer viewer) {
      this.viewer = viewer;
   }

   public void dispose() {
   }

   public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
   }

   public Object[] getElements(Object inputElement) {
      HeaderEntry[] entryArray = null;
      if (inputElement instanceof List<?>) {
         entries = (List<HeaderEntry>)inputElement;
         entryArray = new HeaderEntry[entries.size()];
         entries.toArray(entryArray);
      } else if (inputElement instanceof HeaderEntry[]) {
         entryArray = (HeaderEntry[])inputElement;
      }
      return entryArray;
   }
   
   public void addEntry(HeaderEntry entry) {
      if (entries == null) {
         entries = new ArrayList<HeaderEntry>();
      }
      entries.add(entry);
      viewer.add(entry);
   }
   
   public void removeEntry(int index) {
      if (index < entries.size()) {
         HeaderEntry entryToRemove = entries.get(index);
         entries.remove(index);
         viewer.remove(entryToRemove);
      }
   }
   
   /**
    * Return a deep copy of this content provider's header entries.
    */
   public List<HeaderEntry> getEntries() {
      List<HeaderEntry> result = new LinkedList<HeaderEntry>();
      for (HeaderEntry entry : entries) {
         result.add(entry);
      }
      return result;
   }

}
