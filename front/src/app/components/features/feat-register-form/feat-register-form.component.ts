import { Component, EventEmitter, Input, Output } from '@angular/core';
import { UserRegister } from 'src/app/models/user-register.model';
import { AuthService } from 'src/app/shared/services/auth.service';
import { LocalStorageService } from 'src/app/shared/services/local-storage.service';

@Component({
  selector: 'app-feat-register-form',
  templateUrl: './feat-register-form.component.html',
  styleUrls: ['./feat-register-form.component.scss']
})
export class FeatRegisterFormComponent {

  @Output() onRegisterFormOpenEmit: EventEmitter<boolean> = new EventEmitter<boolean>();
  
  isRegisterFormOpen: boolean = false;

  userRegister: UserRegister = new UserRegister("", "", "", "", "");



  constructor(
    private httpS: AuthService,
    private LsService: LocalStorageService
    ) { }


    onCancelRegisterForm() {
      this.onRegisterFormOpenEmit.emit(this.isRegisterFormOpen);
    }

    onSubmitRegister(): void {
      this.httpS.signUp(this.userRegister);
    }

}
