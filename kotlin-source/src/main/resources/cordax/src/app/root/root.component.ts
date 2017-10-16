import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NodeService } from '../../api/domain/services/node.service';
import { X500Name } from '../../api/domain/x-500-name';

@Component({
  selector: 'app-root',
  templateUrl: './root.component.html',
  styleUrls: ['./root.component.scss']
})
export class RootComponent implements OnInit {
  private localNode: X500Name;

  constructor(
    private readonly nodeService: NodeService,
    private readonly router: Router) {
  }

  ngOnInit() {
    this.nodeService
      .getLocalNode()
      .subscribe(node => this.localNode = node);
  }
}
