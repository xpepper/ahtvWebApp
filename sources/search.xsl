<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml" indent="yes" encoding="ISO-8859-1" />
    <xsl:template match="/">
        <xsl:apply-templates select="*" />
    </xsl:template>
    <xsl:template match="COMPOSITE-RESULTPAGE">
        <xsl:variable name="EPG" select="SEGMENT/RESULTPAGE[PARAM/@source='epg']"/>
        <xsl:variable name="VOD" select="SEGMENT/RESULTPAGE[PARAM/@source='vod']"/>
        <xsl:variable name="EDIT" select="SEGMENT/RESULTPAGE[PARAM/@source='edit']"/>
        <RESULTS>
            <EPG>                
                <xsl:attribute name="TOTAL">
                    <xsl:value-of select="$EPG/RESULTSET/@TOTALHITS"/>                    
                </xsl:attribute>
                <xsl:apply-templates select="$EPG" />
            </EPG>
            <VOD>                
                <xsl:attribute name="TOTAL">
                    <xsl:value-of select="$VOD/RESULTSET/@TOTALHITS"/>                    
                </xsl:attribute>
                <xsl:apply-templates select="$VOD" />
            </VOD>
            <EDIT>                
                <xsl:attribute name="TOTAL">
                    <xsl:value-of select="$EDIT/RESULTSET/@TOTALHITS"/>                    
                </xsl:attribute>
                <xsl:apply-templates select="$EDIT" />
            </EDIT>
        </RESULTS>
    </xsl:template>
    <xsl:template match="RESULTPAGE">
        <xsl:apply-templates select="RESULTSET/HIT" />
    </xsl:template>
    <xsl:template match="HIT">
        <xsl:copy-of select="." />
    </xsl:template>
</xsl:stylesheet>
