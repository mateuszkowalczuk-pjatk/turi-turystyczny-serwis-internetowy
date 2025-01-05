import { useLocation, useNavigate } from 'react-router-dom'
import { useTranslation } from 'react-i18next'
import { useEffect } from 'react'
import Content from '../../../components/Shared/Content'
import Return from '../../../components/Shared/Return'
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
        <Content
            title={
                <Return
                    text={t('offer.return')}
                    url={url}
                />
            }
            firstPanel={<OfferPanel offer={offer} />}
        />
    )
}

export default OfferPage
