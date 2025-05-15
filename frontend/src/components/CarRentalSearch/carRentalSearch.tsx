import { Button } from "@/components/ui/button"
import { Popover, PopoverContent, PopoverTrigger } from "@/components/ui/popover"
import { Calendar } from "@/components/ui/calendar"
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select"
import { format, isBefore } from "date-fns"
import { useNavigate } from 'react-router'
import { FullSearchPopup } from "./FullSearchPopup.tsx/FullSearchPopup"
import { useEffect, useState } from "react"
import { sendRequest } from "@/lib/utils"
import { location } from "@/interfaces/location"

export default function CarRentalSearch() {
	const [startDate, setStartDate] = useState<Date | undefined>(undefined)
	const [endDate, setEndDate] = useState<Date | undefined>(undefined)
	const [location, setLocation] = useState<string | undefined>(undefined)
	const [cities, setCities] = useState<Array<string>>([])

	const navigate = useNavigate()

	useEffect(()=> {
		const fetchCities = async () => {
					let response = await sendRequest({
						url: `/api/v1/locations`,
						method: "GET",
						withCredentials: false,
						headers: {
							"Content-Type": "application/json",
						},
					})
					let filteredCities = response.data.map((city: location) => city.city)
					setCities(filteredCities)
				}
				fetchCities()
	}, [])

	// const cities = ["Kyiv", "Lviv", "Odesa", "Dnipro", "Kharkiv"]

	const handleSearchButtonClick = async () => {
		const requestData = {
			city: location ? location : "",
			startDate: startDate ? format(startDate, "dd.MM.yyyy") : "",
			endDate: endDate ? format(endDate, "dd.MM.yyyy") : "",
		}

		// Filter out empty values
		const filtered = Object.fromEntries(
			Object.entries(requestData).filter(([_, value]) => value)
		)

		const queryParams = new URLSearchParams(filtered).toString()
		console.log("queryParams: ", queryParams)

		navigate(`/search?${queryParams}`)
	}

	return (
		<div className="bg-white p-8 rounded-2xl shadow-lg w-full max-w-2xl">
			<h2 className="text-lg font-semibold mb-4">Choose a car for rental</h2>
			<div className="grid grid-cols-1 md:grid-cols-3 gap-4">
				<div>
					<label className="text-sm font-medium">Pick-up location</label>
					<div className="relative mt-1">
						<Select onValueChange={(value) => setLocation(value)}>
							<SelectTrigger className="w-full">
								<SelectValue placeholder="Select a city" />
							</SelectTrigger>
							<SelectContent>
								{cities.map((city) => (
									<SelectItem key={city} value={city}>{city}</SelectItem>
								))}
							</SelectContent>
						</Select>
					</div>
				</div>

				<div>
					<label className="text-sm font-medium">Pick-up date</label>
					<Popover>
						<PopoverTrigger asChild>
							<Button variant="outline" className="w-full flex justify-between">
								{startDate ? format(startDate, "dd.MM.yyyy") : "dd.MM.yyyy"}
							</Button>
						</PopoverTrigger>
						<PopoverContent>
							<Calendar mode="single" selected={startDate || undefined} onSelect={(date) => date && setStartDate(date)} />
						</PopoverContent>
					</Popover>
				</div>

				<div>
					<label className="text-sm font-medium">Drop-off date</label>
					<Popover>
						<PopoverTrigger asChild>
							<Button variant="outline" className="w-full flex justify-between">
								{endDate ? format(endDate, "dd.MM.yyyy") : "dd.MM.yyyy"}
							</Button>
						</PopoverTrigger>
						<PopoverContent>
							<Calendar
								mode="single"
								selected={endDate || undefined}
								onSelect={(date) => {
									if (!date) return
									if (startDate && isBefore(date, startDate)) return
									setEndDate(date)
								}}
								disabled={(date) => startDate ? isBefore(date, startDate) : false}
							/>
						</PopoverContent>
					</Popover>
				</div>
			</div>

			<div className="mt-4 flex justify-between items-center">
				<FullSearchPopup />
				<Button className="bg-orange-500 hover:bg-orange-600" onClick={handleSearchButtonClick}>
					Find a car
				</Button>
			</div>
		</div>
	)
}
