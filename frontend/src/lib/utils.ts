import { type ClassValue, clsx } from "clsx"
import { twMerge } from "tailwind-merge"
import axios, { AxiosRequestConfig, AxiosResponse } from 'axios'

export function cn(...inputs: ClassValue[]) {
  return twMerge(clsx(inputs))
}

interface RequestOptions extends AxiosRequestConfig {
  url: string;
  method: 'GET' | 'POST' | 'PUT' | 'DELETE';
  data?: any;
  headers?: Record<string, string>;
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