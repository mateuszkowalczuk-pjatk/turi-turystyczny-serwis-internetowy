import { useEffect, useState } from 'react'
import TourismAttractionOfferItem from '../TourismAttractionOfferItem'
import TourismStayOfferItem from '../TourismStayOfferItem'
import { Attraction } from '../../../types/attraction.ts'
import { Stay } from '../../../types/stay.ts'
import { attractionService } from '../../../services/attractionService.ts'
import { stayService } from '../../../services/stayService.ts'
import styles from './TourismOffers.module.css'

const TourismOffers = ({ touristicPlaceId }: { touristicPlaceId: number }) => {
    const [stays, setStays] = useState<Stay[]>()
    const [attractions, setAttractions] = useState<Attraction[]>()

    useEffect(() => {
        const fetchStays = async () => {
            const staysResponse = await stayService.getAllByTouristicPlaceId(touristicPlaceId)
            if (staysResponse.status === 200) {
                const staysData = await staysResponse.json()
                setStays(staysData)
            }
            const attractionsResponse = await attractionService.getAllByTouristicPlaceId(touristicPlaceId)
            if (attractionsResponse.status === 200) {
                const attractionsData = await attractionsResponse.json()
                setAttractions(attractionsData)
            }
        }

        fetchStays().catch((error) => error)
    }, [])

    return (
        <div className={styles.offers}>
            {stays &&
                stays.map((stay, index) => (
                    <TourismStayOfferItem
                        stay={stay}
                        index={index}
                        key={index}
                    />
                ))}
            {attractions &&
                attractions.map((attraction, index) => (
                    <TourismAttractionOfferItem
                        attraction={attraction}
                        index={index}
                        key={index}
                    />
                ))}
        </div>
    )
}

export default TourismOffers
