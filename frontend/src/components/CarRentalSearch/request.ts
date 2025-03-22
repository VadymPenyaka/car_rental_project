import { sendRequest } from "@/lib/utils";

interface SearchParams {
    startDate: Date;
    endDate: Date;
    location: string;
}

export const sendSearchRequest = async (searchParams: SearchParams) => {
    const {
        startDate,
        endDate,
        location
    } = searchParams

    let response: { data: any } = await sendRequest({
        url: 'https://peniaka.site/api/v1/cars',
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        data: JSON.stringify({
            startDate: startDate.toISOString(),
            endDate: endDate.toISOString(),
            location
        }),
    })
    
    return response.data;
}
