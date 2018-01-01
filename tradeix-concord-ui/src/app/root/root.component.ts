import { Component, OnInit, ViewChild } from "@angular/core";
import { NodeService } from "api/domain/nodes/node.service";
import { CordaX500Name } from "api/domain/shared/corda";
import { HistoryService } from "api/domain/history/history.service";
import { MatDrawerContainer, MatDrawer } from "@angular/material";
import { HistoryComponent } from "app/history/history.component";

@Component({
  selector: "app-root",
  templateUrl: "./root.component.html",
  styleUrls: ["./root.component.scss"]
})
export class RootComponent implements OnInit {
  @ViewChild("drawerContainer") drawerContainer: MatDrawerContainer;
  @ViewChild("history") history: HistoryComponent;

  public nodeName: CordaX500Name = null;

  constructor(
    private readonly nodeService: NodeService,
    private readonly historyService: HistoryService) {
  }

  public ngOnInit(): void {
    this.historyService.setComponents(this.drawerContainer, this.history);

    (window as any).setTimeout(() => this.nodeService
      .getLocalNode()
      .subscribe(response => this.nodeName = response), 2000);
  }
}
