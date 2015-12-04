package com.ibm.issw.plugins.datapower.coproc2.headers;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

public class HeaderTable {

   private TableViewer viewer;
   private HeaderContentProvider contentProvider;
   private Button addHeaderButton;
   private Button deleteHeaderButton;
   private Button exportHeaderButton;
   private Button importHeaderButton;
   
   private List<ModifyListener> modifyListeners;
   
   public HeaderTable(Composite parent) {
      modifyListeners = new LinkedList<ModifyListener>();
      createTable(parent);
      addAddButton(parent);
      addDeleteButton(parent);
//      addExportButton(parent);
//      addImportButton(parent);
   }

   public void setInput(List<HeaderEntry> entries) {
      if (entries != null) {
         viewer.setInput(entries);
         contentProvider = (HeaderContentProvider)viewer.getContentProvider();
      }
   }
   
   public void addEntry(HeaderEntry entry) {
      contentProvider.addEntry(entry);
      notifyModifyListeners();
   }
   
   public Map<String, String> getEntries() {
      Map<String, String> result = new HashMap<String, String>();
      if (contentProvider != null) {
         for (HeaderEntry entry : contentProvider.getEntries()) {
            result.put(entry.getName(), entry.getValue());
         }
      }
      return result;
   }
   
   public void addModifyListener(ModifyListener listener) {
      modifyListeners.add(listener);
   }
   
   private void notifyModifyListeners() {
      for (ModifyListener listener : modifyListeners) {
         listener.modifyText(null);
      }
   }

   private void createTable(Composite parent) {
      viewer = new TableViewer(parent, SWT.SINGLE | SWT.FULL_SELECTION);
      final HeaderLabelProvider labelProvider = new HeaderLabelProvider();
      viewer.setLabelProvider(labelProvider);
      final HeaderContentProvider contentProvider = new HeaderContentProvider(viewer);
      viewer.setContentProvider(contentProvider);
      Table table = viewer.getTable();
      GridData gd = new GridData();
      gd.verticalSpan = 5;
      gd.grabExcessHorizontalSpace = true;
      gd.horizontalAlignment = SWT.FILL;
      gd.verticalAlignment = SWT.FILL;
      table.setLayoutData(gd);
      table.setHeaderVisible(true);
      table.setLinesVisible(true);
      TableColumn column = new TableColumn(table, SWT.LEFT);
      column.setText(Messages.HeaderTable_NameColumnTitle);
      column.setWidth(100);
      TableViewerColumn viewerColumn = new TableViewerColumn(viewer, column);
      viewerColumn.setLabelProvider(new ColumnLabelProvider() {
         public String getText(Object element) {
            return labelProvider.getColumnText(element, 0);
         }
      });
      viewerColumn.setEditingSupport(new EditingSupport(viewer) {
         TextCellEditor editor = null;
         
         protected void setValue(Object element, Object value) {
            if (element instanceof HeaderEntry  && value != null) {
               String text = ((String)value).trim();
               HeaderEntry entry = (HeaderEntry)element;
               entry.setName(text);
               getViewer().update(element, null);
               notifyModifyListeners();
            }
         }
         protected Object getValue(Object element) {
            return labelProvider.getColumnText(element, 0); 
         }
         protected CellEditor getCellEditor(Object element) {
            if (editor == null) {
               Composite table = (Composite) viewer.getControl();
               editor = new TextCellEditor(table);
            }
            return editor;
         }
         protected boolean canEdit(Object element) {
            return true;
         }
      });
      
      column = new TableColumn(table, SWT.LEFT);
      column.setText(Messages.HeaderTable_ValueColumnTitle);
      column.setWidth(250);
      viewerColumn = new TableViewerColumn(viewer, column);
      viewerColumn.setLabelProvider(new ColumnLabelProvider() {
         public String getText(Object element) {
            return labelProvider.getColumnText(element, 1);
         }
      });
      viewerColumn.setEditingSupport(new EditingSupport(viewer) {
         TextCellEditor editor = null;
         
         protected void setValue(Object element, Object value) {
            if (element instanceof HeaderEntry  && value != null) {
               String text = ((String)value).trim();
               HeaderEntry entry = (HeaderEntry)element;
               entry.setValue(text);
               getViewer().update(element, null);
               notifyModifyListeners();
            }
         }
         protected Object getValue(Object element) {
            return labelProvider.getColumnText(element, 1); 
         }
         protected CellEditor getCellEditor(Object element) {
            if (editor == null) {
               Composite table = (Composite) viewer.getControl();
               editor = new TextCellEditor(table);
            }
            return editor;
         }
         protected boolean canEdit(Object element) {
            return true;
         }
      });
   }

   private void addAddButton(Composite parent) {
      GridData gd;
      addHeaderButton = new Button(parent, SWT.PUSH);
      addHeaderButton.setText(Messages.HeaderTable_AddButtonName);
      gd = new GridData();
      gd.horizontalAlignment = SWT.FILL;
      addHeaderButton.setLayoutData(gd);
      addHeaderButton.setEnabled(true);
      addHeaderButton.addSelectionListener(new SelectionAdapter() {
         public void widgetSelected(SelectionEvent e) {
            HeaderEntry entry = new HeaderEntry(Messages.HeaderTable_DefaultNameValue, Messages.HeaderTable_DefaultValueValue, true);
            addEntry(entry);
         }
      });
   }

   private void addDeleteButton(Composite parent) {
      GridData gd;
      deleteHeaderButton = new Button(parent, SWT.PUSH);
      deleteHeaderButton.setText(Messages.HeaderTable_DeleteButtonName);
      gd = new GridData();
      gd.horizontalAlignment = SWT.FILL;
      deleteHeaderButton.setLayoutData(gd);
      deleteHeaderButton.setEnabled(true);
      deleteHeaderButton.addSelectionListener(new SelectionAdapter() {
         public void widgetSelected(SelectionEvent e) {
            int itemToBeDeleted = viewer.getTable().getSelectionIndex();
            if (itemToBeDeleted > -1) {
               contentProvider.removeEntry(itemToBeDeleted);
               notifyModifyListeners();
            }
         }
      });
   }

   private void addImportButton(Composite parent) {
      GridData gd;
      importHeaderButton = new Button(parent, SWT.PUSH);
      importHeaderButton.setText(Messages.HeaderTable_ImportButtonName);
      gd = new GridData();
      gd.horizontalAlignment = SWT.FILL;
      importHeaderButton.setLayoutData(gd);
      importHeaderButton.setEnabled(false);
   }

   private void addExportButton(Composite parent) {
      GridData gd;
      exportHeaderButton = new Button(parent, SWT.PUSH);
      exportHeaderButton.setText(Messages.HeaderTable_ExportButtonName);
      gd = new GridData();
      gd.horizontalAlignment = SWT.FILL;
      exportHeaderButton.setLayoutData(gd);
      exportHeaderButton.setEnabled(false);
   }
}
