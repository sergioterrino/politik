import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PostsProfileComponent } from './posts-profile.component';

describe('PostsProfileComponent', () => {
  let component: PostsProfileComponent;
  let fixture: ComponentFixture<PostsProfileComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PostsProfileComponent]
    });
    fixture = TestBed.createComponent(PostsProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
