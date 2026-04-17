import { Routes } from '@angular/router';
import { authGuard } from './core/auth/auth.guard';

export const routes: Routes = [
	{ path: '', pathMatch: 'full', redirectTo: 'login' },
	{
		path: 'login',
		loadComponent: () => import('./auth/login/login.page').then((m) => m.LoginPage)
	},
	{
		path: 'empleados',
		canActivate: [authGuard],
		children: [
			{
				path: '',
				loadComponent: () => import('./empleados/list/empleados-list.page').then((m) => m.EmpleadosListPage)
			},
			{
				path: 'nuevo',
				loadComponent: () => import('./empleados/create/empleado-create.page').then((m) => m.EmpleadoCreatePage)
			},
			{
				path: ':clave',
				loadComponent: () => import('./empleados/detail/empleado-detail.page').then((m) => m.EmpleadoDetailPage)
			},
			{
				path: ':clave/editar',
				loadComponent: () => import('./empleados/edit/empleado-edit.page').then((m) => m.EmpleadoEditPage)
			}
		]
	},
	{ path: '**', redirectTo: 'login' }
];
