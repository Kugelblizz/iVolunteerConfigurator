import { Component, OnInit } from '@angular/core';
import { GlobalInfo } from 'app/main/content/_model/global-info';
import { ClassDefinitionService } from 'app/main/content/_service/meta/core/class/class-definition.service';

@Component({
  selector: "app-configurator",
  templateUrl: './configurator.component.html',
  styleUrls: ['./configurator.component.scss']
})
export class ConfiguratorComponent implements OnInit {
  globalInfo: GlobalInfo;
  loaded = false;

  constructor() { }

  async ngOnInit() {

    this.loaded = true;
  }

  navigateBack() {
    window.history.back();
  }
}
