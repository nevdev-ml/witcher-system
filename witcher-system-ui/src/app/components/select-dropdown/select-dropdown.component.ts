import { Component, OnInit } from '@angular/core';
import {FormControl, Validators} from '@angular/forms';
import {Role} from '../../models/role';

@Component({
  selector: 'app-select-dropdown',
  templateUrl: './select-dropdown.component.html',
  styleUrls: ['./select-dropdown.component.css']
})
export class SelectDropdownComponent implements OnInit {
  rolesControl = new FormControl('', Validators.required);
  roles: Role[] = [
    {name: 'Король'},
    {name: 'Пользователь'},
    {name: 'Ремесленник'},
    {name: 'Торговец'},
  ];
  constructor() { }

  ngOnInit(): void {
  }

}


