import { Component, OnInit, ViewChild, ElementRef } from "@angular/core";
import { StateAndRef, CordaX500Name } from "api/domain/shared/corda";
import { InvoiceState } from "api/domain/invoices/invoice-state";
import { NodeService } from "api/domain/nodes/node.service";
import { InvoicesService } from "api/domain/invoices/invoices.service";
import { MatPaginator, MatDialog } from "@angular/material";
import { HistoryService } from "api/domain/history/history.service";
import { InvoiceInformationComponent } from "app/invoice-information/invoice-information.component";

@Component({
  selector: "app-invoice-table",
  templateUrl: "./invoice-table.component.html",
  styleUrls: ["./invoice-table.component.scss"]
})
export class InvoiceTableComponent implements OnInit {

  @ViewChild("invoicePaginator")
  private invoicePaginator: MatPaginator;

  public states: InvoiceState[] = null;
  public visibleState: string = "loading";
  private hash: string = undefined;
  private pageNumber: number = 1;
  private pageSize: number = 50;
  private pageTimeout: number;

  constructor(
    private readonly invoicesService: InvoicesService,
    private readonly historyService: HistoryService,
    private readonly dialog: MatDialog) {
    this.update();
  }

  public ngOnInit(): void {
    this.invoicePaginator.pageSize = this.pageSize;
    window.setInterval(() => this.update(), 5000);
  }

  public update(forceUpdate: boolean = false): void {
    try {
      this.invoicesService
        .getMostRecentInvoiceHash()
        .subscribe(hash => {
          if (hash !== this.hash) {
            this.hash = hash;
            forceUpdate = true;
          }

          if (forceUpdate) {
            this.invoicesService
              .getInvoiceCount()
              .subscribe(count => {
                this.invoicePaginator.length = count;

                if (count === 0) {
                  this.visibleState = "empty";
                } else {
                  this.visibleState = "full";
                  this.invoicesService
                    .getAllInvoices(this.pageNumber, this.pageSize)
                    .subscribe(assets => this.states = assets);
                }
              });
          }
        });
    } catch (e) {
      console.error(e);
    }
  }

  public onPage(event: any): void {
    window.clearTimeout(this.pageTimeout);
    this.pageTimeout = window.setTimeout(() => {
      this.visibleState = "loading";
      this.pageNumber = event.pageIndex + 1;
      this.update(true);
    }, 1000);
  }

  public onOpenDialog(externalId: string): void {
    this.dialog.open(InvoiceInformationComponent, {
      position: { top: "64px" },
      width: "720px",
      maxHeight: "400px",
      data: this.states.find(state => state.linearId.externalId === externalId)
    });
  }

  public onOpenDrawer(externalId: string): void {
    this.historyService.openDrawer(externalId, "INVOICE");
  }
}
