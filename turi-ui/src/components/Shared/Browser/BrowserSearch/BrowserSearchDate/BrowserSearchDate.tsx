import { useTranslation } from 'react-i18next'
import BrowserSearchCalendar from '../BrowserSearchCalendar'
import BrowserSearchText from '../BrowserSearchText'
import styles from './BrowserSearchDate.module.css'

const BrowserSearchDate = () => {
    const { t } = useTranslation()

    return (
        <div
            className={styles.date}
            onClick={() => console.log('Search')}
            role="button"
            tabIndex={0}
        >
            <BrowserSearchCalendar />
            <BrowserSearchText text={t('home.dashboard.calendar-from')} />
            <BrowserSearchText
                text={t('home.dashboard.calendar-dash')}
                dash={true}
            />
            <BrowserSearchText text={t('home.dashboard.calendar-to')} />
        </div>
    )
}

export default BrowserSearchDate
