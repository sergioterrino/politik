import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss']
})
export class SignupComponent {

  role: string;
  showUseEmailDiv = false; //True -> si el usuario pulsa el btn usar el email para registrarse
  showSecondPart = false; //True -> sí no existe un usuario con ese phone, email or username

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, private dialog: MatDialog, private router: Router) {
    this.role = data.role; // recibe el role del landing.component.ts
  }

  closeDialog(){
    this.dialog.closeAll();
  }

  checkUser(){
    //falta hacer la petición al backend para checkear si existe
    this.showSecondPart = true;
  }

  checkPassword(){
    //falta hacer la petición al backend para checkear si la contraseña es correcta
    this.closeDialog();
    this.router.navigate(['/home']);
  }

  //para mostrar/ocultar la contraseña
  showPassword = false;
  togglePassword() {
    this.showPassword = !this.showPassword;
  }

  goToStart(){
    this.closeDialog();
    this.router.navigate(['/start']);
  }

  useEmail(){
    this.showUseEmailDiv = true;
  }
  usePhone(){
    this.showUseEmailDiv = false;
  }

  goBack(){
    this.showSecondPart = false;
  }
}
