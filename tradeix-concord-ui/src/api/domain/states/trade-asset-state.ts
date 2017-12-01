import { TradeAsset } from "api/domain/models/trade-asset";

export class TradeAssetState {
    public readonly linearId: string;
    public readonly tradeAsset: TradeAsset;
    public readonly owner: string;
    public readonly buyer: string;
    public readonly supplier: string;
    public readonly conductor: string;
}
