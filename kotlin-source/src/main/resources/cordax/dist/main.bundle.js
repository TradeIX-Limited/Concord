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

/***/ "../../../../../src/api/domain/currency.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return Currency; });
var Currency = /** @class */ (function () {
    function Currency(code, symbol, fractionalUnits, fractionalUnitName, name) {
        this.code = code;
        this.symbol = symbol;
        this.fractionalUnits = fractionalUnits;
        this.fractionalUnitName = fractionalUnitName;
        this.name = name;
    }
    Currency.parse = function (value) {
        var result = Currency[value];
        if (result === null || result === undefined) {
            throw new Error("Currency '" + value + "' not found.");
        }
        return result;
    };
    Currency.getKnownCurrencies = function () {
        return [
            Currency.GBP,
            Currency.USD,
            Currency.EUR,
            Currency.JPY,
            Currency.CNY,
            Currency.AUD
        ];
    };
    Currency.prototype.toString = function () {
        return this.code;
    };
    Currency.GBP = new Currency('GBP', '£', 100, 'Penny', 'British Pound');
    Currency.USD = new Currency('USD', '$', 100, 'Cent', 'United States Dollar');
    Currency.EUR = new Currency('EUR', '€', 100, 'Cent', 'Euro');
    Currency.AUD = new Currency('AUD', '$', 100, 'Cent', 'Australian Dollar');
    Currency.JPY = new Currency('JPY', '¥', 100, 'Sen', 'Japanese Yen');
    Currency.CNY = new Currency('CNY', '¥', 100, 'Fen', 'Chinese Yaun');
    return Currency;
}());

//# sourceMappingURL=currency.js.map

/***/ }),

/***/ "../../../../../src/api/domain/messages.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* unused harmony export ErrorResponseMessage */
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "b", function() { return TransactionResponseMessage; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return LinearTransactionResponseMessage; });
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
var ErrorResponseMessage = /** @class */ (function () {
    function ErrorResponseMessage(message) {
        this.message = message;
    }
    return ErrorResponseMessage;
}());

var TransactionResponseMessage = /** @class */ (function () {
    function TransactionResponseMessage(transactionId) {
        this.transactionId = transactionId;
    }
    return TransactionResponseMessage;
}());

var LinearTransactionResponseMessage = /** @class */ (function (_super) {
    __extends(LinearTransactionResponseMessage, _super);
    function LinearTransactionResponseMessage(linearId, transactionId) {
        var _this = _super.call(this, transactionId) || this;
        _this.linearId = linearId;
        _this.transactionId = transactionId;
        return _this;
    }
    return LinearTransactionResponseMessage;
}(TransactionResponseMessage));

//# sourceMappingURL=messages.js.map

/***/ }),

/***/ "../../../../../src/api/domain/models/change-owner.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return ChangeOwnerViewModel; });
var ChangeOwnerViewModel = /** @class */ (function () {
    function ChangeOwnerViewModel(linearId, newOwner) {
        this.linearId = linearId;
        this.newOwner = newOwner;
    }
    ChangeOwnerViewModel.prototype.toRequestObject = function () {
        return {
            linearId: this.linearId,
            newOwner: this.newOwner.toString()
        };
    };
    return ChangeOwnerViewModel;
}());

//# sourceMappingURL=change-owner.js.map

/***/ }),

/***/ "../../../../../src/api/domain/models/trade-asset.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* unused harmony export TradeAsset */
/* unused harmony export TradeAssetState */
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return IssueTradeAssetViewModel; });
var TradeAsset = /** @class */ (function () {
    function TradeAsset(assetId, status, amount) {
        this.assetId = assetId;
        this.status = status;
        this.amount = amount;
    }
    return TradeAsset;
}());

var TradeAssetState = /** @class */ (function () {
    // Other fields?
    function TradeAssetState(linearId, tradeAsset, owner, buyer, supplier, conductor) {
        this.linearId = linearId;
        this.tradeAsset = tradeAsset;
        this.owner = owner;
        this.buyer = buyer;
        this.supplier = supplier;
        this.conductor = conductor;
    }
    return TradeAssetState;
}());

var IssueTradeAssetViewModel = /** @class */ (function () {
    function IssueTradeAssetViewModel(buyer, supplier, assetId, amount, currency) {
        this.buyer = buyer;
        this.supplier = supplier;
        this.assetId = assetId;
        this.amount = amount;
        this.currency = currency;
    }
    IssueTradeAssetViewModel.prototype.toRequestObject = function () {
        var result = {
            supplier: this.supplier.toString(),
            assetId: this.assetId,
            amount: this.amount,
            currency: this.currency.toString()
        };
        if (this.buyer) {
            result['buyer'] = this.buyer.toString();
        }
        return result;
    };
    return IssueTradeAssetViewModel;
}());

//# sourceMappingURL=trade-asset.js.map

/***/ }),

/***/ "../../../../../src/api/domain/services/base.service.ts":
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
        return "http://localhost:10007/api/" + endpoint;
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

/***/ "../../../../../src/api/domain/services/node.service.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return NodeService; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__base_service__ = __webpack_require__("../../../../../src/api/domain/services/base.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__x_500_name__ = __webpack_require__("../../../../../src/api/domain/x-500-name.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_rxjs_add_operator_map__ = __webpack_require__("../../../../rxjs/add/operator/map.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_rxjs_add_operator_map___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_3_rxjs_add_operator_map__);
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
            .map(function (response) { return __WEBPACK_IMPORTED_MODULE_2__x_500_name__["a" /* X500Name */].parse(response.json()); });
    };
    NodeService.prototype.getNodes = function () {
        return this.http
            .get(this.getUrl('nodes'))
            .map(function (response) { return response.json().map(function (name) { return __WEBPACK_IMPORTED_MODULE_2__x_500_name__["a" /* X500Name */].parse(name); }); });
    };
    NodeService = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["C" /* Injectable */])()
    ], NodeService);
    return NodeService;
}(__WEBPACK_IMPORTED_MODULE_1__base_service__["a" /* BaseService */]));

//# sourceMappingURL=node.service.js.map

/***/ }),

/***/ "../../../../../src/api/domain/services/trade-asset.service.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return TradeAssetService; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__base_service__ = __webpack_require__("../../../../../src/api/domain/services/base.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__messages__ = __webpack_require__("../../../../../src/api/domain/messages.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_rxjs_add_operator_map__ = __webpack_require__("../../../../rxjs/add/operator/map.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_rxjs_add_operator_map___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_3_rxjs_add_operator_map__);
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




var TradeAssetService = /** @class */ (function (_super) {
    __extends(TradeAssetService, _super);
    function TradeAssetService() {
        return _super !== null && _super.apply(this, arguments) || this;
    }
    TradeAssetService.prototype.getTradeAssets = function () {
        return this.http
            .get(this.getUrl('tradeassets'))
            .map(function (response) { return response.json(); });
    };
    TradeAssetService.prototype.issueTradeAsset = function (tradeAssetViewModel) {
        return this.http
            .post(this.getUrl('tradeassets/issue'), tradeAssetViewModel.toRequestObject())
            .map(function (response) { return new __WEBPACK_IMPORTED_MODULE_2__messages__["a" /* LinearTransactionResponseMessage */](response.json().linearId, response.json().transactionId); });
    };
    TradeAssetService.prototype.changeOwner = function (changeOwnerViewModel) {
        return this.http
            .put(this.getUrl('tradeassets/changeowner'), changeOwnerViewModel.toRequestObject())
            .map(function (response) { return new __WEBPACK_IMPORTED_MODULE_2__messages__["b" /* TransactionResponseMessage */](response.json().transactionId); });
    };
    TradeAssetService = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["C" /* Injectable */])()
    ], TradeAssetService);
    return TradeAssetService;
}(__WEBPACK_IMPORTED_MODULE_1__base_service__["a" /* BaseService */]));

//# sourceMappingURL=trade-asset.service.js.map

/***/ }),

/***/ "../../../../../src/api/domain/x-500-name.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return X500Name; });
var X500Name = /** @class */ (function () {
    function X500Name(commonName, organizationUnit, organizationName, localityName, stateName, country) {
        this.commonName = commonName;
        this.organizationUnit = organizationUnit;
        this.organizationName = organizationName;
        this.localityName = localityName;
        this.stateName = stateName;
        this.country = country;
        Object.freeze(this);
    }
    X500Name.parse = function (value) {
        var dict = {};
        value.split(',').forEach(function (part) {
            var pair = part.split('=');
            dict[pair[0]] = pair[1];
        });
        return new X500Name(dict['CN'], dict['OU'], dict['O'], dict['L'], dict['S'], dict['C']);
    };
    X500Name.prototype.toString = function () {
        var dict = {
            'CN': this.commonName,
            'OU': this.organizationUnit,
            'O': this.organizationName,
            'L': this.localityName,
            'S': this.stateName,
            'C': this.country
        };
        return Object
            .keys(dict)
            .filter(function (key) { return dict[key] !== undefined && dict[key] !== null; })
            .map(function (key) { return key + "=" + dict[key]; })
            .join(',');
    };
    return X500Name;
}());

//# sourceMappingURL=x-500-name.js.map

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
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__angular_flex_layout__ = __webpack_require__("../../../flex-layout/@angular/flex-layout.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__angular_platform_browser_animations__ = __webpack_require__("../../../platform-browser/@angular/platform-browser/animations.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7__angular_material__ = __webpack_require__("../../../material/esm5/material.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8__root_root_component__ = __webpack_require__("../../../../../src/app/root/root.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_9_hammerjs__ = __webpack_require__("../../../../hammerjs/hammer.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_9_hammerjs___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_9_hammerjs__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_10__conductor_dashboard_conductor_dashboard_component__ = __webpack_require__("../../../../../src/app/conductor-dashboard/conductor-dashboard.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_11__buyer_dashboard_buyer_dashboard_component__ = __webpack_require__("../../../../../src/app/buyer-dashboard/buyer-dashboard.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_12__buyer_dashboard_new_purchase_order_dialog_new_purchase_order_dialog_component__ = __webpack_require__("../../../../../src/app/buyer-dashboard/new-purchase-order-dialog/new-purchase-order-dialog.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_13__conductor_dashboard_new_purchase_order_dialog_new_purchase_order_dialog_component__ = __webpack_require__("../../../../../src/app/conductor-dashboard/new-purchase-order-dialog/new-purchase-order-dialog.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_14__supplier_dashboard_supplier_dashboard_component__ = __webpack_require__("../../../../../src/app/supplier-dashboard/supplier-dashboard.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_15__funder_dashboard_funder_dashboard_component__ = __webpack_require__("../../../../../src/app/funder-dashboard/funder-dashboard.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_16__main_main_component__ = __webpack_require__("../../../../../src/app/main/main.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_17__api_domain_services_node_service__ = __webpack_require__("../../../../../src/api/domain/services/node.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_18__api_domain_services_trade_asset_service__ = __webpack_require__("../../../../../src/api/domain/services/trade-asset.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_19__supplier_dashboard_change_owner_dialog_change_owner_dialog_component__ = __webpack_require__("../../../../../src/app/supplier-dashboard/change-owner-dialog/change-owner-dialog.component.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};




















var routes = [
    { path: 'conductor', component: __WEBPACK_IMPORTED_MODULE_10__conductor_dashboard_conductor_dashboard_component__["a" /* ConductorDashboardComponent */] },
    { path: 'buyer', component: __WEBPACK_IMPORTED_MODULE_11__buyer_dashboard_buyer_dashboard_component__["a" /* BuyerDashboardComponent */] },
    { path: 'supplier', component: __WEBPACK_IMPORTED_MODULE_14__supplier_dashboard_supplier_dashboard_component__["a" /* SupplierDashboardComponent */] },
    { path: 'funder', component: __WEBPACK_IMPORTED_MODULE_15__funder_dashboard_funder_dashboard_component__["a" /* FunderDashboardComponent */] }
];
var AppModule = /** @class */ (function () {
    function AppModule() {
    }
    AppModule = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_1__angular_core__["M" /* NgModule */])({
            declarations: [
                __WEBPACK_IMPORTED_MODULE_8__root_root_component__["a" /* RootComponent */],
                __WEBPACK_IMPORTED_MODULE_11__buyer_dashboard_buyer_dashboard_component__["a" /* BuyerDashboardComponent */],
                __WEBPACK_IMPORTED_MODULE_12__buyer_dashboard_new_purchase_order_dialog_new_purchase_order_dialog_component__["a" /* NewPurchaseOrderDialogComponent */],
                __WEBPACK_IMPORTED_MODULE_13__conductor_dashboard_new_purchase_order_dialog_new_purchase_order_dialog_component__["a" /* NewPurchaseOrderDialogComponent */],
                __WEBPACK_IMPORTED_MODULE_14__supplier_dashboard_supplier_dashboard_component__["a" /* SupplierDashboardComponent */],
                __WEBPACK_IMPORTED_MODULE_15__funder_dashboard_funder_dashboard_component__["a" /* FunderDashboardComponent */],
                __WEBPACK_IMPORTED_MODULE_16__main_main_component__["a" /* MainComponent */],
                __WEBPACK_IMPORTED_MODULE_10__conductor_dashboard_conductor_dashboard_component__["a" /* ConductorDashboardComponent */],
                __WEBPACK_IMPORTED_MODULE_19__supplier_dashboard_change_owner_dialog_change_owner_dialog_component__["a" /* ChangeOwnerDialogComponent */]
            ],
            imports: [
                __WEBPACK_IMPORTED_MODULE_5__angular_flex_layout__["a" /* FlexLayoutModule */],
                __WEBPACK_IMPORTED_MODULE_6__angular_platform_browser_animations__["a" /* BrowserAnimationsModule */],
                __WEBPACK_IMPORTED_MODULE_0__angular_platform_browser__["a" /* BrowserModule */],
                __WEBPACK_IMPORTED_MODULE_2__angular_forms__["c" /* FormsModule */],
                __WEBPACK_IMPORTED_MODULE_3__angular_http__["b" /* HttpModule */],
                __WEBPACK_IMPORTED_MODULE_7__angular_material__["q" /* MatToolbarModule */],
                __WEBPACK_IMPORTED_MODULE_7__angular_material__["p" /* MatSidenavModule */],
                __WEBPACK_IMPORTED_MODULE_7__angular_material__["i" /* MatIconModule */],
                __WEBPACK_IMPORTED_MODULE_7__angular_material__["b" /* MatButtonModule */],
                __WEBPACK_IMPORTED_MODULE_7__angular_material__["l" /* MatMenuModule */],
                __WEBPACK_IMPORTED_MODULE_7__angular_material__["k" /* MatListModule */],
                __WEBPACK_IMPORTED_MODULE_7__angular_material__["c" /* MatCardModule */],
                __WEBPACK_IMPORTED_MODULE_7__angular_material__["f" /* MatDialogModule */],
                __WEBPACK_IMPORTED_MODULE_7__angular_material__["o" /* MatSelectModule */],
                __WEBPACK_IMPORTED_MODULE_7__angular_material__["m" /* MatOptionModule */],
                __WEBPACK_IMPORTED_MODULE_7__angular_material__["j" /* MatInputModule */],
                __WEBPACK_IMPORTED_MODULE_7__angular_material__["h" /* MatFormFieldModule */],
                __WEBPACK_IMPORTED_MODULE_7__angular_material__["n" /* MatProgressSpinnerModule */],
                __WEBPACK_IMPORTED_MODULE_7__angular_material__["d" /* MatChipsModule */],
                __WEBPACK_IMPORTED_MODULE_4__angular_router__["b" /* RouterModule */].forRoot(routes, { enableTracing: false })
            ],
            providers: [__WEBPACK_IMPORTED_MODULE_17__api_domain_services_node_service__["a" /* NodeService */], __WEBPACK_IMPORTED_MODULE_18__api_domain_services_trade_asset_service__["a" /* TradeAssetService */]],
            bootstrap: [__WEBPACK_IMPORTED_MODULE_8__root_root_component__["a" /* RootComponent */]],
            entryComponents: [__WEBPACK_IMPORTED_MODULE_12__buyer_dashboard_new_purchase_order_dialog_new_purchase_order_dialog_component__["a" /* NewPurchaseOrderDialogComponent */], __WEBPACK_IMPORTED_MODULE_13__conductor_dashboard_new_purchase_order_dialog_new_purchase_order_dialog_component__["a" /* NewPurchaseOrderDialogComponent */], __WEBPACK_IMPORTED_MODULE_19__supplier_dashboard_change_owner_dialog_change_owner_dialog_component__["a" /* ChangeOwnerDialogComponent */]]
        })
    ], AppModule);
    return AppModule;
}());

//# sourceMappingURL=app.module.js.map

/***/ }),

/***/ "../../../../../src/app/buyer-dashboard/buyer-dashboard.component.html":
/***/ (function(module, exports) {

module.exports = "<div fxLayout=\"column\">\r\n  <div class=\"app-padding\" fxFlex>\r\n    <div class=\"fxFlex\" fxLayout=\"row\">\r\n      <div fxFlex>\r\n        <h1>Buyer Dashboard</h1>\r\n      </div>\r\n      <div>\r\n        <button mat-raised-button color=\"accent\" (click)=\"openDialog()\">New Trade Asset</button>\r\n      </div>\r\n    </div>\r\n  </div>\r\n  <div class=\"app-padding\" fxFlex>\r\n    <table>\r\n      <thead>\r\n        <tr>\r\n          <th>Linear ID</th>\r\n          <th>Transaction ID</th>\r\n          <th>Asset ID</th>\r\n          <th>Buyer</th>\r\n          <th>Supplier</th>\r\n          <th>Owner</th>\r\n          <th>Status</th>\r\n          <th class=\"align-right\">Value</th>\r\n        </tr>\r\n      </thead>\r\n      <tbody *ngIf=\"states.length > 0\">\r\n        <ng-container *ngFor=\"let state of states\">\r\n          <tr>\r\n            <td>{{state.state.data.linearId.id}}</td>\r\n            <td>{{state.ref.txhash}}</td>\r\n            <td>{{state.state.data.tradeAsset.assetId}}</td>\r\n            <td>{{getName(state.state.data.buyer)}}</td>\r\n            <td>{{getName(state.state.data.supplier)}}</td>\r\n            <td>{{getName(state.state.data.owner)}}</td>\r\n            <td>\r\n              <mat-chip-list>\r\n                <mat-chip selected=\"true\" color=\"accent\">{{state.state.data.tradeAsset.status}}</mat-chip>\r\n              </mat-chip-list>\r\n            </td>\r\n            <td class=\"align-right\">{{state.state.data.tradeAsset.amount}}</td>\r\n          </tr>\r\n        </ng-container>\r\n      </tbody>\r\n    </table>\r\n  </div>\r\n</div>"

/***/ }),

/***/ "../../../../../src/app/buyer-dashboard/buyer-dashboard.component.scss":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/buyer-dashboard/buyer-dashboard.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return BuyerDashboardComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_material__ = __webpack_require__("../../../material/esm5/material.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__new_purchase_order_dialog_new_purchase_order_dialog_component__ = __webpack_require__("../../../../../src/app/buyer-dashboard/new-purchase-order-dialog/new-purchase-order-dialog.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__api_domain_services_trade_asset_service__ = __webpack_require__("../../../../../src/api/domain/services/trade-asset.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__api_domain_x_500_name__ = __webpack_require__("../../../../../src/api/domain/x-500-name.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};





var BuyerDashboardComponent = /** @class */ (function () {
    function BuyerDashboardComponent(dialog, tradeAssetService) {
        this.dialog = dialog;
        this.tradeAssetService = tradeAssetService;
        this.states = [];
    }
    BuyerDashboardComponent.prototype.getStates = function () {
        var _this = this;
        this.tradeAssetService
            .getTradeAssets()
            .subscribe(function (response) {
            if (response.length !== _this.states.length) {
                _this.states = response;
            }
        });
    };
    BuyerDashboardComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.getStates();
        setInterval(function () { return _this.getStates(); }, 2000);
    };
    BuyerDashboardComponent.prototype.openDialog = function () {
        this.dialog.open(__WEBPACK_IMPORTED_MODULE_2__new_purchase_order_dialog_new_purchase_order_dialog_component__["a" /* NewPurchaseOrderDialogComponent */], {
            width: '640px',
            disableClose: true
        });
    };
    BuyerDashboardComponent.prototype.getName = function (value) {
        return !!value ? __WEBPACK_IMPORTED_MODULE_4__api_domain_x_500_name__["a" /* X500Name */].parse(value).organizationName : '';
    };
    BuyerDashboardComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["o" /* Component */])({
            selector: 'app-buyer-dashboard',
            template: __webpack_require__("../../../../../src/app/buyer-dashboard/buyer-dashboard.component.html"),
            styles: [__webpack_require__("../../../../../src/app/buyer-dashboard/buyer-dashboard.component.scss")]
        }),
        __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_1__angular_material__["e" /* MatDialog */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1__angular_material__["e" /* MatDialog */]) === "function" && _a || Object, typeof (_b = typeof __WEBPACK_IMPORTED_MODULE_3__api_domain_services_trade_asset_service__["a" /* TradeAssetService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_3__api_domain_services_trade_asset_service__["a" /* TradeAssetService */]) === "function" && _b || Object])
    ], BuyerDashboardComponent);
    return BuyerDashboardComponent;
    var _a, _b;
}());

//# sourceMappingURL=buyer-dashboard.component.js.map

/***/ }),

/***/ "../../../../../src/app/buyer-dashboard/new-purchase-order-dialog/new-purchase-order-dialog.component.html":
/***/ (function(module, exports) {

module.exports = "<h2>New Trade Asset</h2>\r\n<div *ngIf=\"view === 0\">\r\n  <div fxLayout=\"column\">\r\n    <div fxLayout=\"row\">\r\n      <mat-form-field fxFlex=\"100\">\r\n        <mat-select [(ngModel)]=\"model.supplier\" placeholder=\"Supplier\">\r\n          <mat-option *ngFor=\"let node of nodes\" [value]=\"node\">{{node.organizationName}}</mat-option>\r\n        </mat-select>\r\n      </mat-form-field>\r\n    </div>\r\n    <div fxLayout=\"row\">\r\n      <mat-form-field fxFlex=\"100\">\r\n        <input matInput [(ngModel)]=\"model.assetId\" type=\"text\" placeholder=\"Asset ID\" />\r\n      </mat-form-field>\r\n    </div>\r\n    <div fxLayout=\"row\">\r\n      <mat-form-field fxFlex=\"100\">\r\n        <input matInput [(ngModel)]=\"model.amount\" type=\"text\" placeholder=\"Value\" />\r\n      </mat-form-field>\r\n    </div>\r\n    <div fxLayout=\"row\">\r\n      <mat-form-field fxFlex=\"100\">\r\n        <mat-select [(ngModel)]=\"model.currency\" placeholder=\"Currency\" #select=\"matSelect\">\r\n          <mat-select-trigger>{{select.selected?.value.code}}</mat-select-trigger>\r\n          <mat-option *ngFor=\"let item of currencies\" [value]=\"item\">\r\n            <span fxFlex>{{item.code}}</span>{{item.name}}\r\n          </mat-option>\r\n        </mat-select>\r\n      </mat-form-field>\r\n    </div>\r\n  </div>\r\n  <div fxLayout=\"row\">\r\n    <button mat-raised-button fxFlex color=\"accent\" (click)=\"okay()\">OK</button>\r\n    <div fxFlex=\"5\"></div>\r\n    <button mat-raised-button fxFlex color=\"warn\" (click)=\"cancel()\">Cancel</button>\r\n  </div>\r\n</div>\r\n<div *ngIf=\"view === 1\">\r\n  <div fxLayout=\"column\" fxLayoutAlign=\"space-around center\" class=\"app-padding\">\r\n    <mat-spinner color=\"accent\"></mat-spinner>\r\n    <p>Conducting transaction, please wait...</p>\r\n  </div>\r\n</div>"

/***/ }),

/***/ "../../../../../src/app/buyer-dashboard/new-purchase-order-dialog/new-purchase-order-dialog.component.scss":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "mat-icon {\n  -webkit-transform: scale(2);\n          transform: scale(2); }\n", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/buyer-dashboard/new-purchase-order-dialog/new-purchase-order-dialog.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return NewPurchaseOrderDialogComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_material__ = __webpack_require__("../../../material/esm5/material.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__api_domain_currency__ = __webpack_require__("../../../../../src/api/domain/currency.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__api_domain_models_trade_asset__ = __webpack_require__("../../../../../src/api/domain/models/trade-asset.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__api_domain_services_node_service__ = __webpack_require__("../../../../../src/api/domain/services/node.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__api_domain_services_trade_asset_service__ = __webpack_require__("../../../../../src/api/domain/services/trade-asset.service.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};






var NewPurchaseOrderDialogComponent = /** @class */ (function () {
    function NewPurchaseOrderDialogComponent(nodeService, tradeAssetService, dialogRef) {
        var _this = this;
        this.nodeService = nodeService;
        this.tradeAssetService = tradeAssetService;
        this.dialogRef = dialogRef;
        this.currencies = __WEBPACK_IMPORTED_MODULE_2__api_domain_currency__["a" /* Currency */].getKnownCurrencies();
        this.model = new __WEBPACK_IMPORTED_MODULE_3__api_domain_models_trade_asset__["a" /* IssueTradeAssetViewModel */]();
        this.view = 0;
        this.nodes = [];
        this.nodeService
            .getNodes()
            .subscribe(function (nodes) { return _this.nodes = nodes; });
    }
    NewPurchaseOrderDialogComponent.prototype.ngOnInit = function () {
    };
    NewPurchaseOrderDialogComponent.prototype.okay = function () {
        var _this = this;
        this.view = 1;
        this.tradeAssetService
            .issueTradeAsset(this.model)
            .subscribe(function (response) {
            _this.cancel();
        });
    };
    NewPurchaseOrderDialogComponent.prototype.cancel = function () {
        this.dialogRef.close();
    };
    NewPurchaseOrderDialogComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["o" /* Component */])({
            selector: 'app-new-purchase-order-dialog',
            template: __webpack_require__("../../../../../src/app/buyer-dashboard/new-purchase-order-dialog/new-purchase-order-dialog.component.html"),
            styles: [__webpack_require__("../../../../../src/app/buyer-dashboard/new-purchase-order-dialog/new-purchase-order-dialog.component.scss")]
        }),
        __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_4__api_domain_services_node_service__["a" /* NodeService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_4__api_domain_services_node_service__["a" /* NodeService */]) === "function" && _a || Object, typeof (_b = typeof __WEBPACK_IMPORTED_MODULE_5__api_domain_services_trade_asset_service__["a" /* TradeAssetService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_5__api_domain_services_trade_asset_service__["a" /* TradeAssetService */]) === "function" && _b || Object, typeof (_c = typeof __WEBPACK_IMPORTED_MODULE_1__angular_material__["g" /* MatDialogRef */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1__angular_material__["g" /* MatDialogRef */]) === "function" && _c || Object])
    ], NewPurchaseOrderDialogComponent);
    return NewPurchaseOrderDialogComponent;
    var _a, _b, _c;
}());

//# sourceMappingURL=new-purchase-order-dialog.component.js.map

/***/ }),

/***/ "../../../../../src/app/conductor-dashboard/conductor-dashboard.component.html":
/***/ (function(module, exports) {

module.exports = "<div fxLayout=\"column\">\r\n  <div class=\"app-padding\" fxFlex>\r\n    <div class=\"fxFlex\" fxLayout=\"row\">\r\n      <div fxFlex>\r\n        <h1>Conductor Dashboard</h1>\r\n      </div>\r\n      <div>\r\n        <button mat-raised-button color=\"accent\" (click)=\"openDialog()\">New Trade Asset</button>\r\n      </div>\r\n    </div>\r\n  </div>\r\n  <div class=\"app-padding\" fxFlex>\r\n    <table>\r\n      <thead>\r\n        <tr>\r\n          <th>Linear ID</th>\r\n          <th>Transaction ID</th>\r\n          <th>Asset ID</th>\r\n          <th>Buyer</th>\r\n          <th>Supplier</th>\r\n          <th>Owner</th>\r\n          <th>Status</th>\r\n          <th class=\"align-right\">Value</th>\r\n        </tr>\r\n      </thead>\r\n      <tbody *ngIf=\"states.length > 0\">\r\n        <ng-container *ngFor=\"let state of states\">\r\n          <tr>\r\n            <td>{{state.state.data.linearId.id}}</td>\r\n            <td>{{state.ref.txhash}}</td>\r\n            <td>{{state.state.data.tradeAsset.assetId}}</td>\r\n            <td>{{getName(state.state.data.buyer)}}</td>\r\n            <td>{{getName(state.state.data.supplier)}}</td>\r\n            <td>{{getName(state.state.data.owner)}}</td>\r\n            <td>\r\n              <mat-chip-list>\r\n                <mat-chip selected=\"true\" color=\"accent\">{{state.state.data.tradeAsset.status}}</mat-chip>\r\n              </mat-chip-list>\r\n            </td>\r\n            <td class=\"align-right\">{{state.state.data.tradeAsset.amount}}</td>\r\n          </tr>\r\n        </ng-container>\r\n      </tbody>\r\n    </table>\r\n  </div>\r\n</div>"

/***/ }),

/***/ "../../../../../src/app/conductor-dashboard/conductor-dashboard.component.scss":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/conductor-dashboard/conductor-dashboard.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return ConductorDashboardComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_material__ = __webpack_require__("../../../material/esm5/material.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__new_purchase_order_dialog_new_purchase_order_dialog_component__ = __webpack_require__("../../../../../src/app/conductor-dashboard/new-purchase-order-dialog/new-purchase-order-dialog.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__api_domain_services_trade_asset_service__ = __webpack_require__("../../../../../src/api/domain/services/trade-asset.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__api_domain_x_500_name__ = __webpack_require__("../../../../../src/api/domain/x-500-name.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};





var ConductorDashboardComponent = /** @class */ (function () {
    function ConductorDashboardComponent(dialog, tradeAssetService) {
        this.dialog = dialog;
        this.tradeAssetService = tradeAssetService;
        this.states = [];
    }
    ConductorDashboardComponent.prototype.getStates = function () {
        var _this = this;
        this.tradeAssetService
            .getTradeAssets()
            .subscribe(function (response) {
            if (response.length !== _this.states.length) {
                _this.states = response;
            }
        });
    };
    ConductorDashboardComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.getStates();
        setInterval(function () { return _this.getStates(); }, 2000);
    };
    ConductorDashboardComponent.prototype.openDialog = function () {
        this.dialog.open(__WEBPACK_IMPORTED_MODULE_2__new_purchase_order_dialog_new_purchase_order_dialog_component__["a" /* NewPurchaseOrderDialogComponent */], {
            width: '640px',
            disableClose: true
        });
    };
    ConductorDashboardComponent.prototype.getName = function (value) {
        return !!value ? __WEBPACK_IMPORTED_MODULE_4__api_domain_x_500_name__["a" /* X500Name */].parse(value).organizationName : '';
    };
    ConductorDashboardComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["o" /* Component */])({
            selector: 'app-conductor-dashboard',
            template: __webpack_require__("../../../../../src/app/conductor-dashboard/conductor-dashboard.component.html"),
            styles: [__webpack_require__("../../../../../src/app/conductor-dashboard/conductor-dashboard.component.scss")]
        }),
        __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_1__angular_material__["e" /* MatDialog */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1__angular_material__["e" /* MatDialog */]) === "function" && _a || Object, typeof (_b = typeof __WEBPACK_IMPORTED_MODULE_3__api_domain_services_trade_asset_service__["a" /* TradeAssetService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_3__api_domain_services_trade_asset_service__["a" /* TradeAssetService */]) === "function" && _b || Object])
    ], ConductorDashboardComponent);
    return ConductorDashboardComponent;
    var _a, _b;
}());

//# sourceMappingURL=conductor-dashboard.component.js.map

/***/ }),

/***/ "../../../../../src/app/conductor-dashboard/new-purchase-order-dialog/new-purchase-order-dialog.component.html":
/***/ (function(module, exports) {

module.exports = "<h2>New Trade Asset</h2>\r\n<div *ngIf=\"view === 0\">\r\n  <div fxLayout=\"column\">\r\n    <div fxLayout=\"row\">\r\n      <mat-form-field fxFlex=\"100\">\r\n        <mat-select [(ngModel)]=\"model.buyer\" placeholder=\"Buyer\">\r\n          <mat-option *ngFor=\"let node of nodes\" [value]=\"node\">{{node.organizationName}}</mat-option>\r\n        </mat-select>\r\n      </mat-form-field>\r\n    </div>\r\n    <div fxLayout=\"row\">\r\n      <mat-form-field fxFlex=\"100\">\r\n        <mat-select [(ngModel)]=\"model.supplier\" placeholder=\"Supplier\">\r\n          <mat-option *ngFor=\"let node of nodes\" [value]=\"node\">{{node.organizationName}}</mat-option>\r\n        </mat-select>\r\n      </mat-form-field>\r\n    </div>\r\n    <div fxLayout=\"row\">\r\n      <mat-form-field fxFlex=\"100\">\r\n        <input matInput [(ngModel)]=\"model.assetId\" type=\"text\" placeholder=\"Asset ID\" />\r\n      </mat-form-field>\r\n    </div>\r\n    <div fxLayout=\"row\">\r\n      <mat-form-field fxFlex=\"100\">\r\n        <input matInput [(ngModel)]=\"model.amount\" type=\"text\" placeholder=\"Value\" />\r\n      </mat-form-field>\r\n    </div>\r\n    <div fxLayout=\"row\">\r\n      <mat-form-field fxFlex=\"100\">\r\n        <mat-select [(ngModel)]=\"model.currency\" placeholder=\"Currency\" #select=\"matSelect\">\r\n          <mat-select-trigger>{{select.selected?.value.code}}</mat-select-trigger>\r\n          <mat-option *ngFor=\"let item of currencies\" [value]=\"item\">\r\n            <span fxFlex>{{item.code}}</span>{{item.name}}\r\n          </mat-option>\r\n        </mat-select>\r\n      </mat-form-field>\r\n    </div>\r\n  </div>\r\n  <div fxLayout=\"row\">\r\n    <button mat-raised-button fxFlex color=\"accent\" (click)=\"okay()\">OK</button>\r\n    <div fxFlex=\"5\"></div>\r\n    <button mat-raised-button fxFlex color=\"warn\" (click)=\"cancel()\">Cancel</button>\r\n  </div>\r\n</div>\r\n<div *ngIf=\"view === 1\">\r\n  <div fxLayout=\"column\" fxLayoutAlign=\"space-around center\" class=\"app-padding\">\r\n    <mat-spinner color=\"accent\"></mat-spinner>\r\n    <p>Conducting transaction, please wait...</p>\r\n  </div>\r\n</div>"

/***/ }),

/***/ "../../../../../src/app/conductor-dashboard/new-purchase-order-dialog/new-purchase-order-dialog.component.scss":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "mat-icon {\n  -webkit-transform: scale(2);\n          transform: scale(2); }\n", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/conductor-dashboard/new-purchase-order-dialog/new-purchase-order-dialog.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return NewPurchaseOrderDialogComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_material__ = __webpack_require__("../../../material/esm5/material.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__api_domain_currency__ = __webpack_require__("../../../../../src/api/domain/currency.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__api_domain_models_trade_asset__ = __webpack_require__("../../../../../src/api/domain/models/trade-asset.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__api_domain_services_node_service__ = __webpack_require__("../../../../../src/api/domain/services/node.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__api_domain_services_trade_asset_service__ = __webpack_require__("../../../../../src/api/domain/services/trade-asset.service.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};






var NewPurchaseOrderDialogComponent = /** @class */ (function () {
    function NewPurchaseOrderDialogComponent(nodeService, tradeAssetService, dialogRef) {
        var _this = this;
        this.nodeService = nodeService;
        this.tradeAssetService = tradeAssetService;
        this.dialogRef = dialogRef;
        this.currencies = __WEBPACK_IMPORTED_MODULE_2__api_domain_currency__["a" /* Currency */].getKnownCurrencies();
        this.model = new __WEBPACK_IMPORTED_MODULE_3__api_domain_models_trade_asset__["a" /* IssueTradeAssetViewModel */]();
        this.view = 0;
        this.nodes = [];
        this.nodeService
            .getNodes()
            .subscribe(function (nodes) { return _this.nodes = nodes; });
    }
    NewPurchaseOrderDialogComponent.prototype.ngOnInit = function () {
    };
    NewPurchaseOrderDialogComponent.prototype.okay = function () {
        var _this = this;
        this.view = 1;
        this.tradeAssetService
            .issueTradeAsset(this.model)
            .subscribe(function (response) {
            _this.cancel();
        });
    };
    NewPurchaseOrderDialogComponent.prototype.cancel = function () {
        this.dialogRef.close();
    };
    NewPurchaseOrderDialogComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["o" /* Component */])({
            selector: 'app-new-purchase-order-dialog',
            template: __webpack_require__("../../../../../src/app/conductor-dashboard/new-purchase-order-dialog/new-purchase-order-dialog.component.html"),
            styles: [__webpack_require__("../../../../../src/app/conductor-dashboard/new-purchase-order-dialog/new-purchase-order-dialog.component.scss")]
        }),
        __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_4__api_domain_services_node_service__["a" /* NodeService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_4__api_domain_services_node_service__["a" /* NodeService */]) === "function" && _a || Object, typeof (_b = typeof __WEBPACK_IMPORTED_MODULE_5__api_domain_services_trade_asset_service__["a" /* TradeAssetService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_5__api_domain_services_trade_asset_service__["a" /* TradeAssetService */]) === "function" && _b || Object, typeof (_c = typeof __WEBPACK_IMPORTED_MODULE_1__angular_material__["g" /* MatDialogRef */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1__angular_material__["g" /* MatDialogRef */]) === "function" && _c || Object])
    ], NewPurchaseOrderDialogComponent);
    return NewPurchaseOrderDialogComponent;
    var _a, _b, _c;
}());

//# sourceMappingURL=new-purchase-order-dialog.component.js.map

/***/ }),

/***/ "../../../../../src/app/funder-dashboard/funder-dashboard.component.html":
/***/ (function(module, exports) {

module.exports = "<div fxLayout=\"column\">\r\n  <div class=\"app-padding\" fxFlex>\r\n    <div class=\"fxFlex\" fxLayout=\"row\">\r\n      <div fxFlex>\r\n        <h1>Supplier Dashboard</h1>\r\n      </div>\r\n    </div>\r\n  </div>\r\n  <div class=\"app-padding\" fxFlex>\r\n    <table>\r\n      <thead>\r\n        <tr>\r\n          <th>Linear ID</th>\r\n          <th>Transaction ID</th>\r\n          <th>Asset ID</th>\r\n          <th>Buyer</th>\r\n          <th>Supplier</th>\r\n          <th>Owner</th>\r\n          <th>Value</th>\r\n          <th>Actions</th>\r\n        </tr>\r\n      </thead>\r\n      <tbody>\r\n        <tr>\r\n          <td>e267a4b9-76e3-488b-bc79-b52ee93caae7</td>\r\n          <td>C3AB8FF13720E8AD9047DD39466B3C8974E592C2FA383D4A3960714CAEF0C4F2</td>\r\n          <td>TRADE-ASSET-000-001</td>\r\n          <td>Alice In Wonderland</td>\r\n          <td>Bob The Builder</td>\r\n          <td>Charlie And The Chocolate Factory</td>\r\n          <td>12,000 GBP</td>\r\n          <td><button mat-raised-button color=\"accent\" disabled>Settle</button></td>\r\n        </tr>\r\n        <tr>\r\n          <td>e267a4b9-76e3-488b-bc79-b52ee93caae7</td>\r\n          <td>C3AB8FF13720E8AD9047DD39466B3C8974E592C2FA383D4A3960714CAEF0C4F2</td>\r\n          <td>TRADE-ASSET-000-001</td>\r\n          <td>Alice In Wonderland</td>\r\n          <td>Bob The Builder</td>\r\n          <td>Charlie And The Chocolate Factory</td>\r\n          <td>12,000 GBP</td>\r\n          <td><button mat-raised-button color=\"accent\">Settle</button></td>\r\n        </tr>\r\n        <tr>\r\n          <td>e267a4b9-76e3-488b-bc79-b52ee93caae7</td>\r\n          <td>C3AB8FF13720E8AD9047DD39466B3C8974E592C2FA383D4A3960714CAEF0C4F2</td>\r\n          <td>TRADE-ASSET-000-001</td>\r\n          <td>Alice In Wonderland</td>\r\n          <td>Bob The Builder</td>\r\n          <td>Charlie And The Chocolate Factory</td>\r\n          <td>12,000 GBP</td>\r\n          <td><button mat-raised-button color=\"accent\">Settle</button></td>\r\n        </tr>\r\n        <tr>\r\n          <td>e267a4b9-76e3-488b-bc79-b52ee93caae7</td>\r\n          <td>C3AB8FF13720E8AD9047DD39466B3C8974E592C2FA383D4A3960714CAEF0C4F2</td>\r\n          <td>TRADE-ASSET-000-001</td>\r\n          <td>Alice In Wonderland</td>\r\n          <td>Bob The Builder</td>\r\n          <td>Charlie And The Chocolate Factory</td>\r\n          <td>12,000 GBP</td>\r\n          <td><button mat-raised-button color=\"accent\">Settle</button></td>\r\n        </tr>\r\n      </tbody>\r\n    </table>\r\n  </div>\r\n</div>"

/***/ }),

/***/ "../../../../../src/app/funder-dashboard/funder-dashboard.component.scss":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/funder-dashboard/funder-dashboard.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return FunderDashboardComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};

var FunderDashboardComponent = /** @class */ (function () {
    function FunderDashboardComponent() {
    }
    FunderDashboardComponent.prototype.ngOnInit = function () {
    };
    FunderDashboardComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["o" /* Component */])({
            selector: 'app-funder-dashboard',
            template: __webpack_require__("../../../../../src/app/funder-dashboard/funder-dashboard.component.html"),
            styles: [__webpack_require__("../../../../../src/app/funder-dashboard/funder-dashboard.component.scss")]
        }),
        __metadata("design:paramtypes", [])
    ], FunderDashboardComponent);
    return FunderDashboardComponent;
}());

//# sourceMappingURL=funder-dashboard.component.js.map

/***/ }),

/***/ "../../../../../src/app/main/main.component.html":
/***/ (function(module, exports) {

module.exports = ""

/***/ }),

/***/ "../../../../../src/app/main/main.component.scss":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/main/main.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return MainComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};

var MainComponent = /** @class */ (function () {
    function MainComponent() {
    }
    MainComponent.prototype.ngOnInit = function () {
    };
    MainComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["o" /* Component */])({
            selector: 'app-main',
            template: __webpack_require__("../../../../../src/app/main/main.component.html"),
            styles: [__webpack_require__("../../../../../src/app/main/main.component.scss")]
        }),
        __metadata("design:paramtypes", [])
    ], MainComponent);
    return MainComponent;
}());

//# sourceMappingURL=main.component.js.map

/***/ }),

/***/ "../../../../../src/app/root/root.component.html":
/***/ (function(module, exports) {

module.exports = "<div fxFill fxLayout=\"column\">\r\n  <mat-toolbar>\r\n    <button mat-icon-button (click)=\"sidenav.toggle()\">\r\n      <mat-icon *ngIf=\"!sidenav.opened\">menu</mat-icon>\r\n      <mat-icon *ngIf=\"sidenav.opened\">close</mat-icon>\r\n    </button>\r\n    <img class=\"logo\" src=\"../../assets/images/MarcoPolo.svg\" height=\"20\" />\r\n    <div fxFlex></div>\r\n    <button mat-button [matMenuTriggerFor]=\"menu\">Hello, {{localNode?.organizationName}}</button>\r\n    <mat-menu #menu=\"matMenu\">\r\n      <mat-list>\r\n        <mat-list-item>\r\n          <mat-icon class=\"space-right\">face</mat-icon>\r\n          <span>{{localNode?.organizationName}}</span>\r\n        </mat-list-item>\r\n        <mat-list-item>\r\n          <mat-icon class=\"space-right\">location_on</mat-icon>\r\n          <span>{{localNode?.localityName}}</span>\r\n        </mat-list-item>\r\n        <mat-list-item>\r\n          <mat-icon class=\"space-right\">language</mat-icon>\r\n          <span>{{localNode?.country}}</span>\r\n        </mat-list-item>\r\n        <mat-list-item>\r\n          <button fxFlex mat-raised-button color=\"accent\">Sign Out</button>\r\n        </mat-list-item>\r\n      </mat-list>\r\n    </mat-menu>\r\n  </mat-toolbar>\r\n  <mat-drawer-container fxFlex>\r\n    <mat-drawer fxLayout=\"column\" mode=\"over\" #sidenav>\r\n      <a mat-button routerLink=\"./conductor\">\r\n        <mat-icon>dashboard</mat-icon> Conductor Dashboard</a>\r\n      <a mat-button routerLink=\"./buyer\">\r\n        <mat-icon>dashboard</mat-icon> Buyer Dashboard</a>\r\n      <a mat-button routerLink=\"./supplier\">\r\n        <mat-icon>dashboard</mat-icon> Supplier Dashboard</a>\r\n      <a mat-button routerLink=\"./funder\">\r\n        <mat-icon>dashboard</mat-icon> Funder Dashboard</a>\r\n    </mat-drawer>\r\n    <router-outlet></router-outlet>\r\n  </mat-drawer-container>\r\n</div>"

/***/ }),

/***/ "../../../../../src/app/root/root.component.scss":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "mat-drawer {\n  padding: 20px; }\n\na.mat-button {\n  display: block;\n  margin-bottom: 10px;\n  text-align: left;\n  min-width: 240px; }\n\n.space-right {\n  display: inline-block;\n  margin-right: 20px; }\n\n.logo {\n  margin-left: 16px; }\n", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/root/root.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return RootComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_router__ = __webpack_require__("../../../router/@angular/router.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__api_domain_services_node_service__ = __webpack_require__("../../../../../src/api/domain/services/node.service.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};



var RootComponent = /** @class */ (function () {
    function RootComponent(nodeService, router) {
        this.nodeService = nodeService;
        this.router = router;
    }
    RootComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.nodeService
            .getLocalNode()
            .subscribe(function (node) { return _this.localNode = node; });
    };
    RootComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["o" /* Component */])({
            selector: 'app-root',
            template: __webpack_require__("../../../../../src/app/root/root.component.html"),
            styles: [__webpack_require__("../../../../../src/app/root/root.component.scss")]
        }),
        __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_2__api_domain_services_node_service__["a" /* NodeService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_2__api_domain_services_node_service__["a" /* NodeService */]) === "function" && _a || Object, typeof (_b = typeof __WEBPACK_IMPORTED_MODULE_1__angular_router__["a" /* Router */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1__angular_router__["a" /* Router */]) === "function" && _b || Object])
    ], RootComponent);
    return RootComponent;
    var _a, _b;
}());

//# sourceMappingURL=root.component.js.map

/***/ }),

/***/ "../../../../../src/app/supplier-dashboard/change-owner-dialog/change-owner-dialog.component.html":
/***/ (function(module, exports) {

module.exports = "<h2>Change Asset Owner</h2>\r\n<div *ngIf=\"view === 0\">\r\n  <div fxLayout=\"column\">\r\n    <div fxLayout=\"row\">\r\n      <mat-form-field fxFlex=\"100\">\r\n        <mat-select [(ngModel)]=\"model.newOwner\" placeholder=\"New Owner\">\r\n          <mat-option *ngFor=\"let node of nodes\" [value]=\"node\">{{node.organizationName}}</mat-option>\r\n        </mat-select>\r\n      </mat-form-field>\r\n    </div>\r\n  </div>\r\n  <div fxLayout=\"row\">\r\n    <button mat-raised-button fxFlex color=\"accent\" (click)=\"okay()\">OK</button>\r\n    <div fxFlex=\"5\"></div>\r\n    <button mat-raised-button fxFlex color=\"warn\" (click)=\"cancel()\">Cancel</button>\r\n  </div>\r\n</div>\r\n<div *ngIf=\"view === 1\">\r\n  <div fxLayout=\"column\" fxLayoutAlign=\"space-around center\" class=\"app-padding\">\r\n    <mat-spinner color=\"accent\"></mat-spinner>\r\n    <p>Conducting transaction, please wait...</p>\r\n  </div>\r\n</div>"

/***/ }),

/***/ "../../../../../src/app/supplier-dashboard/change-owner-dialog/change-owner-dialog.component.scss":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/supplier-dashboard/change-owner-dialog/change-owner-dialog.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return ChangeOwnerDialogComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_material__ = __webpack_require__("../../../material/esm5/material.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__api_domain_currency__ = __webpack_require__("../../../../../src/api/domain/currency.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__api_domain_models_change_owner__ = __webpack_require__("../../../../../src/api/domain/models/change-owner.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__api_domain_services_node_service__ = __webpack_require__("../../../../../src/api/domain/services/node.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__api_domain_services_trade_asset_service__ = __webpack_require__("../../../../../src/api/domain/services/trade-asset.service.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
var __param = (this && this.__param) || function (paramIndex, decorator) {
    return function (target, key) { decorator(target, key, paramIndex); }
};






var ChangeOwnerDialogComponent = /** @class */ (function () {
    function ChangeOwnerDialogComponent(nodeService, tradeAssetService, dialogRef, data) {
        var _this = this;
        this.nodeService = nodeService;
        this.tradeAssetService = tradeAssetService;
        this.dialogRef = dialogRef;
        this.data = data;
        this.currencies = __WEBPACK_IMPORTED_MODULE_2__api_domain_currency__["a" /* Currency */].getKnownCurrencies();
        this.model = new __WEBPACK_IMPORTED_MODULE_3__api_domain_models_change_owner__["a" /* ChangeOwnerViewModel */]();
        this.view = 0;
        this.nodes = [];
        this.nodeService
            .getNodes()
            .subscribe(function (nodes) { return _this.nodes = nodes; });
        this.model = new __WEBPACK_IMPORTED_MODULE_3__api_domain_models_change_owner__["a" /* ChangeOwnerViewModel */](data.linearId);
    }
    ChangeOwnerDialogComponent.prototype.ngOnInit = function () {
    };
    ChangeOwnerDialogComponent.prototype.okay = function () {
        var _this = this;
        this.view = 1;
        this.tradeAssetService
            .changeOwner(this.model)
            .subscribe(function (response) {
            _this.cancel();
        });
    };
    ChangeOwnerDialogComponent.prototype.cancel = function () {
        this.dialogRef.close();
    };
    ChangeOwnerDialogComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["o" /* Component */])({
            selector: 'app-change-owner-dialog',
            template: __webpack_require__("../../../../../src/app/supplier-dashboard/change-owner-dialog/change-owner-dialog.component.html"),
            styles: [__webpack_require__("../../../../../src/app/supplier-dashboard/change-owner-dialog/change-owner-dialog.component.scss")]
        }),
        __param(3, Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["B" /* Inject */])(__WEBPACK_IMPORTED_MODULE_1__angular_material__["a" /* MAT_DIALOG_DATA */])),
        __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_4__api_domain_services_node_service__["a" /* NodeService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_4__api_domain_services_node_service__["a" /* NodeService */]) === "function" && _a || Object, typeof (_b = typeof __WEBPACK_IMPORTED_MODULE_5__api_domain_services_trade_asset_service__["a" /* TradeAssetService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_5__api_domain_services_trade_asset_service__["a" /* TradeAssetService */]) === "function" && _b || Object, typeof (_c = typeof __WEBPACK_IMPORTED_MODULE_1__angular_material__["g" /* MatDialogRef */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1__angular_material__["g" /* MatDialogRef */]) === "function" && _c || Object, Object])
    ], ChangeOwnerDialogComponent);
    return ChangeOwnerDialogComponent;
    var _a, _b, _c;
}());

//# sourceMappingURL=change-owner-dialog.component.js.map

/***/ }),

/***/ "../../../../../src/app/supplier-dashboard/supplier-dashboard.component.html":
/***/ (function(module, exports) {

module.exports = "<div fxLayout=\"column\">\r\n  <div class=\"app-padding\" fxFlex>\r\n    <div class=\"fxFlex\" fxLayout=\"row\">\r\n      <div fxFlex>\r\n        <h1>Supplier Dashboard</h1>\r\n      </div>\r\n    </div>\r\n  </div>\r\n  <div class=\"app-padding\" fxFlex>\r\n    <table>\r\n      <thead>\r\n        <tr>\r\n          <th>Linear ID</th>\r\n          <th>Transaction ID</th>\r\n          <th>Asset ID</th>\r\n          <th>Buyer</th>\r\n          <th>Supplier</th>\r\n          <th>Owner</th>\r\n          <th>Status</th>\r\n          <th class=\"align-right\">Value</th>\r\n          <th class=\"align-right\"></th>\r\n        </tr>\r\n      </thead>\r\n      <tbody *ngIf=\"states.length > 0\">\r\n        <ng-container *ngFor=\"let state of states\">\r\n          <tr>\r\n            <td>{{state.state.data.linearId.id}}</td>\r\n            <td>{{state.ref.txhash}}</td>\r\n            <td>{{state.state.data.tradeAsset.assetId}}</td>\r\n            <td>{{getName(state.state.data.buyer)}}</td>\r\n            <td>{{getName(state.state.data.supplier)}}</td>\r\n            <td>{{getName(state.state.data.owner)}}</td>\r\n            <td>\r\n              <mat-chip-list>\r\n                <mat-chip selected=\"true\" color=\"accent\">{{state.state.data.tradeAsset.status}}</mat-chip>\r\n              </mat-chip-list>\r\n            </td>\r\n            <td class=\"align-right\">{{state.state.data.tradeAsset.amount}}</td>\r\n            <td class=\"align-right\">\r\n              <button mat-raised-button color=\"accent\" (click)=\"openDialog(state.state.data.linearId.id)\">Change Owner</button>\r\n            </td>\r\n          </tr>\r\n        </ng-container>\r\n      </tbody>\r\n    </table>\r\n  </div>\r\n</div>"

/***/ }),

/***/ "../../../../../src/app/supplier-dashboard/supplier-dashboard.component.scss":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/supplier-dashboard/supplier-dashboard.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return SupplierDashboardComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_material__ = __webpack_require__("../../../material/esm5/material.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__change_owner_dialog_change_owner_dialog_component__ = __webpack_require__("../../../../../src/app/supplier-dashboard/change-owner-dialog/change-owner-dialog.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__api_domain_services_trade_asset_service__ = __webpack_require__("../../../../../src/api/domain/services/trade-asset.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__api_domain_x_500_name__ = __webpack_require__("../../../../../src/api/domain/x-500-name.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};





var SupplierDashboardComponent = /** @class */ (function () {
    function SupplierDashboardComponent(dialog, tradeAssetService) {
        this.dialog = dialog;
        this.tradeAssetService = tradeAssetService;
        this.states = [];
        this.invalidate = false;
    }
    SupplierDashboardComponent.prototype.getStates = function () {
        var _this = this;
        this.tradeAssetService
            .getTradeAssets()
            .subscribe(function (response) {
            if (_this.invalidate || response.length !== _this.states.length) {
                _this.states = response;
                _this.invalidate = false;
            }
        });
    };
    SupplierDashboardComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.getStates();
        setInterval(function () { return _this.getStates(); }, 2000);
    };
    SupplierDashboardComponent.prototype.openDialog = function (linearId) {
        var _this = this;
        this.dialog.open(__WEBPACK_IMPORTED_MODULE_2__change_owner_dialog_change_owner_dialog_component__["a" /* ChangeOwnerDialogComponent */], {
            width: '640px',
            disableClose: true,
            data: { linearId: linearId }
        }).afterClosed().subscribe(function () {
            _this.invalidate = true;
        });
    };
    SupplierDashboardComponent.prototype.getName = function (value) {
        return !!value ? __WEBPACK_IMPORTED_MODULE_4__api_domain_x_500_name__["a" /* X500Name */].parse(value).organizationName : '';
    };
    SupplierDashboardComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["o" /* Component */])({
            selector: 'app-supplier-dashboard',
            template: __webpack_require__("../../../../../src/app/supplier-dashboard/supplier-dashboard.component.html"),
            styles: [__webpack_require__("../../../../../src/app/supplier-dashboard/supplier-dashboard.component.scss")]
        }),
        __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_1__angular_material__["e" /* MatDialog */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1__angular_material__["e" /* MatDialog */]) === "function" && _a || Object, typeof (_b = typeof __WEBPACK_IMPORTED_MODULE_3__api_domain_services_trade_asset_service__["a" /* TradeAssetService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_3__api_domain_services_trade_asset_service__["a" /* TradeAssetService */]) === "function" && _b || Object])
    ], SupplierDashboardComponent);
    return SupplierDashboardComponent;
    var _a, _b;
}());

//# sourceMappingURL=supplier-dashboard.component.js.map

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