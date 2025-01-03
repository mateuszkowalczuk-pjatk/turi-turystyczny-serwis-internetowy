import React from 'react'
import styles from './BrowserSearchDateInput.module.css'

interface Props {
    date: string | null
    setDate: (value: ((prevState: string | null) => string | null) | string | null) => void
    text: string
}

const BrowserSearchDateInput = ({ date, setDate, text }: Props) => {
    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const date = e.target.value
        setDate(date ? date : null)
    }

    return (
        <div className={styles.wrapper}>
            <input
                className={`${styles.input} ${!date ? styles.empty : ''}`}
                type="date"
                value={date ? date : ''}
                onChange={handleChange}
            />
            {!date && <span className={styles.placeholder}>{text}</span>}
        </div>
    )
}

export default BrowserSearchDateInput
