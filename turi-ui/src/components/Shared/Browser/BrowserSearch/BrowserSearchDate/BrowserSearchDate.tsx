import { useHooks } from '../../../../../hooks/shared/useHooks.ts'
import BrowserSearchText from '../BrowserSearchText'
import BrowserSearchDateInput from '../BrowserSearchDateInput'
import styles from './BrowserSearchDate.module.css'

interface Props {
    dateFrom: string | null
    setDateFrom: (value: ((prevState: string | null) => string | null) | string | null) => void
    dateTo: string | null
    setDateTo: (value: ((prevState: string | null) => string | null) | string | null) => void
}

const BrowserSearchDate = ({ dateFrom, setDateFrom, dateTo, setDateTo }: Props) => {
    const { t } = useHooks()

    return (
        <div className={styles.date}>
            <BrowserSearchDateInput
                date={dateFrom}
                setDate={setDateFrom}
                text={t('home.dashboard.calendar-from')}
            />
            <BrowserSearchText text={t('home.dashboard.calendar-dash')} />
            <BrowserSearchDateInput
                date={dateTo}
                setDate={setDateTo}
                text={t('home.dashboard.calendar-to')}
            />
        </div>
    )
}

export default BrowserSearchDate
