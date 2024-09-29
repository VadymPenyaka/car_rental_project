import {
    Select,
    SelectContent,
    SelectItem,
    SelectTrigger,
    SelectValue,
} from "@/components/ui/select"

import location from '../../assets/location.svg'
import Logo from "../ui/Logo"

export const AddresSelect = () => {

    const locations = [
        { label: "lviv", name: "Львів" },
        { label: "kyiv", name: "Київ" },
        { label: "ternopil", name: "Тернопіль" },
    ]

    return (
        <div className='flex items-center'>
            <Logo
                src={location}
                width='14rem'
            />
            <Select>
                <SelectTrigger className='w-[180px]'>
                    <SelectValue placeholder="Location" onChange={(e) => console.log(e)} />
                </SelectTrigger>
                <SelectContent>
                    {locations.map(loc =>
                        <SelectItem value={loc.label} key={loc.label}>
                            {loc.name}
                        </SelectItem>)
                    }
                </SelectContent>
            </Select>
        </div>
    )
}
