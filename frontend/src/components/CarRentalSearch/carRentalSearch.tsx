import { useState } from "react";
import { Button } from "@/components/ui/button";
import { Popover, PopoverContent, PopoverTrigger } from "@/components/ui/popover";
import { Calendar } from "@/components/ui/calendar";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import { format, isBefore } from "date-fns";
import { sendSearchRequest } from "./request";
import { sendRequest } from "@/lib/utils";

export default function CarRentalForm() {
    const [startDate, setStartDate] = useState<Date>(new Date());
    const [endDate, setEndDate] = useState<Date>(new Date());
    const [location, setLocation] = useState<string>("");
    const cities = ["Київ", "Львів", "Одеса", "Дніпро", "Харків"];

    const handleSearchButtonClick = async () => {
        // const requestData = {
        //     startDate,
        //     endDate,
        //     location,
        // }

        const requestData = {
            carClass: "BUSINESS"
        }

        let response: { data: any } = await sendRequest({
            url: '/api/v1/cars',
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            data: JSON.stringify(requestData),
        })
        
        return response.data
        // sendSearchRequest(requestData)
    }

    return (
        <div className="bg-white p-6 rounded-2xl shadow-lg w-full max-w-2xl">
            <h2 className="text-lg font-semibold mb-4">Підібрати авто в прокат</h2>

            <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
                <div>
                    <label className="text-sm font-medium">Місто видачі</label>
                    <div className="relative mt-1">
                        <Select onValueChange={setLocation}>
                            <SelectTrigger className="w-full">
                                <SelectValue placeholder="Оберіть місто" />
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
                    <label className="text-sm font-medium">Дата і час видачі</label>
                    <Popover>
                        <PopoverTrigger asChild>
                            <Button variant="outline" className="w-full flex justify-between">
                                {startDate ? format(startDate, "dd.MM.yyyy") : "дд.мм.рррр --:--"}
                            </Button>
                        </PopoverTrigger>
                        <PopoverContent>
                            <Calendar mode="single" selected={startDate} onSelect={(date) => date && setStartDate(date)} />
                        </PopoverContent>
                    </Popover>
                </div>

                <div>
                    <label className="text-sm font-medium">Дата і час повернення</label>
                    <Popover>
                        <PopoverTrigger asChild>
                            <Button variant="outline" className="w-full flex justify-between">
                                {endDate ? format(endDate, "dd.MM.yyyy") : "дд.мм.рррр --:--"}
                            </Button>
                        </PopoverTrigger>
                        <PopoverContent>
                            <Calendar
                                mode="single"
                                selected={endDate}
                                onSelect={(date) => {
                                    if (!date) return;
                                    if (startDate && isBefore(date, startDate)) return;
                                    setEndDate(date);
                                }}
                                disabled={(date) => startDate ? isBefore(date, startDate) : false}
                            />
                        </PopoverContent>
                    </Popover>
                </div>
            </div>

            <div className="mt-4 flex justify-between items-center">
                <a href="#" className="text-sm text-red-500">+ Більше опцій</a>
                <Button className="bg-orange-500 hover:bg-orange-600" onClick={handleSearchButtonClick}>Підібрати авто</Button>
            </div>
        </div>
    );
}