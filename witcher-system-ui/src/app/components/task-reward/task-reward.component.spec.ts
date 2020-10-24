import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TaskRewardComponent } from './task-reward.component';

describe('Task.RewardComponent', () => {
  let component: TaskRewardComponent;
  let fixture: ComponentFixture<TaskRewardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TaskRewardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TaskRewardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
