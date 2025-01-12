import { useEffect, useState } from 'react'
import ImageBanner from '../../Shared/Image/ImageBanner'
import TouristicPlaceBanner from '../../Shared/TouristicPlaceBanner/TouristicPlaceBanner/TouristicPlaceBanner.tsx'
import { TouristicPlace } from '../../../types/touristicPlace.ts'
import { Address } from '../../../types'
import { Image } from '../../../types/image.ts'
import { addressService } from '../../../services/addressService.ts'
import { imageService } from '../../../services/imageService.ts'
import styles from './OfferMain.module.css'

const OfferMain = ({ touristicPlace }: { touristicPlace: TouristicPlace }) => {
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
            <TouristicPlaceBanner
                touristicPlace={touristicPlace}
                address={address}
            />
        </div>
    )
}

export default OfferMain
