import { Routes, RouterModule } from '@angular/router';
import {LoginComponent} from './components/login/login.component';
import {RegisterComponent} from './components/register/register.component';
import {ProfileComponent} from './components/profile/profile.component';
import {UrlPermission} from './permission/permission';
import {TaskComponent} from './components/task/task.component';
import {TasksComponent} from './components/tasks/tasks.component';
import {TaskCreateComponent} from './components/task-create/task-create.component';
import {HomeComponent} from './components/home/home.component';
import {ForgotComponent} from './components/forgot/forgot.component';
import {ResetComponent} from './components/reset/reset.component';
import {TaskRewardComponent} from './components/task-reward/task-reward.component';
import {UrlPermissionUserKing} from './permission/url-permission-user-king';
import {TaskEditComponent} from './components/task-edit/task-edit.component';
import {TaskDeleteComponent} from './components/task-delete/task-delete.component';
import {DealsComponent} from './components/deals/deals.component';
import {DealComponent} from './components/deal/deal.component';
import {DealRewardComponent} from './components/deal-reward/deal-reward.component';
import {DealCreateComponent} from './components/deal-create/deal-create.component';
import { UrlPermissionVendorBlacksmithKing } from './permission/url-permission-vendor-blacksmith-king';
import {DealDeleteComponent} from './components/deal-delete/deal-delete.component';
import {DealEditComponent} from './components/deal-edit/deal-edit.component';
import {ChangeInfoComponent} from './components/change-info/change-info.component';
import {UsersComponent} from './components/users/users.component';
import {UrlPermissionKing} from './permission/url-permission-king';


const appRoutes: Routes = [
  { path: 'profile', component: ProfileComponent, canActivate: [UrlPermission] },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'forgot', component: ForgotComponent },
  { path: 'reset/:token', component: ResetComponent },
  { path: 'change_info', component: ChangeInfoComponent, canActivate: [UrlPermission] },
  { path: 'change_password', component: ChangeInfoComponent, canActivate: [UrlPermission] },

  { path: 'users', component: UsersComponent, canActivate: [UrlPermissionKing] },
  { path: 'change_user', component: ChangeInfoComponent, canActivate: [UrlPermissionKing] },
  { path: 'add_user', component: RegisterComponent, canActivate: [UrlPermissionKing] },

  { path: 'tasks/:task', component: TaskComponent, canActivate: [UrlPermission] },
  { path: 'reward_task/:task', component: TaskRewardComponent, canActivate: [UrlPermissionUserKing] },
  { path: 'tasks', component: TasksComponent, canActivate: [UrlPermission] },
  { path: 'quests', component: TasksComponent, canActivate: [UrlPermission] },
  { path: 'add_task', component: TaskCreateComponent, canActivate: [UrlPermission] },
  { path: 'edit_task/:task', component: TaskEditComponent, canActivate: [UrlPermissionUserKing] },
  { path: 'delete_task/:task', component: TaskDeleteComponent, canActivate: [UrlPermissionUserKing] },

  { path: 'deals/:deal', component: DealComponent, canActivate: [UrlPermission] },
  { path: 'reward_deal/:deal', component: DealRewardComponent, canActivate: [UrlPermissionVendorBlacksmithKing] },
  { path: 'deals', component: DealsComponent, canActivate: [UrlPermission] },
  { path: 'workshop', component: DealsComponent, canActivate: [UrlPermission] },
  { path: 'shop', component: DealsComponent, canActivate: [UrlPermission] },
  { path: 'accepted_deal', component: DealsComponent, canActivate: [UrlPermissionVendorBlacksmithKing] },
  { path: 'add_shop', component: DealCreateComponent, canActivate: [UrlPermissionVendorBlacksmithKing] },
  { path: 'add_workshop', component: DealCreateComponent, canActivate: [UrlPermissionVendorBlacksmithKing] },
  { path: 'edit_shop/:deal', component: DealEditComponent, canActivate: [UrlPermissionVendorBlacksmithKing] },
  { path: 'edit_workshop/:deal', component: DealEditComponent, canActivate: [UrlPermissionVendorBlacksmithKing] },
  { path: 'delete_shop/:deal', component: DealDeleteComponent, canActivate: [UrlPermissionVendorBlacksmithKing] },
  { path: 'delete_workshop/:deal', component: DealDeleteComponent, canActivate: [UrlPermissionVendorBlacksmithKing] },

  { path: '', pathMatch: 'full', component: HomeComponent },
  { path: '**', redirectTo: '/login' }
];

export const routing = RouterModule.forRoot(appRoutes);
