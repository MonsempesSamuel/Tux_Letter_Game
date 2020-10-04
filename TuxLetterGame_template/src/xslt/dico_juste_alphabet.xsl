<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:tux='http://myGame/tux'>
>
    <xsl:output method="html" />
    <xsl:template match="/">
        <html>
            <head>
                <title>Dictionnaire</title>
                <link>
                    <xsl:attribute name="href">../css/tux.css</xsl:attribute>
                    <xsl:attribute name="type">text/css</xsl:attribute>
                    <xsl:attribute name="rel">stylesheet</xsl:attribute>
                </link>
            </head>
            <body>

                <h1 class="headline">Dictionnaire</h1>

                <p>Les mots sont les suivants : </p>
                <ul class="dico">
                    <xsl:apply-templates select="//tux:mot">
                        <xsl:sort order="ascending" />
                        <xsl:sort select="./text()"/>
                    </xsl:apply-templates>
                </ul>
            </body>
        </html>
    </xsl:template>

    <xsl:template match="tux:mot">
        <li class="dico-element">
            <xsl:value-of select="./text()" />
        </li>
    </xsl:template>
</xsl:stylesheet>
