export class PensionerDetail {
    public id: number;
	public name: string;
	public dateOfBirth: Date;
	public panNumber: string;
    public aadhaarNumber: string;
	public salary: number;
	public allowance: number;
	public pensionType: string;

    constructor() {
        this.id = 0;
        this.name = '';
        this.dateOfBirth = new Date();
        this.panNumber = '';
        this.aadhaarNumber = '';
        this.salary = 0;
        this.allowance = 0;
        this.pensionType = '';
    }
}