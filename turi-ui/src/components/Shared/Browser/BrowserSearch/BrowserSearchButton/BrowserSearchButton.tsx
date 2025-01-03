import { useTranslation } from 'react-i18next'
import { GreenButton } from '../../../Controls/Button'
import styles from './BrowserSearchButton.module.css'

const BrowserSearchButton = ({ handleSearch }: { handleSearch?: () => void }) => {
    const { t } = useTranslation()

    return (
        <div className={styles.button}>
            <GreenButton
                text={t('home.dashboard.search-button')}
                onClick={handleSearch}
            />
        </div>
    )
}

export default BrowserSearchButton
