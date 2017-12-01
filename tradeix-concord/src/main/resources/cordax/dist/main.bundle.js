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
        this.useOrigin = true;
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
            .get(this.createUrl('nodes/local'))
            .map(function (response) { return __WEBPACK_IMPORTED_MODULE_2__shared_corda__["a" /* CordaX500Name */].parse(response.json().localNode); });
    };
    NodeService.prototype.getAllNodes = function () {
        return this.http
            .get(this.createUrl('nodes/all'))
            .map(function (response) { return response.json().nodes
            .map(function (name) { return __WEBPACK_IMPORTED_MODULE_2__shared_corda__["a" /* CordaX500Name */].parse(name); }); });
    };
    NodeService.prototype.getPeerNodes = function () {
        return this.http
            .get(this.createUrl('nodes/peers'))
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
    TradeAssetService.prototype.getAllTradeAssets = function () {
        return this.http
            .get(this.createUrl("tradeassets/all"))
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
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__angular_flex_layout__ = __webpack_require__("../../../flex-layout/esm5/flex-layout.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__angular_platform_browser_animations__ = __webpack_require__("../../../platform-browser/@angular/platform-browser/animations.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7__angular_material__ = __webpack_require__("../../../material/esm5/material.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8__root_root_component__ = __webpack_require__("../../../../../src/app/root/root.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_9__main_main_component__ = __webpack_require__("../../../../../src/app/main/main.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_10__api_domain_nodes_node_service__ = __webpack_require__("../../../../../src/api/domain/nodes/node.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_11_api_domain_trade_assets_trade_asset_service__ = __webpack_require__("../../../../../src/api/domain/trade-assets/trade-asset.service.ts");
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
                __WEBPACK_IMPORTED_MODULE_9__main_main_component__["a" /* MainComponent */]
            ],
            imports: [
                __WEBPACK_IMPORTED_MODULE_5__angular_flex_layout__["a" /* FlexLayoutModule */],
                __WEBPACK_IMPORTED_MODULE_6__angular_platform_browser_animations__["a" /* BrowserAnimationsModule */],
                __WEBPACK_IMPORTED_MODULE_1__angular_platform_browser__["a" /* BrowserModule */],
                __WEBPACK_IMPORTED_MODULE_3__angular_forms__["c" /* FormsModule */],
                __WEBPACK_IMPORTED_MODULE_4__angular_http__["b" /* HttpModule */],
                __WEBPACK_IMPORTED_MODULE_7__angular_material__["n" /* MatToolbarModule */],
                __WEBPACK_IMPORTED_MODULE_7__angular_material__["m" /* MatSidenavModule */],
                __WEBPACK_IMPORTED_MODULE_7__angular_material__["f" /* MatIconModule */],
                __WEBPACK_IMPORTED_MODULE_7__angular_material__["a" /* MatButtonModule */],
                __WEBPACK_IMPORTED_MODULE_7__angular_material__["i" /* MatMenuModule */],
                __WEBPACK_IMPORTED_MODULE_7__angular_material__["h" /* MatListModule */],
                __WEBPACK_IMPORTED_MODULE_7__angular_material__["b" /* MatCardModule */],
                __WEBPACK_IMPORTED_MODULE_7__angular_material__["d" /* MatDialogModule */],
                __WEBPACK_IMPORTED_MODULE_7__angular_material__["l" /* MatSelectModule */],
                __WEBPACK_IMPORTED_MODULE_7__angular_material__["j" /* MatOptionModule */],
                __WEBPACK_IMPORTED_MODULE_7__angular_material__["g" /* MatInputModule */],
                __WEBPACK_IMPORTED_MODULE_7__angular_material__["e" /* MatFormFieldModule */],
                __WEBPACK_IMPORTED_MODULE_7__angular_material__["k" /* MatProgressSpinnerModule */],
                __WEBPACK_IMPORTED_MODULE_7__angular_material__["c" /* MatChipsModule */]
            ],
            providers: [__WEBPACK_IMPORTED_MODULE_10__api_domain_nodes_node_service__["a" /* NodeService */], __WEBPACK_IMPORTED_MODULE_11_api_domain_trade_assets_trade_asset_service__["a" /* TradeAssetService */]],
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

/***/ "../../../../../src/app/main/main.component.html":
/***/ (function(module, exports) {

module.exports = "<div fxLayout=\"column\">\r\n  <div class=\"app-padding\" fxFlex>\r\n    <h2>Trade Assets</h2>\r\n    <h4>Participating as {{name?.organizationName}}</h4>\r\n  </div>\r\n  <div class=\"app-padding\" fxFlex>\r\n    <table>\r\n      <thead>\r\n        <tr>\r\n          <th>ID</th>\r\n          <th>Buyer</th>\r\n          <th>Supplier</th>\r\n          <th>Owner</th>\r\n          <th>Status</th>\r\n          <th class=\"align-right\">Value</th>\r\n        </tr>\r\n      </thead>\r\n      <tbody>\r\n        <ng-container *ngFor=\"let state of states\">\r\n          <tr>\r\n            <td>{{state.linearId.externalId}}</td>\r\n            <td>{{getName(state.buyer).organizationName}}</td>\r\n            <td>{{getName(state.supplier).organizationName}}</td>\r\n            <td>{{getName(state.owner).organizationName}}</td>\r\n            <td>{{state.tradeAsset.status}}</td>\r\n            <td class=\"align-right\">{{state.tradeAsset.amount}}</td>\r\n          </tr>\r\n        </ng-container>\r\n      </tbody>\r\n    </table>\r\n  </div>\r\n</div>"

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
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_api_domain_shared_corda__ = __webpack_require__("../../../../../src/api/domain/shared/corda.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_api_domain_nodes_node_service__ = __webpack_require__("../../../../../src/api/domain/nodes/node.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_api_domain_trade_assets_trade_asset_service__ = __webpack_require__("../../../../../src/api/domain/trade-assets/trade-asset.service.ts");
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
    function MainComponent(nodeService, tradeAssetService) {
        this.nodeService = nodeService;
        this.tradeAssetService = tradeAssetService;
        this.states = [];
        this.name = null;
        this.update();
    }
    MainComponent.prototype.ngOnInit = function () {
        var _this = this;
        window
            .setInterval(function () { return _this.update(); }, 2000);
    };
    MainComponent.prototype.update = function () {
        var _this = this;
        try {
            this.nodeService
                .getLocalNode()
                .subscribe(function (response) { return _this.name = response; });
            this.tradeAssetService
                .getAllTradeAssets()
                .subscribe(function (response) { return _this.states = response.map(function (o) { return o.state.data; }); });
        }
        catch (e) {
            console.log(e);
        }
    };
    MainComponent.prototype.getName = function (value) {
        return __WEBPACK_IMPORTED_MODULE_1_api_domain_shared_corda__["a" /* CordaX500Name */].parse(value);
    };
    MainComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: "app-main",
            template: __webpack_require__("../../../../../src/app/main/main.component.html"),
            styles: [__webpack_require__("../../../../../src/app/main/main.component.scss")]
        }),
        __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_2_api_domain_nodes_node_service__["a" /* NodeService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_2_api_domain_nodes_node_service__["a" /* NodeService */]) === "function" && _a || Object, typeof (_b = typeof __WEBPACK_IMPORTED_MODULE_3_api_domain_trade_assets_trade_asset_service__["a" /* TradeAssetService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_3_api_domain_trade_assets_trade_asset_service__["a" /* TradeAssetService */]) === "function" && _b || Object])
    ], MainComponent);
    return MainComponent;
    var _a, _b;
}());

//# sourceMappingURL=main.component.js.map

/***/ }),

/***/ "../../../../../src/app/root/root.component.html":
/***/ (function(module, exports) {

module.exports = "<div fxFill fxLayout=\"column\">\r\n  <mat-toolbar>\r\n    <img class=\"logo\" src=\"/web/tix/assets/images/MarcoPolo.svg\" height=\"20\" />\r\n    <div fxFlex></div>\r\n  </mat-toolbar>\r\n  <mat-drawer-container fxFlex>\r\n    <app-main></app-main>\r\n  </mat-drawer-container>\r\n</div>"

/***/ }),

/***/ "../../../../../src/app/root/root.component.scss":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "mat-drawer {\n  padding: 20px; }\n\na.mat-button {\n  display: block;\n  margin-bottom: 10px;\n  text-align: left;\n  min-width: 240px; }\n\n.space-right {\n  display: inline-block;\n  margin-right: 20px; }\n", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/root/root.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return RootComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};

var RootComponent = /** @class */ (function () {
    function RootComponent() {
    }
    RootComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: "app-root",
            template: __webpack_require__("../../../../../src/app/root/root.component.html"),
            styles: [__webpack_require__("../../../../../src/app/root/root.component.scss")]
        })
    ], RootComponent);
    return RootComponent;
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