import React from 'react'
import { useHooks } from '../../../../../hooks/shared/useHooks.ts'
import Input from '../../../Controls/Input'
import styles from './BrowserSearchInput.module.css'

interface Props {
    query: string
    setQuery: (value: ((prevState: string) => string) | string) => void
}

const BrowserSearchInput = ({ query, setQuery }: Props) => {
    const { t } = useHooks()

    return (
        <div className={styles.input}>
            <Input
                type={'text'}
                placeholder={t('home.dashboard.placeholder')}
                name={'query'}
                value={query}
                onChange={(e: React.ChangeEvent<HTMLInputElement>) => setQuery(e.target.value)}
                required
            />
        </div>
    )
}

export default BrowserSearchInput
