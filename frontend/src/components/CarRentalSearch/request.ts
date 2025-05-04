import { sendRequest } from "@/lib/utils";

interface SearchParams {
	startDate: Date;
	endDate: Date;
	location: string;
}

export const sendSearchRequest = async (searchParams: Partial<SearchParams>) => {
	// const {
	// 	startDate,
	// 	endDate,
	// 	location
	// } = searchParams

	let response: { data: any } = await sendRequest({
		url: '/api/v1/cars',
		method: 'POST',
		withCredentials: false,
		headers: {
			'Content-Type': 'application/json',
		},
		data: JSON.stringify({
			
		}),
	})

	return response.data;
}
