import { MatDialog } from '@angular/material/dialog';
import { Injectable } from '@angular/core';
import { DialogSignupComponent } from 'src/app/components/dialogs/dialog-signup/dialog-signup.component';

@Injectable({
  providedIn: 'root'
})
export class DialogService {

  constructor(private matDialog: MatDialog) { }

  openDialog(component: any) {
    this.matDialog.open(component);
  }
}
