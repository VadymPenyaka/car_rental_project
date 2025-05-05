export interface userCarExtended {
	id: string
	model: {
		id: string
		brandName: {
			name: string
		}
		modelName: string
		description: string
		year: number
	}
	color: string
	carClass: string
	bodyType: string
	numberOfSeats: number
	fuelType: string
	trunkCapacity: number
	driveType: string
	fuelConsumption: number
	location: {
		id: string
		locationName: string
		region: string
		city: string
		address: string
		latitude: string
		longitude: string
	}
	fuelTankCapacity: number
	engineCapacity: number
	gearboxType: string
	carPricing: {
		id: string
		pledge: number
		upToThreeDays: number
		upToTenDays: number
		upToMonth: number
		moreThenMonth: number
	}
	licenseCategory: string
}
