import { UserService } from 'src/app/services/user/user.service';
import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { Observable, catchError, firstValueFrom, map, of } from 'rxjs';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss']
})
export class SignupComponent {


  role: string;
  showUseEmailDiv = false; //True -> si el usuario pulsa el btn usar el email para registrarse
  showSecondPart = false; //True -> sí no existe un usuario con ese phone, email or username

  userData: any = {}; // Inicializar el objeto para almacenar los datos del formulario
  phoneExists: boolean = false; //True -> si el teléfono ya existe en la base de datos
  emailExists: boolean = false; //True -> si el email ya existe en la base de datos

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private dialog: MatDialog,
    private router: Router,
    private userService: UserService
  ) {
    this.role = data.role; // recibe el role del landing.component.ts
  }

  //pruebo traerme la birthday del form
  saveData(formSignup: any) {
    if (this.showSecondPart) {
      if (formSignup.valid) {
        console.log("Datos del formulario: ", this.userData);
        console.log("Formulario válido");
        //falta hacer la petición al backend para checkear si la contraseña es correcta---------------------------------$$$$$$$$$$
        this.closeDialog();
        this.router.navigate(['/home']);
      } else {
        console.log("Formulario no valido");
      }
    }
  }


  async checkUser(formSignup: any) {
    if (formSignup.valid) {
      //Se suscribe a la función checkPhone() y espera a que devuelva un valor
      //Si devuelve true, es que el teléfono ya existe en la base de datos --> error
      const phoneExists = await firstValueFrom(this.checkPhone(this.userData.phone));
      if (phoneExists) {
        console.log('El teléfono ya existe checkUser');
        this.phoneExists = true;
        return;
      }
      if (this.userData.email == 'test@test.com') {
        this.emailExists = true;
        console.log('El email ya existe');
        return;
      }

      this.showSecondPart = true;
    } else {
      console.log('El formulario no es válido. Por favor, completa todos los campos.');
    }
  }

  //Comprueba si el teléfono ya existe en la base de datos, si existe devuelve true
  //ese valor lo recogerá checkUser() y si es true(que ya existe el phone) mostrará un mensaje de error
  private checkPhone(phone: string): Observable<boolean> {
    return this.userService.getUserByPhone(phone).pipe(
      map(user => {
        if (user) return true;
        else return false;
      }),
      catchError(error => {
        console.error('Error: checkPhone() ', error);
        return of(false);
      })
    );
  }








  //para mostrar/ocultar la contraseña
  showPassword = false;
  togglePassword() {
    this.showPassword = !this.showPassword;
  }

  useEmail() {
    this.showUseEmailDiv = true;
    this.userData.phone = '';
  }
  usePhone() {
    this.showUseEmailDiv = false;
    this.userData.email = '';
  }

  //Si ya tiene cuenta, lo redirige al login(start)
  goToStart() {
    this.closeDialog();
    this.router.navigate(['/start']);
  }

  goBack() {
    this.showSecondPart = false;
  }

  closeDialog() {
    this.dialog.closeAll();
  }
}
