import { useState } from 'react'
import BrowserSearch from '../BrowserSearch/BrowserSearch'
import BrowserTypeButtons from '../BrowserTypeButtons'
import styles from './Browser.module.css'

interface Props {
    defaultMode: string
    defaultQuery: string
    defaultDateFrom: string | null
    defaultDateTo: string | null
}

const Browser = ({ defaultMode, defaultQuery, defaultDateFrom, defaultDateTo }: Props) => {
    const [mode, setMode] = useState<string>(defaultMode)
    const [query, setQuery] = useState<string>(defaultQuery)
    const [dateFrom, setDateFrom] = useState<string | null>(defaultDateFrom)
    const [dateTo, setDateTo] = useState<string | null>(defaultDateTo)

    const handleSearch = () => {
        if (
            query !== '' &&
            ((dateFrom !== null && dateTo !== null && dateFrom <= dateTo) || (dateFrom === null && dateTo === null))
        ) {
            window.location.href =
                '/search' +
                '?mode=' +
                mode +
                '&query=' +
                query +
                (dateFrom != null ? '&dateFrom=' + dateFrom : '') +
                (dateTo != null ? '&dateTo=' + dateTo : '')
        } else {
            setDateFrom(null)
            setDateTo(null)
        }
    }

    return (
        <div className={styles.browser}>
            <BrowserSearch
                query={query}
                setQuery={setQuery}
                dateFrom={dateFrom}
                setDateFrom={setDateFrom}
                dateTo={dateTo}
                setDateTo={setDateTo}
                handleSearch={handleSearch}
                mode={mode}
            />
            <BrowserTypeButtons
                mode={mode}
                setMode={setMode}
            />
        </div>
    )
}

export default Browser
