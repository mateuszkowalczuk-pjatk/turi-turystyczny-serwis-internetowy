import { touristicPlaceTypeHandler } from '../../../utils/touristicPlaceTypeHandler.ts'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { useEffect, useState } from 'react'
import { useTranslation } from 'react-i18next'
import { useNavigate } from 'react-router-dom'
import ImagePanel from '../../Shared/Image/ImagePanel'
import TextRegular from '../../Shared/Controls/Text/TextRegular'
import { Offer } from '../../../types/offer.ts'
import { TouristicPlaceType } from '../../../types/touristicPlace.ts'
import { Address } from '../../../types'
import { Image } from '../../../types/image.ts'
import { faHeart as solidHeart } from '@fortawesome/free-solid-svg-icons'
import { faHeart as regularHeart } from '@fortawesome/free-regular-svg-icons'
import { addressService } from '../../../services/addressService.ts'
import { imageService } from '../../../services/imageService.ts'
import { offerService } from '../../../services/offerService.ts'
import { faStar } from '@fortawesome/free-solid-svg-icons'
import styles from './SearchOffersItem.module.css'

const SearchOffersItem = ({ offer }: { offer: Offer }) => {
    const { t } = useTranslation()
    const navigate = useNavigate()
    const [image, setImage] = useState<Image>()
    const [address, setAddress] = useState<Address>()
    const [isFavourite, setIsFavourite] = useState<boolean>(false)
    const [rating, setRating] = useState<number | null>(null)

    const toggleFavorite = async () => {
        if (!isFavourite && offer.touristicPlace.touristicPlaceId) {
            await offerService.addFavouriteForAccount(offer.touristicPlace.touristicPlaceId)
        } else if (offer.touristicPlace.touristicPlaceId) {
            await offerService.deleteFavouriteForAccount(offer.touristicPlace.touristicPlaceId)
        }
        setIsFavourite((prev) => !prev)
    }

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
                const favouriteResponse = await offerService.isFavouriteForAccount(
                    offer.touristicPlace.touristicPlaceId
                )
                const favouriteData: boolean = await favouriteResponse.json()
                setIsFavourite(favouriteData)
                // ToDo - Pobranie średniej oceny gości, którzy zrealizowali i ocenili pobyt
            }
        }

        fetchData().catch((error) => error)
    }, [])

    return (
        <div className={styles.item}>
            <div
                className={styles.image}
                onClick={() =>
                    navigate('/offer', {
                        state: { offer: offer }
                    })
                }
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
                    <p
                        className={styles.header}
                        onClick={() =>
                            navigate('/offer', {
                                state: { offer: offer }
                            })
                        }
                    >
                        {offer.touristicPlace.name || ''}
                    </p>

                    <button
                        onClick={toggleFavorite}
                        style={{
                            background: 'none',
                            border: 'none',
                            cursor: 'pointer',
                            color: isFavourite ? 'green' : 'black',
                            fontSize: '1.5rem'
                        }}
                    >
                        <FontAwesomeIcon icon={isFavourite ? solidHeart : regularHeart} />
                    </button>
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
                <p className={styles.attraction}>{'Atrakcje'}</p>
                <ul>
                    {offer.attractions.length === 0 && <p>Nie znaleziono atrakcji</p>}
                    {offer.attractions[0] && (
                        <li>
                            <TextRegular text={offer.attractions[0].name} />
                        </li>
                    )}
                    {offer.attractions[1] && (
                        <li>
                            <TextRegular text={offer.attractions[1].name} />
                        </li>
                    )}
                    {offer.attractions[2] && (
                        <li>
                            <TextRegular text={offer.attractions[2].name} />
                        </li>
                    )}
                </ul>
            </div>
            <div className={styles.details}>
                <div className={styles.rating}>
                    {!rating && <p>Brak oceny gości</p>}
                    {rating && <p>Ocena gości: {rating}</p>}
                    <FontAwesomeIcon
                        icon={faStar}
                        style={{ color: 'green', fontSize: '1.5rem' }}
                    />
                </div>
                <p className={styles.attraction}>Cena {offer.stays[0].price} zł za noc</p>
                <p>Pokój {offer.stays[0].peopleNumber} osobowy</p>
            </div>
        </div>
    )
}

export default SearchOffersItem
