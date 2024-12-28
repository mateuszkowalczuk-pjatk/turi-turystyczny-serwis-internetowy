import { useEffect } from 'react'
import { touristicPlaceService } from '../services/touristicPlaceService.ts'

export const useTouristicPlace = (
    setTouristicPlaceId: (value: ((prevState: number | undefined) => number | undefined) | number | undefined) => void
) => {
    useEffect(() => {
        const fetchTouristicPlace = async () => {
            const touristicPlaceResponse = await touristicPlaceService.getByPremiumId()
            if (touristicPlaceResponse.status === 200) {
                const touristicPlaceResult = await touristicPlaceResponse.json()
                setTouristicPlaceId(touristicPlaceResult.touristicPlaceId)
            }
        }

        fetchTouristicPlace().catch((error) => error)
    }, [setTouristicPlaceId])
}
