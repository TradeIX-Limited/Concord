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
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_api_domain_trade_assets_trade_asset_service__ = __webpack_require__("../../../../../src/api/domain/trade-assets/trade-asset.service.ts");
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
            .getTradeAsset(externalId)
            .subscribe(function (response) {
            _this.historyComponent.setItems(response);
        });
    };
    HistoryService = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["B" /* Injectable */])(),
        __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_1_api_domain_trade_assets_trade_asset_service__["a" /* TradeAssetService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1_api_domain_trade_assets_trade_asset_service__["a" /* TradeAssetService */]) === "function" && _a || Object])
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

/***/ "../../../../../src/api/domain/shared/corda.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* unused harmony export StateRef */
/* unused harmony export State */
/* unused harmony export StateAndRef */
/* unused harmony export UniqueIdentifier */
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

/***/ "../../../../../src/api/domain/trade-assets/trade-asset.service.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return TradeAssetService; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__base_service__ = __webpack_require__("../../../../../src/api/domain/base.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_rxjs_add_operator_map__ = __webpack_require__("../../../../rxjs/_esm5/add/operator/map.js");
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
    TradeAssetService.prototype.getTradeAsset = function (externalId) {
        return this.http
            .get(this.createUrl("tradeassets?externalId=" + externalId))
            .map(function (response) { return response.json(); });
    };
    TradeAssetService.prototype.getTradeAssetCount = function () {
        return this.http
            .get(this.createUrl("tradeassets/count"))
            .map(function (response) { return response.json().count; });
    };
    TradeAssetService.prototype.getLatestTradeAssetHash = function () {
        return this.http
            .get(this.createUrl("tradeassets/hash"))
            .map(function (response) { return response.json().hash; });
    };
    TradeAssetService.prototype.getAllTradeAssets = function (pageNumber, pageSize) {
        return this.http
            .get(this.createUrl("tradeassets/all?page=" + pageNumber + "&count=" + pageSize))
            .map(function (response) { return response.json(); });
    };
    TradeAssetService = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["B" /* Injectable */])()
    ], TradeAssetService);
    return TradeAssetService;
}(__WEBPACK_IMPORTED_MODULE_1__base_service__["a" /* BaseService */]));

//# sourceMappingURL=trade-asset.service.js.map

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
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_10__api_domain_nodes_node_service__ = __webpack_require__("../../../../../src/api/domain/nodes/node.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_11_api_domain_trade_assets_trade_asset_service__ = __webpack_require__("../../../../../src/api/domain/trade-assets/trade-asset.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_12_api_domain_history_history_service__ = __webpack_require__("../../../../../src/api/domain/history/history.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_13__history_history_component__ = __webpack_require__("../../../../../src/app/history/history.component.ts");
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
        Object(__WEBPACK_IMPORTED_MODULE_2__angular_core__["L" /* NgModule */])({
            declarations: [
                __WEBPACK_IMPORTED_MODULE_8__root_root_component__["a" /* RootComponent */],
                __WEBPACK_IMPORTED_MODULE_9__main_main_component__["a" /* MainComponent */],
                __WEBPACK_IMPORTED_MODULE_13__history_history_component__["a" /* HistoryComponent */]
            ],
            imports: [
                __WEBPACK_IMPORTED_MODULE_5__angular_flex_layout__["FlexLayoutModule"],
                __WEBPACK_IMPORTED_MODULE_6__angular_platform_browser_animations__["a" /* BrowserAnimationsModule */],
                __WEBPACK_IMPORTED_MODULE_1__angular_platform_browser__["a" /* BrowserModule */],
                __WEBPACK_IMPORTED_MODULE_3__angular_forms__["c" /* FormsModule */],
                __WEBPACK_IMPORTED_MODULE_4__angular_http__["b" /* HttpModule */],
                __WEBPACK_IMPORTED_MODULE_7__angular_material__["s" /* MatToolbarModule */],
                __WEBPACK_IMPORTED_MODULE_7__angular_material__["q" /* MatSidenavModule */],
                __WEBPACK_IMPORTED_MODULE_7__angular_material__["g" /* MatIconModule */],
                __WEBPACK_IMPORTED_MODULE_7__angular_material__["a" /* MatButtonModule */],
                __WEBPACK_IMPORTED_MODULE_7__angular_material__["j" /* MatMenuModule */],
                __WEBPACK_IMPORTED_MODULE_7__angular_material__["i" /* MatListModule */],
                __WEBPACK_IMPORTED_MODULE_7__angular_material__["b" /* MatCardModule */],
                __WEBPACK_IMPORTED_MODULE_7__angular_material__["d" /* MatDialogModule */],
                __WEBPACK_IMPORTED_MODULE_7__angular_material__["p" /* MatSelectModule */],
                __WEBPACK_IMPORTED_MODULE_7__angular_material__["k" /* MatOptionModule */],
                __WEBPACK_IMPORTED_MODULE_7__angular_material__["h" /* MatInputModule */],
                __WEBPACK_IMPORTED_MODULE_7__angular_material__["f" /* MatFormFieldModule */],
                __WEBPACK_IMPORTED_MODULE_7__angular_material__["o" /* MatProgressSpinnerModule */],
                __WEBPACK_IMPORTED_MODULE_7__angular_material__["c" /* MatChipsModule */],
                __WEBPACK_IMPORTED_MODULE_7__angular_material__["r" /* MatTabsModule */],
                __WEBPACK_IMPORTED_MODULE_7__angular_material__["m" /* MatPaginatorModule */],
                __WEBPACK_IMPORTED_MODULE_7__angular_material__["n" /* MatProgressBarModule */]
            ],
            providers: [__WEBPACK_IMPORTED_MODULE_10__api_domain_nodes_node_service__["a" /* NodeService */], __WEBPACK_IMPORTED_MODULE_11_api_domain_trade_assets_trade_asset_service__["a" /* TradeAssetService */], __WEBPACK_IMPORTED_MODULE_12_api_domain_history_history_service__["a" /* HistoryService */]],
            bootstrap: [__WEBPACK_IMPORTED_MODULE_8__root_root_component__["a" /* RootComponent */]],
            entryComponents: []
        })
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

/***/ "../../../../../src/app/history/history.component.html":
/***/ (function(module, exports) {

module.exports = "<div *ngFor=\"let item of items; let i = index\">\n  <br />\n  <table>\n    <thead>\n      <tr>\n        <th colspan=\"2\">Asset Version {{i + 1}}</th>\n      </tr>\n      <tr>\n        <th>Key</th>\n        <th>Value</th>\n      </tr>\n    </thead>\n    <tbody>\n      <tr>\n        <td>Buyer</td>\n        <td>{{getName(item.state.data.buyer).organizationName}}</td>\n      </tr>\n      <tr>\n        <td>Supplier</td>\n        <td>{{getName(item.state.data.supplier).organizationName}}</td>\n      </tr>\n      <tr>\n        <td>Owner</td>\n        <td>{{getName(item.state.data.owner).organizationName}}</td>\n      </tr>\n      <tr>\n        <td>Status</td>\n        <td>{{item.state.data.tradeAsset.status}}</td>\n      </tr>\n      <tr>\n        <td>Amount</td>\n        <td>{{item.state.data.tradeAsset.amount}}</td>\n      </tr>\n    </tbody>\n  </table>\n</div>"

/***/ }),

/***/ "../../../../../src/app/history/history.component.scss":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "", ""]);

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

module.exports = "<mat-tab-group color=\"accent\">\r\n  <mat-tab label=\"Invoices\">\r\n    <div [hidden]=\"visibleState !== 'full'\">\r\n      <mat-paginator #invoicePaginator (page)=\"onPage($event)\"></mat-paginator>\r\n      <div class=\"scroll-container\">\r\n        <table>\r\n          <thead>\r\n            <tr>\r\n              <th>ID</th>\r\n              <th>Buyer</th>\r\n              <th>Supplier</th>\r\n              <th>Owner</th>\r\n              <th>Status</th>\r\n              <th class=\"align-right\">Amount</th>\r\n            </tr>\r\n          </thead>\r\n          <tbody>\r\n            <ng-container *ngFor=\"let state of states\">\r\n              <tr>\r\n                <td>\r\n                  <a (click)=\"onOpenDrawer(state.linearId.externalId)\" href=\"#\">{{state.linearId.externalId}}</a>\r\n                </td>\r\n                <td>{{getName(state.buyer).organizationName}}</td>\r\n                <td>{{getName(state.supplier).organizationName}}</td>\r\n                <td>{{getName(state.owner).organizationName}}</td>\r\n                <td>{{state.tradeAsset.status}}</td>\r\n                <td class=\"align-right\">{{state.tradeAsset.amount}}</td>\r\n              </tr>\r\n            </ng-container>\r\n          </tbody>\r\n        </table>\r\n      </div>\r\n    </div>\r\n    <div [hidden]=\"visibleState !== 'empty'\">\r\n      <h2 class=\"message\">Nothing to see here</h2>\r\n    </div>\r\n    <div [hidden]=\"visibleState !== 'loading'\">\r\n      <mat-progress-bar color=\"primary\" mode=\"indeterminate\"></mat-progress-bar>\r\n      <h2 class=\"message\">Loading</h2>\r\n    </div>\r\n  </mat-tab>\r\n  <mat-tab label=\"Purchase Orders\">\r\n    <mat-paginator [pageSize]=\"50\"></mat-paginator>\r\n  </mat-tab>\r\n</mat-tab-group>"

/***/ }),

/***/ "../../../../../src/app/main/main.component.scss":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "mat-paginator {\n  background: rgba(0, 0, 0, 0.2); }\n\na {\n  color: #18FFFF; }\n\n.scroll-container {\n  height: calc(100vh - 240px); }\n\n.message {\n  padding: 128px;\n  text-align: center;\n  color: rgba(255, 255, 255, 0.5); }\n", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/main/main.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return MainComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_api_domain_shared_corda__ = __webpack_require__("../../../../../src/api/domain/shared/corda.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_api_domain_trade_assets_trade_asset_service__ = __webpack_require__("../../../../../src/api/domain/trade-assets/trade-asset.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__angular_material__ = __webpack_require__("../../../material/esm5/material.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4_api_domain_history_history_service__ = __webpack_require__("../../../../../src/api/domain/history/history.service.ts");
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
    function MainComponent(tradeAssetService, historyService) {
        this.tradeAssetService = tradeAssetService;
        this.historyService = historyService;
        this.states = null;
        this.name = null;
        this.pageNumber = 1;
        this.pageSize = 50;
        this.hash = undefined;
        this.visibleState = "loading";
        this.update();
    }
    MainComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.invoicePaginator.pageSize = this.pageSize;
        window
            .setInterval(function () { return _this.update(); }, 5000);
    };
    MainComponent.prototype.update = function (forceUpdate) {
        var _this = this;
        if (forceUpdate === void 0) { forceUpdate = false; }
        try {
            this.tradeAssetService.getLatestTradeAssetHash().subscribe(function (hash) {
                if (hash !== _this.hash) {
                    _this.hash = hash;
                    forceUpdate = true;
                }
                if (forceUpdate) {
                    _this.tradeAssetService.getTradeAssetCount().subscribe(function (count) {
                        _this.invoicePaginator.length = count;
                        if (count === 0) {
                            _this.visibleState = "empty";
                        }
                        else {
                            _this.visibleState = "full";
                            _this.tradeAssetService
                                .getAllTradeAssets(_this.pageNumber, _this.pageSize)
                                .subscribe(function (assets) { return _this.states = assets.map(function (o) { return o.state.data; }); });
                        }
                    });
                }
            });
        }
        catch (e) { }
    };
    MainComponent.prototype.getName = function (value) {
        return __WEBPACK_IMPORTED_MODULE_1_api_domain_shared_corda__["a" /* CordaX500Name */].parse(value);
    };
    MainComponent.prototype.onPage = function (event) {
        var _this = this;
        clearInterval(this.pageInterval);
        this.pageInterval = window.setInterval(function () {
            _this.pageNumber = event.pageIndex + 1;
            _this.update(true);
        }, 1000);
    };
    MainComponent.prototype.onOpenDrawer = function (externalId) {
        this.historyService.openDrawer(externalId);
    };
    __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_14" /* ViewChild */])("invoicePaginator"),
        __metadata("design:type", typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_3__angular_material__["l" /* MatPaginator */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_3__angular_material__["l" /* MatPaginator */]) === "function" && _a || Object)
    ], MainComponent.prototype, "invoicePaginator", void 0);
    MainComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: "app-main",
            template: __webpack_require__("../../../../../src/app/main/main.component.html"),
            styles: [__webpack_require__("../../../../../src/app/main/main.component.scss")]
        }),
        __metadata("design:paramtypes", [typeof (_b = typeof __WEBPACK_IMPORTED_MODULE_2_api_domain_trade_assets_trade_asset_service__["a" /* TradeAssetService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_2_api_domain_trade_assets_trade_asset_service__["a" /* TradeAssetService */]) === "function" && _b || Object, typeof (_c = typeof __WEBPACK_IMPORTED_MODULE_4_api_domain_history_history_service__["a" /* HistoryService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_4_api_domain_history_history_service__["a" /* HistoryService */]) === "function" && _c || Object])
    ], MainComponent);
    return MainComponent;
    var _a, _b, _c;
}());

//# sourceMappingURL=main.component.js.map

/***/ }),

/***/ "../../../../../src/app/root/root.component.html":
/***/ (function(module, exports) {

module.exports = "<div fxFill fxLayout=\"column\">\r\n  <mat-toolbar color=\"primary\">\r\n    <img class=\"logo\" src=\"/web/tix/assets/images/MarcoPolo.svg\" height=\"16\" />\r\n    <div fxFlex></div>\r\n    <span>Participating as {{nodeName?.organizationName}}</span>\r\n  </mat-toolbar>\r\n  <mat-drawer-container #drawerContainer fxFlex>\r\n    <mat-drawer mode=\"over\" position=\"end\" opened=\"false\">\r\n      <div fxLayout=\"row\">\r\n        <h1 fxFlex class=\"test\">Asset History</h1>\r\n        <button mat-icon-button color=\"accent\" (click)=\"drawerContainer.close()\">\r\n          <mat-icon>close</mat-icon>\r\n        </button>\r\n      </div>\r\n      <hr />\r\n      <div class=\"scroll-container\">\r\n        <app-history #history></app-history>\r\n      </div>\r\n    </mat-drawer>\r\n    <mat-drawer-content fxFlex fxLayout=\"column\">\r\n      <mat-card fxFlex fxLayout=\"column\">\r\n        <app-main fxFlex fxLayout=\"column\"></app-main>\r\n      </mat-card>\r\n    </mat-drawer-content>\r\n  </mat-drawer-container>\r\n</div>"

/***/ }),

/***/ "../../../../../src/app/root/root.component.scss":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "mat-drawer-container {\n  background: url(" + __webpack_require__("../../../../../src/assets/images/wallpaper.png") + ") fixed center center;\n  padding: 20px; }\n  mat-drawer-container mat-drawer {\n    padding: 20px;\n    width: 640px;\n    background: rgba(0, 0, 0, 0.75); }\n  mat-drawer-container mat-drawer-content {\n    overflow: hidden; }\n\nmat-card {\n  background: rgba(255, 255, 255, 0.1);\n  padding: 32px 0px 4px; }\n\n.scroll-container {\n  height: calc(100vh - 166px); }\n", ""]);

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
        __metadata("design:type", typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_3__angular_material__["e" /* MatDrawerContainer */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_3__angular_material__["e" /* MatDrawerContainer */]) === "function" && _a || Object)
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

/***/ "../../../../../src/assets/images/wallpaper.png":
/***/ (function(module, exports, __webpack_require__) {

module.exports = __webpack_require__.p + "wallpaper.1a4e06d67717beee0580.png";

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