export class Currency {
    public static readonly GBP = new Currency('GBP', '£', 100, 'Penny', 'British Pound');
    public static readonly USD = new Currency('USD', '$', 100, 'Cent', 'United States Dollar');
    public static readonly EUR = new Currency('EUR', '€', 100, 'Cent', 'Euro');
    public static readonly AUD = new Currency('AUD', '$', 100, 'Cent', 'Australian Dollar');
    public static readonly JPY = new Currency('JPY', '¥', 100, 'Sen', 'Japanese Yen');
    public static readonly CNY = new Currency('CNY', '¥', 100, 'Fen', 'Chinese Yaun');

    public static getKnownCurrencies(): Currency[] {
        return [
            Currency.GBP,
            Currency.USD,
            Currency.EUR,
            Currency.JPY,
            Currency.CNY,
            Currency.AUD
        ];
    }

    public constructor(
        public readonly code: string,
        public readonly symbol: string,
        public readonly fractionalUnits: number,
        public readonly fractionalUnitName: string,
        public readonly name) {
    }
}
