import { UserService } from 'src/app/services/user/user.service';
import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ForgotPasswordComponent } from '../forgot-password/forgot-password.component';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Observable, catchError, map, of, firstValueFrom } from 'rxjs';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  showSecondPart = false; //sí no existe un usuario con ese phone, email or username

  formLogin!: FormGroup;

  usernameNotExists: boolean = false; //Por default, siempre existe el usuario, para que no salga el error-message
  phoneNotExists: boolean = false; //True -> si el teléfono no existe en la base de datos
  emailNotExists: boolean = false; //True -> si el email no existe en la base de datos

  constructor(
    private dialog: MatDialog,
    private router: Router,
    private formBuilder: FormBuilder,
    private userService: UserService,
  ) {

  }

  ngOnInit(): void {
    this.formLogin = this.initForm(); //instanciamos el form
  }

  initForm(): FormGroup {
    return this.formBuilder.group({
      firstPart: this.formBuilder.group({
        namePhoneEmail: ['', [Validators.required]],
      }),
      secondPart: this.formBuilder.group({
        namePhoneEmail2: [''],
        password: ['', [Validators.required, Validators.minLength(8)]],
      })
    })
  }

  onSubmit(formLogin: FormGroup) {
    if (this.showSecondPart) {
      const secondPart = formLogin.get('secondPart');
      if (secondPart?.valid) {
        console.log('Datos enviados del formLogin ', formLogin.value);
        console.log("Formulario válido");
        this.closeDialog();
        this.router.navigate(['/home']);
      } else {
        console.log("Formulario no valido");
      }
    }
  }

  async checkUser() {
    const firstPart = this.formLogin.get('firstPart');
    const namePhoneEmailValue = this.formLogin.get('firstPart.namePhoneEmail')?.value;

    console.log('valor del campo acceso -> ', namePhoneEmailValue);

    if (firstPart?.valid) {
      const usernameExists = await firstValueFrom(this.checkUsername(namePhoneEmailValue));
      if (usernameExists) {
        console.log("Este username existe en la bd");
        this.formLogin.get('secondPart.namePhoneEmail2')?.setValue(namePhoneEmailValue);
        this.showSecondPart = true;
      }else{
        this.usernameNotExists = true;
        console.log('Este username no existe en la bd');
      }

      const phoneExists = await firstValueFrom(this.checkPhone(namePhoneEmailValue));
      if (phoneExists) {
        console.log('Este phone existe en la bd');
        this.formLogin.get('secondPart.namePhoneEmail2')?.setValue(namePhoneEmailValue);
        this.showSecondPart = true;
      }else{
        this.phoneNotExists = true;
        console.log('Este phone no existe en la bd');
      }

      const emailExists = await firstValueFrom(this.checkEmail(namePhoneEmailValue));
      if (emailExists) {
        console.log('Este email existe en la bd');
        this.formLogin.get('secondPart.namePhoneEmail2')?.setValue(namePhoneEmailValue);
        this.showSecondPart = true;
      }else{
        this.emailNotExists = true;
        console.log('Este email no existe en la bd');
      }
    }
  }

  private checkUsername(username: string): Observable<boolean> {
    return this.userService.getUserByUsername(username).pipe(
      map(user => {
        if (user) return true;
        else return false;
      }),
      catchError(error => {
        console.log("error - checkYsername() ", error);
        return of(false);
      })
    );
  }
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
  private checkEmail(email: string): Observable<boolean> {
    return this.userService.getUserByEmail(email).pipe(
      map(user => {
        if (user) return true;
        else return false;
      }),
      catchError(error => {
        console.log("Error: checkEmail() ", error);
        return of(false);
      })
    );
  }

  openForgotPassword() {
    this.closeDialog();
    this.dialog.open(ForgotPasswordComponent, {
      width: '40%',
      height: '90%'
    });
  }
  //para mostrar/ocultar la contraseña
  showPassword = false;
  togglePassword() {
    this.showPassword = !this.showPassword;
  }
  goBack() {
    this.showSecondPart = false;
  }
  closeDialog() {
    this.dialog.closeAll();
  }
}

