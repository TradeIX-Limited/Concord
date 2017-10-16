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
