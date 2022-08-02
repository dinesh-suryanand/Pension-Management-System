export class ProcessPensionInput {
    public aadhaarNumber: string;
	public pensionAmount: number;
	public bankServiceCharge: number;

    constructor() {
        this.aadhaarNumber = '';
        this.pensionAmount = 0;
        this.bankServiceCharge = 0;
    }
}