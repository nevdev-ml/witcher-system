export class PasswordModifyModel {
  password: string;
  userId: number;

  constructor(userId: number, password: string){
    this.userId = userId;
    this.password = password;
  }
}
