import { useTranslation } from 'react-i18next'
import Input from '../../../Controls/Input'
import styles from './BrowserSearchInput.module.css'

const BrowserSearchInput = () => {
    const { t } = useTranslation()

    return (
        <div className={styles.input}>
            <Input placeholder={t('home.dashboard.placeholder')} />
        </div>
    )
}

export default BrowserSearchInput
