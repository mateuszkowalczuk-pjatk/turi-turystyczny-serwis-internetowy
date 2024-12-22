import { useTranslation } from 'react-i18next'
import TextMediumExtra from '../../../Shared/Controls/Text/TextMediumExtra'
import TextExtraLight from '../../../Shared/Controls/Text/TextExtraLight'
import styles from './MainDashboardBanner.module.css'

const MainDashboardBanner = () => {
    const { t } = useTranslation()

    return (
        <div className={styles.banner}>
            <TextMediumExtra text={t('home.dashboard.header-text')} />
            <TextExtraLight text={t('home.dashboard.text')} />
        </div>
    )
}

export default MainDashboardBanner
