import { useEffect, useState } from 'react'
import BrowserSearchIcon from '../BrowserSearchIcon'
import BrowserSearchInput from '../BrowserSearchInput'
import BrowserSearchButton from '../BrowserSearchButton'
import { searchService } from '../../../../../services/searchService.ts'
import styles from './BrowserSearchPanel.module.css'

interface Props {
    query: string
    setQuery: (value: ((prevState: string) => string) | string) => void
    handleSearch: () => void
    mode: string
}

const BrowserSearchPanel = ({ query, setQuery, handleSearch, mode }: Props) => {
    const [autocomplete, setAutocomplete] = useState<string[]>([])
    const [isFocused, setIsFocused] = useState(false)

    useEffect(() => {
        const fetchAutocomplete = async (query: string) => {
            const response = await searchService.autocomplete(mode, query)
            const data = await response.json()
            setAutocomplete(data)
        }

        const debounceFetch = setTimeout(() => {
            if (query) fetchAutocomplete(query).catch((error) => error)
            else setAutocomplete([])
        }, 300)

        return () => clearTimeout(debounceFetch)
    }, [mode, query])

    return (
        <div className={styles.wrapper}>
            <div className={styles.panel}>
                <BrowserSearchIcon />
                <BrowserSearchInput
                    query={query}
                    setQuery={(value) => {
                        setQuery(value)
                        setIsFocused(true)
                    }}
                />
                <BrowserSearchButton handleSearch={handleSearch} />
            </div>
            {isFocused && autocomplete.length > 0 && (
                <ul
                    className={styles.autocompletes}
                    onMouseDown={(e) => e.preventDefault()}
                >
                    {autocomplete.map((autocomplete, index) => (
                        <li
                            key={index}
                            className={styles.autocomplete}
                            onClick={() => {
                                setQuery(autocomplete)
                                setIsFocused(false)
                            }}
                        >
                            {autocomplete}
                        </li>
                    ))}
                </ul>
            )}
        </div>
    )
}

export default BrowserSearchPanel
