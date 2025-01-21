import { useRedirectEvery } from '../../../hooks/useRedirect.ts'
import { useLocation } from 'react-router-dom'
import { useHooks } from '../../../hooks/useHooks.ts'
import PageContent from '../../../components/Shared/Contents/PageContent'
import PageReturn from '../../../components/Shared/PageReturn'
import OfferPanel from '../../../components/Offer/OfferPanel'

const OfferPage = () => {
    const { t } = useHooks()
    const offer = useLocation().state?.offer || null
    const dateFrom = useLocation().state?.dateFrom || null
    const dateTo = useLocation().state?.dateTo || null

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
