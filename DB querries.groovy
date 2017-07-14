DB querries 


[report].[SAB_orderTransaction] @prevDay = 0  -   open report




[report].[SAB_promotion]

select voucherId , code, totalPrice from 
SABTST_PIM.promo.voucherInstance i_all 
inner join 
(select voucherCode , sum(totalPrice) totalPrice from so.voucher v
inner join so.[order] o on v.orderId = o.id
group by voucherCode ) o on i_all.code = o.voucherCode