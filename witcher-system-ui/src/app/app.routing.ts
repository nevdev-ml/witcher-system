import { Routes, RouterModule } from '@angular/router';
import {LoginComponent} from './components/login/login.component';
import {RegisterComponent} from './components/register/register.component';
import {ProfileComponent} from './components/profile/profile.component';
import {UrlPermission} from './permission/permission';
import {TaskComponent} from './components/task/task.component';
import {TasksComponent} from './components/tasks/tasks.component';
import {TaskCreateComponent} from './components/task.create/task.create.component';


const appRoutes: Routes = [
  { path: 'profile', component: ProfileComponent, canActivate: [UrlPermission] },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },

  { path: 'tasks/:task', component: TaskComponent, canActivate: [UrlPermission] },
  { path: 'tasks', component: TasksComponent, canActivate: [UrlPermission] },
  { path: 'quests', component: TasksComponent, canActivate: [UrlPermission] },
  { path: 'add', component: TaskCreateComponent, canActivate: [UrlPermission] },

  { path: '**', redirectTo: '/login' }
];

export const routing = RouterModule.forRoot(appRoutes);
