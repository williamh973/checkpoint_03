export class UserRegister {
    constructor(
        public firstname: string,
        public lastname: string,
        public email: string,
        public password: string,
        public requiredRole: string
    ) { }
}