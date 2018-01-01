import "hammerjs";

import { BrowserModule } from "@angular/platform-browser";
import { NgModule } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { HttpModule } from "@angular/http";
import { RouterModule, Routes } from "@angular/router";
import { FlexLayoutModule } from "@angular/flex-layout";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import {
  MatToolbarModule,
  MatSidenavModule,
  MatIconModule,
  MatButtonModule,
  MatMenuModule,
  MatListModule,
  MatCardModule,
  MatDialogModule,
  MatSelectModule,
  MatOptionModule,
  MatInputModule,
  MatFormFieldModule,
  MatProgressSpinnerModule,
  MatChipsModule,
  MatTabsModule,
  MatPaginatorModule,
  MatProgressBarModule
} from "@angular/material";

import { RootComponent } from "./root/root.component";
import { MainComponent } from "./main/main.component";

import { NodeService } from "../api/domain/nodes/node.service";
import { TradeAssetService } from "api/domain/trade-assets/trade-asset.service";
import { HistoryService } from "api/domain/history/history.service";
import { HistoryComponent } from "./history/history.component";

@NgModule({
  declarations: [
    RootComponent,
    MainComponent,
    HistoryComponent
  ],
  imports: [
    FlexLayoutModule,
    BrowserAnimationsModule,
    BrowserModule,
    FormsModule,
    HttpModule,
    MatToolbarModule,
    MatSidenavModule,
    MatIconModule,
    MatButtonModule,
    MatMenuModule,
    MatListModule,
    MatCardModule,
    MatDialogModule,
    MatSelectModule,
    MatOptionModule,
    MatInputModule,
    MatFormFieldModule,
    MatProgressSpinnerModule,
    MatChipsModule,
    MatTabsModule,
    MatPaginatorModule,
    MatProgressBarModule
  ],
  providers: [NodeService, TradeAssetService, HistoryService],
  bootstrap: [RootComponent],
  entryComponents: []
})
export class AppModule { }
