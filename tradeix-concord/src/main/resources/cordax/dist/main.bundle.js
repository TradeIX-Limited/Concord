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

/***/ "../../../../../src/api/domain/base.service.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return BaseService; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_http__ = __webpack_require__("../../../http/@angular/http.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__app_config_config__ = __webpack_require__("../../../../../src/app/config/config.ts");
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
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["B" /* Injectable */])(),
        __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_1__angular_http__["a" /* Http */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1__angular_http__["a" /* Http */]) === "function" && _a || Object])
    ], BaseService);
    return BaseService;
    var _a;
}());

//# sourceMappingURL=base.service.js.map

/***/ }),

/***/ "../../../../../src/api/domain/history/history.service.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return HistoryService; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_api_domain_purchase_orders_purchase_order_service__ = __webpack_require__("../../../../../src/api/domain/purchase-orders/purchase-order.service.ts");
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
    function HistoryService(tradeAssetService) {
        this.tradeAssetService = tradeAssetService;
        this.drawerContainer = null;
        this.historyComponent = null;
    }
    HistoryService.prototype.setComponents = function (drawerContainer, historyComponent) {
        this.drawerContainer = drawerContainer;
        this.historyComponent = historyComponent;
    };
    HistoryService.prototype.openDrawer = function (externalId) {
        var _this = this;
        this.historyComponent.setItems([]);
        this.drawerContainer.open();
        this.tradeAssetService
            .getPurchaseOrderState(externalId)
            .subscribe(function (response) {
            _this.historyComponent.setItems(response);
        });
    };
    HistoryService = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["B" /* Injectable */])(),
        __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_1_api_domain_purchase_orders_purchase_order_service__["a" /* PurchaseOrderService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1_api_domain_purchase_orders_purchase_order_service__["a" /* PurchaseOrderService */]) === "function" && _a || Object])
    ], HistoryService);
    return HistoryService;
    var _a;
}());

//# sourceMappingURL=history.service.js.map

/***/ }),

/***/ "../../../../../src/api/domain/nodes/node.service.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return NodeService; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__base_service__ = __webpack_require__("../../../../../src/api/domain/base.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__shared_corda__ = __webpack_require__("../../../../../src/api/domain/shared/corda.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_rxjs_add_operator_map__ = __webpack_require__("../../../../rxjs/_esm5/add/operator/map.js");
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
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["B" /* Injectable */])()
    ], NodeService);
    return NodeService;
}(__WEBPACK_IMPORTED_MODULE_1__base_service__["a" /* BaseService */]));

//# sourceMappingURL=node.service.js.map

/***/ }),

/***/ "../../../../../src/api/domain/purchase-orders/purchase-order-state.ts":
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

//# sourceMappingURL=purchase-order-state.js.map

/***/ }),

/***/ "../../../../../src/api/domain/purchase-orders/purchase-order.service.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return PurchaseOrderService; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_api_domain_base_service__ = __webpack_require__("../../../../../src/api/domain/base.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_api_domain_shared_mapper__ = __webpack_require__("../../../../../src/api/domain/shared/mapper.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_api_domain_purchase_orders_purchase_order_state__ = __webpack_require__("../../../../../src/api/domain/purchase-orders/purchase-order-state.ts");
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
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["B" /* Injectable */])()
    ], PurchaseOrderService);
    return PurchaseOrderService;
}(__WEBPACK_IMPORTED_MODULE_1_api_domain_base_service__["a" /* BaseService */]));

//# sourceMappingURL=purchase-order.service.js.map

/***/ }),

/***/ "../../../../../src/api/domain/shared/corda.ts":
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

//# sourceMappingURL=corda.js.map

/***/ }),

/***/ "../../../../../src/api/domain/shared/mapper.ts":
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
//# sourceMappingURL=mapper.js.map

/***/ }),

/***/ "../../../../../src/app/app.module.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return AppModule; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_hammerjs__ = __webpack_require__("../../../../hammerjs/hammer.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_hammerjs___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_hammerjs__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_platform_browser__ = __webpack_require__("../../../platform-browser/@angular/platform-browser.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__angular_forms__ = __webpack_require__("../../../forms/@angular/forms.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__angular_http__ = __webpack_require__("../../../http/@angular/http.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__angular_flex_layout__ = __webpack_require__("../../../flex-layout/index.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__angular_platform_browser_animations__ = __webpack_require__("../../../platform-browser/@angular/platform-browser/animations.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7__angular_material__ = __webpack_require__("../../../material/esm5/material.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8__root_root_component__ = __webpack_require__("../../../../../src/app/root/root.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_9__main_main_component__ = __webpack_require__("../../../../../src/app/main/main.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_10__history_history_component__ = __webpack_require__("../../../../../src/app/history/history.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_11__api_domain_nodes_node_service__ = __webpack_require__("../../../../../src/api/domain/nodes/node.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_12_api_domain_history_history_service__ = __webpack_require__("../../../../../src/api/domain/history/history.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_13_api_domain_shared_mapper__ = __webpack_require__("../../../../../src/api/domain/shared/mapper.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_14_api_domain_purchase_orders_purchase_order_state__ = __webpack_require__("../../../../../src/api/domain/purchase-orders/purchase-order-state.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_15_api_domain_shared_corda__ = __webpack_require__("../../../../../src/api/domain/shared/corda.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_16_api_domain_purchase_orders_purchase_order_service__ = __webpack_require__("../../../../../src/api/domain/purchase-orders/purchase-order.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_17__delivery_information_delivery_information_component__ = __webpack_require__("../../../../../src/app/delivery-information/delivery-information.component.ts");
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
        var mapper = __WEBPACK_IMPORTED_MODULE_13_api_domain_shared_mapper__["a" /* Mapper */].getInstance();
        mapper.createMapConfiguration(Object, __WEBPACK_IMPORTED_MODULE_14_api_domain_purchase_orders_purchase_order_state__["a" /* PurchaseOrderState */], function (input) {
            return new __WEBPACK_IMPORTED_MODULE_14_api_domain_purchase_orders_purchase_order_state__["a" /* PurchaseOrderState */](new __WEBPACK_IMPORTED_MODULE_15_api_domain_shared_corda__["b" /* UniqueIdentifier */](input.linearId.externalId, input.linearId.id), __WEBPACK_IMPORTED_MODULE_15_api_domain_shared_corda__["a" /* CordaX500Name */].parse(input.owner), __WEBPACK_IMPORTED_MODULE_15_api_domain_shared_corda__["a" /* CordaX500Name */].parse(input.buyer), __WEBPACK_IMPORTED_MODULE_15_api_domain_shared_corda__["a" /* CordaX500Name */].parse(input.supplier), __WEBPACK_IMPORTED_MODULE_15_api_domain_shared_corda__["a" /* CordaX500Name */].parse(input.conductor), input.reference, input.amount, new Date(input.created * 1000), new Date(input.earliestShipment * 1000), new Date(input.latestShipment * 1000), input.portOfShipment, input.descriptionOfGoods, input.deliveryTerms);
        });
    };
    AppModule = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_2__angular_core__["L" /* NgModule */])({
            declarations: [
                __WEBPACK_IMPORTED_MODULE_8__root_root_component__["a" /* RootComponent */],
                __WEBPACK_IMPORTED_MODULE_9__main_main_component__["a" /* MainComponent */],
                __WEBPACK_IMPORTED_MODULE_10__history_history_component__["a" /* HistoryComponent */],
                __WEBPACK_IMPORTED_MODULE_17__delivery_information_delivery_information_component__["a" /* DeliveryInformationComponent */]
            ],
            imports: [
                __WEBPACK_IMPORTED_MODULE_5__angular_flex_layout__["FlexLayoutModule"],
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
            providers: [__WEBPACK_IMPORTED_MODULE_11__api_domain_nodes_node_service__["a" /* NodeService */], __WEBPACK_IMPORTED_MODULE_16_api_domain_purchase_orders_purchase_order_service__["a" /* PurchaseOrderService */], __WEBPACK_IMPORTED_MODULE_12_api_domain_history_history_service__["a" /* HistoryService */]],
            bootstrap: [__WEBPACK_IMPORTED_MODULE_8__root_root_component__["a" /* RootComponent */]],
            entryComponents: [__WEBPACK_IMPORTED_MODULE_17__delivery_information_delivery_information_component__["a" /* DeliveryInformationComponent */]]
        }),
        __metadata("design:paramtypes", [])
    ], AppModule);
    return AppModule;
}());

//# sourceMappingURL=app.module.js.map

/***/ }),

/***/ "../../../../../src/app/config/config.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return Config; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_http__ = __webpack_require__("../../../http/@angular/http.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_rxjs_Observable__ = __webpack_require__("../../../../rxjs/_esm5/Observable.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_rxjs_add_operator_catch__ = __webpack_require__("../../../../rxjs/_esm5/add/operator/catch.js");
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
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["B" /* Injectable */])(),
        __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_1__angular_http__["a" /* Http */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1__angular_http__["a" /* Http */]) === "function" && _a || Object])
    ], Config);
    return Config;
    var _a;
}());

//# sourceMappingURL=config.js.map

/***/ }),

/***/ "../../../../../src/app/delivery-information/delivery-information.component.html":
/***/ (function(module, exports) {

module.exports = "<table>\r\n  <tbody>\r\n    <tr>\r\n      <td><strong>Earliest Shipment</strong></td>\r\n      <td>{{data.earliestShipment | date:'short'}}</td>\r\n    </tr>\r\n    <tr>\r\n      <td><strong>Latest Shipment</strong></td>\r\n      <td>{{data.latestShipment | date:'short'}}</td>\r\n    </tr>\r\n    <tr>\r\n      <td><strong>Port of Shipment</strong></td>\r\n      <td>{{data.portOfShipment}}</td>\r\n    </tr>\r\n    <tr>\r\n      <td><strong>Description of Goods</strong></td>\r\n      <td>{{data.descriptionOfGoods}}</td>\r\n    </tr>\r\n    <tr>\r\n      <td><strong>Delivery Terms</strong></td>\r\n      <td>{{data.deliveryTerms}}</td>\r\n    </tr>\r\n  </tbody>\r\n</table>"

/***/ }),

/***/ "../../../../../src/app/delivery-information/delivery-information.component.scss":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/delivery-information/delivery-information.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return DeliveryInformationComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_material__ = __webpack_require__("../../../material/esm5/material.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_api_domain_purchase_orders_purchase_order_state__ = __webpack_require__("../../../../../src/api/domain/purchase-orders/purchase-order-state.ts");
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
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: "app-delivery-information",
            template: __webpack_require__("../../../../../src/app/delivery-information/delivery-information.component.html"),
            styles: [__webpack_require__("../../../../../src/app/delivery-information/delivery-information.component.scss")]
        }),
        __param(0, Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["A" /* Inject */])(__WEBPACK_IMPORTED_MODULE_1__angular_material__["a" /* MAT_DIALOG_DATA */])),
        __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_2_api_domain_purchase_orders_purchase_order_state__["a" /* PurchaseOrderState */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_2_api_domain_purchase_orders_purchase_order_state__["a" /* PurchaseOrderState */]) === "function" && _a || Object])
    ], DeliveryInformationComponent);
    return DeliveryInformationComponent;
    var _a;
}());

//# sourceMappingURL=delivery-information.component.js.map

/***/ }),

/***/ "../../../../../src/app/history/history.component.html":
/***/ (function(module, exports) {

module.exports = "<div *ngFor=\"let item of items; let i = index\">\n  <br />\n  <h4>Asset Version {{i + 1}}</h4>\n  <table>\n    <tbody>\n      <tr>\n        <td>\n          <strong>Owner</strong>\n        </td>\n        <td>{{item.owner.organizationName}}</td>\n      </tr>\n      <tr>\n        <td>\n          <strong>Buyer</strong>></td>\n        <td>{{item.buyer.organizationName}}</td>\n      </tr>\n      <tr>\n        <td>\n          <strong>Supplier</strong>\n        </td>\n        <td>{{item.supplier.organizationName}}</td>\n      </tr>\n      <tr>\n        <td>\n          <strong>Reference</strong>\n        </td>\n        <td>{{item.reference}}</td>\n      </tr>\n      <tr>\n        <td>\n          <strong>Created</strong>></td>\n        <td>{{item.created | date:'medium'}}</td>\n      </tr>\n      <tr>\n        <td>Amount</td>\n        <td>{{item.amount}}</td>\n      </tr>\n      <tr>\n        <td>\n          <strong>Earliest Shipment</strong>\n        </td>\n        <td>{{item.earliestShipment | date:'medium'}}</td>\n      </tr>\n      <tr>\n        <td>\n          <strong>Latest Shipment</strong>\n        </td>\n        <td>{{item.latestShipment | date:'medium'}}</td>\n      </tr>\n      <tr>\n        <td>\n          <strong>Port of Shipment</strong>\n        </td>\n        <td>{{item.portOfShipment}}</td>\n      </tr>\n      <tr>\n        <td>\n          <strong>Description of Goods</strong>\n        </td>\n        <td>{{item.descriptionOfGoods}}</td>\n      </tr>\n      <tr>\n        <td>\n          <strong>Delivery Terms</strong>\n        </td>\n        <td>{{item.deliveryTerms}}</td>\n      </tr>\n    </tbody>\n  </table>\n</div>"

/***/ }),

/***/ "../../../../../src/app/history/history.component.scss":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "h4 {\n  display: inline-block;\n  border: 1px solid white;\n  border-radius: 4px;\n  padding: 4px;\n  margin: 0 0 8px;\n  background: rgba(255, 255, 255, 0.1); }\n\ntable {\n  background: transparent;\n  border: none;\n  border-bottom: 1px solid rgba(255, 255, 255, 0.1); }\n  table tr {\n    border: none; }\n", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/history/history.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return HistoryComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_api_domain_shared_corda__ = __webpack_require__("../../../../../src/api/domain/shared/corda.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};


var HistoryComponent = /** @class */ (function () {
    function HistoryComponent() {
        this.items = [];
    }
    HistoryComponent.prototype.setItems = function (items) {
        this.items = items;
    };
    HistoryComponent.prototype.getName = function (value) {
        return __WEBPACK_IMPORTED_MODULE_1_api_domain_shared_corda__["a" /* CordaX500Name */].parse(value);
    };
    HistoryComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: "app-history",
            template: __webpack_require__("../../../../../src/app/history/history.component.html"),
            styles: [__webpack_require__("../../../../../src/app/history/history.component.scss")]
        })
    ], HistoryComponent);
    return HistoryComponent;
}());

//# sourceMappingURL=history.component.js.map

/***/ }),

/***/ "../../../../../src/app/main/main.component.html":
/***/ (function(module, exports) {

module.exports = "<mat-tab-group color=\"accent\">\r\n  <mat-tab label=\"Purchase Orders\">\r\n    <div [hidden]=\"visibleState !== 'full'\">\r\n      <mat-paginator #purchaseOrderPaginator (page)=\"onPage($event)\"></mat-paginator>\r\n      <div class=\"scroll-container\">\r\n        <table>\r\n          <thead>\r\n            <tr>\r\n              <th>ID</th>\r\n              <th>Owner</th>\r\n              <th>Buyer</th>\r\n              <th>Supplier</th>\r\n              <th>Reference</th>\r\n              <th>Created</th>\r\n              <th class=\"align-right\">Amount</th>\r\n              <th></th>\r\n            </tr>\r\n          </thead>\r\n          <tbody>\r\n            <ng-container *ngFor=\"let state of states\">\r\n              <tr>\r\n                <td>\r\n                  <a (click)=\"onOpenDrawer(state.linearId.externalId)\" href=\"#\">{{state.linearId.externalId}}</a>\r\n                </td>\r\n                <td>{{state.owner.organizationName}}</td>\r\n                <td>{{state.buyer.organizationName}}</td>\r\n                <td>{{state.supplier.organizationName}}</td>\r\n                <td>{{state.reference}}</td>\r\n                <td>{{state.created | date:'short'}}</td>\r\n                <td class=\"align-right\">{{state.amount}}</td>\r\n                <td class=\"align-right\"><button mat-button color=\"accent\" (click)=\"onOpenDialog(state.linearId.externalId)\">Delivery Info</button></td>\r\n              </tr>\r\n            </ng-container>\r\n          </tbody>\r\n        </table>\r\n      </div>\r\n    </div>\r\n    <div [hidden]=\"visibleState !== 'empty'\">\r\n      <h2 class=\"message\">Nothing to see here</h2>\r\n    </div>\r\n    <div [hidden]=\"visibleState !== 'loading'\">\r\n      <mat-progress-bar color=\"accent\" mode=\"indeterminate\"></mat-progress-bar>\r\n      <h2 class=\"message\">Loading</h2>\r\n    </div>\r\n  </mat-tab>\r\n  <!-- <mat-tab label=\"Purchase Orders\">\r\n    <mat-paginator [pageSize]=\"50\"></mat-paginator>\r\n  </mat-tab> -->\r\n</mat-tab-group>"

/***/ }),

/***/ "../../../../../src/app/main/main.component.scss":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "mat-paginator {\n  background: transparent; }\n\na {\n  color: #2BE7F8; }\n\n.scroll-container {\n  height: calc(100vh - 240px); }\n\n.message {\n  padding: 128px;\n  text-align: center;\n  color: rgba(255, 255, 255, 0.5); }\n", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/main/main.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return MainComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_api_domain_purchase_orders_purchase_order_service__ = __webpack_require__("../../../../../src/api/domain/purchase-orders/purchase-order.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__angular_material__ = __webpack_require__("../../../material/esm5/material.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_api_domain_history_history_service__ = __webpack_require__("../../../../../src/api/domain/history/history.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4_app_delivery_information_delivery_information_component__ = __webpack_require__("../../../../../src/app/delivery-information/delivery-information.component.ts");
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
    function MainComponent(purchaseOrderService, historyService, dialog) {
        this.purchaseOrderService = purchaseOrderService;
        this.historyService = historyService;
        this.dialog = dialog;
        this.states = null;
        this.name = null;
        this.hash = undefined;
        this.visibleState = "loading";
        this.pageNumber = 1;
        this.pageSize = 50;
        this.update();
    }
    MainComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.purchaseOrderPaginator.pageSize = this.pageSize;
        window.setInterval(function () { return _this.update(); }, 5000);
    };
    MainComponent.prototype.update = function (forceUpdate) {
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
    MainComponent.prototype.onPage = function (event) {
        var _this = this;
        window.clearTimeout(this.pageTimeout);
        this.pageTimeout = window.setTimeout(function () {
            _this.visibleState = "loading";
            _this.pageNumber = event.pageIndex + 1;
            _this.update(true);
        }, 1000);
    };
    MainComponent.prototype.onOpenDialog = function (externalId) {
        this.dialog.open(__WEBPACK_IMPORTED_MODULE_4_app_delivery_information_delivery_information_component__["a" /* DeliveryInformationComponent */], {
            width: "640px",
            data: this.states.find(function (state) { return state.linearId.externalId === externalId; })
        });
    };
    MainComponent.prototype.onOpenDrawer = function (externalId) {
        this.historyService.openDrawer(externalId);
    };
    __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_14" /* ViewChild */])("purchaseOrderPaginator"),
        __metadata("design:type", typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_2__angular_material__["n" /* MatPaginator */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_2__angular_material__["n" /* MatPaginator */]) === "function" && _a || Object)
    ], MainComponent.prototype, "purchaseOrderPaginator", void 0);
    MainComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: "app-main",
            template: __webpack_require__("../../../../../src/app/main/main.component.html"),
            styles: [__webpack_require__("../../../../../src/app/main/main.component.scss")]
        }),
        __metadata("design:paramtypes", [typeof (_b = typeof __WEBPACK_IMPORTED_MODULE_1_api_domain_purchase_orders_purchase_order_service__["a" /* PurchaseOrderService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1_api_domain_purchase_orders_purchase_order_service__["a" /* PurchaseOrderService */]) === "function" && _b || Object, typeof (_c = typeof __WEBPACK_IMPORTED_MODULE_3_api_domain_history_history_service__["a" /* HistoryService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_3_api_domain_history_history_service__["a" /* HistoryService */]) === "function" && _c || Object, typeof (_d = typeof __WEBPACK_IMPORTED_MODULE_2__angular_material__["e" /* MatDialog */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_2__angular_material__["e" /* MatDialog */]) === "function" && _d || Object])
    ], MainComponent);
    return MainComponent;
    var _a, _b, _c, _d;
}());

//# sourceMappingURL=main.component.js.map

/***/ }),

/***/ "../../../../../src/app/root/root.component.html":
/***/ (function(module, exports) {

module.exports = "<div fxFill fxLayout=\"column\">\r\n  <mat-toolbar>\r\n    <img class=\"logo\" src=\"/web/tix/assets/images/MarcoPolo.svg\" height=\"16\" />\r\n    <div fxFlex></div>\r\n    <span>{{nodeName?.organizationName}}</span>\r\n  </mat-toolbar>\r\n  <mat-drawer-container #drawerContainer fxFlex>\r\n    <mat-drawer mode=\"over\" position=\"end\" opened=\"false\">\r\n      <div fxLayout=\"row\">\r\n        <h1 fxFlex class=\"test\">Asset History</h1>\r\n        <button mat-icon-button (click)=\"drawerContainer.close()\">\r\n          <mat-icon>close</mat-icon>\r\n        </button>\r\n      </div>\r\n      <hr />\r\n      <div class=\"scroll-container\">\r\n        <app-history #history></app-history>\r\n      </div>\r\n    </mat-drawer>\r\n    <mat-drawer-content fxFlex fxLayout=\"column\">\r\n      <app-main fxFlex fxLayout=\"column\"></app-main>\r\n    </mat-drawer-content>\r\n  </mat-drawer-container>\r\n</div>"

/***/ }),

/***/ "../../../../../src/app/root/root.component.scss":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "mat-toolbar {\n  background: #243343;\n  border-bottom: 1px solid #616E79; }\n\nmat-drawer-container {\n  background: #35506b;\n  background: linear-gradient(to bottom, #35506b 0%, #1b304b 100%);\n  filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#35506b', endColorstr='#1b304b', GradientType=0); }\n  mat-drawer-container mat-drawer {\n    padding: 20px;\n    width: 640px;\n    background: #42617b;\n    background: linear-gradient(to bottom, #42617b 0%, #273e59 100%);\n    filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#42617b', endColorstr='#273e59', GradientType=0); }\n  mat-drawer-container mat-drawer-content {\n    overflow: hidden; }\n\napp-main {\n  box-sizing: border-box;\n  padding: 20px; }\n\n.scroll-container {\n  height: calc(100vh - 166px); }\n", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/root/root.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return RootComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_api_domain_nodes_node_service__ = __webpack_require__("../../../../../src/api/domain/nodes/node.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_api_domain_history_history_service__ = __webpack_require__("../../../../../src/api/domain/history/history.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__angular_material__ = __webpack_require__("../../../material/esm5/material.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4_app_history_history_component__ = __webpack_require__("../../../../../src/app/history/history.component.ts");
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
        this.historyService.setComponents(this.drawerContainer, this.history);
        window.setTimeout(function () { return _this.nodeService
            .getLocalNode()
            .subscribe(function (response) { return _this.nodeName = response; }); }, 2000);
    };
    __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_14" /* ViewChild */])("drawerContainer"),
        __metadata("design:type", typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_3__angular_material__["g" /* MatDrawerContainer */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_3__angular_material__["g" /* MatDrawerContainer */]) === "function" && _a || Object)
    ], RootComponent.prototype, "drawerContainer", void 0);
    __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_14" /* ViewChild */])("history"),
        __metadata("design:type", typeof (_b = typeof __WEBPACK_IMPORTED_MODULE_4_app_history_history_component__["a" /* HistoryComponent */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_4_app_history_history_component__["a" /* HistoryComponent */]) === "function" && _b || Object)
    ], RootComponent.prototype, "history", void 0);
    RootComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: "app-root",
            template: __webpack_require__("../../../../../src/app/root/root.component.html"),
            styles: [__webpack_require__("../../../../../src/app/root/root.component.scss")]
        }),
        __metadata("design:paramtypes", [typeof (_c = typeof __WEBPACK_IMPORTED_MODULE_1_api_domain_nodes_node_service__["a" /* NodeService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1_api_domain_nodes_node_service__["a" /* NodeService */]) === "function" && _c || Object, typeof (_d = typeof __WEBPACK_IMPORTED_MODULE_2_api_domain_history_history_service__["a" /* HistoryService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_2_api_domain_history_history_service__["a" /* HistoryService */]) === "function" && _d || Object])
    ], RootComponent);
    return RootComponent;
    var _a, _b, _c, _d;
}());

//# sourceMappingURL=root.component.js.map

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
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_21" /* enableProdMode */])();
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