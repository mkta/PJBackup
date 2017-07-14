<sei:createLoad xmlns:sei="http://sei.service.pim.brandpath.com/">
    <upload client="DESILACOL" security="${r"${soapToken}"}" compressed="NO">
        <![CDATA[
            <load action="LOAD_PUBLISHED" >"
                <price_list id="DLC_UK_PL" site_id="DLC_UK_ONLINE">
<#list codes as code>
                    <offering code="${code}">
                        <price type="SALEPRICE" currency="GBP" net="388.00" />
                        <price type="WAS" currency="GBP" net="488.00" />
                        <price type="RRP" currency="GBP" net="588.00" />
                    </offering>
</#list>
                </price_list>
            </load>
        ]]>
    </upload>
</sei:createLoad>