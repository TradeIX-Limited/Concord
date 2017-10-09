import { X500Name } from '../identity/x500-name';

export class PurchaseOrderRequestMessage {
    public constructor(
        public readonly supplier: X500Name,
        public readonly value: number) {
    }
}

export class PurchaseOrderResponseMessage {
    public constructor(
        public readonly linearId: string,
        public readonly transactionId: string) {
    }
}
