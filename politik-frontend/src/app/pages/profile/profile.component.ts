import { Component, ElementRef, ViewChild } from '@angular/core';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent {

  tabs: string[] = ['Posts', 'Polls', 'Reposts', 'LifeBook'];

  activatedTab: number = 0; //paso esta variable con el index para saber que tab esta activo

  setTab(index: number){
    this.activatedTab = index;
  }
}
