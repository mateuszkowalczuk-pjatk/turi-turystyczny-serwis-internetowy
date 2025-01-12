import { useLocation, useNavigate } from 'react-router-dom'
import { useTranslation } from 'react-i18next'
import { useEffect } from 'react'
import PageContent from '../../../components/Shared/Contents/PageContent'
import PageReturn from '../../../components/Shared/PageReturn'
import OfferPanel from '../../../components/Offer/OfferPanel'

const OfferPage = () => {
    const { t } = useTranslation()
    const navigate = useNavigate()
    const offer = useLocation().state?.offer || null
    const url = useLocation().state?.url || ''

    useEffect(() => {
        if (offer === null) navigate('/')
    }, [navigate, offer])

    return (
        <PageContent
            title={
                <PageReturn
                    text={t('offer.return')}
                    url={url}
                />
            }
            firstPanel={<OfferPanel offer={offer} />}
        />
    )
}

export default OfferPage
