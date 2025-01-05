import { useRedirectEvery } from '../../../hooks/useRedirect.ts'
import { useAuthenticated } from '../../../store/slices/auth.ts'
import { useTranslation } from 'react-i18next'
import FavouriteOffers from '../../../components/Offer/FavouriteOffers'
import PageTitle from '../../../components/Shared/PageTitle'
import Content from '../../../components/Shared/Content'
import styles from '../../Page.module.css'

const FavouritePage = () => {
    const { t } = useTranslation()
    const isAuthenticated = useAuthenticated()

    useRedirectEvery([!isAuthenticated], '/')

    return (
        <div className={styles.page}>
            <Content
                title={<PageTitle text={t('offer.favourite')} />}
                firstPanel={<FavouriteOffers />}
            />
        </div>
    )
}

export default FavouritePage
