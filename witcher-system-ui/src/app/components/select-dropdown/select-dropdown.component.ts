import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormControl, Validators} from '@angular/forms';
import {ObjectBase} from '../../models/objectBase';

@Component({
  selector: 'app-select-dropdown',
  templateUrl: './select-dropdown.component.html',
  styleUrls: ['./select-dropdown.component.css']
})
export class SelectDropdownComponent implements OnInit {
  @Input() objectList: ObjectBase[];
  @Input() title: string;
  @Output() changed = new EventEmitter<string>();
  checkedValue = new FormControl('', Validators.required);

  constructor() { }

  onChanged(role: string) {
    this.changed.emit(role);
  }

  ngOnInit(): void { }
}
