import { ChangeEvent } from 'react'
import { useTranslation } from 'react-i18next'
import Input from '../../../Controls/Input'
import styles from './BrowserSearchInput.module.css'

const BrowserSearchInput = () => {
    const { t } = useTranslation()

    return (
        <div className={styles.input}>
            <Input
                type={'text'}
                placeholder={t('home.dashboard.placeholder')}
                name={''}
                value={''}
                onChange={(e: ChangeEvent<HTMLInputElement>) => console.log(e)}
                required
            />
        </div>
    )
}

export default BrowserSearchInput
