import { useEffect, useState } from 'react'
import { touristicPlaceService } from '../../services/touristicPlaceService.ts'

export const useTouristicPlace = () => {
    const [touristicPlaceId, setTouristicPlaceId] = useState<number>()

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

    return { touristicPlaceId }
}
