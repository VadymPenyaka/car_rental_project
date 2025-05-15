import { useSearchParams } from 'react-router-dom'

export function useQueryParams(): Record<string, string> {
  const [searchParams] = useSearchParams()

  const params: Record<string, string> = {}
  for (const [key, value] of searchParams.entries()) {
    params[key] = value
  }

  return params
}