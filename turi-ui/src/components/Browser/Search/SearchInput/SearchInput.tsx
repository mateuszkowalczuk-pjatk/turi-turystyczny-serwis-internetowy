import { useTranslation } from 'react-i18next'
import Input from '../../../Controls/Input'
import styles from './SearchInput.module.css'

const SearchInput = () => {
    const { t } = useTranslation();

    return (
        <div className={styles.input}>
            <Input
                placeholder={t('dashboard.placeholder')}
            />
        </div>
    )
}

export default SearchInput;