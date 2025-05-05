import { sendRequest } from "@/lib/utils";

export const sendSearchRequest = async (searchParams: Object) => {
	let response: { data: any } = await sendRequest({
		url: '/api/v1/cars',
		method: 'POST',
		withCredentials: false,
		headers: {
			'Content-Type': 'application/json',
		},
		data: JSON.stringify({
			searchParams
		}),
	})

	return response.data;
}
