import { useState } from "react";
import { Button } from "@/components/ui/button";
import { Popover, PopoverContent, PopoverTrigger } from "@/components/ui/popover";
import { Calendar } from "@/components/ui/calendar";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import { format, isBefore } from "date-fns";

export default function CarRentalForm() {
    const [pickupDate, setPickupDate] = useState<Date>(new Date());
    const [returnDate, setReturnDate] = useState<Date>(new Date());
    const [selectedCity, setSelectedCity] = useState<string | undefined>();
    const cities = ["Київ", "Львів", "Одеса", "Дніпро", "Харків"];

    const handleSearchButtonClick = () => {
        console.log("States:", pickupDate, returnDate, selectedCity);
    }

    return (
        <div className="bg-white p-6 rounded-2xl shadow-lg w-full max-w-2xl">
            <h2 className="text-lg font-semibold mb-4">Підібрати авто в прокат</h2>

            <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
                <div>
                    <label className="text-sm font-medium">Місто видачі</label>
                    <div className="relative mt-1">
                        <Select onValueChange={setSelectedCity}>
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
                                {pickupDate ? format(pickupDate, "dd.MM.yyyy") : "дд.мм.рррр --:--"}
                            </Button>
                        </PopoverTrigger>
                        <PopoverContent>
                            <Calendar mode="single" selected={pickupDate} onSelect={(date) => date && setPickupDate(date)} />
                        </PopoverContent>
                    </Popover>
                </div>

                <div>
                    <label className="text-sm font-medium">Дата і час повернення</label>
                    <Popover>
                        <PopoverTrigger asChild>
                            <Button variant="outline" className="w-full flex justify-between">
                                {returnDate ? format(returnDate, "dd.MM.yyyy") : "дд.мм.рррр --:--"}
                            </Button>
                        </PopoverTrigger>
                        <PopoverContent>
                            <Calendar
                                mode="single"
                                selected={returnDate}
                                onSelect={(date) => {
                                    if (!date) return;
                                    if (pickupDate && isBefore(date, pickupDate)) return;
                                    setReturnDate(date);
                                }}
                                disabled={(date) => pickupDate ? isBefore(date, pickupDate) : false}
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