import { useHooks } from '../../../hooks/shared/useHooks.ts'
import { useImage } from '../../../hooks/shared/useImage.ts'
import { handleDateDisplay } from '../../../utils/handleDateTimeDisplay.ts'
import { useEffect, useState } from 'react'
import { handleReservationStatusDisplay } from '../../../utils/handleReservationStatusDisplay.ts'
import ImagePanel from '../../Shared/Image/ImagePanel'
import TextMedium from '../../Shared/Controls/Text/TextMedium'
import TextRegular from '../../Shared/Controls/Text/TextRegular'
import TextExtraLight from '../../Shared/Controls/Text/TextExtraLight'
import { Stay } from '../../../types/stay.ts'
import { ImageMode } from '../../../types/image.ts'
import { Attraction } from '../../../types/attraction.ts'
import { TouristicPlace } from '../../../types/touristicPlace.ts'
import { ReservationDto } from '../../../types/reservation.ts'
import { stayService } from '../../../services/stayService.ts'
import { attractionService } from '../../../services/attractionService.ts'
import { reservationService } from '../../../services/reservationService.ts'
import { touristicPlaceService } from '../../../services/touristicPlaceService.ts'
import styles from './ReservationsItem.module.css'

const ReservationsItem = ({ reservation }: { reservation: ReservationDto }) => {
    const { t, navigate } = useHooks()
    const { images } = useImage(reservation.reservation.stayId, ImageMode.STAY)
    const image = images.length > 0 ? images[0] : null
    const [stay, setStay] = useState<Stay>()
    const [touristicPlace, setTouristicPlace] = useState<TouristicPlace>()
    const [attractions, setAttractions] = useState<Attraction[]>()
    const [price, setPrice] = useState<number>()

    useEffect(() => {
        const fetchData = async () => {
            const stayResponse = await stayService.getById(reservation.reservation.stayId)
            const stayData: Stay = await stayResponse.json()
            setStay(stayData)

            const touristicPlaceResponse = await touristicPlaceService.getById(stayData.touristicPlaceId)
            const touristicPlaceData: TouristicPlace = await touristicPlaceResponse.json()
            setTouristicPlace(touristicPlaceData)

            if (touristicPlaceData.touristicPlaceId) {
                const attractionsResponse = await attractionService.getAllByTouristicPlaceId(
                    touristicPlaceData.touristicPlaceId
                )
                const attractionsData: Attraction[] = await attractionsResponse.json()
                const filteredAttractions = attractionsData.filter((attraction) =>
                    reservation.attractions
                        .map((resAttr) => resAttr.attractionId)
                        .includes(attraction.attractionId || 0)
                )
                setAttractions(filteredAttractions)
            }

            const priceResponse = await reservationService.getPrice(reservation.reservation.reservationId)
            const priceData: number = await priceResponse.json()
            setPrice(priceData)
        }
        fetchData().catch((error) => error)
    }, [reservation.reservation.stayId, stay])

    const handleNavigate = () => {
        navigate('/reservation/offer', {
            state: { reservation: reservation, touristicPlace: touristicPlace, stay: stay, isOwner: false, plan: true }
        })
    }

    return (
        <div className={styles.item}>
            <div
                className={styles.image}
                onClick={handleNavigate}
            >
                {image && image.path && (
                    <ImagePanel
                        path={image.path}
                        onlyDisplay
                    />
                )}
            </div>
            <div className={styles.name}>
                {touristicPlace && (
                    <div className={styles.favourite}>
                        <TextRegular
                            text={touristicPlace.name || ''}
                            onClick={handleNavigate}
                        />
                    </div>
                )}
                {stay && <TextExtraLight text={stay.name} />}
                <TextRegular
                    text={
                        handleDateDisplay(reservation.reservation.dateFrom.toString()) +
                        ' - ' +
                        handleDateDisplay(reservation.reservation.dateTo.toString())
                    }
                />
            </div>
            <div className={styles.attractions}>
                {attractions && attractions.length !== 0 && (
                    <p className={styles.attraction}>{t('offer.attraction')}</p>
                )}
                <ul>
                    {attractions &&
                        attractions.map((attraction, index) => (
                            <li
                                key={index}
                                className={styles.customListItem}
                            >
                                <span className={styles.customStep}></span>
                                <TextRegular text={attraction.name} />
                            </li>
                        ))}
                </ul>
            </div>
            <div className={styles.details}>
                <TextMedium text={handleReservationStatusDisplay(reservation.reservation.status, t)} />
                <p className={styles.attraction}>
                    {t('offer.price')}
                    {price}
                    {t('reservation.currency')}
                </p>
            </div>
        </div>
    )
}

export default ReservationsItem
