import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatChipsModule } from '@angular/material/chips';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatToolbarModule } from '@angular/material/toolbar';

import {FuseSharedModule} from '@fuse/shared.module';

import {FuseToolbarComponent} from 'app/main/toolbar/toolbar.component';
import {FuseSearchBarModule, FuseShortcutsModule} from '@fuse/components';
import {FuseUserMenuComponent} from './user-menu/user-menu.component';
import {FuseMarketplaceSelectionComponent} from './marketplace-selection/marketplace-selection.component';
import { MatBadgeModule } from '@angular/material';
import { InboxOverlayModule } from './inbox-overlay/inbox-overlay.module';

@NgModule({
  declarations: [
    FuseToolbarComponent,
    FuseUserMenuComponent,
    FuseMarketplaceSelectionComponent
  ],
  imports: [
    RouterModule,

    MatButtonModule,
    MatChipsModule,
    MatIconModule,
    MatMenuModule,
    MatProgressBarModule,
    MatToolbarModule,

    MatBadgeModule,

    InboxOverlayModule,

    FuseSharedModule,
    FuseSearchBarModule,
    FuseShortcutsModule
  ],
  exports: [
    FuseToolbarComponent
  ]
})
export class FuseToolbarModule {
}
