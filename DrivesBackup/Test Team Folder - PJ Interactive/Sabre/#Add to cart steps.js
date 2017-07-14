//First
//Init Cart

this.http.post('cart').then(response => {
            if (response.content) {
                this.cart.session.cartPublicId = response.content.cartPublicId;
                this.cart.session.consumerReference = response.content.consumerReference;
                this.cart.session.cartReference = response.content.cartReference;

//Check Cart

this.http.get('cart/' + this.cart.session.cartPublicId + '/' + this.cart.session.cartReference).then(response => {
            if (response.content) {
                this.cart.packages = response.content.packages;
                this.cart.cartSummary = response.content.cartSummary;
                this.cart.delivery = response.content.delivery;
//Add to Cart
//1. set payload

let payload = {
            cartReference: this.cart.session.cartReference,
            offeringId: offeringId,
            quantity: 1,
            sitePublicId: this.sitePublicId
        };


//2. PUT request with payload


this.http.put('cart/' + this.cart.session.cartPublicId, payload)




offeringId: 08ee8a5c-2eaa-49fb-a428-d72253f8f812 - s7
sitePublicId: 5b7d3ff1-c560-4879-a4c7-4ec1962ee1c9


{
      "cartReference": "${cartReference}",
      "offeringId": "08ee8a5c-2eaa-49fb-a428-d72253f8f812",
      "quantity": "1",
      "sitePublicId": "5b7d3ff1-c560-4879-a4c7-4ec1962ee1c9"
}




//set payment method
this.http.post(`cart/${this.appConfig.cart.session.cartPublicId}/${this.appConfig.cart.session.cartReference}/${this.appConfig.cart.paymentMethod}`)
BANKTRANSFER
//get delivery options
this.http.get(`cart/delivery/${this.appConfig.cart.session.cartPublicId}/${this.appConfig.cart.session.cartReference}`)

//checkout
//variabile/payload
 if (this.checkoutDetails.sameAddress) {
            this.checkoutDetails.shippingAddress = this.appConfig.cloneObject(this.checkoutDetails.billingAddress);
        }

        this.checkoutDetails.billingAddress.territoryCode = 'GBR';
        this.checkoutDetails.billingAddress.tag = 'BILLING';
        this.checkoutDetails.billingAddress.cartPublicId = this.appConfig.cart.session.cartPublicId;

        this.checkoutDetails.shippingAddress.territoryCode = 'GBR';
        this.checkoutDetails.shippingAddress.tag = 'DELIVERY';
        this.checkoutDetails.shippingAddress.cartPublicId = this.appConfig.cart.session.cartPublicId;
let consumerPayload = {
            accountId: null,
            properties: {
                'TITLE': this.checkoutDetails.consumer.title,
                'FIRSTNAME': this.checkoutDetails.consumer.firstName,
                'LASTNAME': this.checkoutDetails.consumer.lastName,
                'EMAIL': this.checkoutDetails.consumer.email,
                'PHONENUMBER': this.checkoutDetails.consumer.phone
            },
            reference: this.appConfig.cart.session.consumerReference,
            statusId: '100',
            typeTag: 'NEW'
        };
// 4 req
            this.http.put('consumer', consumerPayload),
            this.http.post('address', this.checkoutDetails.billingAddress),
            this.http.post('address', this.checkoutDetails.shippingAddress),
            this.http.post(`cart/${this.appConfig.cart.session.cartPublicId}/${this.appConfig.cart.session.cartReference}/AGREEMENTS`, { terms: this.basketInformation.terms, email: this.basketInformation.newsletter || false })
//complete
this.http.get('cart/complete/' + this.appConfig.cart.session.cartPublicId + '/' + this.appConfig.cart.session.cartReference)
//close seesion
