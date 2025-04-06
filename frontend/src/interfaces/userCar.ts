export interface userCar {
    id: string;
    modelName: string;
    brandName: string;
    numberOfSeats: number;
    fuelType: string;
    fuelConsumption: number;
    driveType: string;
    engineCapacity: number;
    gearboxType: string;
    carPricing: {
        pledge: number;
        upToThreeDays: number;
        upToTenDays: number;
        upToMonth: number;
        moreThenMonth: number;
    }
}