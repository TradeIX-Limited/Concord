export class ErrorResponseMessage {
    public constructor(public readonly message: string) {
    }
}

export class TransactionResponseMessage {
    public constructor(public readonly transactionId: string) {
    }
}

export class LinearTransactionResponseMessage extends TransactionResponseMessage {
    public constructor(
        public readonly linearId: string,
        public readonly transactionId: string) {
        super(transactionId);
    }
}
