import { Component, ElementRef, Renderer2 } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-landing',
  templateUrl: './landing.component.html',
  styleUrls: ['./landing.component.scss']
})
export class LandingComponent {

  constructor(private router: Router,  private el: ElementRef, private renderer: Renderer2) { }

  ngAfterViewInit() {
    const politicRole = this.el.nativeElement.querySelector('#btnL');
    const citizenRole = this.el.nativeElement.querySelector('#btnR');

    this.renderer.listen(politicRole, 'click', () => {
      this.navigateToSignup('politic');
    });

    this.renderer.listen(citizenRole, 'click', () => {
      this.navigateToSignup('citizen');
    });
  }

  navigateToSignup(role: string) {
    this.router.navigate(['/signup'], { queryParams: { role: role } });
  }
}
