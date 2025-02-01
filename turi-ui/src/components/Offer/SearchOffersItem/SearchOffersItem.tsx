import { useHooks } from '../../../hooks/shared/useHooks.ts'
import { useEffect, useState } from 'react'
import { touristicPlaceTypeHandler } from '../../../utils/touristicPlaceTypeHandler.ts'
import Rating from '../../Shared/Controls/Rating'
import Favourite from '../../Shared/Controls/Favourite'
import ImagePanel from '../../Shared/Image/ImagePanel'
import TextRegular from '../../Shared/Controls/Text/TextRegular'
import { Offer } from '../../../types/offer.ts'
import { Image } from '../../../types/image.ts'
import { Address } from '../../../types'
import { TouristicPlaceType } from '../../../types/touristicPlace.ts'
import { imageService } from '../../../services/imageService.ts'
import { addressService } from '../../../services/addressService.ts'
import styles from './SearchOffersItem.module.css'

interface Props {
    offer: Offer
    dateFrom?: string | null
    dateTo?: string | null
}

const SearchOffersItem = ({ offer, dateFrom, dateTo }: Props) => {
    const { t, navigate } = useHooks()
    const [image, setImage] = useState<Image>()
    const [address, setAddress] = useState<Address>()

    useEffect(() => {
        const fetchData = async () => {
            if (offer.touristicPlace.touristicPlaceId) {
                const imagesResponse = await imageService.getAllByTouristicPlaceId(
                    offer.touristicPlace.touristicPlaceId
                )
                if (imagesResponse.status === 200) {
                    const imagesData = await imagesResponse.json()
                    setImage(imagesData[0])
                }
                if (offer.touristicPlace.addressId) {
                    const addressResponse = await addressService.getById(offer.touristicPlace.addressId)
                    if (addressResponse.status === 200) {
                        const addressData = await addressResponse.json()
                        setAddress(addressData)
                    }
                }
            }
        }

        fetchData().catch((error) => error)
    }, [])

    const handleNavigate = () => {
        navigate('/offer', {
            state: {
                offer: offer,
                dateFrom: dateFrom,
                dateTo: dateTo,
                url: `${window.location.pathname}${window.location.search}`
            }
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
                <div className={styles.favourite}>
                    <TextRegular
                        text={offer.touristicPlace.name || ''}
                        onClick={handleNavigate}
                    />
                    <Favourite touristicPlaceId={offer.touristicPlace.touristicPlaceId} />
                </div>
                <TextRegular
                    text={touristicPlaceTypeHandler(
                        offer.touristicPlace.touristicPlaceType || TouristicPlaceType.UNASSIGNED,
                        t
                    )}
                />
                <TextRegular text={address?.country + ', ' + address?.city} />
            </div>
            <div className={styles.attractions}>
                {offer.attractions.length !== 0 && <p className={styles.attraction}>{t('offer.attraction')}</p>}
                <ul>
                    {offer.attractions[0] && (
                        <li
                            key={offer.attractions[0].attractionId}
                            className={styles.customListItem}
                        >
                            <span className={styles.customStep}></span>
                            <TextRegular text={offer.attractions[0].name} />
                        </li>
                    )}
                    {offer.attractions[1] && (
                        <li
                            key={offer.attractions[1].attractionId}
                            className={styles.customListItem}
                        >
                            <span className={styles.customStep}></span>
                            <TextRegular text={offer.attractions[1].name} />
                        </li>
                    )}
                    {offer.attractions[2] && (
                        <li
                            key={offer.attractions[2].attractionId}
                            className={styles.customListItem}
                        >
                            <span className={styles.customStep}></span>
                            <TextRegular text={offer.attractions[2].name} />
                        </li>
                    )}
                </ul>
            </div>
            <div className={styles.details}>
                <Rating touristicPlaceId={offer.touristicPlace.touristicPlaceId} />
                <p className={styles.attraction}>
                    {t('offer.price')}
                    {offer.stays[0].price}
                    {t('offer.currency')}
                </p>
                <p>
                    {t('offer.room')}
                    {offer.stays[0].peopleNumber}
                    {t('offer.people-number')}
                </p>
            </div>
        </div>
    )
}

export default SearchOffersItem
