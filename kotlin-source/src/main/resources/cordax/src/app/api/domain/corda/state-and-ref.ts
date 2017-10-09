import { TransactionState } from './transaction-state';
import { StateRef } from './state-ref';

export class StateAndRef<T extends TransactionState<T>> {
    public constructor(
        public readonly state: TransactionState<T>,
        public readonly ref: StateRef) {
    }
}
