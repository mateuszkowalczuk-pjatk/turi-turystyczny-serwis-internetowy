import { useHooks } from '../../../hooks/shared/useHooks.ts'
import { useRedirectEvery } from '../../../hooks/shared/useRedirect.ts'
import PageReturn from '../../../components/Shared/PageReturn'
import OfferPanel from '../../../components/Offer/OfferPanel'
import PageContent from '../../../components/Shared/Contents/PageContent'

const OfferPage = () => {
    const { t, location } = useHooks()
    const { offer = null, dateFrom = null, dateTo = null } = location.state || {}

    useRedirectEvery([offer === null], '/')

    return (
        <PageContent
            title={<PageReturn text={t('offer.return')} />}
            firstPanel={
                <OfferPanel
                    offer={offer}
                    dateFrom={dateFrom}
                    dateTo={dateTo}
                />
            }
        />
    )
}

export default OfferPage
