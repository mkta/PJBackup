<sei:createLoad xmlns:sei="http://sei.service.pim.brandpath.com/">
    <upload client="DESILACOL" security="${r"${soapToken}"}" compressed="NO">
        <![CDATA[
            <load action="LOAD_PUBLISHED" >"
<#list codes as code>
                <product code="${code}">
                    <name>IMPORT TEST PRODUCT ${code}</name>
                    <type>SHOES</type>
                    <brand tag="KATE SPADE"/>
                    <hierarchy>
                        <category tag="M SHOES"/>
                    </hierarchy>
                    <properties>
                        <property tag="DISCONTINUED">
                            <value>false</value>
                        </property>
                        <property tag="DISCONTINUED_DATE">
                            <value>2018-01-01</value>
                        </property>
                        <property tag="KEY_FEATURES">
                            <value>Awsome feature</value>
                        </property>
                        <property tag="SIZE_GROUP">
                            <value>Women</value>
                        </property>
                        <property tag="SIZE">
                            <value>9</value>
                        </property>
                        <property tag="COLOUR">
                            <value>Pink</value>
                        </property>
                        <property tag="RRP">
                            <value>99</value>
                        </property>
                        <property tag="WEIGHT_UNIT">
                            <value>kg</value>
                        </property>
                        <property tag="WEIGHT">
                            <value>1</value>
                        </property>
                        <property tag="DESCRIPTION">
                            <value>XYZ This is long desc.</value>
                        </property>
                        <property tag="SHORT_DESCRIPTION">
                            <value>ABC this is short</value>
                         </property>
                        <property tag="TEASER">
                            <value>Leather shoes</value>
                        </property>
                    </properties>
                    <set_assets assest_name="Test Assert 1">
                        <asset size="STANDARD" url="/p2/b274802.jpg"/>
                    </set_assets>
                </product>
</#list>
            </load>
        ]]>
    </upload>
</sei:createLoad>