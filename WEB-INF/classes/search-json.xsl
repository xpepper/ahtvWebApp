<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="text" indent="no" encoding="ISO-8859-1" />
    
    <xsl:template match="/">
        <xsl:apply-templates select="*" />
    </xsl:template>
    
    <xsl:template match="RESULTS">
        <xsl:text>{</xsl:text>
        <xsl:text>&#10;</xsl:text>
        <xsl:apply-templates select="EPG|VOD|EDIT" />
        <xsl:text>  "Hits" : [</xsl:text>
        <xsl:text>&#10;</xsl:text>
        <xsl:apply-templates select="//HIT" />
        <xsl:text>  ]</xsl:text>
        <xsl:text>&#10;</xsl:text>
        <xsl:text>}</xsl:text>
        <xsl:text>&#10;</xsl:text>
    </xsl:template>
    
    <xsl:template match="EPG|VOD|EDIT">
        <xsl:text>  "results</xsl:text>
        <xsl:value-of select="name()" />
        <xsl:text>"            : "</xsl:text>
        <xsl:value-of select="@TOTAL" />
        <xsl:text>",</xsl:text>
        <xsl:text>&#10;</xsl:text>
    </xsl:template>
    
    <xsl:template match="HIT">
        <xsl:text>    {</xsl:text>
        <xsl:text>&#10;</xsl:text>

        <xsl:text>"type"	: "</xsl:text><xsl:value-of select="name(..)" /><xsl:text>",&#10;</xsl:text>
        <xsl:text>"title": "</xsl:text>
	        <xsl:call-template name="escape-quot-string">
	          <xsl:with-param name="s" select="normalize-space(TITLE)"/>
	        </xsl:call-template>	        	
        <xsl:text>",&#10;</xsl:text>	        
        <xsl:if test="name(..)='EPG'">
        	<xsl:text>"assetid": "</xsl:text><xsl:value-of select="ASSETID" /><xsl:text>",&#10;</xsl:text>
	        <xsl:text>"category": "</xsl:text><xsl:value-of select="CATEGORIZATION" /><xsl:text>",&#10;</xsl:text>
	        <xsl:text>"startdate": "</xsl:text><xsl:value-of select="STARTDATE" /><xsl:text>",&#10;</xsl:text>
	        <xsl:text>"logo": "</xsl:text><xsl:value-of select="ICONA" /><xsl:text>",&#10;</xsl:text>
	        <xsl:text>"channel_name": "</xsl:text><xsl:value-of select="NOMECANALE" /><xsl:text>",&#10;</xsl:text>
	        <xsl:text>"channel_number": "</xsl:text><xsl:value-of select="CHANNELID" /><xsl:text>",&#10;</xsl:text>
	        <xsl:text>"rating"	: "</xsl:text><xsl:value-of select="RATING" /><xsl:text>",&#10;</xsl:text>
	        <xsl:text>"duration": "</xsl:text><xsl:value-of select="DURATION" /><xsl:text>",&#10;</xsl:text>
	        <xsl:text>"description": "</xsl:text>
		        <xsl:call-template name="escape-quot-string">
		          <xsl:with-param name="s" select="normalize-space(SHORTDESCRIPTION)"/>
		        </xsl:call-template>	        	
	        <xsl:text>",&#10;</xsl:text>	        
	        <xsl:text>"link": "</xsl:text><xsl:value-of select="URL" /><xsl:text>"&#10;</xsl:text>
        </xsl:if> 
        <xsl:if test="name(..)='VOD'">
        	<xsl:text>"assetid": "</xsl:text><xsl:value-of select="ASSETID" /><xsl:text>",&#10;</xsl:text>
	        <xsl:text>"year": "</xsl:text><xsl:value-of select="RELEASE_YEAR" /><xsl:text>",&#10;</xsl:text>
	        <xsl:text>"director": "</xsl:text>
		        <xsl:call-template name="escape-quot-string">
		          <xsl:with-param name="s" select="DIRECTOR"/>
		        </xsl:call-template>	        	
	        <xsl:text>",&#10;</xsl:text>	        
	        <xsl:text>"actor": "</xsl:text>
		        <xsl:call-template name="escape-quot-string">
		          <xsl:with-param name="s" select="ACTOR"/>
		        </xsl:call-template>	        	
	        <xsl:text>",&#10;</xsl:text>	        
	        <xsl:text>"rating"	: "</xsl:text><xsl:value-of select="RATING" /><xsl:text>",&#10;</xsl:text>
	        <xsl:text>"duration": "</xsl:text><xsl:value-of select="DURATION" /><xsl:text>",&#10;</xsl:text>
	        <xsl:text>"link": "</xsl:text><xsl:value-of select="URL" /><xsl:text>",&#10;</xsl:text>
	        <xsl:text>"season": "</xsl:text><xsl:value-of select="STAGIONE" /><xsl:text>"&#10;</xsl:text>
        </xsl:if> 
        <xsl:if test="name(..)='EDIT'">
			<xsl:text>"abstract": "</xsl:text><xsl:value-of select="normalize-space(SOMMARIO)" /><xsl:text>",&#10;</xsl:text>
			<xsl:text>"link": "</xsl:text><xsl:value-of select="URL" /><xsl:text>"&#10;</xsl:text>
        </xsl:if>
         
        <xsl:text>    },</xsl:text>
        <xsl:text>&#10;</xsl:text>
    </xsl:template>

  <!-- Escape the double quote ("). -->
  <xsl:template name="escape-quot-string">
    <xsl:param name="s"/>
    <xsl:choose>
      <xsl:when test="contains($s,'&quot;')">
          <xsl:value-of select="concat(substring-before($s,'&quot;'),'\&quot;')"/>
        <xsl:call-template name="escape-quot-string">
          <xsl:with-param name="s" select="substring-after($s,'&quot;')"/>
        </xsl:call-template>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="$s"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>
  
</xsl:stylesheet>