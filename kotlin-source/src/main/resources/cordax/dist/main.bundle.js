webpackJsonp(["main"],{

/***/ "../../../../../src/$$_gendir lazy recursive":
/***/ (function(module, exports) {

function webpackEmptyAsyncContext(req) {
	// Here Promise.resolve().then() is used instead of new Promise() to prevent
	// uncatched exception popping up in devtools
	return Promise.resolve().then(function() {
		throw new Error("Cannot find module '" + req + "'.");
	});
}
webpackEmptyAsyncContext.keys = function() { return []; };
webpackEmptyAsyncContext.resolve = webpackEmptyAsyncContext;
module.exports = webpackEmptyAsyncContext;
webpackEmptyAsyncContext.id = "../../../../../src/$$_gendir lazy recursive";

/***/ }),

/***/ "../../../../../src/app/api/domain/identity/x500-name.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return X500Name; });
var X500Name = /** @class */ (function () {
    function X500Name(name) {
        this.name = name;
        var dictionary = {};
        name.split(',').forEach(function (kvp) {
            var tuple = kvp.split('=');
            dictionary[tuple[0]] = tuple[1];
        });
        this.commonName = dictionary['CN'];
        this.localityName = dictionary['L'];
        this.stateOrProvinceName = dictionary['ST'];
        this.organizationName = dictionary['O'];
        this.organizationalUnitName = dictionary['OU'];
        this.countryName = dictionary['C'];
        this.street = dictionary['STREET'];
        Object.freeze(this);
    }
    X500Name.prototype.toString = function () {
        return this.name;
    };
    return X500Name;
}());

//# sourceMappingURL=x500-name.js.map

/***/ }),

/***/ "../../../../../src/app/api/domain/messages/purchase-order-messages.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return PurchaseOrderRequestMessage; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "b", function() { return PurchaseOrderResponseMessage; });
var PurchaseOrderRequestMessage = /** @class */ (function () {
    function PurchaseOrderRequestMessage(supplier, value) {
        this.supplier = supplier;
        this.value = value;
    }
    return PurchaseOrderRequestMessage;
}());

var PurchaseOrderResponseMessage = /** @class */ (function () {
    function PurchaseOrderResponseMessage(linearId, transactionId) {
        this.linearId = linearId;
        this.transactionId = transactionId;
    }
    return PurchaseOrderResponseMessage;
}());

//# sourceMappingURL=purchase-order-messages.js.map

/***/ }),

/***/ "../../../../../src/app/api/services/base.service.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return BaseService; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_http__ = __webpack_require__("../../../http/@angular/http.es5.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};


var BaseService = /** @class */ (function () {
    function BaseService(http) {
        this.http = http;
    }
    BaseService.prototype.getUrl = function (endpoint) {
        return window.location.origin + "/api/" + endpoint;
    };
    BaseService = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["C" /* Injectable */])(),
        __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_1__angular_http__["a" /* Http */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1__angular_http__["a" /* Http */]) === "function" && _a || Object])
    ], BaseService);
    return BaseService;
    var _a;
}());

//# sourceMappingURL=base.service.js.map

/***/ }),

/***/ "../../../../../src/app/api/services/node.service.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return NodeService; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__base_service__ = __webpack_require__("../../../../../src/app/api/services/base.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_rxjs_add_operator_map__ = __webpack_require__("../../../../rxjs/add/operator/map.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_rxjs_add_operator_map___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_2_rxjs_add_operator_map__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__domain_identity_x500_name__ = __webpack_require__("../../../../../src/app/api/domain/identity/x500-name.ts");
var __extends = (this && this.__extends) || (function () {
    var extendStatics = Object.setPrototypeOf ||
        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
    return function (d, b) {
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};




var NodeService = /** @class */ (function (_super) {
    __extends(NodeService, _super);
    function NodeService() {
        return _super !== null && _super.apply(this, arguments) || this;
    }
    NodeService.prototype.getLocalNode = function () {
        return this.http
            .get(this.getUrl('nodes/local'))
            .map(function (response) { return new __WEBPACK_IMPORTED_MODULE_3__domain_identity_x500_name__["a" /* X500Name */](response.json()); });
    };
    NodeService.prototype.getNodes = function () {
        return this.http
            .get(this.getUrl('nodes'))
            .map(function (response) { return response.json().map(function (name) { return new __WEBPACK_IMPORTED_MODULE_3__domain_identity_x500_name__["a" /* X500Name */](name); }); });
    };
    NodeService = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["C" /* Injectable */])()
    ], NodeService);
    return NodeService;
}(__WEBPACK_IMPORTED_MODULE_1__base_service__["a" /* BaseService */]));

//# sourceMappingURL=node.service.js.map

/***/ }),

/***/ "../../../../../src/app/api/services/purchase-order.service.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return PurchaseOrderService; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__base_service__ = __webpack_require__("../../../../../src/app/api/services/base.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_rxjs_add_operator_map__ = __webpack_require__("../../../../rxjs/add/operator/map.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_rxjs_add_operator_map___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_2_rxjs_add_operator_map__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__domain_messages_purchase_order_messages__ = __webpack_require__("../../../../../src/app/api/domain/messages/purchase-order-messages.ts");
var __extends = (this && this.__extends) || (function () {
    var extendStatics = Object.setPrototypeOf ||
        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
    return function (d, b) {
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};




var PurchaseOrderService = /** @class */ (function (_super) {
    __extends(PurchaseOrderService, _super);
    function PurchaseOrderService() {
        return _super !== null && _super.apply(this, arguments) || this;
    }
    PurchaseOrderService.prototype.getPurchaseOrders = function () {
        return this.http
            .get(this.getUrl('purchaseorders'))
            .map(function (response) { return response.json(); });
    };
    PurchaseOrderService.prototype.createPurchaseOrder = function (message) {
        return this.http
            .post(this.getUrl('purchaseorders/create'), {
            supplier: message.supplier.toString(),
            value: message.value
        })
            .map(function (response) { return new __WEBPACK_IMPORTED_MODULE_3__domain_messages_purchase_order_messages__["b" /* PurchaseOrderResponseMessage */](response.json().linearId, response.json().transactionId); });
    };
    PurchaseOrderService = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["C" /* Injectable */])()
    ], PurchaseOrderService);
    return PurchaseOrderService;
}(__WEBPACK_IMPORTED_MODULE_1__base_service__["a" /* BaseService */]));

//# sourceMappingURL=purchase-order.service.js.map

/***/ }),

/***/ "../../../../../src/app/app.component.css":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "table {\r\n    width: 100%;\r\n}\r\n\r\nth {\r\n    text-align: left;\r\n}", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/app.component.html":
/***/ (function(module, exports) {

module.exports = "<div fxFill fxLayout=\"column\">\n  <md-toolbar color=\"primary\">\n    {{nodeName?.organizationName}}\n  </md-toolbar>\n  <div fxFlex fxLayout=\"row\" fxLayoutAlign=\"start stretch\">\n    <div fxFlex=\"25\">\n      <div class=\"container\">\n        <h3>Create a Purchase Order</h3>\n        <hr />\n        <md-select #supplier placeholder=\"Supplier\" [style.width.%]=\"100\">\n          <md-option *ngFor=\"let supplier of suppliers\" [value]=\"supplier.name\">{{supplier.organizationName}}</md-option>\n        </md-select>\n        <br />\n        <br />\n        <md-form-field [style.width.%]=\"100\">\n          <input #po mdInput placeholder=\"Value\" value=\"0.00\">\n        </md-form-field>\n        <br />\n        <br />\n        <button [style.width.%]=\"100\" md-raised-button color=\"primary\" (click)=\"create(supplier.value, po.value)\">Sign &amp; Send</button>\n      </div>\n    </div>\n    <div fxFlex=\"75\">\n      <div class=\"container\">\n        <h3>Purchase Order History</h3>\n        <hr />\n        <table>\n          <thead>\n            <tr>\n              <th>Buyer</th>\n              <th>Supplier</th>\n              <th>Value</th>\n            </tr>\n          </thead>\n          <tbody>\n            <ng-container *ngFor=\"let item of dataSource\">\n              <tr>\n                <td>{{getName(item.state.data.buyer)}}</td>\n                <td>{{getName(item.state.data.supplier)}}</td>\n                <td>{{item.state.data.purchaseOrder.value}}</td>\n              </tr>\n            </ng-container>\n          </tbody>\n        </table>\n      </div>\n    </div>\n  </div>\n</div>"

/***/ }),

/***/ "../../../../../src/app/app.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return AppComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__api_domain_identity_x500_name__ = __webpack_require__("../../../../../src/app/api/domain/identity/x500-name.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__api_domain_messages_purchase_order_messages__ = __webpack_require__("../../../../../src/app/api/domain/messages/purchase-order-messages.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__api_services_node_service__ = __webpack_require__("../../../../../src/app/api/services/node.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__api_services_purchase_order_service__ = __webpack_require__("../../../../../src/app/api/services/purchase-order.service.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};





var AppComponent = /** @class */ (function () {
    function AppComponent(nodeService, purchaseOrderService) {
        this.nodeService = nodeService;
        this.purchaseOrderService = purchaseOrderService;
        this.dataSource = [];
    }
    AppComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.nodeService
            .getLocalNode()
            .subscribe(function (response) {
            _this.nodeName = response;
            window.document.title = "Cordax | " + _this.nodeName.organizationName;
        });
        this.nodeService
            .getNodes()
            .subscribe(function (response) { return _this.suppliers = response.filter(function (name) { return name.commonName.indexOf('Supplier') > -1; }); });
        window.setInterval(function () {
            _this.purchaseOrderService
                .getPurchaseOrders()
                .subscribe(function (response) { return _this.dataSource = response; });
        }, 2000);
    };
    AppComponent.prototype.create = function (supplier, value) {
        this.purchaseOrderService
            .createPurchaseOrder(new __WEBPACK_IMPORTED_MODULE_2__api_domain_messages_purchase_order_messages__["a" /* PurchaseOrderRequestMessage */](new __WEBPACK_IMPORTED_MODULE_1__api_domain_identity_x500_name__["a" /* X500Name */](supplier), value))
            .subscribe(function (response) { return alert("Transaction " + response.transactionId + " committed to blockchain."); });
    };
    AppComponent.prototype.getName = function (x500name) {
        return new __WEBPACK_IMPORTED_MODULE_1__api_domain_identity_x500_name__["a" /* X500Name */](x500name).organizationName;
    };
    AppComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["o" /* Component */])({
            selector: 'app-root',
            template: __webpack_require__("../../../../../src/app/app.component.html"),
            styles: [__webpack_require__("../../../../../src/app/app.component.css")]
        }),
        __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_3__api_services_node_service__["a" /* NodeService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_3__api_services_node_service__["a" /* NodeService */]) === "function" && _a || Object, typeof (_b = typeof __WEBPACK_IMPORTED_MODULE_4__api_services_purchase_order_service__["a" /* PurchaseOrderService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_4__api_services_purchase_order_service__["a" /* PurchaseOrderService */]) === "function" && _b || Object])
    ], AppComponent);
    return AppComponent;
    var _a, _b;
}());

//# sourceMappingURL=app.component.js.map

/***/ }),

/***/ "../../../../../src/app/app.module.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return AppModule; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_platform_browser__ = __webpack_require__("../../../platform-browser/@angular/platform-browser.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__angular_forms__ = __webpack_require__("../../../forms/@angular/forms.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__angular_http__ = __webpack_require__("../../../http/@angular/http.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__angular_router__ = __webpack_require__("../../../router/@angular/router.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__angular_platform_browser_animations__ = __webpack_require__("../../../platform-browser/@angular/platform-browser/animations.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__angular_material__ = __webpack_require__("../../../material/@angular/material.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7__angular_flex_layout__ = __webpack_require__("../../../flex-layout/@angular/flex-layout.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8__app_component__ = __webpack_require__("../../../../../src/app/app.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_9__api_services_node_service__ = __webpack_require__("../../../../../src/app/api/services/node.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_10__api_services_purchase_order_service__ = __webpack_require__("../../../../../src/app/api/services/purchase-order.service.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};











var AppModule = /** @class */ (function () {
    function AppModule() {
    }
    AppModule = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_1__angular_core__["M" /* NgModule */])({
            declarations: [
                __WEBPACK_IMPORTED_MODULE_8__app_component__["a" /* AppComponent */]
            ],
            imports: [
                __WEBPACK_IMPORTED_MODULE_0__angular_platform_browser__["a" /* BrowserModule */],
                __WEBPACK_IMPORTED_MODULE_2__angular_forms__["c" /* FormsModule */],
                __WEBPACK_IMPORTED_MODULE_3__angular_http__["b" /* HttpModule */],
                __WEBPACK_IMPORTED_MODULE_4__angular_router__["a" /* RouterModule */],
                __WEBPACK_IMPORTED_MODULE_5__angular_platform_browser_animations__["a" /* BrowserAnimationsModule */],
                __WEBPACK_IMPORTED_MODULE_6__angular_material__["a" /* MaterialModule */],
                __WEBPACK_IMPORTED_MODULE_7__angular_flex_layout__["a" /* FlexLayoutModule */]
            ],
            providers: [__WEBPACK_IMPORTED_MODULE_9__api_services_node_service__["a" /* NodeService */], __WEBPACK_IMPORTED_MODULE_10__api_services_purchase_order_service__["a" /* PurchaseOrderService */]],
            bootstrap: [__WEBPACK_IMPORTED_MODULE_8__app_component__["a" /* AppComponent */]]
        })
    ], AppModule);
    return AppModule;
}());

//# sourceMappingURL=app.module.js.map

/***/ }),

/***/ "../../../../../src/environments/environment.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return environment; });
// The file contents for the current environment will overwrite these during build.
// The build system defaults to the dev environment which uses `environment.ts`, but if you do
// `ng build --env=prod` then `environment.prod.ts` will be used instead.
// The list of which env maps to which file can be found in `.angular-cli.json`.
var environment = {
    production: false
};
//# sourceMappingURL=environment.js.map

/***/ }),

/***/ "../../../../../src/main.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_platform_browser_dynamic__ = __webpack_require__("../../../platform-browser-dynamic/@angular/platform-browser-dynamic.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__app_app_module__ = __webpack_require__("../../../../../src/app/app.module.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__environments_environment__ = __webpack_require__("../../../../../src/environments/environment.ts");




if (__WEBPACK_IMPORTED_MODULE_3__environments_environment__["a" /* environment */].production) {
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_24" /* enableProdMode */])();
}
Object(__WEBPACK_IMPORTED_MODULE_1__angular_platform_browser_dynamic__["a" /* platformBrowserDynamic */])().bootstrapModule(__WEBPACK_IMPORTED_MODULE_2__app_app_module__["a" /* AppModule */]);
//# sourceMappingURL=main.js.map

/***/ }),

/***/ 0:
/***/ (function(module, exports, __webpack_require__) {

module.exports = __webpack_require__("../../../../../src/main.ts");


/***/ })

},[0]);
//# sourceMappingURL=main.bundle.js.map