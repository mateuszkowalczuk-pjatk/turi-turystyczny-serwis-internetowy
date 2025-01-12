import { useRedirectEvery } from '../../../hooks/useRedirect.ts'
import { useAuthenticated } from '../../../store/slices/auth.ts'
import { useTranslation } from 'react-i18next'
import PageContent from '../../../components/Shared/Contents/PageContent'
import FavouriteOffers from '../../../components/Offer/FavouriteOffers'
import PageTitle from '../../../components/Shared/PageTitle'

const FavouritePage = () => {
    const { t } = useTranslation()
    const isAuthenticated = useAuthenticated()

    useRedirectEvery([!isAuthenticated], '/')

    return (
        <PageContent
            title={<PageTitle text={t('offer.favourite')} />}
            firstPanel={<FavouriteOffers />}
        />
    )
}

export default FavouritePage
