import { X500Name } from '../x-500-name'
import { Currency } from '../currency';
import { UniqueIdentifier } from '../corda';

export class TradeAsset {
    public constructor(
        public readonly assetId?: string,
        public readonly status?: string,
        public readonly amount?: string) {
    }
}

export class TradeAssetState {
    public readonly participants: string[];
    // Other fields?

    public constructor(
        public readonly linearId: UniqueIdentifier,
        public readonly tradeAsset: TradeAsset,
        public readonly owner: string,
        public readonly buyer: string,
        public readonly supplier: string,
        public readonly conductor: string) {
    }
}

export class IssueTradeAssetViewModel {
    public constructor(
        public readonly buyer?: X500Name,
        public readonly supplier?: X500Name,
        public readonly assetId?: string,
        public readonly amount?: number,
        public readonly currency?: Currency) {
    }

    public toRequestObject(): object {
        const result = {
            supplier: this.supplier.toString(),
            assetId: this.assetId,
            amount: this.amount,
            currency: this.currency.toString()
        }

        if (this.buyer) {
            result['buyer'] = this.buyer.toString();
        }

        return result;
    }
}
