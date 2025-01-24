import { useHooks } from '../../../../hooks/shared/useHooks.ts'
import TextExtraLight from '../../../Shared/Controls/Text/TextExtraLight'
import TextMediumExtra from '../../../Shared/Controls/Text/TextMediumExtra'
import styles from './MainDashboardBanner.module.css'

const MainDashboardBanner = () => {
    const { t } = useHooks()

    return (
        <div className={styles.banner}>
            <TextMediumExtra text={t('home.dashboard.header-text')} />
            <TextExtraLight text={t('home.dashboard.text')} />
        </div>
    )
}

export default MainDashboardBanner
