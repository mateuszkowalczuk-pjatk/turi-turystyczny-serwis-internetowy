// import { useState, useEffect, useCallback } from 'react'
// import { API_BASE_URL } from '../config/api'
//
// interface FetchOptions extends RequestInit {
//     body?: never
// }
//
// const useFetch = <T>(url: string, options?: FetchOptions) => {
//     const [data, setData] = useState<T | null>(null)
//     const [error, setError] = useState<Error | null>(null)
//     const [loading, setLoading] = useState(false)
//
//     const fetchData = useCallback(async () => {
//         setLoading(true)
//         const controller = new AbortController()
//         const { signal } = controller
//
//         try {
//             const response = await fetch(`${API_BASE_URL}${url}`, {
//                 ...options,
//                 credentials: 'include',
//                 headers: {
//                     'Content-Type': 'application/json',
//                     ...options?.headers
//                 },
//                 signal
//             })
//
//             if (!response.ok) {
//                 throw new Error(`HTTP error! status: ${response.status}`)
//             }
//
//             const result: T = await response.json()
//             setData(result)
//         } catch (err) {
//             if (err instanceof Error && err.name !== 'AbortError') {
//                 setError(err)
//             }
//         } finally {
//             setLoading(false)
//         }
//
//         return () => controller.abort()
//     }, [url, options])
//
//     useEffect(() => {
//         fetchData()
//     }, [fetchData])
//
//     return { data, error, loading, fetchData }
// }
//
// export default useFetch
