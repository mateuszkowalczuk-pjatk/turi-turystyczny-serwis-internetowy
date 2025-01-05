import { useEffect, useState } from 'react'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faHeart as solid } from '@fortawesome/free-solid-svg-icons'
import { faHeart as regular } from '@fortawesome/free-regular-svg-icons'
import { offerService } from '../../../../services/offerService.ts'
import styles from './Favourite.module.css'

const Favourite = ({ touristicPlaceId }: { touristicPlaceId: number | undefined }) => {
    const [isFavourite, setIsFavourite] = useState<boolean>(false)

    useEffect(() => {
        const fetchData = async () => {
            if (touristicPlaceId) {
                const response = await offerService.isFavouriteForAccount(touristicPlaceId)
                const data: boolean = await response.json()
                setIsFavourite(data)
            }
        }
        fetchData().catch((error) => error)
    }, [touristicPlaceId])

    const toggle = async () => {
        if (!isFavourite && touristicPlaceId) await offerService.addFavouriteForAccount(touristicPlaceId)
        else if (touristicPlaceId) await offerService.deleteFavouriteForAccount(touristicPlaceId)
        setIsFavourite((prev) => !prev)
    }

    return (
        <button
            onClick={toggle}
            className={`${styles.favourite} ${isFavourite ? styles.isFavourite : ''}`}
        >
            <FontAwesomeIcon icon={isFavourite ? solid : regular} />
        </button>
    )
}

export default Favourite
