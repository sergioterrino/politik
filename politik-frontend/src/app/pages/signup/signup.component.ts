import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss']
})
export class SignupComponent {

  role: string;

  constructor(@Inject(MAT_DIALOG_DATA) public data: any) {
    this.role = data.role;
  }
}
