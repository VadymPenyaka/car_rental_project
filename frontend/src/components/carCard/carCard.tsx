import { Card, CardHeader, CardTitle, CardContent, CardFooter } from "../ui/card"
import { Button } from "../ui/button"
import { Tabs, TabsList, TabsTrigger, TabsContent } from "../ui/tabs"
import { userCar } from "@/interfaces/userCar"
import { useState } from "react"
import { CarCardSkeleton } from "./CarCardSkeleton"
import { useNavigate } from "react-router"
import { RentalPricingTable } from "../RentalPricingTable"

interface CarCardProps {
	userCar: userCar
}

export const CarCard: React.FC<CarCardProps> = ({ userCar }) => {
	const {
		id,
		modelName,
		brandName,
		numberOfSeats,
		fuelType,
		fuelConsumption,
		engineCapacity,
		gearboxType,
		carPricing,
	} = userCar

	const navigate = useNavigate()

	const [imgLoaded, setImgLoaded] = useState(false)

	const handleCardClick = () => {
		navigate(`/cars/${id}`)
	}

	return (
		<Card className="w-full md:w-auto max-w-lg mx-auto">
			{!imgLoaded && (
				<CarCardSkeleton />
			)}
			<CardHeader
				onClick={handleCardClick}
			>
				<img
					src={`https://carrental.fra1.digitaloceanspaces.com/cars/${id}_0.avif`}
					alt={brandName + " " + modelName}
					loading="lazy"
					className="w-full rounded-md object-cover"
					onLoad={() => setImgLoaded(true)}
					onError={() => {
						setImgLoaded(true) // stop showing skeleton
					}}
				/>
				<div className="flex gap-2 mt-2 overflow-x-auto">
					{[1, 2, 3, 4].map((_, idx) => (
						<img
							key={idx}
							src={`https://carrental.fra1.digitaloceanspaces.com/cars/${id}_${idx}.avif`}
							alt={`thumb-${idx + 1}`}
							className="w-20 h-14 object-cover rounded-md border"
						/>
					))}
				</div>
			</CardHeader>
			{imgLoaded && (
				<>
					<CardContent>
						<CardTitle className="text-xl font-semibold mb-2">
							{brandName} {modelName}
						</CardTitle>
						<div className="grid grid-cols-3 gap-4 text-sm text-gray-700">
							<div>{engineCapacity.toFixed(2)}L</div>
							<div>{fuelType}</div>
							<div>{gearboxType}</div>
							<div>{numberOfSeats} ppl</div>
							<div>{fuelConsumption} L / 100 km</div>
						</div>

						<Tabs defaultValue="rental" className="mt-6">
							<TabsList className="w-full">
								<TabsTrigger value="rental" className="w-1/2">
									Rental without driver
								</TabsTrigger>
								<TabsTrigger value="location" className="w-1/2">
									Pick-up address
								</TabsTrigger>
							</TabsList>

							<RentalPricingTable
								carPricing={carPricing}
							/>

							<TabsContent value="location">
								<p className="text-gray-600 mt-4">Pick-up address details will be shown here.</p>
							</TabsContent>
						</Tabs>
					</CardContent>
					<CardFooter className="justify-center">
						<Button className="bg-orange-500 hover:bg-orange-600 text-white">Book now</Button>
					</CardFooter>
				</>
			)}
		</Card >
	)
}