import { BrowserModule } from '@angular/platform-browser';
import { NgModule, LOCALE_ID } from '@angular/core';

import { AppComponent } from './app.component';

import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { AuthService } from './services/auth-service';
import {HttpClientModule} from '@angular/common/http';
import {AccountService} from './services/account-service';
import { ProfileComponent } from './components/profile/profile.component';
import {routing} from './app.routing';
import {UrlPermission} from './permission/permission';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatSelectModule} from '@angular/material/select';
import { TaskComponent } from './components/task/task.component';
import { FlexLayoutModule } from '@angular/flex-layout';

// Override to russian
import localeRu from '@angular/common/locales/ru';
import { registerLocaleData } from '@angular/common';
import { TasksComponent } from './components/tasks/tasks.component';
import { SelectDropdownComponent } from './components/select-dropdown/select-dropdown.component';
import {MatTableModule} from '@angular/material/table';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatSortModule} from '@angular/material/sort';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { TaskCreateComponent } from './components/task-create/task-create.component';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatButtonModule} from '@angular/material/button';
import {MatCardModule} from '@angular/material/card';
import {MatInputModule} from '@angular/material/input';
import {MatIconModule} from '@angular/material/icon';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatListModule} from '@angular/material/list';
import { HomeComponent } from './components/home/home.component';
import { ForgotComponent } from './components/forgot/forgot.component';
import { ResetComponent } from './components/reset/reset.component';
import { TaskRewardComponent } from './components/task-reward/task-reward.component';
import {UrlPermissionUser} from './permission/url-permission-user';
import {UrlPermissionKing} from './permission/url-permission-king';
import {UrlPermissionWitcher} from './permission/url-permission-witcher';
import { UrlPermissionUserKing } from './permission/url-permission-user-king';
import { TaskEditComponent } from './components/task-edit/task-edit.component';
import { TaskDeleteComponent } from './components/task-delete/task-delete.component';
import {MatTabsModule} from '@angular/material/tabs';
import { TasksTableComponent } from './components/tasks-table/tasks-table.component';
import { DealsComponent } from './components/deals/deals.component';
import { DealsTableComponent } from './components/deals-table/deals-table.component';
import { DealComponent } from './components/deal/deal.component';
import { DealCreateComponent } from './components/deal-create/deal-create.component';
import { DealDeleteComponent } from './components/deal-delete/deal-delete.component';
import { DealEditComponent } from './components/deal-edit/deal-edit.component';
import { DealRewardComponent } from './components/deal-reward/deal-reward.component';
import { UrlPermissionVendorBlacksmithKing } from './permission/url-permission-vendor-blacksmith-king';
import { ChangeInfoComponent } from './components/change-info/change-info.component';
import { UsersComponent } from './components/users/users.component';
import { UsersTableComponent } from './components/users-table/users-table.component';
registerLocaleData(localeRu);

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    ProfileComponent,
    TaskComponent,
    TasksComponent,
    SelectDropdownComponent,
    TaskCreateComponent,
    HomeComponent,
    ForgotComponent,
    ResetComponent,
    TaskRewardComponent,
    TaskEditComponent,
    TaskDeleteComponent,
    TasksTableComponent,
    DealsComponent,
    DealsTableComponent,
    DealComponent,
    DealCreateComponent,
    DealDeleteComponent,
    DealEditComponent,
    DealRewardComponent,
    ChangeInfoComponent,
    UsersComponent,
    UsersTableComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    routing,
    BrowserAnimationsModule,
    MatFormFieldModule,
    MatSelectModule,
    ReactiveFormsModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    FontAwesomeModule,
    MatToolbarModule,
    MatButtonModule,
    MatCardModule,
    MatInputModule,
    FlexLayoutModule,
    MatIconModule,
    MatSidenavModule,
    MatListModule,
    MatTabsModule
  ],
  providers: [AuthService, AccountService, UrlPermission, UrlPermissionUser, UrlPermissionKing, UrlPermissionWitcher, UrlPermissionUserKing,
    UrlPermissionVendorBlacksmithKing, { provide: LOCALE_ID, useValue: 'ru' }],
  bootstrap: [AppComponent]
})
export class AppModule { }
