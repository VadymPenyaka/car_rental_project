import { settings } from '@/config/setting'
import axios, { AxiosRequestConfig } from 'axios'

interface ApiProps extends AxiosRequestConfig {
	path?: string
}
export const apiRequest = async (apiData: ApiProps) => {
	const { path, headers, baseURL, ...rest } = apiData
	const { API_BASE_URL, apiDebugging } = settings

	const callURL = (baseURL || API_BASE_URL) + '/' + path
	let customHeaders = {}
	// Auth process here

	customHeaders = { ...customHeaders, ...headers }
	const apiConfig = { url: callURL, headers: customHeaders, ...rest }

	if (apiDebugging) {
		console.log(`API: Request - ${path}: `, apiConfig)
	}

	try {
		const res = await axios(apiConfig)

		if (apiDebugging) {
			console.log(`API: Response - ${path}: `, res)
		}

		return res.data
	} catch (error) {
		console.error(`API: Error - ${path}:`, error)
		throw error
	}
}