import { useEffect, useState } from 'react'
import SearchOffersItem from '../SearchOffersItem'
import { Offer } from '../../../types/offer.ts'
import { offerService } from '../../../services/offerService.ts'
import styles from './FavouriteOffers.module.css'
import { useHooks } from '../../../hooks/shared/useHooks.ts'

const FavouriteOffers = () => {
    const { t } = useHooks()
    const [offers, setOffers] = useState<Offer[]>([])

    useEffect(() => {
        const fetchOffers = async () => {
            const offersResponse = await offerService.getAllFavouriteByAccount()
            const offersData: Offer[] = await offersResponse.json()
            setOffers(offersData)
        }
        fetchOffers().catch((error) => error)
    }, [])

    return (
        <div className={styles.panel}>
            {offers.map((offer, key) => (
                <SearchOffersItem
                    key={key}
                    offer={offer}
                />
            ))}
            {offers.length === 0 && <div className={styles.empty}>{t('offer.offers-not-found')}</div>}
        </div>
    )
}

export default FavouriteOffers
