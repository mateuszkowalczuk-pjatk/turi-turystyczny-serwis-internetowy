import { useRedirectEvery } from '../../../hooks/useRedirect.ts'
import { useLocation } from 'react-router-dom'
import { useHooks } from '../../../hooks/useHooks.ts'
import PageContent from '../../../components/Shared/Contents/PageContent'
import PageReturn from '../../../components/Shared/PageReturn'
import OfferPanel from '../../../components/Offer/OfferPanel'

const OfferPage = () => {
    const { t } = useHooks()
    const { offer = null, dateFrom = null, dateTo = null } = useLocation().state || {}

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
