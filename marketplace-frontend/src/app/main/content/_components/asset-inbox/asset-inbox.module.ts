import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FuseSharedModule } from "@fuse/shared.module";
import { MatButtonModule } from '@angular/material/button';
import { MatCommonModule } from '@angular/material/core';
import { MatIconModule } from '@angular/material/icon';
import { AssetInboxComponent } from './asset-inbox.component';
import { MatTableModule } from '@angular/material';


@NgModule({
  imports: [
    CommonModule, FuseSharedModule, MatCommonModule, MatButtonModule, MatIconModule, MatTableModule,
  ],
  declarations: [AssetInboxComponent],
  exports: [AssetInboxComponent]
  
})
export class AssetInboxModule { }