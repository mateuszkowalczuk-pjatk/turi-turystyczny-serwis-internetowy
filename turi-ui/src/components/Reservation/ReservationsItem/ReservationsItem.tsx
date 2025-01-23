import { ReservationDto } from '../../../types/reservation.ts'
import styles from './ReservationsItem.module.css'
import ImagePanel from '../../Shared/Image/ImagePanel'
import TextRegular from '../../Shared/Controls/Text/TextRegular'
import { TouristicPlace } from '../../../types/touristicPlace.ts'
import { useImage } from '../../../hooks/shared/useImage.ts'
import { ImageMode } from '../../../types/image.ts'
import { useEffect, useState } from 'react'
import { Stay } from '../../../types/stay.ts'
import { useHooks } from '../../../hooks/shared/useHooks.ts'
import { Address } from '../../../types'
import { stayService } from '../../../services/stayService.ts'
import { attractionService } from '../../../services/attractionService.ts'
import { Attraction } from '../../../types/attraction.ts'
import { touristicPlaceService } from '../../../services/touristicPlaceService.ts'
import { addressService } from '../../../services/addressService.ts'
import TextMedium from '../../Shared/Controls/Text/TextMedium'
import { handleDateDisplay } from '../../../utils/handleDateTimeDisplay.ts'
import TextExtraLight from '../../Shared/Controls/Text/TextExtraLight'
import { handleReservationStatusDisplay } from '../../../utils/handleReservationStatusDisplay.ts'

const ReservationsItem = ({ reservation }: { reservation: ReservationDto }) => {
    const { t, navigate } = useHooks()
    const { images } = useImage(reservation.reservation.stayId, ImageMode.STAY)
    const image = images.length > 0 ? images[0] : null
    const [stay, setStay] = useState<Stay>()
    const [touristicPlace, setTouristicPlace] = useState<TouristicPlace>()
    const [attractions, setAttractions] = useState<Attraction[]>()

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
                const filteredAttractions = attractionsData.filter((attractionData) =>
                    attractionsData
                        .map((attractionData) => attractionData.attractionId)
                        .includes(attractionData.attractionId)
                )
                setAttractions(filteredAttractions)
            }
        }
        fetchData().catch((error) => error)
    }, [reservation.reservation.stayId, stay])

    const handleNavigate = () => {
        navigate('/reservation/offer', { state: { reservation: reservation } })
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
                {/*<TextRegular text={address?.country + ', ' + address?.city} />*/}
            </div>
            <div className={styles.attractions}>
                <p className={styles.attraction}>{t('offer.attraction')}</p>
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
                    {reservation.reservation.price}
                    {t('reservation.currency')}
                </p>
            </div>
        </div>
    )
}

export default ReservationsItem
