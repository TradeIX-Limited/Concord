import { X500Name } from '../x-500-name'

export class ChangeOwnerViewModel {
    constructor(
        public readonly linearId?: string,
        public readonly newOwner?: X500Name) {
    }

    public toRequestObject(): object {
        return {
            linearId: this.linearId,
            newOwner: this.newOwner.toString()
        }
    }
}
