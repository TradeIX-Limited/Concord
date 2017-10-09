export class X500Name {
    public readonly commonName: string;
    public readonly localityName: string;
    public readonly stateOrProvinceName: string;
    public readonly organizationName: string;
    public readonly organizationalUnitName: string;
    public readonly countryName: string;
    public readonly street: string;

    public constructor(private readonly name: string) {
        const dictionary = {};
        name.split(',').forEach(kvp => {
            const tuple = kvp.split('=');
            dictionary[tuple[0]] = tuple[1];
        });
        this.commonName = dictionary['CN'];
        this.localityName = dictionary['L'];
        this.stateOrProvinceName = dictionary['ST'];
        this.organizationName = dictionary['O'];
        this.organizationalUnitName = dictionary['OU'];
        this.countryName = dictionary['C'];
        this.street = dictionary['STREET'];
        Object.freeze(this);
    }

    public toString(): string {
        return this.name;
    }
}
