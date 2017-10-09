// TODO : AttachmentConstraint, ContractClassName, ContractState

export class TransactionState<T> {
    public constructor(
        public readonly data: T,
        public readonly contract: any,
        public readonly notary: string,
        public readonly encumbrance: number,
        public readonly constraint: any) {
    }
}
