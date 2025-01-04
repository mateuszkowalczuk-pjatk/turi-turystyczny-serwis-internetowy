import { useEffect, useState } from 'react'
import { useLocation } from 'react-router-dom'
import SearchBrowser from '../../../components/Offer/SearchBrowser'
import SearchFilter from '../../../components/Offer/SearchFilter'
import SearchOffers from '../../../components/Offer/SearchOffers'
import { Search, SearchMode, SearchTouristicPlaces } from '../../../types/search.ts'
import { searchService } from '../../../services/searchService.ts'
import styles from '../../Page.module.css'

const SearchPage = () => {
    const location = useLocation()
    const urlSearchParams = (query: string | string[][] | Record<string, string> | URLSearchParams | undefined) => {
        return new URLSearchParams(query)
    }
    const params = urlSearchParams(location.search)
    const mode = params.get('mode') || SearchMode.ALL
    const query = params.get('query') || ''
    const dateFrom = params.get('dateFrom') || null
    const dateTo = params.get('dateTo') || null
    const touristicPlaceType = params.get('touristicPlaceType') || null
    const attractionType = params.get('attractionType') || null
    const [touristicPlaceId, setTouristicPlaceId] = useState<string | null>(null)
    const [rank, setRank] = useState<string | null>(null)
    const [offers, setOffers] = useState<SearchTouristicPlaces[]>([])
    const [loading, setLoading] = useState(true)

    useEffect(() => {
        const fetchOffers = async () => {
            setOffers([])
            const offersResponse = await searchService.search(
                mode,
                null,
                null,
                query,
                dateFrom,
                dateTo,
                touristicPlaceType,
                attractionType
            )
            const offersData: Search = await offersResponse.json()
            if (offersData.searchTouristicPlaces.length !== 0) {
                setTouristicPlaceId(offersData.touristicPlaceId.toString())
                setRank(offersData.rank.toString())
                setOffers(offersData.searchTouristicPlaces)
            }
            setLoading(false)
        }
        fetchOffers().catch((error) => error)
    }, [location])

    return (
        <div className={styles.page}>
            <SearchBrowser
                defaultMode={mode}
                defaultQuery={query}
                defaultDateFrom={dateFrom}
                defaultDateTo={dateTo}
            />
            {offers.length !== 0 && <SearchFilter />}
            {!loading && (
                <SearchOffers
                    offers={offers}
                    setOffers={setOffers}
                    touristicPlaceId={touristicPlaceId}
                    setTouristicPlaceId={setTouristicPlaceId}
                    rank={rank}
                    setRank={setRank}
                    mode={mode}
                    query={query}
                    dateFrom={dateFrom}
                    dateTo={dateTo}
                    touristicPlaceType={touristicPlaceType}
                    attractionType={attractionType}
                />
            )}
        </div>
    )
}

export default SearchPage
