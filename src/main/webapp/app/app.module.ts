import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { PdashSharedModule } from 'app/shared/shared.module';
import { PdashCoreModule } from 'app/core/core.module';
import { PdashAppRoutingModule } from './app-routing.module';
import { PdashHomeModule } from './home/home.module';
import { PdashEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    PdashSharedModule,
    PdashCoreModule,
    PdashHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    PdashEntityModule,
    PdashAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
  bootstrap: [MainComponent],
})
export class PdashAppModule {}
