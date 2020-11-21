import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DealRewardComponent } from './deal-reward.component';

describe('DealRewardComponent', () => {
  let component: DealRewardComponent;
  let fixture: ComponentFixture<DealRewardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DealRewardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DealRewardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
