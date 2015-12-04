<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

   <!--  
     Report service version information  
     -->

   <xsl:output method="text"/>
   
   <xsl:template match="/">
      <xsl:apply-templates select="version"/>
   </xsl:template>
   
   <xsl:template match="/version">
      <xsl:variable name="version-string" select="concat(major, '.', minor, '.', release)"/>
         <xsl:text>Version: </xsl:text><xsl:value-of select="$version-string"/>
Date: <xsl:value-of select="date"/>
   </xsl:template>
   
</xsl:stylesheet>