export class StateRef {
  public constructor(
    public readonly txhash: string = null,
    public readonly index: number = 0) {
  }
}

export class State<T> {
  public constructor(public readonly data: T = null) {
  }
}

export class StateAndRef<T> {
  public constructor(
    public readonly state: State<T> = new State(),
    public readonly ref: StateRef = new StateRef()) {
  }
}

export class UniqueIdentifier {
  constructor(
    public readonly externalId: string,
    public readonly id: string) {
  }
}

export class CordaX500Name {
  public static parse(value: string): CordaX500Name {
    const dict: object = {};
    value.split(",").forEach(part => {
      const pair: string[] = part.split("=");
      dict[pair[0]] = pair[1];
    });
    return new CordaX500Name(
      dict["CN"],
      dict["OU"],
      dict["O"],
      dict["L"],
      dict["S"],
      dict["C"]);
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
      "CN": this.commonName,
      "OU": this.organizationUnit,
      "O": this.organizationName,
      "L": this.localityName,
      "S": this.stateName,
      "C": this.country
    };

    return Object
      .keys(dict)
      .filter(key => dict[key] !== undefined && dict[key] !== null)
      .map(key => `${key}=${dict[key]}`)
      .join(",");
  }
}
