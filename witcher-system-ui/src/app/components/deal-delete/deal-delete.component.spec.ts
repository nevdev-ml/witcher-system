import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DealDeleteComponent } from './deal-delete.component';

describe('DealDeleteComponent', () => {
  let component: DealDeleteComponent;
  let fixture: ComponentFixture<DealDeleteComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DealDeleteComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DealDeleteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
