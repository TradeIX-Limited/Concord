import { UniqueIdentifier, CordaX500Name } from "api/domain/shared/corda";

export class PurchaseOrderState {
    public constructor(
        public readonly linearId?: UniqueIdentifier,
        public readonly owner?: CordaX500Name,
        public readonly buyer?: CordaX500Name,
        public readonly supplier?: CordaX500Name,
        public readonly conductor?: CordaX500Name,
        public readonly reference?: string,
        public readonly amount?: string,
        public readonly created?: Date,
        public readonly earliestShipment?: Date,
        public readonly latestShipment?: Date,
        public readonly portOfShipment?: string,
        public readonly descriptionOfGoods?: string,
        public readonly deliveryTerms?: string) {
    }
}