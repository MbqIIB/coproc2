<xsl:stylesheet version="1.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
  xmlns:dp="http://www.datapower.com/extensions"
  extension-element-prefixes="dp"
>
  <xsl:output method="xml" omit-xml-declaration="yes"/>
 
  <xsl:template match="/">
    <dp:set-variable name="'var://context/xsl'" 
      value="dp:inflate(dp:http-request-header('xsl'),'gzip')"/>

    <dp:set-variable name="'var://service/mpgw/skip-backside'" 
      value="1"/>

<!--
    <xsl:message dp:priority="error">
      <xsl:copy-of 
        select="dp:inflate(dp:http-request-header('xsl'),'gzip')" />
    </xsl:message>
-->
  </xsl:template>

</xsl:stylesheet>
