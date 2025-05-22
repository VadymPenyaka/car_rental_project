import React, { useEffect, useRef, useState } from "react"
import { Button } from "@/components/ui/button"
import { Calendar } from "@/components/ui/calendar"
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select"
import { format, isBefore } from "date-fns"
import { useNavigate } from 'react-router'
import { sendRequest } from "@/lib/utils"
import { location as LocationInterface } from "@/interfaces/location"
import { Input } from "@/components/ui/input"
import { Popover, PopoverContent, PopoverTrigger } from "@/components/ui/popover"
import { cn } from "@/lib/utils"

// Additional filters interface and options
interface FilterForm {
	carClass: string
	gearboxType: string
	fuelType: string
	priceMin: number
	priceMax: number
	brand: string
}
const OPTIONS = {
	carClass: ["ECONOMY", "BUSINESS", "LUXURY"],
	gearboxType: ["AUTO", "MANUAL"],
	fuelType: ["PETROL", "DIESEL", "ELECTRIC"],
}

// Reusable PopoverSelect
interface PopoverSelectProps {
	label: string
	name: keyof FilterForm
	options: string[]
	selected: string
	onSelect: (value: string) => void
}
function PopoverSelect({ label, name, options, selected, onSelect }: PopoverSelectProps) {
	return (
		<div className="space-y-1">
			<label className="block text-sm font-medium text-gray-700">{label}</label>
			<input type="hidden" name={name} value={selected} />
			<Popover>
				<PopoverTrigger asChild>
					<Button type="button" variant="outline" className="w-full justify-start text-left">
						{selected || `Select ${label.toLowerCase()}`}
					</Button>
				</PopoverTrigger>
				<PopoverContent className="w-full p-2 space-y-1 cursor-pointer">
					{options.map(option => (
						<Button
							key={option}
							type="button"
							variant="ghost"
							className={cn("w-full justify-start", selected === option && "bg-muted")}
							onClick={() => onSelect(option)}>
							{option}
						</Button>
					))}
				</PopoverContent>
			</Popover>
		</div>
	)
}

export default function CarRentalSearch() {
	const [startDate, setStartDate] = useState<Date | undefined>()
	const [endDate, setEndDate] = useState<Date | undefined>()
	const [location, setLocation] = useState<string | undefined>()
	const [cities, setCities] = useState<string[]>([])
	const navigate = useNavigate()

	// Advanced filter states
	const [showAdvanced, setShowAdvanced] = useState(false)
	const formRef = useRef<HTMLFormElement>(null)
	const [carClass, setCarClass] = useState<string>("")
	const [gearboxType, setGearboxType] = useState<string>("")
	const [fuelType, setFuelType] = useState<string>("")
	const [brand, setBrand] = useState<string>("")
	const [brands] = useState<string[]>(["BMW", "Merc", "XD"])

	useEffect(() => {
		(async () => {
			const response = await sendRequest({ url: `/api/v1/locations`, method: "GET", withCredentials: false, headers: { "Content-Type": "application/json" } })
			const filtered = response.data.map((c: LocationInterface) => c.city)
			setCities(filtered)
		})()
	}, [])

	const handleSearchButtonClick = async () => {
		const baseFilters = {
			city: location || "",
			startDate: startDate ? format(startDate, "dd.MM.yyyy") : "",
			endDate: endDate ? format(endDate, "dd.MM.yyyy") : "",
		}
		// advanced
		const adv = formRef.current ? Object.fromEntries(new FormData(formRef.current).entries()) : {}

		const requestData = { ...baseFilters, ...adv }
		const filtered = Object.fromEntries(Object.entries(requestData).filter(([_, v]) => v))

		console.log(filtered);
		navigate(`/search?${new URLSearchParams(filtered as any).toString()}`)
	}

	const handleAdvancedSubmit = (e: React.FormEvent) => {
		e.preventDefault()
		setShowAdvanced(false)
	}

	return (
		<div className="bg-white p-8 rounded-2xl shadow-lg w-full max-w-2xl">
			<h2 className="text-lg font-semibold mb-4">Choose a car for rental</h2>
			<form ref={formRef} onSubmit={handleAdvancedSubmit} className="mt-6 space-y-4 border-t pt-4 overflow-hidden transition-[max-height,opacity] duration-300 ease-in-out">
				<div className="grid grid-cols-1 md:grid-cols-3 gap-4">
					<div>
						<label className="text-sm font-medium">Pick-up location</label>
						<div className="relative mt-1">
							<Select onValueChange={setLocation}>
								<SelectTrigger className="w-full"><SelectValue placeholder="Select a city" /></SelectTrigger>
								<SelectContent>
									{cities.map(city => <SelectItem key={city} value={city}>{city}</SelectItem>)}
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
								<Calendar mode="single" selected={startDate} onSelect={d => d && setStartDate(d)} />
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
									selected={endDate}
									onSelect={d => { if (d && (!startDate || !isBefore(d, startDate))) setEndDate(d) }}
									disabled={d => startDate ? isBefore(d, startDate) : false}
								/>
							</PopoverContent>
						</Popover>
					</div>
				</div>

				{!showAdvanced && (
					<div className="mt-4 flex justify-between items-center">
						<Button variant="outline" type="button" onClick={() => setShowAdvanced(prev => !prev)}>
							{showAdvanced ? "Hide options" : "More options"}
						</Button>
						<Button className="bg-orange-500 hover:bg-orange-600" type="submit" onClick={handleSearchButtonClick}>
							Find a car
						</Button>
					</div>
				)}
				<div
					className={cn(
						"overflow-hidden transition-[max-height,opacity] duration-500 linear",
						showAdvanced ? "max-h-[1000px] opacity-100 mt-4 border-t pt-4 space-y-4" : "max-h-0 opacity-0"
					)}
				>
					<PopoverSelect label="Car Class" name="carClass" options={OPTIONS.carClass} selected={carClass} onSelect={setCarClass} />
					<PopoverSelect label="Gearbox" name="gearboxType" options={OPTIONS.gearboxType} selected={gearboxType} onSelect={setGearboxType} />
					<PopoverSelect label="Fuel Type" name="fuelType" options={OPTIONS.fuelType} selected={fuelType} onSelect={setFuelType} />
					<div className="grid grid-cols-2 gap-4">
						<div className="space-y-1">
							<label className="block text-sm font-medium text-gray-700">Min Price</label>
							<Input type="number" name="priceMin" defaultValue={0} min={0} />
						</div>
						<div className="space-y-1">
							<label className="block text-sm font-medium text-gray-700">Max Price</label>
							<Input type="number" name="priceMax" defaultValue={1000} min={0} />
						</div>
					</div>
					<PopoverSelect label="Brand" name="brand" options={brands} selected={brand} onSelect={setBrand} />
					<div className="mt-4 flex justify-between items-center">
						<Button variant="outline" type="button" onClick={() => setShowAdvanced(prev => !prev)}>
							{showAdvanced ? "Hide options" : "More options"}
						</Button>
						<Button className="bg-orange-500 hover:bg-orange-600" type="submit" onClick={handleSearchButtonClick}>
							Find a car
						</Button>
					</div>
				</div>
			</form >
		</div >
	)
}
