

PIM issues list:

- if I open the product listing while on Samsung then switch to desi la col and try to create a product in desi la col I will see Samsung product create properties (catalog, manufacture, categories)
- when creating offering variant, friendly URL is copied from the main offering
- assets may be lost when creating drafts/editing products/offerings - it is "random" and it requires more investigation from test team to try to find the exact moment when it happens
- changes in pim (creating products, editing assets, prices, names) are not always propagated to the front end (this issue occurs more on JP site)
- GBP price is displayed by default in the "price list" tab inside offerings details. If i select and set USD or JPY price, and publish the offering, creating a new draft of the offering will display again the GBP price. Sometimes the USD price is copied to the GPB. I have to re-select the price currency to see them and edit them.
- changing the assets at PRODUCT level are not propagated until the related offering is republished (draft+publish)
- i uploaded an image 1500x1500 but on the front end the image sent is 400x400 ... but the display size is 800x800 so the image will be blurry because of the multiple resize 
- on the front end, we have on PLP rectangle images but on PDP we have square images. in pim it is required two fields 1 for the rectangle image, one for the square image







-------------------------------------------------------------

qwertyu-DCZ5-QZ6H


//AGXLN-CYAUOC
//HNMZF-BXWHYT
"PBKIJ-EWLNIP - created"
//GJOKW-ICHLJO - worldpay - 
//ELPDY-DVPZZJ - paypal - 
//UARDO-QEYTZI - bank transfer - 
//JLZJL-OUQKYY - PPC -
UHOPI-WBMHLK - Worldpay - (Q > 1)
XHJMB-BOIJRN  - Paypal - (Q > 1 + normal offering)
HQSEF-FIGNKX
EVQPE-WVSKFU
RKFLC-GSPNAR
MQHES-SLLNQA
"EEGGC-IBGRVZ "
"WWFRO-ENTISS"
XLEJO-CCGGOE - 2 offerings, voucher for specific offering , CC
"YELSA-CNGXCX"
TQUYC-BPUHBI


------------------------------------------------------

https://sabtstpim.brandpath.net/pim-web/pim/offerings/versionView/tabs/offeringTabs.xhtml?mode=edit&id=2731

https://sabtstpim.brandpath.net/pim-web/pim/offerings/versionView/tabs/offeringTabs.xhtml?mode=edit&id=2737

https://sabtstpim.brandpath.net/pim-web/pim/offerings/versionView/tabs/offeringTabs.xhtml?mode=edit&id=2738

https://sabtstpim.brandpath.net/pim-web/pim/offerings/versionView/tabs/offeringTabs.xhtml?mode=edit&id=1974


------------------------------------------

JEETI-MJAJZK


-----------------------------------------

SCR-779 - ok
SCR-786 - ok
SCR-790 - ok
SCR-731 - ok-ish

-------------------------------------------------------------



After live release:
Chrome
3.12
2.05
1.66
2.58
2.22
Avg:2.36
Firefox
5.98
3.28
2.98
2.09
2.7
Avg:3.4

Live before release:
Chrome:
1.88
2.01
5.00
3.09
3.97
Avg:3.19
Firefox:
5.37
2.53
2.97
2.76
2.44
Avg:3.21


After fix Firefox
4907ms
2318ms
5224ms
4326ms
2089ms
Avg:3355


After fix Chrome
5208ms
3816ms 
4342ms 
4363ms
4049ms
Avg:4355.6


Before fix Firefox
4586ms 
1216ms
1696ms 
4759ms
5014ms 
Avg:3454.2


Before fix Chrome
 3707ms
 4462ms
 4619ms
 1683ms 
 4486ms 
 Avg:3791.4

After -  Live HP loadtime Chrome : 7.0; 4.6; 5.6; 5.1; 5.6; 4.8; Avg:5.45

Live HP loadtime Chrome : 6.2; 5.8; 5.9; 5.9; 5.8; 6.2; 6.9; 5.9; 6.3; 6.0 Avg: 6.09
Live HP loadtime Firefox: 5.5; 3.4; 3.7; 4.0; 3.6; 2.17; 5.0; 6.8; 3.6; 3.4; Avg: 4.117



"//SCR-772 Memory Month - 128 gb memory card with all tablet devices - ok
""//SCR-747 Valentines Home Appliance Cashback - ok
"//"SCR-753 Valentines - DA cashback products category listing" - pim ticket
//"SCR-751 Valentines - Bundles Category Listing" - pim ticket
"//SCR-752 Valentines love bundles category listing banners - ok
""//SCR-766 Microwaves extended warranty - ok
""//SAB-4421 GNB colour change - ok
""//SCR-781 Offers Page Tiles 1st Feb - ok
"SCR-782 HP images update - performance - awaiting release 






SCR-668 - ok
scr-584 - ok
scr-585 - ok
sab-4391 - ok
SAB-4376 - ok
scr-735 - ok
scr-670 - ok



status in ("To Do", "In Progress", "Ready for Test", "Test in progress", "Awaiting Dependency", "Ready for STG merge", "Regression Testing", UAT, "Ready for Release") AND issuetype in (Sub-task, Task, Bug, Story) ORDER BY created DESC




- multiple offerings with multiple quantity - different payment from other tab (started wp finished ppc) - FWFXX-VYGBQF - duplicate in OMS
Samsung Gear 360 Action Cam	4	
Samsung Test 28122016	3	
Samsung Variants Test 3	


- multiple offerings with multiple quantity - different payment from other tab (started wp finished bt) - VFCHY-UIUEZN - ok
Samsung Test 28122016	3
Samsung Gear 360 Action Cam	3

- multiple offerings with multiple quantity - different payment from other tab (started wp finished ppc) - IBNIS-VRBFUF - ok
Samsung Gear 360 Action Cam	4	
Samsung Test 28122016	3	
Samsung Variants Test 3

- DAPWG-TZNWSB - payment error - db status?

- multiple offerings(3) with multiple quantity one with multiple products - started PPC - canceled - error on WP - removed product from basket (2 offerigns lefts) - finish PP - MIOZY-IBFZTA - ok
	Samsung Gear S3 Frontier Quantity	3 - 3
	Samsung CLT-K406S Black Toner	3


1. LHTFR-AJOJEZ - Quantity P 5 + Quantity P 3 + Quantity P 1  + voucher for order  - CreditCard - Submitted To Warehouse
2. XSBPU-JVUFUF - Quantity P 5 + Quantity P 3 x 3  + voucher for ( Quantity P 3 ) - Paypal - Submitted to Warehouse
3. CGJKJ-IQSAUW - Quantity P 5 x 3  + Normal offering - Paypal Credit - Submitted to Warehouse
4. ECARQ-LGDPFZ - Quantity P 5 + Quantity P 3 x 4 - Bank Transfer - Approved - Submitted To Warehouse
5. WAPOH-OLGOPN - Quantity P 5 + Quantity P 3 x 2 + voucher for order - CreditCard - Submitted to Warehouse
























https://brandpath.atlassian.net/browse/SAB-4384
https://brandpath.atlassian.net/browse/SAB-4388
https://brandpath.atlassian.net/browse/SAB-4332
https://brandpath.atlassian.net/browse/SAB-4379
https://brandpath.atlassian.net/browse/SAB-3997
https://brandpath.atlassian.net/browse/SAB-3032



4222




SCR-655 - not ready
SCR-686 - not ready
SCR-696 - ok 
SCR-657 - ok
SCR-688 - not ready
SCR-576 - ok
SCR-720 - not ready

SCR-687
SCR-691
SCR- 646
SCR-589
SCR-655
SCR-686
SCR-696
SCR-657
SCR-688
SCR-576
SCR-720
SAB-4378
SCR-723






friendly url - samsung-galaxy-s7-edge-silver-mobile-care
S7EdgeSilver_F-S71YEAR - sku
Samsung Galaxy S7 Edge Silver & Mobile Care - name

	
	S7 Insurance
Samsung Galaxy S7 edge 32 GB Silver


samsung-galaxy-s7-edge-black-mobile-care
samsung-galaxy-s7-edge-silver-mobile-care




bank transfer payment cancelation 

RINUX-VGPWJE - STG - no mails at all received
OVNSW-KZRCOX - TST - issue still occurs 
NMEFO-GLVKAF - LIVE - no email received, maybe it was canceled before payment expire













ERFIC-ZUFSWG - pp - stg
IYCVK-RGVCJM - pp - stg
OEVPB-GUKJPT - ppc - stg - backorder
OJKBO-RDFUYC - ppc - stg

preorder ppc - tst
SUQXZ-EKONIG 
PAVEO-RCYAVN
AICFB-YKCYDE
TDHBS-JDGGHR 
ZEBWV-IJPLUR








SCR-631 - (647+468)
SCR-647 - ok
SCR-648 - ok
SCR-507 - ok
SCR-649 - done?
SAB-4172 - ok






LQBSV-CBHQZR - ppc - tst - backorder - failed
BSLPU-WQQXJE - ppc - tst - backorder - fsiled
FJARW-AMUTTN - pp - tst - backorder - worked
ZPGCQ-VCRXEC - pp - tst











“Offers tiles links are incorrect and some links are 404 errors. Change to below:Martian tile link to: http://shop.samsung.com/uk/martian-bundle/ (Promo set: OFFER_PAGE_MARTIAN)
Tv player app link to: http://shop.samsung.com/uk/tv-player-app/ (Promo set: OFFER_PAGE_TV_PP) 
Deezer music tile link to: http://shop.samsung.com/uk/music-on-us/ (Promo set: OFFER_PAGE_DEEZER)
5 year warranty: http://shop.samsung.com/uk/offers/5-year-warranty/
---------- 404 error ---------------
BA lounge: http://shop.samsung.com/uk/ba-lounge-edit/ (404)
Gear 360/MiscroSD: http://shop.samsung.com/uk/samsung-gear-360-action-cam-128-gb-microsdxc-card-sd-adapter-128gb-293261/
--------- No Products -------------
Samsung tech: http://sabtst.brandpath.net/uk/samsung-technology/ (No products tagged) (Promo set: OFFER_PAGE_SAM_TECH)”










ws/offering/cache?pageSize=9999&filterHIERARCHY=*,*

sab1991
DOREL

filterPROPERTY_LIST 
COMMERCIAL,PROMO_SET_1,BLACK_FRIDAY

promoSet 
OFFER_PAGE_TV_PP


uk/ng/ws/offering/cache?page=1&pageSize=100&sort=sortRankAsc&filterTYPES=DEVICE%2CPROMO_BUNDLE&filterHIERARCHY=*%2C*&filterPROPERTY_LIST=COMMERCIAL%2CPROMO_SET_1%2COFFER_PAGE_TV_PP

uk/ng/ws/offering/cache?page=1&pageSize=100&sort=sortRankAsc&filterTYPES=DEVICE%2CPROMO_BUNDLE&filterHIERARCHY=*%2C*&filterPROPERTY_LIST=COMMERCIAL%2CPROMO_SET_1%2CBLACK_FRIDAY


OFFER_PAGE_TV_PP,BLACK_FRIDAY


Samsung 2 M5 Medium Wireless Audio Multiroom Speakers - PP+BF
Samsung 13 Notebook Carrying Pouch - PP+BF
Samsung Extra Battery Kit for Samsung Galaxy S5 mini - BF+PP
Samsung Battery (Galaxy S4)  - BF



Samsung Galaxy A5 2016 16 GB Pink Gold - MOBILES_WEARABLES,MOBILES
Samsung 40" 2016 4K UHD flat Smart TV KU6400 - TV_AV
Samsung BD-J5500 3D Blu-ray & DVD Player - TV_AV,AV_PRODUCTS
Samsung 24" Professional UHD Monitor with multi-tasking - PC_MONITORS
Samsung WW7500 AddWash Washing Machine - HOME_APPLIANCES
Samsung SmartThings Small Smart Home Kit + Moisture Sensor - HOME_APPLIANCES,APPLIANCE_EXTRAS,SMARTTHINGS.





	SUK0KU-STGZ-ANXN - awaiting stock


	To cancel

	KMGKT-FCWCQB
	QYMTS-FFCBAK- "missing" 
		Samsung Book Cover (Galaxy Tab A 9.7) Black
			Samsung Book Cover (Galaxy Tab S 10.5)
    OCDQO-WKQBLD - "missing" 
    XTUQA-JKIKKF
    KLFWD-UKUEHD



