import BrowserSearchDate from '../BrowserSearchDate'
import BrowserSearchPanel from '../BrowserSearchPanel'
import styles from './BrowserSearch.module.css'

interface Props {
    query: string
    setQuery: (value: ((prevState: string) => string) | string) => void
    dateFrom: string | null
    setDateFrom: (value: ((prevState: string | null) => string | null) | string | null) => void
    dateTo: string | null
    setDateTo: (value: ((prevState: string | null) => string | null) | string | null) => void
    handleSearch: () => void
    mode: string
}

const BrowserSearch = ({ query, setQuery, dateFrom, setDateFrom, dateTo, setDateTo, handleSearch, mode }: Props) => {
    return (
        <div className={styles.search}>
            <BrowserSearchDate
                dateFrom={dateFrom}
                setDateFrom={setDateFrom}
                dateTo={dateTo}
                setDateTo={setDateTo}
            />
            <BrowserSearchPanel
                query={query}
                setQuery={setQuery}
                handleSearch={handleSearch}
                mode={mode}
            />
        </div>
    )
}

export default BrowserSearch
