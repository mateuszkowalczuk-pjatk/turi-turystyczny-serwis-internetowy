import { useEffect, useRef, useState } from 'react'
import BrowserSearchIcon from '../BrowserSearchIcon'
import BrowserSearchInput from '../BrowserSearchInput'
import BrowserSearchButton from '../BrowserSearchButton'
import { offerService } from '../../../../../services/offerService.ts'
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
    const wrapper = useRef<HTMLDivElement>(null)

    useEffect(() => {
        const fetchAutocomplete = async (query: string) => {
            const response = await offerService.autocomplete(mode, query)
            const data = await response.json()
            setAutocomplete(data)
        }

        const debounceFetch = setTimeout(() => {
            if (query) fetchAutocomplete(query).catch((error) => error)
            else setAutocomplete([])
        }, 300)

        return () => clearTimeout(debounceFetch)
    }, [mode, query])

    useEffect(() => {
        const handleClickOutside = (event: MouseEvent) => {
            if (wrapper.current && !wrapper.current.contains(event.target as Node)) {
                setIsFocused(false)
            }
        }

        document.addEventListener('mousedown', handleClickOutside)
        return () => {
            document.removeEventListener('mousedown', handleClickOutside)
        }
    }, [])

    return (
        <div
            className={styles.wrapper}
            ref={wrapper}
        >
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
