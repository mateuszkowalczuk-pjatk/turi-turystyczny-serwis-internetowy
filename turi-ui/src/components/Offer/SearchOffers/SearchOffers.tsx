import { useCallback, useEffect, useRef, useState } from 'react'
import { useTranslation } from 'react-i18next'
import SearchOffersItem from '../SearchOffersItem'
import { Search, Offer } from '../../../types/offer.ts'
import { offerService } from '../../../services/offerService.ts'
import styles from './SearchOffers.module.css'

interface Props {
    offers: Offer[]
    setOffers: (value: ((prevState: Offer[]) => Offer[]) | Offer[]) => void
    touristicPlaceId: string | null
    setTouristicPlaceId: (value: ((prevState: string | null) => string | null) | string | null) => void
    rank: string | null
    setRank: (value: ((prevState: string | null) => string | null) | string | null) => void
    mode: string
    query: string | ''
    dateFrom: string | null
    dateTo: string | null
    touristicPlaceType: string | null
    attractionType: string | null
}

const SearchOffers = ({
    offers,
    touristicPlaceId,
    rank,
    setOffers,
    setTouristicPlaceId,
    setRank,
    mode,
    query,
    dateFrom,
    dateTo,
    touristicPlaceType,
    attractionType
}: Props) => {
    const { t } = useTranslation()
    const [loading, setLoading] = useState(false)
    const [cursor, setCursor] = useState(true)
    const loader = useRef<HTMLDivElement | null>(null)

    const fetchOffers = async () => {
        if (!rank || !touristicPlaceId || loading) return

        setLoading(true)

        const offersResponse = await offerService.search(
            mode,
            touristicPlaceId,
            rank,
            query,
            dateFrom,
            dateTo,
            touristicPlaceType,
            attractionType
        )
        const offersData: Search = await offersResponse.json()
        if (offersData.offers.length > 0) {
            setOffers((prevOffers) => [...prevOffers, ...offersData.offers])
            setTouristicPlaceId(offersData.touristicPlaceId.toString())
            setRank(offersData.rank.toString())
        } else setCursor(false)

        if (offersData.touristicPlaceId === null || offersData.rank === null) setCursor(false)

        setLoading(false)
    }

    const handleObserver = useCallback(
        (entries: IntersectionObserverEntry[]) => {
            const target = entries[0]
            if (target.isIntersecting && cursor) fetchOffers()
        },
        [cursor, fetchOffers]
    )

    useEffect(() => {
        if (rank === null || touristicPlaceId === null) setCursor(false)

        const observer = new IntersectionObserver(handleObserver, {
            root: null,
            rootMargin: '20px',
            threshold: 1.0
        })
        if (loader.current) observer.observe(loader.current)

        return () => {
            if (loader.current) observer.unobserve(loader.current)
        }
    }, [handleObserver, rank, touristicPlaceId])

    return (
        <div className={styles.offers}>
            <div className={styles.panel}>
                {offers.map((offer, key) => (
                    <SearchOffersItem
                        key={key}
                        offer={offer}
                        dateFrom={dateFrom}
                        dateTo={dateTo}
                    />
                ))}
                <div
                    ref={loader}
                    className={styles.loader}
                />
                {offers.length === 0 && <div className={styles.empty}>{t('offer.offers-not-found')}</div>}
            </div>
        </div>
    )
}

export default SearchOffers
