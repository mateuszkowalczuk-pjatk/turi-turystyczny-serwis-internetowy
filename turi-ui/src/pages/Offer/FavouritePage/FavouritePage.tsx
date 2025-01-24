import { useRedirectEvery } from '../../../hooks/shared/useRedirect.ts'
import { useHooks } from '../../../hooks/shared/useHooks.ts'
import PageContent from '../../../components/Shared/Contents/PageContent'
import FavouriteOffers from '../../../components/Offer/FavouriteOffers'
import PageTitle from '../../../components/Shared/PageTitle'
import { useStates } from '../../../hooks/shared/useStates.ts'

const FavouritePage = () => {
    const { t } = useHooks()
    const { isAuthenticated } = useStates()

    useRedirectEvery([!isAuthenticated], '/')

    return (
        <PageContent
            title={<PageTitle text={t('offer.favourite')} />}
            firstPanel={<FavouriteOffers />}
        />
    )
}

export default FavouritePage
