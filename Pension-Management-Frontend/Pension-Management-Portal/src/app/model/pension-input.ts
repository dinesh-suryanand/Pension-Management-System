export class PensionInput {
    public name: string;
    public dateOfBirth: Date;
    public panNumber: string;
    public aadhaarNumber: string;
    public pensionType: string;

    constructor() {
        this.name = '';
        this.dateOfBirth = new Date();
        this.panNumber = '';
        this.aadhaarNumber = '';
        this.pensionType = '';
    }
}