// src/lib/utils.ts
import { type ClassValue, clsx } from "clsx"
import { twMerge } from "tailwind-merge"
import axios, { AxiosRequestConfig, AxiosResponse } from "axios"

export function cn(...inputs: ClassValue[]) {
	return twMerge(clsx(inputs))
}

const api = axios.create({
	withCredentials: true,
})

api.interceptors.request.use((config) => {
	const token = localStorage.getItem("accessToken")
	if (token && config.headers) {
		config.headers.Authorization = `Bearer ${token}`
	}
	return config
})

api.interceptors.response.use(
	(res) => res,
	async (error) => {
		const original = error.config as AxiosRequestConfig & { _retry?: boolean }
		if (
			error.response?.status === 401 &&
			!original._retry &&
			!original.url?.includes("/auth/refresh")
		) {
			original._retry = true
			try {
				const { data } = await api.post("/api/v1/auth/refresh")
				localStorage.setItem("accessToken", data.accessToken)
				if (original.headers) {
					original.headers.Authorization = `Bearer ${data.accessToken}`
				}
				return api(original)
			} catch (refreshErr) {
				window.location.href = '/login'
				return Promise.reject(refreshErr)
			}
		}
		return Promise.reject(error)
	}
)

interface RequestOptions extends AxiosRequestConfig {
	url: string
	method: "GET" | "POST" | "PUT" | "DELETE"
	data?: any
	headers?: Record<string, string>
}

export const sendRequest = async (options: RequestOptions): Promise<AxiosResponse<any>> => {
	const { url, method, data, headers } = options;

	try {
		console.log(`Sending ${method} request to ${url} with data:`, data);

		const response = await axios({
			url,
			method,
			data,
			headers,
		});

		console.log(`Received response from ${url}:`, response.data);

		return response;
	} catch (error) {
		console.error(`Error during ${method} request to ${url}:`, error);
		throw error;
	}
};

export const formatDate = (date: Date): string => {
	const y = date.getFullYear()
	const m = String(date.getMonth() + 1).padStart(2, '0')
	const d = String(date.getDate()).padStart(2, '0')
	return `${y}-${m}-${d}` as string
}
