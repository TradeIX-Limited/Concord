webpackJsonp(["main"],{

/***/ "./src/$$_lazy_route_resource lazy recursive":
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
webpackEmptyAsyncContext.id = "./src/$$_lazy_route_resource lazy recursive";

/***/ }),

/***/ "./src/api/domain/base.service.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return BaseService; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_http__ = __webpack_require__("./node_modules/@angular/http/esm5/http.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__app_config_config__ = __webpack_require__("./src/app/config/config.ts");
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
        var _this = this;
        this.http = http;
        this.config = null;
        this.useOrigin = true;
        this.apiUrl = null;
        this.config = new __WEBPACK_IMPORTED_MODULE_2__app_config_config__["a" /* Config */](http);
        this.config.load()
            .then(function (o) {
            _this.useOrigin = o.useOrigin;
            _this.apiUrl = o.apiUrl;
        });
    }
    BaseService.prototype.createUrl = function (endpoint) {
        return this.useOrigin
            ? window.location.origin + "/api/" + endpoint
            : this.apiUrl + "/" + endpoint;
    };
    BaseService = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["z" /* Injectable */])(),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1__angular_http__["a" /* Http */]])
    ], BaseService);
    return BaseService;
}());



/***/ }),

/***/ "./src/api/domain/history/history.service.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return HistoryService; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_api_domain_purchase_orders_purchase_order_service__ = __webpack_require__("./src/api/domain/purchase-orders/purchase-order.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_api_domain_invoices_invoices_service__ = __webpack_require__("./src/api/domain/invoices/invoices.service.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};



var HistoryService = /** @class */ (function () {
    function HistoryService(purchaseOrderService, invoiceService) {
        this.purchaseOrderService = purchaseOrderService;
        this.invoiceService = invoiceService;
        this.drawerContainer = null;
        this.purchaseOrderHistoryComponent = null;
        this.invoiceHistoryComponent = null;
    }
    HistoryService.prototype.setDrawerContainer = function (drawerContainer) {
        this.drawerContainer = drawerContainer;
    };
    HistoryService.prototype.setInvoiceHistoryComponent = function (invoiceHistoryComponent) {
        this.invoiceHistoryComponent = invoiceHistoryComponent;
    };
    HistoryService.prototype.setPurchaseOrderHistoryComponent = function (purchaseOrderHistoryComponent) {
        this.purchaseOrderHistoryComponent = purchaseOrderHistoryComponent;
    };
    HistoryService.prototype.openDrawer = function (externalId, mode) {
        var _this = this;
        this.reset();
        this.drawerContainer.open();
        switch (mode) {
            case "PURCHASE_ORDER":
                this.purchaseOrderService
                    .getPurchaseOrderState(externalId)
                    .subscribe(function (response) {
                    _this.purchaseOrderHistoryComponent.setItems(response);
                });
                break;
            case "INVOICE":
                this.invoiceService
                    .getInvoiceState(externalId)
                    .subscribe(function (response) {
                    _this.invoiceHistoryComponent.setItems(response);
                });
                break;
        }
    };
    HistoryService.prototype.reset = function () {
        this.invoiceHistoryComponent.setItems([]);
        this.purchaseOrderHistoryComponent.setItems([]);
    };
    HistoryService = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["z" /* Injectable */])(),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1_api_domain_purchase_orders_purchase_order_service__["a" /* PurchaseOrderService */],
            __WEBPACK_IMPORTED_MODULE_2_api_domain_invoices_invoices_service__["a" /* InvoicesService */]])
    ], HistoryService);
    return HistoryService;
}());



/***/ }),

/***/ "./src/api/domain/invoices/invoice-state.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return InvoiceState; });
var InvoiceState = /** @class */ (function () {
    function InvoiceState(linearId, owner, buyer, supplier, conductor, invoiceVersion, invoiceVersionDate, tixInvoiceVersion, invoiceNumber, invoiceType, reference, dueDate, offerId, amount, totalOutstanding, created, updated, expectedSettlementDate, settlementDate, mandatoryReconciliationDate, invoiceDate, status, rejectionReason, eligibleValue, invoicePurchaseValue, tradeDate, tradePaymentDate, invoicePayments, invoiceDilutions, cancelled, closeDate, originationNetwork, hash, currency, siteId, purchaseOrderNumber, purchaseOrderId, composerProgramId) {
        this.linearId = linearId;
        this.owner = owner;
        this.buyer = buyer;
        this.supplier = supplier;
        this.conductor = conductor;
        this.invoiceVersion = invoiceVersion;
        this.invoiceVersionDate = invoiceVersionDate;
        this.tixInvoiceVersion = tixInvoiceVersion;
        this.invoiceNumber = invoiceNumber;
        this.invoiceType = invoiceType;
        this.reference = reference;
        this.dueDate = dueDate;
        this.offerId = offerId;
        this.amount = amount;
        this.totalOutstanding = totalOutstanding;
        this.created = created;
        this.updated = updated;
        this.expectedSettlementDate = expectedSettlementDate;
        this.settlementDate = settlementDate;
        this.mandatoryReconciliationDate = mandatoryReconciliationDate;
        this.invoiceDate = invoiceDate;
        this.status = status;
        this.rejectionReason = rejectionReason;
        this.eligibleValue = eligibleValue;
        this.invoicePurchaseValue = invoicePurchaseValue;
        this.tradeDate = tradeDate;
        this.tradePaymentDate = tradePaymentDate;
        this.invoicePayments = invoicePayments;
        this.invoiceDilutions = invoiceDilutions;
        this.cancelled = cancelled;
        this.closeDate = closeDate;
        this.originationNetwork = originationNetwork;
        this.hash = hash;
        this.currency = currency;
        this.siteId = siteId;
        this.purchaseOrderNumber = purchaseOrderNumber;
        this.purchaseOrderId = purchaseOrderId;
        this.composerProgramId = composerProgramId;
    }
    return InvoiceState;
}());



/***/ }),

/***/ "./src/api/domain/invoices/invoices.service.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return InvoicesService; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_api_domain_base_service__ = __webpack_require__("./src/api/domain/base.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_api_domain_shared_mapper__ = __webpack_require__("./src/api/domain/shared/mapper.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_api_domain_invoices_invoice_state__ = __webpack_require__("./src/api/domain/invoices/invoice-state.ts");
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




var InvoicesService = /** @class */ (function (_super) {
    __extends(InvoicesService, _super);
    function InvoicesService() {
        return _super !== null && _super.apply(this, arguments) || this;
    }
    InvoicesService.prototype.getInvoiceState = function (externalId) {
        return this.http
            .get(this.createUrl("invoices?externalId=" + externalId))
            .map(function (response) { return response.json()
            .map(function (stateAndRef) { return __WEBPACK_IMPORTED_MODULE_2_api_domain_shared_mapper__["a" /* Mapper */].getInstance()
            .map(Object, __WEBPACK_IMPORTED_MODULE_3_api_domain_invoices_invoice_state__["a" /* InvoiceState */], stateAndRef.state.data); }); });
    };
    InvoicesService.prototype.getInvoiceCount = function () {
        return this.http
            .get(this.createUrl("invoices/count"))
            .map(function (response) { return Number(response.json().count); });
    };
    InvoicesService.prototype.getMostRecentInvoiceHash = function () {
        return this.http
            .get(this.createUrl("invoices/hash"))
            .map(function (response) { return response.json().hash; });
    };
    InvoicesService.prototype.getAllInvoices = function (page, count) {
        if (page === void 0) { page = 1; }
        if (count === void 0) { count = 50; }
        return this.http
            .get(this.createUrl("invoices/all?page=" + page + "&count=" + count))
            .map(function (response) { return response.json()
            .map(function (stateAndRef) { return __WEBPACK_IMPORTED_MODULE_2_api_domain_shared_mapper__["a" /* Mapper */].getInstance()
            .map(Object, __WEBPACK_IMPORTED_MODULE_3_api_domain_invoices_invoice_state__["a" /* InvoiceState */], stateAndRef.state.data); }); });
    };
    InvoicesService = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["z" /* Injectable */])()
    ], InvoicesService);
    return InvoicesService;
}(__WEBPACK_IMPORTED_MODULE_1_api_domain_base_service__["a" /* BaseService */]));



/***/ }),

/***/ "./src/api/domain/nodes/node.service.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return NodeService; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__base_service__ = __webpack_require__("./src/api/domain/base.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__shared_corda__ = __webpack_require__("./src/api/domain/shared/corda.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_rxjs_add_operator_map__ = __webpack_require__("./node_modules/rxjs/_esm5/add/operator/map.js");
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
            .get(this.createUrl("nodes/local"))
            .map(function (response) { return __WEBPACK_IMPORTED_MODULE_2__shared_corda__["a" /* CordaX500Name */].parse(response.json().localNode); });
    };
    NodeService.prototype.getAllNodes = function () {
        return this.http
            .get(this.createUrl("nodes/all"))
            .map(function (response) { return response.json().nodes
            .map(function (name) { return __WEBPACK_IMPORTED_MODULE_2__shared_corda__["a" /* CordaX500Name */].parse(name); }); });
    };
    NodeService.prototype.getPeerNodes = function () {
        return this.http
            .get(this.createUrl("nodes/peers"))
            .map(function (response) { return response.json().nodes
            .map(function (name) { return __WEBPACK_IMPORTED_MODULE_2__shared_corda__["a" /* CordaX500Name */].parse(name); }); });
    };
    NodeService = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["z" /* Injectable */])()
    ], NodeService);
    return NodeService;
}(__WEBPACK_IMPORTED_MODULE_1__base_service__["a" /* BaseService */]));



/***/ }),

/***/ "./src/api/domain/purchase-orders/purchase-order-state.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return PurchaseOrderState; });
var PurchaseOrderState = /** @class */ (function () {
    function PurchaseOrderState(linearId, owner, buyer, supplier, conductor, reference, amount, created, earliestShipment, latestShipment, portOfShipment, descriptionOfGoods, deliveryTerms) {
        this.linearId = linearId;
        this.owner = owner;
        this.buyer = buyer;
        this.supplier = supplier;
        this.conductor = conductor;
        this.reference = reference;
        this.amount = amount;
        this.created = created;
        this.earliestShipment = earliestShipment;
        this.latestShipment = latestShipment;
        this.portOfShipment = portOfShipment;
        this.descriptionOfGoods = descriptionOfGoods;
        this.deliveryTerms = deliveryTerms;
    }
    return PurchaseOrderState;
}());



/***/ }),

/***/ "./src/api/domain/purchase-orders/purchase-order.service.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return PurchaseOrderService; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_api_domain_base_service__ = __webpack_require__("./src/api/domain/base.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_api_domain_shared_mapper__ = __webpack_require__("./src/api/domain/shared/mapper.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_api_domain_purchase_orders_purchase_order_state__ = __webpack_require__("./src/api/domain/purchase-orders/purchase-order-state.ts");
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
    PurchaseOrderService.prototype.getPurchaseOrderState = function (externalId) {
        return this.http
            .get(this.createUrl("purchaseorders?externalId=" + externalId))
            .map(function (response) { return response.json()
            .map(function (stateAndRef) { return __WEBPACK_IMPORTED_MODULE_2_api_domain_shared_mapper__["a" /* Mapper */].getInstance()
            .map(Object, __WEBPACK_IMPORTED_MODULE_3_api_domain_purchase_orders_purchase_order_state__["a" /* PurchaseOrderState */], stateAndRef.state.data); }); });
    };
    PurchaseOrderService.prototype.getPurchaseOrderCount = function () {
        return this.http
            .get(this.createUrl("purchaseorders/count"))
            .map(function (response) { return Number(response.json().count); });
    };
    PurchaseOrderService.prototype.getMostRecentPurchaseOrderHash = function () {
        return this.http
            .get(this.createUrl("purchaseorders/hash"))
            .map(function (response) { return response.json().hash; });
    };
    PurchaseOrderService.prototype.getAllPurchaseOrders = function (page, count) {
        if (page === void 0) { page = 1; }
        if (count === void 0) { count = 50; }
        return this.http
            .get(this.createUrl("purchaseorders/all?page=" + page + "&count=" + count))
            .map(function (response) { return response.json()
            .map(function (stateAndRef) { return __WEBPACK_IMPORTED_MODULE_2_api_domain_shared_mapper__["a" /* Mapper */].getInstance()
            .map(Object, __WEBPACK_IMPORTED_MODULE_3_api_domain_purchase_orders_purchase_order_state__["a" /* PurchaseOrderState */], stateAndRef.state.data); }); });
    };
    PurchaseOrderService = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["z" /* Injectable */])()
    ], PurchaseOrderService);
    return PurchaseOrderService;
}(__WEBPACK_IMPORTED_MODULE_1_api_domain_base_service__["a" /* BaseService */]));



/***/ }),

/***/ "./src/api/domain/shared/corda.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* unused harmony export StateRef */
/* unused harmony export State */
/* unused harmony export StateAndRef */
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "b", function() { return UniqueIdentifier; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return CordaX500Name; });
var StateRef = /** @class */ (function () {
    function StateRef(txhash, index) {
        if (txhash === void 0) { txhash = null; }
        if (index === void 0) { index = 0; }
        this.txhash = txhash;
        this.index = index;
    }
    return StateRef;
}());

var State = /** @class */ (function () {
    function State(data) {
        if (data === void 0) { data = null; }
        this.data = data;
    }
    return State;
}());

var StateAndRef = /** @class */ (function () {
    function StateAndRef(state, ref) {
        if (state === void 0) { state = new State(); }
        if (ref === void 0) { ref = new StateRef(); }
        this.state = state;
        this.ref = ref;
    }
    return StateAndRef;
}());

var UniqueIdentifier = /** @class */ (function () {
    function UniqueIdentifier(externalId, id) {
        this.externalId = externalId;
        this.id = id;
    }
    return UniqueIdentifier;
}());

var CordaX500Name = /** @class */ (function () {
    function CordaX500Name(commonName, organizationUnit, organizationName, localityName, stateName, country) {
        this.commonName = commonName;
        this.organizationUnit = organizationUnit;
        this.organizationName = organizationName;
        this.localityName = localityName;
        this.stateName = stateName;
        this.country = country;
        Object.freeze(this);
    }
    CordaX500Name.parse = function (value) {
        var dict = {};
        value.split(",").forEach(function (part) {
            var pair = part.split("=");
            dict[pair[0]] = pair[1];
        });
        return new CordaX500Name(dict["CN"], dict["OU"], dict["O"], dict["L"], dict["S"], dict["C"]);
    };
    CordaX500Name.prototype.toString = function () {
        var dict = {
            "CN": this.commonName,
            "OU": this.organizationUnit,
            "O": this.organizationName,
            "L": this.localityName,
            "S": this.stateName,
            "C": this.country
        };
        return Object
            .keys(dict)
            .filter(function (key) { return dict[key] !== undefined && dict[key] !== null; })
            .map(function (key) { return key + "=" + dict[key]; })
            .join(",");
    };
    return CordaX500Name;
}());



/***/ }),

/***/ "./src/api/domain/shared/mapper.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return Mapper; });
var Mapper = /** @class */ (function () {
    function Mapper() {
        this.mapConfigurations = [];
    }
    Mapper.getInstance = function () {
        return Mapper.instance || (Mapper.instance = new Mapper());
    };
    Mapper.prototype.createMapConfiguration = function (from, to, mapFunction) {
        if (this.mapConfigurations.some(function (mc) { return mc.from === from && mc.to === to; })) {
            throw new TypeError("Type is already mapped");
        }
        this.mapConfigurations.push(new MapConfiguration(from, to, mapFunction));
    };
    Mapper.prototype.map = function (from, to, input) {
        var mapConfiguration = this
            .mapConfigurations.find(function (mc) { return mc.from === from && mc.to === to; });
        if (!mapConfiguration) {
            throw new TypeError("No mapping configuration found");
        }
        return mapConfiguration.mapFunction(input);
    };
    return Mapper;
}());

var MapConfiguration = /** @class */ (function () {
    function MapConfiguration(from, to, mapFunction) {
        this.from = from;
        this.to = to;
        this.mapFunction = mapFunction;
    }
    return MapConfiguration;
}());


/***/ }),

/***/ "./src/app/app.module.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return AppModule; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_hammerjs__ = __webpack_require__("./node_modules/hammerjs/hammer.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_hammerjs___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_hammerjs__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_platform_browser__ = __webpack_require__("./node_modules/@angular/platform-browser/esm5/platform-browser.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__angular_forms__ = __webpack_require__("./node_modules/@angular/forms/esm5/forms.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__angular_http__ = __webpack_require__("./node_modules/@angular/http/esm5/http.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__angular_flex_layout__ = __webpack_require__("./node_modules/@angular/flex-layout/esm5/flex-layout.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__angular_platform_browser_animations__ = __webpack_require__("./node_modules/@angular/platform-browser/esm5/animations.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7__angular_material__ = __webpack_require__("./node_modules/@angular/material/esm5/material.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8__root_root_component__ = __webpack_require__("./src/app/root/root.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_9__main_main_component__ = __webpack_require__("./src/app/main/main.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_10__api_domain_nodes_node_service__ = __webpack_require__("./src/api/domain/nodes/node.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_11_api_domain_history_history_service__ = __webpack_require__("./src/api/domain/history/history.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_12_api_domain_shared_mapper__ = __webpack_require__("./src/api/domain/shared/mapper.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_13_api_domain_purchase_orders_purchase_order_state__ = __webpack_require__("./src/api/domain/purchase-orders/purchase-order-state.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_14_api_domain_shared_corda__ = __webpack_require__("./src/api/domain/shared/corda.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_15_api_domain_purchase_orders_purchase_order_service__ = __webpack_require__("./src/api/domain/purchase-orders/purchase-order.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_16_api_domain_invoices_invoices_service__ = __webpack_require__("./src/api/domain/invoices/invoices.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_17__delivery_information_delivery_information_component__ = __webpack_require__("./src/app/delivery-information/delivery-information.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_18__purchase_order_table_purchase_order_table_component__ = __webpack_require__("./src/app/purchase-order-table/purchase-order-table.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_19__invoice_table_invoice_table_component__ = __webpack_require__("./src/app/invoice-table/invoice-table.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_20_api_domain_invoices_invoice_state__ = __webpack_require__("./src/api/domain/invoices/invoice-state.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_21__invoice_information_invoice_information_component__ = __webpack_require__("./src/app/invoice-information/invoice-information.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_22__purchase_order_history_purchase_order_history_component__ = __webpack_require__("./src/app/purchase-order-history/purchase-order-history.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_23__invoice_history_invoice_history_component__ = __webpack_require__("./src/app/invoice-history/invoice-history.component.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
























var AppModule = /** @class */ (function () {
    function AppModule() {
        this.initialize();
    }
    AppModule.prototype.initialize = function () {
        var mapper = __WEBPACK_IMPORTED_MODULE_12_api_domain_shared_mapper__["a" /* Mapper */].getInstance();
        mapper.createMapConfiguration(Object, __WEBPACK_IMPORTED_MODULE_13_api_domain_purchase_orders_purchase_order_state__["a" /* PurchaseOrderState */], function (input) {
            return new __WEBPACK_IMPORTED_MODULE_13_api_domain_purchase_orders_purchase_order_state__["a" /* PurchaseOrderState */](new __WEBPACK_IMPORTED_MODULE_14_api_domain_shared_corda__["b" /* UniqueIdentifier */](input.linearId.externalId, input.linearId.id), __WEBPACK_IMPORTED_MODULE_14_api_domain_shared_corda__["a" /* CordaX500Name */].parse(input.owner), __WEBPACK_IMPORTED_MODULE_14_api_domain_shared_corda__["a" /* CordaX500Name */].parse(input.buyer), __WEBPACK_IMPORTED_MODULE_14_api_domain_shared_corda__["a" /* CordaX500Name */].parse(input.supplier), __WEBPACK_IMPORTED_MODULE_14_api_domain_shared_corda__["a" /* CordaX500Name */].parse(input.conductor), input.reference, input.amount, new Date(input.created * 1000), new Date(input.earliestShipment * 1000), new Date(input.latestShipment * 1000), input.portOfShipment, input.descriptionOfGoods, input.deliveryTerms);
        });
        mapper.createMapConfiguration(Object, __WEBPACK_IMPORTED_MODULE_20_api_domain_invoices_invoice_state__["a" /* InvoiceState */], function (input) {
            return new __WEBPACK_IMPORTED_MODULE_20_api_domain_invoices_invoice_state__["a" /* InvoiceState */](new __WEBPACK_IMPORTED_MODULE_14_api_domain_shared_corda__["b" /* UniqueIdentifier */](input.linearId.externalId, input.linearId.id), __WEBPACK_IMPORTED_MODULE_14_api_domain_shared_corda__["a" /* CordaX500Name */].parse(input.owner), __WEBPACK_IMPORTED_MODULE_14_api_domain_shared_corda__["a" /* CordaX500Name */].parse(input.buyer), __WEBPACK_IMPORTED_MODULE_14_api_domain_shared_corda__["a" /* CordaX500Name */].parse(input.supplier), __WEBPACK_IMPORTED_MODULE_14_api_domain_shared_corda__["a" /* CordaX500Name */].parse(input.conductor), input.invoiceVersion, input.invoiceVersionDate, input.tixInvoiceVersion, input.invoiceNumber, input.invoiceType, input.reference, new Date(input.dueDate * 1000), input.offerId, input.amount, input.totalOutstanding, new Date(input.created * 1000), new Date(input.updated * 1000), new Date(input.expectedSettlementDate * 1000), new Date(input.settlementDate * 1000), new Date(input.mandatoryReconciliationDate * 1000), new Date(input.invoiceDate * 1000), input.status, input.rejectionReason, input.eligibleValue, input.invoicePurchaseValue, new Date(input.tradeDate * 1000), new Date(input.tradePaymentDate * 1000), input.invoicePayments, input.invoiceDilutions, input.cancelled, new Date(input.closeDate * 1000), input.originationNetwork, input.hash, input.currency, input.siteId, input.purchaseOrderNumber, input.purchaseOrderId, input.composerProgramId);
        });
    };
    AppModule = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_2__angular_core__["H" /* NgModule */])({
            declarations: [
                __WEBPACK_IMPORTED_MODULE_8__root_root_component__["a" /* RootComponent */],
                __WEBPACK_IMPORTED_MODULE_9__main_main_component__["a" /* MainComponent */],
                __WEBPACK_IMPORTED_MODULE_17__delivery_information_delivery_information_component__["a" /* DeliveryInformationComponent */],
                __WEBPACK_IMPORTED_MODULE_18__purchase_order_table_purchase_order_table_component__["a" /* PurchaseOrderTableComponent */],
                __WEBPACK_IMPORTED_MODULE_19__invoice_table_invoice_table_component__["a" /* InvoiceTableComponent */],
                __WEBPACK_IMPORTED_MODULE_21__invoice_information_invoice_information_component__["a" /* InvoiceInformationComponent */],
                __WEBPACK_IMPORTED_MODULE_22__purchase_order_history_purchase_order_history_component__["a" /* PurchaseOrderHistoryComponent */],
                __WEBPACK_IMPORTED_MODULE_23__invoice_history_invoice_history_component__["a" /* InvoiceHistoryComponent */]
            ],
            imports: [
                __WEBPACK_IMPORTED_MODULE_5__angular_flex_layout__["a" /* FlexLayoutModule */],
                __WEBPACK_IMPORTED_MODULE_6__angular_platform_browser_animations__["a" /* BrowserAnimationsModule */],
                __WEBPACK_IMPORTED_MODULE_1__angular_platform_browser__["a" /* BrowserModule */],
                __WEBPACK_IMPORTED_MODULE_3__angular_forms__["c" /* FormsModule */],
                __WEBPACK_IMPORTED_MODULE_4__angular_http__["b" /* HttpModule */],
                __WEBPACK_IMPORTED_MODULE_7__angular_material__["u" /* MatToolbarModule */],
                __WEBPACK_IMPORTED_MODULE_7__angular_material__["s" /* MatSidenavModule */],
                __WEBPACK_IMPORTED_MODULE_7__angular_material__["i" /* MatIconModule */],
                __WEBPACK_IMPORTED_MODULE_7__angular_material__["b" /* MatButtonModule */],
                __WEBPACK_IMPORTED_MODULE_7__angular_material__["l" /* MatMenuModule */],
                __WEBPACK_IMPORTED_MODULE_7__angular_material__["k" /* MatListModule */],
                __WEBPACK_IMPORTED_MODULE_7__angular_material__["c" /* MatCardModule */],
                __WEBPACK_IMPORTED_MODULE_7__angular_material__["f" /* MatDialogModule */],
                __WEBPACK_IMPORTED_MODULE_7__angular_material__["r" /* MatSelectModule */],
                __WEBPACK_IMPORTED_MODULE_7__angular_material__["m" /* MatOptionModule */],
                __WEBPACK_IMPORTED_MODULE_7__angular_material__["j" /* MatInputModule */],
                __WEBPACK_IMPORTED_MODULE_7__angular_material__["h" /* MatFormFieldModule */],
                __WEBPACK_IMPORTED_MODULE_7__angular_material__["q" /* MatProgressSpinnerModule */],
                __WEBPACK_IMPORTED_MODULE_7__angular_material__["d" /* MatChipsModule */],
                __WEBPACK_IMPORTED_MODULE_7__angular_material__["t" /* MatTabsModule */],
                __WEBPACK_IMPORTED_MODULE_7__angular_material__["o" /* MatPaginatorModule */],
                __WEBPACK_IMPORTED_MODULE_7__angular_material__["p" /* MatProgressBarModule */]
            ],
            providers: [__WEBPACK_IMPORTED_MODULE_10__api_domain_nodes_node_service__["a" /* NodeService */], __WEBPACK_IMPORTED_MODULE_15_api_domain_purchase_orders_purchase_order_service__["a" /* PurchaseOrderService */], __WEBPACK_IMPORTED_MODULE_16_api_domain_invoices_invoices_service__["a" /* InvoicesService */], __WEBPACK_IMPORTED_MODULE_11_api_domain_history_history_service__["a" /* HistoryService */]],
            bootstrap: [__WEBPACK_IMPORTED_MODULE_8__root_root_component__["a" /* RootComponent */]],
            entryComponents: [__WEBPACK_IMPORTED_MODULE_17__delivery_information_delivery_information_component__["a" /* DeliveryInformationComponent */], __WEBPACK_IMPORTED_MODULE_21__invoice_information_invoice_information_component__["a" /* InvoiceInformationComponent */]]
        }),
        __metadata("design:paramtypes", [])
    ], AppModule);
    return AppModule;
}());



/***/ }),

/***/ "./src/app/config/config.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return Config; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_http__ = __webpack_require__("./node_modules/@angular/http/esm5/http.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_rxjs_Observable__ = __webpack_require__("./node_modules/rxjs/_esm5/Observable.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_rxjs_add_operator_catch__ = __webpack_require__("./node_modules/rxjs/_esm5/add/operator/catch.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};




var Config = /** @class */ (function () {
    function Config(http) {
        this.http = http;
    }
    Config.prototype.load = function () {
        var _this = this;
        return new Promise(function (resolve, reject) {
            _this.http.get("app/config/env.json")
                .map(function (res) { return res.json(); })
                .subscribe(function (env_data) {
                _this.env = env_data;
                _this.http.get("app/config/" + env_data.env + ".json")
                    .map(function (res) { return res.json(); })
                    .catch(function (error) {
                    console.error(error);
                    return __WEBPACK_IMPORTED_MODULE_2_rxjs_Observable__["a" /* Observable */].throw(error.json().error || "Server error");
                })
                    .subscribe(function (data) {
                    _this.config = data;
                    resolve(data);
                });
            });
        });
    };
    Config = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["z" /* Injectable */])(),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1__angular_http__["a" /* Http */]])
    ], Config);
    return Config;
}());



/***/ }),

/***/ "./src/app/delivery-information/delivery-information.component.html":
/***/ (function(module, exports) {

module.exports = "<table>\n  <tbody>\n    <tr>\n      <td><strong>Earliest Shipment</strong></td>\n      <td>{{data.earliestShipment | date:'short'}}</td>\n    </tr>\n    <tr>\n      <td><strong>Latest Shipment</strong></td>\n      <td>{{data.latestShipment | date:'short'}}</td>\n    </tr>\n    <tr>\n      <td><strong>Port of Shipment</strong></td>\n      <td>{{data.portOfShipment}}</td>\n    </tr>\n    <tr>\n      <td><strong>Description of Goods</strong></td>\n      <td>{{data.descriptionOfGoods}}</td>\n    </tr>\n    <tr>\n      <td><strong>Delivery Terms</strong></td>\n      <td>{{data.deliveryTerms}}</td>\n    </tr>\n  </tbody>\n</table>"

/***/ }),

/***/ "./src/app/delivery-information/delivery-information.component.scss":
/***/ (function(module, exports) {

module.exports = ""

/***/ }),

/***/ "./src/app/delivery-information/delivery-information.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return DeliveryInformationComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_material__ = __webpack_require__("./node_modules/@angular/material/esm5/material.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_api_domain_purchase_orders_purchase_order_state__ = __webpack_require__("./src/api/domain/purchase-orders/purchase-order-state.ts");
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



var DeliveryInformationComponent = /** @class */ (function () {
    function DeliveryInformationComponent(data) {
        this.data = data;
    }
    DeliveryInformationComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["m" /* Component */])({
            selector: "app-delivery-information",
            template: __webpack_require__("./src/app/delivery-information/delivery-information.component.html"),
            styles: [__webpack_require__("./src/app/delivery-information/delivery-information.component.scss")]
        }),
        __param(0, Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["y" /* Inject */])(__WEBPACK_IMPORTED_MODULE_1__angular_material__["a" /* MAT_DIALOG_DATA */])),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_2_api_domain_purchase_orders_purchase_order_state__["a" /* PurchaseOrderState */]])
    ], DeliveryInformationComponent);
    return DeliveryInformationComponent;
}());



/***/ }),

/***/ "./src/app/invoice-history/invoice-history.component.html":
/***/ (function(module, exports) {

module.exports = "<div *ngFor=\"let item of items; let i = index\">\n  <br />\n  <h4>Invoice Version {{i + 1}}</h4>\n  <table>\n    <tbody>\n      <tr>\n        <td>\n          <strong>ID</strong>\n        </td>\n        <td>{{item.linearId.externalId}}</td>\n      </tr>\n      <tr>\n        <td>\n          <strong>Owner</strong>\n        </td>\n        <td>{{item.owner.organizationName}}</td>\n      </tr>\n      <tr>\n        <td>\n          <strong>Buyer</strong>\n        </td>\n        <td>{{item.buyer.organizationName}}</td>\n      </tr>\n      <tr>\n        <td>\n          <strong>Supplier</strong>\n        </td>\n        <td>{{item.supplier.organizationName}}</td>\n      </tr>\n      <tr>\n        <td>\n          <strong>Invoice Version</strong>\n        </td>\n        <td>{{item.invoiceVersion}}</td>\n      </tr>\n      <tr>\n        <td>\n          <strong>Invoice Version Date</strong>\n        </td>\n        <td>{{item.invoiceVersionDate | date:'short'}}</td>\n      </tr>\n      <tr>\n        <td>\n          <strong>TIX Invoice Version</strong>\n        </td>\n        <td>{{item.tixInvoiceVersion}}</td>\n      </tr>\n      <tr>\n        <td>\n          <strong>Invoice Number</strong>\n        </td>\n        <td>{{item.invoiceNumber}}</td>\n      </tr>\n      <tr>\n        <td>\n          <strong>Invoice Type</strong>\n        </td>\n        <td>{{item.invoiceType}}</td>\n      </tr>\n      <tr>\n        <td>\n          <strong>Reference</strong>\n        </td>\n        <td>{{item.reference}}</td>\n      </tr>\n      <tr>\n        <td>\n          <strong>Due Date</strong>\n        </td>\n        <td>{{item.dueDate | date:'short'}}</td>\n      </tr>\n      <tr>\n        <td>\n          <strong>Offer ID</strong>\n        </td>\n        <td>{{item.offerId}}</td>\n      </tr>\n      <tr>\n        <td>\n          <strong>Amount</strong>\n        </td>\n        <td>{{item.amount}}</td>\n      </tr>\n      <tr>\n        <td>\n          <strong>Total Outstanding</strong>\n        </td>\n        <td>{{item.totalOutstanding}}</td>\n      </tr>\n      <tr>\n        <td>\n          <strong>Created</strong>\n        </td>\n        <td>{{item.created | date:'short'}}</td>\n      </tr>\n      <tr>\n        <td>\n          <strong>Updated</strong>\n        </td>\n        <td>{{item.updated | date:'short'}}</td>\n      </tr>\n      <tr>\n        <td>\n          <strong>Expected Settlement Date</strong>\n        </td>\n        <td>{{item.expectedSettlementDate | date:'short'}}</td>\n      </tr>\n      <tr>\n        <td>\n          <strong>Settlement Date</strong>\n        </td>\n        <td>{{item.settlementDate | date:'short'}}</td>\n      </tr>\n      <tr>\n        <td>\n          <strong>Mandatory Reconciliation Date</strong>\n        </td>\n        <td>{{item.mandatoryReconciliationDate | date:'short'}}</td>\n      </tr>\n      <tr>\n        <td>\n          <strong>Invoice Date</strong>\n        </td>\n        <td>{{item.invoiceDate | date:'short'}}</td>\n      </tr>\n      <tr>\n        <td>\n          <strong>Status</strong>\n        </td>\n        <td>{{item.status}}</td>\n      </tr>\n      <tr>\n        <td>\n          <strong>Rejection Reason</strong>\n        </td>\n        <td>{{item.rejectionReason}}</td>\n      </tr>\n      <tr>\n        <td>\n          <strong>EligibleValue</strong>\n        </td>\n        <td>{{item.eligibleValue}}</td>\n      </tr>\n      <tr>\n        <td>\n          <strong>Invoice Purchase Value</strong>\n        </td>\n        <td>{{item.invoicePurchaseValue}}</td>\n      </tr>\n      <tr>\n        <td>\n          <strong>Trade Date</strong>\n        </td>\n        <td>{{item.tradeDate | date:'short'}}</td>\n      </tr>\n      <tr>\n        <td>\n          <strong>Trade Payment Date</strong>\n        </td>\n        <td>{{item.tradePaymentDate | date:'short'}}</td>\n      </tr>\n      <tr>\n        <td>\n          <strong>Invoice Payments</strong>\n        </td>\n        <td>{{item.invoicePayments}}</td>\n      </tr>\n      <tr>\n        <td>\n          <strong>Invoice Dilutions</strong>\n        </td>\n        <td>{{item.invoiceDilutions}}</td>\n      </tr>\n      <tr>\n        <td>\n          <strong>Cancelled</strong>\n        </td>\n        <td>{{item.cancelled ? 'Yes' : 'No'}}</td>\n      </tr>\n      <tr>\n        <td>\n          <strong>Close Date</strong>\n        </td>\n        <td>{{item.closeDate | date:'short'}}</td>\n      </tr>\n      <tr>\n        <td>\n          <strong>Origination Network</strong>\n        </td>\n        <td>{{item.originationNetwork}}</td>\n      </tr>\n      <tr>\n        <td>\n          <strong>Hash</strong>\n        </td>\n        <td>{{item.hash}}</td>\n      </tr>\n      <tr>\n        <td>\n          <strong>Currency</strong>\n        </td>\n        <td>{{item.currency}}</td>\n      </tr>\n      <tr>\n        <td>\n          <strong>Site ID</strong>\n        </td>\n        <td>{{item.siteId}}</td>\n      </tr>\n      <tr>\n        <td>\n          <strong>Purchase Order Number</strong>\n        </td>\n        <td>{{item.purchaseOrderNumber}}</td>\n      </tr>\n      <tr>\n        <td>\n          <strong>Purchase Order Id</strong>\n        </td>\n        <td>{{item.purchaseOrderId}}</td>\n      </tr>\n      <tr>\n        <td>\n          <strong>Composer Program Id</strong>\n        </td>\n        <td>{{item.composerProgramId}}</td>\n      </tr>\n    </tbody>\n  </table>\n</div>\n"

/***/ }),

/***/ "./src/app/invoice-history/invoice-history.component.scss":
/***/ (function(module, exports) {

module.exports = "h4 {\n  display: inline-block;\n  border: 1px solid white;\n  border-radius: 4px;\n  padding: 4px;\n  margin: 0 0 8px;\n  background: rgba(255, 255, 255, 0.1); }\n\ntable {\n  background: transparent;\n  border: none;\n  border-bottom: 1px solid rgba(255, 255, 255, 0.1); }\n\ntable tr {\n    border: none; }\n"

/***/ }),

/***/ "./src/app/invoice-history/invoice-history.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return InvoiceHistoryComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_api_domain_shared_corda__ = __webpack_require__("./src/api/domain/shared/corda.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};


var InvoiceHistoryComponent = /** @class */ (function () {
    function InvoiceHistoryComponent() {
        this.items = [];
    }
    InvoiceHistoryComponent.prototype.setItems = function (items) {
        this.items = items;
    };
    InvoiceHistoryComponent.prototype.getName = function (value) {
        return __WEBPACK_IMPORTED_MODULE_1_api_domain_shared_corda__["a" /* CordaX500Name */].parse(value);
    };
    InvoiceHistoryComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["m" /* Component */])({
            selector: "app-invoice-history",
            template: __webpack_require__("./src/app/invoice-history/invoice-history.component.html"),
            styles: [__webpack_require__("./src/app/invoice-history/invoice-history.component.scss")]
        })
    ], InvoiceHistoryComponent);
    return InvoiceHistoryComponent;
}());



/***/ }),

/***/ "./src/app/invoice-information/invoice-information.component.html":
/***/ (function(module, exports) {

module.exports = "<mat-dialog-content>\n  <table>\n    <tbody>\n      <tr>\n        <td>\n          <strong>Invoice Version</strong>\n        </td>\n        <td>{{data.invoiceVersion}}</td>\n      </tr>\n      <tr>\n        <td>\n          <strong>Invoice Version Date</strong>\n        </td>\n        <td>{{data.invoiceVersionDate | date:'short'}}</td>\n      </tr>\n      <tr>\n        <td>\n          <strong>TIX Invoice Version</strong>\n        </td>\n        <td>{{data.tixInvoiceVersion}}</td>\n      </tr>\n      <tr>\n        <td>\n          <strong>Invoice Number</strong>\n        </td>\n        <td>{{data.invoiceNumber}}</td>\n      </tr>\n      <tr>\n        <td>\n          <strong>Invoice Type</strong>\n        </td>\n        <td>{{data.invoiceType}}</td>\n      </tr>\n      <tr>\n        <td>\n          <strong>Reference</strong>\n        </td>\n        <td>{{data.reference}}</td>\n      </tr>\n      <tr>\n        <td>\n          <strong>Due Date</strong>\n        </td>\n        <td>{{data.dueDate | date:'short'}}</td>\n      </tr>\n      <tr>\n        <td>\n          <strong>Offer ID</strong>\n        </td>\n        <td>{{data.offerId}}</td>\n      </tr>\n      <tr>\n        <td>\n          <strong>Amount</strong>\n        </td>\n        <td>{{data.amount}}</td>\n      </tr>\n      <tr>\n        <td>\n          <strong>Total Outstanding</strong>\n        </td>\n        <td>{{data.totalOutstanding}}</td>\n      </tr>\n      <tr>\n        <td>\n          <strong>Created</strong>\n        </td>\n        <td>{{data.created | date:'short'}}</td>\n      </tr>\n      <tr>\n        <td>\n          <strong>Updated</strong>\n        </td>\n        <td>{{data.updated | date:'short'}}</td>\n      </tr>\n      <tr>\n        <td>\n          <strong>Expected Settlement Date</strong>\n        </td>\n        <td>{{data.expectedSettlementDate | date:'short'}}</td>\n      </tr>\n      <tr>\n        <td>\n          <strong>Settlement Date</strong>\n        </td>\n        <td>{{data.settlementDate | date:'short'}}</td>\n      </tr>\n      <tr>\n        <td>\n          <strong>Mandatory Reconciliation Date</strong>\n        </td>\n        <td>{{data.mandatoryReconciliationDate | date:'short'}}</td>\n      </tr>\n      <tr>\n        <td>\n          <strong>Invoice Date</strong>\n        </td>\n        <td>{{data.invoiceDate | date:'short'}}</td>\n      </tr>\n      <tr>\n        <td>\n          <strong>Status</strong>\n        </td>\n        <td>{{data.status}}</td>\n      </tr>\n      <tr>\n        <td>\n          <strong>Rejection Reason</strong>\n        </td>\n        <td>{{data.rejectionReason}}</td>\n      </tr>\n      <tr>\n        <td>\n          <strong>EligibleValue</strong>\n        </td>\n        <td>{{data.eligibleValue}}</td>\n      </tr>\n      <tr>\n        <td>\n          <strong>Invoice Purchase Value</strong>\n        </td>\n        <td>{{data.invoicePurchaseValue}}</td>\n      </tr>\n      <tr>\n        <td>\n          <strong>Trade Date</strong>\n        </td>\n        <td>{{data.tradeDate | date:'short'}}</td>\n      </tr>\n      <tr>\n        <td>\n          <strong>Trade Payment Date</strong>\n        </td>\n        <td>{{data.tradePaymentDate | date:'short'}}</td>\n      </tr>\n      <tr>\n        <td>\n          <strong>Invoice Payments</strong>\n        </td>\n        <td>{{data.invoicePayments}}</td>\n      </tr>\n      <tr>\n        <td>\n          <strong>Invoice Dilutions</strong>\n        </td>\n        <td>{{data.invoiceDilutions}}</td>\n      </tr>\n      <tr>\n        <td>\n          <strong>Cancelled</strong>\n        </td>\n        <td>{{data.cancelled ? 'Yes' : 'No'}}</td>\n      </tr>\n      <tr>\n        <td>\n          <strong>Close Date</strong>\n        </td>\n        <td>{{data.closeDate | date:'short'}}</td>\n      </tr>\n      <tr>\n        <td>\n          <strong>Origination Network</strong>\n        </td>\n        <td>{{data.originationNetwork}}</td>\n      </tr>\n      <tr>\n        <td>\n          <strong>Hash</strong>\n        </td>\n        <td>{{data.hash}}</td>\n      </tr>\n      <tr>\n        <td>\n          <strong>Currency</strong>\n        </td>\n        <td>{{data.currency}}</td>\n      </tr>\n      <tr>\n        <td>\n          <strong>Site ID</strong>\n        </td>\n        <td>{{data.siteId}}</td>\n      </tr>\n      <tr>\n        <td>\n          <strong>Purchase Order Number</strong>\n        </td>\n        <td>{{data.purchaseOrderNumber}}</td>\n      </tr>\n      <tr>\n        <td>\n          <strong>Purchase Order Id</strong>\n        </td>\n        <td>{{data.purchaseOrderId}}</td>\n      </tr>\n      <tr>\n        <td>\n          <strong>Composer Program Id</strong>\n        </td>\n        <td>{{data.composerProgramId}}</td>\n      </tr>\n    </tbody>\n  </table>\n</mat-dialog-content>\n"

/***/ }),

/***/ "./src/app/invoice-information/invoice-information.component.scss":
/***/ (function(module, exports) {

module.exports = ""

/***/ }),

/***/ "./src/app/invoice-information/invoice-information.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return InvoiceInformationComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_material__ = __webpack_require__("./node_modules/@angular/material/esm5/material.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_api_domain_invoices_invoice_state__ = __webpack_require__("./src/api/domain/invoices/invoice-state.ts");
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



var InvoiceInformationComponent = /** @class */ (function () {
    function InvoiceInformationComponent(data) {
        this.data = data;
    }
    InvoiceInformationComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["m" /* Component */])({
            selector: "app-invoice-information",
            template: __webpack_require__("./src/app/invoice-information/invoice-information.component.html"),
            styles: [__webpack_require__("./src/app/invoice-information/invoice-information.component.scss")]
        }),
        __param(0, Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["y" /* Inject */])(__WEBPACK_IMPORTED_MODULE_1__angular_material__["a" /* MAT_DIALOG_DATA */])),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_2_api_domain_invoices_invoice_state__["a" /* InvoiceState */]])
    ], InvoiceInformationComponent);
    return InvoiceInformationComponent;
}());



/***/ }),

/***/ "./src/app/invoice-table/invoice-table.component.html":
/***/ (function(module, exports) {

module.exports = "<div [hidden]=\"visibleState !== 'full'\">\n  <mat-paginator #invoicePaginator (page)=\"onPage($event)\"></mat-paginator>\n  <div class=\"scroll-container\">\n    <table>\n      <thead>\n        <tr>\n          <th>ID</th>\n          <th>Owner</th>\n          <th>Buyer</th>\n          <th>Supplier</th>\n          <th>Created</th>\n          <th class=\"align-right\">Amount</th>\n          <th></th>\n        </tr>\n      </thead>\n      <tbody>\n        <ng-container *ngFor=\"let state of states\">\n          <tr>\n            <td>\n              <a (click)=\"onOpenDrawer(state.linearId.externalId)\" href=\"#\">{{state.linearId.externalId}}</a>\n            </td>\n            <td>{{state.owner.organizationName}}</td>\n            <td>{{state.buyer.organizationName}}</td>\n            <td>{{state.supplier.organizationName}}</td>\n            <td>{{state.created | date:'short'}}</td>\n            <td class=\"align-right\">{{state.amount}}</td>\n            <td class=\"align-right\">\n              <button mat-button color=\"accent\" (click)=\"onOpenDialog(state.linearId.externalId)\">More Information</button>\n            </td>\n          </tr>\n        </ng-container>\n      </tbody>\n    </table>\n  </div>\n</div>\n<div [hidden]=\"visibleState !== 'empty'\">\n  <h2 class=\"message\">Nothing to see here</h2>\n</div>\n<div [hidden]=\"visibleState !== 'loading'\">\n  <mat-progress-bar color=\"accent\" mode=\"indeterminate\"></mat-progress-bar>\n  <h2 class=\"message\">Loading</h2>\n</div>\n"

/***/ }),

/***/ "./src/app/invoice-table/invoice-table.component.scss":
/***/ (function(module, exports) {

module.exports = "mat-paginator {\n  background: transparent; }\n\na {\n  color: #2BE7F8; }\n\n.scroll-container {\n  height: calc(100vh - 240px); }\n\n.message {\n  padding: 128px;\n  text-align: center;\n  color: rgba(255, 255, 255, 0.5); }\n"

/***/ }),

/***/ "./src/app/invoice-table/invoice-table.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return InvoiceTableComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_api_domain_invoices_invoices_service__ = __webpack_require__("./src/api/domain/invoices/invoices.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__angular_material__ = __webpack_require__("./node_modules/@angular/material/esm5/material.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_api_domain_history_history_service__ = __webpack_require__("./src/api/domain/history/history.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4_app_invoice_information_invoice_information_component__ = __webpack_require__("./src/app/invoice-information/invoice-information.component.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};





var InvoiceTableComponent = /** @class */ (function () {
    function InvoiceTableComponent(invoicesService, historyService, dialog) {
        this.invoicesService = invoicesService;
        this.historyService = historyService;
        this.dialog = dialog;
        this.states = null;
        this.visibleState = "loading";
        this.hash = undefined;
        this.pageNumber = 1;
        this.pageSize = 50;
        this.update();
    }
    InvoiceTableComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.invoicePaginator.pageSize = this.pageSize;
        window.setInterval(function () { return _this.update(); }, 5000);
    };
    InvoiceTableComponent.prototype.update = function (forceUpdate) {
        var _this = this;
        if (forceUpdate === void 0) { forceUpdate = false; }
        try {
            this.invoicesService
                .getMostRecentInvoiceHash()
                .subscribe(function (hash) {
                if (hash !== _this.hash) {
                    _this.hash = hash;
                    forceUpdate = true;
                }
                if (forceUpdate) {
                    _this.invoicesService
                        .getInvoiceCount()
                        .subscribe(function (count) {
                        _this.invoicePaginator.length = count;
                        if (count === 0) {
                            _this.visibleState = "empty";
                        }
                        else {
                            _this.visibleState = "full";
                            _this.invoicesService
                                .getAllInvoices(_this.pageNumber, _this.pageSize)
                                .subscribe(function (assets) { return _this.states = assets; });
                        }
                    });
                }
            });
        }
        catch (e) {
            console.error(e);
        }
    };
    InvoiceTableComponent.prototype.onPage = function (event) {
        var _this = this;
        window.clearTimeout(this.pageTimeout);
        this.pageTimeout = window.setTimeout(function () {
            _this.visibleState = "loading";
            _this.pageNumber = event.pageIndex + 1;
            _this.update(true);
        }, 1000);
    };
    InvoiceTableComponent.prototype.onOpenDialog = function (externalId) {
        this.dialog.open(__WEBPACK_IMPORTED_MODULE_4_app_invoice_information_invoice_information_component__["a" /* InvoiceInformationComponent */], {
            position: { top: "64px" },
            width: "720px",
            maxHeight: "400px",
            data: this.states.find(function (state) { return state.linearId.externalId === externalId; })
        });
    };
    InvoiceTableComponent.prototype.onOpenDrawer = function (externalId) {
        this.historyService.openDrawer(externalId, "INVOICE");
    };
    __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_7" /* ViewChild */])("invoicePaginator"),
        __metadata("design:type", __WEBPACK_IMPORTED_MODULE_2__angular_material__["n" /* MatPaginator */])
    ], InvoiceTableComponent.prototype, "invoicePaginator", void 0);
    InvoiceTableComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["m" /* Component */])({
            selector: "app-invoice-table",
            template: __webpack_require__("./src/app/invoice-table/invoice-table.component.html"),
            styles: [__webpack_require__("./src/app/invoice-table/invoice-table.component.scss")]
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1_api_domain_invoices_invoices_service__["a" /* InvoicesService */],
            __WEBPACK_IMPORTED_MODULE_3_api_domain_history_history_service__["a" /* HistoryService */],
            __WEBPACK_IMPORTED_MODULE_2__angular_material__["e" /* MatDialog */]])
    ], InvoiceTableComponent);
    return InvoiceTableComponent;
}());



/***/ }),

/***/ "./src/app/main/main.component.html":
/***/ (function(module, exports) {

module.exports = "<mat-tab-group color=\"accent\">\n  <mat-tab label=\"Purchase Orders\">\n    <app-purchase-order-table></app-purchase-order-table>\n  </mat-tab>\n  <mat-tab label=\"Invoices\">\n    <app-invoice-table></app-invoice-table>\n  </mat-tab>\n</mat-tab-group>"

/***/ }),

/***/ "./src/app/main/main.component.scss":
/***/ (function(module, exports) {

module.exports = ""

/***/ }),

/***/ "./src/app/main/main.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return MainComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};

var MainComponent = /** @class */ (function () {
    function MainComponent() {
    }
    MainComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["m" /* Component */])({
            selector: "app-main",
            template: __webpack_require__("./src/app/main/main.component.html"),
            styles: [__webpack_require__("./src/app/main/main.component.scss")]
        })
    ], MainComponent);
    return MainComponent;
}());



/***/ }),

/***/ "./src/app/purchase-order-history/purchase-order-history.component.html":
/***/ (function(module, exports) {

module.exports = "<div *ngFor=\"let item of items; let i = index\">\n  <br />\n  <h4>Purchase Order Version {{i + 1}}</h4>\n  <table>\n    <tbody>\n      <tr>\n        <td><strong>Owner</strong></td>\n        <td>{{item.owner.organizationName}}</td>\n      </tr>\n      <tr>\n        <td><strong>Buyer</strong></td>\n        <td>{{item.buyer.organizationName}}</td>\n      </tr>\n      <tr>\n        <td><strong>Supplier</strong></td>\n        <td>{{item.supplier.organizationName}}</td>\n      </tr>\n      <tr>\n        <td><strong>Reference</strong></td>\n        <td>{{item.reference}}</td>\n      </tr>\n      <tr>\n        <td><strong>Created</strong></td>\n        <td>{{item.created | date:'medium'}}</td>\n      </tr>\n      <tr>\n        <td><strong>Amount</strong></td>\n        <td>{{item.amount}}</td>\n      </tr>\n      <tr>\n        <td><strong>Earliest Shipment</strong></td>\n        <td>{{item.earliestShipment | date:'medium'}}</td>\n      </tr>\n      <tr>\n        <td><strong>Latest Shipment</strong></td>\n        <td>{{item.latestShipment | date:'medium'}}</td>\n      </tr>\n      <tr>\n        <td><strong>Port of Shipment</strong></td>\n        <td>{{item.portOfShipment}}</td>\n      </tr>\n      <tr>\n        <td><strong>Description of Goods</strong></td>\n        <td>{{item.descriptionOfGoods}}</td>\n      </tr>\n      <tr>\n        <td><strong>Delivery Terms</strong></td>\n        <td>{{item.deliveryTerms}}</td>\n      </tr>\n    </tbody>\n  </table>\n</div>"

/***/ }),

/***/ "./src/app/purchase-order-history/purchase-order-history.component.scss":
/***/ (function(module, exports) {

module.exports = "h4 {\n  display: inline-block;\n  border: 1px solid white;\n  border-radius: 4px;\n  padding: 4px;\n  margin: 0 0 8px;\n  background: rgba(255, 255, 255, 0.1); }\n\ntable {\n  background: transparent;\n  border: none;\n  border-bottom: 1px solid rgba(255, 255, 255, 0.1); }\n\ntable tr {\n    border: none; }\n"

/***/ }),

/***/ "./src/app/purchase-order-history/purchase-order-history.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return PurchaseOrderHistoryComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_api_domain_shared_corda__ = __webpack_require__("./src/api/domain/shared/corda.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};


var PurchaseOrderHistoryComponent = /** @class */ (function () {
    function PurchaseOrderHistoryComponent() {
        this.items = [];
    }
    PurchaseOrderHistoryComponent.prototype.setItems = function (items) {
        this.items = items;
    };
    PurchaseOrderHistoryComponent.prototype.getName = function (value) {
        return __WEBPACK_IMPORTED_MODULE_1_api_domain_shared_corda__["a" /* CordaX500Name */].parse(value);
    };
    PurchaseOrderHistoryComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["m" /* Component */])({
            selector: "app-purchase-order-history",
            template: __webpack_require__("./src/app/purchase-order-history/purchase-order-history.component.html"),
            styles: [__webpack_require__("./src/app/purchase-order-history/purchase-order-history.component.scss")]
        })
    ], PurchaseOrderHistoryComponent);
    return PurchaseOrderHistoryComponent;
}());



/***/ }),

/***/ "./src/app/purchase-order-table/purchase-order-table.component.html":
/***/ (function(module, exports) {

module.exports = "<div [hidden]=\"visibleState !== 'full'\">\n  <mat-paginator #purchaseOrderPaginator (page)=\"onPage($event)\"></mat-paginator>\n  <div class=\"scroll-container\">\n    <table>\n      <thead>\n        <tr>\n          <th>ID</th>\n          <th>Owner</th>\n          <th>Buyer</th>\n          <th>Supplier</th>\n          <th>Reference</th>\n          <th>Created</th>\n          <th class=\"align-right\">Amount</th>\n          <th></th>\n        </tr>\n      </thead>\n      <tbody>\n        <ng-container *ngFor=\"let state of states\">\n          <tr>\n            <td>\n              <a (click)=\"onOpenDrawer(state.linearId.externalId)\" href=\"#\">{{state.linearId.externalId}}</a>\n            </td>\n            <td>{{state.owner.organizationName}}</td>\n            <td>{{state.buyer.organizationName}}</td>\n            <td>{{state.supplier.organizationName}}</td>\n            <td>{{state.reference}}</td>\n            <td>{{state.created | date:'short'}}</td>\n            <td class=\"align-right\">{{state.amount}}</td>\n            <td class=\"align-right\">\n              <button mat-button color=\"accent\" (click)=\"onOpenDialog(state.linearId.externalId)\">Delivery Info</button>\n            </td>\n          </tr>\n        </ng-container>\n      </tbody>\n    </table>\n  </div>\n</div>\n<div [hidden]=\"visibleState !== 'empty'\">\n  <h2 class=\"message\">Nothing to see here</h2>\n</div>\n<div [hidden]=\"visibleState !== 'loading'\">\n  <mat-progress-bar color=\"accent\" mode=\"indeterminate\"></mat-progress-bar>\n  <h2 class=\"message\">Loading</h2>\n</div>"

/***/ }),

/***/ "./src/app/purchase-order-table/purchase-order-table.component.scss":
/***/ (function(module, exports) {

module.exports = "mat-paginator {\n  background: transparent; }\n\na {\n  color: #2BE7F8; }\n\n.scroll-container {\n  height: calc(100vh - 240px); }\n\n.message {\n  padding: 128px;\n  text-align: center;\n  color: rgba(255, 255, 255, 0.5); }\n"

/***/ }),

/***/ "./src/app/purchase-order-table/purchase-order-table.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return PurchaseOrderTableComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_api_domain_purchase_orders_purchase_order_service__ = __webpack_require__("./src/api/domain/purchase-orders/purchase-order.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__angular_material__ = __webpack_require__("./node_modules/@angular/material/esm5/material.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_api_domain_history_history_service__ = __webpack_require__("./src/api/domain/history/history.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4_app_delivery_information_delivery_information_component__ = __webpack_require__("./src/app/delivery-information/delivery-information.component.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};





var PurchaseOrderTableComponent = /** @class */ (function () {
    function PurchaseOrderTableComponent(purchaseOrderService, historyService, dialog) {
        this.purchaseOrderService = purchaseOrderService;
        this.historyService = historyService;
        this.dialog = dialog;
        this.states = null;
        this.hash = undefined;
        this.visibleState = "loading";
        this.pageNumber = 1;
        this.pageSize = 50;
        this.update();
    }
    PurchaseOrderTableComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.purchaseOrderPaginator.pageSize = this.pageSize;
        window.setInterval(function () { return _this.update(); }, 5000);
    };
    PurchaseOrderTableComponent.prototype.update = function (forceUpdate) {
        var _this = this;
        if (forceUpdate === void 0) { forceUpdate = false; }
        try {
            this.purchaseOrderService
                .getMostRecentPurchaseOrderHash()
                .subscribe(function (hash) {
                if (hash !== _this.hash) {
                    _this.hash = hash;
                    forceUpdate = true;
                }
                if (forceUpdate) {
                    _this.purchaseOrderService
                        .getPurchaseOrderCount()
                        .subscribe(function (count) {
                        _this.purchaseOrderPaginator.length = count;
                        if (count === 0) {
                            _this.visibleState = "empty";
                        }
                        else {
                            _this.visibleState = "full";
                            _this.purchaseOrderService
                                .getAllPurchaseOrders(_this.pageNumber, _this.pageSize)
                                .subscribe(function (assets) { return _this.states = assets; });
                        }
                    });
                }
            });
        }
        catch (e) {
            console.error(e);
        }
    };
    PurchaseOrderTableComponent.prototype.onPage = function (event) {
        var _this = this;
        window.clearTimeout(this.pageTimeout);
        this.pageTimeout = window.setTimeout(function () {
            _this.visibleState = "loading";
            _this.pageNumber = event.pageIndex + 1;
            _this.update(true);
        }, 1000);
    };
    PurchaseOrderTableComponent.prototype.onOpenDialog = function (externalId) {
        this.dialog.open(__WEBPACK_IMPORTED_MODULE_4_app_delivery_information_delivery_information_component__["a" /* DeliveryInformationComponent */], {
            position: { top: "64px" },
            width: "720px",
            maxHeight: "400px",
            data: this.states.find(function (state) { return state.linearId.externalId === externalId; })
        });
    };
    PurchaseOrderTableComponent.prototype.onOpenDrawer = function (externalId) {
        this.historyService.openDrawer(externalId, "PURCHASE_ORDER");
    };
    __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_7" /* ViewChild */])("purchaseOrderPaginator"),
        __metadata("design:type", __WEBPACK_IMPORTED_MODULE_2__angular_material__["n" /* MatPaginator */])
    ], PurchaseOrderTableComponent.prototype, "purchaseOrderPaginator", void 0);
    PurchaseOrderTableComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["m" /* Component */])({
            selector: "app-purchase-order-table",
            template: __webpack_require__("./src/app/purchase-order-table/purchase-order-table.component.html"),
            styles: [__webpack_require__("./src/app/purchase-order-table/purchase-order-table.component.scss")]
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1_api_domain_purchase_orders_purchase_order_service__["a" /* PurchaseOrderService */],
            __WEBPACK_IMPORTED_MODULE_3_api_domain_history_history_service__["a" /* HistoryService */],
            __WEBPACK_IMPORTED_MODULE_2__angular_material__["e" /* MatDialog */]])
    ], PurchaseOrderTableComponent);
    return PurchaseOrderTableComponent;
}());



/***/ }),

/***/ "./src/app/root/root.component.html":
/***/ (function(module, exports) {

module.exports = "<div fxFill fxLayout=\"column\">\n  <mat-toolbar>\n    <img class=\"logo\" src=\"/web/tix/assets/images/MarcoPolo.svg\" height=\"16\" />\n    <div fxFlex></div>\n    <span>{{nodeName?.organizationName}}</span>\n  </mat-toolbar>\n  <mat-drawer-container #drawerContainer fxFlex>\n    <mat-drawer mode=\"over\" position=\"end\" opened=\"false\">\n      <div fxLayout=\"row\">\n        <h1 fxFlex class=\"test\">Asset History</h1>\n        <button mat-icon-button (click)=\"drawerContainer.close()\">\n          <mat-icon>close</mat-icon>\n        </button>\n      </div>\n      <hr />\n      <div class=\"scroll-container\">\n        <app-purchase-order-history #purchaseOrderHistory></app-purchase-order-history>\n        <app-invoice-history #invoiceHistory></app-invoice-history>\n      </div>\n    </mat-drawer>\n    <mat-drawer-content fxFlex fxLayout=\"column\">\n      <app-main fxFlex fxLayout=\"column\"></app-main>\n    </mat-drawer-content>\n  </mat-drawer-container>\n</div>"

/***/ }),

/***/ "./src/app/root/root.component.scss":
/***/ (function(module, exports) {

module.exports = "mat-toolbar {\n  background: #243343;\n  border-bottom: 1px solid #616E79; }\n\nmat-drawer-container {\n  background: #35506b;\n  background: -webkit-gradient(linear, left top, left bottom, from(#35506b), to(#1b304b));\n  background: linear-gradient(to bottom, #35506b 0%, #1b304b 100%);\n  filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#35506b', endColorstr='#1b304b', GradientType=0); }\n\nmat-drawer-container mat-drawer {\n    padding: 20px;\n    width: 640px;\n    background: #42617b;\n    background: -webkit-gradient(linear, left top, left bottom, from(#42617b), to(#273e59));\n    background: linear-gradient(to bottom, #42617b 0%, #273e59 100%);\n    filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#42617b', endColorstr='#273e59', GradientType=0); }\n\nmat-drawer-container mat-drawer-content {\n    overflow: hidden; }\n\napp-main {\n  -webkit-box-sizing: border-box;\n          box-sizing: border-box;\n  padding: 20px; }\n\n.scroll-container {\n  height: calc(100vh - 166px); }\n"

/***/ }),

/***/ "./src/app/root/root.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return RootComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_api_domain_nodes_node_service__ = __webpack_require__("./src/api/domain/nodes/node.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_api_domain_history_history_service__ = __webpack_require__("./src/api/domain/history/history.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__angular_material__ = __webpack_require__("./node_modules/@angular/material/esm5/material.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4_app_purchase_order_history_purchase_order_history_component__ = __webpack_require__("./src/app/purchase-order-history/purchase-order-history.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5_app_invoice_history_invoice_history_component__ = __webpack_require__("./src/app/invoice-history/invoice-history.component.ts");
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
    function RootComponent(nodeService, historyService) {
        this.nodeService = nodeService;
        this.historyService = historyService;
        this.nodeName = null;
    }
    RootComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.historyService.setDrawerContainer(this.drawerContainer);
        this.historyService.setPurchaseOrderHistoryComponent(this.purchaseOrderHistory);
        this.historyService.setInvoiceHistoryComponent(this.invoiceHistory);
        window.setTimeout(function () { return _this.nodeService
            .getLocalNode()
            .subscribe(function (response) { return _this.nodeName = response; }); }, 2000);
    };
    __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_7" /* ViewChild */])("drawerContainer"),
        __metadata("design:type", __WEBPACK_IMPORTED_MODULE_3__angular_material__["g" /* MatDrawerContainer */])
    ], RootComponent.prototype, "drawerContainer", void 0);
    __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_7" /* ViewChild */])("purchaseOrderHistory"),
        __metadata("design:type", __WEBPACK_IMPORTED_MODULE_4_app_purchase_order_history_purchase_order_history_component__["a" /* PurchaseOrderHistoryComponent */])
    ], RootComponent.prototype, "purchaseOrderHistory", void 0);
    __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_7" /* ViewChild */])("invoiceHistory"),
        __metadata("design:type", __WEBPACK_IMPORTED_MODULE_5_app_invoice_history_invoice_history_component__["a" /* InvoiceHistoryComponent */])
    ], RootComponent.prototype, "invoiceHistory", void 0);
    RootComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["m" /* Component */])({
            selector: "app-root",
            template: __webpack_require__("./src/app/root/root.component.html"),
            styles: [__webpack_require__("./src/app/root/root.component.scss")]
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1_api_domain_nodes_node_service__["a" /* NodeService */],
            __WEBPACK_IMPORTED_MODULE_2_api_domain_history_history_service__["a" /* HistoryService */]])
    ], RootComponent);
    return RootComponent;
}());



/***/ }),

/***/ "./src/environments/environment.ts":
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


/***/ }),

/***/ "./src/main.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_platform_browser_dynamic__ = __webpack_require__("./node_modules/@angular/platform-browser-dynamic/esm5/platform-browser-dynamic.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__app_app_module__ = __webpack_require__("./src/app/app.module.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__environments_environment__ = __webpack_require__("./src/environments/environment.ts");




if (__WEBPACK_IMPORTED_MODULE_3__environments_environment__["a" /* environment */].production) {
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_13" /* enableProdMode */])();
}
Object(__WEBPACK_IMPORTED_MODULE_1__angular_platform_browser_dynamic__["a" /* platformBrowserDynamic */])().bootstrapModule(__WEBPACK_IMPORTED_MODULE_2__app_app_module__["a" /* AppModule */]);


/***/ }),

/***/ 0:
/***/ (function(module, exports, __webpack_require__) {

module.exports = __webpack_require__("./src/main.ts");


/***/ })

},[0]);
//# sourceMappingURL=main.bundle.js.map