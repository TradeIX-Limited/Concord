type TypeOf<T> = { new(...args: any[]): T };

export class Mapper {
    private static instance: Mapper;
    private readonly mapConfigurations: MapConfiguration<any, any>[] = [];

    private constructor() {
    }

    public static getInstance(): Mapper {
        return Mapper.instance || (Mapper.instance = new Mapper());
    }

    public createMapConfiguration<TFrom, TTo>(
        from: TypeOf<TFrom>,
        to: TypeOf<TTo>,
        mapFunction: (input: TFrom) => TTo): void {
            if(this.mapConfigurations.some(mc => mc.from === from && mc.to === to)) {
                throw new TypeError("Type is already mapped");
            }

            this.mapConfigurations.push(new MapConfiguration(from, to, mapFunction));
    }

    public map<TFrom, TTo>(from: TypeOf<TFrom>, to: TypeOf<TTo>, input: TFrom): TTo {
        const mapConfiguration: MapConfiguration<TFrom, TTo> = this
            .mapConfigurations.find(mc => mc.from === from && mc.to === to);

        if(!mapConfiguration) {
            throw new TypeError("No mapping configuration found");
        }

        return mapConfiguration.mapFunction(input);
    }
}

class MapConfiguration<TFrom, TTo> {
    public constructor(
        public readonly from: TypeOf<TFrom>,
        public readonly to: TypeOf<TTo>,
        public readonly mapFunction: (input: TFrom) => TTo) {
    }
}