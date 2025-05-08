import { Card, CardContent } from "@/components/ui/card"
import { Button } from "@/components/ui/button"
import { useParams } from "react-router"
import { sendRequest } from "@/lib/utils"
import { useEffect, useState } from "react"
import { userCarExtended } from "@/interfaces/userCarExtended"
import { Header } from "../header/Header"
import { RentalPricingTable } from "../RentalPricingTable"

export const CarPage = () => {
	const { carId } = useParams()

	const [carData, setCarData] = useState<userCarExtended | null>(null)

	useEffect(() => {
		const fetchCar = async () => {
			let response = await sendRequest({
				url: `/api/v1/cars/${carId}`,
				method: "GET",
				withCredentials: false,
				headers: {
					"Content-Type": "application/json",
				},
			})

			setCarData(response.data as userCarExtended)
		}
		fetchCar()
	}, [])

	useEffect(() => {
		console.log("Got car data! - ", carData)
	}, [carData])

	if (!carData) {
		// TODO: write a loading
		return <div className="p-4">Loading car data...</div>
	}

	const {
		brandName,
		modelName
	} = carData.model

	return (
		<>
			<Header />
			<div className="max-w-6xl mx-auto p-4 space-y-6">
				{/* Title */}
				<h1 className="text-3xl font-bold text-red-600">Rent {brandName.name + " " + modelName} in {carData.location.city}</h1>

				<div className="grid grid-cols-1 md:grid-cols-2 gap-6 items-start">
					{/* Car Image */}
					<div className="w-full">
						<img
							src={`https://carrental.fra1.digitaloceanspaces.com/cars/${carId}_0.avif`}
							alt={brandName.name + " " + modelName}
							loading="lazy"
							className="w-full rounded-md object-cover"
						/>
					</div>

					{/* Location Selector and Pricing */}
					<div className="space-y-4">
						<Card>
							<CardContent className="space-y-2 pt-4">
								<label htmlFor="city" className="font-semibold">Pickup City</label>
								<select
									id="city"
									className="w-full border rounded p-2"
									defaultValue="Kyiv"
								>
									<option>Kyiv</option>
									<option>Lviv</option>
									<option>Odesa</option>
								</select>
							</CardContent>
						</Card>
						<Card>
							<RentalPricingTable carPricing={carData.carPricing} />
						</Card>
					</div>
				</div>

				{/* Order Button */}
				<div className="text-center">
					<Button className="bg-orange-500 hover:bg-orange-600 text-white text-lg px-6 py-2 rounded">Rent!</Button>
				</div>

				{/* Car Specifications */}
				<Card>
					<CardContent className="pt-4">
						<h2 className="text-xl font-semibold mb-2">Details</h2>
						<table className="w-full">
							<tbody>
								<tr><td className="py-1 font-medium">Brand</td><td>{brandName.name}</td></tr>
								<tr><td className="py-1 font-medium">Body Type</td><td>{carData.bodyType}</td></tr>
								<tr><td className="py-1 font-medium">Engine Capacity</td><td>{carData.engineCapacity} L.</td></tr>
								<tr><td className="py-1 font-medium">Transmission Type</td><td>{carData.gearboxType}</td></tr>
								<tr><td className="py-1 font-medium">Fuel Type</td><td>{carData.fuelType}</td></tr>
								<tr><td className="py-1 font-medium">Number of Passengers</td><td>{carData.numberOfSeats} seats</td></tr>
							</tbody>
						</table>
					</CardContent>
				</Card>
			</div>
		</>
	)
}
