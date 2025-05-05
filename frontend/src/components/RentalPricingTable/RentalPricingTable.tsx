import { Table, TableHeader, TableRow, TableHead, TableBody, TableCell } from "../ui/table"

interface RentalPricingTableProps {
	carPricing: {
		pledge: number
		upToThreeDays: number
		upToTenDays: number
		upToMonth: number
		moreThenMonth: number
	}
}

export function RentalPricingTable({ carPricing }: RentalPricingTableProps) {
	const {
		pledge,
		upToThreeDays,
		upToTenDays,
		upToMonth,
		moreThenMonth
	} = carPricing

	return (
		<div>
			<Table className="mt-4">
				<TableHeader>
					<TableRow>
						<TableHead>Period</TableHead>
						<TableHead>more than month</TableHead>
						<TableHead>10–29 days</TableHead>
						<TableHead>4–9 days</TableHead>
						<TableHead>1–3 days</TableHead>
						<TableHead>Pledge</TableHead>
					</TableRow>
				</TableHeader>
				<TableBody>
					<TableRow>
						<TableCell>Price (with Taxes)</TableCell>
						<TableCell>{moreThenMonth.toFixed(2)}$</TableCell>
						<TableCell>{upToMonth.toFixed(2)}$</TableCell>
						<TableCell>{upToTenDays.toFixed(2)}$</TableCell>
						<TableCell>{upToThreeDays.toFixed(2)}$</TableCell>
						<TableCell className="text-orange-500">{pledge.toFixed(2)}$</TableCell>
					</TableRow>
				</TableBody>
			</Table>

			<p className="text-xs mt-2 text-gray-500">
				* Read the <span className="text-orange-500 underline">daily car rental terms</span>
				<br />
				** Additional insurance is available for rentals of 3 days or more
			</p>
		</div>
	)
}  