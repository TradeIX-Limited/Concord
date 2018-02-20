import { CordaX500Name } from "api/domain/shared/corda";

export interface IHistoryComponent<TState> {
    setItems(items: TState[]): void;
    getName(value: string): CordaX500Name;
}