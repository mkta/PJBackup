<sei:createLoad xmlns:sei="http://sei.service.pim.brandpath.com/">
    <upload client="DESILACOL" security="${r"${soapToken}"}" compressed="NO">
        <![CDATA[
            <load action="LOAD_PUBLISHED" >"
                <price_list id="DLC_UK_PL" site_id="DLC_UK_ONLINE">
<#assign codes = ctp?keys>
<#list codes as code>
                    <offering code="${code}">
                        <#assign pr = ctp[code]>
                        <price type="SALEPRICE" currency="GBP" net="${pr["SALEPRICE"]}" />
                        <price type="WAS" currency="GBP" net="${pr["WAS"]}" />
                        <price type="RRP" currency="GBP" net="${pr["RRP"]}" />
                    </offering>
</#list>
                </price_list>
            </load>
        ]]>
    </upload>
</sei:createLoad>