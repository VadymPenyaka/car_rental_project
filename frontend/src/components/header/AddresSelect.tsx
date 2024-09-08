import React from 'react'
import {
    Select,
    SelectContent,
    SelectGroup,
    SelectItem,
    SelectLabel,
    SelectTrigger,
    SelectValue,
} from "@/components/ui/select"

export const AddresSelect = () => {

    const locations = [
        { label: "lviv", name: "Львів" },
        { label: "kyiv", name: "Київ" },
        { label: "ternopil", name: "Тернопіль" },
    ]

    return (
        <div className='flex items-center'>
            <p className='m-2'>
                Location
            </p>
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
