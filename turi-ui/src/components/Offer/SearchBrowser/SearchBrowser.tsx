import Browser from '../../Shared/Browser/Browser'
import styles from './SearchBrowser.module.css'

interface Props {
    defaultMode: string
    defaultQuery: string
    defaultDateFrom: string | null
    defaultDateTo: string | null
}

const SearchBrowser = ({ defaultMode, defaultQuery, defaultDateFrom, defaultDateTo }: Props) => {
    return (
        <div className={styles.content}>
            <div className={styles.browser}>
                <Browser
                    defaultMode={defaultMode}
                    defaultQuery={defaultQuery}
                    defaultDateFrom={defaultDateFrom}
                    defaultDateTo={defaultDateTo}
                />
            </div>
        </div>
    )
}

export default SearchBrowser
