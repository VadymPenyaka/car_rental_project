import { carCategoryPanel } from "@/interfaces/carCategoryPanel"
import { sendRequest } from "@/lib/utils"
import { useEffect, useState } from "react"
import { useNavigate } from "react-router"

const categories = [
	{
		"min": 35.0,
		"max": 35.0,
		"carClass": "ECONOMY"
	},
	{
		"min": 35.0,
		"max": 45.0,
		"carClass": "COMFORT"
	},
	{
		"min": 50.0,
		"max": 65.0,
		"carClass": "BUSINESS"
	},
	{
		"min": 70.0,
		"max": 70.0,
		"carClass": "PREMIUM"
	},
	{
		"min": 0.0,
		"max": 0.0,
		"carClass": "MINIVAN"
	},
	{
		"min": 0.0,
		"max": 0.0,
		"carClass": "SUV"
	}
]

export function CarMainCategoriesPanel() {
	// const [categories, setCategories] = useState<carCategoryPanel[]>([])
	const navigate = useNavigate()

	// useEffect(() => {
	// 	const fetchCategoriesDetails = async () => {
	// 		let response = await sendRequest({
	// 			url: `/api/v1/cars/categoriesPriceRange`,
	// 			method: "GET",
	// 			withCredentials: false,
	// 			headers: {
	// 				"Content-Type": "application/json",
	// 			},
	// 		})

	// 		setCategories(response.data as carCategoryPanel[])
	// 	}
	// 	fetchCategoriesDetails()
	// }, [])

	return (
		<div className="max-w-5xl mx-auto flex justify-between p-4">
			{categories.map(({ min, max, carClass }) => (
				<figure
					key={carClass}
					className="shrink-0 cursor-pointer"
					onClick={() => {
						navigate(`/search?carClass=${carClass}`)
					}}>
					<div className="overflow-hidden rounded-md w-40 ">
						<img
							// src={`https://carrental.fra1.digitaloceanspaces.com/cars/category/${carClass}.avif`}
							src={`https://7cars.com.ua/wp-content/uploads/2016/03/business.jpg`}
							alt={`Category - ${carClass}`}
							className="aspect-[4/3] h-fit w-40 object-scale-down mx-auto"
						/>
					</div>
					<figcaption className="pt-2 text-sm text-center text-orange-500 font-semibold">
						<p className="font-bold text-foreground text-sm">
							{carClass}
						</p>
						{`$${min} - $${max}`}
					</figcaption>
				</figure>
			))}
		</div>
	)
}