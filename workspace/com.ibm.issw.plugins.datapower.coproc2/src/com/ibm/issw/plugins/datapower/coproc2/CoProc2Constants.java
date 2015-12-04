package com.ibm.issw.plugins.datapower.coproc2;

public interface CoProc2Constants {
   
   public final String BUNDLE_NAME = "com.ibm.issw.plugins.datapower.coproc2";
   
   /**
    * This attribute determines whether in input XML document is used
    * as part of the XSLT execution.
    */
   public final  String ATTR_NULL_XML = "com.ibm.issw.plugins.datapower.coproc2.NullXml";
   public final boolean DFLT_NULL_XML = false; 
   
   /**
    * This attribute provides the file name of the XML input, if any.
    */
   public final String ATTR_INPUT_XML = "com.ibm.issw.plugins.datapower.coproc2.InputXml";
   public final String DFLT_INPUT_XML = "";
   
   /**
    * This attribute provides the file name of the XSL input.
    */
   public final String ATTR_INPUT_XSL = "com.ibm.issw.plugins.datapower.coproc2.InputXsl";
   public final String DFLT_INPUT_XSL = "";
   
   /**
    * This attribute provides the hostname of the CoProc2 service.
    */
   public final String ATTR_COPROC_HOST = "com.ibm.issw.plugins.datapower.coproc2.Host";
   public final String DFLT_COPROC_HOST = "";
   
   
   /**
    * This attribute provides the port of the CoProc2 service.
    */
   public final String ATTR_COPROC_PORT = "com.ibm.issw.plugins.datapower.coproc2.Port";
   public final String DFLT_COPROC_PORT = "22223";
   
   /**
    * This attribute provides the header values of the CoProc2 service.
    */
   public final String ATTR_HEADER_VALUES = "com.ibm.issw.plugins.datapower.coproc2.HeaderValues";
   public final String ATTR_HEADER_STATUS = "com.ibm.issw.plugins.datapower.coproc2.HeaderStatus";
}
