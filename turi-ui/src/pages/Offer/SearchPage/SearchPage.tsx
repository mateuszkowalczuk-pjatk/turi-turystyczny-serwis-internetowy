import { useHooks } from '../../../hooks/shared/useHooks.ts'
import { useEffect, useState } from 'react'
import SearchOffers from '../../../components/Offer/SearchOffers'
import SearchBrowser from '../../../components/Offer/SearchBrowser'
import { Offer, Search, SearchMode } from '../../../types/offer.ts'
import { offerService } from '../../../services/offerService.ts'

const SearchPage = () => {
    const { location } = useHooks()
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
    const [offers, setOffers] = useState<Offer[]>([])
    const [loading, setLoading] = useState(true)

    useEffect(() => {
        const fetchOffers = async () => {
            setOffers([])
            const offersResponse = await offerService.search(
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
            if (offersData.offers.length !== 0) {
                setTouristicPlaceId(offersData.touristicPlaceId.toString())
                setRank(offersData.rank.toString())
                setOffers(offersData.offers)
            }
            setLoading(false)
        }
        fetchOffers().catch((error) => error)
    }, [location])

    return (
        <>
            <SearchBrowser
                defaultMode={mode}
                defaultQuery={query}
                defaultDateFrom={dateFrom}
                defaultDateTo={dateTo}
            />
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
        </>
    )
}

export default SearchPage
