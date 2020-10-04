<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
    xmlns:tux='http://myGame/tux'>
>
    <xsl:output method="html" />
    <xsl:template match="/">
        <html>
            <head>
                <title>Profil</title>
                <link>
                    <xsl:attribute name="href">../css/profil.css</xsl:attribute>
                    <xsl:attribute name="type">text/css</xsl:attribute>
                    <xsl:attribute name="rel">stylesheet</xsl:attribute>
                </link>
            </head>

            <body>
                <h3 class="profile-name">Profil de <xsl:value-of select="//tux:nom/text()" />
                </h3>
                <p>Anniversaire : <xsl:value-of select="//tux:anniversaire/text()" />
                </p>
                <xsl:element name="img">
                    <xsl:attribute name="src">
                        <xsl:value-of select="//tux:avatar/text()" />
                    </xsl:attribute>
                    <xsl:attribute name="alt">
                    Avatar de <xsl:value-of select="//tux:nom/text()" />
                    </xsl:attribute>
                </xsl:element>

                <br/>

                <h3 class="parties">Parties</h3>

                <xsl:apply-templates select="//tux:partie" />

            </body>

        </html>
    </xsl:template>
    <xsl:template match="tux:partie">

        <b>Date : </b>
        <xsl:value-of select="@date" />
        <b>Trouvé :</b>
        <xsl:value-of select="@trouvé" />

        <b>Temps : </b>
        <xsl:value-of select="./tux:temps/text()" />

        Mot : <xsl:value-of select="./tux:mot/text()" />
    </xsl:template>
</xsl:stylesheet>