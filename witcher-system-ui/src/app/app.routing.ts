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
import {UrlPermissionWitcher} from './permission/url-permission-witcher';
import {UrlPermissionUserKing} from './permission/url-permission-user-king';
import {TaskEditComponent} from './components/task-edit/task-edit.component';
import {TaskDeleteComponent} from './components/task-delete/task-delete.component';


const appRoutes: Routes = [
  { path: 'profile', component: ProfileComponent, canActivate: [UrlPermission] }, // TODO
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'forgot', component: ForgotComponent },
  { path: 'reset/:token', component: ResetComponent },

  { path: 'tasks/:task', component: TaskComponent, canActivate: [UrlPermission] },
  { path: 'reward/:task', component: TaskRewardComponent, canActivate: [UrlPermissionUserKing] },
  { path: 'tasks', component: TasksComponent, canActivate: [UrlPermission] },
  { path: 'quests', component: TasksComponent, canActivate: [UrlPermission] },
  { path: 'profile/completed', component: TasksComponent, canActivate: [UrlPermissionWitcher] }, // TODO
  { path: 'profile/history', component: TasksComponent, canActivate: [UrlPermissionWitcher] }, // TODO
  { path: 'add', component: TaskCreateComponent, canActivate: [UrlPermission] },
  { path: 'edit/:task', component: TaskEditComponent, canActivate: [UrlPermissionUserKing] },
  { path: 'delete/:task', component: TaskDeleteComponent, canActivate: [UrlPermissionUserKing] },

  { path: '', pathMatch: 'full', component: HomeComponent },

  { path: '**', redirectTo: '/login' }
];

export const routing = RouterModule.forRoot(appRoutes);
