import { Component, ElementRef, ViewChild } from '@angular/core';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent {

  tabs: string[] = ['Posts', 'Polls', 'Reposts'];

  activatedTab: number = 0;

  setTab(index: number){
    this.activatedTab = index;
  }

  @ViewChild('main') main!: ElementRef;
  @ViewChild('sidebar') sidebar!: ElementRef;
  @ViewChild('rightBar') rightBar!: ElementRef;

  ngAfterViewInit() {
    this.sidebar.nativeElement.addEventListener('wheel', this.scrollMain.bind(this));
    this.rightBar.nativeElement.addEventListener('wheel', this.scrollMain.bind(this));
  }

  scrollMain(event: WheelEvent) {
    event.preventDefault();
    this.main.nativeElement.scrollTop += event.deltaY;
  }

}
