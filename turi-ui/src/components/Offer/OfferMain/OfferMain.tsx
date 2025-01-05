import { useEffect, useState } from 'react'
import { useTranslation } from 'react-i18next'
import ImageBanner from '../../Shared/Image/ImageBanner'
import TextRegular from '../../Shared/Controls/Text/TextRegular'
import Favourite from '../../Shared/Controls/Favourite'
import Rating from '../../Shared/Controls/Rating'
import { TouristicPlace } from '../../../types/touristicPlace.ts'
import { Address } from '../../../types'
import { Image } from '../../../types/image.ts'
import { addressService } from '../../../services/addressService.ts'
import { imageService } from '../../../services/imageService.ts'
import styles from './OfferMain.module.css'

const OfferMain = ({ touristicPlace }: { touristicPlace: TouristicPlace }) => {
    const { t } = useTranslation()
    const [images, setImages] = useState<Image[]>([])
    const [address, setAddress] = useState<Address>()

    useEffect(() => {
        const fetchData = async () => {
            if (touristicPlace.touristicPlaceId) {
                const imagesResponse = await imageService.getAllByTouristicPlaceId(touristicPlace.touristicPlaceId)
                const imagesData: Image[] = await imagesResponse.json()
                setImages(imagesData)
            }
            if (touristicPlace.addressId) {
                const addressResponse = await addressService.getById(touristicPlace.addressId)
                const addressData: Address = await addressResponse.json()
                setAddress(addressData)
            }
        }
        fetchData().catch((error) => error)
    }, [touristicPlace.addressId, touristicPlace.touristicPlaceId])

    return (
        <div className={styles.main}>
            <ImageBanner
                images={images}
                presentation
            />
            <div className={styles.data}>
                <div className={styles.information}>
                    <div className={styles.name}>
                        <TextRegular text={touristicPlace.name || ''} />
                        <Favourite touristicPlaceId={touristicPlace.touristicPlaceId} />
                    </div>
                    {address && (
                        <TextRegular
                            text={
                                t('offer.street') +
                                address.street +
                                ' ' +
                                address.buildingNumber +
                                (address.apartmentNumber ? '/' + address.apartmentNumber : '') +
                                ', ' +
                                address.zipCode +
                                ' ' +
                                address?.city +
                                ', ' +
                                address.country
                            }
                        />
                    )}
                </div>
                <div className={styles.rating}>
                    <Rating touristicPlaceId={touristicPlace.touristicPlaceId} />
                </div>
            </div>
        </div>
    )
}

export default OfferMain
