import { useHooks } from '../../../../../hooks/shared/useHooks.ts'
import { useEffect } from 'react'
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

    useEffect(() => {
        const day = new Date()
        day.setDate(day.getDate() + 1)

        if (dateFrom && new Date(dateFrom) < day) setDateFrom(day.toISOString().split('T')[0])

        if (dateFrom && dateTo && new Date(dateTo) <= new Date(dateFrom)) {
            const nextDay = new Date(dateFrom)
            nextDay.setDate(nextDay.getDate() + 1)
            setDateTo(nextDay.toISOString().split('T')[0])
        }
    }, [dateFrom, dateTo, setDateFrom, setDateTo])

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
