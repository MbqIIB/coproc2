package com.ibm.issw.plugins.datapower.coproc2.headers;

import java.io.Serializable;

public class HeaderEntry implements Serializable {

   private static final long serialVersionUID = 1L;
   private String name;
   private String value;
   private boolean enabled;
   
   public HeaderEntry() {
   }
   
   public HeaderEntry(String name, String value, boolean enabled) {
      this.name = name;
      this.value = value;
      this.enabled = enabled;
   }
   public String getName() {
      return name;
   }
   public void setName(String name) {
      this.name = name;
   }
   public String getValue() {
      return value;
   }
   public void setValue(String value) {
      this.value = value;
   }
   public boolean isEnabled() {
      return enabled;
   }
   public void setEnabled(boolean enabled) {
      this.enabled = enabled;
   }  
}
