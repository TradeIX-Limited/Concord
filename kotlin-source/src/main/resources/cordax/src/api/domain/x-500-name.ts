export class X500Name {
    public static parse(value: string): X500Name {
        const dict = {};
        value.split(',').forEach(part => {
            const pair = part.split('=');
            dict[pair[0]] = pair[1];
        });
        return new X500Name(
            dict['CN'],
            dict['OU'],
            dict['O'],
            dict['L'],
            dict['S'],
            dict['C']);
    }

    constructor(
        public readonly commonName: string,
        public readonly organizationUnit: string,
        public readonly organizationName: string,
        public readonly localityName: string,
        public readonly stateName: string,
        public readonly country: string) {
        Object.freeze(this);
    }

    public toString(): string {
        const dict = {
            'CN': this.commonName,
            'OU': this.organizationUnit,
            'O': this.organizationName,
            'L': this.localityName,
            'S': this.stateName,
            'C': this.country
        }
        return Object
            .keys(dict)
            .filter(key => dict[key] !== undefined && dict[key] !== null)
            .map(key => `${key}=${dict[key]}`)
            .join(',')
    }
}
