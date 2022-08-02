export class PensionDetail {
    public id: number;
	public name: string;
	public dateOfBirth: Date;
	public panNumber: string;
	public pensiontype: string;
	public pensionAmount: number;

    constructor() {
        this.id = 0;
        this.name = '';
        this.dateOfBirth = new Date();
        this.panNumber = '';
        this.pensiontype = '';
        this.pensionAmount = 0;
    }
}